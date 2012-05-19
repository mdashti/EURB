ALTER TABLE `eurb`.`report_filter` DROP FOREIGN KEY `fk_report_filter_report_column_operand1` ;
ALTER TABLE `eurb`.`report_filter` CHANGE COLUMN `operand1_column_id` `operand1_column_mapping_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL  , CHANGE COLUMN `operand1_column_dataset_id` `operand1_dataset_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL  , CHANGE COLUMN `operand1_column_design_id` `operand1_report_column_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL  , 
  ADD CONSTRAINT `fk_report_filter_operand1_dataset`
  FOREIGN KEY (`operand1_dataset_id` )
  REFERENCES `eurb`.`report_dataset` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_report_fitler_operand1_column_mapping`
  FOREIGN KEY (`operand1_column_mapping_id` )
  REFERENCES `eurb`.`column_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_report_filter_operand1_report_column`
  FOREIGN KEY (`operand1_report_column_id` )
  REFERENCES `eurb`.`report_column` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_report_filter_operand1_dataset` (`operand1_dataset_id` ASC) 
, ADD INDEX `fk_report_fitler_operand1_column_mapping` (`operand1_column_mapping_id` ASC) 
, ADD INDEX `fk_report_filter_operand1_report_column` (`operand1_report_column_id` ASC) ;