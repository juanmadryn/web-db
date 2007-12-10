//package statement
package proyectos.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

import proyectos.models.TareasProyectoModel;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * AdmProyectoController: a SOFIA generated controller
 */
public class AdmProyectoController extends BaseController implements
		ValueChangedListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -3954862158267321481L;

	// Visual Components
	public com.salmonllc.html.HtmlLookUpComponent _actividadTE20;

	public com.salmonllc.html.HtmlLookUpComponent _actividadTE21;

	public com.salmonllc.html.HtmlLookUpComponent _claseTareaTE34;

	public com.salmonllc.html.HtmlLookUpComponent _clienteTE4;

	public com.salmonllc.html.HtmlText _activiadadPadrePadreTXT22;

	public com.salmonllc.html.HtmlText _activiadadPadreTXT21;

	public com.salmonllc.html.HtmlText _actividadCAP20;

	public com.salmonllc.html.HtmlText _actividadPadreCAP21;

	public com.salmonllc.html.HtmlText _actividadPadrePadreCAP22;

	public com.salmonllc.html.HtmlText _atributoCAP10;

	public com.salmonllc.html.HtmlText _atributoTXT10;

	public com.salmonllc.html.HtmlText _claseTareaCAP34;

	public com.salmonllc.html.HtmlText _clienteCAP4;

	public com.salmonllc.html.HtmlText _descripcionCAP2;

	public com.salmonllc.html.HtmlText _descripcionCAP32;

	public com.salmonllc.html.HtmlText _idCAP30;

	public com.salmonllc.html.HtmlText _idCAP5;

	public com.salmonllc.html.HtmlText _idTXT30;

	public com.salmonllc.html.HtmlText _idTXT5;

	public com.salmonllc.html.HtmlText _nombreCAP1;

	public com.salmonllc.html.HtmlText _proyectoCAP1;

	public com.salmonllc.html.HtmlText _nombreCAP31;

	public com.salmonllc.html.HtmlText _observacionesCAP33;

	public com.salmonllc.html.HtmlText _observacionesCAP3;

	public com.salmonllc.html.HtmlText _valorCAP11;

	public com.salmonllc.html.HtmlText _vigenciahastaCAP35;

	public com.salmonllc.html.HtmlText _vigenciadesdeCAP36;

	public com.salmonllc.html.HtmlTextEdit _descripcionTE2;

	public com.salmonllc.html.HtmlTextEdit _descripcionTE32;

	public com.salmonllc.html.HtmlTextEdit _nombreTE1;

	public com.salmonllc.html.HtmlTextEdit _proyectoTE1;

	public com.salmonllc.html.HtmlTextEdit _nombreTE31;

	public com.salmonllc.html.HtmlTextEdit _observacionesTE33;

	public com.salmonllc.html.HtmlTextEdit _observacionesTE3;

	public com.salmonllc.html.HtmlTextEdit _valorTE11;

	public com.salmonllc.html.HtmlTextEdit _vigenciahastaTE35;

	public com.salmonllc.html.HtmlTextEdit _vigenciadesdeTE36;

	public com.salmonllc.jsp.JspBox _box1;

	public com.salmonllc.jsp.JspBox _box2;

	public com.salmonllc.jsp.JspBox _box3;

	public com.salmonllc.jsp.JspDataTable _datatable1;

	public com.salmonllc.jsp.JspDataTable _datatable2;

	public com.salmonllc.jsp.JspDataTable _datatable3;

	public com.salmonllc.jsp.JspDetailFormDisplayBox _detailformdisplaybox1;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;

	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox3;

	public com.salmonllc.jsp.JspTable _table1;

	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader0;

	public com.salmonllc.jsp.JspTableCell _datatable1TDHeader1;

	public com.salmonllc.jsp.JspTableCell _datatable1TDRow0;

	public com.salmonllc.jsp.JspTableCell _datatable1TDRow1;

	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader0;

	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader1;

	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader2;

	public com.salmonllc.jsp.JspTableCell _datatable2TDRow0;

	public com.salmonllc.jsp.JspTableCell _datatable2TDRow1;

	public com.salmonllc.jsp.JspTableCell _datatable2TDRow2;

	public com.salmonllc.jsp.JspTableCell _datatable3TDHeader0;

	public com.salmonllc.jsp.JspTableCell _datatable3TDHeader1;

	public com.salmonllc.jsp.JspTableCell _datatable3TDHeader2;

	public com.salmonllc.jsp.JspTableCell _datatable3TDHeader3;

	public com.salmonllc.jsp.JspTableCell _datatable3TDHeader4;

	public com.salmonllc.jsp.JspTableCell _datatable3TDRow0;

	public com.salmonllc.jsp.JspTableCell _datatable3TDRow1;

	public com.salmonllc.jsp.JspTableCell _datatable3TDRow2;

	public com.salmonllc.jsp.JspTableCell _datatable3TDRow3;

	public com.salmonllc.jsp.JspTableCell _datatable3TDRow4;

	public com.salmonllc.jsp.JspTableCell _table1TDRow0;

	public com.salmonllc.jsp.JspTableCell _table1TDRow1;

	public com.salmonllc.jsp.JspTableRow _datatable1TRHeader0;

	public com.salmonllc.jsp.JspTableRow _datatable1TRRow0;

	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader0;

	public com.salmonllc.jsp.JspTableRow _datatable2TRRow0;

	public com.salmonllc.jsp.JspTableRow _datatable3TRHeader0;

	public com.salmonllc.jsp.JspTableRow _datatable3TRRow0;

	public com.salmonllc.jsp.JspTableRow _table1TRRow0;

	// DataSources
	public proyectos.models.ActividadesProyectoModel _dsActividadesProyecto;

	public infraestructura.models.AtributosEntidadModel _dsAtributos;

	public proyectos.models.ProyectoModel _dsProyecto;

	public proyectos.models.TareasProyectoModel _dsTareasProyecto;

	// DataSource Column Constants
	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_TAREA_ID = "tareas_proyecto.tarea_id";

	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_NOMBRE = "tareas_proyecto.nombre";

	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_DESCRIPCION = "tareas_proyecto.descripcion";

	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_OBSERVACIONES = "tareas_proyecto.observaciones";

	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_ESTADO = "tareas_proyecto.estado";

	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_PROYECTO_ID = "tareas_proyecto.proyecto_id";

	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_CLASE_TAREA_ID = "tareas_proyecto.clase_tarea_id";

	public static final String DSTAREASPROYECTO_TAREAS_PROYECTO_ACTIVIDAD_PROYECTO_ID = "tareas_proyecto.actividad_proyecto_id";

	public static final String DSTAREASPROYECTO_PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String DSTAREASPROYECTO_ACTIVIDADES_PROYECTO_ACTIVIDAD_ID = "actividades_proyecto.actividad_id";

	public static final String DSTAREASPROYECTO_ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String DSTAREASPROYECTO_ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String DSTAREASPROYECTO_ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	public static final String DSTAREASPROYECTO_ACTIVIDAD_PADRE_ACTIVIDAD_ID_PADRE = "actividad_padre.actividad_id_padre";

	public static final String DSTAREASPROYECTO_ACTIVIDAD_PADRE_PADRE_NOMBRE = "actividad_padre_padre.nombre";

	public static final String DSTAREASPROYECTO_CLASES_TAREA_NOMBRE = "clases_tareas.nombre";

	public static final String DSTAREASPROYECTO_ESTADOS_NOMBRE = "estados.nombre";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDADES_PROYECTO_PROYECTO_ID = "actividades_proyecto.proyecto_id";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDADES_PROYECTO_ACTIVIDAD_ID = "actividades_proyecto.actividad_id";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDADES_PROYECTO_ACTIVIDAD_PROYECTO_ID = "actividades_proyecto.actividad_proyecto_id";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDADES_NOMBRE = "actividades.nombre";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDADES_ACTIVIDAD_ID_PADRE = "actividades.actividad_id_padre";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDADES_ES_HOJA = "actividades.es_hoja";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDAD_PADRE_NOMBRE = "actividad_padre.nombre";

	public static final String DSACTIVIDADESPROYECTO_PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String DSACTIVIDADESPROYECTO_ACTIVIDAD_PADRE_PADRE_NOMBRE = "actividad_padre_padre.nombre";

	public static final String DSPROYECTO_PROYECTOS_PROYECTO_ID = "proyectos.proyecto_id";

	public static final String DSPROYECTO_PROYECTOS_PROYECTO = "proyectos.proyecto";

	public static final String DSPROYECTO_PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String DSPROYECTO_PROYECTOS_DESCRIPCION = "proyectos.descripcion";

	public static final String DSPROYECTO_PROYECTOS_OBSERVACIONES = "proyectos.observaciones";

	public static final String DSPROYECTO_PROYECTOS_ESTADO = "proyectos.estado";

	public static final String DSPROYECTO_PROYECTOS_PLANTILLA = "proyectos.plantilla";

	public static final String DSPROYECTO_PROYECTOS_ENTIDAD_ID = "proyectos.entidad_id";

	public static final String DSPROYECTO_PROYECTOS_VIGENCIA_DESDE = "proyectos.vigencia_desde";

	public static final String DSPROYECTO_PROYECTOS_VIGENCIA_HASTA = "proyectos.vigencia_hasta";

	public static final String DSPROYECTO_ESTADOS_NOMBRE = "estados.nombre";

	public static final String DSPROYECTO_ENTIDAD_EXTERNA_CODIGO = "entidad_externa.codigo";

	public static final String DSPROYECTO_ENTIDAD_EXTERNA_NOMBRE = "entidad_externa.nombre";

	public static final String DSATRIBUTOS_ATRIBUTOS_PROYECTO_VALOR = "atributos_proyecto.valor";

	public static final String DSATRIBUTOS_ATRIBUTOS_PROYECTO_VALOR_ENTERO = "atributos_proyecto.valor_entero";

	public static final String DSATRIBUTOS_ATRIBUTOS_PROYECTO_VALOR_REAL = "atributos_proyecto.valor_real";

	public static final String DSATRIBUTOS_ATRIBUTOS_PROYECTO_VALOR_FECHA = "atributos_proyecto.valor_fecha";

	public static final String DSATRIBUTOS_ATRIBUTOS_PROYECTO_VALOR_LOGICO = "atributos_proyecto.valor_logico";

	public static final String DSATRIBUTOS_ATRIBUTOS_PROYECTO_ATRIBUTO_ID = "atributos_proyecto.atributo_id";

	public static final String DSATRIBUTOS_ATRIBUTOS_PROYECTO_PROYECTO_ID = "atributos_proyecto.proyecto_id";

	public static final String DSATRIBUTOS_PROYECTOS_NOMBRE = "proyectos.nombre";

	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_NOMBRE = "atributos_rol.nombre";

	public static final String DSATRIBUTOS_ATRIBUTOS_ROL_CLASE_ATRIBUTO_ROL_ID = "atributos_rol.clase_atributo_rol_id";

	public static final String DSATRIBUTOS_CLASE_ATRIBUTO_ROL_ETIQUETA = "clase_atributo_rol.etiqueta";

	// componentes custom
	// public com.salmonllc.html.HtmlSubmitButton _atributosProyectoBUT1;
	public com.salmonllc.html.HtmlSubmitButton _nuevoProyectoBUT1;

	public com.salmonllc.html.HtmlSubmitButton _grabarProyectoBUT2;

	public com.salmonllc.html.HtmlSubmitButton _atributoGenerarAtributosBUT3;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiqueta1BUT4;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiqueta2BUT5;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiqueta3BUT6;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiqueta4BUT7;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiqueta5BUT8;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiqueta6BUT9;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiqueta7BUT10;

	public com.salmonllc.html.HtmlSubmitButton _actividadCrearBUT11;

	public com.salmonllc.html.HtmlSubmitButton _actividadEliminarBUT12;

	public com.salmonllc.html.HtmlSubmitButton _tareaCrearBUT13;

	public com.salmonllc.html.HtmlSubmitButton _tareaEliminarBUT14;

	public com.salmonllc.html.HtmlCheckBox _seleccionActividad;

	public com.salmonllc.html.HtmlText _selActividadCAP60;

	private String SELECCION_ACTIVIDAD_FLAG = "SELECCION_ACTIVIDAD_FLAG";

	public com.salmonllc.html.HtmlCheckBox _seleccionTarea;

	public com.salmonllc.html.HtmlText _selTareaCAP70;

	private String SELECCION_TAREA_FLAG = "SELECCION_TAREA_FLAG";

	private int botonSeleccionado = -1;

	// Juan Manuel Cortez - 30/10/2007
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox4;

	public com.salmonllc.html.HtmlSubmitButton _grabarAtributoBUT1;

	public com.salmonllc.html.HtmlSubmitButton _atributoGenerarAtributosBUT11;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT1;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT2;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT3;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT4;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT5;

	public com.salmonllc.html.HtmlSubmitButton _atributoEtiquetaBUT6;

	public com.salmonllc.html.HtmlSubmitButton _recargarProyectoBUT1;

	private boolean recargar = false;

	// Fin Juan Manuel Cortez - 30/10/2007

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 *
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		super.initialize();

		// completo y asigno botones custom

		_recargarProyectoBUT1 = new HtmlSubmitButton("recargarProyectoBUT1",
				"Recargar", this);
		_recargarProyectoBUT1.setAccessKey("R");
		_detailformdisplaybox1.addButton(_recargarProyectoBUT1);

		_nuevoProyectoBUT1 = new HtmlSubmitButton("nuevoProyectoBUT1",
				"Nuevo proyecto", this);
		_nuevoProyectoBUT1.setAccessKey("N");
		_detailformdisplaybox1.addButton(_nuevoProyectoBUT1);

		_grabarProyectoBUT2 = new HtmlSubmitButton("grabarProyectoBUT2",
				"Grabar", this);
		_grabarProyectoBUT2.setAccessKey("G");
		_detailformdisplaybox1.addButton(_grabarProyectoBUT2);

		_actividadCrearBUT11 = new HtmlSubmitButton("actividadCrearBUT11",
				"Crear", this);
		_actividadCrearBUT11.setAccessKey("C");
		_listformdisplaybox2.addButton(_actividadCrearBUT11);

		_actividadEliminarBUT12 = new HtmlSubmitButton(
				"actividadEliminarBUT12", "Eliminar", this);
		_actividadEliminarBUT12.setAccessKey("E");
		_listformdisplaybox2.addButton(_actividadEliminarBUT12);

		_tareaCrearBUT13 = new HtmlSubmitButton("tareaCrearBUT13", "Crear",
				this);
		_tareaCrearBUT13.setAccessKey("C");
		_listformdisplaybox3.addButton(_tareaCrearBUT13);

		_tareaEliminarBUT14 = new HtmlSubmitButton("tareaEliminarBUT14",
				"Eliminar", this);
		_tareaEliminarBUT14.setAccessKey("E");
		_listformdisplaybox3.addButton(_tareaEliminarBUT14);

		// Juan Manuel Cortez - 30/10/2007

		// botones para atributos
		_grabarAtributoBUT1 = new HtmlSubmitButton("grabarAtributoBUT1",
				"grabar", this);
		_listformdisplaybox4.addButton(_grabarAtributoBUT1);

		_atributoGenerarAtributosBUT11 = new HtmlSubmitButton(
				"atributoGenerarAtributosBUT11", "generar", this);
		_listformdisplaybox4.addButton(_atributoGenerarAtributosBUT11);

		_atributoEtiquetaBUT1 = new HtmlSubmitButton("atributoEtiquetaBUT1",
				"etiqueta 1", this);
		_listformdisplaybox4.addButton(_atributoEtiquetaBUT1);
		_atributoEtiquetaBUT2 = new HtmlSubmitButton("atributoEtiquetaBUT2",
				"etiqueta 2", this);
		_listformdisplaybox4.addButton(_atributoEtiquetaBUT2);
		_atributoEtiquetaBUT3 = new HtmlSubmitButton("atributoEtiquetaBUT3",
				"etiqueta 3", this);
		_listformdisplaybox4.addButton(_atributoEtiquetaBUT3);
		_atributoEtiquetaBUT4 = new HtmlSubmitButton("atributoEtiquetaBUT4",
				"etiqueta 4", this);
		_listformdisplaybox4.addButton(_atributoEtiquetaBUT4);
		_atributoEtiquetaBUT5 = new HtmlSubmitButton("atributoEtiquetaBUT5",
				"etiqueta 5", this);
		_listformdisplaybox4.addButton(_atributoEtiquetaBUT5);
		_atributoEtiquetaBUT6 = new HtmlSubmitButton("atributoEtiquetaBUT6",
				"etiqueta 6", this);
		_listformdisplaybox4.addButton(_atributoEtiquetaBUT6);

		// Fin Juan Manuel Cortez - 30/10/2007

		// agrega los listener a lso botones
		_nuevoProyectoBUT1.addSubmitListener(this);
		_grabarProyectoBUT2.addSubmitListener(this);
		_recargarProyectoBUT1.addSubmitListener(this);

		_actividadCrearBUT11.addSubmitListener(this);
		_actividadEliminarBUT12.addSubmitListener(this);
		_tareaCrearBUT13.addSubmitListener(this);
		_tareaEliminarBUT14.addSubmitListener(this);

		// Juan Manuel Cortez - 30/10/2007
		_atributoGenerarAtributosBUT11.addSubmitListener(this);
		_grabarAtributoBUT1.addSubmitListener(this);
		_atributoEtiquetaBUT1.addSubmitListener(this);
		_atributoEtiquetaBUT2.addSubmitListener(this);
		_atributoEtiquetaBUT3.addSubmitListener(this);
		_atributoEtiquetaBUT4.addSubmitListener(this);
		_atributoEtiquetaBUT5.addSubmitListener(this);
		_atributoEtiquetaBUT6.addSubmitListener(this);
		// Fin Juan Manuel Cortez - 30/10/2007

		// listeners para validar fecha antes de ser enviada al DataStore
		_vigenciadesdeTE36.addValueChangedListener(this);
		_vigenciahastaTE35.addValueChangedListener(this);

		// agrego columna de seleccion
		_dsActividadesProyecto.addBucket(SELECCION_ACTIVIDAD_FLAG,
				DataStore.DATATYPE_INT);
		_seleccionActividad.setColumn(_dsActividadesProyecto,
				SELECCION_ACTIVIDAD_FLAG);
		_seleccionActividad.setFalseValue(null);

		_dsTareasProyecto.addBucket(SELECCION_TAREA_FLAG,
				DataStore.DATATYPE_INT);
		_seleccionTarea.setColumn(_dsTareasProyecto, SELECCION_TAREA_FLAG);
		_seleccionTarea.setFalseValue(null);

		// seteo la validación para los datasource
		_dsProyecto.setAutoValidate(true);
		_dsAtributos.setAutoValidate(true);
		_dsActividadesProyecto.setAutoValidate(true);
		_dsTareasProyecto.setAutoValidate(true);

		// setea la página para los datastores
		_dsProyecto.setPage(this);
		_dsTareasProyecto.setPage(this);

		// seteo inicial para la visualización de componentes
		// genero un nuevo proyecto vacio.
		_dsProyecto.reset();
		_dsAtributos.reset();
		_dsActividadesProyecto.reset();
		_dsTareasProyecto.reset();

		_dsProyecto.insertRow();
		_dsProyecto.gotoFirst();

		// setea primera visualización
		seteaBotonesAtributos(-1);
		seteaNuevoBoton(-1);

		_proyectoTE1.setFocus();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {

		// seteo la URL del lookup de actividad proyecto para tareas
		_actividadTE21.setLookUpPageURL("%LkpActividadesProyecto?proyecto_id="
				+ _dsProyecto.getProyectosProyectoId());


		if (e.getComponent() == _recargarProyectoBUT1) {
			// genero un nuevo proyecto vacio.
			setRecargar(true);
			pageRequested(new PageEvent(this));
		}

		if (e.getComponent() == _nuevoProyectoBUT1) {
			// genero un nuevo proyecto vacio.
			_dsProyecto.reset();
			_dsAtributos.reset();
			_dsActividadesProyecto.reset();
			_dsTareasProyecto.reset();

			_dsProyecto.insertRow();
			_dsProyecto.gotoFirst();

			_proyectoTE1.setFocus();

		}

		// Juan Manuel Cortez - 30/10/2007

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
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id < 1) {
				displayErrorMessage("Debe seleccionar un proyecto para poder generar sus atributos");
				return false;
			}

			// manda a generar los atributos de la entidad
			try {
				_dsAtributos.generaAtributosObjetoAplicacion(v_objeto_id,
						"proyectos");
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
			seteaBotonesAtributos(v_objeto_id);
			recuperaAtributosBotonSeleccionado(v_objeto_id);
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT1) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(1);
				recuperaAtributosBotonSeleccionado(v_objeto_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT2) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(2);
				recuperaAtributosBotonSeleccionado(v_objeto_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT3) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(3);
				recuperaAtributosBotonSeleccionado(v_objeto_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT4) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(4);
				recuperaAtributosBotonSeleccionado(v_objeto_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT5) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(5);
				recuperaAtributosBotonSeleccionado(v_objeto_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT6) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(6);
				recuperaAtributosBotonSeleccionado(v_objeto_id);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}
		// Fin Juan Manuel Cortez - 30/10/2007

		if (e.getComponent() == _grabarProyectoBUT2) {
			try {
				// grabo todos los datasource
				try {
					_dsProyecto.update();
					this.replicaProyectoEnTarea(); // horror
					_dsActividadesProyecto.update();
					// si estoy parado en una fila válida del DataStore de tareas
					if (_dsTareasProyecto.getRow() != -1) {
						// si no es válida la actividad introducida para la tarea
						if (!validaProyectoActividadTarea()) {
							// muestro mensaje de error y retorno
							displayErrorMessage("La actividad consignada no se corresponde con ninguna asignada a este proyecto.");
							return false;
						}
						_dsTareasProyecto.update();
					}
				} catch (Exception ex) {
					MessageLog.writeErrorMessage(ex, _dsProyecto);
					displayErrorMessage(ex.getMessage());
					return false;
				}

				// recupero el proyecto grabado
				_dsProyecto.gotoFirst();
				if (_dsProyecto.getRow() != -1) {
					int v_proyecto_id = _dsProyecto.getProyectosProyectoId();

					// despues de grabar los atributos (por las dudas) se deben
					// insertar
					// y verificar que estén todos
					// los atributos para el proyecto
					try {
						_dsAtributos.update();
						_dsAtributos.generaAtributosObjetoAplicacion(
								v_proyecto_id, "proyectos");
						// _dsActividadesProyecto.update();
						// _dsTareasProyecto.update();
					} catch (DataStoreException ex) {
						MessageLog.writeErrorMessage(ex, null);
						displayErrorMessage(ex.getMessage());
						return false;
					}

					// setea los botones de los atributos
					seteaBotonesAtributos(v_proyecto_id);
					seteaNuevoBoton(botonSeleccionado);

					// recupera la información del boton seleccionado
					recuperaAtributosBotonSeleccionado(v_proyecto_id);
				}

			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			} catch (Exception ex) {
				MessageLog.writeErrorMessage(ex, _dsProyecto);
				displayErrorMessage(ex.getMessage());
				return false;
			}
		}

		if (e.getComponent() == _actividadCrearBUT11) {
			// crea un nuevo registro de actividad
			int v_proyecto_id = _dsProyecto.getProyectosProyectoId();
			if (v_proyecto_id > 1) {
				int reg = _dsActividadesProyecto.insertRow();
				_dsActividadesProyecto.gotoRow(reg);
				_dsActividadesProyecto.setActividadesProyectoProyectoId(reg,
						v_proyecto_id);
			}
		}

		if (e.getComponent() == _actividadEliminarBUT12) {
			// elimina todas las actividades seleccionadas
			for (int i = 0; i < _dsActividadesProyecto.getRowCount(); i++) {
				if (_dsActividadesProyecto.getInt(i, SELECCION_ACTIVIDAD_FLAG) == 1) {
					// Rol marcado para selección
					_dsActividadesProyecto.deleteRow(i);
					try {
						_dsActividadesProyecto.update();
					} catch (DataStoreException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}
				}
			}
		}

		if (e.getComponent() == _tareaCrearBUT13) {
			// crea un nuevo registro de actividad
			int v_proyecto_id = _dsProyecto.getProyectosProyectoId();
			if (v_proyecto_id > 1) {
				int reg = _dsTareasProyecto.insertRow();
				_dsTareasProyecto.gotoRow(reg);
				System.out.println("" + _dsTareasProyecto.getRow() + " "
						+ _dsTareasProyecto.getRowCount() + " " + reg);
				_dsTareasProyecto.setTareasProyectoProyectoId(reg,
						v_proyecto_id);
				// _actividadTE21.setLookUpPageURL("%LkpActividadesProyecto?proyecto_id="+_dsProyecto.getProyectosProyectoId());
			}
		}

		if (e.getComponent() == _tareaEliminarBUT14) {
			// elimina todas las actividades seleccionadas
			for (int i = 0; i < _dsTareasProyecto.getRowCount(); i++) {
				if (_dsTareasProyecto.getInt(i, SELECCION_TAREA_FLAG) == 1) {
					// Rol marcado para selección
					_dsTareasProyecto.deleteRow(i);

					try {
						_dsTareasProyecto.update();
					} catch (DataStoreException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}
				}
			}
		}
		return super.submitPerformed(e);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.salmonllc.html.events.ValueChangedListener#valueChanged(com.salmonllc.html.events.ValueChangedEvent)
	 */
	public boolean valueChanged(ValueChangedEvent e) throws Exception {
		// TextEdits para fechas de vigencia
		if ((e.getComponent() == _vigenciadesdeTE36)
				|| (e.getComponent() == _vigenciahastaTE35)) {
			if (_dsProyecto != null) {
				if (!_dsProyecto.isFormattedStringValid(e.getColumn(), e
						.getNewValue())) {
					// No movemos el nuevo valor al dataStore,pero evitamos
					// que sea eliminado la proxima vez que la pagina sea
					// mostrada
					e
							.setAcceptValue(ValueChangedEvent.PROCESSING_KEEP_CHANGE_IN_QUEUE);

					// Mostramos el error
					if (e.getComponent() == _vigenciadesdeTE36)
						displayErrorMessage("Formato de fecha incorrecto para campo vigencia desde.");
					else
						displayErrorMessage("Formato de fecha incorrecto para campo vigencia hasta.");

					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void pageRequested(PageEvent p) throws Exception {
		super.pageRequested(p);
		try {

			int v_proyecto_id = -1;
			// si la página es requerida por si misma no hago nada
			if (!isReferredByCurrentPage() || isRecargar()) {
				// verifico si tiene parámetro
				v_proyecto_id = getIntParameter("p_proyecto_id");
				if(isRecargar())
					v_proyecto_id = _dsProyecto.getProyectosProyectoId();
				if (v_proyecto_id > 0) {
					// Viene seteado el proyecto. lo recupero sino no se hace
					// nada

					// resetea todos los datasource
					_dsProyecto.reset();
					_dsAtributos.reset();
					_dsActividadesProyecto.reset();
					_dsTareasProyecto.reset();

					// recupera toda la información para el proyecto
					_dsProyecto.retrieve("proyectos.proyecto_id = "
							+ Integer.toString(v_proyecto_id));
					_dsProyecto.gotoFirst();

					_dsAtributos.generaAtributosObjetoAplicacion(v_proyecto_id,
							"proyectos");
					// setea los botones de los atributos
					seteaBotonesAtributos(v_proyecto_id);
					seteaNuevoBoton(botonSeleccionado);
					// recupera la información del boton seleccionado
					recuperaAtributosBotonSeleccionado(v_proyecto_id);
					_dsAtributos.gotoFirst();

					// sigue recuperando información del resto de los detalles
					// (actividades y tareas)
					_dsActividadesProyecto
							.retrieve("actividades_proyecto.proyecto_id = "
									+ Integer.toString(v_proyecto_id));
					_dsActividadesProyecto.gotoFirst();

					_dsTareasProyecto.retrieve("tareas_proyecto.proyecto_id = "
							+ Integer.toString(v_proyecto_id));
					_dsTareasProyecto.gotoFirst();
					_actividadTE21
							.setLookUpPageURL("%LkpActividadesProyecto?proyecto_id="
									+ _dsProyecto.getProyectosProyectoId());

					// hace visible todos los componentes UI
				}

			}
			setRecargar(false);
		} catch (Exception e) {
			displayErrorMessage(e.getMessage());
		}

	}

	/**
	 * workaround que replica datos de proyecto en una tarea
	 *
	 * @throws DataStoreException
	 * @deprecated eliminar cuando se haya implementado el uso de
	 *             tareas/proyectos
	 */
	private void replicaProyectoEnTarea() throws Exception {
		if (_dsTareasProyecto.getRowCount() == 0)

			try {
				int newrow = _dsTareasProyecto.insertRow();
				/*_dsTareasProyecto.setInt(newrow,
						TareasProyectoModel.TAREAS_PROYECTO_TAREA_ID,
						_dsProyecto.getProyectosProyectoId());*/
				_dsTareasProyecto.setInt(newrow,
						TareasProyectoModel.TAREAS_PROYECTO_PROYECTO_ID,
						_dsProyecto.getProyectosProyectoId());
				_dsTareasProyecto.setString(newrow,
						TareasProyectoModel.TAREAS_PROYECTO_NOMBRE, _dsProyecto
								.getProyectosNombre());
				_dsTareasProyecto.setInt(newrow,
						TareasProyectoModel.TAREAS_PROYECTO_CLASE_TAREA_ID, 1);
				_dsTareasProyecto
						.setString(newrow,
								TareasProyectoModel.TAREAS_PROYECTO_ESTADO,
								"0001.0001");
				_dsTareasProyecto.update();

			} catch (Exception e) {
				MessageLog.writeErrorMessage(e, null);
				displayErrorMessage("hola3" + e.getMessage());
				_dsTareasProyecto.retrieve("tareas_proyecto.proyecto_id = "
						+ _dsProyecto.getProyectosProyectoId());
				return;
			}
	}

	@Override
	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);
		// ante cada requerimiento verifica contexto y determina detalle de
		// atributos y completa FK's
		// Es row de rol válida?
		try {
			boolean actualizar = false;
			int row = _dsProyecto.getRow();
			int objeto_id = 0;
			int objeto_id_atributos = 0;
			if (row != -1) {

				// completa default de las columnas
				// _dsProyecto.setColumnasDefault(row);

				// recupera el id del proyecto de contexto
				objeto_id = _dsProyecto.getProyectosProyectoId();
				// si se está insertando un nuevo registro de rol, no se
				// actualiza
				if (!(_dsAtributos.getRowStatus() == DataStoreBuffer.STATUS_NEW || _dsAtributos
						.getRowStatus() == DataStoreBuffer.STATUS_NEW_MODIFIED)) {
					// Ya existe detalle de atributos?
					if (_dsAtributos.getRowCount() > 0) {
						// es el mismo contexto? --> recupero la entidad del
						// detalle para verificación, siempre del primer
						// registro
						objeto_id_atributos = _dsAtributos
								.getAtributosEntidadObjetoId(0);
						if (objeto_id_atributos == 0)
							actualizar = true;
						if (objeto_id_atributos != objeto_id) {
							// Es distinto el contexto del rol de atributo
							actualizar = true;
						}
					} else {
						actualizar = true;
					}
				}
			}
			if (actualizar) {
				actualizarDetalles(objeto_id);
			}

		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		} catch (SQLException e) {
			displayErrorMessage(e.getMessage());
		}

	}

	public void actualizarDetalles(int p_proyecto_id) throws SQLException,
			DataStoreException {
		_dsAtributos.reset();
		seteaBotonesAtributos(p_proyecto_id);
		recuperaAtributosBotonSeleccionado(p_proyecto_id);
	}

	private void seteaBotonesAtributos(int p_proyecto_id) {

		Hashtable et = null;

		// resetea la botonera
		_atributoEtiquetaBUT1.setVisible(false);
		_atributoEtiquetaBUT2.setVisible(false);
		_atributoEtiquetaBUT3.setVisible(false);
		_atributoEtiquetaBUT4.setVisible(false);
		_atributoEtiquetaBUT5.setVisible(false);
		_atributoEtiquetaBUT6.setVisible(false);

		// si no hay seteado proyecto termina
		if (p_proyecto_id < 1)
			return;

		// setea los botones de los atributos según las etiquetas
		try {
			et = _dsAtributos
					.recuperaEtiquetasAtributosObjetoAplicacion("proyectos");
		} catch (SQLException e) {
			displayErrorMessage(e.getLocalizedMessage());
			return;
		}
		if (et != null) {
			final Enumeration etiquetas = et.elements();
			for (int i = 0; i < et.size(); i++) {
				switch (i) {
				case 0:
					_atributoEtiquetaBUT1.setDisplayName((String) etiquetas
							.nextElement());
					if (_atributoEtiquetaBUT1.getDisplayName() == null
							|| _atributoEtiquetaBUT1.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT1.setDisplayName("General");
					_atributoEtiquetaBUT1.setVisible(true);
					break;
				case 1:
					_atributoEtiquetaBUT2.setDisplayName((String) etiquetas
							.nextElement());
					if (_atributoEtiquetaBUT2.getDisplayName() == null
							|| _atributoEtiquetaBUT2.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT2.setDisplayName("General");
					_atributoEtiquetaBUT2.setVisible(true);
					break;
				case 2:
					_atributoEtiquetaBUT3.setDisplayName((String) etiquetas
							.nextElement());
					if (_atributoEtiquetaBUT3.getDisplayName() == null
							|| _atributoEtiquetaBUT3.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT3.setDisplayName("General");
					_atributoEtiquetaBUT3.setVisible(true);
					break;
				case 3:
					_atributoEtiquetaBUT4.setDisplayName((String) etiquetas
							.nextElement());
					if (_atributoEtiquetaBUT4.getDisplayName() == null
							|| _atributoEtiquetaBUT4.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT4.setDisplayName("General");
					_atributoEtiquetaBUT4.setVisible(true);
					break;
				case 4:
					_atributoEtiquetaBUT5.setDisplayName((String) etiquetas
							.nextElement());
					if (_atributoEtiquetaBUT5.getDisplayName() == null
							|| _atributoEtiquetaBUT5.getDisplayName().trim()
									.length() == 0)
						_atributoEtiquetaBUT5.setDisplayName("General");
					_atributoEtiquetaBUT5.setVisible(true);
					break;
				case 5:
					_atributoEtiquetaBUT6.setDisplayName((String) etiquetas
							.nextElement());
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

	private void recuperaAtributosBotonSeleccionado(int p_objeto_id)
			throws SQLException, DataStoreException {
		// recupera los atributos asociados al proyectos de acuerdo al boton
		// seleccionado
		switch (botonSeleccionado) {
		case 1:
			if (_atributoEtiquetaBUT1.getDisplayName().equalsIgnoreCase(
					"General"))
				_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(null,
						p_objeto_id, "proyectos");
			else
				_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
						_atributoEtiquetaBUT1.getDisplayName(), p_objeto_id,
						"proyectos");
			break;
		case 2:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT2.getDisplayName(), p_objeto_id,
					"proyectos");
			break;
		case 3:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT3.getDisplayName(), p_objeto_id,
					"proyectos");

			break;
		case 4:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT4.getDisplayName(), p_objeto_id,
					"proyectos");
			break;
		case 5:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT5.getDisplayName(), p_objeto_id,
					"proyectos");
			break;
		case 6:
			_dsAtributos.recuperaAtributosEtiquetaObjetoAplicacion(
					_atributoEtiquetaBUT6.getDisplayName(), p_objeto_id,
					"proyectos");
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

	/**
	 * Validación sobre la actividad consignada para una tarea. El método
	 * chequea si se introdujo una actividad para la tarea. Luego busca en el
	 * DataStore de ActividadesProyecto si el proyecto actual tiene asociada la
	 * tarea consignada.
	 *
	 * @throws DataStoreException
	 *             ante un error en el DataStore
	 * @throws SQLException
	 *             ante un error en alguna sentencia SQL
	 * @return devuelve 'true' si la actividad es válida para el proyecto y
	 *         'false' en el caso contrario.
	 *
	 */

	private boolean validaProyectoActividadTarea() throws DataStoreException,
			SQLException {

		// si está seteada una actividad para la tarea
		if (_dsTareasProyecto.getTareasProyectoActividadProyectoId() != 0) {
			// pero la cuenta de actividades con ese id y para este proyecto da
			// 0
			if (_dsActividadesProyecto
					.estimateRowsRetrieved("actividades_proyecto.proyecto_id = "
							+ _dsTareasProyecto.getTareasProyectoProyectoId()
							+ " and "
							+ "actividades_proyecto.actividad_id = "
							+ _dsTareasProyecto
									.getTareasProyectoActividadProyectoId()) == 0)
				// devuelvo falso
				return false;
		}
		// si no se consignó una actividad, o la actividad consignada se designó
		// para este proyecto devuelvo verdadero
		return true;
	}

	public boolean isRecargar() {
		return recargar;
	}

	public void setRecargar(boolean recargar) {
		this.recargar = recargar;
	}


	// public void fileUploaded(FileUploadEvent e) {
	// String nombre = _validacionTE8.getFileName();
	//
	// if (nombre == null) {
	// displayErrorMessage("Debe escribir el nombre de un archivo para porder
	// recuperarlo");
	// } else {
	//
	// byte[] buf = _validacionTE8.getContent();
	// if (buf == null) {
	// displayErrorMessage("No se recuperó ningún archivo");
	//
	// } else {
	// int leido = buf.length;
	// for (int i = 0; i < leido; i++)
	// try {
	// _dsProyecto
	// .setServicioDistribuidoValidacion(_dsProyecto
	// .getServicioDistribuidoValidacion()
	// + (char) buf[i]);
	// } catch (DataStoreException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// }
	// }
	// try {
	// _dsProyecto.update();
	// } catch (DataStoreException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (SQLException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// }

}
