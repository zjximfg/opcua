/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : tst_opc_ua

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-12-27 17:19:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_opc_ua_connection
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_connection`;
CREATE TABLE `tbl_opc_ua_connection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opc_ua_namespace_id` bigint(20) NOT NULL,
  `connection_name` varchar(255) NOT NULL,
  `opc_ua_server_id` bigint(20) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_connection
-- ----------------------------
INSERT INTO `tbl_opc_ua_connection` VALUES ('1', '4', '@LOCALSERVER', '3', '0');
INSERT INTO `tbl_opc_ua_connection` VALUES ('2', '4', 'S7_Connection_1', '3', '0');

-- ----------------------------
-- Table structure for tbl_opc_ua_group
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_group`;
CREATE TABLE `tbl_opc_ua_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  `storage_period_id` bigint(20) NOT NULL,
  `opc_ua_connection_id` bigint(20) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_group
-- ----------------------------
INSERT INTO `tbl_opc_ua_group` VALUES ('1', '1', '1', '1', '1');
INSERT INTO `tbl_opc_ua_group` VALUES ('2', 'DB11 - TEST', '1', '1', '0');

-- ----------------------------
-- Table structure for tbl_opc_ua_item
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_item`;
CREATE TABLE `tbl_opc_ua_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_category_id` bigint(20) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `opc_ua_connection_id` bigint(20) NOT NULL,
  `opc_ua_group_id` bigint(20) NOT NULL,
  `item_object_id` bigint(20) NOT NULL,
  `db_number` int(11) DEFAULT NULL,
  `address` int(11) NOT NULL,
  `item_type_id` bigint(20) NOT NULL,
  `bit_address` int(11) DEFAULT NULL,
  `string_length` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_array` tinyint(4) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_item
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_opc_ua_item_category
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_item_category`;
CREATE TABLE `tbl_opc_ua_item_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opc_ua_namespace_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_item_category
-- ----------------------------
INSERT INTO `tbl_opc_ua_item_category` VALUES ('1', '4', 'S7Basic', 'S7 基本类型，包括DB, M, I, Q, PI, PQ');
INSERT INTO `tbl_opc_ua_item_category` VALUES ('2', '4', 'S7Timer', 'S7 定时器， 包括 T');
INSERT INTO `tbl_opc_ua_item_category` VALUES ('3', '4', 'S7Counter', 'S7 计数器， 包括 C');

-- ----------------------------
-- Table structure for tbl_opc_ua_item_object
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_item_object`;
CREATE TABLE `tbl_opc_ua_item_object` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opc_ua_namespace_id` bigint(20) NOT NULL,
  `item_category_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_item_object
-- ----------------------------
INSERT INTO `tbl_opc_ua_item_object` VALUES ('1', '4', '1', 'db', '数据块或实例数据块。 数据块中 S7 变量的标识符。 在 S7 \r\n通信中，不区分实例数据块和正常数据块。 \r\n因此，无需为省事而分配附加标识符。 \r\n<编号> \r\n数据块或实例数据块的编号，无前导零。');
INSERT INTO `tbl_opc_ua_item_object` VALUES ('2', '4', '1', 'm', '位存储器 ');
INSERT INTO `tbl_opc_ua_item_object` VALUES ('3', '4', '1', 'i', '输入 \r\n可读写 \r\n为简化起见，仅使用英语标识符。');
INSERT INTO `tbl_opc_ua_item_object` VALUES ('4', '4', '1', 'q', '输出 \r\n可读写 \r\n为简化起见，仅使用英语标识符。');
INSERT INTO `tbl_opc_ua_item_object` VALUES ('5', '4', '1', 'pi', 'I/O 输入 \r\n只读 \r\n为简化起见，仅使用英语标识符。');
INSERT INTO `tbl_opc_ua_item_object` VALUES ('6', '4', '1', 'pq', 'I/O 输出 \r\n只写 \r\n为简化起见，仅使用英语标识符。');
INSERT INTO `tbl_opc_ua_item_object` VALUES ('7', '4', '2', 't', '定时器。 字（无符号）。 \r\n后面的地址信息为定时器编号。');
INSERT INTO `tbl_opc_ua_item_object` VALUES ('8', '0', '3', 'c', '计数器。 字（无符号）。 \r\n后面的地址信息是计数器编号。');

