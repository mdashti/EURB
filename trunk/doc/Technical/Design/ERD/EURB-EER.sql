SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `eurb` ;
CREATE SCHEMA IF NOT EXISTS `eurb` DEFAULT CHARACTER SET utf8 COLLATE utf8_persian_ci ;
USE `eurb` ;

-- -----------------------------------------------------
-- Table `eurb`.`persistable_object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`persistable_object` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`persistable_object` (
  `obj_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`obj_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`db_config`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`db_config` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`db_config` (
  `obj_id` BIGINT UNSIGNED NOT NULL ,
  INDEX `fk_db_config_persistable_object` (`obj_id` ASC) ,
  PRIMARY KEY (`obj_id`) ,
  CONSTRAINT `fk_db_config_persistable_object`
    FOREIGN KEY (`obj_id` )
    REFERENCES `eurb`.`persistable_object` (`obj_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`table_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`table_mapping` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`table_mapping` (
  `obj_id` BIGINT UNSIGNED NOT NULL ,
  INDEX `fk_table_mapping_persistable_object1` (`obj_id` ASC) ,
  PRIMARY KEY (`obj_id`) ,
  CONSTRAINT `fk_table_mapping_persistable_object1`
    FOREIGN KEY (`obj_id` )
    REFERENCES `eurb`.`persistable_object` (`obj_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`column_mapping`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`column_mapping` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`column_mapping` (
  `obj_id` BIGINT UNSIGNED NOT NULL ,
  INDEX `fk_column_mapping_persistable_object1` (`obj_id` ASC) ,
  PRIMARY KEY (`obj_id`) ,
  CONSTRAINT `fk_column_mapping_persistable_object1`
    FOREIGN KEY (`obj_id` )
    REFERENCES `eurb`.`persistable_object` (`obj_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`users` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`users` (
  `username` VARCHAR(50) NOT NULL ,
  `password` VARCHAR(50) NOT NULL ,
  `enabled` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`username`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`authorities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`authorities` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`authorities` (
  `username` VARCHAR(50) NOT NULL ,
  `authority` VARCHAR(50) NOT NULL ,
  UNIQUE INDEX `ix_auth_username` (`username` ASC, `authority` ASC) ,
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username` )
    REFERENCES `eurb`.`users` (`username` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`groups` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`groups` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `group_name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`id`) );


-- -----------------------------------------------------
-- Table `eurb`.`group_authorities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`group_authorities` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`group_authorities` (
  `group_id` BIGINT UNSIGNED NOT NULL ,
  `authority` VARCHAR(50) NOT NULL ,
  INDEX `fk_group_authorities_group` (`group_id` ASC) ,
  CONSTRAINT `fk_group_authorities_group`
    FOREIGN KEY (`group_id` )
    REFERENCES `eurb`.`groups` (`id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`group_members`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`group_members` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`group_members` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(50) NOT NULL ,
  `group_id` BIGINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_group_members_group` (`group_id` ASC) ,
  CONSTRAINT `fk_group_members_group`
    FOREIGN KEY (`group_id` )
    REFERENCES `eurb`.`groups` (`id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`persistent_logins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`persistent_logins` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`persistent_logins` (
  `username` VARCHAR(64) NOT NULL ,
  `series` VARCHAR(64) NULL DEFAULT NULL ,
  `token` VARCHAR(64) NOT NULL ,
  `last_used` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`series`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_sid`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_sid` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_sid` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `principal` TINYINT(1) NOT NULL ,
  `sid` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_uk_1` (`sid` ASC, `principal` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_class` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_class` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `class` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_uk_2` (`class` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_object_identity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_object_identity` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_object_identity` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `object_id_class` BIGINT UNSIGNED NOT NULL ,
  `object_id_identity` BIGINT UNSIGNED NOT NULL ,
  `parent_object` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `owner_sid` BIGINT UNSIGNED NOT NULL ,
  `entries_inheriting` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_uk_3` (`object_id_class` ASC, `object_id_identity` ASC) ,
  INDEX `foreign_fk_1` (`parent_object` ASC) ,
  INDEX `foreign_fk_3` (`owner_sid` ASC) ,
  CONSTRAINT `foreign_fk_1`
    FOREIGN KEY (`parent_object` )
    REFERENCES `eurb`.`acl_object_identity` (`id` ),
  CONSTRAINT `foreign_fk_2`
    FOREIGN KEY (`object_id_class` )
    REFERENCES `eurb`.`acl_class` (`id` ),
  CONSTRAINT `foreign_fk_3`
    FOREIGN KEY (`owner_sid` )
    REFERENCES `eurb`.`acl_sid` (`id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eurb`.`acl_entry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`acl_entry` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`acl_entry` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `acl_object_identity` BIGINT UNSIGNED NOT NULL ,
  `ace_order` INT UNSIGNED NOT NULL ,
  `sid` BIGINT UNSIGNED NOT NULL ,
  `mask` INT NOT NULL ,
  `granting` TINYINT(1) NOT NULL ,
  `audit_success` TINYINT(1) NOT NULL ,
  `audit_failure` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_uk_4` (`acl_object_identity` ASC, `ace_order` ASC) ,
  INDEX `foreign_fk_5` (`sid` ASC) ,
  CONSTRAINT `foreign_fk_4`
    FOREIGN KEY (`acl_object_identity` )
    REFERENCES `eurb`.`acl_object_identity` (`id` ),
  CONSTRAINT `foreign_fk_5`
    FOREIGN KEY (`sid` )
    REFERENCES `eurb`.`acl_sid` (`id` ))
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
