start transaction;
use infraestructura;

delete from acceso_menu where menu_id = 70;
delete from acceso_menu where menu_id between 7000 and 7999;
delete from acceso_menu where menu_id between 701000 and 709099;

delete from menu where menu_id between 7000 and 7999;
delete from menu where menu_id between 701000 and 709099;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7010, 'Configuración','Configuración de Inventarios','ConfigurarInventario','MenuInventario'),
  (701010, 'Atributos','ABMC de Atributos para Artículos','AbmcAtributoArticulo','ConfigurarInventario'),
  (701020, 'Tipos de Movimiento','ABMC de Tipo de Movimientos de Artículos','AbmcTipoMovimientoArticulo','ConfigurarInventario'),
  (701030, 'Clases de Articulo','ABMC Clase de Articulo','AbmcClaseArticulo','ConfigurarInventario'),
  (701040, 'Almacenes','ABMC de Almacenes','AbmcAlmacenes','ConfigurarInventario'),
  (701050, 'Centros de Costo','ABMC de Centro de Costos','AbmcCentroCosto','ConfigurarInventario'),
  (701060, 'Cadenas de aprobación','Configuración de cadenas de aprobación','AbmcCadenasAprobacion','ConfigurarInventario'),
  (701070, 'Unidades de medida','Configuración de unidades de medida','AbmcUnidadesMedida','ConfigurarInventario'),

  (7020, 'Articulos','Menú de Articulos','MenuArticulos','MenuInventario'),
  (702010, 'Carga de Articulos','ABMC de Articulo','AbmcArticulo','MenuArticulos'),
  (702020, 'Consulta Artículos','Consulta de Artículos','ConsultaArticulos','MenuArticulos'),

  (7030, 'Solicitudes de Compra','Menú de Solicitudes de Compra','MenuSolicitudes','MenuInventario'),
  (703010, 'Carga de SCs','ABMC de Solicitudes de Compra','AbmcSolicitudCompra','MenuSolicitudes'),
  (703020, 'Consulta de SCs','Consulta de Solicitudes de Compra','ConsultaSolicitudCompra','MenuSolicitudes'),

  (7040, 'Ordenes de Compra','Menú de Ordenes de Compra','MenuOrdenes','MenuInventario'),
  (704010, 'Generar OCs','Generar Ordenes Compra','GenerarOrdenesCompra','MenuOrdenes'),
  (704020, 'Editar OCs','Editar Ordenes Compra','EditarOrdenCompra','MenuOrdenes'),
  (704030, 'Consultar OCs','Consultar Ordenes Compra','ConsultaOrdenesCompra','MenuOrdenes');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
(70, 1, NULL, NULL),
(7010, 1, NULL, NULL),
(701010, 1, NULL, NULL),
(701020, 1, NULL, NULL),
(701030, 1, NULL, NULL),
(701040, 1, NULL, NULL),
(701050, 1, NULL, NULL),
(701060, 1, NULL, NULL),
(701070, 1, NULL, NULL),
(7020, 1, NULL, NULL),
(702010, 1, NULL, NULL),
(702020, 1, NULL, NULL),
(7030, 1, NULL, NULL),
(703010, 1, NULL, NULL),
(703020, 1, NULL, NULL),
(7040, 1, NULL, NULL),
(704010, 1, NULL, NULL),
(704020, 1, NULL, NULL),
(704030, 1, NULL, NULL),
(70, 5, NULL, NULL),
(7010, 5, NULL, NULL),
(701010, 5, NULL, NULL),
(701020, 5, NULL, NULL),
(701030, 5, NULL, NULL),
(701040, 5, NULL, NULL),
(701050, 5, NULL, NULL),
(701060, 5, NULL, NULL),
(701070, 5, NULL, NULL),
(7020, 5, NULL, NULL),
(702010, 5, NULL, NULL),
(702020, 5, NULL, NULL),
(7030, 5, NULL, NULL),
(703010, 5, NULL, NULL),
(703020, 5, NULL, NULL),
(7040, 5, NULL, NULL),
(704010, 5, NULL, NULL),
(704020, 5, NULL, NULL),
(704030, 5, NULL, NULL),
(70, 6, NULL, NULL),
(7010, 6, NULL, NULL),
(701010, 6, NULL, NULL),
(701020, 6, NULL, NULL),
(701030, 6, NULL, NULL),
(701040, 6, NULL, NULL),
(701050, 6, NULL, NULL),
(701060, 6, NULL, NULL),
(701070, 6, NULL, NULL),
(7020, 6, NULL, NULL),
(702010, 6, NULL, NULL),
(702020, 6, NULL, NULL),
(7030, 6, NULL, NULL),
(703010, 6, NULL, NULL),
(703020, 6, NULL, NULL),
(7040, 6, NULL, NULL),
(704010, 6, NULL, NULL),
(704020, 6, NULL, NULL),
(704030, 6, NULL, NULL);

-- rollback;
 commit;