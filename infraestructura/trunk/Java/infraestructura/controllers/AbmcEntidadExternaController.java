//package statement
package infraestructura.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;

//Salmon import statements

/**
 * AbmcEntidadExternaController: a SOFIA generated controller
 */
public class AbmcEntidadExternaController extends BaseController {

	/**
	 *
	 */
	private static final long serialVersionUID = 7737790935379289410L;

	// Visual Components
	public com.salmonllc.html.HtmlCheckBox _seleccion1;

	public com.salmonllc.html.HtmlCheckBox _seleccion2;

	private String SELECCION_FLAG1 = "SELECCION_FLAG1";

	private String SELECCION_FLAG2 = "SELECCION_FLAG2";

	public com.salmonllc.html.HtmlLookUpComponent _rolTE6;

	public com.salmonllc.html.HtmlLookUpComponent _valorTE9;

	public com.salmonllc.html.HtmlText _activoCAP12;

	public com.salmonllc.html.HtmlText _activoCAP8;

	public com.salmonllc.html.HtmlText _activoTXT2;

	public com.salmonllc.html.HtmlText _activoTXT3;

	public com.salmonllc.html.HtmlText _atributoCAP13;

	public com.salmonllc.html.HtmlText _atributoTXT4;

	public com.salmonllc.html.HtmlText _buscarCAP1;

	public com.salmonllc.html.HtmlText _codigoCAP4;

	public com.salmonllc.html.HtmlText _descripcionCAP6;

	public com.salmonllc.html.HtmlText _idCAP3;

	public com.salmonllc.html.HtmlText _idTXT1;

	public com.salmonllc.html.HtmlText _nombreCAP5;

	public com.salmonllc.html.HtmlText _observacionesCAP7;

	public com.salmonllc.html.HtmlText _rolCAP10;

	public com.salmonllc.html.HtmlText _selCAP2;

	public com.salmonllc.html.HtmlText _selCAP9;

	public com.salmonllc.html.HtmlText _valorCAP14;

	public com.salmonllc.html.HtmlText _vigenciaCAP11;

	public com.salmonllc.html.HtmlTextEdit _buscarTE1;

	public com.salmonllc.html.HtmlTextEdit _codigoTE2;

	public com.salmonllc.html.HtmlTextEdit _descripcionTE4;

	public com.salmonllc.html.HtmlTextEdit _desdeTE7;

	public com.salmonllc.html.HtmlTextEdit _hastaTE8;

	public com.salmonllc.html.HtmlTextEdit _nombreTE3;

	public com.salmonllc.html.HtmlTextEdit _observacionesTE5;

	public com.salmonllc.jsp.JspBox _box1;

	public com.salmonllc.jsp.JspBox _box2;

	public com.salmonllc.jsp.JspBox _box3;

	public com.salmonllc.jsp.JspDataTable _datatable1;

	public com.salmonllc.jsp.JspDataTable _datatable2;

	public com.salmonllc.jsp.JspDataTable _datatable3;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox3;

	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;

	public com.salmonllc.jsp.JspTable _table1;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBE;

	public infraestructura.models.AtributosEntidadModel _dsAtributos;

	public infraestructura.models.EntidadExternaModel _dsEntidad;

	public infraestructura.models.RolesEntidadModel _dsRoles;

	// DataSource Column Constants
	public static final String DSROLES_ROLES_ENTIDAD_DESDE = "roles_entidad.desde";

	public static final String DSROLES_ROLES_ENTIDAD_HASTA = "roles_entidad.hasta";

	public static final String DSROLES_ROLES_ENTIDAD_ACTIVO = "roles_entidad.activo";

	public static final String DSROLES_ROLES_ENTIDAD_ANULADO = "roles_entidad.anulado";

	public static final String DSROLES_ROLES_ENTIDAD_ENTIDAD_ID = "roles_entidad.entidad_id";

