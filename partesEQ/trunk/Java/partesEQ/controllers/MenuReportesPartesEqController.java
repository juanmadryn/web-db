//package statement
package partesEQ.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

/**
 * MenuReportesPartesEqController: a SOFIA generated controller
 */
public class MenuReportesPartesEqController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Visual Components
	public com.salmonllc.html.HtmlImage _reporte1IMG2;
	public com.salmonllc.html.HtmlImage _reporte2IMG2;
	public com.salmonllc.html.HtmlText _reporte1TXT2;
	public com.salmonllc.html.HtmlText _reporte2TXT2;
	public com.salmonllc.html.HtmlText _text1Footer;
	public com.salmonllc.html.HtmlText _text2;
	public com.salmonllc.html.HtmlText _text2Footer;
	public com.salmonllc.html.HtmlText _text3Footer;
	public com.salmonllc.jsp.JspLink _reporte1BUT2;
	public com.salmonllc.jsp.JspLink _reporte2BUT2;

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		super.initialize();
		String URL = armarUrlReporte("PDF", "reporte_horas_equipo_ot", "");
		_reporte1BUT2.setHref(URL);
		
		URL = armarUrlReporte("XLS", "reporte_horas_equipo_ot", "");
		_reporte2BUT2.setHref(URL);
	}

}
