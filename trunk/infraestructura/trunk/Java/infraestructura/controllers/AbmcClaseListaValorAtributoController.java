//package statement
package infraestructura.controllers;

import java.sql.SQLException;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;

//Salmon import statements


/**
 * AbmcClaseListaValorAtributoController: a SOFIA generated controller
 */
public class AbmcClaseListaValorAtributoController extends BaseController {

/**
	 * 
	 */
	private static final long serialVersionUID = 7401492965300948321L;
	//Visual Components
      public com.salmonllc.html.HtmlText _buscarCAP5;
      public com.salmonllc.html.HtmlText _descripcionCAP10;
      public com.salmonllc.html.HtmlText _descripcionCAP13;
      public com.salmonllc.html.HtmlText _descripcionCAP19;
      public com.salmonllc.html.HtmlText _descripcionTXT7;
      public com.salmonllc.html.HtmlText _idCAP11;
      public com.salmonllc.html.HtmlText _idTE7;
      public com.salmonllc.html.HtmlText _nombreCAP11;
      public com.salmonllc.html.HtmlText _nombreTXT8;
      public com.salmonllc.html.HtmlText _observacionesCAP15;
      public com.salmonllc.html.HtmlText _observacionesCAP31;
      public com.salmonllc.html.HtmlText _observacionesTXT31;
      public com.salmonllc.html.HtmlText _rolCAP12;
      public com.salmonllc.html.HtmlText _valorCAP20;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlTextEdit _descripcionTE5;
      public com.salmonllc.html.HtmlTextEdit _descripcionTXT16;
      public com.salmonllc.html.HtmlTextEdit _nombreTE6;
      public com.salmonllc.html.HtmlTextEdit _observacionesTE7;
      public com.salmonllc.html.HtmlTextEdit _valorTXT18;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspBox _box3;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspDataTable _datatable2;
      public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
      public com.salmonllc.jsp.JspTable _table2;

//DataSources
      public com.salmonllc.sql.QBEBuilder _dsQBE;
      public infraestructura.models.ClaseLovAtributoModel _dsClaseLOV;
      public infraestructura.models.LovAtributoModel _dsLOV;

//DataSource Column Constants
       public static final String DSLOV_LISTA_VALORES_ATRIBUTO_CLASE_LISTA_VALORES_ATRIBUTO_ID="Lista_valores_atributo.Clase_Lista_valores_atributo_id";
       public static final String DSLOV_LISTA_VALORES_ATRIBUTO_VALOR="Lista_valores_atributo.valor";
       public static final String DSLOV_LISTA_VALORES_ATRIBUTO_DESCRIPCION="Lista_valores_atributo.descripcion";
       public static final String DSLOV_CLASE_LISTA_VALORES_ATRIBUTO_NOMBRE="Clase_Lista_valores_atributo.nombre";

       public static final String DSCLASELOV_CLASE_LISTA_VALORES_ATRIBUTO_CLASE_LISTA_VALORES_ATRIBUTO_ID="Clase_Lista_valores_atributo.Clase_Lista_valores_atributo_id";
       public static final String DSCLASELOV_CLASE_LISTA_VALORES_ATRIBUTO_NOMBRE="Clase_Lista_valores_atributo.nombre";
       public static final String DSCLASELOV_CLASE_LISTA_VALORES_ATRIBUTO_DESCRIPCION="Clase_Lista_valores_atributo.descripcion";
       public static final String DSCLASELOV_CLASE_LISTA_VALORES_ATRIBUTO_OBSERVACIONES="Clase_Lista_valores_atributo.observaciones";

       public static final String DSQBE_BUSCAR="buscar";

//     Componentes custom
       public com.salmonllc.html.HtmlSubmitButton _agregarLovBUT1;
       public com.salmonllc.html.HtmlSubmitButton _grabarLovBUT2;
       public com.salmonllc.html.HtmlSubmitButton _borrarLovBUT3;
       
       public com.salmonllc.html.HtmlCheckBox _seleccion;
       public com.salmonllc.html.HtmlText _selCAP60;

       private String SELECCION_FLAG = "SELECCION_FLAG";
       