	public static final String DSROLES_ROLES_ENTIDAD_ROL = "roles_entidad.rol";

	public static final String DSROLES_ROL_ENTIDAD_NOMBRE = "rol_entidad.nombre";

	public static final String DSROLES_ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";

	public static final String DSROLES_ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";

	public static final String DSQBE_BUSCAR = "buscar";

	public static final String DSENTIDAD_ENTIDAD_EXTERNA_ENTIDAD_ID = "entidad_externa.entidad_id";

	public static final String DSENTIDAD_ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";

	public static final String DSENTIDAD_ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";

	public static final String DSENTIDAD_ENTIDAD_EXTERNA_DESCRIPCION = "entidad_externa.descripcion";

	public static final String DSENTIDAD_ENTIDAD_EXTERNA_OBSERVACIONES = "entidad_externa.observaciones";

	public static final String DSENTIDAD_ENTIDAD_EXTERNA_ACTIVO = "entidad_externa.activo";

	public static final String DSENTIDAD_ENTIDAD_EXTERNA_ANULADO = "entidad_externa.anulado";

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

	// botones custom
	public com.salmonllc.html.HtmlSubmitButton _agregarEntidadBUT1;

	public com.salmonllc.html.HtmlSubmitButton _grabarEntidadBUT2;

	public com.salmonllc.html.HtmlSubmitButton _borrarEntidadBUT3;

	public com.salmonllc.html.HtmlSubmitButton _activarEntidadBUT4;

	public com.salmonllc.html.HtmlSubmitButton _agregarRolBUT1;

	public com.salmonllc.html.HtmlSubmitButton _grabarRolBUT2;

	public com.salmonllc.html.HtmlSubmitButton _borrarRolBUT3;

	public com.salmonllc.html.HtmlSubmitButton _activarRolBUT4;

	public com.salmonllc.html.HtmlSubmitButton _grabarAtributoBUT1;

	public com.salmonllc.html.HtmlSubmitButton _atributoGenerarAtributosBUT11;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT1;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT2;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT3;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT4;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT5;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT6;

	private int botonSeleccionado = -1;

