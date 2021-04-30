-- ----------------------------
-- Table structure for city_info
-- ----------------------------
DROP TABLE IF EXISTS `city_info`;
CREATE TABLE `city_info`  (
  `CITY_NO` varchar(20) CHARACTER SET utf8  NOT NULL COMMENT '城市编号',
  `CITY_NAME` varchar(32) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '城市名称',
  `PROVINCE` varchar(32) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '省份',
  `WEATHER_NO` varchar(20) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '获取天气对应的编号',
  `NOTE` varchar(200) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`CITY_NO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '城市业务表';

-- ----------------------------
-- Records of city_info
-- ----------------------------
BEGIN;
INSERT INTO `city_info` VALUES ('22', '上海', '江苏', '101020100', '备注');
INSERT INTO `city_info` VALUES ('23', '南京', '江苏', '101190101', '备注');
INSERT INTO `city_info` VALUES ('24', '南通', '江苏', '101190501', '备注');
COMMIT;

-- ----------------------------
-- Table structure for city_weather
-- ----------------------------
DROP TABLE IF EXISTS `city_weather`;
CREATE TABLE `city_weather`  (
  `CITY_ID` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '城市编号',
  `CITY_NAME` varchar(10) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '城市名称',
  `WEATHER_DATE` varchar(10) CHARACTER SET utf8  NOT NULL COMMENT '日期 YYYY-MM-DD',
  `DAY_WEATHER_ID` varchar(10) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '天气编号 0 晴 1 多云 ……',
  `DAY_WEATHER` varchar(20) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '天气类型',
  `DAY_TEMP` decimal(10, 2) NULL DEFAULT NULL COMMENT '温度',
  `DAY_WIND` varchar(20) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '风向',
  `DAY_WIND_COMP` varchar(20) CHARACTER SET utf8  NULL DEFAULT NULL COMMENT '风级',
  PRIMARY KEY (`CITY_ID`, `WEATHER_DATE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '天气信息表';
