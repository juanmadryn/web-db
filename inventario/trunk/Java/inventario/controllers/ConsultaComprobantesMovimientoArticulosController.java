//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.UsuarioRolesModel;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;

/**
 * ConsultaRecepcionesComprasController: a SOFIA generated controller
 */
public class ConsultaComprobantesMovimientoArticulosController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5233176031862847585L;
	//Visual Components
	public com.salmonllc.html.HtmlDropDownList _estado2;
	public com.salmonllc.html.HtmlDropDownList _solicitante2;
	public com.salmonllc.html.HtmlImage _bannerDividerImage;
	public com.salmonllc.html.HtmlImage _bannerDivImage2;
	public com.salmonllc.html.HtmlImage _imgMainLogo;
	public com.salmonllc.html.HtmlText _clienteCAP5;
	public com.salmonllc.html.HtmlText _comprador_nombreTXT3;
	public com.salmonllc.html.HtmlText _descripcionCAP4;
	public com.salmonllc.html.HtmlText _descripcionTXT2;
	public com.salmonllc.html.HtmlText _editar;
	public com.salmonllc.html.HtmlText _estado1;
	public com.salmonllc.html.HtmlText _estadoCAP5;
	public com.salmonllc.html.HtmlText _estadoTXT3;
	public com.salmonllc.html.HtmlText _fecha_solicitudCAP5;
	public com.salmonllc.html.HtmlText _fecha_solicitudTXT4;
	public com.salmonllc.html.HtmlText _fechadesde1;
	public com.salmonllc.html.HtmlText _fechahasta1;
	public com.salmonllc.html.HtmlText _n1;
	public com.salmonllc.html.HtmlText _nombreCAP3;
	public com.salmonllc.html.HtmlText _numeroCAP2;
	public com.salmonllc.html.HtmlText _proyectoTXT1;
	public com.salmonllc.html.HtmlText _solicitante1;
	public com.salmonllc.html.HtmlText _solicitante_nombreTXT3;
	public com.salmonllc.html.HtmlText _text1Footer;
	public com.salmonllc.html.HtmlText _text2Footer;
	public com.salmonllc.html.HtmlText _text3Footer;
	public com.salmonllc.html.HtmlText _welcomeText;
	public com.salmonllc.html.HtmlTextEdit _fechadesde2;
	public com.salmonllc.html.HtmlTextEdit _fechahasta2;
	public com.salmonllc.html.HtmlTextEdit _n2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspContainer _welcomeContainer;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspLink _baseLinkAdminSalmon;
	public com.salmonllc.jsp.JspLink _footerInfDevAbout;
	public com.salmonllc.jsp.JspLink _footerproyectosHelp;
	public com.salmonllc.jsp.JspLink _footerSalmonLink;
	public com.salmonllc.jsp.JspLink _footerSofiaLink;
	public com.salmonllc.jsp.JspLink _lnkBannerOptions;
	public com.salmonllc.jsp.JspLink _lnksolicitud1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTable _table2;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader6;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow6;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _table2TDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _table2TRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	//DataSources
	public com.salmonllc.sql.DataStore _dsPeriodo;
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.ComprobanteMovimientoArticuloModel _dsComprobantes;

	//DataSource Column Constants
	public static final String DSQBE_N = "n";
	public static final String DSQBE_ESTADO = "estado";
	public static final String DSQBE_USUARIO_COMPLETO = "usuario_completo";

	public static final String DSPERIODO_DESDE = "desde";
	public static final String DSPERIODO_HASTA = "hasta";

	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_RECEPCION_COMPRA_ID = "recepciones_compras.recepcion_compra_id";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_FECHA = "recepciones_compras.fecha";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_ESTADO = "recepciones_compras.estado";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_PROVEEDOR_ID = "recepciones_compras.proveedor_id";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_USER_ID_COMPLETA = "recepciones_compras.user_id_completa";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_USER_ID_RECIBE = "recepciones_compras.user_id_recibe";
	public static final String DSRECEPCIONES_RECEPCIONES_COMPRAS_OBSERVACIONES = "recepciones_compras.observaciones";
	public static final String DSRECEPCIONES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSRECEPCIONES_PROVEEDORES_NOMBRE = "proveedores.nombre";
	public static final String DSRECEPCIONES_USER_COMPLETA_NOMBRE_COMPLETO = "user_completa.nombre_completo";
	public static final String DSRECEPCIONES_USER_RECIBE_NOMBRE_COMPLETO = "user_recibe.nombre_completo";
	public static final String DSRECEPCIONES_WEBSITE_USER_USER_ID = "website_user.user_id";

	public com.salmonllc.html.HtmlSubmitButton _recuperaRecepcionesAnuladas;
	
	private Timestamp desde;
	private Timestamp hasta;
	
	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		_recuperaRecepcionesAnuladas = new HtmlSubmitButton(
				"recuperaSolicitudesPendientes", "Recupera recepción anulada", this);
		_recuperaRecepcionesAnuladas.setAccessKey("R");
		_listformdisplaybox1.addButton(_recuperaRecepcionesAnuladas);
		_recuperaRecepcionesAnuladas.addSubmitListener(this);
		_recuperaRecepcionesAnuladas.setVisible(false);

		_searchformdisplaybox1.getSearchButton().addSubmitListener(this);

		_dsPeriodo.reset();
		_dsPeriodo.insertRow();
		seteaPeriodo(null, null); // valores por defecto para el periodo de
		// fechas
		_dsPeriodo.gotoFirst();

		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// TODO Auto-generated method stub

		if (e.getComponent() == _searchformdisplaybox1.getSearchButton()) {
			String whereFecha = null;
			if (_fechadesde2.getValue() != null && _fechahasta2.getValue() != null) {
				desde = _dsPeriodo.getDateTime("desde");
				hasta = _dsPeriodo.getDateTime("hasta");
				seteaPeriodo(desde, hasta);

				if (desde != null && hasta != null) {
					// chequeo las fechas
					if (hasta.compareTo(desde) < 0) {
						displayErrorMessage("La fecha desde debe ser anterior a la fecha hasta");
						return false;
					}
					whereFecha = "recepciones_compras.fecha BETWEEN '"
							+ desde.toString()
							+ "' AND '"
							+ hasta.toString() + "'";
				}
			}
			_dsComprobantes.reset();
			String where = _dsQBE.generateSQLFilter(_dsComprobantes);
			if (whereFecha != null) {
				if (where != null)
					where += " AND ";
				else
					where = "";
				where += whereFecha;
			} else if (where != null)
				where += "";
			_dsComprobantes.retrieve(where);
			_dsComprobantes.gotoFirst();
		}

		if (e.getComponent() == _recuperaRecepcionesAnuladas) {
			_dsComprobantes.setComprobanteMovimientoArticuloEstado("0010.0001");
			_dsComprobantes.update();
		}

		return super.submitPerformed(e);
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 * @throws Exception
	 */
	public void pageRequested(PageEvent event) throws Exception {
		// si la página es requerida por si misma no hago nada

		int currentUser = getSessionManager().getWebSiteUser().getUserID();

		// Si el usuario no es comprador, solo puede consultar las solicitudes
		// realizadas por él
		if (!UsuarioRolesModel.isRolUsuario(currentUser, "RECEPTOR")) {
			_dsQBE.setString("usuario_completo", String.valueOf(currentUser));
			_solicitante2.setEnabled(false);
		} else {
			_solicitante2.setEnabled(true);
			if (_dsComprobantes.getRow() != -1
					&& "0010.0004".equalsIgnoreCase(_dsComprobantes.getComprobanteMovimientoArticuloEstado()))
				_recuperaRecepcionesAnuladas.setVisible(true);
			else
				_recuperaRecepcionesAnuladas.setVisible(false);
		}
		super.pageRequested(event);
	}

	/**
	 * Setea el periodo por defecto para el rango de fechas
	 * 
	 * @throws DataStoreException
	 */
	public void seteaPeriodo(Timestamp desdeTime, Timestamp hastaTime)
			throws DataStoreException {

		GregorianCalendar cal = new GregorianCalendar();
		if (desdeTime != null)
			cal.setTime(desdeTime);
		Timestamp day = new Timestamp(cal.getTimeInMillis());
		//_dsPeriodo.setDateTime("desde", day);
		desde = day;
		if (hastaTime != null)
			cal.setTime(hastaTime);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 9);
		day = new Timestamp(cal.getTimeInMillis());
		//_dsPeriodo.setDateTime("hasta", day);
		hasta = day;
	}
}
