USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
 	(6060, 'Reportes','Reportes de Partes de Equipos','MenuReportesPartesEq','MenuPartesEQ');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
	(6060, 1, NULL, NULL);
