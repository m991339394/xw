package io.renren.modules.app.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.Constants.CouponConstants;
import io.renren.common.Constants.HCardConstants;
import io.renren.common.Constants.ScheduleJobConstants;
import io.renren.common.Result;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.CronUtil;
import io.renren.modules.app.dao.*;
import io.renren.modules.app.model.dto.UserHCardDTO;
import io.renren.modules.app.model.form.HCardOrderForm;
import io.renren.modules.app.model.form.HCardPriceForm;
import io.renren.modules.app.model.form.HCardPriceFormComp;
import io.renren.modules.app.model.po.*;
import io.renren.modules.app.service.UserHCardBuyLogService;
import io.renren.modules.app.service.UserHCardMapService;
import io.renren.modules.app.service.UserHCardOrderService;
import io.renren.modules.app.service.UserHCardPriceService;
import io.renren.modules.app.service.thirdParty.wxSdk.WXOrder;
import io.renren.modules.app.service.thirdParty.wxSdk.WXPayConstants;
import io.renren.common.utils.TimeUtil;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.service.ScheduleJobService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * <p>
 * 用户购买心意卡订单记录 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
@Service
public class UserHCardOrderServiceImpl extends ServiceImpl<UserHCardOrderDao, HCardOrderPO> implements UserHCardOrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserHCardOrderDao userHCardOrderDao;
    @Autowired
    UserHCardPriceDao priceDao;
    @Autowired
    UserHCardPriceService priceService;
    @Autowired
    WXOrder wXOrder;
    @Autowired
    UserUserHCardDao userUserHCardDao;
    @Autowired
    ScheduleJobService scheduleJobService;
    @Autowired
    UserHCardTypeDao userHCardTypeDao;
    @Autowired
    UserHCardMapService hCardMapService;
    @Autowired
    UserUserCouponDao couponDao;
    @Autowired
    UserHCardBuyLogService buyLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> getHCardOrder(HttpServletRequest request, HCardOrderForm hCardOrderForm) {

        int num;
        boolean flag;
        float discountAmount = 0;
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        HCardOrderPO hCardOrderPO = dozerBeanMapper.map(hCardOrderForm ,HCardOrderPO.class);
        Long hCardCouponId = hCardOrderPO.getUserCouponId();

        // 1、限购判断
        String priceList = hCardOrderPO.getHcardPriceIds();
        // 用户选购的卡
        List<HCardPriceForm> hCardPriceForms = JSONArray.parseArray(priceList, HCardPriceForm.class);
        // 根据价格id排序
        Collections.sort(hCardPriceForms, new HCardPriceFormComp());

        List<Long> ids = new ArrayList<Long>();
        for (HCardPriceForm hCardPriceForm : hCardPriceForms) {
            ids.add(hCardPriceForm.getHcardPriceId());
        }
        // 库存
        List<HCardPricePO> hCardPricePOs = priceDao.selectBatchIds(ids);

        // 用户购买记录
        // 限购判断
        Map<String, Object> limitBuyMap = getLimitBuy(hCardOrderPO, hCardPriceForms, hCardPricePOs, ids);
        boolean limitBuy = (boolean) limitBuyMap.get("limitBuy");
        if (limitBuy) {
            Object msg = limitBuyMap.get("msg");
            return Result.fail(msg.toString());
        }


        // 2心意卡数量是否充足
        int totalFee = 0;
        for (int i = 0; i < hCardPriceForms.size(); i++) {
            HCardPriceForm hCardPriceForm = hCardPriceForms.get(i);
            HCardPricePO hCardPricePO = hCardPricePOs.get(i);
            if (hCardPriceForm.getCount() > hCardPricePO.getStock()) {
                return Result.fail("库存不足！");
            }
            // 获取总金额
            totalFee += hCardPricePO.getPrice() * 100 * hCardPriceForm.getCount();
        }
        // 3、创建订单
        num = userHCardOrderDao.insert(hCardOrderPO);
        if (num == 0) {
            // 抛出异常
            throw new RRException("创建订单" + "异常");
        }
        // 4、扣除心意卡
        priceService.subHCardCount(hCardPriceForms);

        // 查询优惠卷
        if (hCardCouponId > 0) {
            UserCouponPO userCouponPO = couponDao.selectById(hCardCouponId);
            if (userCouponPO.getState() == CouponConstants.STATE_NOT_USED) {
                discountAmount = userCouponPO.getMoney() * 100;
                userCouponPO.setState(CouponConstants.STATE_USED);
                num = couponDao.updateById(userCouponPO);
                if (num == 0) {
                    // 抛出异常
                    throw new RRException("修改用户优惠卷状态" + "异常" + " userCouponId =" + userCouponPO.getId());
                }
            }
        }
        // 设置订单总金额
        hCardOrderPO.setTotalFee((totalFee - discountAmount) / 100f);
        hCardOrderForm.setTotalFee((totalFee - discountAmount) / 100f);
        // 5、调取统一支付接口
        Map map = wXOrder.wxPay(request, hCardOrderForm);
        // 6、判断微信订单是否创建成功
        Map<String, String> data = (Map) map.get("data");
        if (data.get("package") == null) {
            // 订单创建失败，恢复心意卡数量
            for (int i = 0; i < hCardPricePOs.size(); i++) {
                HCardPricePO hCardPricePO = new HCardPricePO();
                hCardPricePO.setId(hCardPricePOs.get(i).getId());
                hCardPricePO.setStock(hCardPricePOs.get(i).getStock());
                hCardPricePO.setSaleCount(hCardPricePOs.get(i).getSaleCount());
                num = priceDao.updateById(hCardPricePO);
                if (num == 0) {
                    // 抛出异常
                    throw new RRException("订单创建失败，恢复心意卡数量" + "异常," + " hCardOrderId =" + hCardPricePO.getId());
                }
            }
        } else {

            try {
                Map<String, String> order = (Map) map.get("order");
                // 7、订单创建成功 修改订单信息
                long nowSeconds = System.currentTimeMillis();
                hCardOrderPO.setOutTradeNo(order.get("out_trade_no"));
                hCardOrderPO.setSpbillCreateIp(order.get("spbill_create_ip"));
                hCardOrderPO.setTimeStart(TimeUtil.getTimestamp(nowSeconds));
                hCardOrderPO.setTimeExpire(TimeUtil.getTimestamp(nowSeconds + 30 * 60 * 1000));
                hCardOrderPO.setTradeType(order.get("trade_type"));
                hCardOrderPO.setState(0);
                num = userHCardOrderDao.updateById(hCardOrderPO);
                if (num == 0) {
                    // 抛出异常
                    throw new RRException("订单创建成功 修改订单信息" + "异常," + " out_trade_no = " + order.get("out_trade_no"));
                }
                // 8、 创建定时任务，时间到了未支付关闭订单
                Date date = new Date();
                ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
                scheduleJobEntity.setBeanName(ScheduleJobConstants.CANCEL_ORDER_BEAN_NAME);
                scheduleJobEntity.setCreateTime(date);
                scheduleJobEntity.setCronExpression(CronUtil.getCronByOnce(date.getTime() + ScheduleJobConstants.CANCEL_ORDER_DELAY_SECONDS));
                scheduleJobEntity.setParams(ScheduleJobConstants.CANCEL_ORDER_PARAMS + ScheduleJobConstants.SEPARATOR + hCardOrderPO.getId() + ScheduleJobConstants.SEPARATOR + order.get("out_trade_no"));
                scheduleJobEntity.setRemark(ScheduleJobConstants.CANCEL_ORDER_REMARK);
                scheduleJobEntity.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
                flag = scheduleJobService.saveJob(scheduleJobEntity);
                if (!flag) {
                    // 抛出异常
                    throw new RRException("创建定时任务" + "异常," + " out_trade_no = " + order.get("out_trade_no"));
                }
                // 保存定时任务第到订单中
                hCardOrderPO.setScheduleId(scheduleJobEntity.getJobId());
                num = userHCardOrderDao.updateById(hCardOrderPO);
                if (num == 0) {
                    // 抛出异常
                    throw new RRException("保存定时任务第到订单中" + "异常," + " out_trade_no = " + order.get("out_trade_no"));
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // 9、返回小程序
        return Result.success(data);
    }

    @Override
    public Result<?> getHCardOrder(HCardOrderPO hCardOrderVO) {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * @throws
     * @Title: getHCardOrder
     * @Description: 支付成功后修改订单状态
     * @param: @param request
     * @param: @param hCardOrderPO
     * @param: @return
     * @return: Result<?>
     * @date: 2019年10月4日:下午6:40:12
     */
    @Override
    @Transactional
    public int updateOrderState(Long out_trade_no_id) {
        HCardOrderPO hCardOrderPO = new HCardOrderPO();
        hCardOrderPO.setId(out_trade_no_id);
        hCardOrderPO.setState(HCardConstants.ORDER_PAID);
        int num = userHCardOrderDao.updateById(hCardOrderPO);
        if (num == 0) {
            throw new RRException("修改订单状态" + "异常," + " out_trade_no_id= " + out_trade_no_id + " state=" + HCardConstants.ORDER_PAID);
        }
        return num;
    }

    @Override
    @Transactional
    public int updateOrderState(Long out_trade_no_id, Integer state) {
        HCardOrderPO hCardOrderPO = new HCardOrderPO();
        hCardOrderPO.setId(out_trade_no_id);
        hCardOrderPO.setState(state);
        int num = userHCardOrderDao.updateById(hCardOrderPO);
        if (num == 0) {
            throw new RRException("修改订单状态" + "异常," + " out_trade_no_id= " + out_trade_no_id + " state=" + state);
        }
        return num;
    }

    /**
     * @throws
     * @Title: getHCardOrder
     * @Description: 支付成功后的通知
     * @param: @param request
     * @param: @param hCardOrderPO
     * @param: @return
     * @return: Result<?>
     * @date: 2019年10月4日:下午6:40:12
     */
    @Override
    @Transactional
    public Result<?> successfulPaymentNotify(String out_trade_no) {

        synchronized (out_trade_no) {
            QueryWrapper<HCardOrderPO> queryWrapper = new QueryWrapper<HCardOrderPO>();
            queryWrapper.eq("out_trade_no", out_trade_no);
            // 查询订单
            HCardOrderPO hCardOrderPO = userHCardOrderDao.selectOne(queryWrapper);
            Long scheduleId = hCardOrderPO.getScheduleId();
            int num = 1;
            if (hCardOrderPO.getState() == 0) { // 查询订单状态为0代表未完成

                // 1、查询订单状态
                Map<String, String> map = wXOrder.orderQuery(out_trade_no);
                if (WXPayConstants.FAIL.equals(map.get("return_code"))) {  //通信标识

                    return Result.fail(map.get("return_msg"));
                }
                if (WXPayConstants.FAIL.equals(map.get("result_code"))) { //业务结果

                    return Result.fail(map.get("err_code_des"));
                }
                if (WXPayConstants.SUCCESS.equals(map.get("trade_state"))) { //交易状态

                    // 实际支付金额
                    Integer cash_fee = Integer.parseInt(map.get("cash_fee"));
                    //订单金额
                    Integer totol_fee = Math.round(hCardOrderPO.getTotalFee() * 100);
                    if (cash_fee != totol_fee) {
                        return Result.fail("支付金额与订单金额不符！");
                    }

                    // 2、取消定时任务
                    scheduleJobService.pause(scheduleId);
                    // 3、给用户发卡
                    String priceList = hCardOrderPO.getHcardPriceIds();
                    // 用户购买的卡
                    List<HCardPriceForm> hCardPriceForms = JSONArray.parseArray(priceList, HCardPriceForm.class);
                    // 根据价格id排序
                    Collections.sort(hCardPriceForms, new HCardPriceFormComp());

                    List<Long> ids = new ArrayList<Long>();
                    for (HCardPriceForm hCardPriceForm : hCardPriceForms) {
                        ids.add(hCardPriceForm.getHcardPriceId());
                    }

                    // 库存
                    List<HCardPricePO> hCardPricePOs = priceService.findListByIds(priceList);
                    Long hCardTypeId = hCardPricePOs.get(0).getHcardTypeId();
                    HCardTypePO hCardTypePO = userHCardTypeDao.selectById(hCardTypeId);

                    HCardMapPO hCardMapPO = hCardMapService.getById(hCardOrderPO.gethCardMapId());

                    String img = hCardMapPO.getImg();

                    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
                    List<UserHCardPO> userHCardPOs = new ArrayList<UserHCardPO>();
                    int j = 0;
                    for (HCardPricePO hCardPricePO : hCardPricePOs) {
                        UserHCardPO userHcardPo = dozerBeanMapper.map(hCardOrderPO, UserHCardPO.class);
                        userHcardPo.setId(null);
                        userHcardPo.setConsumerOpenid(userHcardPo.getOpenid());
                        userHcardPo.setChannel(HCardConstants.CHANNEL_SELF_BUY);
                        userHcardPo.setFetchTime(new Date());
                        userHcardPo.setGiftRestrictionCount(hCardPricePO.getGiftRestrictionCount());
                        userHcardPo.setName(hCardPricePO.getName());
                        userHcardPo.setBalance(hCardPricePO.getPrice());
                        userHcardPo.sethCardTypeName(hCardTypePO.getName());
                        userHcardPo.setImg(img);
                        userHcardPo.sethCardPriceId(hCardPricePO.getId());
                        userHCardPOs.add(userHcardPo);

                        for (int i = 0; i < hCardPriceForms.get(j).getCount(); i++) {
                            //给用户发卡
                            int num2 = userUserHCardDao.insert(userHcardPo);
                            if (num2 == 0) {
                                throw new RRException("给用户发卡失败！" + "异常," + " out_trade_no = " + out_trade_no);
                            }
                            // 添加购卡记录
                            HCardBuyLogPO hCardBuyLogPO = new HCardBuyLogPO();
                            hCardBuyLogPO.setHcardId(userHcardPo.getId());
                            hCardBuyLogPO.setOpenid(hCardOrderPO.getOpenid());
                            hCardBuyLogPO.sethCardPriceId(hCardPricePO.getId());
                            hCardBuyLogPO.setOutTradeNo(hCardOrderPO.getOutTradeNo());
                            hCardBuyLogPO.setCreateTime(new Date());
                            hCardBuyLogPO.setState(0);
                            boolean flag = buyLogService.save(hCardBuyLogPO);
                            if (!flag) {
                                throw new RRException("添加购卡日志" + "异常," + " out_trade_no = " + out_trade_no);
                            }

                        }
                        j++;
                    }

                    // 4、修改订单状态
                    num = updateOrderState(hCardOrderPO.getId(), HCardConstants.ORDER_PAID);
                    // 发生异常，数据回滚
                    if (num == 0) {
                        throw new RRException("修改订单状态" + "异常," + " out_trade_no = " + out_trade_no);
                    }
                    return Result.success("购卡成功！");
                } else {
                    return Result.fail("未支付！");
                }
            }
            return Result.success("订单已完成！！");
        }

    }

    @Override
    @Transactional
    public Result<?> closeOrder(Long id) {
        // TODO Auto-generated method stub
        int num = 1;
        HCardOrderPO hCardOrderPO = userHCardOrderDao.selectById(id);

        if (hCardOrderPO.getState() == HCardConstants.ORDER_PAID) {
            return Result.success("订单已付款！");
        } else if (hCardOrderPO.getState() == HCardConstants.ORDER_CANCEL) {
            return Result.success("订单已经取消了！");
        }

        String out_trade_no = hCardOrderPO.getOutTradeNo();
        // 1、将库存和销售数量恢复
        String priceList = hCardOrderPO.getHcardPriceIds();
        // 用户购买的卡
        List<HCardPriceForm> hCardPriceForms = JSONArray.parseArray(priceList, HCardPriceForm.class);

        // 取消订单  退卡
        priceService.increaswHCardCount(hCardPriceForms, out_trade_no);

        // 2、修改订单状态
        num = updateOrderState(id, HCardConstants.ORDER_CANCEL);
        if (num == 0) {
            throw new RRException("取消订单  修改订单状态" + "异常," + " out_trade_no = " + out_trade_no);
        }

        //3、关闭定时任务
        scheduleJobService.pause(hCardOrderPO.getScheduleId());

        // 4、关闭微信支付订单
        Map<String, String> map = wXOrder.closeOrder(out_trade_no);

        if (WXPayConstants.FAIL.equals(map.get("return_code"))) {  //通信标识

            return Result.fail(map.get("return_msg"));
        }
        if (WXPayConstants.SUCCESS.equals(map.get("result_code"))) { //业务结果

            return Result.success(map.get("result_msg"));
        }
        if (WXPayConstants.FAIL.equals(map.get("result_code"))) { //业务结果

            return Result.fail(map.get("err_code_des"));
        }

        return Result.success();
    }

    @Override
    public Result<?> refund(String out_trade_no) {
        // TODO Auto-generated method stub
        // 查询订单
        HCardOrderPO hCardOrderPO = getHCardOrderByOutTradeNo(out_trade_no);

        if (hCardOrderPO == null) {

            return Result.fail("订单不存在！");
        }
        String openid = hCardOrderPO.getOpenid();

        Integer total_fee = Math.round(hCardOrderPO.getTotalFee() * 100);

        Map<String, String> data = wXOrder.refund(out_trade_no, openid, total_fee, total_fee);

        if (WXPayConstants.SUCCESS.equals(data.get("return_code"))) {
            if (WXPayConstants.SUCCESS.equals(data.get("result_code"))) {
                return Result.success(null, "退款成功！！！");
            } else {
                return Result.fail(data.get("err_code_des"));
            }

        } else {
            return Result.fail(data.get("return_msg"));
        }

    }

    /**
     * 根据订单号查询订单
     */
    @Override
    public HCardOrderPO getHCardOrderByOutTradeNo(String out_trade_no) {
        // TODO Auto-generated method stub
        QueryWrapper<HCardOrderPO> queryWrapper = new QueryWrapper<HCardOrderPO>();
        queryWrapper.eq("out_trade_no", out_trade_no);
        // 查询订单
        HCardOrderPO hCardOrderPO = userHCardOrderDao.selectOne(queryWrapper);
        return hCardOrderPO;
    }

    /**
     * 用户购买记录
     * 限购判断
     */
    public Map<String, Object> getLimitBuy(HCardOrderPO hCardOrderPO, List<HCardPriceForm> hCardPriceForms, List<HCardPricePO> hCardPricePOs, List<Long> ids) {

        boolean limitBuy = false;
        boolean flag = false;
        Map<String, Object> map = new HashMap<String, Object>();

        // 用户购买记录
        StringBuffer idsStringBuffer = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            idsStringBuffer.append(ids.get(i));
            idsStringBuffer.append(",");
        }
        String idsString = idsStringBuffer.toString();
        Map<String, Object> buyedMap = new HashMap<String, Object>();
        buyedMap.put("openid", hCardOrderPO.getOpenid());
        buyedMap.put("channel", HCardConstants.CHANNEL_SELF_BUY);
        buyedMap.put("priceIds", ids);
        List<UserHCardDTO> userHCardDTOs = userUserHCardDao.getBuyedCount(buyedMap);

        // 限购判断
        StringBuffer msg = new StringBuffer();
        msg.append("达到购买上限");

        for (int i = 0; i < hCardPricePOs.size(); i++) {
            HCardPricePO hCardPricePO = hCardPricePOs.get(i);
            flag = true;
            for (int j = 0; j < userHCardDTOs.size(); j++) {
                UserHCardDTO userHCardDTO = userHCardDTOs.get(j);
                if (hCardPricePO.getId() == userHCardDTO.gethCardPriceId()) {
                    if ((userHCardDTO.getCount() + hCardPriceForms.get(i).getCount()) > hCardPricePO.getLimitCount()) {
                        limitBuy = true;
                        msg.append("，单价为" + hCardPricePO.getPrice() + "卡，还可以购买" + (hCardPricePO.getLimitCount() - userHCardDTO.getCount()) + "张 ");
                    }
                    flag = false;
                }
            }

            if (flag) { // 之前没有购买过
                if (hCardPriceForms.get(i).getCount() > hCardPricePO.getLimitCount()) {
                    limitBuy = true;
                    msg.append("，单价为" + hCardPricePO.getPrice() + "卡，还可以购买" + hCardPricePO.getLimitCount() + "张 ");
                }
            }
        }
        msg.append("。");
        map.put("msg", msg);
        map.put("limitBuy", limitBuy);
        return map;
    }


}
