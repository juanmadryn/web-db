-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (1040, 'Administra Tango','Administra Tango','MenuTango','Administracion'),
  (104010, 'Importa Legajos','Consulta e importa legajos desde Tango','ConsultaLegajos','MenuTango');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
  (1040, 1, NULL, NULL),
  (104010, 1, NULL, NULL);
  
ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;