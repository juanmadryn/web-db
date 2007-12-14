//package statement
package partesMO.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.jsp.*;
import com.salmonllc.html.events.*;


/**
 * AbmcSupervisoresController: a SOFIA generated controller
 */
public class AbmcSupervisoresController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = -8462060491953180703L;
	//Visual Components
      public com.salmonllc.html.HtmlLookUpComponent _supervisoTE4;
      public com.salmonllc.html.HtmlText _apeynombreTXT16;
      public com.salmonllc.html.HtmlText _apeynomCAP19;
      public com.salmonllc.html.HtmlText _apeynomCAP24;
      public com.salmonllc.html.HtmlText _apeynomTE10;
      public com.salmonllc.html.HtmlText _buscarCAP5;
      public com.salmonllc.html.HtmlText _idCAP19;
      public com.salmonllc.html.HtmlText _idCAP23;
      public com.salmonllc.html.HtmlText _idTXT18;
      public com.salmonllc.html.HtmlText _legajoCAP20;
      public com.salmonllc.html.HtmlText _legajoCAP25;
      public com.salmonllc.html.HtmlText _legajoTE11;
      public com.salmonllc.html.HtmlText _legajoTXT18;
      public com.salmonllc.html.HtmlText _vigenciaCAP20;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlTextEdit _desdeTE21;
      public com.salmonllc.html.HtmlTextEdit _hastaTE22;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;

//DataSources
      public com.salmonllc.sql.QBEBuilder _dsQBE;
      public partesMO.models.SupervisoresModel _dsSupervisor;

//DataSource Column Constants
       public static final String DSQBE_BUSCAR="buscar";

       public static final String DSSUPERVISOR_SUPERVISORES_PERSONAL_ID="supervisores.personal_id";
       public static final String DSSUPERVISOR_SUPERVISORES_NRO_LEGAJO="supervisores.nro_legajo";
       public static final String DSSUPERVISOR_SUPERVISORES_APEYNOM="supervisores.apeynom";
       public static final String DSSUPERVISOR_SUPERVISORES_FECHA_DESDE="supervisores.fecha_desde";
       public static final String DSSUPERVISOR_SUPERVISORES_FECHA_HASTA="supervisores.fecha_hasta";

 

}
