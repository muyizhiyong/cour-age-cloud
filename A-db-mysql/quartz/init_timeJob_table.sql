-- 获取天气
INSERT INTO `time_job` VALUES ('100001', 'TestWeatherJob_1', '0 30 8 * * ?', 'weatherJob', 'com.muyi.courage.quartz.jobs.WeatherJob', 0, 1, '天气定时任务', '{\"cityNo\":\"22\"}', 1);
INSERT INTO `time_job` VALUES ('100002', 'TestWeatherJob_2', '0 30 8 * * ?', 'weatherJob', 'com.muyi.courage.quartz.jobs.WeatherJob', 0, 1, '天气定时任务', '{\"cityNo\":\"23\"}', 1);
INSERT INTO `time_job` VALUES ('100003', 'TestWeatherJob_3', '0 30 8 * * ?', 'weatherJob', 'com.muyi.courage.quartz.jobs.WeatherJob', 0, 1, '天气定时任务', '{\"cityNo\":\"24\"}', 1);
