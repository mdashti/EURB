ALTER TABLE `eurb`.`report_chart_axis` ADD COLUMN `report_dataset_id` BIGINT(20) UNSIGNED NOT NULL  AFTER `aggregation` , 
  ADD CONSTRAINT `report_chart_axis_report_dataset_fk`
  FOREIGN KEY (`report_dataset_id` )
  REFERENCES `eurb`.`report_dataset` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `report_chart_axis_report_dataset_fk` (`report_dataset_id` ASC) ;