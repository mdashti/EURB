CREATE  TABLE IF NOT EXISTS `eurb`.`dashboard` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `is_default` TINYINT(1) NOT NULL DEFAULT 0 ,
  `title` VARCHAR(255) NOT NULL ,
  `parent_dashboard` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `username` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_dashboard_persistable_object` (`id` ASC) ,
  INDEX `fk_dashboard_dashboard` (`parent_dashboard` ASC) ,
  INDEX `fk_dashboard_users` (`username` ASC) ,
  CONSTRAINT `fk_dashboard_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_dashboard`
    FOREIGN KEY (`parent_dashboard` )
    REFERENCES `eurb`.`dashboard` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_users`
    FOREIGN KEY (`username` )
    REFERENCES `eurb`.`users` (`username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

CREATE  TABLE IF NOT EXISTS `eurb`.`dashboard_col` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `dashboard_id` BIGINT UNSIGNED NOT NULL ,
  `col_order` INT(2) NOT NULL ,
  `col_width` DOUBLE NULL DEFAULT NULL ,
  INDEX `fk_dashboard_col_dashboard` (`dashboard_id` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_dashboard_col_dashboard`
    FOREIGN KEY (`dashboard_id` )
    REFERENCES `eurb`.`dashboard` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_col_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

CREATE  TABLE IF NOT EXISTS `eurb`.`dashboard_item` (
  `id` BIGINT UNSIGNED NOT NULL ,
  `dashboard_id` BIGINT UNSIGNED NOT NULL ,
  `dashboard_col_id` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `item_order` INT(2) NOT NULL ,
  `item_height` DOUBLE NULL DEFAULT NULL ,
  `item_collapsed` TINYINT(1) NOT NULL DEFAULT 0 ,
  `item_closed` TINYINT(1) NOT NULL DEFAULT 0 ,
  `report_design_id` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `report_chart_id` BIGINT UNSIGNED NULL DEFAULT NULL ,
  `is_show_table` TINYINT(1) NOT NULL DEFAULT 0 ,
  `item_title` VARCHAR(255) NULL ,
  `item_content` LONGTEXT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_dashboard_item_dashboard_col` (`dashboard_col_id` ASC) ,
  INDEX `fk_dashboard_item_dashboard` (`dashboard_id` ASC) ,
  INDEX `fk_dashboard_item_report_design1` (`report_design_id` ASC) ,
  INDEX `fk_dashboard_item_report_chart1` (`report_chart_id` ASC) ,
  CONSTRAINT `fk_dashboard_item_persistable_object`
    FOREIGN KEY (`id` )
    REFERENCES `eurb`.`persistable_object` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_dashboard_col`
    FOREIGN KEY (`dashboard_col_id` )
    REFERENCES `eurb`.`dashboard_col` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_dashboard`
    FOREIGN KEY (`dashboard_id` )
    REFERENCES `eurb`.`dashboard` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_report_design1`
    FOREIGN KEY (`report_design_id` )
    REFERENCES `eurb`.`report_design` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dashboard_item_report_chart1`
    FOREIGN KEY (`report_chart_id` )
    REFERENCES `eurb`.`report_chart` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_persian_ci;

INSERT INTO `eurb`.`persistable_object` (`obj_type`, `creator`, `create_date`, `modifier`, `modify_date`) VALUES (510, 'admin', NULL, NULL, NULL);

INSERT INTO `eurb`.`dashboard` (`id`, `is_default`, `title`) VALUES ( LAST_INSERT_ID(), 1, 'dashboard' );
