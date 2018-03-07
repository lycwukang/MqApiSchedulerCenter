package com.ms.job.job;

import com.alibaba.fastjson.JSON;
import com.ms.job.dao.MqApiSchedulerReadDao;
import com.ms.job.dao.MqApiSchedulerWriteDao;
import com.ms.job.model.MpApiPostResult;
import com.ms.job.dto.MqApiSchedulerDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class MpApiSchedulerJob implements JobBase {

    private final Logger logger = LoggerFactory.getLogger(MpApiSchedulerJob.class);

    public MpApiSchedulerJob(int partition, MqApiSchedulerReadDao mqApiSchedulerReadDao, MqApiSchedulerWriteDao mqApiSchedulerWriteDao) {
        this.partition = partition;
        this.mqApiSchedulerReadDao = mqApiSchedulerReadDao;
        this.mqApiSchedulerWriteDao = mqApiSchedulerWriteDao;
    }

    private int partition;
    private MqApiSchedulerReadDao mqApiSchedulerReadDao;
    private MqApiSchedulerWriteDao mqApiSchedulerWriteDao;

    public void execute() {
        List<MqApiSchedulerDTO> schedulerDTOList = mqApiSchedulerReadDao.getWaitHandleMpApiSchedulerByPartition(partition, 100);
        for (MqApiSchedulerDTO schedulerDTO : schedulerDTOList) {
            // 获取悲观锁
            if (mqApiSchedulerWriteDao.lockMpApiScheduler(schedulerDTO.getId()) > 0) {
                logger.info("获取锁 - " + JSON.toJSONString(schedulerDTO));
                // 记录请求时间
                schedulerDTO.setPreviousRequestTime(new Date());
                if (schedulerDTO.getRequestCount() == 0) {
                    schedulerDTO.setProcessTime(new Date());
                }
                schedulerDTO.setRequestCount(schedulerDTO.getRequestCount() + 1);

                try {
                    MpApiPostResult result = post(schedulerDTO.getRequestAddress(), schedulerDTO.getContent());
                    if (result.isSuccess()) {
                        schedulerDTO.setRequestStatus(1);
                        schedulerDTO.setRequestResult(JSON.toJSONString(result));
                    } else {
                        // 下一次请求时间
                        schedulerDTO.setNextRequestTime(NextRequestTimeRule.nextTime(schedulerDTO.getPreviousRequestTime(), schedulerDTO.getRequestCount()));
                        // 超过5次失败，放弃
                        if (schedulerDTO.getRequestCount() > 7) {
                            schedulerDTO.setRequestStatus(9);
                        }
                        schedulerDTO.setRequestResult(JSON.toJSONString(result));
                    }
                } catch (Throwable e) {
                    logger.error("请求出错 - " + JSON.toJSONString(schedulerDTO), e);
                    // 下一次请求时间
                    schedulerDTO.setNextRequestTime(NextRequestTimeRule.nextTime(schedulerDTO.getPreviousRequestTime(), schedulerDTO.getRequestCount()));
                    // 超过5次失败，放弃
                    if (schedulerDTO.getRequestCount() > 7) {
                        schedulerDTO.setRequestStatus(9);
                    }
                    schedulerDTO.setRequestResult("请求出错:" + e.getMessage());
                }

                try {
                    // 保存状态
                    mqApiSchedulerWriteDao.updateMpApiScheduler(schedulerDTO);
                } finally {
                    // 释放锁
                    logger.info("释放锁 - " + JSON.toJSONString(schedulerDTO));
                    mqApiSchedulerWriteDao.freeLockMpApiScheduler(schedulerDTO.getId());
                }
            }
        }
    }

    private static MpApiPostResult post(String address, String body) throws Exception {
        HttpPost post = new HttpPost(address);
        StringEntity entity = new StringEntity(body, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json;charset=utf-8");
        post.setEntity(entity);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse resp = client.execute(post);

        String respContent = EntityUtils.toString(resp.getEntity(), "UTF-8");

        MpApiPostResult result;
        if (respContent.equalsIgnoreCase("SUCCESS")) {
            result = new MpApiPostResult();
            result.setSuccess(true);
            result.setResult(0);
            result.setMessage("SUCCESS");
            return result;
        }
        if (respContent.equalsIgnoreCase("FAIL")) {
            result = new MpApiPostResult();
            result.setSuccess(false);
            result.setResult(1);
            result.setMessage("FAIL");
            return result;
        }

        try {
            result = JSON.parseObject(respContent, MpApiPostResult.class);
        } catch (Exception e) {
            result = new MpApiPostResult();
            result.setSuccess(false);
            result.setResult(9);
            result.setMessage("body数据序列化出错:" + e.getMessage());
            return result;
        }

        return result;
    }
}