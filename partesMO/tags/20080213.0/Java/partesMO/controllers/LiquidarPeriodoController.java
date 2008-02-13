//package statement
package partesMO.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;


/**
 * LiquidarPeriodoController: a SOFIA generated controller
 */
public class LiquidarPeriodoController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = -6042624578520115958L;
	//Visual Components
      public com.salmonllc.html.HtmlSubmitButton _generarBUT;
      public com.salmonllc.html.HtmlText _al100CAP16;
      public com.salmonllc.html.HtmlText _al100TE16;
      public com.salmonllc.html.HtmlText _al50CAP15;
      public com.salmonllc.html.HtmlText _al50TE15;
      public com.salmonllc.html.HtmlText _anioCAP2;
      public com.salmonllc.html.HtmlText _apeynomTE12;
      public com.salmonllc.html.HtmlText _guionTE12;
      public com.salmonllc.html.HtmlText _horasCAP13;
      public com.salmonllc.html.HtmlText _horasTE13;
      public com.salmonllc.html.HtmlText _legajoCAP12;
      public com.salmonllc.html.HtmlText _mesCAP1;
      public com.salmonllc.html.HtmlText _nocturnasCAP17;
      public com.salmonllc.html.HtmlText _nocturnasTE17;
      public com.salmonllc.html.HtmlText _normalesCAP14;
      public com.salmonllc.html.HtmlText _normalesTE14;
      public com.salmonllc.html.HtmlText _nroLegajoTE12;
      public com.salmonllc.html.HtmlText _periodoCAP10;
      public com.salmonllc.html.HtmlText _periodoTE10;
      public com.salmonllc.html.HtmlText _quincenaCAP11;
      public com.salmonllc.html.HtmlText _quincenaTE11;
      public com.salmonllc.html.HtmlTextEdit _anioTE2;
      public com.salmonllc.html.HtmlTextEdit _mesTE1;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable2;
      public com.salmonllc.jsp.JspDisplayBox _displaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;

//DataSources
      public com.salmonllc.sql.DataStore _dsPeriodo;
      public partesMO.models.ResumenHorasModel _dsResHor;

//DataSource Column Constants
       public static final String DSRESHOR_LOTE_CARGA_PARTES_MO_LOTE_ID="lote_carga_partes_mo.lote_id";
       public static final String DSRESHOR_LOTE_CARGA_PARTES_MO_ESTADO="lote_carga_partes_mo.estado";
       public static final String DSRESHOR_LOTE_CARGA_PARTES_MO_FECHA_ALTA="lote_carga_partes_mo.fecha_alta";
       public static final String DSRESHOR_LOTE_CARGA_PARTES_MO_FECHA_CIERRE="lote_carga_partes_mo.fecha_cierre";
       public static final String DSRESHOR_LOTE_CARGA_PARTES_MO_DESCRIPCION="lote_carga_partes_mo.descripcion";
       public static final String DSRESHOR_ESTADOS_NOMBRE="estados.nombre";

       public static final String DSPERIODO_MES="mes";
       public static final String DSPERIODO_ANIO="anio";
       
// Variables privadas al controlador
       private partesMO.models.PartesMoModel _dsPartes;

 
/**
 * Initialize the page. Set up listeners and perform other initialization activities.
 * @throws Exception 
 */
public void initialize() throws Exception{
	super.initialize();
	
     addPageListener(this);
     _generarBUT.addSubmitListener(this);
     
     // inserta el registr para periodo y anio
     _dsPeriodo.reset();
     _dsPeriodo.insertRow();
     _dsPeriodo.gotoFirst();
     
     // crea el data source que se va a usar para liquidar
     _dsPartes = new partesMO.models.PartesMoModel(getApplicationName(),"partesmo");
}


@Override
public boolean submitPerformed(SubmitEvent e) throws Exception {
	
	if (e.getComponent() == _generarBUT) {
		// genera liquidación para el mes y anio
		try {
			_displaybox1.setHeadingCaption("Resumiendo...");
			_dsPartes.liquidarPeriodo(_dsPeriodo.getInt("mes"), _dsPeriodo.getInt("anio"));
			
			// recupera resumen
			String SQL = "periodo between '"+Integer.toString(_dsPeriodo.getInt("anio"))
						+"-"+Integer.toString(_dsPeriodo.getInt("mes"))	+"-01' "
						+" and last_day('"+Integer.toString(_dsPeriodo.getInt("anio"))
						+"-"+Integer.toString(_dsPeriodo.getInt("mes"))	+"-01')";
			_dsResHor.retrieve(SQL);
			_displaybox1.setHeadingCaption("Generación de resumen mensual de horas");
		} catch (DataStoreException ex) {
			MessageLog.writeErrorMessage(ex, this);
			_displaybox1.setHeadingCaption("Generación de resumen mensual de horas");
			displayErrorMessage("Error Liquidando Partes: " + ex.getMessage());
			return false;
		} catch (Exception ex) {
			MessageLog.writeErrorMessage(ex, this);
			_displaybox1.setHeadingCaption("Generación de resumen mensual de horas");
			displayErrorMessage("Error General liquidando: " + ex.getMessage());
			return false;
		}
	}
	
	return super.submitPerformed(e);
}
 

}
