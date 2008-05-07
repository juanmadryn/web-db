DROP DATABASE IF EXISTS tango;
CREATE DATABASE tango;

DROP USER tango@localhost;
CREATE USER tango@localhost IDENTIFIED BY 'tango';
GRANT ALL ON tango.* TO tango IDENTIFIED BY 'tango';
GRANT ALL ON tango.* TO tango@localhost IDENTIFIED BY 'tango';

START TRANSACTION;

CREATE TABLE  `tango`.`LEGAJO` (
  `ID_LEGAJO` int(15) unsigned NOT NULL,
  `ID_TIPO_DOCUMENTO` int(15) unsigned default NULL,
  `ID_LEGAJO_JEFE` int(15) unsigned default NULL,
  `ID_PROVINCIA` int(15) unsigned default NULL,
  `ID_MODELO_ASIENTO_SU` int(15) unsigned default NULL,
  `ID_NACIONALIDAD` int(15) unsigned default NULL,
  `ID_EXPEDIDO_POR` int(15) unsigned default NULL,
  `NRO_LEGAJO` int(15) unsigned NOT NULL,
  `APELLIDO` varchar(90) NOT NULL,
  `NOMBRE` varchar(90) NOT NULL,
  `APELLIDO_CONYUGE` varchar(90) default NULL,
  `FECHA_NACIMIENTO` datetime default NULL,
  `NRO_DOCUMENTO` int(15) unsigned default NULL,
  `CALLE` varchar(30) default NULL,
  `NRO_DOMIC` varchar(10) default NULL,
  `PISO` varchar(10) default NULL,
  `DEPARTAMENTO_DOMIC` varchar(10) default NULL,
  `CODIGO_POSTAL` varchar(8) default NULL,
  `LOCALIDAD` varchar(40) default NULL,
  `TAREA_HABITUAL` varchar(30) default NULL,
  `CUIL` varchar(13) default NULL,
  `EMAIL` varchar(90) default NULL,
  `SEXO` varchar(20) NOT NULL,
  `ESTADO_CIVIL` varchar(20) default NULL,
  `FOTO_LEGAJO` blob,
  `CONFIDENCIAL` char(1) NOT NULL,
  `OBSERVACIONES` varchar(255) default NULL,
  `APELLIDO_MATERNO` varchar(90) default NULL,
  `ID_COMUNA` int(15) unsigned default NULL,
  `CA_83_IMPORT1` float default NULL,
  `CA_83_IMPORT2` float default NULL,
  `CA_83_IMPORT3` float default NULL,
  `CA_83_IMPORT4` float default NULL,
  `CA_83_IMPORT5` float default NULL,
  `CA_83_DESC1` varchar(20) default NULL,
  `CA_83_DESC2` varchar(20) default NULL,
  `CA_83_DESC3` varchar(20) default NULL,
  `CA_83_DESC4` varchar(20) default NULL,
  `CA_83_DESC5` varchar(20) default NULL,
  `CA_83_ANTIGUE` int(2) unsigned default NULL,
  `CA_83_ANTIG_ANT` int(2) unsigned default NULL,
  `CA_83_EVALUAC` decimal(5,2) default NULL,
  `CA_83_ADI_FIJ` float default NULL,
  `CA_83_TIP_SEG` varchar(2) default NULL,
  `CA_83_SEG_VID` float default NULL,
  `CA_83_NUM_JUB` varchar(10) default NULL,
  `CA_83_N_INSCRIP` varchar(20) default NULL,
  PRIMARY KEY  (`ID_LEGAJO`),
  UNIQUE KEY (`NRO_LEGAJO`)
) ENGINE=InnoDB;

COMMIT;