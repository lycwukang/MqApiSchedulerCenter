package com.ms.job.dao;

import com.ms.job.dto.MqApiSchedulerDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MqApiSchedulerReadDao {

    List<MqApiSchedulerDTO> getWaitHandleMpApiSchedulerByPartition(
            @Param("partition") int partition, @Param("count") int count);
}