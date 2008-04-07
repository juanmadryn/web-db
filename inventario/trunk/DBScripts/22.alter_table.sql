ALTER TABLE `inventario`.`detalle_sc` CHANGE COLUMN `unidad_medida` `unidad_medida_id` INTEGER UNSIGNED NOT NULL,
 ADD CONSTRAINT `FK_detalle_sc_unidad_medida` FOREIGN KEY `FK_detalle_sc_unidad_medida` (`unidad_medida_id`)
    REFERENCES `unidades_medida` (`unidad_medida_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;
