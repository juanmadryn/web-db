--
USE infraestructura;

START TRANSACTION;

ALTER TABLE `infraestructura`.`website_user` ADD COLUMN `nro_comprador` INTEGER UNSIGNED DEFAULT NULL AFTER `nro_legajo`;

ROLLBACK;
-- COMMIT;