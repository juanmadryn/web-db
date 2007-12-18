//package statement
package partesMO.controllers;

//Salmon import statements
import infraestructura.controllers.BaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * AdmLotesPartesController: a SOFIA generated controller
 */
public class AdmLotesPartesController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1991607599767195262L;

	// Visual Components
	public com.salmonllc.html.HtmlDropDownList _buscarTE21;
	public com.salmonllc.html.HtmlText _apeynomTE23;
	public com.salmonllc.html.HtmlText _buscarCAP1;
	public com.salmonllc.html.HtmlText _buscarCAP2;
	public com.salmonllc.html.HtmlText _buscarCAP3;
	public com.salmonllc.html.HtmlText _buscarCAP4;
	public com.salmonllc.html.HtmlText _descripcionCAP11;
	public com.salmonllc.html.HtmlTextEdit _descripcionTE11;
	public com.salmonllc.html.HtmlText _estadoCAP12;
	public com.salmonllc.html.HtmlText _estadoTE12;
	public com.salmonllc.html.HtmlText _fechaAltaCAP13;
	public com.salmonllc.html.HtmlText _fechaAltaTE13;
	public com.salmonllc.html.HtmlText _fechaCAP22;
	public com.salmonllc.html.HtmlText _fechaCierreCAP14;
	public com.salmonllc.html.HtmlText _fechaCierreTE14;
	public com.salmonllc.html.HtmlText _fechaTE22;
	public com.salmonllc.html.HtmlText _horaDesdeTE26;
	public com.salmonllc.html.HtmlText _horaHastaTE26;
	public com.salmonllc.html.HtmlText _horarioCAP27;
	public com.salmonllc.html.HtmlText _horasTE26;
	public com.salmonllc.html.HtmlText _legajoCAP23;
	public com.salmonllc.html.HtmlText _loteIdCAP10;
	public com.salmonllc.html.HtmlText _loteIdTE10;
	public com.salmonllc.html.HtmlText _nroLegajoTE23;
	public com.salmonllc.html.HtmlText _parteIdCAP21;
	public com.salmonllc.html.HtmlText _parteIdTE21;
	public com.salmonllc.html.HtmlText _proyectoCAP24;
	public com.salmonllc.html.HtmlText _proyectosNombreTE24;
	public com.salmonllc.html.HtmlText _proyectosProyectoTE24;
	public com.salmonllc.html.HtmlText _sectorCAP25;
	public com.salmonllc.html.HtmlText _sectrorTE25;
	public com.salmonllc.html.HtmlText _selCAP20;
	public com.salmonllc.html.HtmlText _selTE20;
	public com.salmonllc.html.HtmlText _supervisorCAP26;
	public com.salmonllc.html.HtmlText _supervisorTE26;
	public com.salmonllc.html.HtmlTextEdit _buscarTE1;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlTextEdit _buscarTE4;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspBox _box3;
	public com.salmonllc.jsp.JspDataTable _datatable1;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox2;
	public com.salmonllc.jsp.JspRaw _rawAddRow;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;

	// DataSources
	public com.salmonllc.sql.QBEBuilder _dsQBELotes;
	public com.salmonllc.sql.QBEBuilder _dsQBEPartes;
	public partesMO.models.LoteCargaPartesMoModel _dsLotes;
	public partesMO.models.PartesMoModel _dsPartes;

	// DataSource Column Constants
	public static final String DSQBELOTES_DESCRIPCION = "descripcion";
	public static final String DSQBELOTES_FECHA_DESDE = "fecha_desde";
	public static final String DSQBELOTES_FECHA_HASTA = "fecha_hasta";
	public static final String DSQBELOTES_ESTADO = "estado";
	public static final String DSLOTES_LOTE_CARGA_PARTES_MO_LOTE_ID = "lote_carga_partes_mo.lote_id";
	public static final String DSLOTES_LOTE_CARGA_PARTES_MO_ESTADO = "lote_carga_partes_mo.estado";
	public static final String DSLOTES_LOTE_CARGA_PARTES_MO_FECHA_ALTA = "lote_carga_partes_mo.fecha_alta";
	public static final String DSLOTES_LOTE_CARGA_PARTES_MO_FECHA_CIERRE = "lote_carga_partes_mo.fecha_cierre";
	public static final String DSLOTES_LOTE_CARGA_PARTES_MO_DESCRIPCION = "lote_carga_partes_mo.descripcion";
	public static final String DSLOTES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSPARTES_PARTES_MO_PARTE_ID = "partes_mo.parte_id";
	public static final String DSPARTES_PARTES_MO_FECHA = "partes_mo.fecha";
	public static final String DSPARTES_PARTES_MO_HORA_DESDE = "partes_mo.hora_desde";
	public static final String DSPARTES_PARTES_MO_HORA_HASTA = "partes_mo.hora_hasta";
	public static final String DSPARTES_PARTES_MO_HORAS = "partes_mo.horas";
	public static final String DSPARTES_PARTES_MO_LOTE_ID = "partes_mo.lote_id";
	public static final String DSPARTES_PARTES_MO_SUPERVISOR = "partes_mo.supervisor";
	public static final String DSPARTES_PARTES_MO_CATEGORIA = "partes_mo.categoria";
	public static final String DSPARTES_PARTES_MO_DESC_CATEGORIA = "partes_mo.desc_categoria";
	public static final String DSPARTES_PARTES_MO_SECTOR_ID = "partes_mo.sector_id";
	public static final String DSPARTES_PARTES_MO_PROYECTO_ID = "partes_mo.proyecto_id";
	public static final String DSPARTES_PARTES_MO_PERSONAL_ID = "partes_mo.personal_id";
	public static final String DSPARTES_PARTES_MO_NRO_LEGAJO = "partes_mo.nro_legajo";
	public static final String DSPARTES_PARTES_MO_APEYNOM = "partes_mo.apeynom";
	public static final String DSPARTES_PARTES_MO_ESTADO = "partes_mo.estado";
	public static final String DSPARTES_SECTOR_TRABAJO_NOMBRE = "sector_trabajo.nombre";
	public static final String DSPARTES_SUPERVISORES_APEYNOM = "supervisores.apeynom";
	public static final String DSPARTES_ESTADOS_NOMBRE = "estados.nombre";
	public static final String DSPARTES_LOTE_CARGA_PARTES_MO_ESTADO = "lote_carga_partes_mo.estado";
	public static final String DSPARTES_LOTE_CARGA_PARTES_MO_FECHA_ALTA = "lote_carga_partes_mo.fecha_alta";
	public static final String DSPARTES_LOTE_CARGA_PARTES_MO_FECHA_CIERRE = "lote_carga_partes_mo.fecha_cierre";
	public static final String DSPARTES_PROYECTOS_PROYECTO = "proyectos.proyecto";
	public static final String DSPARTES_PROYECTOS_NOMBRE = "proyectos.nombre";
	public static final String DSPARTES_MENSAJE_ERROR = "mensaje_error";
	public static final String DSQBEPARTES_BUSCAR = "buscar";

	// Componentes visuales custom
	public com.salmonllc.html.HtmlSubmitButton _nuevoLoteBUT1;
	public com.salmonllc.html.HtmlSubmitButton _grabarLoteBUT2;
	public com.salmonllc.html.HtmlSubmitButton _lotearLoteBUT3;
	public com.salmonllc.html.HtmlSubmitButton _anularLoteBUT4;
	public com.salmonllc.html.HtmlSubmitButton _validarLoteBUT5;
	public com.salmonllc.html.HtmlSubmitButton _reLotearLoteBUT6;
	public com.salmonllc.html.HtmlSubmitButton _firmarLoteBUT7;
	public com.salmonllc.html.HtmlSubmitButton _lotearParteBUT8;
	public com.salmonllc.html.HtmlSubmitButton _anularParteBUT9;
	public com.salmonllc.html.HtmlSubmitButton _validarParteBUT10;
	public com.salmonllc.html.HtmlSubmitButton _firmarParteBUT11;
	public com.salmonllc.html.HtmlSubmitButton _buscarPartesSinLotearBUT12;
	public com.salmonllc.html.HtmlSubmitButton _seleccionaTodoBUT13;
	public com.salmonllc.html.HtmlSubmitButton _desSeleccionaTodoBUT14;
	public com.salmonllc.html.HtmlSubmitButton _lotearPartesSinLotearBUT15;
	public com.salmonllc.html.HtmlSubmitButton _buscarLotesBUT16;

	public com.salmonllc.html.HtmlCheckBox _seleccionParte;
	private String SELECCION_PARTE_FLAG = "SELECCION_PARTE_FLAG";
	
	// Variables que indical el lote corriente
	private int rowLote_actual = -1;

	@Override
	public void initialize() throws Exception {
		_buscarLotesBUT16 = new HtmlSubmitButton("buscarLotesBUT16","Buscar",this);
		_buscarLotesBUT16.setAccessKey("B");
		_searchformdisplaybox1.addButton(_buscarLotesBUT16);

		_nuevoLoteBUT1 = new HtmlSubmitButton("nuevoLoteBUT1","Nuevo lote",this);
		_nuevoLoteBUT1.setAccessKey("N");
		_listformdisplaybox1.addButton(_nuevoLoteBUT1);

		_grabarLoteBUT2 = new HtmlSubmitButton("grabarLoteBUT2","Grabar",this);
		_grabarLoteBUT2.setAccessKey("G");
		_listformdisplaybox1.addButton(_grabarLoteBUT2);

		_buscarPartesSinLotearBUT12 = new HtmlSubmitButton("buscarPartesSinLotearBUT12","Partes sin lotear",this);
		_buscarPartesSinLotearBUT12.setAccessKey("P");
		_listformdisplaybox1.addButton(_buscarPartesSinLotearBUT12);

		_lotearLoteBUT3 = new HtmlSubmitButton("lotearLoteBUT3","Lotear",this);
		_listformdisplaybox1.addButton(_lotearLoteBUT3);

		_anularLoteBUT4 = new HtmlSubmitButton("anularLoteBUT4","Anular",this);
		_listformdisplaybox1.addButton(_anularLoteBUT4);

		_validarLoteBUT5 = new HtmlSubmitButton("validarLoteBUT5","Validar",this);
		_listformdisplaybox1.addButton(_validarLoteBUT5);

		_reLotearLoteBUT6 = new HtmlSubmitButton("reLotearLoteBUT6","Re-Lotear",this);
		_listformdisplaybox1.addButton(_reLotearLoteBUT6);

		_firmarLoteBUT7 = new HtmlSubmitButton("firmarLoteBUT7","Firmar",this);
		_listformdisplaybox1.addButton(_firmarLoteBUT7);

		_lotearParteBUT8 = new HtmlSubmitButton("lotearParteBUT8","Lotear",this);
		_listformdisplaybox2.addButton(_lotearParteBUT8);

		_anularParteBUT9 = new HtmlSubmitButton("anularParteBUT9","Anular",this);
		_listformdisplaybox2.addButton(_anularParteBUT9);

		_validarParteBUT10 = new HtmlSubmitButton("validarParteBUT10","Validar",this);
		_listformdisplaybox2.addButton(_validarParteBUT10);

		_firmarParteBUT11 = new HtmlSubmitButton("firmarParteBUT11","Firmar",this);
		_listformdisplaybox2.addButton(_firmarParteBUT11);
		
		_seleccionaTodoBUT13 = new HtmlSubmitButton("seleccionaTodoBUT13","Seleccionar",this);
		_listformdisplaybox2.addButton(_seleccionaTodoBUT13);
		
		_desSeleccionaTodoBUT14 = new HtmlSubmitButton("desSeleccionaTodoBUT14","Deseleccionar",this);
		_listformdisplaybox2.addButton(_desSeleccionaTodoBUT14);
		
		_lotearPartesSinLotearBUT15 = new HtmlSubmitButton("lotearPartesSinLotearBUT15","Lotear",this);
		_listformdisplaybox2.addButton(_lotearPartesSinLotearBUT15);
		
		// Agrega columna de seleccion al datasource de informes
		_dsPartes.addBucket(SELECCION_PARTE_FLAG, DataStore.DATATYPE_INT);
		_seleccionParte.setColumn(_dsPartes, SELECCION_PARTE_FLAG);
		_seleccionParte.setFalseValue(null);
		
		_dsPartes.setAutoValidate(true);
		addPageListener(this);
		_nuevoLoteBUT1.addSubmitListener(this);
		_grabarLoteBUT2.addSubmitListener(this);
		_lotearLoteBUT3.addSubmitListener(this);
		_anularLoteBUT4.addSubmitListener(this);
		_validarLoteBUT5.addSubmitListener(this);
		_reLotearLoteBUT6.addSubmitListener(this);
		_firmarLoteBUT7.addSubmitListener(this);
		_lotearParteBUT8.addSubmitListener(this);
		_anularParteBUT9.addSubmitListener(this);
		_validarParteBUT10.addSubmitListener(this);
		_firmarParteBUT11.addSubmitListener(this);
		_buscarPartesSinLotearBUT12.addSubmitListener(this);
		_seleccionaTodoBUT13.addSubmitListener(this);
		_desSeleccionaTodoBUT14.addSubmitListener(this);
		_lotearPartesSinLotearBUT15.addSubmitListener(this);
		_buscarLotesBUT16.addSubmitListener(this);
		
		// inicialmente oculta todos los botones de accioners 
		seteaBotones(-1);
		
		// adicionalmente hace invisible el boton de partes sin lotes
		_lotearPartesSinLotearBUT15.setVisible(false);

		super.initialize();
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {

		if (e.getComponent() == _buscarLotesBUT16) {
			// solicita buscar lotes
			String whereClause = _dsQBELotes.generateDataStoreFilter(_dsLotes);
			_dsLotes.reset();
			_dsLotes.retrieve(whereClause);
		}
		
		if (e.getComponent() == _nuevoLoteBUT1) {
			// se solicita crear un nuevo lote
			int row = _dsLotes.insertRow();
			// completa los datos obligatorios y luiego graba el lote
			_dsLotes.setLoteCargaPartesMoEstado(row, "0004.0001");
			java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
			_dsLotes.setLoteCargaPartesMoFechaAlta(row, hoy);
			try {
				_dsLotes.update();
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage("Error SQL recuperando partes del lote: " + ex.getMessage());
			}
			// hace foco en el registro
			_datatable1.setPage(_datatable1.getPage(row));
			_descripcionTE11.setFocus(row, true);
		}
		
		if (e.getComponent() == _grabarLoteBUT2) {
			// graba las modificaciones al lote
			try {
				_dsLotes.update();
			} catch (DataStoreException ex) {
				MessageLog.writeErrorMessage(ex, null);
				displayErrorMessage("Error SQL recuperando partes del lote: " + ex.getMessage());
				return false;
			}
			// hace foco en el registro
			if (rowLote_actual != -1)
			_datatable1.setPage(_datatable1.getPage(rowLote_actual));
			_descripcionTE11.setFocus(rowLote_actual, true);
		}
		
		if (e.getComponent() == _buscarPartesSinLotearBUT12) {
			// recupera los partes que no tienen asignados lote
			_dsPartes.reset();
			_dsPartes.retrieve("partes_mo.lote_id is null");
			_dsPartes.waitForRetrieve();
			if (_dsPartes.getRowCount() > 0) {
				_lotearPartesSinLotearBUT15.setVisible(true);
				_box3.setVisible(true);
			} else {
				_lotearPartesSinLotearBUT15.setVisible(false);
				_box3.setVisible(false);
			}
		}
		
		if (e.getComponent() == _lotearPartesSinLotearBUT15 || e.getComponent() == _lotearLoteBUT3) {
			// recorre el datastore de partes, asignando el parte y ejecutando la acción de lotear
			if (rowLote_actual != -1) {
				String estado_lote = _dsLotes.getLoteCargaPartesMoEstado(rowLote_actual);
				try {
					for (int i = 0; i < _dsPartes.getRowCount(); i++) {
						if (_dsPartes.getInt(i, SELECCION_PARTE_FLAG) == 1) {
							// parte marcado para lotear
							_dsPartes.setPartesMoLoteId(i,
									_dsLotes.getLoteCargaPartesMoLoteId(rowLote_actual));
							_dsPartes.ejecutaAccion(i, 5, "0003",
									getCurrentRequest().getRemoteAddr(),
									getSessionManager().getWebSiteUser().getUserID(),
									"lote_cargas_partes_mo",
									null,true);									
						}
					}
					// si todo estuvo ok hasta acá si el estado del lote es
					// generado lo cambia a loteado,
					// sino no hace nada
					if (estado_lote.equalsIgnoreCase("0004.0001"))
						_dsLotes.ejecutaAccion(rowLote_actual, "6", "0004",
								getSessionManager().getWebSiteUser(),
								getCurrentRequest().getRemoteAddr());
					_dsPartes.update();
					_dsLotes.update();
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				}

				try {
					_dsPartes.retrieve("partes_mo.lote_id = " + Integer.toString(_dsLotes.getLoteCargaPartesMoLoteId(rowLote_actual)));
				} catch (SQLException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage("Error SQL recuperando partes del lote: " + ex.getMessage());
					return false;
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage("Error de DataStore recuperando partes del lote: " + ex.getMessage());
					return false;
				}
				_lotearPartesSinLotearBUT15.setVisible(false);
				
				if (_dsPartes.getRowCount() > 0)
					_box3.setVisible(true);
				else
					_box3.setVisible(false);

			} else
				displayErrorMessage("Debe seleccionar un lote");
				return false;
		}
		
		if (e.getComponent() == _seleccionaTodoBUT13) {
			// marca todos los partes del datasource como seleccionados
			for (int i = 0; i < _dsPartes.getRowCount(); i++) {
				_dsPartes.setInt(i, SELECCION_PARTE_FLAG,1);
			}
		}

		if (e.getComponent() == _desSeleccionaTodoBUT14) {
			// marca todos los partes del datasource como seleccionados
			for (int i = 0; i < _dsPartes.getRowCount(); i++) {
				_dsPartes.setInt(i, SELECCION_PARTE_FLAG,0);
			}
		}
		
		if (e.getComponent() == _validarLoteBUT5) {
			// ejecuta la validación para los partes seleccionados
			// recorre el datastore de partes, asignando el parte y ejecutando la acción de validar
			if (rowLote_actual != -1) {
				try {
					for (int i = 0; i < _dsPartes.getRowCount(); i++) {
						_dsPartes.ejecutaAccion(i, 11, "0003",
								getCurrentRequest().getRemoteAddr(),
								getSessionManager().getWebSiteUser().getUserID(),
								"partes_mo",
								null,true);				
					}

					// si todo estuvo ok hasta acá si el estado del lote es
					// generado lo cambia a loteado,
					// sino no hace nada
					_dsLotes.ejecutaAccion(rowLote_actual, "7", "0004",
							getSessionManager().getWebSiteUser(),
							getCurrentRequest().getRemoteAddr());

					_dsPartes.update();
					_dsLotes.update();
				} catch (DataStoreException ex) {
					MessageLog.writeErrorMessage(ex, null);
					displayErrorMessage(ex.getMessage());
					return false;
				}
			} else
				displayErrorMessage("Debe seleccionar un lote");
				return false;
		}

		return super.submitPerformed(e);
	}
	
	@Override
	public void pageSubmitEnd(PageEvent p) {
		
		// ante cada submit de página verifica contexto y setea componentes visuales comportamiento
		if (_dsLotes.getRow() != -1) {
			// hay lote seleccionado
			if (_dsLotes.getRow() != rowLote_actual) {
				// cambio el lote, reseteo partes y busco los nuevos
				rowLote_actual = _dsLotes.getRow(); 
				_dsPartes.reset();
				try {
					_dsPartes.retrieve("partes_mo.lote_id = " + Integer.toString(_dsLotes.getLoteCargaPartesMoLoteId(rowLote_actual)));
				} catch (SQLException e) {
					MessageLog.writeErrorMessage(e, null);
					displayErrorMessage("Error SQL recuperando partes del lote: " + e.getMessage());
					e.printStackTrace();
				} catch (DataStoreException e) {
					MessageLog.writeErrorMessage(e, null);
					displayErrorMessage("Error de DataStore recuperando partes del lote: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			rowLote_actual = -1;
			_dsPartes.reset();
		}
		
		// en todos los casos reseteo los botones
		seteaBotones(rowLote_actual);

		super.pageSubmitEnd(p);
	}
	
	public void seteaBotones(int rowLote){
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		String SQL;
		String estado = null;
		String prompt = null;
		int accion = -1;

		if (rowLote == -1) {
			// no hay lote seleccionado, resetea botones
			_lotearLoteBUT3.setVisible(false);
			_anularLoteBUT4.setVisible(false);
			_validarLoteBUT5.setVisible(false);
			_reLotearLoteBUT6.setVisible(false);
			_firmarLoteBUT7.setVisible(false);
			_lotearParteBUT8.setVisible(false);
			_anularParteBUT9.setVisible(false);
			_validarParteBUT10.setVisible(false);
			_firmarParteBUT11.setVisible(false);
			
			// tambien oculto el box de los partes
			_box3.setVisible(false);
		} else {
			// hay seleccionado un lote, setea botones de acuerdo al estado
			_box3.setVisible(true);
			_lotearLoteBUT3.setVisible(false);
			_anularLoteBUT4.setVisible(false);
			_validarLoteBUT5.setVisible(false);
			_reLotearLoteBUT6.setVisible(false);
			_firmarLoteBUT7.setVisible(false);
			_lotearParteBUT8.setVisible(false);
			_anularParteBUT9.setVisible(false);
			_validarParteBUT10.setVisible(false);
			_firmarParteBUT11.setVisible(false);
			
			try {
				conn = DBConnection.getConnection(getApplicationName());

				// determino el estado actual del lote
				estado = _dsLotes.getLoteCargaPartesMoEstado(rowLote);

				// recorro los estados  y seteo los botones
				SQL = " SELECT prompt_accion,accion "
					+ " FROM infraestructura.transicion_estados t "
					+ " left join infraestructura.estados e on t.estado_origen = e.estado "
					+ " where e.circuito = '0004' "
					+ " and t.estado_origen = '" + estado + "'";
				st = conn.createStatement();
				r = st.executeQuery(SQL);

				while (r.next()) {
					prompt = r.getString(1);
					accion = r.getInt(2);
					
					if (prompt.equalsIgnoreCase("lotear")){
						_lotearLoteBUT3.setDisplayNameLocaleKey(Integer.toString(accion));
						_lotearLoteBUT3.setVisible(true);
					}
					else if (prompt.equalsIgnoreCase("anular")){
						_anularLoteBUT4.setDisplayNameLocaleKey(Integer.toString(accion));
						_anularLoteBUT4.setVisible(true);
					}
					else if (prompt.equalsIgnoreCase("validar")){
						_validarLoteBUT5.setDisplayNameLocaleKey(Integer.toString(accion));
						_validarLoteBUT5.setVisible(true);
					}
					else if (prompt.equalsIgnoreCase("re-lotear")){
						_reLotearLoteBUT6.setDisplayNameLocaleKey(Integer.toString(accion));
						_reLotearLoteBUT6.setVisible(true);
					}
					else if (prompt.equalsIgnoreCase("firmar")){
						_firmarLoteBUT7.setDisplayNameLocaleKey(Integer.toString(accion));
						_firmarLoteBUT7.setVisible(true);
					}
				}
				
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				displayErrorMessage("Error SQL armando botonera: " + e.getMessage());
			} catch (DataStoreException e) {
				MessageLog.writeErrorMessage(e, null);
				displayErrorMessage("Error de DataStore armando botonera: " + e.getMessage());
				e.printStackTrace();
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

}
