//package statement
package inventario.controllers;

//Salmon import statements
import java.sql.SQLException;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.jsp.JspController;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.QBEBuilder;

/**
 * LkpArticulosParaRecepcionController: a SOFIA generated controller
 */
public class LkpArticulosParaRecepcionController extends JspController
		implements SubmitListener, PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8106685309422839552L;
	// Visual Components
	public com.salmonllc.html.HtmlText _buscar1;
	public com.salmonllc.html.HtmlText _comprador1;
	public com.salmonllc.html.HtmlText _comprador2;
	public com.salmonllc.html.HtmlText _fecha1;
	public com.salmonllc.html.HtmlText _fecha2;
	public com.salmonllc.html.HtmlText _orden_compra_id1;
	public com.salmonllc.html.HtmlText _orden_compra_id2;
	public com.salmonllc.html.HtmlText _proveedor1;
	public com.salmonllc.html.HtmlText _articulo1;
	public com.salmonllc.html.HtmlText _articulo2;
	public com.salmonllc.html.HtmlText _articulo3;
	public com.salmonllc.html.HtmlText _articulo4;
	public com.salmonllc.html.HtmlText _proveedor2;
	public com.salmonllc.html.HtmlTextEdit _buscar2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.ArticulosCompradosModel _dsArticulosComprados;

	// DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_DETALLE_SC_ID = "articulos_comprados.detalle_SC_id";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_ORDEN_COMPRA_ID = "articulos_comprados.orden_compra_id";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_CANTIDAD_PEDIDA = "articulos_comprados.cantidad_pedida";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_CANTIDAD_RECIBIDA = "articulos_comprados.cantidad_recibida";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_ESTADO = "articulos_comprados.estado";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_NOMBRE = "articulos_comprados.nombre";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_DESCRIPCION = "articulos_comprados.descripcion";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_DESCRIPCION_COMPLETA = "articulos_comprados.descripcion_completa";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_ENTIDAD_ID_PROVEEDOR = "articulos_comprados.entidad_id_proveedor";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_PROVEEDOR_NOMBRE = "articulos_comprados.proveedor_nombre";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_FECHA = "articulos_comprados.fecha";
	public static final String DSARTICULOSCOMPRADOS_ARTICULOS_COMPRADOS_COMPRADOR = "articulos_comprados.comprador";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() {
		addPageListener(this);
		_dsQBE.addCriteria("proveedor", QBEBuilder.CRITERIA_TYPE_EQUALS,
				"articulos_comprados.entidad_id_proveedor");
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 * @throws DataStoreException
	 * @throws SQLException
	 */
	public void pageRequested(PageEvent event) throws DataStoreException,
			SQLException {

		if (!isReferredByCurrentPage())
			_dsArticulosComprados.reset();
		int proveedor_id = getIntParameter("proveedor_id");
		if (proveedor_id != -1) {
			// sólo recupera detalles q se hayan comprado al proveedor indicado
			_dsQBE.setString("proveedor", String.valueOf(proveedor_id));
		}

		if (_dsArticulosComprados.getRowCount() == 0) {
			_dsArticulosComprados.retrieve(_dsQBE
					.generateSQLFilter(_dsArticulosComprados));
			_dsArticulosComprados.gotoFirst();
		}

		// super.pageRequested(event);
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void pageRequestEnd(PageEvent p) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void pageSubmitEnd(PageEvent p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pageSubmitted(PageEvent p) {
		// TODO Auto-generated method stub

	}
}
