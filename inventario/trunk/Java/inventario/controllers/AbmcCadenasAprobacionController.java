//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import java.sql.SQLException;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * AbmcCadenasAprobacionController: a SOFIA generated controller
 */
public class AbmcCadenasAprobacionController extends BaseController implements
		SubmitListener, PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6544079971013795734L;
	// Visual Components

	public com.salmonllc.html.HtmlCheckBox _seleccion1;
	public com.salmonllc.html.HtmlLookUpComponent _firmantes2;
	public com.salmonllc.html.HtmlText _cardinalidad1;
	public com.salmonllc.html.HtmlText _cardinalidad2;
	public com.salmonllc.html.HtmlText _descripcion1;
	public com.salmonllc.html.HtmlText _descripcion2;
	public com.salmonllc.html.HtmlText _firmante1;
	public com.salmonllc.html.HtmlText _nombre1;
	public com.salmonllc.html.HtmlText _nombre2;
	public com.salmonllc.html.HtmlText _numero1;
	public com.salmonllc.html.HtmlText _numero2;
	public com.salmonllc.html.HtmlText _observaciones1;
	public com.salmonllc.html.HtmlText _observaciones2;
	public com.salmonllc.html.HtmlText _orden1;
	public com.salmonllc.html.HtmlText _prioridad1;
	public com.salmonllc.html.HtmlText _prioridad2;
	public com.salmonllc.html.HtmlTextEdit _orden2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspForm _bannerForm;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	public com.salmonllc.jsp.JspRaw _rawAddRow;
	public com.salmonllc.jsp.JspTable _navbarTable;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader5;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow5;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow1;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow0;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	// DataSources
	public infraestructura.models.ConfiguracionModel _dsConfiguracion;
	public inventario.models.CadenasAprobacionModel _dsCadenasAprobacion;

	// custom components
	public com.salmonllc.html.HtmlSubmitButton _grabarBUT4;
	public com.salmonllc.html.HtmlSubmitButton _agregarBUT2;
	public com.salmonllc.html.HtmlSubmitButton _cancelarBUT5;
	public com.salmonllc.html.HtmlSubmitButton _eliminarBUT3;

	// DataSource Column Constants
	public static final String DSCADENASAPROBACION_CADENAS_APROBACION_CADENA_APROBACION_ID = "cadenas_aprobacion.cadena_aprobacion_id";
	public static final String DSCADENASAPROBACION_CADENAS_APROBACION_USER_FIRMANTE = "cadenas_aprobacion.user_firmante";
	public static final String DSCADENASAPROBACION_CADENAS_APROBACION_ORDEN = "cadenas_aprobacion.orden";
	public static final String DSCADENASAPROBACION_CADENAS_APROBACION_CONFIGURACION_ID = "cadenas_aprobacion.configuracion_id";
	public static final String DSCADENASAPROBACION_WEBSITE_USER_NOMBRE_COMPLETO = "website_user.nombre_completo";

	public static final String DSCONFIGURACION_CONFIGURACION_CONFIGURACION_ID = "configuracion.configuracion_id";
	public static final String DSCONFIGURACION_CONFIGURACION_ESQUEMA_CONFIGURACION_ID = "configuracion.esquema_configuracion_id";
	public static final String DSCONFIGURACION_CONFIGURACION_NOMBRE = "configuracion.nombre";
	public static final String DSCONFIGURACION_CONFIGURACION_DESCRIPCION = "configuracion.descripcion";
	public static final String DSCONFIGURACION_CONFIGURACION_OBSERVACIONES = "configuracion.observaciones";
	public static final String DSCONFIGURACION_CONFIGURACION_CARDINALIDAD = "configuracion.cardinalidad";
	public static final String DSCONFIGURACION_CONFIGURACION_PRIORIDAD = "configuracion.prioridad";
	public static final String DSCONFIGURACION_ESQUEMA_CONFIGURACION_NOMBRE = "esquema_configuracion.nombre";
	public static final String DSCONFIGURACION_ESQUEMA_CONFIGURACION_TIPO_OBJETO = "esquema_configuracion.tipo_objeto";
	public static final String DSCONFIGURACION_ESQUEMA_CONFIGURACION_NOMBRE_OBJETO = "esquema_configuracion.nombre_objeto";

	private String SELECCION_FLAG1 = "SELECCION_FLAG1";

	private int regId = -1;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		super.initialize();

		// Agrega lo sbotones al listFormDisplayBox
		_agregarBUT2 = new HtmlSubmitButton("agregarBUT2", "Agregar", this);
		_agregarBUT2.setAccessKey("A");
		_listformdisplaybox2.addButton(_agregarBUT2);

		_eliminarBUT3 = new HtmlSubmitButton("eliminarBUT3", "Eliminar", this);
		_eliminarBUT3.setAccessKey("E");
		_listformdisplaybox2.addButton(_eliminarBUT3);

		_grabarBUT4 = new HtmlSubmitButton("grabarBUT4", "Grabar", this);
		_grabarBUT4.setAccessKey("G");
		_listformdisplaybox2.addButton(_grabarBUT4);

		_cancelarBUT5 = new HtmlSubmitButton("cancelarBUT5", "Cancelar", this);
		_cancelarBUT5.setAccessKey("C");
		_listformdisplaybox2.addButton(_cancelarBUT5);

		_agregarBUT2.addSubmitListener(this);
		_eliminarBUT3.addSubmitListener(this);
		_grabarBUT4.addSubmitListener(this);
		_cancelarBUT5.addSubmitListener(this);

		_dsCadenasAprobacion.addBucket(SELECCION_FLAG1, DataStore.DATATYPE_INT);
		_seleccion1.setColumn(_dsCadenasAprobacion, SELECCION_FLAG1);

		_dsCadenasAprobacion.setAutoValidate(true);

	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {

		// Nuevo registro de configuración
		if (e.getComponent() == _agregarBUT2) {

			// crea un nuevo registro de tarea
			try {
				int configuracion_id = _dsConfiguracion
						.getConfiguracionConfiguracionId();

				if (configuracion_id > 0) {
					int reg = _dsCadenasAprobacion.insertRow();
					_dsCadenasAprobacion.gotoRow(reg);
					_dsCadenasAprobacion
							.setCadenasAprobacionConfiguracionId(configuracion_id);
					System.out.println(_dsCadenasAprobacion
							.getCadenasAprobacionConfiguracionId());
				}
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsConfiguracion);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// borro registros de configuración (baja física)
		if (e.getComponent() == _eliminarBUT3) {
			// recorro el datastore y seteo como anulado a los registros
			for (int i = 0; i < _dsCadenasAprobacion.getRowCount(); i++) {
				if (_dsCadenasAprobacion.getInt(i, SELECCION_FLAG1) == 1) {
					// Entidad marcado para selección
					_dsCadenasAprobacion.deleteRow(i);
					// dessetea el flag
					// _dsCadenasAprobacion.setInt(i, SELECCION_FLAG1, 0);
					try {
						_dsCadenasAprobacion.update();
					} catch (DataStoreException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}
				}

			}
		}

		// graba los datos del datastore configuraciones
		if (e.getComponent() == _grabarBUT4) {
			try {
				_dsCadenasAprobacion.update();
				_dsCadenasAprobacion.reset();
				_dsCadenasAprobacion.retrieve("configuracion_id = "+_dsConfiguracion.getConfiguracionConfiguracionId());
				_dsCadenasAprobacion.gotoFirst();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (SQLException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// cancela operación de Configuración
		if (e.getComponent() == _cancelarBUT5) {
			_dsCadenasAprobacion.resetStatus();
			_dsCadenasAprobacion.reset();
			_dsCadenasAprobacion.retrieve("configuracion_id = "+_dsConfiguracion.getConfiguracionConfiguracionId());
			_dsCadenasAprobacion.gotoFirst();
		}

		return super.submitPerformed(e);
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		// TODO Auto-generated method stub
		if (!isReferredByCurrentPage() && _dsConfiguracion.getRow() != -1) {
			regId = _dsConfiguracion.getConfiguracionConfiguracionId();
		}
		super.pageRequested(p);
	}

	@Override
	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);

		// ante cada requerimiento verifica contexto y determina detalle de
		// estados y completa FK's
		// Es row de circuito válida?
		try {
			if (_dsConfiguracion.getRow() != -1) {
				int configuracion_id = _dsConfiguracion
						.getConfiguracionConfiguracionId();
				if (regId != configuracion_id) {
					_dsCadenasAprobacion.retrieve("configuracion_id = "
							+ configuracion_id);
					_dsCadenasAprobacion.gotoFirst();
					regId = configuracion_id;
				}
			}

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		} catch (SQLException e) {
			displayErrorMessage(e.getMessage());
		}

	}

}
