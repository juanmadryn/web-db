CREATE SCHEMA IF NOT EXISTS inventario;

-- ------------------------------------------------------------
-- Define al almacen y su localización. Se deberán impelemtar atributos asociados a los almacenes para indicar funcionalidad, comportamiento y/o segmentación de los almacenes
-- ------------------------------------------------------------

CREATE TABLE inventario.almacenes (
  almacen_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(90) NOT NULL,
  descripcion VARCHAR(255) NULL,
  observaciones VARCHAR(255) NULL,
  PRIMARY KEY(almacen_id),
  INDEX almacenes_UK1(nombre)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Permite definir los centros de costo de la organización, permitiendo de esta foprma realizar movimientos de almacen a un centro de costo.
-- ------------------------------------------------------------

CREATE TABLE inventario.centro_costo (
  centro_costo_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(90) NOT NULL,
  descripcion VARCHAR(255) NULL,
  observaciones VARCHAR(255) NULL,
  PRIMARY KEY(centro_costo_id),
  UNIQUE INDEX centro_costo_UK1(nombre)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Clase de artículo que permite segmentar y definir atributos comunes (de funcionalidad, comportamiento o segmentación) a todos los artículos de la misma clase.
-- ------------------------------------------------------------

CREATE TABLE inventario.clase_articulo (
  clase_articulo_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(90) NOT NULL,
  descripcion VARCHAR(255) NULL,
  observaciones VARCHAR(255) NULL,
  generico CHAR(1) NOT NULL DEFAULT 'F',
  PRIMARY KEY(clase_articulo_id),
  UNIQUE INDEX clase_articulo_UK1(nombre)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Tabla que contiene la definición de los artículos. A esta table se le deben implementar  atributos para extender tyodo tipo de información requerida para funcionalidad según relevamiento del negocio. Como por ejemplo Cuantas contable sasociadas. SI es inventariable. Si lleva reposición y control de Stock. Características adicionales al artículo y si tiene claves externas a otros sistemas (como por ejemplo TANGO)
-- ------------------------------------------------------------

CREATE TABLE inventario.articulos (
  articulo_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  clase_articulo_id INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(90) NOT NULL,
  descripcion VARCHAR(255) NULL,
  observaciones VARCHAR(255) NULL,
  descripcion_completa MEDIUMTEXT NULL,
  clave_externa1 VARCHAR(20) NULL,
  clave_externa2 VARCHAR(20) NULL,
  clave_externa3 VARCHAR(20) NULL,
  activo CHAR(1) NOT NULL DEFAULT 'F',
  anulado CHAR(1) NOT NULL DEFAULT 'F',
  PRIMARY KEY(articulo_id),
  UNIQUE INDEX articulos_UK1(nombre),
  UNIQUE INDEX articulos_UK2(clave_externa1),
  FOREIGN KEY(clase_articulo_id)
    REFERENCES clase_articulo(clase_articulo_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Tabla que desnormaliza los saldos por mes de los artículos, permitiendo mejorar la performance en la generación dinámica de saldos y stock
-- ------------------------------------------------------------

CREATE TABLE inventario.resumen_saldo_articulos (
  resumen_saldo_articulo_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  almacen_id INTEGER UNSIGNED NOT NULL,
  articulo_id INTEGER UNSIGNED NOT NULL,
  periodo DATE NULL,
  stock_en_mano DOUBLE NOT NULL DEFAULT '0',
  reservado DOUBLE NOT NULL DEFAULT '0',
  PRIMARY KEY(resumen_saldo_articulo_id),
  UNIQUE INDEX resumen_saldo_articulos_UK1(periodo, articulo_id, almacen_id),
  FOREIGN KEY(articulo_id)
    REFERENCES articulos(articulo_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(almacen_id)
    REFERENCES almacenes(almacen_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Implementa los distintos tipos de comprobantes de movimientos de artículo de los almacenes.
-- ------------------------------------------------------------

CREATE TABLE inventario.tipo_movimiento_articulo (
  tipo_movimiento_articulo_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(90) NOT NULL,
  descripcion VARCHAR(255) NULL,
  observaciones VARCHAR(255) NULL,
  positivo CHAR(1) NOT NULL DEFAULT 'V',
  reserva CHAR(1) NOT NULL DEFAULT 'F',
  PRIMARY KEY(tipo_movimiento_articulo_id),
  UNIQUE INDEX tipo_movimiento_articulo_UK1(nombre)
)
TYPE=InnoDB;

-- ------------------------------------------------------------
-- Cabecera: Comprobante del movimiento de almacen. El mismo implementa la columna de estado que permite implementar un workflow de proceso definido por el usuario. Por ejemplo alguien genera un vale de consumo, que el almacenero prepara y luego entrega
-- ------------------------------------------------------------

CREATE TABLE inventario.comprobante_movimiento_articulo (
  comprobante_movimiento_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  almacen_id INTEGER UNSIGNED NOT NULL,
  user_id_retira INTEGER(10) UNSIGNED NOT NULL,
  user_id_preparador INTEGER(10) UNSIGNED NOT NULL,
  estado VARCHAR(15) NOT NULL,
  tipo_movimiento_articulo_id INTEGER UNSIGNED NOT NULL,
  fecha DATE NOT NULL,
  observaciones MEDIUMTEXT NULL,
  PRIMARY KEY(comprobante_movimiento_id),
  FOREIGN KEY(tipo_movimiento_articulo_id)
    REFERENCES inventario.tipo_movimiento_articulo(tipo_movimiento_articulo_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(estado)
    REFERENCES infraestructura.estados(estado)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(user_id_preparador)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(user_id_retira)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(almacen_id)
    REFERENCES inventario.almacenes(almacen_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE inventario.movimiento_articulo (
  movimiento_articulo_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  centro_costo_id INTEGER UNSIGNED NULL,
  proyecto_id INTEGER(15) UNSIGNED NULL,
  tarea_id INTEGER(15) UNSIGNED NULL,
  resumen_saldo_articulo_id INTEGER UNSIGNED NULL,
  comprobante_movimiento_id INTEGER UNSIGNED NOT NULL,
  articulo_id INTEGER UNSIGNED NOT NULL,
  cantidad_solicitada DOUBLE NOT NULL DEFAULT '0',
  cantidad_entregada DOUBLE NOT NULL DEFAULT '0',
  cantidad_anulada DOUBLE NOT NULL DEFAULT '0',
  descripcion VARCHAR(255) NULL,
  descripcion_adicional MEDIUMTEXT NULL,
  PRIMARY KEY(movimiento_articulo_id),
  INDEX movimiento_articulo_FK1(comprobante_movimiento_id),
  FOREIGN KEY(articulo_id)
    REFERENCES inventario.articulos(articulo_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(comprobante_movimiento_id)
    REFERENCES inventario.comprobante_movimiento_articulo(comprobante_movimiento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(resumen_saldo_articulo_id)
    REFERENCES inventario.resumen_saldo_articulos(resumen_saldo_articulo_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(tarea_id, proyecto_id)
    REFERENCES proyectos.tareas_proyecto(tarea_id, proyecto_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(centro_costo_id)
    REFERENCES inventario.centro_costo(centro_costo_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;