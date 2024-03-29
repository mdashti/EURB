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
  `email` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`username`) ,
  INDEX `fk_usr_persistable_object` (`id` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
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
  `referenced_table` BIGINT UNSIGNED NULL ,
  `referenced_id_col` BIGINT UNSIGNED NULL ,
  `referenced_value_col` BIGINT UNSIGNED NULL ,
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
    ON UPDATE NO ACTION,
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
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `group_name_UNIQUE` (`group_name` ASC) ,
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
  PRIMARY KEY (`id`, `version_id`) ,
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
  `design_version_id` BIGINT UNSIGNED NOT NULL ,
  `table_mapping_id` BIGINT UNSIGNED NULL ,
  `base_report_id` BIGINT UNSIGNED NULL ,
  `base_report_version_id` BIGINT UNSIGNED NULL ,
  `ds_order` INT UNSIGNED NOT NULL ,
  `operator` INT UNSIGNED NOT NULL DEFAULT 0 ,
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
  `dataset_id` BIGINT UNSIGNED NOT NULL ,
  `design_id` BIGINT UNSIGNED NOT NULL ,
  `design_version_id` BIGINT UNSIGNED NOT NULL ,
  `col_type` INT UNSIGNED NOT NULL ,
  `column_mapping_id` BIGINT UNSIGNED NULL ,
  `report_column_id` BIGINT UNSIGNED NULL ,
  `col_order` INT NOT NULL DEFAULT 0 ,
  `sort_order` INT NOT NULL DEFAULT 0 ,
  `sort_type` TINYINT(1) NOT NULL DEFAULT 0 ,
  `group_level` INT NULL ,
  `column_width` INT NOT NULL DEFAULT 20 ,
  `column_align` VARCHAR(10) NOT NULL DEFAULT 'right' ,
  `column_dir` VARCHAR(3) NOT NULL DEFAULT 'rtl' ,
  `column_header` VARCHAR(255) NULL ,
  `is_custom` TINYINT(1) NOT NULL DEFAULT 0 ,
  `formula` LONGTEXT NULL ,
  PRIMARY KEY (`id`, `dataset_id`, `design_id`, `design_version_id`) ,
  INDEX `fk_report_column_column_mapping` (`column_mapping_id` ASC) ,
  INDEX `fk_report_column_report_column` (`report_column_id` ASC) ,
  INDEX `fk_report_column_report_dataset` (`dataset_id` ASC, `design_id` ASC, `design_version_id` ASC) ,
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
    FOREIGN KEY (`dataset_id` , `design_id` , `design_version_id` )
    REFERENCES `eurb`.`report_dataset` (`id` , `design_id` , `design_version_id` )
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
  `parent_column_design_version_id` BIGINT UNSIGNED NOT NULL ,
  `aggregated_report_column_id` BIGINT UNSIGNED NOT NULL ,
  `aggregated_report_column_dataset_id` BIGINT UNSIGNED NOT NULL ,
  `aggregated_report_column_design_id` BIGINT UNSIGNED NOT NULL ,
  `aggregated_report_column_design_version_id` BIGINT UNSIGNED NOT NULL ,
  `aggregation_function` VARCHAR(45) NOT NULL ,
  `place` INT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_group_aggregation_report_column_parent` (`parent_column_id` ASC, `parent_column_dataset_id` ASC, `parent_column_design_id` ASC, `parent_column_design_version_id` ASC) ,
  INDEX `fk_group_aggregation_report_column_aggregated` (`aggregated_report_column_id` ASC, `aggregated_report_column_dataset_id` ASC, `aggregated_report_column_design_id` ASC, `aggregated_report_column_design_version_id` ASC) ,
  CONSTRAINT `fk_group_aggregation_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_aggregation_report_column_parent`
    FOREIGN KEY (`parent_column_id` , `parent_column_dataset_id` , `parent_column_design_id` , `parent_column_design_version_id` )
    REFERENCES `eurb`.`report_column` (`id` , `dataset_id` , `design_id` , `design_version_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_aggregation_report_column_aggregated`
    FOREIGN KEY (`aggregated_report_column_id` , `aggregated_report_column_dataset_id` , `aggregated_report_column_design_id` , `aggregated_report_column_design_version_id` )
    REFERENCES `eurb`.`report_column` (`id` , `dataset_id` , `design_id` , `design_version_id` )
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
  `report_column_design_version_id` BIGINT UNSIGNED NOT NULL ,
  `prefix` VARCHAR(45) NULL ,
  `operator` VARCHAR(45) NULL ,
  `suffix` VARCHAR(45) NULL ,
  `operand1` VARCHAR(255) NULL ,
  `operand2` VARCHAR(255) NULL ,
  `filter_type` INT NULL ,
  `operand1_column_id` BIGINT UNSIGNED NULL ,
  `operand1_column_dataset_id` BIGINT UNSIGNED NULL ,
  `operand1_column_design_id` BIGINT UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_report_filter_report_column_operand1` (`operand1_column_id` ASC, `operand1_column_dataset_id` ASC, `operand1_column_design_id` ASC) ,
  INDEX `fk_report_filter_report_column_id` (`report_column_id` ASC, `report_column_dataset_id` ASC, `report_column_design_id` ASC, `report_column_design_version_id` ASC) ,
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
    FOREIGN KEY (`report_column_id` , `report_column_dataset_id` , `report_column_design_id` , `report_column_design_version_id` )
    REFERENCES `eurb`.`report_column` (`id` , `dataset_id` , `design_id` , `design_version_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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


CREATE  TABLE IF NOT EXISTS `eurb`.`report_chart` (
  `id` BIGINT(20) UNSIGNED NOT NULL ,
  `design_id` BIGINT(20) UNSIGNED NOT NULL ,
  `design_version_id` BIGINT(20) UNSIGNED NOT NULL ,
  `chart_type` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_persian_ci' NOT NULL ,
  `name` VARCHAR(100) CHARACTER SET 'utf8' COLLATE 'utf8_persian_ci' NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `report_chart_persistable_object` (`id` ASC) ,
  CONSTRAINT `report_chart_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

CREATE  TABLE IF NOT EXISTS `eurb`.`report_chart_axis` (
  `id` BIGINT(20) UNSIGNED NOT NULL ,
  `chart_id` BIGINT(20) UNSIGNED NOT NULL ,
  `axis_type` VARCHAR(2) CHARACTER SET 'utf8' COLLATE 'utf8_persian_ci' NOT NULL ,
  `title` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_persian_ci' NULL DEFAULT NULL ,
  `column_mapping_id` BIGINT(20) UNSIGNED NOT NULL ,
  `aggregation` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_persian_ci' NULL DEFAULT NULL ,
  `report_dataset_id` BIGINT(20) UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `report_chart_axis_column_mapping` (`column_mapping_id` ASC) ,
  INDEX `report_chart_axis_persistable_object` (`id` ASC) ,
  INDEX `report_chart_axis_chart` (`chart_id` ASC) ,
  INDEX `report_chart_axis_report_dataset_fk` (`report_dataset_id` ASC) ,
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
  CONSTRAINT `report_chart_axis_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `report_chart_axis_report_dataset_fk`
    FOREIGN KEY (`report_dataset_id` )
    REFERENCES `eurb`.`report_dataset` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

-- -----------------------------------------------------
-- Table `eurb`.`user_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`user_message` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`user_message` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `username` VARCHAR(50) NOT NULL ,
  `message` LONGTEXT NOT NULL ,
  `type` INT NOT NULL DEFAULT 1 ,
  `show` TINYINT(1) NOT NULL DEFAULT 1 ,
  `available_from` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  INDEX `fk_user_message_persistable_object` (`id` ASC) ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_user_message_users` (`username` ASC) ,
  CONSTRAINT `fk_user_message_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_message_users`
    FOREIGN KEY (`username` )
    REFERENCES `eurb`.`users` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `eurb`.`dashboard` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `is_default` TINYINT(1) NOT NULL DEFAULT 0 ,
  `is_active` TINYINT(1) NOT NULL DEFAULT 0 ,
  `title` VARCHAR(255) NOT NULL ,
  `parent_dashboard` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `username` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_dashboard_persistable_object` (`id` ASC) ,
  INDEX `fk_dashboard_dashboard` (`parent_dashboard` ASC) ,
  INDEX `fk_dashboard_users` (`username` ASC) ,
  CONSTRAINT `fk_dashboard_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_dashboard`
    FOREIGN KEY (`parent_dashboard` )
    REFERENCES `eurb`.`dashboard` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_users`
    FOREIGN KEY (`username` )
    REFERENCES `eurb`.`users` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

CREATE  TABLE IF NOT EXISTS `eurb`.`dashboard_col` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `dashboard_id` BIGINT UNSIGNED NOT NULL ,
  `col_order` INT(2) NOT NULL ,
  `col_width` DOUBLE NULL DEFAULT NULL ,
  INDEX `fk_dashboard_col_dashboard` (`dashboard_id` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_dashboard_col_dashboard`
    FOREIGN KEY (`dashboard_id` )
    REFERENCES `eurb`.`dashboard` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_col_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

CREATE  TABLE IF NOT EXISTS `eurb`.`dashboard_item` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `dashboard_id` BIGINT UNSIGNED NOT NULL ,
  `dashboard_col_id` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `item_order` INT(2) NOT NULL ,
  `item_height` DOUBLE NULL DEFAULT NULL ,
  `item_collapsed` TINYINT(1) NOT NULL DEFAULT 0 ,
  `item_closed` TINYINT(1) NOT NULL DEFAULT 0 ,
  `report_design_id` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `report_chart_id` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `is_show_table` TINYINT(1) NOT NULL DEFAULT 0 ,
  `item_title` VARCHAR(255) NULL ,
  `item_content` LONGTEXT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_dashboard_item_dashboard_col` (`dashboard_col_id` ASC) ,
  INDEX `fk_dashboard_item_dashboard` (`dashboard_id` ASC) ,
  INDEX `fk_dashboard_item_report_design1` (`report_design_id` ASC) ,
  INDEX `fk_dashboard_item_report_chart1` (`report_chart_id` ASC) ,
  CONSTRAINT `fk_dashboard_item_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_dashboard_col`
    FOREIGN KEY (`dashboard_col_id` )
    REFERENCES `eurb`.`dashboard_col` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_dashboard`
    FOREIGN KEY (`dashboard_id` )
    REFERENCES `eurb`.`dashboard` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_report_design1`
    FOREIGN KEY (`report_design_id` )
    REFERENCES `eurb`.`report_design` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_report_chart1`
    FOREIGN KEY (`report_chart_id` )
    REFERENCES `eurb`.`report_chart` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

-- START QUARTZ TABLES --
--
-- In your Quartz properties file, you'll need to set
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
--

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

CREATE TABLE QRTZ_JOB_DETAILS(
SCHED_NAME VARCHAR(120) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
JOB_CLASS_NAME VARCHAR(250) NOT NULL,
IS_DURABLE VARCHAR(1) NOT NULL,
IS_NONCONCURRENT VARCHAR(1) NOT NULL,
IS_UPDATE_DATA VARCHAR(1) NOT NULL,
REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
NEXT_FIRE_TIME BIGINT(13) NULL,
PREV_FIRE_TIME BIGINT(13) NULL,
PRIORITY INTEGER NULL,
TRIGGER_STATE VARCHAR(16) NOT NULL,
TRIGGER_TYPE VARCHAR(8) NOT NULL,
START_TIME BIGINT(13) NOT NULL,
END_TIME BIGINT(13) NULL,
CALENDAR_NAME VARCHAR(200) NULL,
MISFIRE_INSTR SMALLINT(2) NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
INDEX (SCHED_NAME,JOB_NAME, JOB_GROUP),
FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
REPEAT_COUNT BIGINT(7) NOT NULL,
REPEAT_INTERVAL BIGINT(12) NOT NULL,
TIMES_TRIGGERED BIGINT(10) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_CRON_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
CRON_EXPRESSION VARCHAR(120) NOT NULL,
TIME_ZONE_ID VARCHAR(80),
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
  (          
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_BLOB_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
BLOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_CALENDARS (
SCHED_NAME VARCHAR(120) NOT NULL,
CALENDAR_NAME VARCHAR(200) NOT NULL,
CALENDAR BLOB NOT NULL,
PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
ENGINE=InnoDB;

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_FIRED_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
ENTRY_ID VARCHAR(95) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
FIRED_TIME BIGINT(13) NOT NULL,
PRIORITY INTEGER NOT NULL,
STATE VARCHAR(16) NOT NULL,
JOB_NAME VARCHAR(200) NULL,
JOB_GROUP VARCHAR(200) NULL,
IS_NONCONCURRENT VARCHAR(1) NULL,
REQUESTS_RECOVERY VARCHAR(1) NULL,
PRIMARY KEY (SCHED_NAME,ENTRY_ID))
ENGINE=InnoDB;

CREATE TABLE QRTZ_SCHEDULER_STATE (
SCHED_NAME VARCHAR(120) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
CHECKIN_INTERVAL BIGINT(13) NOT NULL,
PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
ENGINE=InnoDB;

CREATE TABLE QRTZ_LOCKS (
SCHED_NAME VARCHAR(120) NOT NULL,
LOCK_NAME VARCHAR(40) NOT NULL,
PRIMARY KEY (SCHED_NAME,LOCK_NAME))
ENGINE=InnoDB;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);

COMMIT; 
-- FINISH QUARTZ TABLES --

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
-- Data for table `eurb`.`acl_sid`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`acl_sid` (`id`, `principal`, `sid`) VALUES ('1', '1', 'admin'); 

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
INSERT INTO `eurb`.`group_authorities` (`group_id`, `authority`) VALUES (200, 'ROLE_USER');
INSERT INTO `eurb`.`group_authorities` (`group_id`, `authority`) VALUES (200, 'ROLE_ADMIN');

COMMIT;

-- -----------------------------------------------------
-- Data for table `eurb`.`group_members`
-- -----------------------------------------------------
START TRANSACTION;
USE `eurb`;
INSERT INTO `eurb`.`group_members` (`id`, `username`, `group_id`) VALUES (1, 'admin', 200);

COMMIT;
