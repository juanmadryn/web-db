-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (104020, 'Importa Compradores','Consulta e importa compradores desde Tango','ConsultaCompradores','MenuTango');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
  (104020, 1, NULL, NULL);
  
ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;
