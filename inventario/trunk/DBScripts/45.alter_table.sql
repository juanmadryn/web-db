START TRANSACTION;

ALTER TABLE `infraestructura`.`atributos_entidad` DROP INDEX `unique_index`,
 ADD UNIQUE INDEX unique_index USING BTREE(`atributo_id`, `objeto_id`, `tipo_objeto`, `nombre_objeto`),
 ADD UNIQUE INDEX unique_entidad_index USING BTREE(`atributo_entidad_id`, `entidad_id`);

ROLLBACK;
-- COMMIT;