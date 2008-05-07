-- Data for the 'menu' table
USE inventario;
START TRANSACTION;

ALTER TABLE `inventario`.`ordenes_compra` ADD COLUMN `descuento` DOUBLE UNSIGNED DEFAULT NULL AFTER `condicion_compra_id`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;