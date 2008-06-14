-- Data for the 'menu' table
START TRANSACTION;
ALTER TABLE `inventario`.`movimiento_articulo` 
ADD COLUMN `legajo_cargo` INTEGER UNSIGNED AFTER `unidad_medida_id`,
ADD COLUMN `monto_unitario` DOUBLE AFTER `legajo_cargo`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;