ALTER TABLE `eurb`.`report_category` ADD COLUMN `parent_category_id` BIGINT(20) UNSIGNED NULL  AFTER `description` , 
  ADD CONSTRAINT `fk_report_category_parent`
  FOREIGN KEY (`parent_category_id` )
  REFERENCES `eurb`.`report_category` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_report_category_parent` (`parent_category_id` ASC) ;
