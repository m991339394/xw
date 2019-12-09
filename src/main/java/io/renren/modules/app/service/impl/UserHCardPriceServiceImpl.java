package io.renren.modules.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.modules.app.dao.UserHCardPriceDao;
import io.renren.modules.app.model.form.HCardPriceForm;
import io.renren.modules.app.model.po.HCardPricePO;
import io.renren.modules.app.service.UserHCardPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 心意卡 服务实现类
 * </p>
 *
 * @author jgl
 * @since 2019-09-28
 */
@Service
public class UserHCardPriceServiceImpl extends ServiceImpl<UserHCardPriceDao, HCardPricePO> implements UserHCardPriceService {
	
	@Autowired
	UserHCardPriceDao priceDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int increaswHCardCount(List<HCardPriceForm> hCardPriceForms, String out_trade_no) {
		// 1、退卡(恢复心意卡数量)
		int flag=1;
		long count;
		int num;
		for (int i = 0; i < hCardPriceForms.size(); i++) {
			count=hCardPriceForms.get(i).getCount();
			HCardPricePO hCardPricePO=new HCardPricePO();
			hCardPricePO.setId(hCardPriceForms.get(i).getHcardPriceId());
			hCardPricePO.setStock(count);
			hCardPricePO.setSaleCount(count);
			num=priceDao.increaswHCardCount(hCardPricePO);
			if(num==0){
				flag=0;
				// 抛出异常
				throw new RRException("取消订单  退卡"+ "异常,"+" out_trade_no = " + out_trade_no );
			}
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int subHCardCount(List<HCardPriceForm> hCardPriceForms, String out_trade_no) {
		// 2、扣除心意卡
		int flag=1;
		long count;
		int num;
		for (int i = 0; i < hCardPriceForms.size(); i++) {
			count=hCardPriceForms.get(i).getCount();
			HCardPricePO hCardPricePO=new HCardPricePO();
			hCardPricePO.setId(hCardPriceForms.get(i).getHcardPriceId());
			hCardPricePO.setStock(count);
			hCardPricePO.setSaleCount(count);
			num=priceDao.subHCardCount(hCardPricePO);
			if(num==0){
				flag=0;
				// 抛出异常
				throw new  RRException("扣除心意卡"+ "异常,"+" out_trade_no = " + out_trade_no );
			}
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int subHCardCount(List<HCardPriceForm> hCardPriceForms) {
		// 2、扣除心意卡
				int flag=1;
				long count;
				int num;
				for (int i = 0; i < hCardPriceForms.size(); i++) {
					count=hCardPriceForms.get(i).getCount();
					HCardPricePO hCardPricePO=new HCardPricePO();
					hCardPricePO.setId(hCardPriceForms.get(i).getHcardPriceId());
					hCardPricePO.setStock(count);
					hCardPricePO.setSaleCount(count);
					num=priceDao.subHCardCount(hCardPricePO);
					if(num==0){
						flag=0;
						// 抛出异常
						throw new  RRException("扣除心意卡"+ "异常,");
					}
				}
				return flag;
	}
	
	@Override
	public List<HCardPricePO> findListByIds(String priceList){
		// 用户购买的卡
		List<HCardPriceForm> hCardPriceForms= JSONArray.parseArray(priceList, HCardPriceForm.class);
		List<Long> ids=new ArrayList<Long>();
		for (HCardPriceForm hCardPriceForm : hCardPriceForms) {
			ids.add(hCardPriceForm.getHcardPriceId());
		}
		// 库存
		List<HCardPricePO> hCardPricePOs=priceDao.selectBatchIds(ids);
		return hCardPricePOs;
	}

	@Override
	public int increaswHCardCount(Long hCardPriceId) {
		// TODO Auto-generated method stub
		HCardPricePO hCardPricePO=new HCardPricePO();
		hCardPricePO.setId(hCardPriceId);
		hCardPricePO.setStock(1L);
		hCardPricePO.setSaleCount(1L);
		int num=priceDao.increaswHCardCount(hCardPricePO);
		if(num==0){
			// 抛出异常
			throw new  RRException("扣除心意卡"+ "异常,"+" hCardPriceId = " + hCardPriceId );
		}
		return num;
	}
	
}
