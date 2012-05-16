CREATE  TABLE `eurb`.`object_config` (
  `object_id` BIGINT(20) UNSIGNED NOT NULL ,
  `key` VARCHAR(100) NOT NULL ,
  `value` VARCHAR(100) NOT NULL ,
  INDEX `pk` (`object_id` ASC, `key` ASC) );

ALTER TABLE `eurb`.`object_config` 
  ADD CONSTRAINT `object_config_persistable_object`
  FOREIGN KEY (`object_id` )
  REFERENCES `eurb`.`persistable_object` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `object_config_persistable_object` (`object_id` ASC) ;

