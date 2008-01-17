//package statement
package partesEQ.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;


/**
 * AbmcChoferesController: a SOFIA generated controller
 */
public class AbmcChoferesController extends BaseController {

	private static final long serialVersionUID = -2776606324063304989L;
	//Visual Components
	public com.salmonllc.html.HtmlLookUpComponent _legajoTE1;
	//public com.salmonllc.html.HtmlSubmitButton _menuBUT;
	public com.salmonllc.html.HtmlText _apeynomCAP12;
	public com.salmonllc.html.HtmlText _apeynomTXT8;
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _choferCAP12;
	public com.salmonllc.html.HtmlText _desdeCAP13;
	public com.salmonllc.html.HtmlText _desdeCAP16;
	public com.salmonllc.html.HtmlText _desdeTXT9;
	public com.salmonllc.html.HtmlText _hastaCAP14;
	public com.salmonllc.html.HtmlText _hastaCAP18;
	public com.salmonllc.html.HtmlText _hastaTXT10;
	public com.salmonllc.html.HtmlText _idchoferCAP10;
	public com.salmonllc.html.HtmlText _idchoferTXT6;
	public com.salmonllc.html.HtmlText _idTE5;
	public com.salmonllc.html.HtmlText _legajoCAP14;
	public com.salmonllc.html.HtmlText _nrolegajoCAP11;
	public com.salmonllc.html.HtmlText _nrolegajoTXT7;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlTextEdit _desdeTE6;
	public com.salmonllc.html.HtmlTextEdit _hastaTE6;
	public com.salmonllc.html.HtmlText _mensajesCAP19;
	public com.salmonllc.html.HtmlText _mensajesTE19;	
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;      

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public partesEQ.models.ChoferesModel _dsChofer;

	// DataSource Column Constants
	public static final String DSCHOFER_CHOFERES_CHOFER_ID="choferes.chofer_id";
	public static final String DSCHOFER_CHOFERES_PERSONAL_ID="choferes.personal_id";
	public static final String DSCHOFER_CHOFERES_NRO_LEGAJO="choferes.nro_legajo";
	public static final String DSCHOFER_CHOFERES_APEYNOM="choferes.apeynom";
	public static final String DSCHOFER_CHOFERES_DESDE="choferes.desde";
	public static final String DSCHOFER_CHOFERES_HASTA="choferes.hasta";

	public static final String DSQBE_BUSCAR="buscar";


	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {		
			super.initialize();
			_dsChofer.setPage(this);				
	}


}
