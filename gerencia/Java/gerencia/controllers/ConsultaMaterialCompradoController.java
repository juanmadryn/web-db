package gerencia.controllers;


//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.feriadosModel;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.HtmlText;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;

/**
 * ConsultaRecepcionesComprasController: a SOFIA generated controller
 */
public class ConsultaMaterialCompradoController extends
		BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5233176031862847585L;
	// Visual Components
	public com.salmonllc.html.HtmlLookUpComponent _proyecto2;
	public com.salmonllc.html.HtmlDropDownList _tarea_proyecto1;
	public com.salmonllc.html.HtmlDropDownList _solicitante2;
	public com.salmonllc.html.HtmlImage _bannerDividerImage;
	public com.salmonllc.html.HtmlImage _bannerDivImage2;
	public com.salmonllc.html.HtmlImage _imgMainLogo;
	public com.salmonllc.html.HtmlText _monto_total1;
	public com.salmonllc.html.HtmlText _tipo1;
	public com.salmonllc.html.HtmlDropDownList _tipo2;
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

	// DataSources
	public com.salmonllc.sql.DataStore _dsPeriodo;
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public gerencia.models.MaterialCompradoModel _dsMateriales;

	// DataSource Column Constants
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
	public com.salmonllc.html.HtmlSubmitButton _limpiarBUT;

	private Timestamp desde;
	private Timestamp hasta;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		_limpiarBUT = new HtmlSubmitButton("limpiarBUT", "Limpiar", this);
		_limpiarBUT.setAccessKey("L");
		_searchformdisplaybox1.addButton(_limpiarBUT);
		_limpiarBUT.addSubmitListener(this);
		
		_searchformdisplaybox1.getSearchButton().addSubmitListener(this);

		_dsPeriodo.reset();
		_dsPeriodo.insertRow();
		seteaPeriodo(null, null); // valores por defecto para el periodo de
		// fechas
		_dsPeriodo.gotoFirst();

		_monto_total1.setExpression(_dsMateriales,"material_comprado.monto_unitario * material_comprado.cantidad_pedida",HtmlText.AGGREGATE_SUM);  
				
		_proyecto2.getEditField().addOnChange("llenarLista(true);");
		_proyecto2.getEditField().addOnLoseFocus("llenarLista(true);");
		setOnFocus("llenarLista(false);");
		
		//feriadosModel.feriados();
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
					whereFecha = "materiales_comprados.fecha_aprobacion_oc BETWEEN '"
							+ desde.toString() + "' AND '" + hasta.toString() + "'";
				}
			}
			_dsMateriales.reset();
			String where = _dsQBE.generateSQLFilter(_dsMateriales);
			if (whereFecha != null) {
				if (where != null)
					where += " AND ";
				else
					where = "";
				where += whereFecha;
			} 
			_dsMateriales.retrieve(where);
			_dsMateriales.gotoFirst();
		}

		// limpia los criterios
		if (e.getComponent() == _limpiarBUT) {
			_proyecto2.setValue(null);
			_tarea_proyecto1.setSelectedIndex(0);
			_fechadesde2.setValue(null);
			_fechahasta2.setValue(null);
		}

		return super.submitPerformed(e);
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
		// _dsPeriodo.setDateTime("desde", day);
		desde = day;
		if (hastaTime != null)
			cal.setTime(hastaTime);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 9);
		day = new Timestamp(cal.getTimeInMillis());
		// _dsPeriodo.setDateTime("hasta", day);
		hasta = day;
	}
}
