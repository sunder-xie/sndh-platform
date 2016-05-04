--database
create database todolist CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS message;
CREATE TABLE message (
  label varchar(255) DEFAULT NULL,
  id int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
);

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('张三', '1');
INSERT INTO `message` VALUES ('李四', '2');
