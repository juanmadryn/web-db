//package statement
package infraestructura.controllers;

//Salmon import statements
import com.salmonllc.html.events.PageEvent;


/**
 * AbmcTransicionEstadoController: a SOFIA generated controller
 */
public class AbmcTransicionEstadoController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = -9207469986112410558L;
	//Visual Components
      public com.salmonllc.html.HtmlDropDownList _accionDD4;
      public com.salmonllc.html.HtmlDropDownList _circuitoDD3;
      public com.salmonllc.html.HtmlDropDownList _estadoDestinoDD5;
      public com.salmonllc.html.HtmlDropDownList _estadoOrigenDD3;
      public com.salmonllc.html.HtmlText _accionCAP12;
      public com.salmonllc.html.HtmlText _accionCAP8;
      public com.salmonllc.html.HtmlText _accionTXT8;
      public com.salmonllc.html.HtmlText _circuitoCAP5;
      public com.salmonllc.html.HtmlText _destinoTXT9;
      public com.salmonllc.html.HtmlText _estadoDestinoCAP13;
      public com.salmonllc.html.HtmlText _estadoDestinoCAP9;
      public com.salmonllc.html.HtmlText _estadoOrigenCAP11;
      public com.salmonllc.html.HtmlText _estadoOrigenCAP7;
      public com.salmonllc.html.HtmlText _origenTXT7;
      public com.salmonllc.html.HtmlText _promptAccionCAP10;
      public com.salmonllc.html.HtmlText _promptAccionCAP14;
      public com.salmonllc.html.HtmlText _promptAccionTXT6;
      public com.salmonllc.html.HtmlTextEdit _promptAccionTE3;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;

//DataSource Column Constants
       public static final String DSQBE_CIRCUITO="Circuito";

       public static final String DSTRANSICIONESTADO_TRANSICION_ESTADOS_ESTADO_ORIGEN="transicion_estados.estado_origen";
       public static final String DSTRANSICIONESTADO_TRANSICION_ESTADOS_ACCION="transicion_estados.accion";
       public static final String DSTRANSICIONESTADO_TRANSICION_ESTADOS_ESTADO_DESTINO="transicion_estados.estado_destino";
       public static final String DSTRANSICIONESTADO_TRANSICION_ESTADOS_PROMPT_ACCION="transicion_estados.prompt_accion";
       public static final String DSTRANSICIONESTADO_ESTADOS_ORIGEN_NOMBRE="estados_origen.nombre";
       public static final String DSTRANSICIONESTADO_ESTADOS_ORIGEN_CIRCUITO="estados_origen.circuito";
       public static final String DSTRANSICIONESTADO_ESTADOS_DESTINO_NOMBRE="estados_destino.nombre";
       public static final String DSTRANSICIONESTADO_ESTADOS_DESTINO_CIRCUITO="estados_destino.circuito";
       public static final String DSTRANSICIONESTADO_ACCIONES_APLICACION_NOMBRE="acciones_apps.nombre";
       public static final String DSTRANSICIONESTADO_ACCIONES_APLICACION_CIRCUITO="acciones_apps.circuito";


       /* (non-Javadoc)
	 * @see infraestructura.controllers.BaseController#pageRequested(com.salmonllc.html.events.PageEvent)
	 */
	public void pageRequested(PageEvent p) throws Exception {
		super.pageRequested(p);
		
		// actualiza los ítems del tipo select según el circuito seleccionado
		String circuito = _circuitoDD3.getValue();
		
		// siempre limpio los dropdown
		_accionDD4.resetOptions();
		_estadoOrigenDD3.resetOptions();
		_estadoDestinoDD5.resetOptions();
		
		if (circuito != null) {
			// seteo el nuevo criterio
			_accionDD4.setCriteria("circuito = '" +circuito+"'");
			_estadoOrigenDD3.setCriteria("circuito = '" +circuito+"'");
			_estadoDestinoDD5.setCriteria("circuito = '" +circuito+"'");
			
			// lleno nuevamente los dropdown
			_accionDD4.populateDropdownOptions();
			_estadoOrigenDD3.populateDropdownOptions();
			_estadoDestinoDD5.populateDropdownOptions();
		}
	}

 

}
