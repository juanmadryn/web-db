-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`detalle_cotizacion` ADD COLUMN `unidad_medida_id` INTEGER UNSIGNED NULL AFTER `cotizacion_compra_id`,
 ADD COLUMN `cantidad` FLOAT UNSIGNED NULL AFTER `unidad_medida_id`,
 ADD CONSTRAINT `detalle_cotizacion_unidad_medida_fk` FOREIGN KEY `detalle_cotizacion_unidad_medida_fk` (`unidad_medida_id`)
    REFERENCES `unidades_medida` (`unidad_medida_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
        
-- actualiza todos los datos de la tabla con las unidades de medida de los artículos de la SC y la cantidad
update `inventario`.`detalle_cotizacion` as t1 
   set unidad_medida_id = (select unidad_medida_id_pedida from detalle_sc as t2
                           where t2.detalle_SC_id = t1.detalle_SC_id),
       cantidad =  (select IFNULL(cantidad_pedida,cantidad_solicitada) from detalle_sc as t2
                    where t2.detalle_SC_id = t1.detalle_SC_id);

ALTER TABLE `inventario`.`detalle_cotizacion` MODIFY COLUMN `unidad_medida_id` INTEGER UNSIGNED NOT NULL,
 MODIFY COLUMN `cantidad` FLOAT UNSIGNED NOT NULL;
    

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;