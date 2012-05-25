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
  `obj_type` INT UNSIGNED NOT NULL ,
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
  `record_status` VARCHAR(1) NULL ,
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
  `catalog` VARCHAR(255) NULL ,
  `schema` VARCHAR(255) NULL ,
  `table_name` VARCHAR(255) NOT NULL ,
  `mapped_name` VARCHAR(255) NULL ,
  `mapped_type` INT NOT NULL DEFAULT 0 ,
  `active_for_manager` TINYINT(1) NOT NULL DEFAULT 1 ,
  `active_for_user` TINYINT(1) NOT NULL DEFAULT 1 ,
  `table_mappingcol` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`, `db_config_id`) ,
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
  `db_config_id` BIGINT UNSIGNED NOT NULL ,
  `column_name` VARCHAR(255) NOT NULL ,
  `mapped_name` VARCHAR(255) NOT NULL ,
  `col_type_name` VARCHAR(100) NOT NULL ,
  `col_data_type` INT NOT NULL ,
  `col_order` INT NOT NULL ,
  `format_pattern` VARCHAR(255) NULL ,
  `static_mapping` LONGTEXT NULL ,
  `referenced_table`BIGINT(19) UNSIGNED NULL ,
  `referenced_id_col` BIGINT(19) UNSIGNED NULL ,
  `referenced_value_col` BIGINT(19) UNSIGNED NULL ,
  `active_for_manager` TINYINT(1) NOT NULL DEFAULT 1 ,
  `active_for_user` TINYINT(1) NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`id`, `table_mapping_id`, `db_config_id`) ,
  INDEX `fk_column_mapping_persistable_object` (`id` ASC) ,
  INDEX `fk_column_mapping_table_mapping` (`table_mapping_id` ASC) ,
  INDEX `fk_column_mapping_db_config` (`db_config_id` ASC) ,
  INDEX `fk_column_mapping_table_mapping_referenced_table` (`referenced_table` ASC) ,
  INDEX `fk_column_mapping_column_mapping_referenced_id_col` (`referenced_id_col` ASC) ,
  INDEX `fk_column_mapping_column_mapping_referenced_value_col` (`referenced_value_col` ASC) ,
  CONSTRAINT `fk_column_mapping_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_column_mapping_table_mapping`
    FOREIGN KEY (`table_mapping_id` )
    REFERENCES `eurb`.`table_mapping` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_column_mapping_db_config`
    FOREIGN KEY (`db_config_id` )
    REFERENCES `eurb`.`db_config` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION ,
  CONSTRAINT `fk_column_mapping_table_mapping_referenced_table`
	FOREIGN KEY (`referenced_table` )
	REFERENCES `eurb`.`table_mapping` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `fk_column_mapping_column_mapping_referenced_id_col`
	FOREIGN KEY (`referenced_id_col` )
	REFERENCES `eurb`.`column_mapping` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `fk_column_mapping_column_mapping_referenced_value_col`
	FOREIGN KEY (`referenced_value_col` )
	REFERENCES `eurb`.`column_mapping` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION )
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
  `version_id` BIGINT UNSIGNED NOT NULL ,
  `name` VARCHAR(100) NULL ,
  `description` LONGTEXT NULL ,
  `category_id` BIGINT UNSIGNED NULL ,
  `query_text` LONGTEXT NULL ,
  `select_part` LONGTEXT NULL ,
  `result_data` LONGTEXT NULL ,
  `format_file` LONGTEXT NULL ,
  `is_current` TINYINT(1) NOT NULL DEFAULT 1 ,
  `record_status` VARCHAR(1) NOT NULL DEFAULT 'A' ,
  `db_config_id` BIGINT(20) UNSIGNED NULL,
  PRIMARY KEY (`id`, `version_id`) ,
  INDEX `fk_report_design_report_category` (`category_id` ASC) ,
  INDEX `fk_report_design_db_config` (`db_config_id` ASC),
  CONSTRAINT `fk_report_design_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_design_report_category`
    FOREIGN KEY (`category_id` )
    REFERENCES `eurb`.`report_category` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_design_db_config`
	FOREIGN KEY (`db_config_id` )
	REFERENCES `eurb`.`db_config` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION	)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_dataset`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_dataset` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_dataset` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `design_id` BIGINT UNSIGNED NOT NULL ,
  `design_version_id` BIGINT UNSIGNED NOT NULL ,
  `table_mapping_id` BIGINT UNSIGNED NULL ,
  `base_report_id` BIGINT UNSIGNED NULL ,
  `base_report_version_id` BIGINT UNSIGNED NULL ,
  `ds_order` INT UNSIGNED NULL DEFAULT 1  ,
  `operator` INT UNSIGNED NULL DEFAULT 0 ,
  PRIMARY KEY (`id`, `design_id`, `design_version_id`) ,
  INDEX `fk_report_dataset_report_design_base_rpt` (`base_report_id` ASC, `base_report_version_id` ASC) ,
  INDEX `fk_report_dataset_report_design_id` (`design_id` ASC, `design_version_id` ASC) ,
  INDEX `fk_report_dataset_table_mapping` (`table_mapping_id` ASC) ,
  CONSTRAINT `fk_report_dataset_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_dataset_report_design_base_rpt`
    FOREIGN KEY (`base_report_id` , `base_report_version_id` )
    REFERENCES `eurb`.`report_design` (`id` , `version_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_dataset_report_design_id`
    FOREIGN KEY (`design_id` , `design_version_id` )
    REFERENCES `eurb`.`report_design` (`id` , `version_id` )
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
  `dataset_id` BIGINT UNSIGNED NULL ,
  `design_id` BIGINT UNSIGNED NOT NULL ,
  `design_version_id` BIGINT UNSIGNED NOT NULL ,
  `col_type` INT UNSIGNED NOT NULL ,
  `column_mapping_id` BIGINT UNSIGNED NULL ,
  `report_column_id` BIGINT UNSIGNED NULL ,
  `col_order` INT NOT NULL DEFAULT 0 ,
  `sort_order` INT NULL DEFAULT 0 ,
  `sort_type` TINYINT(1) NULL DEFAULT 0 ,
  `group_level` INT NULL ,
  `column_width` INT NOT NULL DEFAULT 20 ,
  `column_align` VARCHAR(10) NOT NULL DEFAULT 'right' ,
  `column_dir` VARCHAR(3) NOT NULL DEFAULT 'rtl' ,
  `column_header` VARCHAR(255) NULL ,
  `is_custom` TINYINT(1) NOT NULL DEFAULT 0 ,
  `formula` LONGTEXT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_report_column_column_mapping` (`column_mapping_id` ASC) ,
  INDEX `fk_report_column_report_column` (`report_column_id` ASC) ,
  INDEX `fk_report_column_report_design` (`design_id` ASC, `design_version_id` ASC) ,
  INDEX `fk_report_column_report_dataset` (`dataset_id` ASC) ,
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
    FOREIGN KEY (`dataset_id` )
    REFERENCES `eurb`.`report_dataset` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_column_report_design`
    FOREIGN KEY (`design_id` , `design_version_id` )
    REFERENCES `eurb`.`report_design` (`id` ,`version_id` )
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
  `aggregated_report_column_id` BIGINT UNSIGNED NULL ,
  `aggregated_column_dataset_id` BIGINT UNSIGNED NULL ,
  `aggregated_column_mapping_id` BIGINT UNSIGNED NULL ,
  `aggregation_function` VARCHAR(45) NOT NULL ,
  `place` INT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_group_aggregation_report_column_parent` (`parent_column_id` ASC) ,
  INDEX `fk_group_aggregation_aggregated_column_mapping` (`aggregated_column_mapping_id` ASC) ,
  INDEX `fk_group_aggregation_aggregated_column_dataset` (`aggregated_column_dataset_id` ASC) ,
  INDEX `fk_group_aggregation_aggregated_report_column` (`aggregated_report_column_id` ASC) ,
   CONSTRAINT `fk_group_aggregation_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_aggregation_report_column_parent`
    FOREIGN KEY (`parent_column_id`)
    REFERENCES `eurb`.`report_column` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_aggregation_aggregated_column_mapping`
	FOREIGN KEY (`aggregated_column_mapping_id` )
	REFERENCES `eurb`.`column_mapping` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `fk_group_aggregation_aggregated_column_dataset`
	FOREIGN KEY (`aggregated_column_dataset_id` )
	REFERENCES `eurb`.`report_dataset` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `fk_group_aggregation_aggregated_report_column`
	FOREIGN KEY (`aggregated_report_column_id` )
	REFERENCES `eurb`.`report_column` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_filter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_filter` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_filter` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `column_mapping_id` BIGINT UNSIGNED NOT NULL ,
  `report_dataset_id` BIGINT UNSIGNED NOT NULL ,
  `report_design_id` BIGINT UNSIGNED NOT NULL ,
  `report_design_version_id` BIGINT UNSIGNED NOT NULL ,
  `prefix` VARCHAR(45) NULL ,
  `operator` VARCHAR(45) NULL ,
  `suffix` VARCHAR(45) NULL ,
  `operand1` VARCHAR(255) NULL ,
  `operand2` VARCHAR(255) NULL ,
  `filter_type` INT NULL ,
  `operand1_column_mapping_id` BIGINT UNSIGNED NULL ,
  `operand1_dataset_id` BIGINT UNSIGNED NULL ,
  `operand1_report_column_id` BIGINT UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_report_filter_report_design` (`report_design_id` ASC, `report_design_version_id` ASC),
  INDEX `fk_report_filter_report_dataset` (`report_dataset_id` ASC), 
  INDEX `fk_report_filter_operand1_dataset` (`operand1_dataset_id` ASC) ,
  INDEX `fk_report_fitler_operand1_column_mapping` (`operand1_column_mapping_id` ASC) ,
  INDEX `fk_report_filter_operand1_report_column` (`operand1_report_column_id` ASC) ,
   CONSTRAINT `fk_report_filter_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
 CONSTRAINT `fk_report_filter_column_mapping_id`
	FOREIGN KEY (`column_mapping_id` )
	REFERENCES `eurb`.`column_mapping` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `fk_report_filter_report_design`
	FOREIGN KEY (`report_design_id` , `report_design_version_id` )
	REFERENCES `eurb`.`report_design` (`id` , `version_id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `fk_report_filter_report_dataset`
	FOREIGN KEY (`report_dataset_id` )
	REFERENCES `eurb`.`report_dataset` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_filter_operand1_dataset`
	FOREIGN KEY (`operand1_dataset_id` )
	REFERENCES `eurb`.`report_dataset` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `fk_report_fitler_operand1_column_mapping`
	FOREIGN KEY (`operand1_column_mapping_id` )
	REFERENCES `eurb`.`column_mapping` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
 CONSTRAINT `fk_report_filter_operand1_report_column`
	FOREIGN KEY (`operand1_report_column_id` )
	REFERENCES `eurb`.`report_column` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_execution_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_execution_history` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_execution_history` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `version_id` BIGINT UNSIGNED NOT NULL ,
  `execution_result` LONGTEXT NULL ,
  `is_current` TINYINT(1) NOT NULL DEFAULT 1 ,
  `record_status` VARCHAR(1) NOT NULL DEFAULT 'A' ,
  `report_design_id` BIGINT UNSIGNED NOT NULL ,
  `report_design_version_id` BIGINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`, `version_id`) ,
  INDEX `fk_report_execution_history_report_design` (`report_design_id` ASC, `report_design_version_id` ASC) ,
  INDEX `fk_report_execution_history_persistable_object` (`id` ASC) ,
  CONSTRAINT `fk_report_execution_history_report_design`
    FOREIGN KEY (`report_design_id` , `report_design_version_id` )
    REFERENCES `eurb`.`report_design` (`id` , `version_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_execution_history_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_format`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_format` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_format` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `version_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `format_file` LONGTEXT NULL ,
  `is_current` TINYINT(1) NOT NULL DEFAULT 1 ,
  `record_status` VARCHAR(1) NOT NULL DEFAULT 'A' ,
  `report_design_id` BIGINT UNSIGNED NULL ,
  `report_design_version_id` BIGINT UNSIGNED NULL ,
  PRIMARY KEY (`version_id`, `id`) ,
  INDEX `fk_report_format_report_design` (`report_design_id` ASC, `report_design_version_id` ASC) ,
  INDEX `fk_report_format_persistable_object` (`id` ASC) ,
  CONSTRAINT `fk_report_format_report_design1`
    FOREIGN KEY (`report_design_id` , `report_design_version_id` )
    REFERENCES `eurb`.`report_design` (`id` , `version_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_format_persistable_object1`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `eurb`.`object_config`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`object_config` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`object_config` (
  `object_id` BIGINT(20) UNSIGNED NOT NULL ,
  `key` VARCHAR(100) NOT NULL ,
  `value` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`object_id`, `key`) ,
  INDEX `pk` (`object_id` ASC, `key` ASC),
  INDEX `object_config_persistable_object` (`object_id` ASC) ,
  CONSTRAINT `object_config_persistable_object`
	FOREIGN KEY (`object_id` )
	REFERENCES `eurb`.`persistable_object` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`report_chart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_chart` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_chart` (
  `id` BIGINT(20) UNSIGNED NOT NULL ,
  `design_id` BIGINT(20) UNSIGNED NOT NULL ,
  `design_version_id` BIGINT(20) UNSIGNED NOT NULL ,
  `chart_type` VARCHAR(45) NOT NULL ,
  `name` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) , 
  INDEX `report_chart_persistable_object` (`id` ASC) ,
  CONSTRAINT `report_chart_persistable_object`
	FOREIGN KEY (`id` )
	REFERENCES `eurb`.`persistable_object` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `eurb`.`report_chart_axis`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`report_chart_axis` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_chart_axis` (
  `id` BIGINT(20) UNSIGNED NOT NULL ,
  `chart_id` BIGINT(20) UNSIGNED NOT NULL ,
  `axis_type` VARCHAR(2) NOT NULL ,
  `title` VARCHAR(45) NULL ,
  `column_mapping_id` BIGINT(20) UNSIGNED NOT NULL ,
  `aggregation` VARCHAR(45) NULL ,
  `report_dataset_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) , 
  INDEX `report_chart_axis_persistable_object` (`id` ASC) ,
  INDEX `report_chart_axis_chart` (`chart_id` ASC) ,
  INDEX `report_chart_axis_column_mapping` (`column_mapping_id` ASC) ,
  INDEX `report_chart_axis_report_dataset_fk` (`report_dataset_id` ASC) ,
  CONSTRAINT `report_chart_axis_persistable_object`
	FOREIGN KEY (`id` )
	REFERENCES `eurb`.`persistable_object` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `report_chart_axis_chart`
	FOREIGN KEY (`chart_id` )
	REFERENCES `eurb`.`report_chart` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION, 
  CONSTRAINT `report_chart_axis_column_mapping`
	FOREIGN KEY (`column_mapping_id` )
	REFERENCES `eurb`.`column_mapping` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION,
  CONSTRAINT `report_chart_axis_report_dataset_fk`
	FOREIGN KEY (`report_dataset_id` )
	REFERENCES `eurb`.`report_dataset` (`id` )
	ON DELETE NO ACTION
	ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `eurb`.`users`
-- -----------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`users` (`id`, `username`, `password`, `enabled`) VALUES (100, 'admin', 'a0a287a0fcdad714fa244b1f2071c4337cffb888', 1); -- admin/mohamad

COMMIT;

-- -----------------------------------------------------
-- Data for table `eurb`.`persistable_object`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`persistable_object` (`id`, `obj_type`, `creator`, `create_date`, `modifier`, `modify_date`) VALUES (100, 100, 'admin', NULL, NULL, NULL);
INSERT INTO `eurb`.`persistable_object` (`id`, `obj_type`, `creator`, `create_date`, `modifier`, `modify_date`) VALUES (200, 200, 'admin', NULL, NULL, NULL);

COMMIT;
SET FOREIGN_KEY_CHECKS = 1;
-- -----------------------------------------------------
-- Data for table `eurb`.`authorities`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`authorities` (`username`, `authority`) VALUES ('admin', 'ROLE_MAPPING_MANAGEMENT_DB_LIST');

COMMIT;

-- -----------------------------------------------------
-- Data for table `eurb`.`groups`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`groups` (`id`, `group_name`) VALUES (200, 'مدیران');

COMMIT;

-- -----------------------------------------------------
-- Data for table `eurb`.`group_authorities`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`group_authorities` (`group_id`, `authority`) VALUES (200, 'ROLE_MAPPING_MANAGEMENT_DB_LIST');

COMMIT;

-- -----------------------------------------------------
-- Data for table `eurb`.`group_members`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`group_members` (`id`, `username`, `group_id`) VALUES (1, 'admin', 200);

COMMIT;
