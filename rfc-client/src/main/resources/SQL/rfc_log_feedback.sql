/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : sap-interface

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-17 22:46:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rfc_log_feedback
-- ----------------------------
DROP TABLE IF EXISTS `rfc_log_feedback`;
CREATE TABLE `rfc_log_feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msg_id` varchar(32) NOT NULL,
  `rfc` varchar(255) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `data_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `timestamp` bigint(20) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`,`msg_id`,`rfc`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rfc_log_feedback
-- ----------------------------
