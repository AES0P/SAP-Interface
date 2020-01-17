/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : sap-interface

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-17 22:46:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for po_header
-- ----------------------------
DROP TABLE IF EXISTS `po_header`;
CREATE TABLE `po_header` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `ebeln` int(10) NOT NULL,
  `bukrs` varchar(4) NOT NULL,
  `bstyp` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `loekz` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `aedat` date NOT NULL,
  `ernam` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`,`ebeln`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of po_header
-- ----------------------------
