package com.ms.job.web;

import com.alibaba.fastjson.JSON;
import com.ms.job.dao.MqApiConfigReadDao;
import com.ms.job.dao.MqApiSchedulerWriteDao;
import com.ms.job.model.MpApiRequestResult;
import com.ms.job.dto.MqApiConfigDTO;
import com.ms.job.dto.MqApiSchedulerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Api(description = "API中心")
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    MqApiConfigReadDao mqApiConfigReadDao;
    @Autowired
    MqApiSchedulerWriteDao mqApiSchedulerWriteDao;

    @ApiOperation(value = "发送API调用请求", response = MpApiRequestResult.class)
    @PostMapping("/mq/{name}")
    public MpApiRequestResult mq(@ApiParam("API编号") @PathVariable String name, @RequestBody String body) {
        MpApiRequestResult result = new MpApiRequestResult();
        MqApiConfigDTO configDTO = mqApiConfigReadDao.findByApi(name);
        if (configDTO == null) {
            result.setSuccess(false);
            result.setResult(8);
            result.setApi(name);
            result.setMessage("找不到对应的api");
            return result;
        }

        Map bodyMap;
        try {
            bodyMap = JSON.parseObject(body, Map.class);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(9);
            result.setApi(name);
            result.setMessage("body数据序列化出错:" + e.getMessage());
            return result;
        }

        MqApiSchedulerDTO schedulerDTO = new MqApiSchedulerDTO();
        schedulerDTO.setApi(name);
        schedulerDTO.setRequestAddress(configDTO.getRequestAddress());
        schedulerDTO.setContent(JSON.toJSONString(bodyMap));
        schedulerDTO.setPartition((Math.abs(schedulerDTO.getContent().hashCode()) % 4) + 1);
        schedulerDTO.setUid(UUID.randomUUID().toString().replace("-", ""));

        try {
            mqApiSchedulerWriteDao.addHandleMpApiScheduler(schedulerDTO);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(99);
            result.setApi(name);
            result.setMessage("数据保存出错:" + e.getMessage());
            return result;
        }

        result.setSuccess(true);
        result.setResult(0);
        result.setApi(name);
        result.setMessage("保存成功");
        return result;
    }
}