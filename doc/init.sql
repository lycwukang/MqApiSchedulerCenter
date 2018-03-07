CREATE TABLE mq_api_scheduler (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `api` VARCHAR(200) NOT NULL COMMENT 'API名称',
  `partition` INT(11) NOT NULL DEFAULT 1 COMMENT '分区',
  `uid` VARCHAR(50) NOT NULL COMMENT '请求唯一编号',
  `content` TEXT NOT NULL COMMENT '请求内容',
  `request_address` VARCHAR(200) NOT NULL COMMENT '请求地址',
  `request_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '请求时间（生产者提供）',
  `process_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '请求处理时间（消费者真实处理时间）',
  `request_count` INT(11) NOT NULL DEFAULT 0 COMMENT '请求次数',
  `previous_request_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '上一次请求时间',
  `next_request_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '下一次请求时间',
  `request_status` INT(11) NOT NULL DEFAULT 0 COMMENT '请求状态',
  `request_result` TEXT COMMENT '请求结果',
  `possess_count` INT(11) NOT NULL DEFAULT 0 COMMENT '实现悲观锁',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  INDEX `INDEX_partition_request_status` (`partition`, `request_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'MQ的API调度实现';

CREATE TABLE mq_api_config (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `api` VARCHAR(200) NOT NULL COMMENT 'APICode',
  `name` VARCHAR(200) NOT NULL COMMENT 'API名称',
  `remark` VARCHAR(400) NOT NULL COMMENT 'API说明',
  `request_address` VARCHAR(200) NOT NULL COMMENT '请求地址',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  INDEX `INDEX_api` (`api`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'MQ的API调度实现';