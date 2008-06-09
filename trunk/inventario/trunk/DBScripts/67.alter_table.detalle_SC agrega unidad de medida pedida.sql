-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`detalle_sc` ADD COLUMN `unidad_medida_id_pedida` INTEGER UNSIGNED AFTER `unidad_medida_id`,
 ADD CONSTRAINT `FK_detalle_sc_unidad_medida_pedida` FOREIGN KEY `FK_detalle_sc_unidad_medida_pedida` (`unidad_medida_id_pedida`)
    REFERENCES `unidades_medida` (`unidad_medida_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
    
-- actualiza todos los datos de la tabla con la unidades de medida solicitadas
update `inventario`.`detalle_sc` set unidad_medida_id_pedida = unidad_medida_id;
    

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;