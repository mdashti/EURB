USE `eurb`;

ALTER TABLE `eurb`.`db_config` CHANGE COLUMN `password` `password` BLOB NULL DEFAULT NULL  ;
ALTER TABLE `eurb`.`db_config` CHANGE COLUMN `username` `username` BLOB NULL DEFAULT NULL  ;
ALTER TABLE `eurb`.`db_config` CHANGE COLUMN `driver_url` `driver_url` BLOB NULL DEFAULT NULL  ;

DELIMITER $$

DROP TRIGGER IF EXISTS eurb.DB_CONFIG_INSERT_Trigger$$
USE `eurb`$$

    
CREATE TRIGGER DB_CONFIG_INSERT_Trigger
AFTER insert ON db_config
FOR EACH ROW
BEGIN
    UPDATE `db_config` SET 
    `password` = AES_ENCRYPT(new.password, concat('pspwd', new.id)), 
    `username` = AES_ENCRYPT(new.username, concat('usrpwd', new.id)),
    `driver_url` = AES_ENCRYPT(new.driver_url, concat('durlpwd', new.id))
    WHERE `id`=new.id;
END$$

DELIMITER ;


UPDATE `db_config` SET 
`password` = AES_ENCRYPT(password, concat('pspwd', id)), 
`username` = AES_ENCRYPT(username, concat('usrpwd', id)),
`driver_url` = AES_ENCRYPT(driver_url, concat('durlpwd', id));