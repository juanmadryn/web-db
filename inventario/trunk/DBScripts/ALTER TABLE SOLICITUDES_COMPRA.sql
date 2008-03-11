-- Alter table solicitudes_compra; drops columns centro_costo_id, 
-- proyecto_id and tarea_id; drops indexes centro_costo_id and tarea_id; 
-- drops foreign keys solicitudes_compra_ibfk_1 and solicitudes_compra_ibfk_2.


ALTER TABLE `inventario`.`solicitudes_compra` 
	DROP COLUMN `tarea_id`, 
	DROP INDEX `tarea_id`,
	DROP FOREIGN KEY `solicitudes_compra_ibfk_1`,
 	DROP FOREIGN KEY `solicitudes_compra_ibfk_2`;	
