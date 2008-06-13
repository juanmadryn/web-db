-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`resumen_saldo_articulos` ADD COLUMN `precio_reposicion` DOUBLE AFTER `cant_transacciones_egresos`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;