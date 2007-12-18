//package statement
package infraestructura.controllers;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.SubmitEvent;

//Salmon import statements


/**
 * AbmcEsquemaConfiguracionController: a SOFIA generated controller
 */
public class AbmcEsquemaConfiguracionController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = 1044254373591627007L;
	//Visual Components
      public com.salmonllc.html.HtmlText _buscarCAP1;
      public com.salmonllc.html.HtmlText _dapSelectorCAP14;
      public com.salmonllc.html.HtmlText _descripcionCAP16;
      public com.salmonllc.html.HtmlText _descripcionCAP4;
      public com.salmonllc.html.HtmlText _descripcionTXT2;
      public com.salmonllc.html.HtmlText _nombreCAP15;
      public com.salmonllc.html.HtmlText _nombreCAP2;
      public com.salmonllc.html.HtmlText _nombreObjetoTXT8;
      public com.salmonllc.html.HtmlText _nombreTXT1;
      public com.salmonllc.html.HtmlText _objetoCAP20;
      public com.salmonllc.html.HtmlText _observacionesCAP5;
      public com.salmonllc.html.HtmlTextEdit _buscarTE1;
      public com.salmonllc.html.HtmlTextEdit _descripcionTE5;
      public com.salmonllc.html.HtmlTextEdit _nombreObjetoTE11;
      public com.salmonllc.html.HtmlTextEdit _nombreTE2;
      public com.salmonllc.html.HtmlTextEdit _observacionesTE6;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;

//DataSources
      public com.salmonllc.sql.QBEBuilder _dsQBE;
      public infraestructura.models.EsquemaConfiguracionModel _dsEsquema;

//DataSource Column Constants
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_ESQUEMA_CONFIGURACION_ID="esquema_configuracion.esquema_configuracion_id";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_NOMBRE="esquema_configuracion.nombre";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_DESCRIPCION="esquema_configuracion.descripcion";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_OBSERVACIONES="esquema_configuracion.observaciones";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_TIPO_OBJETO="esquema_configuracion.tipo_objeto";
       public static final String DSESQUEMA_ESQUEMA_CONFIGURACION_NOMBRE_OBJETO="esquema_configuracion.nombre_objeto";

       public static final String DSQBE_BUSCAR="buscar";

      	// botones custom
       public com.salmonllc.html.HtmlSubmitButton _configuraEsquemaBUT1;

	@Override
	public void initialize() throws Exception {
   		// botones para Entidades externas Listformdisplaybox1
   		_configuraEsquemaBUT1 = new HtmlSubmitButton("configuraEsquemaBUT1","cOnfigurar Esquema",this);
   		_configuraEsquemaBUT1.setAccessKey("O");
   		_listformdisplaybox1.addButton(_configuraEsquemaBUT1);
   		
   		_configuraEsquemaBUT1.addSubmitListener(this);
   		
   		_dsEsquema.setAutoValidate(true);
   		
		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {

		// configura el esquema de configuración
		if (e.getComponent() == _configuraEsquemaBUT1) {
			
			int esquema = _dsEsquema.getEsquemaConfiguracionEsquemaConfiguracionId();
			if (esquema < 1) {
				displayErrorMessage("Debe seleccionar un esquema para ver o configurar");
				return false;
			}
			
			gotoSiteMapPage("AbmcConfiguracion","?p_esquema_configuracion_id="+ Integer.toString(esquema));
		}
		
		return super.submitPerformed(e);
	}
 
       
       

}
