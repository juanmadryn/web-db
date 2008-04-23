CREATE VIEW `inventario`.`proveedores` AS
SELECT entidad.entidad_id, entidad.codigo, entidad.nombre, entidad.descripcion, entidad.observaciones, entidad.activo, entidad.anulado, rol.desde, rol.hasta, rol.rol FROM infraestructura.entidad_externa entidad INNER JOIN infraestructura.roles_entidad rol ON entidad.entidad_id = rol.entidad_id WHERE rol.rol LIKE 'PROV';