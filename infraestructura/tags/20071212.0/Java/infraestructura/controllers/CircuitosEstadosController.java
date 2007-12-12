//package statement
package infraestructura.controllers;

//Salmon import statements
import java.sql.SQLException;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.sql.DataStoreException;

/**
 * CircuitosEstadosController: a SOFIA generated controller
 */
public class CircuitosEstadosController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6409225481340153678L;

	//Visual Components
	public com.salmonllc.html.HtmlText _buscarCAP5;

	public com.salmonllc.html.HtmlText _circuitoCAP12;

	public com.salmonllc.html.HtmlText _circuitoCAP9;

	public com.salmonllc.html.HtmlText _circuitoTXT6;

	public com.salmonllc.html.HtmlText _descripcionCAP10;

	public com.salmonllc.html.HtmlText _descripcionCAP13;

	public com.salmonllc.html.HtmlText _descripcionCAP19;

	public com.salmonllc.html.HtmlText _descripcionCAP24;

	public com.salmonllc.html.HtmlText _descripcionTXT16;

	public com.salmonllc.html.HtmlText _descripcionTXT7;

	public com.salmonllc.html.HtmlText _estadoCAP20;

	public com.salmonllc.html.HtmlText _estadoCAP22;

	public com.salmonllc.html.HtmlText _estadoTXT17;

	public com.salmonllc.html.HtmlText _nombreCAP11;

	public com.salmonllc.html.HtmlText _nombreCAP14;

	public com.salmonllc.html.HtmlText _nombreCAP21;

	public com.salmonllc.html.HtmlText _nombreCAP23;

	public com.salmonllc.html.HtmlText _nombreTXT18;

	public com.salmonllc.html.HtmlText _nombreTXT8;

	public com.salmonllc.html.HtmlText _observacionesCAP15;

	public com.salmonllc.html.HtmlText _observacionesCAP25;

	public com.salmonllc.html.HtmlTextEdit _buscarTE3;

	public com.salmonllc.html.HtmlTextEdit _circuitoTE4;

	public com.salmonllc.html.HtmlTextEdit _descripcionTE10;

	public com.salmonllc.html.HtmlTextEdit _descripcionTE5;

	public com.salmonllc.html.HtmlTextEdit _estadoTE8;

	public com.salmonllc.html.HtmlTextEdit _nombreTE6;

	public com.salmonllc.html.HtmlTextEdit _nombreTE9;

	public com.salmonllc.html.HtmlTextEdit _observacionesTE11;

	public com.salmonllc.html.HtmlTextEdit _observacionesTE7;

	public com.salmonllc.jsp.JspBox _box1;

	public com.salmonllc.jsp.JspBox _box2;

	public com.salmonllc.jsp.JspBox _box3;

	public com.salmonllc.jsp.JspBox _box4;

	public com.salmonllc.jsp.JspDataTable _datatable1;

	public com.salmonllc.jsp.JspDataTable _datatable2;

	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;

	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox2;

	public com.salmonllc.jsp.JspForm _pageForm;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;

	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;

	public com.salmonllc.jsp.JspTable _table2;

	//DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;

	//DataSource Column Constants
	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSCIRCUITO_CIRCUITOS_ESTADOS_CIRCUITO = "circuitos_estados.circuito";

	public static final String DSCIRCUITO_CIRCUITOS_ESTADOS_NOMBRE = "circuitos_estados.nombre";

	public static final String DSCIRCUITO_CIRCUITOS_ESTADOS_DESCRIPCION = "circuitos_estados.descripcion";

	public static final String DSCIRCUITO_CIRCUITOS_ESTADOS_OBSERVACIONES = "circuitos_estados.observaciones";

	public static final String DSESTADO_ESTADOS_ESTADO = "estados.estado";

	public static final String DSESTADO_ESTADOS_NOMBRE = "estados.nombre";

	public static final String DSESTADO_ESTADOS_DESCRIPCION = "estados.descripcion";

	public static final String DSESTADO_ESTADOS_OBSERVACIONES = "estados.observaciones";

	public static final String DSESTADO_ESTADOS_CIRCUITO = "estados.circuito";

	public static final String DSESTADO_CIRCUITOS_ESTADOS_NOMBRE = "circuitos_estados.nombre";

	public static final String DSESTADO_CIRCUITOS_ESTADOS_DESCRIPCION = "circuitos_estados.descripcion";

	public void initialize() throws Exception {
		super.initialize();
	}

	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);
		
		// ante cada requerimiento verifica contexto y determina detalle de
		// estados y completa FK's
		// Es row de circuito v�lida?
		try {
			boolean actualizar = false;
			String circuito = null;
			String circuito_estado = null;
			if (_dsCircuito.getRow() != -1) {
				circuito = _dsCircuito.getString(DSCIRCUITO_CIRCUITOS_ESTADOS_CIRCUITO);
				// Ya existe detalle de circuitos?
				if (_dsEstado.getRowCount() > 0) {
					// es el mismo contexto?
					circuito_estado = _dsEstado.getString(0,DSESTADO_ESTADOS_CIRCUITO);
					if (circuito_estado != null && ! circuito_estado.equalsIgnoreCase(circuito)) {
						// mismo contexto de circuito
						actualizar = true;
					}
				}
				else {
					actualizar = true;
				}
			}
			if (actualizar) {
				_dsEstado.reset();
				_dsEstado.retrieve("estados.circuito = '" + circuito + "'");
				_dsEstado.gotoFirst();
			}
			
			// reviso el contexto de datos a actualizar o grabar
			if (_dsCircuito.getRow() != -1 && _dsEstado.getRow() != -1 && _dsEstado.getString(DSESTADO_ESTADOS_CIRCUITO) == null){
				_dsEstado.setString(DSESTADO_ESTADOS_CIRCUITO,circuito);
			}
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}

}
