-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (70, 'Inventario','Control de Inventario','MenuInventario','HomePage');
INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7010, 'Clase de Articulo','ABMC Clase de Articulo','AbmcClaseArticulo','MenuInventario');
INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7020, 'Carga de Articulos','ABMC de Articulo','AbmcArticulo','MenuInventario');
INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7030, 'Consulta Artículos','Consulta de Artículos','ConsultaArticulos','MenuInventario');
INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7005, 'Configuración','Configuración de Inventarios','ConfigurarInventario','MenuInventario');
INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (700505, 'Atributos','ABMC de Atributos para Artículos','AbmcAtributoArticulo','ConfigurarInventario');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
  (70, 1, NULL, 95),
  (7010, 1, NULL, 96),
  (7020, 1, NULL, 97),
  (7030, 1, NULL, 98),
  (7005, 1, NULL, 99),
  (700505, 1, NULL, 100);

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;