	   private static int clase_ant = -1;
 
/**
 * Initialize the page. Set up listeners and perform other initialization activities.
 * @throws Exception 
 */
public void initialize() throws Exception{
     super.initialize();
     
 	// completo botones a los listform
 	_grabarLovBUT2 = new HtmlSubmitButton("grabarLovBUT2","Grabar(S)",this);
 	_grabarLovBUT2.setAccessKey("S");
 	_listformdisplaybox2.addButton(_grabarLovBUT2);
 	
 	_agregarLovBUT1 = new HtmlSubmitButton("agregarLovBUT1","Nuevo",this);
 	_agregarLovBUT1.setAccessKey("N");
 	_listformdisplaybox2.addButton(_agregarLovBUT1);
 	
 	_borrarLovBUT3 = new HtmlSubmitButton("borrarLovBUT3","Borrar",this);
 	_agregarLovBUT1.setAccessKey("B");
 	_listformdisplaybox2.addButton(_borrarLovBUT3);

 	_agregarLovBUT1.addSubmitListener(this);
 	_grabarLovBUT2.addSubmitListener(this);
 	_borrarLovBUT3.addSubmitListener(this);
 	
 	
 	// Agrega columna de seleccion al datasource de informes
 	_dsLOV.addBucket(SELECCION_FLAG, DataStore.DATATYPE_INT);
 	_seleccion.setColumn(_dsLOV, SELECCION_FLAG);
 	_seleccion.setFalseValue(null);

 	
 	_dsClaseLOV.setAutoValidate(true);
 	_dsLOV.setAutoValidate(true);
}



@Override
public boolean submitPerformed(SubmitEvent e) throws Exception {

	if (e.getComponent() == _agregarLovBUT1) {
		// agrega nuevo registro al detalle
		_dsLOV.insertRow();
	}
	
	if (e.getComponent() == _grabarLovBUT2) {

		try {
			// antes reviso el contexto de datos a actualizar o grabar
			int clase = -1;
			if (_dsClaseLOV.getRow() != -1) {

				// recupera el id del registro actual de clase de valores
				clase = _dsClaseLOV.getClaseListaValoresAtributoClaseListaValoresAtributoId();
				
				for (int i = 0; i < _dsLOV.getRowCount() ; i++) {
					
					int clase_tabla = _dsLOV.getListaValoresAtributoClaseLovAtributoId(i);
					if (clase_tabla < 1)
						_dsLOV.setListaValoresAtributoClaseLovAtributoId(i,clase);
				}

				_dsLOV.update();
				
			}
		} catch (DataStoreException ex) {
			displayErrorMessage(ex.getMessage());
			return false;
		}
	}
	
	if (e.getComponent() == _borrarLovBUT3) {
		// Recorro todos los registros y para aquelos marcados les doy la baja
		for (int i = 0; i < _dsLOV.getRowCount(); i++) {
			if (_dsLOV.getInt(i, SELECCION_FLAG) == 1) {
				// Rol marcado para selección
				_dsLOV.deleteRow(i);
				try {
					_dsLOV.update();
				} catch (DataStoreException ex) {
					displayErrorMessage(ex.getMessage());
					return false;
				}
			}
		}
	}

	return super.submitPerformed(e);
}



@Override
public void pageSubmitEnd(PageEvent p) {
	super.pageSubmitEnd(p);

	// ante cada requerimiento verifica contexto y determina detalle de
	// la clase de valores de atributos y completa FK's
	// Es row de rol vÃ¡lida?
	try {
		boolean actualizar = false;
		int clase = -1;
		int valor_clase = -1;
		if (_dsClaseLOV.getRow() != -1) {
				// recupera el id del registro actual de clase de valores
				clase = _dsClaseLOV
						.getClaseListaValoresAtributoClaseListaValoresAtributoId();

				// si la clase anterior no fue seteada la setea y continÃºa, sino
				// la compara para ver cambio de contexto
				if (clase_ant == -1)
					clase_ant = clase;

				if (clase_ant == clase) {
					// si se estÃ¡ insertando un nuevo registro de valores, no se
					// actualiza
					if (!(_dsLOV.getRowStatus() == DataStoreBuffer.STATUS_NEW || _dsLOV
							.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED)) {
						// Ya existe detalle de atributos?
						if (_dsLOV.getRowCount() > 0) {
							// es el mismo contexto? --> recupero el rol del
							// detalle para verificación, siempre del primer
							// registro
							valor_clase = _dsLOV.getListaValoresAtributoClaseLovAtributoId(0);
							if (valor_clase == -1)
								actualizar = true;
							if (valor_clase != -1 && valor_clase != clase) {
								// Es distinto el contexto de la clase de
								// atributo
								actualizar = true;
							}
						} else {
							actualizar = true;
						}
					}
				} else {
					actualizar = true;
					clase_ant = clase;
				}
			}

			if (actualizar) {
				_dsLOV.reset();
				_dsLOV.retrieve("lov_atributo.clase_lov_atributo_id = " + Integer.toString(clase));
				_dsLOV.gotoFirst();
			}
		
	} catch (DataStoreException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

 

}
