ALTER TABLE `eurb`.`report_chart_axis` DROP FOREIGN KEY `report_chart_axis_column_mapping` , DROP FOREIGN KEY `report_chart_axis_report_dataset_fk` ;
ALTER TABLE `eurb`.`report_chart_axis` CHANGE COLUMN `column_mapping_id` `column_mapping_id` BIGINT(20) UNSIGNED NULL  , CHANGE COLUMN `report_dataset_id` `report_dataset_id` BIGINT(20) UNSIGNED NULL  , 
  ADD CONSTRAINT `report_chart_axis_column_mapping`
  FOREIGN KEY (`column_mapping_id` )
  REFERENCES `eurb`.`column_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `report_chart_axis_report_dataset_fk`
  FOREIGN KEY (`report_dataset_id` )
  REFERENCES `eurb`.`report_dataset` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
