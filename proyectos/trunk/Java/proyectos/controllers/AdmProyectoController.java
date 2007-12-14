//package statement
package proyectos.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;
import infraestructura.reglasNegocio.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import proyectos.models.TareasProyectoModel;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.sql.DBConnection;
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

	private static final String TABLA_PRINCIPAL = "proyectos";

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
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox5;

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

	public com.salmonllc.html.HtmlSubmitButton _verActividadesTareasBUT1;

	private boolean recargar = false;

	// Fin Juan Manuel Cortez - 30/10/2007

	public com.salmonllc.html.HtmlSubmitButton _customBUT100;

	public com.salmonllc.html.HtmlSubmitButton _customBUT110;

	public com.salmonllc.html.HtmlSubmitButton _customBUT120;

	public com.salmonllc.html.HtmlSubmitButton _customBUT130;

	public com.salmonllc.html.HtmlSubmitButton _customBUT140;

	public com.salmonllc.html.HtmlSubmitButton _customBUT150;

	private String circuito = "0001";

	//

	//

	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		super.initialize();

		// completo y asigno botones custom

		_verActividadesTareasBUT1 = new HtmlSubmitButton(
				"verActividadesTareasBUT1", "Ver actividades", this);
		_verActividadesTareasBUT1.setAccessKey("V");
		_detailformdisplaybox1.addButton(_verActividadesTareasBUT1);

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
		_verActividadesTareasBUT1.addSubmitListener(this);

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

		// Juan Manuel Cortez - 29/11/2007
		_customBUT150.addSubmitListener(this);
		_customBUT140.addSubmitListener(this);
		_customBUT130.addSubmitListener(this);
		_customBUT120.addSubmitListener(this);
		_customBUT110.addSubmitListener(this);
		_customBUT100.addSubmitListener(this);

		// Fin Juan Manuel Cortez - 29/11/2007

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
		// _dsProyecto.setAutoValidate(true);
		// _dsAtributos.setAutoValidate(true);
		// _dsActividadesProyecto.setAutoValidate(true);
		// _dsTareasProyecto.setAutoValidate(true);

		// setea la página para los datastores
		// _dsProyecto.setPage(this);
		// _dsTareasProyecto.setPage(this);

		_box2.setVisible(false);

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
		String remoteAddr = this.getCurrentRequest().getRemoteHost();
		String nombre_tabla = TABLA_PRINCIPAL;
		int userId = this.getSessionManager().getWebSiteUser().getUserID();
		DBConnection conn = DBConnection.getConnection(getApplicationName());
		boolean batchInserts = false;

		conn.beginTransaction();
		try {
			if (e.getComponent() == _customBUT100) {
				if (circuito != null) {
					_dsProyecto.ejecutaAccion(Integer.parseInt(_customBUT100
							.getDisplayNameLocaleKey()), circuito, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (e.getComponent() == _customBUT110) {
				if (circuito != null) {
					_dsProyecto.ejecutaAccion(Integer.parseInt(_customBUT110
							.getDisplayNameLocaleKey()), circuito, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (e.getComponent() == _customBUT120) {
				if (circuito != null) {
					_dsProyecto.ejecutaAccion(Integer.parseInt(_customBUT120
							.getDisplayNameLocaleKey()), circuito, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (e.getComponent() == _customBUT130) {
				if (circuito != null) {
					_dsProyecto.ejecutaAccion(Integer.parseInt(_customBUT130
							.getDisplayNameLocaleKey()), circuito, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (e.getComponent() == _customBUT140) {
				if (circuito != null) {
					_dsProyecto.ejecutaAccion(Integer.parseInt(_customBUT140
							.getDisplayNameLocaleKey()), circuito, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}

			if (e.getComponent() == _customBUT150) {
				if (circuito != null) {
					_dsProyecto.ejecutaAccion(Integer.parseInt(_customBUT150
							.getDisplayNameLocaleKey()), circuito, remoteAddr,
							userId, nombre_tabla, conn, batchInserts);
					armaBotonera();

				}
			}
			conn.commit();
		} catch (ValidationException ex) {
			conn.rollback();			
			for (String er : ex.getStackErrores()) {
				displayErrorMessage(er);
			}
			return false;
		} finally {
			conn.freeConnection();
		}

		if (e.getComponent() == _verActividadesTareasBUT1) {
			// genero un nuevo proyecto vacio.
			if (_box3.getVisible()) {
				_verActividadesTareasBUT1.setDisplayName("Ver tareas");
				_box3.setVisible(false);
				_box2.setVisible(true);
			} else {
				_verActividadesTareasBUT1.setDisplayName("Ver actividades");
				_box3.setVisible(true);
				_box2.setVisible(false);
			}
		}

		if (e.getComponent() == _recargarProyectoBUT1) {
			// recargo el proyecto actual
			setRecargar(true);
			pageRequested(new PageEvent(this));
		}

		if (e.getComponent() == _nuevoProyectoBUT1) {
			// genero un nuevo proyecto vacio.
			_dsProyecto.reset();
			_dsAtributos.reset();
			_dsActividadesProyecto.reset();
			_dsTareasProyecto.reset();

			_dsProyecto.gotoRow(_dsProyecto.insertRow());

			_proyectoTE1.setFocus();
		}

		// Juan Manuel Cortez - 30/10/2007

		// graba atributos de entidad
		if (e.getComponent() == _grabarAtributoBUT1) {
			if (("0001.0001".equalsIgnoreCase(_dsProyecto.getProyectosEstado()) || _dsProyecto.getProyectosEstado() == null)) {
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
			} else {
				displayErrorMessage("No puede modificar el proyecto una vez que lo ha completado");
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
						TABLA_PRINCIPAL);
			} catch (DataStoreException ex) {
				displayErrorMessage(ex.getMessage());
				return false;
			}
			seteaBotonesAtributos(v_objeto_id);
			recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
		}

		// controla el efecto de solapa de los botones y recupera los atributos
		// correspondientes
		if (e.getComponent() == _atributoEtiquetaBUT1) {
			// toma acciones de solapa de atributos
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
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
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
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
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
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
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
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
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
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
			int v_objeto_id = _dsProyecto.getProyectosProyectoId();
			if (v_objeto_id > 0) {
				seteaNuevoBoton(6);
				recuperaAtributosBotonSeleccionado(v_objeto_id, TABLA_PRINCIPAL);
			} else {
				// no está en contexto de nungún proyecto reseteo las vistas
				_dsAtributos.reset();
			}
		}
		// Fin Juan Manuel Cortez - 30/10/2007

		if (e.getComponent() == _grabarProyectoBUT2) {
			// si el proyecto esta en estado generado o esta siendo generado
			if ("0001.0001".equalsIgnoreCase(_dsProyecto.getProyectosEstado()) || _dsProyecto.getProyectosEstado() == null)
				{
				int v_objeto_id = 0;
				try {
					// grabo todos los datasource
					_dsProyecto.update();
					// me aseguro que cada proyecto tenga por lo menos una tarea
					// asociada, con el mismo nombre del proyecto
					replicaProyectoEnTarea();
					// actualizo las actividades cargadas
					_dsActividadesProyecto.update();
					// actualizo las tareas
					_dsTareasProyecto.update();

					// genero atributos faltantes si los hubiera, es decir, los
					// inserto en la tabla de atributos con sus valores en null
					v_objeto_id = _dsProyecto.getProyectosProyectoId();
					if (_dsAtributos.getRow() == -1) {

						if (v_objeto_id < 1) {
							displayErrorMessage("Debe seleccionar un proyecto para poder generar sus atributos");
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
					return false;
				} catch (SQLException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				} catch (Exception ex) {
					MessageLog.writeErrorMessage(ex, _dsProyecto);
					displayErrorMessage(ex.getMessage());
					return false;
				} finally {
					seteaBotonesAtributos(v_objeto_id);
					recuperaAtributosBotonSeleccionado(v_objeto_id,
							TABLA_PRINCIPAL);
				}
			} else {
				// si el proyecto no está generado, bloqueo toda modificación				
				displayErrorMessage("No puede modificar el proyecto en el estado actual.");
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
					try {
						_dsActividadesProyecto.deleteRow(i);

						_dsActividadesProyecto.update();
					} catch (Exception ex) {
						displayErrorMessage("No se ha podido eliminar la actividad seleccionada. Error: "
								+ ex.getMessage());
						_dsActividadesProyecto.unDeleteRow(i);
						_dsActividadesProyecto.setInt(i,
								SELECCION_ACTIVIDAD_FLAG, 0);
						return false;
					}

				}
			}
		}

		if (e.getComponent() == _tareaCrearBUT13) {
			// crea un nuevo registro de tarea
			int v_proyecto_id = _dsProyecto.getProyectosProyectoId();
			if (v_proyecto_id > 1) {
				int reg = _dsTareasProyecto.insertRow();
				_dsTareasProyecto.gotoRow(reg);
				_dsTareasProyecto.setTareasProyectoProyectoId(reg,
						v_proyecto_id);
			}
		}

		if (e.getComponent() == _tareaEliminarBUT14) {
			// elimina todas las actividades seleccionadas
			for (int i = 0; i < _dsTareasProyecto.getRowCount(); i++) {
				if (_dsTareasProyecto.getInt(i, SELECCION_TAREA_FLAG) == 1) {
					// Rol marcado para selección
					try {
						if (!_dsTareasProyecto.deleteRow(i))
							displayErrorMessage("El proyecto debe tener por lo menos una tarea");
						_dsTareasProyecto.update();
					} catch (DataStoreException ex) {
						displayErrorMessage("No se ha podido eliminar la tarea seleccionada. Error: "
								+ ex.getMessage());
						_dsTareasProyecto.unDeleteRow(i);
						_dsTareasProyecto
								.setInt(i, SELECCION_ACTIVIDAD_FLAG, 0);
						return false;
					} catch (SQLException ex) {
						displayErrorMessage(ex.getMessage());
						return false;
					}
				}
			}
		}

		// seteo la URL del lookup de actividad proyecto para tareas, para que
		// la página de lookup muestre sólo actividades asociadas al proyecto.
		// Esto no se pudo transcribir directamente a la jsp
		_actividadTE21.setLookUpPageURL("%LkpActividadesProyecto?proyecto_id="
				+ _dsProyecto.getProyectosProyectoId());

		// arma botonera de cambio de estados
		armaBotonera();

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
			// si la página es requerida por si misma o está en proceso de
			// recargar un proyecto no hago nada
			if (!isReferredByCurrentPage() || isRecargar()) {
				// verifico si tiene parámetro
				v_proyecto_id = getIntParameter("p_proyecto_id");
				if (isRecargar())
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

					// genero atributos si faltan
					_dsAtributos.generaAtributosObjetoAplicacion(v_proyecto_id,
							TABLA_PRINCIPAL);

					// setea los botones de los atributos
					seteaBotonesAtributos(v_proyecto_id);
					seteaNuevoBoton(botonSeleccionado);

					// recupera la información del boton seleccionado
					recuperaAtributosBotonSeleccionado(v_proyecto_id,
							TABLA_PRINCIPAL);
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

					// seteo la lookup de actividades de la tabla de tareas
					_actividadTE21
							.setLookUpPageURL("%LkpActividadesProyecto?proyecto_id="
									+ _dsProyecto.getProyectosProyectoId());

				}

			}

			setRecargar(false);

			// arma botonera de cambio de estados
			armaBotonera();
		} catch (DataStoreException e) {
			displayErrorMessage(e.getMessage());
		}

	}

	/**
	 * Genera una tarea nueva para el proyecto actual, con el mismo nombre que
	 * este
	 * 
	 * @throws DataStoreException
	 * @deprecated analizar si conviene mantener la funcionalidad de generar
	 *             automaticamente una tarea por cada proyecto
	 * 
	 */
	private void replicaProyectoEnTarea() throws Exception {
		if (_dsTareasProyecto.getRowCount() == 0)

			try {
				int newrow = _dsTareasProyecto.insertRow();
				/*
				 * _dsTareasProyecto.setInt(newrow,
				 * TareasProyectoModel.TAREAS_PROYECTO_TAREA_ID,
				 * _dsProyecto.getProyectosProyectoId());
				 */
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
				displayErrorMessage(e.getMessage());
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
		recuperaAtributosBotonSeleccionado(p_proyecto_id, TABLA_PRINCIPAL);
	}

	/**
	 * Arma la botonera de atributos en función del id de proyecto indicado.
	 * 
	 * @param p_proyecto_id
	 *            el id del proyecto para el cual se quieren setear los botones
	 * 
	 * TODO generalizar este método a un número indefinido de etiquetas para
	 * atributos
	 */
	private void seteaBotonesAtributos(int p_proyecto_id) {

		ArrayList<String> et = null;

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

	public boolean isRecargar() {
		return recargar;
	}

	public void setRecargar(boolean recargar) {
		this.recargar = recargar;
	}

	/**
	 * Determina si existe un registro en contexto, recupera su estado y arma la
	 * botonera acorde
	 * 
	 * @throws DataStoreException
	 *             TODO generalizar este método para un número indefinido de
	 *             transiciones entre estados TODO considerar recuperar todo el
	 *             circuito para la entidad actual y recuperar los estados de un
	 *             mismo model. Se reducirían las interacciones con la BD.
	 */
	private void armaBotonera() throws DataStoreException {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		String SQL;
		String nombre_boton;
		String estado = null;

		// resetea todos los botones
		_customBUT100.setVisible(false);
		_customBUT110.setVisible(false);
		_customBUT120.setVisible(false);
		_customBUT130.setVisible(false);
		_customBUT140.setVisible(false);
		_customBUT150.setVisible(false);

		// controla estar dentro de un contexto de Informe
		if (_dsProyecto.getRow() == -1) {
			throw new DataStoreException(
					"Debe seleccionar un proyecto para recuperar su estado");
		}

		try {
			conn = DBConnection.getConnection("infraestructura");

			// determina datos para evaluar las transiciones y acciones del
			// circuiro
			// recupero la columna para el circuito
			// Si no existe configuración no hace nada
			SQL = "select nombre_detalle from infraestructura.aplica_circuito where circuito = '"
					+ circuito + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			// en función de la columna del circuito, determino el estado actual
			estado = _dsProyecto.getString("proyectos.estado");

			// recorro los estados y seteo los botones
			SQL = "SELECT prompt_accion,accion FROM infraestructura.transicion_estados t left join infraestructura.estados e on t.estado_origen = e.estado "
					+ "where e.circuito = '"
					+ circuito
					+ "' and t.estado_origen = '" + estado + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first()) {
				int i = 100;
				com.salmonllc.html.HtmlSubmitButton boton;
				do {
					nombre_boton = "customBUT" + i;
					boton = (com.salmonllc.html.HtmlSubmitButton) this
							.getComponent(nombre_boton);
					if (boton != null) {
						boton.setVisible(true);
						boton.setDisplayName(r.getString(1));
						boton.setDisplayNameLocaleKey(Integer.toString(r
								.getInt(2)));
					}
					nombre_boton = "customBUT" + i * 100;
					boton = (com.salmonllc.html.HtmlSubmitButton) this
							.getComponent(nombre_boton);
					if (boton != null) {
						boton.setVisible(true);
						boton.setDisplayName(r.getString(1));
						boton.setDisplayNameLocaleKey(Integer.toString(r
								.getInt(2)));
					}
					i = i + 10;
				} while (r.next() && i < 150);
			}

		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}

	}

}
