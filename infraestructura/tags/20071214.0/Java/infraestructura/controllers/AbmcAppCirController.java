//package statement
package infraestructura.controllers;

//Salmon import statements
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;


/**
 * AbmcAppCirController: a SOFIA generated controller
 */
public class AbmcAppCirController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = 4411844802870506626L;
	//Visual Components
      public com.salmonllc.html.HtmlDropDownList _circuitoTE5;
      public com.salmonllc.html.HtmlSubmitButton _actualizarBUT;
      public com.salmonllc.html.HtmlSubmitButton _buscarBUT;
      public com.salmonllc.html.HtmlText _circuitoCAP17;
      public com.salmonllc.html.HtmlText _columnaCAP17;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspDisplayBox _objetoBuscar;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspTable _table2;
      public infraestructura.controllers.DDAPSelector _selector;

//DataSource Column Constants
       public static final String DSAPPCIR_APLICA_CIRCUITO_TIPO_OBJETO="aplica_circuito.tipo_objeto";
       public static final String DSAPPCIR_APLICA_CIRCUITO_NOMBRE_OBJETO="aplica_circuito.nombre_objeto";
       public static final String DSAPPCIR_APLICA_CIRCUITO_TIPO_DETALLE="aplica_circuito.tipo_detalle";
       public static final String DSAPPCIR_APLICA_CIRCUITO_NOMBRE_DETALLE="aplica_circuito.nombre_detalle";
       public static final String DSAPPCIR_APLICA_CIRCUITO_CIRCUITO="aplica_circuito.circuito";
       public static final String DSAPPCIR_CIRCUITOS_ESTADOS_CIRCUITO="circuitos_estados.circuito";
       public static final String DSAPPCIR_CIRCUITOS_ESTADOS_NOMBRE="circuitos_estados.nombre";
       

	public void initialize() throws Exception {
		super.initialize();

		_actualizarBUT.addSubmitListener(this);
	     _buscarBUT.addSubmitListener(this);
	}


	public boolean submitPerformed(SubmitEvent e) throws Exception, DataStoreException {
		
		if (e.getComponent() == _buscarBUT){
			String tipo = _selector.getTipoSeleccionado();
			String objeto = _selector.getObjetoSeleccionado();
			String tipo_detalle = _selector.getTipoDetalleSeleccionado();
			String nombre_detalle = _selector.getNombreDetalleSeleccionado();
			
			_dsAppCir.reset();
			_dsAppCir.retrieve("tipo_objeto = '"
						+ tipo +"' and nombre_objeto = '"
						+ objeto +"' and tipo_detalle = '" 
						+ tipo_detalle + "' and nombre_detalle = '"
						+ nombre_detalle + "'");
			if (_dsAppCir.getRowCount() == 0) {
				// no recupera nada, reseteo la selecci�n
				_circuitoTE5.setSelectedIndex(0);
			}
			if (_dsAppCir.getRowCount() == 1){
				// seleccion� ex�ctamente 1 seteo el valor del drop
				for (int i = 0 ; i < _circuitoTE5.getOptionCount() ; i++) {
					String circuito = _dsAppCir.getString(0,DSAPPCIR_APLICA_CIRCUITO_CIRCUITO);
					if (_circuitoTE5.getOptionKey(i) != null &&_circuitoTE5.getOptionKey(i).equalsIgnoreCase(circuito)){
						_circuitoTE5.setSelectedIndex(i);
					}
				}
			}
			if (_dsAppCir.getRowCount() > 1) {
				displayErrorMessage("Se recuper� mas de un circuito para la Columna...");
			}
		}
		
		if (e.getComponent() == _actualizarBUT) {
			int row = _dsAppCir.getRow();
			if (row == -1) {
				row = _dsAppCir.insertRow();
			}
			if (row != -1){
				String circuito = _circuitoTE5.getValue();
				_dsAppCir.setString(row, DSAPPCIR_APLICA_CIRCUITO_TIPO_OBJETO,_selector.getTipoSeleccionado());
				_dsAppCir.setString(row,DSAPPCIR_APLICA_CIRCUITO_NOMBRE_OBJETO, _selector.getObjetoSeleccionado());
				_dsAppCir.setString(row, DSAPPCIR_APLICA_CIRCUITO_TIPO_DETALLE,_selector.getTipoDetalleSeleccionado());
				_dsAppCir.setString(row,DSAPPCIR_APLICA_CIRCUITO_NOMBRE_DETALLE, _selector.getNombreDetalleSeleccionado());
				_dsAppCir.setString(row, DSAPPCIR_APLICA_CIRCUITO_CIRCUITO,circuito);

				try {
					_dsAppCir.update();
				} catch (DataStoreException ex){
					displayErrorMessage(ex.getMessage());
					return false;
				}
			}
		}
		
		return super.submitPerformed(e);
	}


}
