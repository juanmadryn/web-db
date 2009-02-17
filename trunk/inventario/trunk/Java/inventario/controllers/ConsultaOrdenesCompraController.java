//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.utils.BusquedaPorAtributo;
import infraestructura.utils.Utilities;
import inventario.models.OrdenesCompraModel;

import java.sql.SQLException;
import java.util.Hashtable;

import proyectos.models.ProyectoModel;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.sql.DataStoreException;

/**
 * ConsultaOrdenesCompraController: a SOFIA generated controller
 */
public class ConsultaOrdenesCompraController extends BaseController implements
		SubmitListener, PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8521584275839861805L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _comprador2;
	public com.salmonllc.html.HtmlDropDownList _estado2;
	public com.salmonllc.html.HtmlText _clienteCAP5;
	public com.salmonllc.html.HtmlText _comprador1;
	public com.salmonllc.html.HtmlText _descripcion1;
	public com.salmonllc.html.HtmlText _descripcion2;
	public com.salmonllc.html.HtmlText _descripcionCAP4;
	public com.salmonllc.html.HtmlText _descripcionTXT2;
	public com.salmonllc.html.HtmlText _editar;
	public com.salmonllc.html.HtmlText _estado1;
	public com.salmonllc.html.HtmlText _estadoCAP5;
	public com.salmonllc.html.HtmlText _estadoTXT3;
	public com.salmonllc.html.HtmlText _fecha1;
	public com.salmonllc.html.HtmlText _fecha2;
	public com.salmonllc.html.HtmlText _fecha_entrega_completa1;
	public com.salmonllc.html.HtmlText _fecha_entrega_completa2;
	public com.salmonllc.html.HtmlText _fecha_estimada_entrega1;
	public com.salmonllc.html.HtmlText _fecha_estimada_entrega2;
	public com.salmonllc.html.HtmlText _fechaCAP5;
	public com.salmonllc.html.HtmlText _fechadesde1;
	public com.salmonllc.html.HtmlText _fechahasta1;
	public com.salmonllc.html.HtmlText _fechaTXT4;
	public com.salmonllc.html.HtmlText _n1;
	public com.salmonllc.html.HtmlText _nroOrdenTango1;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador1;
	public com.salmonllc.html.HtmlText _nombre_completo_comprador2;
	public com.salmonllc.html.HtmlText _numero1;
	public com.salmonllc.html.HtmlText _numero2;
	public com.salmonllc.html.HtmlText _numeroCAP2;
	public com.salmonllc.html.HtmlText _observacion1;
	public com.salmonllc.html.HtmlText _observacion2;
	public com.salmonllc.html.HtmlText _proyectoTXT1;
	public com.salmonllc.html.HtmlText _solicitante_nombreTXT3;
	public com.salmonllc.html.HtmlTextEdit _fechadesde2;
	public com.salmonllc.html.HtmlTextEdit _fechahasta2;
	public com.salmonllc.html.HtmlTextEdit _n2;
	public com.salmonllc.html.HtmlTextEdit _nroOrdenTango2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspLink _lnksolicitud1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTable _table2;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.html.HtmlDropDownList _solicitante2;
	public com.salmonllc.html.HtmlLookUpComponent _proveedor2;
	public com.salmonllc.jsp.JspTableRow _nroOcTangoTr;
	public com.salmonllc.html.HtmlText _numeroTango1;
	public com.salmonllc.html.HtmlText _numeroTango2;
	public com.salmonllc.jsp.JspLink _lnkRecepciones1;
	public com.salmonllc.jsp.JspLink _lnkReporteRecepciones;
	public com.salmonllc.html.HtmlLookUpComponent _proyecto2;
	public com.salmonllc.jsp.JspLink _imprimirReporteBUT2;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.OrdenesCompraModel _dsOrdenes;

	// DataSource Column Constants
	public static final String DSQBE_NROOC = "nroOc";
	public static final String DSQBE_DESDE = "desde";
	public static final String DSQBE_HASTA = "hasta";
	public static final String DSQBE_ESTADO = "estado";
	public static final String DSQBE_COMPRADOR = "comprador";

	public static final String DSORDENES_ORDENES_COMPRA_ORDEN_COMPRA_ID = "ordenes_compra.orden_compra_id";
	public static final String DSORDENES_ORDENES_COMPRA_ENTIDAD_ID_PROVEEDOR = "ordenes_compra.entidad_id_proveedor";
	public static final String DSORDENES_ORDENES_COMPRA_USER_ID_COMPRADOR = "ordenes_compra.user_id_comprador";
	public static final String DSORDENES_ORDENES_COMPRA_ESTADO = "ordenes_compra.estado";
	public static final String DSORDENES_ORDENES_COMPRA_FECHA = "ordenes_compra.fecha";
	public static final String DSORDENES_ORDENES_COMPRA_FECHA_ESTIMADA_ENTREGA = "ordenes_compra.fecha_estimada_entrega";
	public static final String DSORDENES_ORDENES_COMPRA_FECHA_ENTREGA_COMPLETA = "ordenes_compra.fecha_entrega_completa";
	public static final String DSORDENES_ORDENES_COMPRA_DESCRIPCION = "ordenes_compra.descripcion";
	public static final String DSORDENES_ORDENES_COMPRA_OBSERVACIONES = "ordenes_compra.observaciones";
	public static final String DSORDENES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSORDENES_NOMBRE_COMPLETO_COMPRADOR = "nombre_completo_comprador";
	public static final String DSORDENES_ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";
	public static final String DSORDENES_ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";
	public static final String DSORDENES_SOLICITUDES_COMPRA_FECHA_APROBACION = "solicitudes_compra.fecha_aprobacion";
	public static final String DSORDENES_WEBSITE_USER_USER_ID = "website_user.user_id";
	public static final String DSORDENES_ESQUEMA_CONFIGURACION_ID = "esquema_configuracion_id";
	public static final String DSORDENES_OBSERVACIONES = "observaciones";
	public static final String DSORDENES_TOTAL_ORDEN_COMPRA = "total_orden_compra";

	// custom
	public com.salmonllc.html.HtmlSubmitButton _recuperaOrdenesPendientes;
	public com.salmonllc.html.HtmlSubmitButton _recuperaOrdenesObservadas;
	public com.salmonllc.html.HtmlSubmitButton _buscarBUT;
	public com.salmonllc.html.HtmlSubmitButton _limpiarBUT;

	public static final int MODO_TITULO_ESPECIAL_PENDIENTES = 0;
	public static final int MODO_TITULO_ESPECIAL_OBSERVADAS = 1;
	public static final int MODO_TITULO_NORMAL = 2;

	private static Integer N_ORDEN_CO_PROP = 19;

	private int _modoBandeja;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		_recuperaOrdenesPendientes = new HtmlSubmitButton(
				"recuperaOrdenesPendientes", "OCs pendientes de aprobación", this);
		_recuperaOrdenesPendientes.setAccessKey("R");
		_listformdisplaybox1.addButton(_recuperaOrdenesPendientes);
		_recuperaOrdenesPendientes.addSubmitListener(this);
		_recuperaOrdenesPendientes.setVisible(false);

		_recuperaOrdenesObservadas = new HtmlSubmitButton(
				"recuperaSolicitudesObservadas", "OC's Observadas", this);
		_recuperaOrdenesObservadas.setAccessKey("O");
		_listformdisplaybox1.addButton(_recuperaOrdenesObservadas);
		_recuperaOrdenesObservadas.addSubmitListener(this);
		_recuperaOrdenesObservadas.setVisible(false);

		_buscarBUT = new HtmlSubmitButton("buscarBUT", "Buscar", this);
		_buscarBUT.setAccessKey("B");
		_searchformdisplaybox1.addButton(_buscarBUT);

		_limpiarBUT = new HtmlSubmitButton("limpiarBUT", "Limpiar", this);
		_limpiarBUT.setAccessKey("L");
		_searchformdisplaybox1.addButton(_limpiarBUT);

		_buscarBUT.addSubmitListener(this);
		_limpiarBUT.addSubmitListener(this);

		_dsOrdenes.setDistinct(true);
		
		_modoBandeja = MODO_TITULO_NORMAL;

		_n2.setFocus();

		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// TODO Auto-generated method stub

		setListFormTitle(MODO_TITULO_NORMAL);

		int userId = getSessionManager().getWebSiteUser().getUserID();

		if (e.getComponent() == _recuperaOrdenesPendientes) {
			try {
				retrieveOrdenesCompraPendientesAprobacion(userId);
				setListFormTitle(MODO_TITULO_ESPECIAL_PENDIENTES);
			} catch (SQLException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			}
		}

		if (e.getComponent() == _recuperaOrdenesObservadas) {
			try {
				retrieveOrdenesCompraPendientesObservacion(userId);
				setListFormTitle(MODO_TITULO_ESPECIAL_OBSERVADAS);
			} catch (SQLException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				ex.printStackTrace();
			}
		}

		// bùsqueda
		if (e.getComponent() == _buscarBUT) {
			try {
				// create the sql where clause
				recuperarOrdenesDeCompra();
				if (_dsOrdenes.getRowCount() == 0) {
					displayErrorMessage("No se encontraron OCs con estas condiciones");
				}
			} catch (Exception ex) {
				displayErrorMessage("Error: " + ex.getMessage());
			}
		}

		// limpia los criterios
		if (e.getComponent() == _limpiarBUT) {
			_n2.setValue(null);
			_nroOrdenTango2.setValue(null);
			_estado2.setSelectedIndex(0);
			_proveedor2.setValue(null);
			_fechadesde2.setValue(null);
			_fechahasta2.setValue(null);
			_comprador2.setSelectedIndex(0);
			_solicitante2.setSelectedIndex(0);
			_proyecto2.setValue(null);
		}

		return super.submitPerformed(e);
	}

	/**
	 * Recupera OCs que cumplan con las caracteristicas indicadas. Hace foco en
	 * el primer registro.
	 * 
	 * @throws DataStoreException
	 * @throws SQLException
	 * 
	 */
	private void recuperarOrdenesDeCompra() throws SQLException,
			DataStoreException {
		_dsOrdenes.reset();
		_dsOrdenes.setOrderBy(OrdenesCompraModel.ORDENES_COMPRA_FECHA + " desc");
		_dsOrdenes.retrieve(armarCriterio());
		_dsOrdenes.gotoFirst();
	}

	/**
	 * Arma la clausula WHERE para la bùsqueda de OC.
	 * 
	 * @return Una clausula WHERE para usar en una operacion retrieve
	 * @throws SQLException
	 * @throws DataStoreException
	 */
	private String armarCriterio() throws SQLException, DataStoreException {
		StringBuilder sb = new StringBuilder(500);

		// recuperamos OC segun solicitante
		if (_solicitante2.getSelectedIndex() > 0) {
			int userId = Integer.valueOf(_solicitante2.getOptionKey(_solicitante2
					.getSelectedIndex()));
			sb
					.append(
							"(ordenes_compra.orden_compra_id IN ( "
									+ " SELECT d.orden_compra_id FROM detalle_sc d "
									+ " JOIN solicitudes_compra s ON s.solicitud_compra_id = d.solicitud_compra_id "
									+ " WHERE s.user_id_solicita = ").append(userId)
					.append("))");
		}

		// Atributo Nro. de OC Tango
		if (_nroOrdenTango2.getValue() != null) {
			Hashtable<Integer, String> atributos = new Hashtable<Integer, String>();
			String nroOcTango = " 00010000".concat(_nroOrdenTango2.getValue());
			atributos.put(N_ORDEN_CO_PROP, nroOcTango);

			String criterioAtributos = BusquedaPorAtributo
					.armarBusquedaPorAtributos(atributos,
							BusquedaPorAtributo.OPERATOR_OR, "ordenes_compra",
							"orden_compra_id");

			if (criterioAtributos.length() > 0) {
				if (sb.length() > 0)
					sb.append(" and ");
				sb.append(
						OrdenesCompraModel.ORDENES_COMPRA_ORDEN_COMPRA_ID + " IN ( ")
						.append(criterioAtributos).append(" )");
			}
		}

		// resto de los criterios especificados por el usuario
		String sqlFilter = _dsQBE.generateSQLFilter(_dsOrdenes);
		if (sqlFilter != null) {
			if (sb.length() > 0)
				sb.append(" and ");
			sb.append(sqlFilter);
		}

		if (_proyecto2.getValue() != null
				&& _proyecto2.getValue().trim().length() > 0) {
			ProyectoModel dsProyecto = new ProyectoModel("proyectos", "proyectos");
			dsProyecto.retrieve("proyecto = '" + _proyecto2.getValue() + "'");
			if (dsProyecto.gotoFirst()) {
				if (sb.length() > 0)
					sb.append(" and ");
				sb.append("solicitudes_compra.proyecto_id ="
						+ dsProyecto.getProyectosProyectoId());
			} else
				throw new DataStoreException("El proyecto indicado no existe");

		}

		return sb.toString();
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *           the page event to be processed
	 * @throws Exception
	 */
	public void pageRequested(PageEvent event) throws Exception {
		// si la página es requerida por si misma no hago nada
		if (!isReferredByCurrentPage()) {
			int userId = getIntParameter("user_id");
			int mode = getIntParameter("mode");
			String orden_compra_id = ""+getIntParameter("orden_compra_id");
			
			if ((userId > 0)) {
				// verifica si cambión el contexto
				try {
					switch (mode) {
					case MODO_TITULO_ESPECIAL_PENDIENTES:
						retrieveOrdenesCompraPendientesAprobacion(userId);
						setListFormTitle(MODO_TITULO_ESPECIAL_PENDIENTES);
						break;
					case MODO_TITULO_ESPECIAL_OBSERVADAS:
						retrieveOrdenesCompraPendientesObservacion(userId);
						setListFormTitle(MODO_TITULO_ESPECIAL_OBSERVADAS);
						break;
					}
				} catch (SQLException e) {
					displayErrorMessage(e.getMessage());
					e.printStackTrace();
				} catch (DataStoreException e) {
					displayErrorMessage(e.getMessage());
					e.printStackTrace();
				}
			} else {
				// actualizo el estado de los OCs visibles
				for (int row = 0; row < _dsOrdenes.getRowCount(); row++) {
					_dsOrdenes.reloadRow(row);
				}
				setListFormTitle(MODO_TITULO_NORMAL);
			}
			if (orden_compra_id.length() > 0) {
				_dsOrdenes.retrieve("ordenes_compra.orden_compra_id = "+orden_compra_id);
				_dsOrdenes.gotoFirst();
			}
			
		}

		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		_recuperaOrdenesPendientes.setVisible(Utilities
				.getOrdenesCompraPendientesAprobacion(currentUser) > 0 ? true
				: false);

		_recuperaOrdenesObservadas.setVisible(Utilities
				.getOrdenesCompraPendientesObservacion(currentUser) > 0 ? true
				: false);

		// Si el usuario no es comprador, solo puede consultar las solicitudes
		// realizadas por él
		if (!UsuarioRolesModel.isRolUsuario(currentUser, USER_COMPRADOR) && !UsuarioRolesModel.isRolUsuario(currentUser, USER_ENCARGADO_ALMACEN)) {
			_comprador2.setEnabled(false);
			_dsQBE.setString("comprador", String.valueOf(currentUser));
		}

		// Si la OC esta emitida mostrar número de OC en tango y links para
		// recepciones
		if ((_dsOrdenes.getRow() > -1)
				&& "0008.0006"
						.equalsIgnoreCase(_dsOrdenes.getOrdenesCompraEstado())) {
			// Nro. OC Tango
			_numeroTango2.setText(AtributosEntidadModel.getValorAtributoObjeto(
					N_ORDEN_CO, _dsOrdenes.getOrdenesCompraOrdenCompraId(), "TABLA",
					"ordenes_compra"));
			_nroOcTangoTr.setVisible(true);
			// Links recepciones
			_lnkRecepciones1.setVisible(true);
			_lnkReporteRecepciones.setVisible(true);
			_imprimirReporteBUT2.setVisible(true);

			// setea la URL del reporte a generar al presionar el botón de
			// impresión
			String URL = armarUrlReporte("PDF", "resumen_recepciones_oc",
					"&param_orden_compra_id="
							+ _dsOrdenes.getOrdenesCompraOrdenCompraId());
			_lnkReporteRecepciones.setHref(URL);
			
			URL = armarUrlReporte("PDF", "resumen_pendientes_oc", "&param_orden_compra_id="+_dsOrdenes.getOrdenesCompraOrdenCompraId());
			_imprimirReporteBUT2.setHref(URL);
		} else {
			_nroOcTangoTr.setVisible(false);
			_lnkRecepciones1.setVisible(false);
			_lnkReporteRecepciones.setVisible(false);
			_imprimirReporteBUT2.setVisible(false);
		}

		super.pageRequested(event);
	}

	/**
	 * Cambia título de lista de ordenes segùn criterio de visualización de la
	 * bandeja
	 * 
	 * @param modo
	 */
	private void setListFormTitle(int modo) {
		_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingSpecialFont");
		switch (modo) {
		case MODO_TITULO_ESPECIAL_PENDIENTES:
			_listformdisplaybox1
					.setHeadingCaption("Ordenes de compra pendientes de aprobación");
			_modoBandeja = MODO_TITULO_ESPECIAL_PENDIENTES;
			break;
		case MODO_TITULO_ESPECIAL_OBSERVADAS:
			_listformdisplaybox1
					.setHeadingCaption("Ordenes de compra pendientes de observación");
			_modoBandeja = MODO_TITULO_ESPECIAL_OBSERVADAS;
			break;
		default:
			_listformdisplaybox1.setHeadingCaption("Ordenes de compra");
			_listformdisplaybox1.setHeaderFont("DisplayBoxHeadingFont");
			_modoBandeja = MODO_TITULO_NORMAL;
			break;
		}
	}

	/**
	 * Recupera OCs con aprobacion pendiente por parte del usuario especificado
	 * 
	 * @param userId
	 *           usuario firmante
	 * @throws SQLException
	 * @throws DataStoreException
	 */
	private void retrieveOrdenesCompraPendientesAprobacion(int userId)
			throws SQLException, DataStoreException {
		_dsOrdenes.retrieve("ordenes_compra.orden_compra_id IN "
				+ "(SELECT objeto_id FROM inventario.instancias_aprobacion i "
				+ "WHERE i.estado LIKE '0007.0001' and i.user_firmante = " + userId
				+ " AND nombre_objeto='ordenes_compra') "
				+ "AND ordenes_compra.estado LIKE '0008.0002'");
		_dsOrdenes.waitForRetrieve();
		_dsOrdenes.gotoFirst();
	}

	/**
	 * Recupera OCs del usuario especificado que hayan sido observadas en el
	 * proceso de aprobaciòn
	 * 
	 * @param userId
	 *           usuario creador de las OCs
	 * @throws SQLException
	 * @throws DataStoreException
	 */
	private void retrieveOrdenesCompraPendientesObservacion(int userId)
			throws SQLException, DataStoreException {
		_dsOrdenes
				.retrieve("ordenes_compra.estado LIKE '0008.0005' AND user_id_comprador = "
						+ userId);
		_dsOrdenes.waitForRetrieve();
		_dsOrdenes.gotoFirst();
	}

}
