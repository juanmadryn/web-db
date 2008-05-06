-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7060, 'Movimientos','Manejo de movimientos de inventario','MenuMovimientos','MenuInventario'),
  (706010, 'Carga de movimientos','ABM movimientos','AbmComprobantesMovimientoArticulos','MenuMovimientos'),
 (706020, 'Consulta de movimientos','Consulta de movimientos','ConsultaComprobantesMovimientoArticulos','MenuMovimientos');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
  (7060, 1, NULL, NULL),
  (706010, 1, NULL, NULL),
(706020, 1, NULL, NULL);

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;