//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.UsuarioRolesModel;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.sql.DataStoreException;

/**
 * ConsultaArticulosParaRecepcionController: a SOFIA generated controller
 */
public class ConsultaArticulosParaRecepcionController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6848662870905535478L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _comprador2;
	public com.salmonllc.html.HtmlDropDownList _solicitante2;
	public com.salmonllc.html.HtmlText _articulo1;
	public com.salmonllc.html.HtmlText _articulo2;
	public com.salmonllc.html.HtmlText _articulo3;
	public com.salmonllc.html.HtmlText _articulo4;
	public com.salmonllc.html.HtmlText _cantidad_pedida1;
	public com.salmonllc.html.HtmlText _cantidad_pedida2;
	public com.salmonllc.html.HtmlText _cantidad_recibida1;
	public com.salmonllc.html.HtmlText _cantidad_recibida2;
	public com.salmonllc.html.HtmlText _comprador1;
	public com.salmonllc.html.HtmlText _comprador3;
	public com.salmonllc.html.HtmlText _comprador4;
	public com.salmonllc.html.HtmlText _fecha1;
	public com.salmonllc.html.HtmlText _fecha2;
	public com.salmonllc.html.HtmlText _fechadesde1;
	public com.salmonllc.html.HtmlText _fechahasta1;
	public com.salmonllc.html.HtmlText _n1;
	public com.salmonllc.html.HtmlText _orden_compra_id1;
	public com.salmonllc.html.HtmlText _orden_compra_id2;
	public com.salmonllc.html.HtmlText _proveedor1;
	public com.salmonllc.html.HtmlText _proveedor2;
	public com.salmonllc.html.HtmlText _separador1;
	public com.salmonllc.html.HtmlText _separador2;
	public com.salmonllc.html.HtmlText _solicitante1;
	public com.salmonllc.html.HtmlText _solicitante3;
	public com.salmonllc.html.HtmlText _solicitante4;
	public com.salmonllc.html.HtmlTextEdit _fechadesde2;
	public com.salmonllc.html.HtmlTextEdit _fechahasta2;
	public com.salmonllc.html.HtmlTextEdit _n2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _bannerForm;
	public com.salmonllc.jsp.JspForm _pageForm;
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
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader7;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow6;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow7;
	public com.salmonllc.jsp.JspTableCell _table2TDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	// DataSources
	public com.salmonllc.sql.DataStore _dsPeriodo;
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.ArticulosCompradosModel _dsArticulosComprados;

	// DataSource Column Constants
	public static final String DSQBE_N = "n";
	public static final String DSQBE_COMPRADOR = "comprador";
	public static final String DSQBE_SOLICITANTE = "solicitante";
	public static final String DSQBE_PROVEEDOR_ID = "proveedor_id";

	public static final String DSPERIODO_DESDE = "desde";
	public static final String DSPERIODO_HASTA = "hasta";

	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_DETALLE_SC_ID = "articulos_comprados.detalle_SC_id";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID = "articulos_comprados.orden_compra_id";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA = "articulos_comprados.cantidad_pedida";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA = "articulos_comprados.cantidad_recibida";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_ESTADO = "articulos_comprados.estado";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_NOMBRE = "articulos_comprados.nombre";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_DESCRIPCION = "articulos_comprados.descripcion";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA = "articulos_comprados.descripcion_completa";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR = "articulos_comprados.entidad_id_proveedor";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE = "articulos_comprados.proveedor_nombre";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_FECHA = "articulos_comprados.fecha";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_COMPRADOR = "articulos_comprados.comprador";
	public static final String DSARTICULOSCOMPRADOR_ARTICULOS_COMPRADOS_SOLICITANTE = "articulos_comprados.solicitante";

	private Timestamp desde;
	private Timestamp hasta;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {

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
			if (_fechadesde2.getValue() != null
					&& _fechahasta2.getValue() != null) {
				desde = _dsPeriodo.getDateTime("desde");
				hasta = _dsPeriodo.getDateTime("hasta");
				seteaPeriodo(desde, hasta);

				if (desde != null && hasta != null) {
					// chequeo las fechas
					if (hasta.compareTo(desde) < 0) {
						displayErrorMessage("La fecha desde debe ser anterior a la fecha hasta");
						return false;
					}
					whereFecha = "articulos_comprados.fecha BETWEEN '"
							+ desde.toString() + "' AND '" + hasta.toString()
							+ "'";
				}
			}
			_dsArticulosComprados.reset();
			String where = _dsQBE.generateSQLFilter(_dsArticulosComprados);
			if (whereFecha != null) {
				if (where != null)
					where += " AND ";
				else
					where = "";
				where += whereFecha;
			} else if (where != null)
				where += "";
			_dsArticulosComprados.retrieve(where);
			_dsArticulosComprados.gotoFirst();
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
		if (!UsuarioRolesModel.isRolUsuario(currentUser, USER_ENCARGADO_ALMACEN)) {
			_dsQBE.setString("solicitante", String.valueOf(currentUser));
			_solicitante2.setEnabled(false);			
		} else {
			_solicitante2.setEnabled(true);			
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
