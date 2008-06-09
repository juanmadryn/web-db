//package statement
package tango.controllers;

//Salmon import statements
import tango.util.ReplicateCpa50QuartzJob;
import tango.util.ReplicateLegajoQuartzJob;
import infraestructura.controllers.BaseController;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;

/**
 * ConsultaCompradoresController: a SOFIA generated controller
 */
public class ConsultaCompradoresController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8158366687563108852L;
	//Visual Components
	public com.salmonllc.html.HtmlText _buscarCAP;
	public com.salmonllc.html.HtmlText _codcompraCAP;
	public com.salmonllc.html.HtmlText _codcompraTE;
	public com.salmonllc.html.HtmlText _fillerCAP;
	public com.salmonllc.html.HtmlText _fillerTE;
	public com.salmonllc.html.HtmlText _idCAP;
	public com.salmonllc.html.HtmlText _idTE;
	public com.salmonllc.html.HtmlText _nomcompraCAP;
	public com.salmonllc.html.HtmlText _nomcompraTE;
	public com.salmonllc.html.HtmlText _text1Footer;
	public com.salmonllc.html.HtmlText _text2Footer;
	public com.salmonllc.html.HtmlText _text3Footer;
	public com.salmonllc.html.HtmlTextEdit _buscarTE;
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspContainer _noCache;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspNavBar _navbar1;
	public com.salmonllc.jsp.JspRaw _rawAddRow;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTable _table2;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable1TDRow3;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _table2TDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _table2TRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public tango.models.Cpa50View _dsLegajos;

	//DataSource Column Constants
	public static final String DSLEGAJOS_CPA50_ID_CPA50 = "cpa50.ID_CPA50";
	public static final String DSLEGAJOS_CPA50_FILLER = "cpa50.FILLER";
	public static final String DSLEGAJOS_CPA50_COD_COMPRA = "cpa50.COD_COMPRA";
	public static final String DSLEGAJOS_CPA50_NOM_COMPRA = "cpa50.NOM_COMPRA";

	public static final String DSQBE_BUSCAR = "buscar";
	
	//Custom
	public com.salmonllc.html.HtmlSubmitButton _importarBUT1;

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		_importarBUT1 = new HtmlSubmitButton("importarBUT1",
				"Importar de Tango", this);
		_importarBUT1.setAccessKey("I");
		_searchformdisplaybox1.addButton(_importarBUT1);
		
		addPageListener(this);
		_importarBUT1.addSubmitListener(this);

		super.initialize();
	}
	
	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
	
		// TODO: change
		if (e.getComponent() == _importarBUT1) {
			ReplicateCpa50QuartzJob replicateCpa50QuartzJob = new ReplicateCpa50QuartzJob();
			replicateCpa50QuartzJob.importaCompradores();
		}
		
		return super.submitPerformed(e);
	}
}
