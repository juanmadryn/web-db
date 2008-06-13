-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`movimiento_articulo` ADD COLUMN `monto_unitario` DOUBLE AFTER `legajo_cargo`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;