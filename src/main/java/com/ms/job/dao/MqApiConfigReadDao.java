package com.ms.job.dao;

import com.ms.job.dto.MqApiConfigDTO;
import org.apache.ibatis.annotations.Param;

public interface MqApiConfigReadDao {

    MqApiConfigDTO findByApi(@Param("api") String api);
}