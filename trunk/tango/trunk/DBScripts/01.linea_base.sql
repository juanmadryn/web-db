DROP DATABASE IF EXISTS tango;
CREATE DATABASE tango;

DROP USER tango;
CREATE USER tango IDENTIFIED BY 'tango';
GRANT ALL ON tango.* TO tango IDENTIFIED BY 'tango';
GRANT ALL ON tango.* TO tango@localhost IDENTIFIED BY 'tango';

START TRANSACTION;

CREATE TABLE tango.legajo (
  	id int(10) unsigned NOT NULL auto_increment,
	id_legajo int(10) NOT NULL,
	nro_legajo int(10) NOT NULL,
	apellido VARCHAR(100) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	cuil VARCHAR(13) DEFAULT NULL,
  	PRIMARY KEY(id),
	UNIQUE INDEX(nro_legajo)
) 
ENGINE InnoDB;

COMMIT;