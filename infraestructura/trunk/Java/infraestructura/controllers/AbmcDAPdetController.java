//package statement
package infraestructura.controllers;

//Salmon import statements
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;


/**
 * AbmcDAPdetController: a SOFIA generated controller
 */
public class AbmcDAPdetController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = -7498608311894061440L;
	//Visual Components
      public com.salmonllc.html.HtmlSubmitButton _buscarBUT;
      public com.salmonllc.html.HtmlText _nombredetalleCAP15;
      public com.salmonllc.html.HtmlText _nombreitemTXT7;
      public com.salmonllc.html.HtmlText _nombreobjetoCAP11;
      public com.salmonllc.html.HtmlText _nombreobjetoTXT7;
      public com.salmonllc.html.HtmlText _tipodetalleCAP15;
      public com.salmonllc.html.HtmlText _tipoitemTXT6;
      public com.salmonllc.html.HtmlText _tipoobjetoCAP10;
      public com.salmonllc.html.HtmlText _tipoobjetoTXT6;
      public com.salmonllc.html.HtmlTextEdit _nombredetalleTE5;
      public com.salmonllc.html.HtmlDropDownList _nombreTE5;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspDisplayBox _objetoBuscar;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;
      public infraestructura.controllers.DAPSelector _selector;

//DataSource Column Constants
       public static final String DSDAPDET_DICCIONARIO_APLICACION_DETALLE_TIPO_OBJETO="diccionario_aplicacion_detalle.tipo_objeto";
       public static final String DSDAPDET_DICCIONARIO_APLICACION_DETALLE_NOMBRE_OBJETO="diccionario_aplicacion_detalle.nombre_objeto";
       public static final String DSDAPDET_DICCIONARIO_APLICACION_DETALLE_TIPO_DETALLE="diccionario_aplicacion_detalle.tipo_detalle";
       public static final String DSDAPDET_DICCIONARIO_APLICACION_DETALLE_NOMBRE_DETALLE="diccionario_aplicacion_detalle.nombre_detalle";

       public static final String DSQBE_BUSCAR="buscar";

	public void initialize() throws Exception {
		super.initialize();
		
		_buscarBUT.addSubmitListener(this);
	}

	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 */
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		
		if (e.getComponent() == _buscarBUT) {
			String tipo = _selector.getTipoSeleccionado();
			String objeto = _selector.getObjetoSeleccionado();
			
			_dsDAPdet.reset();
			_dsDAPdet.retrieve("tipo_objeto = '"+tipo+"' and nombre_objeto = '"+objeto+"'");
		}
		
		return super.submitPerformed(e);
	}

	public void pageSubmitted(PageEvent p) {
		super.pageSubmitted(p);
		
		// seimpre en funci�n del tipo de objeto lleno la tabla de tipo de �tem
		populateTipoItem();
		
		// chequeo si corresponde actualizar el tipo y nombre del objeto al insertar
		try {
			if (_dsDAPdet.getRow() != -1) {
				String tipo = _dsDAPdet
						.getString("diccionario_aplicacion_detalle.tipo_objeto");
				String objeto = _dsDAPdet
						.getString("diccionario_aplicacion_detalle.nombre_objeto");
				String tipo_sel = _selector.getTipoSeleccionado();
				String nombre_sel = _selector.getObjetoSeleccionado();
				if (tipo == null && objeto == null && tipo_sel != null
						&& nombre_sel != null) {
					_dsDAPdet.setString(
							"diccionario_aplicacion_detalle.tipo_objeto",
							_selector.getTipoSeleccionado());
					_dsDAPdet.setString(
							"diccionario_aplicacion_detalle.nombre_objeto",
							_selector.getObjetoSeleccionado());
				}
			}
		} catch (DataStoreException e) {
			e.printStackTrace();
		}
		   
	}
	
	private void populateTipoItem(){
		String tipo = _selector.getTipoSeleccionado();
		
		_nombreTE5.resetOptions();

		if (tipo != null) {
			if (tipo.equalsIgnoreCase("TABLA") || tipo.equalsIgnoreCase("VIEW")) {
				_nombreTE5.addOption("COLUMNA","Columna");
			}
		}
	}

 

}
