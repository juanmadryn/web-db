USE inventario;
START TRANSACTION;

ALTER TABLE `inventario`.`detalle_sc` ADD COLUMN `iva` DOUBLE UNSIGNED DEFAULT NULL AFTER `unidad_medida_id`,
 ADD COLUMN `descuento` DOUBLE UNSIGNED DEFAULT NULL AFTER `iva`;

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;
