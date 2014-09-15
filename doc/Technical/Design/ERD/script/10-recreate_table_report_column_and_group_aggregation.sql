SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP TABLE IF EXISTS `eurb`.`group_aggregation`;
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
  INDEX `fk_group_aggregation_report_column_parent` (`parent_column_id` ASC) ,
  INDEX `fk_group_aggregation_report_column_aggregated` (`aggregated_report_column_id` ASC) ,
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
  CONSTRAINT `fk_group_aggregation_report_column_aggregated`
    FOREIGN KEY (`aggregated_report_column_id`)
    REFERENCES `eurb`.`report_column` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

