-- ----------------------------
-- Table structure for time_job
-- ----------------------------
DROP TABLE IF EXISTS `time_job`;
CREATE TABLE `time_job`  (
  `NO` varchar(36) CHARACTER SET utf8  NOT NULL COMMENT '编号',
  `TRIG_NAME` varchar(40) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '触发名称',
  `CRON` varchar(40) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT 'cron表达式',
  `JOB_NAME` varchar(40) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '任务名称',
  `OBJ_NAME` varchar(80) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '任务实现类',
  `CONCURRENT` int(11) NULL DEFAULT NULL COMMENT '是否允许并发',
  `JOB_STATE` int(11) NULL DEFAULT NULL COMMENT '任务状态',
  `DESP` varchar(100) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '描述',
  `ARGUMENTS` varchar(100) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '任务执行参数',
  `INIT_FLAG` int(11) NULL DEFAULT NULL COMMENT '初始化标志',
  PRIMARY KEY (`NO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8  COMMENT = '定时任务表';

-- ----------------------------
-- Table structure for time_job_log
-- ----------------------------
DROP TABLE IF EXISTS `time_job_log`;
CREATE TABLE `time_job_log`  (
  `LOGIC_NO` varchar(36) CHARACTER SET utf8  NOT NULL COMMENT '任务编号',
  `JOB_NAME` varchar(40) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '任务名称',
  `JOB_TYPE` int(11) NULL DEFAULT NULL COMMENT '任务类型',
  `JOB_RESULT` int(11) NULL DEFAULT NULL COMMENT '任务结果',
  `JOB_CREATOR` varchar(20) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '任务创建人',
  `START_TIME` varchar(20) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '任务开始时间',
  `END_TIME` varchar(20) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '任务结束时间',
  `RESULT_DESC` text CHARACTER SET utf8  NULL COMMENT '任务结果描述',
  PRIMARY KEY (`LOGIC_NO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '定时任务日志表';
