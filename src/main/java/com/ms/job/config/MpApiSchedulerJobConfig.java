package com.ms.job.config;

import com.ms.job.dao.MqApiSchedulerReadDao;
import com.ms.job.dao.MqApiSchedulerWriteDao;
import com.ms.job.job.MpApiSchedulerJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class MpApiSchedulerJobConfig {

    @Bean(name = "mpApiSchedulerJobDetail-partition1")
    public FactoryBean<JobDetail> mpApiSchedulerJobDetail(
            MqApiSchedulerReadDao mqApiSchedulerReadDao,
            MqApiSchedulerWriteDao mqApiSchedulerWriteDao) {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setGroup("jobGroup1");
        factoryBean.setName("jobGroupName1");
        factoryBean.setConcurrent(false);
        factoryBean.setTargetObject(new MpApiSchedulerJob(1, mqApiSchedulerReadDao, mqApiSchedulerWriteDao));
        factoryBean.setTargetMethod("execute");
        return factoryBean;
    }

    @Bean(name = "mpApiSchedulerJobTrigger-partition1")
    public FactoryBean<CronTrigger> mpApiSchedulerJobTrigger(
            @Qualifier("mpApiSchedulerJobDetail-partition1") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setGroup("jobGroup1");
        factoryBean.setName("jobGroupName1");
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0/3 * * * * ?");
        return factoryBean;
    }

    @Bean(name = "mpApiSchedulerJobDetail-partition2")
    public FactoryBean<JobDetail> mpApiSchedulerJobDetail2(
            MqApiSchedulerReadDao mqApiSchedulerReadDao,
            MqApiSchedulerWriteDao mqApiSchedulerWriteDao) {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setGroup("jobGroup2");
        factoryBean.setName("jobGroupName2");
        factoryBean.setConcurrent(false);
        factoryBean.setTargetObject(new MpApiSchedulerJob(2, mqApiSchedulerReadDao, mqApiSchedulerWriteDao));
        factoryBean.setTargetMethod("execute");
        return factoryBean;
    }

    @Bean(name = "mpApiSchedulerJobTrigger-partition2")
    public FactoryBean<CronTrigger> mpApiSchedulerJobTrigger2(
            @Qualifier("mpApiSchedulerJobDetail-partition2") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setGroup("jobGroup2");
        factoryBean.setName("jobGroupName2");
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0/3 * * * * ?");
        return factoryBean;
    }

    @Bean(name = "mpApiSchedulerJobDetail-partition3")
    public FactoryBean<JobDetail> mpApiSchedulerJobDetail3(
            MqApiSchedulerReadDao mqApiSchedulerReadDao,
            MqApiSchedulerWriteDao mqApiSchedulerWriteDao) {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setGroup("jobGroup3");
        factoryBean.setName("jobGroupName3");
        factoryBean.setConcurrent(false);
        factoryBean.setTargetObject(new MpApiSchedulerJob(3, mqApiSchedulerReadDao, mqApiSchedulerWriteDao));
        factoryBean.setTargetMethod("execute");
        return factoryBean;
    }

    @Bean(name = "mpApiSchedulerJobTrigger-partition3")
    public FactoryBean<CronTrigger> mpApiSchedulerJobTrigger3(
            @Qualifier("mpApiSchedulerJobDetail-partition3") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setGroup("jobGroup3");
        factoryBean.setName("jobGroupName3");
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0/3 * * * * ?");
        return factoryBean;
    }

    @Bean(name = "mpApiSchedulerJobDetail-partition4")
    public FactoryBean<JobDetail> mpApiSchedulerJobDetail4(
            MqApiSchedulerReadDao mqApiSchedulerReadDao,
            MqApiSchedulerWriteDao mqApiSchedulerWriteDao) {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setGroup("jobGroup4");
        factoryBean.setName("jobGroupName4");
        factoryBean.setConcurrent(false);
        factoryBean.setTargetObject(new MpApiSchedulerJob(4, mqApiSchedulerReadDao, mqApiSchedulerWriteDao));
        factoryBean.setTargetMethod("execute");
        return factoryBean;
    }

    @Bean(name = "mpApiSchedulerJobTrigger-partition4")
    public FactoryBean<CronTrigger> mpApiSchedulerJobTrigger4(
            @Qualifier("mpApiSchedulerJobDetail-partition4") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setGroup("jobGroup4");
        factoryBean.setName("jobGroupName4");
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0/3 * * * * ?");
        return factoryBean;
    }

    @Bean
    public SchedulerFactoryBean mpApiSchedulerJobScheduler(
            @Qualifier("mpApiSchedulerJobTrigger-partition1") CronTrigger cronTrigger1,
            @Qualifier("mpApiSchedulerJobTrigger-partition2") CronTrigger cronTrigger2,
            @Qualifier("mpApiSchedulerJobTrigger-partition3") CronTrigger cronTrigger3,
            @Qualifier("mpApiSchedulerJobTrigger-partition4") CronTrigger cronTrigger4) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setTriggers(cronTrigger1, cronTrigger2, cronTrigger3, cronTrigger4);
        return factoryBean;
    }
}