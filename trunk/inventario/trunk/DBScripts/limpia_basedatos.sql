delete from infraestructura.atributos_entidad where tipo_objeto = 'TABLA' and nombre_objeto = 'solicitudes_compra';
delete from infraestructura.atributos_entidad where tipo_objeto = 'TABLA' and nombre_objeto = 'ordenes_compra';
delete from infraestructura.audita_estados_circuitos where nombre_tabla = 'inventario.solicitudes_compra';
delete from infraestructura.audita_estados_circuitos where nombre_tabla = 'inventario.ordenes_compra';
truncate inventario.instancias_aprobacion;
truncate inventario.detalle_sc;
truncate inventario.solicitudes_compra;
truncate inventario.ordenes_compra;