/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : sap-interface

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-17 22:46:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for po_lines
-- ----------------------------
DROP TABLE IF EXISTS `po_lines`;
CREATE TABLE `po_lines` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ebeln` int(10) NOT NULL,
  `ebelp` int(5) NOT NULL,
  `loekz` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `aedat` date DEFAULT NULL,
  `txz01` varchar(255) DEFAULT NULL,
  `bukrs` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `werks` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `lgort` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `menge` float(13,3) DEFAULT NULL,
  `meins` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bprme` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bpumz` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`,`ebeln`,`ebelp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of po_lines
-- ----------------------------
