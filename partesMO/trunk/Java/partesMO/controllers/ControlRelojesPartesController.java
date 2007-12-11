//package statement
package partesMO.controllers;

//Salmon import statements

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import partesMO.models.ResumenHorasRelojModel;
import partesMO.reglasNegocio.ValidarTotalRelojPartesMo;
import infraestructura.controllers.BaseController;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import com.salmonllc.html.HtmlOption;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.*;

/**
 * ControlRelojesPartesController: a SOFIA generated controller
 */
public class ControlRelojesPartesController extends BaseController implements ValueChangedListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5466704678964409448L;
	// Visual Components
	public com.salmonllc.html.HtmlImage _bannerDividerImage;
	public com.salmonllc.html.HtmlImage _bannerDivImage2;
	public com.salmonllc.html.HtmlImage _imgMainLogo;
	public com.salmonllc.html.HtmlSubmitButton _generaResumenBUT;
	public com.salmonllc.html.HtmlText _fechahastaCAP2;
	public com.salmonllc.html.HtmlText _apeynomTE12;
	public com.salmonllc.html.HtmlText _guionTE12;
	public com.salmonllc.html.HtmlText _horasCAP13;
	public com.salmonllc.html.HtmlText _horasrelojCAP14;
	public com.salmonllc.html.HtmlText _horasrelojTE14;	
	public com.salmonllc.html.HtmlText _estadoCAP19;
	public com.salmonllc.html.HtmlDropDownList _estadoTE19;
	public com.salmonllc.html.HtmlText _obsvTE15;
	public com.salmonllc.html.HtmlText _horasTE13;
	public com.salmonllc.html.HtmlTextEdit _buscarTE3;
	public com.salmonllc.html.HtmlText _legajoCAP12;
	public com.salmonllc.html.HtmlText _fechadesdeCAP1;
	public com.salmonllc.html.HtmlText _nroLegajoTE12;
	public com.salmonllc.html.HtmlText _obsvCAP15;
	public com.salmonllc.html.HtmlText _periodoCAP10;
	public com.salmonllc.html.HtmlText _periodoTE10;
	public com.salmonllc.html.HtmlText _quincenaCAP11;
	public com.salmonllc.html.HtmlText _quincenaTE11;
	public com.salmonllc.html.HtmlText _text1Footer;
	public com.salmonllc.html.HtmlText _text2Footer;
	public com.salmonllc.html.HtmlText _text3Footer;
	public com.salmonllc.html.HtmlText _txtBannerOptions;
	public com.salmonllc.html.HtmlText _welcomeText;
	public com.salmonllc.html.HtmlTextEdit _fechahastaTE2;
	public com.salmonllc.html.HtmlTextEdit _fechadesdeTE1;
	public com.salmonllc.jsp.JspBox _box1;
	public com.salmonllc.jsp.JspBox _box2;
	public com.salmonllc.jsp.JspContainer _welcomeContainer;
	public com.salmonllc.jsp.JspDataTable _datatable2;
	public com.salmonllc.jsp.JspDisplayBox _displaybox1;
	public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;
	public com.salmonllc.jsp.JspForm _bannerForm;
	public com.salmonllc.jsp.JspForm _pageForm;
	public com.salmonllc.jsp.JspLink _baseLinkAdminSalmon;
	public com.salmonllc.jsp.JspLink _footerInfDevAbout;
	public com.salmonllc.jsp.JspLink _footerproyectosHelp;
	public com.salmonllc.jsp.JspLink _footerSalmonLink;
	public com.salmonllc.jsp.JspLink _footerSofiaLink;
	public com.salmonllc.jsp.JspLink _lnkBannerOptions;
	public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
	public com.salmonllc.jsp.JspTable _tableFooter;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader4;
	public com.salmonllc.jsp.JspTableCell _datatable2TDHeader5;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow0;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow1;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow2;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow3;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow4;
	public com.salmonllc.jsp.JspTableCell _datatable2TDRow5;
	public com.salmonllc.jsp.JspTableCell _navbarTableTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow0;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow1;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow2;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow3;
	public com.salmonllc.jsp.JspTableCell _tableFooterTDRow4;
	public com.salmonllc.jsp.JspTableRow _datatable2TRHeader0;
	public com.salmonllc.jsp.JspTableRow _datatable2TRRow0;
	public com.salmonllc.jsp.JspTableRow _navbarTableTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow0;
	public com.salmonllc.jsp.JspTableRow _tableFooterTRRow1;

	// DataSources
	public com.salmonllc.sql.DataStore _dsPeriodo;
	public partesMO.models.ResumenHorasRelojModel _dsResHor;
	private partesMO.models.PartesMoModel _dsPartes;
	public com.salmonllc.sql.QBEBuilder _dsQBEResHor;

	// DataSource Column Constants
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_RESUMEN_ID = "resumen_horas_reloj.resumen_id";
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_PERIODO = "resumen_horas_reloj.periodo";
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_NRO_LEGAJO = "resumen_horas_reloj.nro_legajo";
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_APEYNOM = "resumen_horas_reloj.apeynom";
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_QUINCENA = "resumen_horas_reloj.quincena";
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_HORAS = "resumen_horas_reloj.horas";
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_HORAS_RELOJ = "resumen_horas_reloj.horas_reloj";
	public static final String DSRESHOR_RESUMEN_HORAS_RELOJ_OBSERVACIONES = "resumen_horas_reloj.observaciones";

	public static final String SELECCION_RESUMEN_FLAG = "resumen_horas_reloj.SELECCION_RESUMEN_FLAG";
	public static final String DSPERIODO_DESDE = "desde";
	public static final String DSPERIODO_HASTA = "hasta";

	// Componentes visuales custom
	public com.salmonllc.html.HtmlSubmitButton _buscarBUT16;	
	public com.salmonllc.html.HtmlCheckBox _seleccionParte;
	public com.salmonllc.html.HtmlText _selParteCAP18;
	
	public com.salmonllc.html.HtmlSubmitButton _anularParteBUT9;
	public com.salmonllc.html.HtmlSubmitButton _validarParteBUT10;
	public com.salmonllc.html.HtmlSubmitButton _firmarParteBUT11;
	public com.salmonllc.html.HtmlSubmitButton _seleccionaTodoBUT13;
	public com.salmonllc.html.HtmlSubmitButton _desSeleccionaTodoBUT14;
	
	// Custom
	private DBConnection conexion = null;	
	
	/**
	 * Initialize the page. Set up listeners and perform other initialization
	 * activities.
	 */
	public void initialize() throws Exception {
		super.initialize();
		
		_buscarBUT16 = new HtmlSubmitButton("buscarBUT16","Buscar",this);
		_buscarBUT16.setAccessKey("b");
		_searchformdisplaybox1.addButton(_buscarBUT16);
		
		_anularParteBUT9 = new HtmlSubmitButton("anularParteBUT9","Anular",this);
		_listformdisplaybox1.addButton(_anularParteBUT9);		

		_validarParteBUT10 = new HtmlSubmitButton("validarParteBUT10","Validar",this);
		_listformdisplaybox1.addButton(_validarParteBUT10);

		_firmarParteBUT11 = new HtmlSubmitButton("firmarParteBUT11","Firmar",this);
		_listformdisplaybox1.addButton(_firmarParteBUT11);
		
		_seleccionaTodoBUT13 = new HtmlSubmitButton("seleccionaTodoBUT13","Seleccionar",this);
		_listformdisplaybox1.addButton(_seleccionaTodoBUT13);
		
		_desSeleccionaTodoBUT14 = new HtmlSubmitButton("desSeleccionaTodoBUT14","Deseleccionar",this);
		_listformdisplaybox1.addButton(_desSeleccionaTodoBUT14);
				
		_generaResumenBUT.setAccessKey("v");
		
		/*_estadoTE19.addOption(new HtmlOption(_dsResHor.conErroresInClause(),"Partes con errores", true));
		_estadoTE19.addOption(new HtmlOption(String.valueOf(ResumenHorasRelojModel.PARTES_ERROR),ResumenHorasRelojModel.PARTES_ERROR_MSG));
		_estadoTE19.addOption(new HtmlOption(String.valueOf(ResumenHorasRelojModel.PARTES_SIN_FICHADA),ResumenHorasRelojModel.PARTES_SIN_FICHADA_MSG));
		_estadoTE19.addOption(new HtmlOption(String.valueOf(ResumenHorasRelojModel.FICHADA_SIN_PARTES),ResumenHorasRelojModel.FICHADA_SIN_PARTES_MSG));
		_estadoTE19.addOption(new HtmlOption(String.valueOf(ResumenHorasRelojModel.PARTES_OK),ResumenHorasRelojModel.PARTES_OK_MSG));		
		_estadoTE19.addOption(new HtmlOption(String.valueOf(ResumenHorasRelojModel.PARTES_VAL),ResumenHorasRelojModel.PARTES_VAL_MSG));
		_estadoTE19.addOption(new HtmlOption(_dsResHor.todosInClause(),"Todos los resultados"));*/
		
		addPageListener(this);
		_generaResumenBUT.addSubmitListener(this);
		_buscarBUT16.addSubmitListener(this);		
		_anularParteBUT9.addSubmitListener(this);
		_validarParteBUT10.addSubmitListener(this);
		_firmarParteBUT11.addSubmitListener(this);
		_seleccionaTodoBUT13.addSubmitListener(this);
		_desSeleccionaTodoBUT14.addSubmitListener(this);
		// validar fecha antes de ser enviada al DataStore
		_fechadesdeTE1.addValueChangedListener(this);		
		_fechahastaTE2.addValueChangedListener(this);		
		
		_dsPeriodo.reset();
		_dsPeriodo.insertRow();				
		seteaPeriodo(); // valores por defecto para el periodo de fechas		
		_dsPeriodo.gotoFirst();
		
		_dsPartes = new partesMO.models.PartesMoModel(getApplicationName(),"partesmo");		
		// Agrega columna de seleccion al datasource de resumen de horas
		_dsResHor.addBucket(SELECCION_RESUMEN_FLAG, DataStore.DATATYPE_INT);
		_seleccionParte.setColumn(_dsResHor, SELECCION_RESUMEN_FLAG);
		_seleccionParte.setFalseValue(null);
		
		seteaBotones(-1);
	} 
	
	/**
	 * Setea el periodo por defecto para el rango de fechas
	 * @throws DataStoreException 
	 */
	public void seteaPeriodo() throws DataStoreException {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
				
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));		
		java.sql.Date hasta = new java.sql.Date(cal.getTimeInMillis());
		_dsPeriodo.setDate("hasta", hasta);		
		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		java.sql.Date desde = new java.sql.Date(cal.getTimeInMillis());
		_dsPeriodo.setDate("desde", desde);		
	}

	/**
	 * Process the given submit event
	 * 
	 * @param event the submit event to be processed
	 * @return true to continue processing events, false to stop processing
	 *         events
	 * @throws Exception
	 */
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		String whereFecha = " fecha between '"
				+ _dsPeriodo.getDate("desde").toString() + "' and '"
				+ _dsPeriodo.getDate("hasta").toString() + "'";
		
		// chequeo las fechas
		if (_dsPeriodo.getDate("hasta").compareTo(_dsPeriodo.getDate("desde")) < 0 )  {
			displayErrorMessage("Error Controlando Relojes: combinación de fechas inválida");
			return false;
		}
		
		// marca todos los partes del datasource como seleccionados
		if (e.getComponent() == _seleccionaTodoBUT13) {
			for (int i = 0; i < _dsResHor.getRowCount(); i++) {
				_dsResHor.setInt(i, SELECCION_RESUMEN_FLAG,1);
			}
		}
		
		// marca todos los partes del datasource como seleccionados
		if (e.getComponent() == _desSeleccionaTodoBUT14) {
			for (int i = 0; i < _dsResHor.getRowCount(); i++) {
				_dsResHor.setInt(i, SELECCION_RESUMEN_FLAG,0);
			}
		}
		
		// Buscar
		if (e.getComponent() == _buscarBUT16) {			
			_dsResHor.reset();
			_dsResHor.retrieve(_dsQBEResHor.generateSQLFilter(_dsResHor)
					+ " and " + whereFecha);
			seteaBotones(_estadoTE19.getSelectedIndex());
		}
		
		// Validar partes seleccionados
		if (e.getComponent() == _validarParteBUT10) {
			try {
				_dsResHor.filter(SELECCION_RESUMEN_FLAG + " != null");

				// validacion manual
				ValidarTotalRelojPartesMo autorizarPartesMo = new ValidarTotalRelojPartesMo();

				conexion = DBConnection.getConnection(getApplicationName(),"partesmo");
				conexion.beginTransaction();
				// actualiza el estado de resumenes seleccionados 
				autorizarPartesMo.esValido(_dsResHor, null, conexion);			

				_dsResHor.gotoFirst();			
				// intervalo de partes a validar
				_dsPartes.retrieve(whereFecha);
				_dsPartes.setBatchInserts(true);
				_dsPartes.doValidarPartes(false);

				// cambiamos el estado de los partes a valido				
				for (int i = 0; i < _dsResHor.getRowCount(); i++ ) {
					String[] pids = _dsResHor.getResumenHorasRelojParteIds(i).split(",");
					for (String p : pids) {
						_dsPartes.setFindExpression("partes_mo.parte_id == " + p);
						_dsPartes.findFirst();
						_dsPartes.ejecutaAccion(11, "0003", getCurrentRequest()
								.getRemoteAddr(), getSessionManager()
								.getWebSiteUser().getUserID(), "partes_mo",
								conexion,true);
					}					
				}
				conexion.commit();	
				_dsPartes.doValidarPartes(true);
			} catch (DataStoreException ex) {				
				MessageLog.writeErrorMessage(ex, this);
				displayErrorMessage("Error Autorizando Partes: " + ex.getMessage());
				return false;
			} catch (Exception ex) {				
				MessageLog.writeErrorMessage(ex, this);
				displayErrorMessage("Error Autorizando Partes: " + ex.getMessage());
				return false;
			} finally {
				if (conexion != null) {
					conexion.rollback();
					conexion.freeConnection();
				}
			}
		}
		
		// Genera el resumen
		if (e.getComponent() == _generaResumenBUT) {			
			try {				
				// Preprocesa las partes y las fichadas en tango para facilitar la validación
				_dsPartes.generaResumenRelojes(_dsPeriodo.getDate("desde"), _dsPeriodo.getDate("hasta"));
				// recupera resumen
				_dsResHor.reset();				
				_dsResHor.retrieve(whereFecha + " and estado in (" + _dsResHor.conErroresInClause() +")");
				seteaBotones(1);
			} catch (DataStoreException ex) {				
				MessageLog.writeErrorMessage(ex, this);
				displayErrorMessage("Error Controlando Relojes: " + ex.getMessage());
				return false;
			} catch (Exception ex) {				
				MessageLog.writeErrorMessage(ex, this);
				displayErrorMessage("Error Controlando Relojes: " + ex.getMessage());
				return false;
			} finally {
				if (conexion != null) {
					conexion.rollback();
					conexion.freeConnection();
				}
			}
		}	
		
		return super.submitPerformed(e);
	}
	
	public boolean valueChanged(ValueChangedEvent e) throws Exception {
		// TextEdits para fechas de vigencia
		if (e.getComponent() == _fechadesdeTE1) {			
			if (!_dsPeriodo.isFormattedStringValid(e.getColumn(), e.getNewValue())) {
				// No movemos el nuevo valor al dataStore,pero evitamos 
				// que sea eliminado la proxima vez que la pagina sea mostrada					
				e.setAcceptValue(ValueChangedEvent.PROCESSING_KEEP_CHANGE_IN_QUEUE);
				displayErrorMessage("Error Controlando Relojes: fecha o formato de fecha inválido");
				return false;				
			}
		}
		
		if (e.getComponent() == _fechahastaTE2) {			
			if (!_dsPeriodo.isFormattedStringValid(e.getColumn(), e.getNewValue())) {
				// No movemos el nuevo valor al dataStore,pero evitamos 
				// que sea eliminado la proxima vez que la pagina sea mostrada					
				e.setAcceptValue(ValueChangedEvent.PROCESSING_KEEP_CHANGE_IN_QUEUE);
				displayErrorMessage("Error Controlando Relojes: fecha o formato de fecha inválido");					
				return false;				
			}
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see infraestructura.controllers.BaseController#pageSubmitEnd(com.salmonllc.html.events.PageEvent)
	 */
	@Override
	public void pageSubmitEnd(PageEvent p) {
		super.pageSubmitEnd(p);
	}
	
	public void seteaBotones(int opcion) {
		DBConnection conn = null;
		Statement st = null;
		ResultSet r = null;
		String SQL;
		String estado = null;
		String prompt = null;
		int accion = -1;
		boolean activarVal = true;

		if (opcion == -1) {
			// no hay lote seleccionado, resetea botones
			_anularParteBUT9.setVisible(false);
			_validarParteBUT10.setVisible(false);
			_firmarParteBUT11.setVisible(false);
			_seleccionaTodoBUT13.setVisible(false);
			_desSeleccionaTodoBUT14.setVisible(false);
		} else {
			if (opcion == ResumenHorasRelojModel.PARTES_VAL) {
				activarVal = false;
			}
			_seleccionParte.setEnabled(activarVal);
			_validarParteBUT10.setVisible(activarVal);
			_anularParteBUT9.setVisible(false);			
			_firmarParteBUT11.setVisible(false);
			_seleccionaTodoBUT13.setVisible(true);
			_desSeleccionaTodoBUT14.setVisible(true);
		
			try {
				conn = DBConnection.getConnection(getApplicationName());

				// recorro los estados  y seteo los botones
				SQL = " SELECT prompt_accion,accion "
					+ " FROM infraestructura.transicion_estados t "
					+ " left join infraestructura.estados e on t.estado_origen = e.estado "
					+ " where e.circuito = '0003' "
					+ " and t.estado_origen = '" + estado + "'";
				st = conn.createStatement();
				r = st.executeQuery(SQL);

				while (r.next()) {
					prompt = r.getString(1);
					accion = r.getInt(2);
					
					if (prompt.equalsIgnoreCase("validar")) {
						_validarParteBUT10.setDisplayNameLocaleKey(Integer.toString(accion));
						_validarParteBUT10.setVisible(true);
					}
				}
				
			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				displayErrorMessage("Error SQL armando botonera: " + e.getMessage());
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
