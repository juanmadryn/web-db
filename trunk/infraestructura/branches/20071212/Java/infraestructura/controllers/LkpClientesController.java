//package statement
package infraestructura.controllers;

//Salmon import statements
import com.salmonllc.jsp.*;
import com.salmonllc.html.events.*;


/**
 * LkpClientesController: a SOFIA generated controller
 */
public class LkpClientesController extends JspController implements SubmitListener, PageListener {

/**
	 * 
	 */
	private static final long serialVersionUID = -1927531092216804931L;
	//Visual Components
      public com.salmonllc.html.HtmlText _buscarCAP5;
      public com.salmonllc.html.HtmlText _descripcionCAP12;
      public com.salmonllc.html.HtmlText _descripcionTXT8;
      public com.salmonllc.html.HtmlText _entidadIdCAP10;
      public com.salmonllc.html.HtmlText _entidadIdTXT6;
      public com.salmonllc.html.HtmlText _nombreCAP11;
      public com.salmonllc.html.HtmlText _nombreTXT7;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
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
      public infraestructura.models.EntidadExternaModel _dsCliente;

//DataSource Column Constants
       public static final String DSCLIENTE_ENTIDAD_EXTERNA_ENTIDAD_ID="entidad_externa.entidad_id";
       public static final String DSCLIENTE_ENTIDAD_EXTERNA_CODIGO="entidad_externa.codigo";
       public static final String DSCLIENTE_ENTIDAD_EXTERNA_NOMBRE="entidad_externa.nombre";
       public static final String DSCLIENTE_ENTIDAD_EXTERNA_DESCRIPCION="entidad_externa.descripcion";
       public static final String DSCLIENTE_ENTIDAD_EXTERNA_OBSERVACIONES="entidad_externa.observaciones";
       public static final String DSCLIENTE_ENTIDAD_EXTERNA_ACTIVO="entidad_externa.activo";
       public static final String DSCLIENTE_ENTIDAD_EXTERNA_ANULADO="entidad_externa.anulado";

       public static final String DSQBE_BUSCAR="buscar";

 
/**
 * Initialize the page. Set up listeners and perform other initialization activities.
 */
public void initialize(){
	
	// seteo la condición de sólo rol de cliente
	_dsCliente.setCriteria("entidad_id in (select entidad_id from infraestructura.roles_entidad where rol = \"CLIE\")");
     addPageListener(this);
}
 
/**
 * Process the given submit event
 * @param event the submit event to be processed
 * @return true to continue processing events, false to stop processing events
 */
public boolean submitPerformed(SubmitEvent event) {
     return true;
}
 
/**
 * Process the page requested event
 * @param event the page event to be processed
 */
public void pageRequested(PageEvent event) {
}
 
/**
 * Process the page request end event
 * @param event the page event to be processed
 */
public void pageRequestEnd(PageEvent event) {
}
 
/**
 * Process the page submit end event
 * @param event the page event to be processed
 */
public void pageSubmitEnd(PageEvent event) {
}
 
/**
 * Process the page submit event
 * @param event the page event to be processed
 */
public void pageSubmitted(PageEvent event){
}

}
