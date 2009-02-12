INSERT INTO `infraestructura`.`menu` (menu_id, nombre, descripcion, url, grupo) VALUES 
(80,'Gerencia','M�dulo de informaci�n gerencial','MenuGerencia','HomePage'),
(8010,'Tendencia de Compra','Generaci�n de reportes de tendencia de compra','ReporteTendenciaCompra','MenuGerencia'),
(8020,'Compras por proyecto','Generaci�n de reporte de compras por proyecto','ReporteComprasProyecto','MenuGerencia'),
(8030,'Compras por art�culo','Generaci�n de reporte de compras por art�culo','ReporteComprasArticulo','MenuGerencia'),
(8040,'Detalle de compras','Generaci�n de reporte de compras detallado','ReporteCompras','MenuGerencia'),
(8050,'Ranking de compradores','Generaci�n de reporte de ranking de compradores','ReporteRankingCompradores','MenuGerencia');

INSERT INTO `infraestructura`.`acceso_menu` (menu_id, rol_id, user_id, acceso_menu_id) VALUES 
(80,1,null,null),
(8010,1,null,null),
(8020,1,null,null),
(8030,1,null,null),
(8040,1,null,null),
(8050,1,null,null);