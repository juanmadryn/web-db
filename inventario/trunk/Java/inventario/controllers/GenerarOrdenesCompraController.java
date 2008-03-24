//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.OrdenesCompraModel;
import inventario.models.SolicitudCompraModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Iterator;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.SalmonDateFormat;

/**
 * GenerarOrdenesCompraController: a SOFIA generated controller
 */
public class GenerarOrdenesCompraController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410350530842061433L;
	//Visual Components
	public com.salmonllc.html.HtmlCheckBox _selSolicitudCB;
	public com.salmonllc.html.HtmlImage _bannerDividerImage;
	public com.salmonllc.html.HtmlImage _bannerDivImage2;
	public com.salmonllc.html.HtmlImage _imgMainLogo;
	public com.salmonllc.html.HtmlText _articuloCAP3;
	public com.salmonllc.html.HtmlText _articuloDescCAP4;
	public com.salmonllc.html.HtmlText _articuloDescTXT3;
	public com.salmonllc.html.HtmlText _articuloClaseTXT3;
	public com.salmonllc.html.HtmlText _articuloTXT2;
	public com.salmonllc.html.HtmlText _cantPedidaCAP9;
	public com.salmonllc.html.HtmlText _cantidadSolicitadaCAP5;
	public com.salmonllc.html.HtmlText _articuloClaseCAP12;
	public com.salmonllc.html.HtmlText _cantidadSolicitadaTXT4;
	public com.salmonllc.html.HtmlText _monto_fecha_ultima_compra2;
	public com.salmonllc.html.HtmlText _montoUnitCAP10;
	public com.salmonllc.html.HtmlText _ordenCompraCAP5;
	public com.salmonllc.html.HtmlText _proyectoCAP7;
	public com.salmonllc.html.HtmlText _proyectoTXT6;
	public com.salmonllc.html.HtmlText _selSolicitudCAP1;
	public com.salmonllc.html.HtmlText _tareaCAP8;
	public com.salmonllc.html.HtmlText _tareaTXT7;
	public com.salmonllc.html.HtmlText _centroCostoCAP11;
	public com.salmonllc.html.HtmlText _centroCostoTXT8;	
	public com.salmonllc.html.HtmlText _text1Footer;
	public com.salmonllc.html.HtmlText _text2Footer;
	public com.salmonllc.html.HtmlText _text3Footer;
	public com.salmonllc.html.HtmlText _txtBannerOptions;
	public com.salmonllc.html.HtmlText _welcomeText;
	public com.salmonllc.html.HtmlTextEdit _cantPedidaINP8;
	public com.salmonllc.html.HtmlTextEdit _montoUnitINP9;
	public com.salmonllc.html.HtmlTextEdit _valorAttr1;
	public com.salmonllc.html.HtmlTextEdit _valorAttr2;
	public com.salmonllc.html.HtmlTextEdit _valorAttr3;
	public com.salmonllc.html.HtmlLookUpComponent _ordenCompraINP5;
	public com.salmonllc.html.HtmlLookUpComponent _lkpAttrINP1;
	public com.salmonllc.html.HtmlLookUpComponent _lkpAttrINP2;
	public com.salmonllc.html.HtmlLookUpComponent _lkpAttrINP3;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspContainer _welcomeContainer;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _bannerForm;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspLink _baseLinkAdminSalmon;
	public com.salmonllc.jsp.JspLink _footerInfDevAbout;
	public com.salmonllc.jsp.JspLink _footerproyectosHelp;
	public com.salmonllc.jsp.JspLink _footerSalmonLink;
	public com.salmonllc.jsp.JspLink _footerSofiaLink;
	public com.salmonllc.jsp.JspLink _lnkBannerOptions;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader10;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader6;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader7;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader8;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader9;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow10;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow6;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow7;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow8;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow9;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader1;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow1;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;
	
	//Custom components
	public com.salmonllc.html.HtmlSubmitButton _seleccionaTodoBUT1;
	public com.salmonllc.html.HtmlSubmitButton _desSeleccionaTodoBUT2;
	public com.salmonllc.html.HtmlSubmitButton _generaOcsBUT3;
	public com.salmonllc.html.HtmlSubmitButton _buscarBUT;
	public com.salmonllc.html.HtmlSubmitButton _limpiarBUT;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.DetalleSCModel _dsDetalleSC;
	public inventario.models.OrdenesCompraModel _dsOrdenCompra;

	//DataSource Column Constants	
	public static final String DSQBE_N = "n";
	
	public static final String DSDETALLESC_DETALLE_SC_DETALLE_SC_ID = "detalle_sc.detalle_SC_id";
	public static final String DSDETALLESC_DETALLE_SC_RECEPCION_COMPRA_ID = "detalle_sc.recepcion_compra_id";
	public static final String DSDETALLESC_DETALLE_SC_ORDEN_COMPRA_ID = "detalle_sc.orden_compra_id";
	public static final String DSDETALLESC_DETALLE_SC_ARTICULO_ID = "detalle_sc.articulo_id";
	public static final String DSDETALLESC_DETALLE_SC_SOLICITUD_COMPRA_ID = "detalle_sc.solicitud_compra_id";
	public static final String DSDETALLESC_DETALLE_SC_CANTIDAD_SOLICITADA = "detalle_sc.cantidad_solicitada";
	public static final String DSDETALLESC_DETALLE_SC_CANTIDAD_PEDIDA = "detalle_sc.cantidad_pedida";
	public static final String DSDETALLESC_DETALLE_SC_CANTIDAD_RECIBIDA = "detalle_sc.cantidad_recibida";
	public static final String DSDETALLESC_DETALLE_SC_DESCRIPCION = "detalle_sc.descripcion";
	public static final String DSDETALLESC_DETALLE_SC_OBSERVACIONES = "detalle_sc.observaciones";
	public static final String DSDETALLESC_ARTICULOS_CLASE_ARTICULO_ID = "articulos.clase_articulo_id";
	public static final String DSDETALLESC_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSDETALLESC_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSDETALLESC_CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String DSDETALLESC_CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";
	public static final String DSDETALLESC_DETALLE_SC_TAREA_ID = "detalle_sc.tarea_id";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_UNITARIO = "detalle_sc.monto_unitario";
	public static final String DSDETALLESC_DETALLE_SC_MONTO_ULTIMA_COMPRA = "detalle_sc.monto_ultima_compra";
	public static final String DSDETALLESC_DETALLE_SC_FECHA_ULTIMA_COMPRA = "detalle_sc.fecha_ultima_compra";
	public static final String DSDETALLESC_TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";
	public static final String DSDETALLESC_SOLICITUDES_COMPRA_ESTADO = "solicitudes_compra.estado";
	public static final String DSDETALLESC_MONTO_TOTAL = "monto_total";
	
	public static final String SELECCION_DETALLE_SC_FLAG = "detalle_sc.SELECCION_DETALLE_SC_FLAG";

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		// find button
		_buscarBUT = new HtmlSubmitButton("buscarBUT","Buscar",this);
		_buscarBUT.setAccessKey("b");		
		_searchformdisplaybox1.addButton(_buscarBUT);
		
		_limpiarBUT = new HtmlSubmitButton("limpiarBUT","Limpiar",this);
		_limpiarBUT.setAccessKey("b");		
		_searchformdisplaybox1.addButton(_limpiarBUT);
		
		_generaOcsBUT3 = new HtmlSubmitButton("generaOcsBUT3","Genera OCs", this);
		_listformdisplaybox1.addButton(_generaOcsBUT3);
		_seleccionaTodoBUT1 = new HtmlSubmitButton("seleccionaTodoBUT1","Seleccionar todo",this);
		_listformdisplaybox1.addButton(_seleccionaTodoBUT1);		
		_desSeleccionaTodoBUT2 = new HtmlSubmitButton("desSeleccionaTodoBUT2","Deseleccionar",this);
		_listformdisplaybox1.addButton(_desSeleccionaTodoBUT2);
				
		_seleccionaTodoBUT1.addSubmitListener(this);
		_desSeleccionaTodoBUT2.addSubmitListener(this);
		_generaOcsBUT3.addSubmitListener(this);
		_buscarBUT.addSubmitListener(this);
		_limpiarBUT.addSubmitListener(this);
		
		// Agrega columna de seleccion al datasource de resumen de horas
		_dsDetalleSC.addBucket(SELECCION_DETALLE_SC_FLAG, DataStore.DATATYPE_INT);
		_selSolicitudCB.setColumn(_dsDetalleSC, SELECCION_DETALLE_SC_FLAG);
		_selSolicitudCB.setFalseValue(null);
	}
	
	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		DBConnection conexion = null;
		
		// clear button
		if (e.getComponent() == _limpiarBUT) {
			_lkpAttrINP1.setValue(null);
			_lkpAttrINP2.setValue(null);
			_lkpAttrINP3.setValue(null);
			_valorAttr1.setValue(null);
			_valorAttr2.setValue(null);
			_valorAttr3.setValue(null);
		}
		
		// find button
		if (e.getComponent() == _buscarBUT) {
			try {
				// create the sql where clause
				recuperarArticulosParaComprar();
				if (_dsDetalleSC.getRowCount() > 0) {
				} else {
					displayErrorMessage("No hay artículos para comprar con estas condiciones");
				}
			} catch (Exception ex) {
				displayErrorMessage("Error: " + ex.getMessage());
			}			
		}
		
		// marca todos los partes del datasource como seleccionados
		if (e.getComponent() == _seleccionaTodoBUT1) {
			for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
				_dsDetalleSC.setInt(i, SELECCION_DETALLE_SC_FLAG,1);
			}
		}
		
		// desmarca todos los partes del datasource como seleccionados
		if (e.getComponent() == _desSeleccionaTodoBUT2) {
			for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
				_dsDetalleSC.setInt(i, SELECCION_DETALLE_SC_FLAG,0);
			}
		}
	
		// genera ordenes de compra
		if (e.getComponent() == _generaOcsBUT3) {
			try {
				_dsDetalleSC.filter(SELECCION_DETALLE_SC_FLAG + " != null");

				if (_dsDetalleSC.getRowCount() <= 0) {
					displayErrorMessage("Debe seleccionar al menos un artículo");
					return false;
				}
				
				// start the transaction
				conexion = DBConnection.getConnection(getApplicationName(),"inventario");
				conexion.beginTransaction();
				
				_dsDetalleSC.setFindExpression("detalle_sc.orden_compra_id == null");
				
				if (_dsDetalleSC.findFirst()) {
					_dsOrdenCompra = new OrdenesCompraModel("inventario");

					int ocId = _dsOrdenCompra.insertRow();

					_dsOrdenCompra.setOrdenesCompraEstado(ocId, "0008.0001");
					_dsOrdenCompra.setOrdenesCompraEntidadIdProveedor(ocId, 1);
					_dsOrdenCompra.setOrdenesCompraFecha(ocId, new java.sql.Date(System.currentTimeMillis()));
					_dsOrdenCompra.setOrdenesCompraUserIdComprador(ocId, 
							getUserFromSession(getCurrentRequest().getRemoteAddr()).getUserID());
					
					_dsOrdenCompra.update(conexion);
					
					for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
						if (_dsDetalleSC.getDetalleScOrdenCompraId(i) == 0) {
							_dsDetalleSC.setDetalleScOrdenCompraId(i, 
									_dsOrdenCompra.getOrdenesCompraOrdenCompraId(ocId));							
						}
					}					
				}
				
				// update the SC states
				SolicitudCompraModel dsSolicitudCompra = new SolicitudCompraModel("inventario");
				
				for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
					if ("0006.0003".equalsIgnoreCase(_dsDetalleSC.getSolicitudCompraEstado(i))) {
						dsSolicitudCompra.retrieve(
								SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
								" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
								);
						dsSolicitudCompra.gotoFirst();
						dsSolicitudCompra.ejecutaAccion(18,	"0006", 
								this.getCurrentRequest().getRemoteHost(), 
								getSessionManager().getWebSiteUser().getUserID(), 
								"solicitudes_compra", conexion, false);
					}					
				}				
				
				_dsDetalleSC.update(conexion);
				conexion.commit();

				// our daily wtf ...
				conexion.beginTransaction();				
				for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
					if ("0006.0006".equalsIgnoreCase(_dsDetalleSC.getSolicitudCompraEstado(i))) {
						dsSolicitudCompra.retrieve(
								SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID +
								" = " + _dsDetalleSC.getDetalleScSolicitudCompraId(i) 
						);
						dsSolicitudCompra.gotoFirst();
						try {
							dsSolicitudCompra.ejecutaAccion(19,	"0006",
									this.getCurrentRequest().getRemoteHost(), 
									getSessionManager().getWebSiteUser().getUserID(), 
									"solicitudes_compra", conexion, false);	
						} catch (DataStoreException ex) {
							MessageLog.writeErrorMessage(ex, null);
						}														
					}
				}				
				conexion.commit();
				
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (SQLException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (ValidationException ex) {
				MessageLog.writeErrorMessage(ex, null);
				for (String er : ex.getStackErrores()) {
					displayErrorMessage(er);
				}
				return false;
			}
			finally {
				if (conexion != null) {
					conexion.rollback();
					conexion.freeConnection();
				}
			}
		}
		
		return super.submitPerformed(e);
	}
	
	@Override
	public void pageRequested(PageEvent p) throws Exception {
		// set the 'cantidad pedida' field value to 'cantidad solicitada' value 
		for (int i = 0; i < _dsDetalleSC.getRowCount(); i++) {
			_dsDetalleSC.setDetalleScCantidadPedida(i, _dsDetalleSC.getDetalleScCantidadSolicitada(i));
		}		
		super.pageRequested(p);
	}
	
	private void recuperarArticulosParaComprar() throws SQLException, DataStoreException{
		_dsDetalleSC.reset();
		_dsDetalleSC.retrieve(armarCriterio());
		_dsDetalleSC.gotoFirst();
	}	
	
	private String armarCriterio() throws SQLException, DataStoreException {
		StringBuilder sb = new StringBuilder(500);
		
		// only retrieve SCs which are aproved and hasn't got an OCs assigned 
		sb.append(" (solicitudes_compra.estado IN ('0006.0003','0006.0006') and detalle_sc.orden_compra_id is null) ");		
		
		// build where clause with specified attributes if any
		String criterioAtributos = armarBusquedaPorAtributos();
		if (criterioAtributos.length() > 0) {
			sb.append(" and detalle_sc.articulo_id IN ( ").append(criterioAtributos).append(" )");
		}
		
		// add all the selection criterias specified by the user
		String sqlFilter = _dsQBE.generateSQLFilter(_dsDetalleSC);		
		if (sqlFilter != null) {
			sb.append(" and ").append(sqlFilter);
		}
			
		return sb.toString();
	}	
	
	private String armarBusquedaPorAtributos() throws SQLException {
		StringBuilder querySql = new StringBuilder(500);

		// get the attributes 
		Hashtable<Integer,String> atributos = new Hashtable<Integer,String>();	    
		if (_lkpAttrINP1.getValue() != null)	    	
			atributos.put(Integer.parseInt(_lkpAttrINP1.getValue()), _valorAttr1.getValue());	     
		if (_lkpAttrINP2.getValue() != null)	    	
			atributos.put(Integer.parseInt(_lkpAttrINP2.getValue()), _valorAttr2.getValue());
		if (_lkpAttrINP3.getValue() != null)	    	
			atributos.put(Integer.parseInt(_lkpAttrINP3.getValue()), _valorAttr3.getValue());

		if (atributos.size() > 0) {
			querySql.append(" SELECT objeto_id "
					+ " FROM infraestructura.atributos_entidad " 
					+ " WHERE tipo_objeto = 'TABLA' AND nombre_objeto = 'articulos' "
					+ " AND ( ");
			
			Iterator<Integer> i = atributos.keySet().iterator();
			
			while (i.hasNext()) {
				int atributoId = i.next();
				String valorAtributo = atributos.get(atributoId);
					
				String tipoAtributo = AtributosEntidadModel.getTipoAtributo(atributoId);
				String sqlClause = null;

				if ("entero".equalsIgnoreCase(tipoAtributo)) {
					sqlClause = "valor_entero = " + Integer.parseInt(valorAtributo);
				}
				else if ("real".equalsIgnoreCase(tipoAtributo)) {
					sqlClause = "valor_real = " + Float.parseFloat(valorAtributo); 
				}
				else if ("fecha".equalsIgnoreCase(tipoAtributo)) {
					try {
						sqlClause = "valor_fecha = '" + java.sql.Date.valueOf(valorAtributo).toString() + "'";
					} catch (Exception e) {
						SalmonDateFormat sdf = new SalmonDateFormat();
						try {						
							sqlClause = "valor_fecha = '" + 
								new java.sql.Date(sdf.parse((String) valorAtributo).getTime()).toString() + "'" ;
						} catch (ParseException e1) {
							throw new NumberFormatException("Imposible determinar fecha");						
						}
					}				
				}			
				else if ("logico".equalsIgnoreCase(tipoAtributo)) {
					if (valorAtributo.equalsIgnoreCase("V")
							|| valorAtributo.equalsIgnoreCase("F"))
						sqlClause = "valor_logico = '" + valorAtributo + "'";
					else 
						throw new RuntimeException("Debe introducir 'V' para verdadero o 'F' para falso para el atributo");
				}
				else {
					sqlClause = "valor LIKE '%" + valorAtributo + "%'";
				}
			
				querySql.append(sqlClause);				
				if (i.hasNext()) 
					querySql.append(" OR ");
			}					
			querySql.append(" ) ");
		}		
		return querySql.toString();
	}
	
	private int getDatosAtributo(String nombreAtributo) {
		DBConnection conexion = null;
		String SQL = null;
		Statement st = null;
		ResultSet r = null;
		
		
		
		return 0;
	}
}
