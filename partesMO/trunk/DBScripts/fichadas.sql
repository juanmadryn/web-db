-- This table keeps the data retrieved by the query at procesaFichadas
-- 5/2/08
CREATE TABLE `partesMO`.`fichadas` (
	ID_FICHADA INT(15) NOT NULL,
	ID_PARTE_DIARIO INT(15) DEFAULT NULL,
	FICHADA DATETIME NOT NULL,
	NRO_LEGAJO INT(15),
	APELLIDO VARCHAR(20) DEFAULT NULL,
	NOMBRE VARCHAR(20) DEFAULT NULL,
	ORIGEN_FICHADA VARCHAR(10) DEFAULT NULL,
    PRIMARY KEY (`ID_FICHADA`)
) ENGINE=InnoDB