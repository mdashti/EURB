ALTER TABLE `eurb`.`group_aggregation` 
  ADD CONSTRAINT `fk_group_aggregation_aggregated_column_mapping`
  FOREIGN KEY (`aggregated_column_mapping_id` )
  REFERENCES `eurb`.`column_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_group_aggregation_aggregated_column_dataset`
  FOREIGN KEY (`aggregated_column_dataset_id` )
  REFERENCES `eurb`.`report_dataset` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_group_aggregation_aggregated_report_column`
  FOREIGN KEY (`aggregated_report_column_id` )
  REFERENCES `eurb`.`report_column` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_group_aggregation_aggregated_column_mapping` (`aggregated_column_mapping_id` ASC) 
, ADD INDEX `fk_group_aggregation_aggregated_column_dataset` (`aggregated_column_dataset_id` ASC) 
, ADD INDEX `fk_group_aggregation_aggregated_report_column` (`aggregated_report_column_id` ASC) ;