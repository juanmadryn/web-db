-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `infraestructura`.`acciones_apps` MODIFY COLUMN `manual` CHAR(1)  NOT NULL DEFAULT 'V';

UPDATE `infraestructura`.`acciones_apps` SET `manual` = 'V';

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;