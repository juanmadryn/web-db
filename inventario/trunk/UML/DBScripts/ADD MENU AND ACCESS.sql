-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (70, 'Inventario','Control de Inventario','MenuInventario','HomePage');
INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7010, 'Clase de Articulo','ABMC Clase de Articulo','AbmcClaseArticulo','MenuInventario');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
  (70, 1, NULL, 95),
  (7010, 1, NULL, 96);

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;