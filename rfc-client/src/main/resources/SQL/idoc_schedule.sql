/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : sap-interface

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-17 22:46:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for idoc_schedule
-- ----------------------------
DROP TABLE IF EXISTS `idoc_schedule`;
CREATE TABLE `idoc_schedule` (
  `id` int(11) NOT NULL,
  `cron` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of idoc_schedule
-- ----------------------------
INSERT INTO `idoc_schedule` VALUES ('1', '0/30 * * * * ?');