-- ----------------------------
-- Table structure for tbl_opc_ua_item_type
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_item_type`;
CREATE TABLE `tbl_opc_ua_item_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `opc_ua_namespace_id` bigint(20) NOT NULL,
  `item_category_id` bigint(20) NOT NULL,
  `s7_name` varchar(255) NOT NULL,
  `ua_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_item_type
-- ----------------------------
INSERT INTO `tbl_opc_ua_item_type` VALUES ('1', '4', '1', 'b', 'Byte', '字节（无符号），作为默认');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('2', '4', '1', 'w', 'UInt16', '字（无符号）');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('3', '4', '1', 'c', 'SByte', '字节（有符号）');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('4', '4', '1', 'i', 'Int16', '字（有符号）');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('5', '4', '1', 'di', 'Int32', '双字（有符号）');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('6', '4', '1', 'dw', 'UInt32', '双字（无符号）');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('7', '4', '1', 'r', 'Float', '浮点（4字节）');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('8', '4', '1', 'dt', 'DateTime', '日期时间，CPU DATE_AND_TIME');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('9', '4', '1', 't', 'Int32', '有符号时间值，单位为毫秒');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('10', '4', '1', 'tod', 'Int32', '时钟，从午夜开始， 0到86399999ms');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('11', '4', '1', 's5tbcd', 'UInt16', 'CPU数据类型S5TIME映射到UInt16（无符号，16位），取值范围限制为0至9990000ms');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('12', '4', '1', 'x', 'Boolean', '位（布尔），还必须在字节中指定<位地址>，值范围是0-7');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('13', '4', '1', 's', 'String', '字符串，还必须指定为字符串保留的<stringLength>,值范围是1到254');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('14', '4', '2', 'tbcd', 'UInt16', '定时器，BCD编码，作为Timer默认');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('15', '4', '2', 'tda', 'UInt16[2]', '定时器，十进制时间基准和时间值');
INSERT INTO `tbl_opc_ua_item_type` VALUES ('16', '4', '3', 'c', 'UInt16', '计数器，S7的取值范围： 0 - 999，十进制编码，作为Counter的默认');

-- ----------------------------
-- Table structure for tbl_opc_ua_namespace
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_namespace`;
CREATE TABLE `tbl_opc_ua_namespace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `protocol_id` bigint(20) NOT NULL,
  `namespace_index` int(11) NOT NULL,
  `namespace_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_namespace
