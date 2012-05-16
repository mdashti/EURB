ALTER TABLE `eurb`.`report_design` ADD COLUMN `db_config_id` BIGINT(20) UNSIGNED NULL  AFTER `record_status` , 
  ADD CONSTRAINT `fk_report_design_db_config`
  FOREIGN KEY (`db_config_id` )
  REFERENCES `eurb`.`db_config` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_report_design_db_config` (`db_config_id` ASC) ;
