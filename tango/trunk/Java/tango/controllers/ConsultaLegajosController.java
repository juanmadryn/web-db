//package statement
package tango.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import tango.util.ReplicateLegajoQuartzJob;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.SubmitEvent;

/**
 * ConsultaLegajosController: a SOFIA generated controller
 */
public class ConsultaLegajosController extends BaseController {

	//Visual Components
	public com.salmonllc.html.HtmlText _apellidoCAP;
	public com.salmonllc.html.HtmlText _apellidoTE;
	public com.salmonllc.html.HtmlText _buscarCAP;
	public com.salmonllc.html.HtmlText _cuilCAP;
	public com.salmonllc.html.HtmlText _cuilTE;
	public com.salmonllc.html.HtmlText _nombreCAP;
	public com.salmonllc.html.HtmlText _nombreTE;
	public com.salmonllc.html.HtmlText _nroLegajoCAP;
	public com.salmonllc.html.HtmlText _nroLegajoTE;
	public com.salmonllc.html.HtmlTextEdit _buscarTE;
	public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;	
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
	public tango.models.LegajoView _dsLegajos;

	//DataSource Column Constants
	public static final String DSLEGAJOS_LEGAJO_ID_LEGAJO = "LEGAJO.ID_LEGAJO";
	public static final String DSLEGAJOS_LEGAJO_ID_TIPO_DOCUMENTO = "LEGAJO.ID_TIPO_DOCUMENTO";
	public static final String DSLEGAJOS_LEGAJO_ID_LEGAJO_JEFE = "LEGAJO.ID_LEGAJO_JEFE";
	public static final String DSLEGAJOS_LEGAJO_ID_PROVINCIA = "LEGAJO.ID_PROVINCIA";
	public static final String DSLEGAJOS_LEGAJO_ID_MODELO_ASIENTO_SU = "LEGAJO.ID_MODELO_ASIENTO_SU";
	public static final String DSLEGAJOS_LEGAJO_ID_NACIONALIDAD = "LEGAJO.ID_NACIONALIDAD";
	public static final String DSLEGAJOS_LEGAJO_ID_EXPEDIDO_POR = "LEGAJO.ID_EXPEDIDO_POR";
	public static final String DSLEGAJOS_LEGAJO_NRO_LEGAJO = "LEGAJO.NRO_LEGAJO";
	public static final String DSLEGAJOS_LEGAJO_APELLIDO = "LEGAJO.APELLIDO";
	public static final String DSLEGAJOS_LEGAJO_NOMBRE = "LEGAJO.NOMBRE";
	public static final String DSLEGAJOS_LEGAJO_APELLIDO_CONYUGE = "LEGAJO.APELLIDO_CONYUGE";
	public static final String DSLEGAJOS_LEGAJO_FECHA_NACIMIENTO = "LEGAJO.FECHA_NACIMIENTO";
	public static final String DSLEGAJOS_LEGAJO_NRO_DOCUMENTO = "LEGAJO.NRO_DOCUMENTO";
	public static final String DSLEGAJOS_LEGAJO_CALLE = "LEGAJO.CALLE";
	public static final String DSLEGAJOS_LEGAJO_NRO_DOMIC = "LEGAJO.NRO_DOMIC";
	public static final String DSLEGAJOS_LEGAJO_PISO = "LEGAJO.PISO";
	public static final String DSLEGAJOS_LEGAJO_DEPARTAMENTO_DOMIC = "LEGAJO.DEPARTAMENTO_DOMIC";
	public static final String DSLEGAJOS_LEGAJO_CODIGO_POSTAL = "LEGAJO.CODIGO_POSTAL";
	public static final String DSLEGAJOS_LEGAJO_LOCALIDAD = "LEGAJO.LOCALIDAD";
	public static final String DSLEGAJOS_LEGAJO_TAREA_HABITUAL = "LEGAJO.TAREA_HABITUAL";
	public static final String DSLEGAJOS_LEGAJO_CUIL = "LEGAJO.CUIL";
	public static final String DSLEGAJOS_LEGAJO_EMAIL = "LEGAJO.EMAIL";
	public static final String DSLEGAJOS_LEGAJO_SEXO = "LEGAJO.SEXO";
	public static final String DSLEGAJOS_LEGAJO_ESTADO_CIVIL = "LEGAJO.ESTADO_CIVIL";
	public static final String DSLEGAJOS_LEGAJO_FOTO_LEGAJO = "LEGAJO.FOTO_LEGAJO";
	public static final String DSLEGAJOS_LEGAJO_CONFIDENCIAL = "LEGAJO.CONFIDENCIAL";
	public static final String DSLEGAJOS_LEGAJO_OBSERVACIONES = "LEGAJO.OBSERVACIONES";
	public static final String DSLEGAJOS_LEGAJO_APELLIDO_MATERNO = "LEGAJO.APELLIDO_MATERNO";
	public static final String DSLEGAJOS_LEGAJO_ID_COMUNA = "LEGAJO.ID_COMUNA";
	public static final String DSLEGAJOS_LEGAJO_CA_83_IMPORT1 = "LEGAJO.CA_83_IMPORT1";
	public static final String DSLEGAJOS_LEGAJO_CA_83_IMPORT2 = "LEGAJO.CA_83_IMPORT2";
	public static final String DSLEGAJOS_LEGAJO_CA_83_IMPORT3 = "LEGAJO.CA_83_IMPORT3";
	public static final String DSLEGAJOS_LEGAJO_CA_83_IMPORT4 = "LEGAJO.CA_83_IMPORT4";
	public static final String DSLEGAJOS_LEGAJO_CA_83_IMPORT5 = "LEGAJO.CA_83_IMPORT5";
	public static final String DSLEGAJOS_LEGAJO_CA_83_DESC1 = "LEGAJO.CA_83_DESC1";
	public static final String DSLEGAJOS_LEGAJO_CA_83_DESC2 = "LEGAJO.CA_83_DESC2";
	public static final String DSLEGAJOS_LEGAJO_CA_83_DESC3 = "LEGAJO.CA_83_DESC3";
	public static final String DSLEGAJOS_LEGAJO_CA_83_DESC4 = "LEGAJO.CA_83_DESC4";
	public static final String DSLEGAJOS_LEGAJO_CA_83_DESC5 = "LEGAJO.CA_83_DESC5";
	public static final String DSLEGAJOS_LEGAJO_CA_83_ANTIGUE = "LEGAJO.CA_83_ANTIGUE";
	public static final String DSLEGAJOS_LEGAJO_CA_83_ANTIG_ANT = "LEGAJO.CA_83_ANTIG_ANT";
	public static final String DSLEGAJOS_LEGAJO_CA_83_EVALUAC = "LEGAJO.CA_83_EVALUAC";
	public static final String DSLEGAJOS_LEGAJO_CA_83_ADI_FIJ = "LEGAJO.CA_83_ADI_FIJ";
	public static final String DSLEGAJOS_LEGAJO_CA_83_TIP_SEG = "LEGAJO.CA_83_TIP_SEG";
	public static final String DSLEGAJOS_LEGAJO_CA_83_SEG_VID = "LEGAJO.CA_83_SEG_VID";
	public static final String DSLEGAJOS_LEGAJO_CA_83_NUM_JUB = "LEGAJO.CA_83_NUM_JUB";
	public static final String DSLEGAJOS_LEGAJO_CA_83_N_INSCRIP = "LEGAJO.CA_83_N_INSCRIP";

	public static final String DSQBE_BUSCAR = "buscar";

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
		
		if (e.getComponent() == _importarBUT1) {
			ReplicateLegajoQuartzJob replicateLegajoQuartzJob = new ReplicateLegajoQuartzJob();
			replicateLegajoQuartzJob.importaLegajos();
		}
	
		return super.submitPerformed(e);
	}

}