	@Override
	public void initialize() throws Exception {
		// completo botones a los listform

		// botones para Entidades externas Listformdisplaybox1
		_agregarEntidadBUT1 = new HtmlSubmitButton("agregarEntidadBUT1",
				"Agregar", this);
		_agregarEntidadBUT1.setAccessKey("A");
		_listformdisplaybox1.addButton(_agregarEntidadBUT1);
		_grabarEntidadBUT2 = new HtmlSubmitButton("grabarEntidadBUT2",
				"Grabar", this);
		_grabarEntidadBUT2.setAccessKey("G");
		_listformdisplaybox1.addButton(_grabarEntidadBUT2);
		_borrarEntidadBUT3 = new HtmlSubmitButton("borrarEntidadBUT3",
				"Eliminar", this);
		_borrarEntidadBUT3.setAccessKey("E");
		_listformdisplaybox1.addButton(_borrarEntidadBUT3);
		_activarEntidadBUT4 = new HtmlSubmitButton("activarEntidadBUT4",
				"acTivar", this);
		_activarEntidadBUT4.setAccessKey("T");
		_listformdisplaybox1.addButton(_activarEntidadBUT4);

		// botones para roles de entidades Listformdisplaybox2
		_agregarRolBUT1 = new HtmlSubmitButton("agregarRolBUT1", "agregar",
				this);
		_listformdisplaybox2.addButton(_agregarRolBUT1);
		_grabarRolBUT2 = new HtmlSubmitButton("grabarRolBUT2", "grabar", this);
		_listformdisplaybox2.addButton(_grabarRolBUT2);
		_borrarRolBUT3 = new HtmlSubmitButton("borrarRolBUT3", "eliminar", this);
		_listformdisplaybox2.addButton(_borrarRolBUT3);
		_activarRolBUT4 = new HtmlSubmitButton("activarRolBUT4", "activar",
				this);
		_listformdisplaybox2.addButton(_activarRolBUT4);

		// botones para atributos
		_grabarAtributoBUT1 = new HtmlSubmitButton("grabarAtributoBUT1",
				"grabar", this);
		_listformdisplaybox3.addButton(_grabarAtributoBUT1);

		_atributoGenerarAtributosBUT11 = new HtmlSubmitButton(
				"atributoGenerarAtributosBUT11", "generar", this);
		_listformdisplaybox3.addButton(_atributoGenerarAtributosBUT11);

		_atributoEtiquetaBUT1 = new HtmlSubmitButton("atributoEtiquetaBUT1",
				"etiqueta 1", this);
		_listformdisplaybox3.addButton(_atributoEtiquetaBUT1);
		_atributoEtiquetaBUT2 = new HtmlSubmitButton("atributoEtiquetaBUT2",
				"etiqueta 2", this);
		_listformdisplaybox3.addButton(_atributoEtiquetaBUT2);
		_atributoEtiquetaBUT3 = new HtmlSubmitButton("atributoEtiquetaBUT3",
				"etiqueta 3", this);
		_listformdisplaybox3.addButton(_atributoEtiquetaBUT3);
		_atributoEtiquetaBUT4 = new HtmlSubmitButton("atributoEtiquetaBUT4",
				"etiqueta 4", this);
		_listformdisplaybox3.addButton(_atributoEtiquetaBUT4);
		_atributoEtiquetaBUT5 = new HtmlSubmitButton("atributoEtiquetaBUT5",
				"etiqueta 5", this);
		_listformdisplaybox3.addButton(_atributoEtiquetaBUT5);
		_atributoEtiquetaBUT6 = new HtmlSubmitButton("atributoEtiquetaBUT6",
				"etiqueta 6", this);
		_listformdisplaybox3.addButton(_atributoEtiquetaBUT6);

		// genera el listener para los botones
		_agregarEntidadBUT1.addSubmitListener(this);
		_grabarEntidadBUT2.addSubmitListener(this);
		_borrarEntidadBUT3.addSubmitListener(this);
		_activarEntidadBUT4.addSubmitListener(this);
		_agregarRolBUT1.addSubmitListener(this);
		_grabarRolBUT2.addSubmitListener(this);
		_borrarRolBUT3.addSubmitListener(this);
		_activarRolBUT4.addSubmitListener(this);
		_atributoGenerarAtributosBUT11.addSubmitListener(this);
		_grabarAtributoBUT1.addSubmitListener(this);
		_atributoEtiquetaBUT1.addSubmitListener(this);
		_atributoEtiquetaBUT2.addSubmitListener(this);
		_atributoEtiquetaBUT3.addSubmitListener(this);
		_atributoEtiquetaBUT4.addSubmitListener(this);
		_atributoEtiquetaBUT5.addSubmitListener(this);
		_atributoEtiquetaBUT6.addSubmitListener(this);

		// agrega los flags de seleccion para entidades y roles
		// Agrega columna de seleccion al datasource de informes
		_dsEntidad.addBucket(SELECCION_FLAG1, DataStore.DATATYPE_INT);
		_seleccion1.setColumn(_dsEntidad, SELECCION_FLAG1);
		_seleccion1.setFalseValue(null);

		_dsRoles.addBucket(SELECCION_FLAG2, DataStore.DATATYPE_INT);
		_seleccion2.setColumn(_dsRoles, SELECCION_FLAG2);
		_seleccion2.setFalseValue(null);

		// setea la validación para lso datasources
		_dsEntidad.setAutoValidate(true);
		_dsRoles.setAutoValidate(true);
		_dsAtributos.setAutoValidate(true);

		// setea primera visualización
		seteaBotonesAtributos(-1);
		seteaNuevoBoton(-1);

		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		try {
		// agrega una nueva entidad
		if (e.getComponent() == _agregarEntidadBUT1) {
			int row = _dsEntidad.insertRow(0);

			// completa default de las columnas
			_dsEntidad.setColumnasDefault(row);

			// hace foco en el registro
			int nroPagerow = _datatable1.getPage(row);
			int nroPageActual = _datatable1.getPage(_dsEntidad.getRow());
			if (nroPagerow != nroPageActual)
				_datatable1.setPage(_datatable1.getPage(row));
			_codigoTE2.setFocus(row, true);
		}

		// graba la nueva entidad
		if (e.getComponent() == _grabarEntidadBUT2) {
			try {
				_dsEntidad.update();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// eliminar entidades --> las pasa a baja lógica
		if (e.getComponent() == _borrarEntidadBUT3) {
			for (int i = 0; i < _dsEntidad.getRowCount(); i++) {
				if (_dsEntidad.getInt(i, SELECCION_FLAG1) == 1) {
					// Entidad marcado para selección
					_dsEntidad.setEntidadExternaAnulado(i, "V");
					// dessetea el flag
					_dsEntidad.setInt(i, SELECCION_FLAG1, 0);
					try {
						_dsEntidad.update();
					} catch (DataStoreException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}

					// elimina al registro del datastore
					_dsEntidad.removeRow(i);
				}
			}

		}

		// Activa entidades -- este propceso garantiza que esté todo en orden
		// con la entidad
		if (e.getComponent() == _activarEntidadBUT4) {
			int row = _dsEntidad.getRow();

			if (row == -1) {
				displayErrorMessage("Debe seleccionar una entidad para poder activarla");
				return false;
			}

			// controles sobre la coluna activo
			String estado = _dsEntidad.getEntidadExternaActivo(row);
			if (estado == "V") {
				displayErrorMessage("La entidad YA está activa");
				return false;
			}

			// ahora si activo
			try {
				_dsEntidad.activarEntidad(row);
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// agrega una nuevo rol vinculado a la entidad
		if (e.getComponent() == _agregarRolBUT1) {
			// recupera la entidad de contexto o da error
			int rowEntidad = _dsEntidad.getRow();
			if (rowEntidad == -1) {
				displayErrorMessage("Debe seleccionar una entidad para poder asignar un rol");
				return false;
			}

			int v_entidad_id = _dsEntidad
					.getEntidadExternaEntidadId(rowEntidad);
			if (v_entidad_id < 1) {
				displayErrorMessage("Primero debe grabar los cambios de la entidad");
				return false;
			}

			int row = _dsRoles.insertRow(0);

			// completa default de las columnas
			_dsRoles.setColumnasDefault(row, v_entidad_id);

			// hace foco en el registro
			int nroPagerow = _datatable2.getPage(row);
			int nroPageActual = _datatable2.getPage(_dsRoles.getRow());
			if (nroPagerow != nroPageActual)
				_datatable2.setPage(_datatable2.getPage(row));
		}

		// graba el nuevo rol de entidad
		if (e.getComponent() == _grabarRolBUT2) {
			try {
				_dsRoles.update();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// eliminar roles de una entidad --> las pasa a baja lógica
		if (e.getComponent() == _borrarRolBUT3) {
			for (int i = 0; i < _dsRoles.getRowCount(); i++) {
				if (_dsRoles.getInt(i, SELECCION_FLAG2) == 1) {
					// Entidad marcado para selección
					_dsRoles.setRolesEntidadAnulado(i, "V");
					// dessetea el flag
					_dsRoles.setInt(i, SELECCION_FLAG2, 0);
					try {
						_dsRoles.update();
					} catch (DataStoreException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}

					// elimina al registro del datastore
					_dsRoles.removeRow(i);
				}
			}

		}

		// Activa el rol dentro de la entidad -- este propceso garantiza que
		// esté todo en orden con el rol de la entidad
		if (e.getComponent() == _activarRolBUT4) {
			int row = _dsRoles.getRow();

			if (row == -1) {
				displayErrorMessage("Debe seleccionar un Rol de entidad para poder activarlo");
				return false;
			}

			// controles sobre la coluna activo
			String estado = _dsRoles.getRolesEntidadActivo(row);
			if (estado == "V") {
				displayErrorMessage("La Rol de la entidad YA está activa");
				return false;
			}

			// ahora si activo
			try {
				_dsRoles.activarEntidad(row);
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// graba atributos de entidad
		if (e.getComponent() == _grabarAtributoBUT1) {
			try {
				_dsAtributos.update();
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		// genera atributos, por si faltan
		if (e.getComponent() == _atributoGenerarAtributosBUT11) {
			// primero determina contexto
			int v_entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			if (v_entidad_id < 1) {
				displayErrorMessage("Debe seleccionar una entidad para poder generar sus atributos");
				return false;
			}

			// manda a generar los atributos de la entidad
			try {
				_dsAtributos.generaAtributosEntidad(v_entidad_id);
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
			seteaBotonesAtributos(v_entidad_id);
			recuperaAtributosBotonSeleccionado(v_entidad_id);
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT1) {
			// toma acciones de solapa de atributos
			int v_entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			if (v_entidad_id > 0) {
				seteaNuevoBoton(1);
				recuperaAtributosBotonSeleccionado(v_entidad_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsRoles.reset();
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT2) {
			// toma acciones de solapa de atributos
			int v_entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			if (v_entidad_id > 0) {
				seteaNuevoBoton(2);
				recuperaAtributosBotonSeleccionado(v_entidad_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsRoles.reset();
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT3) {
			// toma acciones de solapa de atributos
			int v_entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			if (v_entidad_id > 0) {
				seteaNuevoBoton(3);
				recuperaAtributosBotonSeleccionado(v_entidad_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsRoles.reset();
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT4) {
			// toma acciones de solapa de atributos
			int v_entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			if (v_entidad_id > 0) {
				seteaNuevoBoton(4);
				recuperaAtributosBotonSeleccionado(v_entidad_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsRoles.reset();
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT5) {
			// toma acciones de solapa de atributos
			int v_entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			if (v_entidad_id > 0) {
				seteaNuevoBoton(5);
				recuperaAtributosBotonSeleccionado(v_entidad_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsRoles.reset();
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT6) {
			// toma acciones de solapa de atributos
			int v_entidad_id = _dsEntidad.getEntidadExternaEntidadId();
			if (v_entidad_id > 0) {
				seteaNuevoBoton(6);
				recuperaAtributosBotonSeleccionado(v_entidad_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsRoles.reset();
				_dsAtributos.reset();
			}
		}
		} catch (Exception ex) {
		displayErrorMessage(ex.getMessage());
		}
		return super.submitPerformed(e);
	}

	@Override
	public void pageSubmitEnd(PageEvent p) {
		// ante cada requerimiento verifica contexto y determina detalle de
		// atributos y completa FK's
		// Es row de rol válida?
		try {
			boolean actualizar = false;
			int entidad_id = 0;
			int entidad_id_roles = 0;
			int entidad_id_atributos = 0;
			if (_dsEntidad.getRow() != -1) {
				// recupera la entidad de contexto
				entidad_id = _dsEntidad.getEntidadExternaEntidadId();
				// si se está insertando un nuevo registro de rol, no se
				// actualiza
				if (!(_dsRoles.getRowStatus() == DataStoreBuffer.STATUS_NEW
						|| _dsRoles.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED
						|| _dsAtributos.getRowStatus() == DataStoreBuffer.STATUS_NEW || _dsAtributos
						.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED)) {
					// Ya existe detalle de roles?
					if (_dsRoles.getRowCount() > 0) {
						// es el mismo contexto? --> recupero la entidad del
						// detalle para verificación, siempre del primer
						// registro
						entidad_id_roles = _dsRoles.getRolesEntidadEntidadId(0);

						if (entidad_id_roles == 0)
							actualizar = true;
						if (entidad_id_roles != entidad_id) {
							// Es distinto el contexto del rol de atributo
							actualizar = true;
						}
					} else {
						actualizar = true;
					}

					// Ya existe detalle de atributos?
					if (_dsAtributos.getRowCount() > 0) {
						// es el mismo contexto? --> recupero la entidad del
						// detalle para verificación, siempre del primer
						// registro
						entidad_id_atributos = _dsAtributos
								.getAtributosEntidadEntidadId(0);
						if (entidad_id_atributos == 0)
							actualizar = true;
						if (entidad_id_atributos != entidad_id) {
							// Es distinto el contexto del rol de atributo
							actualizar = true;
						}

					} else {
						//actualizar = true;
					}
				}
			}
			if (actualizar) {
				actualizarDetalles(entidad_id);
			}

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		} catch (SQLException e) {
			displayErrorMessage(e.getMessage());
		}

		super.pageSubmitEnd(p);
	}

	public void actualizarDetalles(int p_entidad_id) throws SQLException,
			DataStoreException {
		_dsRoles.reset();
		_dsAtributos.reset();
		_dsRoles.retrieve("roles_entidad.entidad_id = "
				+ Integer.toString(p_entidad_id));
		_dsRoles.gotoFirst();
		seteaBotonesAtributos(p_entidad_id);
		recuperaAtributosBotonSeleccionado(p_entidad_id);
	}

	@SuppressWarnings("unchecked")
	private void seteaBotonesAtributos(int p_entidad_id) {

		ArrayList<String> et = null;



		// resetea la botonera
		_atributoEtiquetaBUT1.setVisible(false);
		_atributoEtiquetaBUT2.setVisible(false);
		_atributoEtiquetaBUT3.setVisible(false);
		_atributoEtiquetaBUT4.setVisible(false);
		_atributoEtiquetaBUT5.setVisible(false);
		_atributoEtiquetaBUT6.setVisible(false);

		// si no hay seteada entidad termina
		if (p_entidad_id < 1)
			return;

		// setea los botones de los atributos según las etiquetas
		try {
			et = _dsAtributos.recuperaEtiquetasAtributosEntidad(p_entidad_id);
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

	private void recuperaAtributosBotonSeleccionado(int p_entidad_id)
			throws SQLException, DataStoreException {
		// recupera los atributos asociados al proyectos de acuerdo al boton
		// seleccionado

		switch (botonSeleccionado) {
		case 1:
			if (_atributoEtiquetaBUT1.getDisplayName().equalsIgnoreCase(
					"General"))
				_dsAtributos.recuperaAtributosEtiquetaEntidad(null,
						p_entidad_id);
			else
				_dsAtributos.recuperaAtributosEtiquetaEntidad(
						_atributoEtiquetaBUT1.getDisplayName(), p_entidad_id);
			break;
		case 2:
			_dsAtributos.recuperaAtributosEtiquetaEntidad(_atributoEtiquetaBUT2
					.getDisplayName(), p_entidad_id);			
			break;
		case 3:
			_dsAtributos.recuperaAtributosEtiquetaEntidad(_atributoEtiquetaBUT3
					.getDisplayName(), p_entidad_id);
			break;
		case 4:
			_dsAtributos.recuperaAtributosEtiquetaEntidad(_atributoEtiquetaBUT4
					.getDisplayName(), p_entidad_id);
			break;
		case 5:
			_dsAtributos.recuperaAtributosEtiquetaEntidad(_atributoEtiquetaBUT5
					.getDisplayName(), p_entidad_id);
			break;
		case 6:
			_dsAtributos.recuperaAtributosEtiquetaEntidad(_atributoEtiquetaBUT6
					.getDisplayName(), p_entidad_id);
			break;
		}
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

}
