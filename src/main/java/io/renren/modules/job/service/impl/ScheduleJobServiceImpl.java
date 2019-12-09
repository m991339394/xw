package io.renren.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.CronUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.job.dao.ScheduleJobDao;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.service.ScheduleJobService;
import io.renren.modules.job.utils.ScheduleUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.*;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {
	@Autowired
    private Scheduler scheduler;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		try {
			scheduler.clear();
			QueryWrapper<ScheduleJobEntity> queryWrapper=new QueryWrapper<ScheduleJobEntity>();
			queryWrapper.eq("status", Constant.ScheduleStatus.NORMAL.getValue());
			List<ScheduleJobEntity> scheduleJobList = this.list(queryWrapper);
			boolean flag;
			for(ScheduleJobEntity scheduleJob : scheduleJobList){
				CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
	            
				// 判断时间是否过期
				scheduleJob=checkTime(scheduler,scheduleJob);
				
				// 如果不存在，则创建
	            if(cronTrigger == null) {
	                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	            }else {
	                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
	            }
	            
			}
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String beanName = (String)params.get("beanName");

		IPage<ScheduleJobEntity> page = this.page(
			new Query<ScheduleJobEntity>().getPage(params),
			new QueryWrapper <ScheduleJobEntity>().like(StringUtils.isNotBlank(beanName),"bean_name", beanName)
		);

		return new PageUtils(page);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveJob(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        boolean flag=this.save(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        return flag;
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                
        return this.updateById(scheduleJob);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	this.removeByIds(Arrays.asList(jobIds));
	}

	@Override
    public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>(2);
    	map.put("list", jobIds);
    	map.put("status", status);
    	return baseMapper.updateBatch(map);
    }
    
	@Override
	@Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, this.getById(jobId));
    	}
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public int pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	return updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
    public int pause(Long jobId) {
    	ScheduleUtils.pauseJob(scheduler, jobId);
    	Long[] jobIds=new Long[1];
    	jobIds[0]=jobId;
    	return updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }
	
	@Transactional
	public int stop(ScheduleJobEntity scheduleJob) {
		 scheduleJob.setStatus(Constant.ScheduleStatus.PAUSE.getValue());
	    return baseMapper.updateById(scheduleJob);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }
	
	
	 /**
     * 
     * @Title: deleteScheduleJob   
     * @Description: 判断时间是否过期   ,如果过期给一个新的时间
     * @param: @param scheduler
     * @param: @param jobId      
     * @return: void      
     * @date: 2019年10月18日:上午10:21:47
     * @throws
     */
    public ScheduleJobEntity checkTime(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
    	
    	Date date=CronUtil.getDate(scheduleJob.getCronExpression());
    	
    	if(date==null) {
    		return scheduleJob;
    	}
    	long seconds=date.getTime()-10*60*1000;
    	long nowSeconds=System.currentTimeMillis();
    	
    	if(seconds<=nowSeconds) {
    		try {
    			nowSeconds=nowSeconds+2*60*1000; // 延时时间
				String cronExpression= CronUtil.getCronByOnce(nowSeconds);
				scheduleJob.setCronExpression(cronExpression);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return scheduleJob;
    }
    
}
