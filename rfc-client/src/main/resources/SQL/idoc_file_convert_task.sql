/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : sap-interface

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-17 22:46:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for idoc_file_convert_task
-- ----------------------------
DROP TABLE IF EXISTS `idoc_file_convert_task`;
CREATE TABLE `idoc_file_convert_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idoc_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `directory` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `done` int(1) NOT NULL,
  `convert_date` date NOT NULL,
  `convert_time` time NOT NULL,
  `convert_failure_times` int(1) NOT NULL,
  `version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`,`idoc_type`,`directory`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of idoc_file_convert_task
-- ----------------------------
