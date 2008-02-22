USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (700506, 'Clase de Articulo','ABMC Clase de Articulo','AbmcClaseArticulo','ConfigurarInventario'),
  (700530, 'Almacenes','ABMC de Almacenes','AbmcAlmacenes','ConfigurarInventario'); 

UPDATE acceso_menu SET menu_id = 700506 WHERE menu_id = 7010;
UPDATE acceso_menu SET menu_id = 700530 WHERE menu_id = 7040;

DELETE FROM menu WHERE menu_id = 7010;
DELETE FROM menu WHERE menu_id = 7040;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;