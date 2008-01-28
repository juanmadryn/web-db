//package statement
package inventario.controllers;

//Salmon import statements
import java.sql.SQLException;
import java.util.ArrayList;

import infraestructura.controllers.BaseController;
import infraestructura.reglasNegocio.ValidationException;
import inventario.models.ArticulosModel;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * AbmcArticulosConstroller: a SOFIA generated controller
 */
public class AbmcArticuloController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7914706895539178446L;
	
	private static final String TABLA_PRINCIPAL = "articulos";
	
	//Visual Components
	// public com.salmonllc.html.HtmlCheckBox _estadoCB8;
	// public com.salmonllc.html.HtmlCheckBox _estadoCB9;
	public com.salmonllc.html.HtmlDropDownList _activoSE8;
	public com.salmonllc.html.HtmlDropDownList _anuladoSE9;
	public com.salmonllc.html.HtmlLookUpComponent _claseTE4;
	public com.salmonllc.html.HtmlLookUpComponent _valorTE11;
	public com.salmonllc.html.HtmlMultiLineTextEdit _descripcionCompTE4;
	public com.salmonllc.html.HtmlText _atributoCAP15;
	public com.salmonllc.html.HtmlText _atributoTXT6;
	public com.salmonllc.html.HtmlText _claseCAP1;
	public com.salmonllc.html.HtmlText _claveExtCAP5;
	public com.salmonllc.html.HtmlText _claveExtTCAP5;
	public com.salmonllc.html.HtmlText _claveExtTCAP6;
	public com.salmonllc.html.HtmlText _claveExtTCAP7;
	public com.salmonllc.html.HtmlText _descripcionCAP3;
	public com.salmonllc.html.HtmlText _descripcionCompCAP4;
	public com.salmonllc.html.HtmlText _estadoCAP8;
	public com.salmonllc.html.HtmlText _estadoCAP9;
	public com.salmonllc.html.HtmlText _idCAP5;
	public com.salmonllc.html.HtmlText _idTXT5;
	public com.salmonllc.html.HtmlText _nombreCAP1;
	public com.salmonllc.html.HtmlText _observacionesCAP2;
	public com.salmonllc.html.HtmlText _valorCAP16;
	public com.salmonllc.html.HtmlTextEdit _claveExtTE5;
	public com.salmonllc.html.HtmlTextEdit _claveExtTE6;
	public com.salmonllc.html.HtmlTextEdit _claveExtTE7;
	public com.salmonllc.html.HtmlTextEdit _descripcionTE3;
	public com.salmonllc.html.HtmlTextEdit _nombreTE1;
	public com.salmonllc.html.HtmlTextEdit _observacionesTE2;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
		
	//DataSources
	public infraestructura.models.AtributosEntidadModel _dsAtributos;
	public inventario.models.ArticulosModel _dsArticulo;

	//DataSource Column Constants
	public static final String DSARTICULO_ARTICULOS_ARTICULO_ID = "articulos.articulo_id";
	public static final String DSARTICULO_ARTICULOS_CLASE_ARTICULO_ID = "articulos.clase_articulo_id";
	public static final String DSARTICULO_ARTICULOS_NOMBRE = "articulos.nombre";
	public static final String DSARTICULO_ARTICULOS_DESCRIPCION = "articulos.descripcion";
	public static final String DSARTICULO_ARTICULOS_OBSERVACIONES = "articulos.observaciones";
	public static final String DSARTICULO_ARTICULOS_DESCRIPCION_COMPLETA = "articulos.descripcion_completa";
	public static final String DSARTICULO_ARTICULOS_CLAVE_EXTERNA1 = "articulos.clave_externa1";
	public static final String DSARTICULO_ARTICULOS_CLAVE_EXTERNA2 = "articulos.clave_externa2";
	public static final String DSARTICULO_ARTICULOS_CLAVE_EXTERNA3 = "articulos.clave_externa3";
	public static final String DSARTICULO_ARTICULOS_ACTIVO = "articulos.activo";
	public static final String DSARTICULO_ARTICULOS_ANULADO = "articulos.anulado";
	public static final String DSARTICULO_CLASE_ARTICULO_NOMBRE = "clase_articulo.nombre";
	public static final String DSARTICULO_CLASE_ARTICULO_DESCRIPCION = "clase_articulo.descripcion";

	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_ATRIBUTO_ENTIDAD_ID = "atributos_entidad.atributo_entidad_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_ATRIBUTO_ID = "atributos_entidad.atributo_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_ENTIDAD_ID = "atributos_entidad.entidad_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_OBJETO_ID = "atributos_entidad.objeto_id";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_TIPO_OBJETO = "atributos_entidad.tipo_objeto";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_NOMBRE_OBJETO = "atributos_entidad.nombre_objeto";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR = "atributos_entidad.valor";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_ENTERO = "atributos_entidad.valor_entero";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_REAL = "atributos_entidad.valor_real";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_FECHA = "atributos_entidad.valor_fecha";
	public static final String DSATRIBUTOS_ATRIBUTOS_ENTIDAD_VALOR_LOGICO = "atributos_entidad.valor_logico";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_NOMBRE = "atributos_rol.nombre";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_ROL = "atributos_rol.rol";
	public static final String DSATRIBUTOS_ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";
	public static final String DSATRIBUTOS_CLASE_ATRIBUTO_ROL_ETIQUETA = "clase_atributo_rol.etiqueta";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_NOMBRE_OBJETO = "atributos_rol.nombre_objeto";
	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_TIPO_OBJETO = "atributos_rol.tipo_objeto";

	// Custom (not present in the JSP)
	public HtmlSubmitButton _grabarArticuloBUT;
	public HtmlSubmitButton _nuevoArticuloBUT;
	public HtmlSubmitButton _eliminaArticuloBUT;
	public HtmlSubmitButton _recargarBUT;
	public HtmlSubmitButton _grabarAtributoBUT1;
	public HtmlSubmitButton _atributoGenerarAtributosBUT11;
	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT1;
	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT2;
	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT3;
	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT4;
	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT5;
	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT6;
	
	private int botonSeleccionado = -1;
	private boolean recargar = false;
	
	/**
	 * Initialize the page. Set up listeners and perform other initialization activities.
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		// genera botones custom
		_grabarArticuloBUT = new HtmlSubmitButton("grabarParteBUT3","Grabar",this);
		_grabarArticuloBUT.setAccessKey("g");
		_detailformdisplaybox1.addButton(_grabarArticuloBUT);
				
		_nuevoArticuloBUT = new HtmlSubmitButton("nuevoParteNuevoBUT2","Nuevo Artículo",this);
		_nuevoArticuloBUT.setAccessKey("n");
		_detailformdisplaybox1.addButton(_nuevoArticuloBUT);
		
		_eliminaArticuloBUT = new HtmlSubmitButton("eliminaParteBUT4","Eliminar",this);
		_eliminaArticuloBUT.setAccessKey("e");
		_detailformdisplaybox1.addButton(_eliminaArticuloBUT);
		
		_recargarBUT = new HtmlSubmitButton("recargarBUT5","Recargar",this);
		_recargarBUT.setAccessKey("r");
		_detailformdisplaybox1.addButton(_recargarBUT);
		
		// botones para atributos
		_grabarAtributoBUT1 = new HtmlSubmitButton("grabarAtributoBUT1",
				"grabar", this);
		_listformdisplaybox1.addButton(_grabarAtributoBUT1);
		_atributoGenerarAtributosBUT11 = new HtmlSubmitButton(
				"atributoGenerarAtributosBUT11", "generar", this);
		_listformdisplaybox1.addButton(_atributoGenerarAtributosBUT11);

		_atributoEtiquetaBUT1 = new HtmlSubmitButton("atributoEtiquetaBUT1",
				"etiqueta 1", this);
		_listformdisplaybox1.addButton(_atributoEtiquetaBUT1);
		_atributoEtiquetaBUT2 = new HtmlSubmitButton("atributoEtiquetaBUT2",
				"etiqueta 2", this);
		_listformdisplaybox1.addButton(_atributoEtiquetaBUT2);
		_atributoEtiquetaBUT3 = new HtmlSubmitButton("atributoEtiquetaBUT3",
				"etiqueta 3", this);
		_listformdisplaybox1.addButton(_atributoEtiquetaBUT3);
		_atributoEtiquetaBUT4 = new HtmlSubmitButton("atributoEtiquetaBUT4",
				"etiqueta 4", this);
		_listformdisplaybox1.addButton(_atributoEtiquetaBUT4);
		_atributoEtiquetaBUT5 = new HtmlSubmitButton("atributoEtiquetaBUT5",
				"etiqueta 5", this);
		_listformdisplaybox1.addButton(_atributoEtiquetaBUT5);
		_atributoEtiquetaBUT6 = new HtmlSubmitButton("atributoEtiquetaBUT6",
				"etiqueta 6", this);
		_listformdisplaybox1.addButton(_atributoEtiquetaBUT6);

		// listeners
		_grabarArticuloBUT.addSubmitListener(this);
		_nuevoArticuloBUT.addSubmitListener(this);
		_eliminaArticuloBUT.addSubmitListener(this);
		_recargarBUT.addSubmitListener(this);
		_grabarAtributoBUT1.addSubmitListener(this);
		_atributoGenerarAtributosBUT11.addSubmitListener(this);
		_atributoEtiquetaBUT1.addSubmitListener(this);
		_atributoEtiquetaBUT2.addSubmitListener(this);
		_atributoEtiquetaBUT3.addSubmitListener(this);
		_atributoEtiquetaBUT4.addSubmitListener(this);
		_atributoEtiquetaBUT5.addSubmitListener(this);
		_atributoEtiquetaBUT6.addSubmitListener(this);
		
		_dsArticulo.setAutoValidate(true);
		
		_dsAtributos.reset();
		_dsArticulo.reset();		
		_dsArticulo.insertRow();
		_dsArticulo.gotoFirst();
		
		// setea primera visualización
		seteaBotonesAtributos(-1);
		seteaNuevoBoton(-1);
		
		_nombreTE1.setFocus();
	}
	
	/* (non-Javadoc)
	 * @see infraestructura.controllers.BaseController#submitPerformed(com.salmonllc.html.events.SubmitEvent)
	 */
	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		
		// Save the item
		if (e.getComponent() == _grabarArticuloBUT) {
			// grabo todos los datasource
			int v_objeto_id = 0;
			try {
				if (_dsArticulo.getRow() == -1)
					return false;
				_dsArticulo.update();		
				
				// genero atributos faltantes si los hubiera, es decir, los
				// inserto en la tabla de atributos con sus valores en null				
				v_objeto_id = _dsArticulo.getArticulosArticuloId();
				if (_dsAtributos.getRow() == -1) {
					if (v_objeto_id < 1) {
						displayErrorMessage("Debe seleccionar un artículo para poder generar sus atributos");
						return false;
					}
					// manda a generar los atributos de la entidad
					_dsAtributos.generaAtributosObjetoAplicacion(
							v_objeto_id, TABLA_PRINCIPAL);
				}
				
				// actulizo atributos
				_dsAtributos.update();
				// recupero atributos "Generales"
				_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
						null, v_objeto_id, TABLA_PRINCIPAL);
				
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());				
			} catch (SQLException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage(ex.getMessage());
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsArticulo);
				displayErrorMessage(ex.getMessage());
				
			} finally {
				seteaBotonesAtributos(v_objeto_id);
				recuperaAtributosBotonSeleccionado(v_objeto_id,
						TABLA_PRINCIPAL);
			}
		}
		
		if (e.getComponent() == _recargarBUT) {
			this.recargar = false;
			pageRequested(new PageEvent(this));
		}
		
		if (e.getComponent() == _nuevoArticuloBUT) {
			_dsAtributos.reset();
			_dsArticulo.reset();			
			_dsArticulo.gotoRow(_dsArticulo.insertRow());			
		}
		
		if (e.getComponent() == _eliminaArticuloBUT) {					
			_dsArticulo.deleteRow();			
			_dsArticulo.update();				
			_dsArticulo.reset();
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT1) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsArticulo.getArticulosArticuloId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(1);
				recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT2) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsArticulo.getArticulosArticuloId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(2);
				recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT3) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsArticulo.getArticulosArticuloId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(3);
				recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT4) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsArticulo.getArticulosArticuloId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(4);
				recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT5) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsArticulo.getArticulosArticuloId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(5);
				recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT6) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsArticulo.getArticulosArticuloId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(6);
				recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}
		
		// graba atributos de entidad
		if (e.getComponent() == _grabarAtributoBUT1) {
			try {
				_dsAtributos.update();
			} catch (ValidationException ex) {
				for (String er : ex.getStackErrores())
					displayErrorMessage(er);
				MessageLog.writeErrorMessage(ex, null);
				return false;

			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// genera atributos, por si faltan
		if (e.getComponent() == _atributoGenerarAtributosBUT11) {
			// primero determina contexto
			int v_objeto_id = _dsArticulo.getArticulosArticuloId();
			if (v_objeto_id < 1) {
				displayErrorMessage("Debe seleccionar un artículo para poder generar sus atributos");
				return false;
			}

			// manda a generar los atributos de la entidad
			try {
				_dsAtributos.generaAtributosObjetoAplicacion(v_objeto_id,
						TABLA_PRINCIPAL);
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
			seteaBotonesAtributos(v_objeto_id);
			recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
		}
		
		return super.submitPerformed(e);
	}
	
	@Override
	public void pageRequested(PageEvent p) throws Exception {
		int v_articulo_id = -1;

		// si la página es requerida por si misma no hago nada
		if (!isReferredByCurrentPage() || this.recargar) {
			_dsAtributos.reset();
			_dsArticulo.reset();					
			
			v_articulo_id = this.recargar ? _dsArticulo.getArticulosArticuloId()
					: getIntParameter("p_articulo_id");
			
			if (v_articulo_id > 0){				
				// recupera toda la información del artículo
				_dsArticulo.retrieve(ArticulosModel.ARTICULOS_ARTICULO_ID + " = " + Integer.toString(v_articulo_id));
				_dsArticulo.gotoFirst();				
				// genero atributos si faltan
				_dsAtributos.generaAtributosObjetoAplicacion(v_articulo_id,
						TABLA_PRINCIPAL);
				// setea los botones de los atributos
				seteaBotonesAtributos(v_articulo_id);
				seteaNuevoBoton(botonSeleccionado);

				// recupera la información del boton seleccionado
				recuperaAtributosBotonSeleccionado(v_articulo_id,
						TABLA_PRINCIPAL);
				_dsAtributos.gotoFirst();
			}
			else {
				_dsArticulo.insertRow();
				_dsArticulo.gotoFirst();
			}
			_nombreTE1.setFocus();
		}
		
		super.pageRequested(p);
	}	
	
	private void seteaNuevoBoton(int nuevoBoton) {
		// resetea el boton actual y setea el nuevo
		if (nuevoBoton == -1) {
			// no hay botón seleccionado, reseteo todos los botones
			botonSeleccionado = nuevoBoton;
			_atributoEtiquetaBUT1.setButtonBgColor("lightGray");
			_atributoEtiquetaBUT2.setButtonBgColor("lightGray");
			_atributoEtiquetaBUT3.setButtonBgColor("lightGray");
			_atributoEtiquetaBUT4.setButtonBgColor("lightGray");
			_atributoEtiquetaBUT5.setButtonBgColor("lightGray");
			_atributoEtiquetaBUT6.setButtonBgColor("lightGray");
		} else {
			// es un boton válido seteo a nuevo botón
			switch (botonSeleccionado) {
			case 1:
				_atributoEtiquetaBUT1.setButtonBgColor("lightGray");
				break;
			case 2:
				_atributoEtiquetaBUT2.setButtonBgColor("lightGray");
				break;
			case 3:
				_atributoEtiquetaBUT3.setButtonBgColor("lightGray");
				break;
			case 4:
				_atributoEtiquetaBUT4.setButtonBgColor("lightGray");
				break;
			case 5:
				_atributoEtiquetaBUT5.setButtonBgColor("lightGray");
				break;
			case 6:
				_atributoEtiquetaBUT6.setButtonBgColor("lightGray");
				break;
			}

			botonSeleccionado = nuevoBoton;

			// enfatiza el nuevo boton
			switch (botonSeleccionado) {
			case 1:
				_atributoEtiquetaBUT1.setButtonBgColor("red");
				break;
			case 2:
				_atributoEtiquetaBUT2.setButtonBgColor("red");
				break;
			case 3:
				_atributoEtiquetaBUT3.setButtonBgColor("red");
				break;
			case 4:
				_atributoEtiquetaBUT4.setButtonBgColor("red");
				break;
			case 5:
				_atributoEtiquetaBUT5.setButtonBgColor("red");
				break;
			case 6:
				_atributoEtiquetaBUT6.setButtonBgColor("red");
				break;
			}
		}
	}
	
	/**
	 * Recupera los atributos correspondientes a la etiqueta del botón
	 * seleccionado para el objeto .
	 * 
	 * @param p_objeto_id
	 *            el id del registro para la cual se desean recuperar los
	 *            atributos
	 * @param nombre_tabla
	 *            nombre de la tabla para la cual se desean recuperar los
	 *            atributos
	 * @throws SQLException
	 *             en caso de que surja un error de conexión o interacción con
	 *             la base de datos
	 * @throws DataStoreException
	 *             en caso de que ocurra un error de manejos de los DataStore's
	 *             TODO generalizar este método a un número indefinido de
	 *             etiquetas para atributos
	 */
	private void recuperaAtributosBotonSeleccionado(int p_objeto_id,
			String nombre_tabla) throws SQLException, DataStoreException {

		switch (botonSeleccionado) {
		case 1:
			if (_atributoEtiquetaBUT1.getDisplayName().equalsIgnoreCase(
					"General"))
				_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(null,
						p_objeto_id, nombre_tabla);
			else
				_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
						_atributoEtiquetaBUT1.getDisplayName(), p_objeto_id,
						nombre_tabla);
			break;
		case 2:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT2.getDisplayName(), p_objeto_id,
					nombre_tabla);
			break;
		case 3:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT3.getDisplayName(), p_objeto_id,
					nombre_tabla);

			break;
		case 4:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT4.getDisplayName(), p_objeto_id,
					nombre_tabla);
			break;
		case 5:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT5.getDisplayName(), p_objeto_id,
					nombre_tabla);
			break;
		case 6:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT6.getDisplayName(), p_objeto_id,
					nombre_tabla);
			break;
		}
	}

	/**
	 * Arma la botonera de atributos en función del id de proyecto indicado.
	 * 
	 * @param p_articulo_id
	 *            el id del proyecto para el cual se quieren setear los botones
	 * 
	 * TODO generalizar este método a un número indefinido de etiquetas para
	 * atributos
	 */
	private void seteaBotonesAtributos(int p_articulo_id) {

		ArrayList<String> et = null;

		// resetea la botonera
		_atributoEtiquetaBUT1.setVisible(false);
		_atributoEtiquetaBUT2.setVisible(false);
		_atributoEtiquetaBUT3.setVisible(false);
		_atributoEtiquetaBUT4.setVisible(false);
		_atributoEtiquetaBUT5.setVisible(false);
		_atributoEtiquetaBUT6.setVisible(false);

		// si no hay seteado proyecto termina
		if (p_articulo_id < 1)
			return;

		// setea los botones de los atributos según las etiquetas
		try {
			et = _dsAtributos
					.recuperaEtiquetasAtributosObjetoAplicacion(TABLA_PRINCIPAL);
		} catch (DataStoreException e) {
			displayErrorMessage(e.getLocalizedMessage());
			return;
		} catch (SQLException e) {
			displayErrorMessage(e.getLocalizedMessage());
			return;
		}
		if (et != null) {
			for (int i = 0; i < et.size(); i++) {
				switch (i) {
				case 0:
					_atributoEtiquetaBUT1.setDisplayName((String) et.get(i));
					if (_atributoEtiquetaBUT1.getDisplayName() == null
							|| _atributoEtiquetaBUT1.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT1.setDisplayName("General");
					_atributoEtiquetaBUT1.setVisible(true);
					break;
				case 1:
					_atributoEtiquetaBUT2.setDisplayName((String) et.get(i));
					if (_atributoEtiquetaBUT2.getDisplayName() == null
							|| _atributoEtiquetaBUT2.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT2.setDisplayName("General");
					_atributoEtiquetaBUT2.setVisible(true);
					break;
				case 2:
					_atributoEtiquetaBUT3.setDisplayName((String) et.get(i));
					if (_atributoEtiquetaBUT3.getDisplayName() == null
							|| _atributoEtiquetaBUT3.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT3.setDisplayName("General");
					_atributoEtiquetaBUT3.setVisible(true);
					break;
				case 3:
					_atributoEtiquetaBUT4.setDisplayName((String) et.get(i));
					if (_atributoEtiquetaBUT4.getDisplayName() == null
							|| _atributoEtiquetaBUT4.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT4.setDisplayName("General");
					_atributoEtiquetaBUT4.setVisible(true);
					break;
				case 4:
					_atributoEtiquetaBUT5.setDisplayName((String) et.get(i));
					if (_atributoEtiquetaBUT5.getDisplayName() == null
							|| _atributoEtiquetaBUT5.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT5.setDisplayName("General");
					_atributoEtiquetaBUT5.setVisible(true);
					break;
				case 5:
					_atributoEtiquetaBUT6.setDisplayName((String) et.get(i));
					if (_atributoEtiquetaBUT6.getDisplayName() == null
							|| _atributoEtiquetaBUT6.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT6.setDisplayName("General");
					_atributoEtiquetaBUT6.setVisible(true);
					break;
				}
			}
			// seteo el primero
			seteaNuevoBoton(1);
		} else
			seteaNuevoBoton(-1);
	}
	
	@Override
	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);
		// ante cada requerimiento verifica contexto y determina detalle de
		// atributos y completa FK's
		// Es row de rol válida?
		try {
			boolean actualizar = false;
			int row = _dsArticulo.getRow();
			int articulo_id = 0;
			int articulo_id_atributos = 0;
			if (row != -1) {
				// recupera el id del proyecto de contexto
				articulo_id = _dsArticulo.getArticulosArticuloId();
				// si se está insertando un nuevo registro de rol, no se
				// actualiza
				if (!(_dsAtributos.getRowStatus() == DataStoreBuffer.STATUS_NEW || _dsAtributos
						.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED)) {
					// Ya existe detalle de atributos?
					if (_dsAtributos.getRowCount() > 0) {
						// es el mismo contexto? --> recupero la entidad del
						// detalle para verificación, siempre del primer
						// registro
						articulo_id_atributos = _dsAtributos
								.getAtributosEntidadObjetoId(0);
						if (articulo_id_atributos == 0)
							actualizar = true;
						if (articulo_id_atributos != articulo_id) {
							// Es distinto el contexto del rol de atributo
							actualizar = true;
						}
					} else {
						actualizar = true;
					}
				}
			}
			if (actualizar) {
				actualizarDetalles(articulo_id);
			}
		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		} catch (SQLException e) {
			displayErrorMessage(e.getMessage());
		}

	}

	public void actualizarDetalles(int p_articulo_id) throws SQLException,
			DataStoreException {
		_dsAtributos.reset();
		seteaBotonesAtributos(p_articulo_id);
		recuperaAtributosBotonSeleccionado(p_articulo_id, TABLA_PRINCIPAL);
	}	
}
