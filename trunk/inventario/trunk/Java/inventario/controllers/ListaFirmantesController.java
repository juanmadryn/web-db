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
import com.salmonllc.util.MessageLog;

/**
 * ListaFirmantesController: a SOFIA generated controller
 */
public class ListaFirmantesController extends JspController
	implements
		SubmitListener,
		PageListener {

	//Visual Components
	public com.salmonllc.html.HtmlSubmitButton _cerrar;
	public com.salmonllc.html.HtmlText _accion1;
	public com.salmonllc.html.HtmlText _accion2;
	public com.salmonllc.html.HtmlText _aEstado1;
	public com.salmonllc.html.HtmlText _aEstado2;
	public com.salmonllc.html.HtmlText _apeynomCAP11;
	public com.salmonllc.html.HtmlText _apeynomTXT7;
	public com.salmonllc.html.HtmlText _deEstado1;
	public com.salmonllc.html.HtmlText _deEstado2;
	public com.salmonllc.html.HtmlText _estado1;
	public com.salmonllc.html.HtmlText _estadoTXT;
	public com.salmonllc.html.HtmlText _fecha1;
	public com.salmonllc.html.HtmlText _fecha2;
	public com.salmonllc.html.HtmlText _fecha3;
	public com.salmonllc.html.HtmlText _fechaTXT;
	public com.salmonllc.html.HtmlText _loginNameCAP12;
	public com.salmonllc.html.HtmlText _ordenTXT;
	public com.salmonllc.html.HtmlText _usuario2;
	public com.salmonllc.html.HtmlText _usuario;
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow0;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public infraestructura.models.AuditaEstadosCircuitosModel _dsAuditoria;
	public inventario.models.InstanciasAprobacionModel _dsFirmantes;

	//DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_AUDITA_ID = "audita_estados_circuitos.audita_id";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_CIRCUITO = "audita_estados_circuitos.circuito";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_FECHA = "audita_estados_circuitos.fecha";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_ACCION = "audita_estados_circuitos.accion";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_DE_ESTADO = "audita_estados_circuitos.de_estado";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_A_ESTADO = "audita_estados_circuitos.a_estado";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_USER_ID = "audita_estados_circuitos.user_id";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_NOMBRE_TABLA = "audita_estados_circuitos.nombre_tabla";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_REGISTRO_ID = "audita_estados_circuitos.registro_id";
	public static final String DSAUDITORIA_AUDITA_ESTADOS_CIRCUITOS_HOST = "audita_estados_circuitos.host";
	public static final String DSAUDITORIA_CIRCUITOS_ESTADOS_NOMBRE = "circuitos_estados.nombre";
	public static final String DSAUDITORIA_ACCIONES_APPS_NOMBRE = "acciones_apps.nombre";
	public static final String DSAUDITORIA_WEBSITE_USER_NOMBRE_COMPLETO = "website_user.nombre_completo";
	public static final String DSAUDITORIA_WEBSITE_USER_NRO_LEGAJO = "website_user.nro_legajo";
	public static final String DSAUDITORIA_DE_ESTADOS_NOMBRE = "de_estados.nombre";
	public static final String DSAUDITORIA_A_ESTADOS_NOMBRE = "a_estados.nombre";

	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID = "instancias_aprobacion.instancia_aprobacion_id";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_USER_FIRMANTE = "instancias_aprobacion.user_firmante";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_ESTADO = "instancias_aprobacion.estado";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_FECHA_ENTRADA = "instancias_aprobacion.fecha_entrada";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_FECHA_ACCION = "instancias_aprobacion.fecha_accion";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_MENSAJE = "instancias_aprobacion.mensaje";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_ORDEN = "instancias_aprobacion.orden";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_NOMBRE_OBJETO = "instancias_aprobacion.nombre_objeto";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_OBJETO_ID = "instancias_aprobacion.objeto_id";
	public static final String DSFIRMANTES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSFIRMANTES_USER_FIRMANTE_NOMBRE_COMPLETO = "user_firmante.nombre_completo";

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public void initialize() throws Exception{
		super.initialize();
		addPageListener(this);
		_cerrar.addSubmitListener(this);
	}

	/**
	 * Process the page requested event
	 * @param event the page event to be processed
	 */
	public void pageRequested(PageEvent event) {
		try {
			System.out.println();
			// si la página es requerida por si misma no hago nada
			if (!isReferredByCurrentPage()) {
				// verifico si tiene parámetro
				int solicitud_id = getIntParameter("solicitud_id");
				if (solicitud_id > 0) {
					_dsFirmantes.setOrderBy("instancia_aprobacion_id DESC");
					_dsFirmantes.retrieve("instancias_aprobacion.nombre_objeto LIKE 'solicitudes_compra' AND instancias_aprobacion.objeto_id="+solicitud_id+" AND (instancias_aprobacion.fecha_accion IS NOT NULL OR instancias_aprobacion.estado LIKE '0007.0001')");
					_dsFirmantes.gotoFirst();
					_dsFirmantes.setOrderBy("audita_id DESC");
					_dsAuditoria.retrieve("audita_estados_circuitos.nombre_tabla LIKE 'inventario.solicitudes_compra' AND audita_estados_circuitos.registro_id ="+solicitud_id);
					_dsAuditoria.gotoFirst();
					
					
				}
				
			}

		} catch (DataStoreException e) {
			MessageLog.writeInfoMessage(e.getMessage(), this);
		} catch (SQLException e) {
			MessageLog.writeInfoMessage(e.getMessage(), this);
		}
		
	}
	

	public void pageRequestEnd(PageEvent p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void pageSubmitEnd(PageEvent p) {
		// TODO Auto-generated method stub
		
	}

	public void pageSubmitted(PageEvent p) {
		// TODO Auto-generated method stub
		
	}

	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
