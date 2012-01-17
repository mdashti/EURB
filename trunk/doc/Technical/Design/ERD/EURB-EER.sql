SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `eurb` ;
CREATE SCHEMA IF NOT EXISTS `eurb` DEFAULT CHARACTER SET utf8 COLLATE utf8_persian_ci ;
USE `eurb` ;

-- -----------------------------------------------------
-- Table `eurb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`users` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`users` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(50) NOT NULL ,
  `password` VARCHAR(50) NOT NULL ,
  `enabled` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`username`) ,
  INDEX `fk_usr_persistable_object` (`id` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  CONSTRAINT `fk_usr_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`persistable_object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`persistable_object` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`persistable_object` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `type` INT UNSIGNED NOT NULL ,
  `creator` VARCHAR(50) NOT NULL ,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `modifier` VARCHAR(50) NULL ,
  `modify_date` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_persistable_object_creator` (`creator` ASC) ,
  INDEX `fk_persistable_object_modifier` (`modifier` ASC) ,
  CONSTRAINT `fk_persistable_object_creator`
    FOREIGN KEY (`creator` )
    REFERENCES `eurb`.`users` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_persistable_object_modifier`
    FOREIGN KEY (`modifier` )
    REFERENCES `eurb`.`users` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`db_config`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`db_config` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`db_config` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `driver_class` VARCHAR(255) NOT NULL ,
  `driver_url` VARCHAR(255) NOT NULL ,
  `username` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NULL ,
  `test_query` VARCHAR(1024) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_db_config_persistable_object` (`id` ASC) ,
  CONSTRAINT `fk_db_config_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`table_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`table_mapping` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`table_mapping` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `db_config_id` BIGINT UNSIGNED NOT NULL ,
  `table_name` VARCHAR(255) NOT NULL ,
  `mapped_name` VARCHAR(255) NOT NULL ,
  `type` INT NOT NULL DEFAULT 0 ,
  `active_for_manager` TINYINT(1) NOT NULL DEFAULT 1 ,
  `active_for_user` TINYINT(1) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_table_mapping_persistable_object` (`id` ASC) ,
  INDEX `fk_table_mapping_db_config` (`db_config_id` ASC) ,
  CONSTRAINT `fk_table_mapping_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_table_mapping_db_config`
    FOREIGN KEY (`db_config_id` )
    REFERENCES `eurb`.`db_config` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`column_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`column_mapping` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`column_mapping` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `table_mapping_id` BIGINT UNSIGNED NOT NULL ,
  `column_name` VARCHAR(255) NOT NULL ,
  `mapped_name` VARCHAR(255) NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `format_pattern` VARCHAR(255) NULL ,
  `static_mapping` LONGTEXT NULL ,
  `referenced_table` VARCHAR(255) NULL ,
  `referenced_id_col` VARCHAR(255) NULL ,
  `referenced_value_col` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_column_mapping_persistable_object` (`id` ASC) ,
  INDEX `fk_column_mapping_table_mapping` (`table_mapping_id` ASC) ,
  CONSTRAINT `fk_column_mapping_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_column_mapping_table_mapping`
    FOREIGN KEY (`table_mapping_id` )
    REFERENCES `eurb`.`table_mapping` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`authorities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`authorities` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`authorities` (
  `username` VARCHAR(50) NOT NULL ,
  `authority` VARCHAR(50) NOT NULL ,
  UNIQUE INDEX `ix_auth_username` (`username` ASC, `authority` ASC) ,
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username` )
    REFERENCES `eurb`.`users` (`username` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`groups` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`groups` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `group_name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_groups_persistable_object` (`id` ASC) ,
  CONSTRAINT `fk_groups_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`group_authorities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`group_authorities` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`group_authorities` (
  `group_id` BIGINT UNSIGNED NOT NULL ,
  `authority` VARCHAR(50) NOT NULL ,
  INDEX `fk_group_authorities_group` (`group_id` ASC) ,
  CONSTRAINT `fk_group_authorities_group`
    FOREIGN KEY (`group_id` )
    REFERENCES `eurb`.`groups` (`id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`group_members`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`group_members` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`group_members` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(50) NOT NULL ,
  `group_id` BIGINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_group_members_group` (`group_id` ASC) ,
  CONSTRAINT `fk_group_members_group`
    FOREIGN KEY (`group_id` )
    REFERENCES `eurb`.`groups` (`id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`persistent_logins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`persistent_logins` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`persistent_logins` (
  `username` VARCHAR(64) NOT NULL ,
  `series` VARCHAR(64) NULL DEFAULT NULL ,
  `token` VARCHAR(64) NOT NULL ,
  `last_used` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`series`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_sid`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_sid` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_sid` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `principal` TINYINT(1) NOT NULL ,
  `sid` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_principal_sid` (`sid` ASC, `principal` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_class` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_class` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `class` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_uk_2` (`class` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_object_identity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_object_identity` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_object_identity` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `object_id_class` BIGINT UNSIGNED NOT NULL ,
  `object_id_identity` BIGINT UNSIGNED NOT NULL ,
  `parent_object` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `owner_sid` BIGINT UNSIGNED NOT NULL ,
  `entries_inheriting` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_class_identity` (`object_id_class` ASC, `object_id_identity` ASC) ,
  INDEX `fk_aoi_owner` (`parent_object` ASC) ,
  INDEX `fk_aoi_acl_sid` (`owner_sid` ASC) ,
  CONSTRAINT `fk_aoi_owner`
    FOREIGN KEY (`parent_object` )
    REFERENCES `eurb`.`acl_object_identity` (`id` ),
  CONSTRAINT `fk_aoi_acl_class`
    FOREIGN KEY (`object_id_class` )
    REFERENCES `eurb`.`acl_class` (`id` ),
  CONSTRAINT `fk_aoi_acl_sid`
    FOREIGN KEY (`owner_sid` )
    REFERENCES `eurb`.`acl_sid` (`id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_entry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_entry` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_entry` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `acl_object_identity` BIGINT UNSIGNED NOT NULL ,
  `ace_order` INT UNSIGNED NOT NULL ,
  `sid` BIGINT UNSIGNED NOT NULL ,
  `mask` INT NOT NULL ,
  `granting` TINYINT(1) NOT NULL ,
  `audit_success` TINYINT(1) NOT NULL ,
  `audit_failure` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_acl_object_identity_order` (`acl_object_identity` ASC, `ace_order` ASC) ,
  INDEX `fk_acl_entry_acl_sid` (`sid` ASC) ,
  CONSTRAINT `fk_acl_entry_aoi`
    FOREIGN KEY (`acl_object_identity` )
    REFERENCES `eurb`.`acl_object_identity` (`id` ),
  CONSTRAINT `fk_acl_entry_acl_sid`
    FOREIGN KEY (`sid` )
    REFERENCES `eurb`.`acl_sid` (`id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_category` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_category` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `description` VARCHAR(1024) NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_report_category_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_design`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_design` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_design` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `description` VARCHAR(45) NULL ,
  `category_id` BIGINT UNSIGNED NULL ,
  `query_text` LONGTEXT NULL ,
  `select_part` LONGTEXT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_report_design_report_category` (`category_id` ASC) ,
  CONSTRAINT `fk_report_design_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_design_report_category`
    FOREIGN KEY (`category_id` )
    REFERENCES `eurb`.`report_category` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_dataset`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_dataset` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_dataset` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `design_id` BIGINT UNSIGNED NOT NULL ,
  `table_mapping_id` BIGINT UNSIGNED NULL ,
  `base_report_id` BIGINT UNSIGNED NULL ,
  `order` INT UNSIGNED NOT NULL ,
  `operator` INT UNSIGNED NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`, `design_id`) ,
  INDEX `fk_report_dataset_report_design_base_rpt` (`base_report_id` ASC) ,
  INDEX `fk_report_dataset_report_design_id` (`design_id` ASC) ,
  INDEX `fk_report_dataset_table_mapping` (`table_mapping_id` ASC) ,
  CONSTRAINT `fk_report_dataset_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_dataset_report_design_base_rpt`
    FOREIGN KEY (`base_report_id` )
    REFERENCES `eurb`.`report_design` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_dataset_report_design_id`
    FOREIGN KEY (`design_id` )
    REFERENCES `eurb`.`report_design` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_dataset_table_mapping`
    FOREIGN KEY (`table_mapping_id` )
    REFERENCES `eurb`.`table_mapping` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_column`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_column` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_column` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `dataset_id` BIGINT UNSIGNED NOT NULL ,
  `design_id` BIGINT UNSIGNED NOT NULL ,
  `type` INT UNSIGNED NOT NULL ,
  `column_mapping_id` BIGINT UNSIGNED NULL ,
  `report_column_id` BIGINT UNSIGNED NULL ,
  `order` INT NOT NULL DEFAULT 0 ,
  `sort_order` INT NOT NULL DEFAULT 0 ,
  `sort_type` TINYINT(1) NOT NULL DEFAULT 0 ,
  `group_level` INT NULL ,
  `column_width` INT NOT NULL DEFAULT 20 ,
  `column_header` VARCHAR(255) NULL ,
  `is_custom` TINYINT(1) NOT NULL DEFAULT 0 ,
  `formula` LONGTEXT NULL ,
  PRIMARY KEY (`id`, `dataset_id`, `design_id`) ,
  INDEX `fk_report_column_column_mapping` (`column_mapping_id` ASC) ,
  INDEX `fk_report_column_report_column` (`report_column_id` ASC) ,
  INDEX `fk_report_column_report_dataset` (`dataset_id` ASC, `design_id` ASC) ,
  CONSTRAINT `fk_report_column_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_column_column_mapping`
    FOREIGN KEY (`column_mapping_id` )
    REFERENCES `eurb`.`column_mapping` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_column_report_column`
    FOREIGN KEY (`report_column_id` )
    REFERENCES `eurb`.`report_column` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_column_report_dataset`
    FOREIGN KEY (`dataset_id` , `design_id` )
    REFERENCES `eurb`.`report_dataset` (`id` , `design_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`group_aggregation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`group_aggregation` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`group_aggregation` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `parent_column_id` BIGINT UNSIGNED NOT NULL ,
  `parent_column_dataset_id` BIGINT UNSIGNED NOT NULL ,
  `parent_column_design_id` BIGINT UNSIGNED NOT NULL ,
  `aggregated_report_column_id` BIGINT UNSIGNED NOT NULL ,
  `aggregated_report_column_dataset_id` BIGINT UNSIGNED NOT NULL ,
  `aggregated_report_column_design_id` BIGINT UNSIGNED NOT NULL ,
  `aggregation_function` VARCHAR(45) NOT NULL ,
  `place` INT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_group_aggregation_report_column_parent` (`parent_column_id` ASC, `parent_column_dataset_id` ASC, `parent_column_design_id` ASC) ,
  INDEX `fk_group_aggregation_report_column_aggregated` (`aggregated_report_column_id` ASC, `aggregated_report_column_dataset_id` ASC, `aggregated_report_column_design_id` ASC) ,
  CONSTRAINT `fk_group_aggregation_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_aggregation_report_column_parent`
    FOREIGN KEY (`parent_column_id` , `parent_column_dataset_id` , `parent_column_design_id` )
    REFERENCES `eurb`.`report_column` (`id` , `dataset_id` , `design_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_aggregation_report_column_aggregated`
    FOREIGN KEY (`aggregated_report_column_id` , `aggregated_report_column_dataset_id` , `aggregated_report_column_design_id` )
    REFERENCES `eurb`.`report_column` (`id` , `dataset_id` , `design_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_filter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_filter` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_filter` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `report_column_id` BIGINT UNSIGNED NOT NULL ,
  `report_column_dataset_id` BIGINT UNSIGNED NOT NULL ,
  `report_column_design_id` BIGINT UNSIGNED NOT NULL ,
  `prefix` VARCHAR(45) NULL ,
  `operator` VARCHAR(45) NULL ,
  `suffix` VARCHAR(45) NULL ,
  `operand1` VARCHAR(255) NULL ,
  `operand2` VARCHAR(255) NULL ,
  `type` INT NULL ,
  `operand1_column_id` BIGINT UNSIGNED NULL ,
  `operand1_column_dataset_id` BIGINT UNSIGNED NULL ,
  `operand1_column_design_id` BIGINT UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_report_filter_report_column_operand1` (`operand1_column_id` ASC, `operand1_column_dataset_id` ASC, `operand1_column_design_id` ASC) ,
  INDEX `fk_report_filter_report_column_id` (`report_column_id` ASC, `report_column_dataset_id` ASC, `report_column_design_id` ASC) ,
  CONSTRAINT `fk_report_filter_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_filter_report_column_operand1`
    FOREIGN KEY (`operand1_column_id` , `operand1_column_dataset_id` , `operand1_column_design_id` )
    REFERENCES `eurb`.`report_column` (`id` , `dataset_id` , `design_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_filter_report_column_id`
    FOREIGN KEY (`report_column_id` , `report_column_dataset_id` , `report_column_design_id` )
    REFERENCES `eurb`.`report_column` (`id` , `dataset_id` , `design_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;
-- -----------------------------------------------------
-- Data for table `eurb`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`users` (`id`, `username`, `password`, `enabled`) VALUES (100, 'dashti', 'mohamad', 1);
INSERT INTO `eurb`.`users` (`id`, `username`, `password`, `enabled`) VALUES (101, 'sadeghi', 'alireza', 1);
INSERT INTO `eurb`.`users` (`id`, `username`, `password`, `enabled`) VALUES (102, 'keshavarz', 'behrouz', 1);
INSERT INTO `eurb`.`users` (`id`, `username`, `password`, `enabled`) VALUES (103, 'farahani', 'farahani', 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `eurb`.`persistable_object`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`persistable_object` (`id`, `type`, `creator`, `create_date`, `modifier`, `modify_date`) VALUES (100, 100, 'dashti', NULL, NULL, NULL);
INSERT INTO `eurb`.`persistable_object` (`id`, `type`, `creator`, `create_date`, `modifier`, `modify_date`) VALUES (101, 100, 'dashti', NULL, NULL, NULL);
INSERT INTO `eurb`.`persistable_object` (`id`, `type`, `creator`, `create_date`, `modifier`, `modify_date`) VALUES (102, 100, 'dashti', NULL, NULL, NULL);
INSERT INTO `eurb`.`persistable_object` (`id`, `type`, `creator`, `create_date`, `modifier`, `modify_date`) VALUES (103, 100, 'dashti', NULL, NULL, NULL);

COMMIT;
SET FOREIGN_KEY_CHECKS = 1;