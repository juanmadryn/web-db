-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`cotizaciones_compra` 
   ADD COLUMN `total_cotizacion_seleccionada` DOUBLE  NOT NULL AFTER `observaciones`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;