//package statement
package infraestructura.controllers;

//Salmon import statements
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.sql.DataStoreException;


/**
 * AccionesCircuitoController: a SOFIA generated controller
 */
public class AccionesCircuitoController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = 2083081188840198149L;
	//Visual Components
      public com.salmonllc.html.HtmlDropDownList _circuitoDD3;
      public com.salmonllc.html.HtmlText _accAppAccionTXT;
      public com.salmonllc.html.HtmlText _accAppdescripcionTXT;
      public com.salmonllc.html.HtmlText _accAppNombreTXT;
      public com.salmonllc.html.HtmlText _acciones_aplicacionAccionCAP;
      public com.salmonllc.html.HtmlText _acciones_aplicacionDescripcionCAP;
      public com.salmonllc.html.HtmlText _acciones_aplicacionNombreCAP;
      public com.salmonllc.html.HtmlText _accionINP;
      public com.salmonllc.html.HtmlText _accionINPCAP;
      public com.salmonllc.html.HtmlText _buscarCAP6;
      public com.salmonllc.html.HtmlText _circuitoCAP5;
      public com.salmonllc.html.HtmlText _descripcionINPCAP;
      public com.salmonllc.html.HtmlText _nombreINPCAP;
      public com.salmonllc.html.HtmlText _observacionesINPCAP;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlTextEdit _descripcionINP;
      public com.salmonllc.html.HtmlTextEdit _nombreINP;
      public com.salmonllc.html.HtmlTextEdit _observacionesINP;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;

//DataSource Column Constants
       public static final String DSQBE_BUSCAR="buscar";
       public static final String DSQBE_CIRCUITO="circuito";

       public static final String DSACCIONESAPLICACION_ACCIONES_APLICACION_ACCION="acciones_apps.accion";
       public static final String DSACCIONESAPLICACION_ACCIONES_APLICACION_NOMBRE="acciones_apps.nombre";
       public static final String DSACCIONESAPLICACION_ACCIONES_APLICACION_DESCRIPCION="acciones_apps.descripcion";
       public static final String DSACCIONESAPLICACION_ACCIONES_APLICACION_OBSERVACIONES="acciones_apps.observaciones";
       public static final String DSACCIONESAPLICACION_ACCIONES_APLICACION_CIRCUITO="acciones_apps.circuito";
       public static final String DSACCIONESAPLICACION_CIRCUITOS_ESTADOS_NOMBRE="circuitos_estados.nombre";

       
	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);
		
		// Verifica contexto y completa información
		try {
			if (_dsAccionesAplicacion.getRowCount() > 0 && 
				_dsAccionesAplicacion.getRow() != -1 && 
				_dsAccionesAplicacion.getString(DSACCIONESAPLICACION_ACCIONES_APLICACION_CIRCUITO) == null){
				
				_dsAccionesAplicacion.setString(DSACCIONESAPLICACION_ACCIONES_APLICACION_CIRCUITO,_circuitoDD3.getValue());
			}
		} catch (DataStoreException e) {
			e.printStackTrace();
		}
	}

 

}
