ALTER TABLE `inventario`.`ordenes_compra` DROP FOREIGN KEY `ordenes_compra_ibfk_3`,
 DROP FOREIGN KEY `ordenes_compra_ibfk_4`;
ALTER TABLE `inventario`.`ordenes_compra` DROP COLUMN `centro_costo_id`,
 DROP COLUMN `proyecto_id`,
 DROP COLUMN `tarea_id`;
