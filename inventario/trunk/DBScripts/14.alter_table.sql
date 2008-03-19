ALTER TABLE `inventario`.`detalle_sc` 
ADD COLUMN `unidad_medida` ENUM('Piezas','Kgs','Mts','Lts','Grs') NOT NULL AFTER `fecha_ultima_compra`;