-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`detalles_rc` ADD COLUMN `cantidad_excedencia` FLOAT  DEFAULT NULL AFTER `cantidad_recibida`;

ALTER TABLE `inventario`.`comprobante_movimiento_articulo`
ADD COLUMN `user_id_autoriza` INTEGER UNSIGNED 
AFTER `user_id_preparador`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;