-- ----------------------------
INSERT INTO `tbl_opc_ua_namespace` VALUES ('1', '1', '0', 'http://opcfoundation.org/UA/');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('2', '1', '1', 'urn:Siemens.Automation.SimaticNET.S7:(GUID)');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('3', '1', '2', 'S7TYPES:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('4', '1', '3', 'S7:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('5', '1', '4', 'S7COM:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('6', '1', '5', 'S7SOURCES:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('7', '1', '6', 'S7AREAS:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('8', '1', '7', 'SYM:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('9', '5', '0', 'http://opcfoundation.org/UA/');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('10', '5', '1', 'urn:Siemens.Automation.SimaticNET.S7OPT:(GUID)');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('11', '5', '2', 'S7OPTTYPES:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('12', '5', '3', 'S7OPT:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('13', '5', '4', 'S7OPTSOURCES:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('14', '5', '5', 'S7OPTAREAS:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('15', '5', '6', 'SYM:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('16', '4', '0', 'http://opcfoundation.org/UA/');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('17', '4', '1', 'urn:Siemens.Automation.SimaticNET.PNIO:(GUID)');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('18', '4', '2', 'PNIOTYPES:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('19', '4', '3', 'PNIO:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('20', '4', '4', 'PNIOCOM:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('21', '3', '0', 'http://opcfoundation.org/UA/');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('22', '3', '1', 'urn:Siemens.Automation.SimaticNET.DP:(GUID)');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('23', '3', '2', 'DPTYPES:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('24', '3', '3', 'DP:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('25', '3', '4', 'DPCOM:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('26', '2', '0', 'http://opcfoundation.org/UA/');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('27', '2', '1', 'urn:Siemens.Automation.SimaticNET.SR:(GUID)');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('28', '2', '2', 'SRTYPES:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('29', '2', '3', 'SR:');
INSERT INTO `tbl_opc_ua_namespace` VALUES ('30', '2', '4', 'SRCOM:');

-- ----------------------------
-- Table structure for tbl_opc_ua_protocol
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_protocol`;
CREATE TABLE `tbl_opc_ua_protocol` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `protocol_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `port` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_protocol
-- ----------------------------
INSERT INTO `tbl_opc_ua_protocol` VALUES ('1', 'OPC.SimaticNET.S7', '55101');
INSERT INTO `tbl_opc_ua_protocol` VALUES ('2', 'OPC.SimaticNET.SR', '55102');
INSERT INTO `tbl_opc_ua_protocol` VALUES ('3', 'OPC.SimaticNET.DP', '55103');
INSERT INTO `tbl_opc_ua_protocol` VALUES ('4', 'OPC.SimaticNET.PNIO', '55104');
INSERT INTO `tbl_opc_ua_protocol` VALUES ('5', 'OPC.SimaticNET.S7OPT', '55105');

-- ----------------------------
-- Table structure for tbl_opc_ua_security_policy_uri
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_security_policy_uri`;
CREATE TABLE `tbl_opc_ua_security_policy_uri` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_security_policy_uri
-- ----------------------------
INSERT INTO `tbl_opc_ua_security_policy_uri` VALUES ('1', 'http://opcfoundation.org/UA/SecurityPolicy#Basic128Rsa15');
INSERT INTO `tbl_opc_ua_security_policy_uri` VALUES ('2', '	http://opcfoundation.org/UA/SecurityPolicy#Basic256');
INSERT INTO `tbl_opc_ua_security_policy_uri` VALUES ('3', '	http://opcfoundation.org/UA/SecurityPolicy#Basic256Sha256');

-- ----------------------------
-- Table structure for tbl_opc_ua_server
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_server`;
CREATE TABLE `tbl_opc_ua_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endpoint_url` varchar(255) NOT NULL,
  `server_name` varchar(255) NOT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `opc_ua_protocol_id` bigint(20) NOT NULL,
  `security_policy_uri` varchar(255) NOT NULL,
  `security_mode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `supported_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `authentication_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `application_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `application_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_server
-- ----------------------------
INSERT INTO `tbl_opc_ua_server` VALUES ('3', 'opc.tcp://TJAW014:55101', 'OPC.SimaticNET.S7', 'opc.tcp://TJAW014:55101[OPC.SimaticNET.S7]#Basic128Rsa15', '1', 'http://opcfoundation.org/UA/SecurityPolicy#Basic128Rsa15', 'SignAndEncrypt', 'http://opcfoundation.org/UA-Profile/Transport/uatcp-uasc-uabinary', 'UserName', 'Siemens/SimaticNet/OpcUaServer/S7', 'urn:Siemens.Automation.SimaticNET.S7:(072C82A6-754E-4ECC-8E89-B5E719213DDC)', 'OPC.SimaticNET.S7', '1', '0');

-- ----------------------------
-- Table structure for tbl_opc_ua_storage_period
-- ----------------------------
DROP TABLE IF EXISTS `tbl_opc_ua_storage_period`;
CREATE TABLE `tbl_opc_ua_storage_period` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `period` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `corn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tbl_opc_ua_storage_period
-- ----------------------------
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('1', '1000', '1s', '*/1 * * * * ?');
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('2', '2000', '2s', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('3', '5000', '5s', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('4', '10000', '10s', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('5', '20000', '20s', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('6', '30000', '30s', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('7', '60000', '1min', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('8', '120000', '2min', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('9', '300000', '5min', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('10', '600000', '10min', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('11', '1800000', '30min', null);
INSERT INTO `tbl_opc_ua_storage_period` VALUES ('12', '3600000', '1h', null);
