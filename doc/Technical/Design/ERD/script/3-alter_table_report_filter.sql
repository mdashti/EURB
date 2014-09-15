ALTER TABLE `eurb`.`report_filter` DROP FOREIGN KEY `fk_report_filter_report_column_id` ;
ALTER TABLE `eurb`.`report_filter` CHANGE COLUMN `report_column_id` `column_mapping_id` BIGINT(20) UNSIGNED NOT NULL  , CHANGE COLUMN `report_column_dataset_id` `report_dataset_id` BIGINT(20) UNSIGNED NOT NULL  , CHANGE COLUMN `report_column_design_id` `report_design_id` BIGINT(20) UNSIGNED NOT NULL  , CHANGE COLUMN `report_column_design_version_id` `report_design_version_id` BIGINT(20) UNSIGNED NOT NULL  , 
  ADD CONSTRAINT `fk_report_filter_column_mapping_id`
  FOREIGN KEY (`column_mapping_id` )
  REFERENCES `eurb`.`column_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_report_filter_report_design`
  FOREIGN KEY (`report_design_id` , `report_design_version_id` )
  REFERENCES `eurb`.`report_design` (`id` , `version_id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_report_filter_report_dataset`
  FOREIGN KEY (`report_dataset_id` )
  REFERENCES `eurb`.`report_dataset` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_report_filter_report_design` (`report_design_id` ASC, `report_design_version_id` ASC) 
, ADD INDEX `fk_report_filter_report_dataset` (`report_dataset_id` ASC) ;