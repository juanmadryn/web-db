-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `infraestructura`.`acciones_apps` 
ADD COLUMN `manual` BOOLEAN  NOT NULL DEFAULT TRUE AFTER `circuito`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;