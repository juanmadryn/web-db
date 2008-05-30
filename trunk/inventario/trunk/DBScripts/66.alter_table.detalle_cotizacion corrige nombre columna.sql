-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`detalle_cotizacion` CHANGE COLUMN `maraca_proveedor2` `marca_proveedor2` VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;