USE inventario;
START TRANSACTION;

CREATE TABLE `inventario`.`condiciones_compra` (
  `condicion_compra_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50)  NOT NULL,
  `descripcion` VARCHAR(255)  NOT NULL,
  `observaciones` MEDIUMTEXT  DEFAULT NULL,
  PRIMARY KEY (`condicion_compra_id`),
  UNIQUE `nombre_unique`(`nombre`)
)
ENGINE = InnoDB
COMMENT = 'Condiciones de Compra';

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;