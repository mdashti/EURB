CREATE  TABLE `eurb`.`report_chart_axis` (
  `id` BIGINT(20) UNSIGNED NOT NULL ,
  `chart_id` BIGINT(20) UNSIGNED NOT NULL ,
  `axis_type` VARCHAR(2) NOT NULL ,
  `title` VARCHAR(45) NULL ,
  `column_mapping_id` BIGINT(20) UNSIGNED NOT NULL ,
  `aggregation` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );


ALTER TABLE `eurb`.`report_chart_axis` 
  ADD CONSTRAINT `report_chart_axis_persistable_object`
  FOREIGN KEY (`id` )
  REFERENCES `eurb`.`persistable_object` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `report_chart_axis_chart`
  FOREIGN KEY (`chart_id` )
  REFERENCES `eurb`.`report_chart` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `report_chart_axis_column_mapping`
  FOREIGN KEY (`column_mapping_id` )
  REFERENCES `eurb`.`column_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `report_chart_axis_persistable_object` (`id` ASC) 
, ADD INDEX `report_chart_axis_chart` (`chart_id` ASC) 
, ADD INDEX `report_chart_axis_column_mapping` (`column_mapping_id` ASC) ;

