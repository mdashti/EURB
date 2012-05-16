CREATE  TABLE `eurb`.`report_chart` (
  `id` BIGINT(20) UNSIGNED NOT NULL ,
  `design_id` BIGINT(20) UNSIGNED NOT NULL ,
  `design_version_id` BIGINT(20) UNSIGNED NOT NULL ,
  `chart_type` VARCHAR(45) NOT NULL ,
  `name` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) );


ALTER TABLE `eurb`.`report_chart` 
  ADD CONSTRAINT `report_chart_persistable_object`
  FOREIGN KEY (`id` )
  REFERENCES `eurb`.`persistable_object` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `report_chart_persistable_object` (`id` ASC) ;

