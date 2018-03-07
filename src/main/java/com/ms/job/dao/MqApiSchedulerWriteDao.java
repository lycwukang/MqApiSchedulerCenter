package com.ms.job.dao;

import com.ms.job.dto.MqApiSchedulerDTO;
import org.apache.ibatis.annotations.Param;

public interface MqApiSchedulerWriteDao {

    int addHandleMpApiScheduler(MqApiSchedulerDTO dto);

    int lockMpApiScheduler(@Param("id") Long id);

    int freeLockMpApiScheduler(@Param("id") Long id);

    int updateMpApiScheduler(MqApiSchedulerDTO dto);
}