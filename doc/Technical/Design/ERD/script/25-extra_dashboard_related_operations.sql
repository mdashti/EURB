ALTER TABLE `eurb`.`dashboard` ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT 0  AFTER `is_default`;

CREATE  TABLE IF NOT EXISTS `eurb`.`user_preferences` (
  `id` BIGINT(19) UNSIGNED NOT NULL ,
  `username` VARCHAR(50) NOT NULL ,
  `pkey` VARCHAR(255) NOT NULL ,
  `pvalue` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_user_preferences_users` (`username` ASC) ,
  CONSTRAINT `fk_user_preferences_persistable_object1`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_preferences_users`
    FOREIGN KEY (`username` )
    REFERENCES `eurb`.`users` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;