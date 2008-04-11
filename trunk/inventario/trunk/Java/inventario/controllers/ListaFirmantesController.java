//package statement
package inventario.controllers;

//Salmon import statements
import java.sql.SQLException;

import com.salmonllc.jsp.*;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import com.salmonllc.html.events.*;

/**
 * ListaFirmantesController: a SOFIA generated controller
 */
public class ListaFirmantesController extends JspController
	implements
		SubmitListener,
		PageListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9018098327528615900L;
	//Visual Components
	public com.salmonllc.html.HtmlSubmitButton _cerrar;
	public com.salmonllc.html.HtmlText _apeynomCAP11;
	public com.salmonllc.html.HtmlText _apeynomTXT7;
	public com.salmonllc.html.HtmlText _estado1;
	public com.salmonllc.html.HtmlText ordenTXT;
	public com.salmonllc.html.HtmlText estadoTXT;
	public com.salmonllc.html.HtmlText fecha1;
	public com.salmonllc.html.HtmlText fechaTXT;
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.InstanciasAprobacionModel _dsFirmantes;

	//DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_INSTANCIA_APROBACION_ID = "instancias_aprobacion.instancia_aprobacion_id";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_SOLICITUD_COMPRA_ID = "instancias_aprobacion.solicitud_compra_id";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_USER_FIRMANTE = "instancias_aprobacion.user_firmante";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_ESTADO = "instancias_aprobacion.estado";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_FECHA_ENTRADA = "instancias_aprobacion.fecha_entrada";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_FECHA_ACCION = "instancias_aprobacion.fecha_accion";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_MENSAJE = "instancias_aprobacion.mensaje";
	public static final String DSFIRMANTES_INSTANCIAS_APROBACION_ORDEN = "instancias_aprobacion.orden";
	public static final String DSFIRMANTES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSFIRMANTES_USER_NOMBRE_COMPLETO = "user.nombre_completo";

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
					_dsFirmantes.setOrderBy("orden DESC");
					_dsFirmantes.retrieve("instancias_aprobacion.solicitud_compra_id="+solicitud_id+" AND (instancias_aprobacion.fecha_accion IS NOT NULL OR instancias_aprobacion.estado LIKE '0007.0001')");
					_dsFirmantes.gotoFirst();

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
