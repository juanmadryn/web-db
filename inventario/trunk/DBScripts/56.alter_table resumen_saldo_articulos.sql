ALTER TABLE `inventario`.`resumen_saldo_articulos` ADD COLUMN `total_ingresos` DOUBLE AFTER `en_proceso`,
 ADD COLUMN `total_egresos` DOUBLE AFTER `total_ingresos`,
 ADD COLUMN `cant_transacciones_ingresos` INTEGER UNSIGNED AFTER `total_egresos`,
 ADD COLUMN `cant_transacciones_egresos` INTEGER UNSIGNED AFTER `cant_transacciones_ingresos`;
