ALTER TABLE `partesmo`.`resumen_horas_reloj` DROP COLUMN `quincena`;

-- Change table resumen_horas_reloj storage engine to InnoDB (!)
ALTER TABLE `partesMO`.`resumen_horas_reloj` ENGINE = InnoDB;

-- Add a unique constraint
ALTER TABLE `partesMO`.`resumen_horas_reloj` ADD UNIQUE fecha_legajo_uq (fecha,nro_legajo);