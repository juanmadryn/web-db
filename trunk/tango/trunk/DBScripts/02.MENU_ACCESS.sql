-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (1040, 'Administra Tango','AdministraTango,'MenuInventario','HomePage'),
  (104010, 'Atributos','ABMC de Atributos para Artículos','AbmcAtributoArticulo','ConfigurarInventario');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
  (70, 1, NULL, 95),
  (7010, 1, NULL, 96),
  (7020, 1, NULL, 97),
  (7030, 1, NULL, 98),
  (7005, 1, NULL, 99),
  (700505, 1, NULL, 100),  
  (7040, 1, NULL, 101),
  (700510, 1, NULL, 102),
  (700520, 1, NULL, 103);

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;