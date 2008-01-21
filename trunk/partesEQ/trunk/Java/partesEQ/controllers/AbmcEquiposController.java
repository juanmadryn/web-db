//package statement
package partesEQ.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.html.events.SubmitListener;

/**
 * AbmcEquiposController: a SOFIA generated controller
 */
public class AbmcEquiposController extends BaseController implements
		SubmitListener, PageListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 4483962271581579268L;
	//Visual Components
	public com.salmonllc.html.HtmlImage _bannerDividerImage;
	public com.salmonllc.html.HtmlImage _bannerDivImage2;
	public com.salmonllc.html.HtmlImage _imgMainLogo;
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlText _codigoCAP22;
	public com.salmonllc.html.HtmlText _descripcionCAP13;
	public com.salmonllc.html.HtmlText _descripcionCAP18;
	public com.salmonllc.html.HtmlText _descripcionTXT9;
	public com.salmonllc.html.HtmlText _equipoCAP10;
	public com.salmonllc.html.HtmlText _equipoCAP12;
	public com.salmonllc.html.HtmlText _equipoCAP15;
	public com.salmonllc.html.HtmlText _equipoIdTXT6;
	public com.salmonllc.html.HtmlText _nombreCAP12;
	public com.salmonllc.html.HtmlText _nombreCAP16;
	public com.salmonllc.html.HtmlText _nombreTXT8;
	public com.salmonllc.html.HtmlText _observacionesCAP20;
	public com.salmonllc.html.HtmlText _text1Footer;
	public com.salmonllc.html.HtmlText _text2Footer;
	public com.salmonllc.html.HtmlText _text3Footer;
	public com.salmonllc.html.HtmlText _tipoCAP11;
	public com.salmonllc.html.HtmlText _tipoCAP14;
	public com.salmonllc.html.HtmlText _tipoIdTXT7;
	public com.salmonllc.html.HtmlText _txtBannerOptions;
	public com.salmonllc.html.HtmlText _welcomeText;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlTextEdit _codigoTE10;
	public com.salmonllc.html.HtmlTextEdit _descripcionTE6;
	public com.salmonllc.html.HtmlTextEdit _nombreTE5;
	public com.salmonllc.html.HtmlTextEdit _nombreTE6;
	public com.salmonllc.html.HtmlTextEdit _observacionesTE8;
	public com.salmonllc.html.HtmlTextEdit _tipoTE6;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspContainer _welcomeContainer;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _bannerForm;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspLink _baseLinkAdminSalmon;
	public com.salmonllc.jsp.JspLink _footerInfDevAbout;
	public com.salmonllc.jsp.JspLink _footerproyectosHelp;
	public com.salmonllc.jsp.JspLink _footerSalmonLink;
	public com.salmonllc.jsp.JspLink _footerSofiaLink;
	public com.salmonllc.jsp.JspLink _lnkBannerOptions;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
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
	public com.salmonllc.jsp.JspTableCell _table2TDRow1;
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
	public partesEQ.models.EquipoModel _dsEquipo;

	//DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSEQUIPO_EQUIPOS_EQUIPO_ID = "equipos.equipo_id";
	public static final String DSEQUIPO_EQUIPOS_TIPO_EQUIPO = "equipos.tipo_equipo";
	public static final String DSEQUIPO_EQUIPOS_NOMBRE = "equipos.nombre";
	public static final String DSEQUIPO_EQUIPOS_DESCRIPCION = "equipos.descripcion";
	public static final String DSEQUIPO_EQUIPOS_OBSERVACIONES = "equipos.observaciones";
	public static final String DSEQUIPO_EQUIPOS_CODIGO_INVENTARIO = "equipos.codigo_inventario";
	public static final String DSEQUIPO_TIPO_EQUIPO_NOMBRE = "tipo_equipo.nombre";

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public void initialize() {
		try {
			super.initialize();
			_dsEquipo.setPage(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		_detailformdisplaybox1.setVisible((_dsEquipo.getRow() == -1) ? false
				: true);
		super.pageRequested(p);
	}

}
