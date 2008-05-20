//package statement
package inventario.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.models.AtributosEntidadModel;
import inventario.models.ArticulosModel;

import java.sql.SQLException;

import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreException;

/**
 * AbmcConversionesController: a SOFIA generated controller
 */
public class AbmcConversionesController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 638833374097966814L;
	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _unidad_medida2;
	public com.salmonllc.html.HtmlLookUpComponent _articulo2;
	public com.salmonllc.html.HtmlMultiLineTextEdit _observaciones2;
	public com.salmonllc.html.HtmlText _articulo1;
	public com.salmonllc.html.HtmlText _articulo3;
	public com.salmonllc.html.HtmlText _articulo4;
	public com.salmonllc.html.HtmlText _articulo_unidad_medida1;
	public com.salmonllc.html.HtmlDropDownList _articulo_unidad_medida2;
	public com.salmonllc.html.HtmlText _factor1;
	public com.salmonllc.html.HtmlText _factor3;
	public com.salmonllc.html.HtmlText _factor4;
	public com.salmonllc.html.HtmlText _observaciones1;
	public com.salmonllc.html.HtmlText _unidad_medida1;
	public com.salmonllc.html.HtmlText _unidad_medida3;
	public com.salmonllc.html.HtmlText _unidad_medida4;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlText _buscarCAP5;
	public com.salmonllc.html.HtmlTextEdit _factor2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspTable _table2;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;
	public inventario.models.ConversionesModel _dsConversiones;

	// DataSource Column Constants
	public static final String DSCONVERSIONES_CONVERSIONES_CONVERSION_ID = "conversiones.conversion_id";
	public static final String DSCONVERSIONES_CONVERSIONES_ARTICULO_ID = "conversiones.articulo_id";
	public static final String DSCONVERSIONES_CONVERSIONES_UNIDAD_MEDIDA_ID = "conversiones.unidad_medida_id";
	public static final String DSCONVERSIONES_CONVERSIONES_FACTOR = "conversiones.factor";
	public static final String DSCONVERSIONES_CONVERSIONES_OBSERVACIONES = "conversiones.observaciones";
	public static final String DSCONVERSIONES_UNIDADES_MEDIDA_NOMBRE = "unidades_medida.nombre";
	public static final String DSCONVERSIONES_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSCONVERSIONES_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSCONVERSIONES_ARTICULOS_OBSERVACIONES = "articulos.observaciones";
	public static final String DSCONVERSIONES_ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String DSCONVERSIONES_ARTICULO_UNIDAD_MEDIDA = "articulo_unidad_medida";

	public static final String DSQBE_BUSCAR = "buscar";

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		_detailformdisplaybox1.getSaveButton().addSubmitListener(this);
		_dsConversiones.setAutoValidate(true);
		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		if (e.getComponent() == _detailformdisplaybox1.getSaveButton()) {
			AtributosEntidadModel.setValorAtributoObjeto(
					_articulo_unidad_medida2.getValue(),
					ARTICULO_UNIDAD_MEDIDA, _dsConversiones
							.getConversionesArticuloId(), "TABLA", "articulos");
		}
		return super.submitPerformed(e);
	}

	/**
	 * Process the page requested event
	 * 
	 * @param event
	 *            the page event to be processed
	 */
	public void pageRequested(PageEvent event) throws Exception {
		try {
			if (_dsConversiones.getRow() != -1) {
			String unidad_patron = AtributosEntidadModel
			.getValorAtributoObjeto(ARTICULO_UNIDAD_MEDIDA,
					_dsConversiones.getConversionesArticuloId(),
					"TABLA", "articulos");		
			if (unidad_patron != "0") 			
					_articulo_unidad_medida2.setValue(unidad_patron);
			else
				displayErrorMessage("Indique la unidad de medida patrón del artículo");
			}
			
			ArticulosModel dsArticulos = new ArticulosModel("inventario");
			if (!isReferredByCurrentPage()) {
				int articulo_id = getIntParameter("articulo_id");
				int unidad_medida_id = getIntParameter("unidad_medida_id");
				if (articulo_id != 0 && unidad_medida_id != 0) {
					_dsConversiones.retrieve("conversiones.articulo_id ="
							+ articulo_id
							+ " AND conversiones.unidad_medida_id ="
							+ unidad_medida_id);
					if (!_dsConversiones.gotoFirst()) {
						_dsConversiones.gotoRow(_dsConversiones.insertRow());
						_dsConversiones.setConversionesArticuloId(articulo_id);
						_dsConversiones
								.setConversionesUnidadMedidaId(unidad_medida_id);
						dsArticulos.retrieve("articulos.articulo_id ="
								+ articulo_id);
						dsArticulos.gotoFirst();
						_dsConversiones.setArticulosNombre(dsArticulos
								.getArticulosNombre());
						_dsConversiones.setArticulosDescripcion(dsArticulos
								.getArticulosDescripcion());
						_dsConversiones
								.setArticulosDescripcionCompleta(dsArticulos
										.getArticulosDescripcionCompleta());
						_dsConversiones.update();
					}
				}

			}
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			displayErrorMessage(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			displayErrorMessage(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			displayErrorMessage(e.getMessage());
		}
		super.pageRequested(event);
	}
}
