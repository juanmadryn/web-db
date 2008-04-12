START TRANSACTION;

ALTER TABLE `inventario`.`instancias_aprobacion` 
	ADD COLUMN `nombre_objeto` VARCHAR(45)  NOT NULL AFTER `instancia_aprobacion_id`,
 	ADD COLUMN `objeto_id` INT(15)  NOT NULL AFTER `nombre_objeto`;

CREATE INDEX instancias_aprobacion_nombre_objeto_index 
	ON `inventario`.`instancias_aprobacion`(`nombre_objeto`);  

UPDATE `inventario`.`instancias_aprobacion` SET
	`nombre_objeto` = 'solicitudes_compra',
	`objeto_id` =  `solicitud_compra_id`;

ALTER TABLE `inventario`.`instancias_aprobacion` 
	DROP FOREIGN KEY `FK_instancias_aprobacion_solicitudes_compra`;
ALTER TABLE `inventario`.`instancias_aprobacion` 
	DROP COLUMN `solicitud_compra_id`;

ROLLBACK;
-- COMMIT;
