-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `infraestructura`.`audita_estados_circuitos` ADD COLUMN `observaciones` MEDIUMTEXT AFTER `host`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;