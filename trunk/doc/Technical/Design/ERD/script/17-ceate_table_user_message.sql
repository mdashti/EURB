-- This table is used to provide the system information that
-- should be seen by end-user, after logging into system
-- -----------------------------------------------------
-- Table `eurb`.`user_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eurb`.`user_message` ;

CREATE  TABLE IF NOT EXISTS `eurb`.`user_message` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `username` VARCHAR(50) NOT NULL ,
  `message` LONGTEXT NOT NULL ,
  `type` INT NOT NULL DEFAULT 1 ,
  `show` TINYINT(1) NOT NULL DEFAULT 1 ,
  `available_from` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  INDEX `fk_user_message_persistable_object` (`id` ASC) ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_user_message_users` (`username` ASC) ,
  CONSTRAINT `fk_user_message_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_message_users`
    FOREIGN KEY (`username` )
    REFERENCES `eurb`.`users` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;