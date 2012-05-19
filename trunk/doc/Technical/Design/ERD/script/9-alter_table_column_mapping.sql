SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';
SET @OLD_SQL_SAFE_UPDATES=@@SQL_SAFE_UPDATES, SQL_SAFE_UPDATES=0;

UPDATE `eurb`.`column_mapping` SET referenced_table = NULL, referenced_id_col = NULL, referenced_value_col = NULL;

ALTER TABLE `eurb`.`column_mapping` CHANGE COLUMN `referenced_table` `referenced_table` BIGINT(19) UNSIGNED NULL DEFAULT NULL  , CHANGE COLUMN `referenced_id_col` `referenced_id_col` BIGINT(19) UNSIGNED NULL DEFAULT NULL  , CHANGE COLUMN `referenced_value_col` `referenced_value_col` BIGINT(19) UNSIGNED NULL DEFAULT NULL  , 
  ADD CONSTRAINT `fk_column_mapping_table_mapping_referenced_table`
  FOREIGN KEY (`referenced_table` )
  REFERENCES `eurb`.`table_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_column_mapping_column_mapping_referenced_id_col`
  FOREIGN KEY (`referenced_id_col` )
  REFERENCES `eurb`.`column_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_column_mapping_column_mapping_referenced_value_col`
  FOREIGN KEY (`referenced_value_col` )
  REFERENCES `eurb`.`column_mapping` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_column_mapping_table_mapping_referenced_table` (`referenced_table` ASC) 
, ADD INDEX `fk_column_mapping_column_mapping_referenced_id_col` (`referenced_id_col` ASC) 
, ADD INDEX `fk_column_mapping_column_mapping_referenced_value_col` (`referenced_value_col` ASC) ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
SET SQL_SAFE_UPDATES=@OLD_SQL_SAFE_UPDATES;

