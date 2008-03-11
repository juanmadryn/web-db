-- Alter table detalle_sc; adds centro_costo_id, proyecto_id and tarea_id columns and foreign keys.


ALTER TABLE `inventario`.`detalle_sc` 
 	ADD COLUMN `tarea_id` INT(15) UNSIGNED AFTER `proyecto_id`,
	ADD COLUMN `monto_unitario` DOUBLE UNSIGNED AFTER `tarea_id`,
 	ADD COLUMN `monto_ultima_compra` DOUBLE UNSIGNED AFTER `monto_unitario`;
	ADD CONSTRAINT `FK_detalle_sc_6` FOREIGN KEY `FK_detalle_sc_6` (`tarea_id`)
    	REFERENCES `proyectos`.`tareas_proyecto` (`tarea_id`)
    	ON DELETE NO ACTION
    	ON UPDATE NO ACTION;
	MODIFY COLUMN `orden_compra_id` INTEGER UNSIGNED;