package infraestructura.controllers;

import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.AttributeButton;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.jsp.JspListFormDisplayBox;
import com.salmonllc.sql.DataStoreException;

//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com

/**
 * This's
 */

public class BaseEntityController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 764223385541957154L;

	private String tabla_principal = null;

	private int row_id;

	private JspListFormDisplayBox attributeContainer;

	public AtributosEntidadModel _dsAttributes;

	private Vector<AttributeButton> attributeButtons;

	private Vector<HtmlSubmitButton> accionesButtons;

	private int botonSeleccionado = -1;

	/**
	 * Base constructor that call superclass constructor
	 * 
	 */
	public BaseEntityController() {
		super();
		this.row_id = -1;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean submitPerformed(SubmitEvent e) throws Exception {
		// TODO Auto-generated method stub

		if(_dsAttributes == null)
			return super.submitPerformed(e);
		
		HtmlComponent componente = e.getComponent();
		if (componente instanceof HtmlSubmitButton)
			if (attributeButtons != null) {
				HtmlSubmitButton button = (HtmlSubmitButton) componente;
				String label = button.getDisplayName().trim();

				for (AttributeButton attButton : attributeButtons) {
					if (attButton.getLabel().equalsIgnoreCase(label)) {
						if (row_id > 0) {
							seteaNuevoBoton(attButton.getIndex());
							recuperaAtributosBotonSeleccionado();
						} else {
							// no est� en contexto de nung�n proyecto reseteo
							// las
							// vistas
							_dsAttributes.reset();
						}
					}
				}
			}

		seteaBotonesAtributos();
		recuperaAtributosBotonSeleccionado();
		_dsAttributes.gotoFirst();

		return super.submitPerformed(e);
	}

	public void setAttributeButton(AttributeButton button) {

		Enumeration<HtmlComponent> componentes = attributeContainer.getComponents();
		HtmlComponent componente;
		while (componentes.hasMoreElements()) {
			componente = componentes.nextElement();
			if (componente instanceof HtmlSubmitButton)
				if (button.getButton().getDisplayName().equalsIgnoreCase(
						((HtmlSubmitButton) componente).getDisplayName()))
					return;
			;
		}

		attributeContainer.addButton(button.getButton());
		attributeButtons.add(button);
	}

	/**
	 * Arma la botonera de atributos en funci�n del id de proyecto indicado.
	 * 
	 * @param p_proyecto_id
	 *            el id del proyecto para el cual se quieren setear los botones
	 * 
	 * TODO generalizar este m�todo a un n�mero indefinido de etiquetas para
	 * atributos
	 */
	public void seteaBotonesAtributos() throws DataStoreException, SQLException {

		Vector<String> et = null;

		// si no hay seteado proyecto termina
		if (!(row_id > 0))
			return;

		
		
		// setea los botones de los atributos seg�n las etiquetas
		try {
			
			et = _dsAttributes.recuperaEtiquetasAtributosObjetoAplicacion(tabla_principal);
		} catch (DataStoreException e) {
			displayErrorMessage(e.getLocalizedMessage());
			return;
		} catch (SQLException e) {
			displayErrorMessage(e.getLocalizedMessage());
			return;
		}
		if (et != null) {
			if (attributeButtons == null)
				attributeButtons = new Vector<AttributeButton>();

			Vector<String> labels = new Vector<String>();

			for (AttributeButton attButton : attributeButtons)
				labels.add(attButton.getLabel());
			
			
			for (String displayName : et) {				
				if (!labels.contains(displayName.trim())) {

					HtmlSubmitButton button = new HtmlSubmitButton("attButton"
							+ displayName, "", this);
					if (displayName != null && displayName.trim().length() != 0)
						button.setDisplayName(displayName);
					else if (!labels.contains("General"))
						button.setDisplayName("General");
					else {						
						return;
					}

					button.setVisible(true);
					button.addSubmitListener(this);
					AttributeButton attBoton = new AttributeButton(button,
							button.getDisplayName(), attributeButtons.size());					

					setAttributeButton(attBoton);					
				}

			}

			// seteo el primero
			seteaNuevoBoton(0);
		} else
			seteaNuevoBoton(-1);

	}

	public void seteaNuevoBoton(int newBotton) throws DataStoreException,
			SQLException {
		// resetea el boton actual y setea el nuevo

		if (attributeButtons != null && attributeButtons.size() != 0) {
			if (newBotton == -1) {
				for (AttributeButton button : attributeButtons)
					button.getButton().setButtonBgColor("lightGray");				
			} else {
				if (botonSeleccionado != -1)
					for (AttributeButton button : attributeButtons)
						if (button.getIndex() == botonSeleccionado)
							button.getButton().setButtonBgColor("lightGray");

				botonSeleccionado = newBotton;

				for (AttributeButton button : attributeButtons)
					if (button.getIndex() == botonSeleccionado)
						button.getButton().setButtonBgColor("red");

			}
		}
	}

	/**
	 * Recupera los atributos correspondientes a la etiqueta del bot�n
	 * seleccionado para el objeto .
	 * 
	 * @param p_objeto_id
	 *            el id del registro para la cual se desean recuperar los
	 *            atributos
	 * @param nombre_tabla
	 *            nombre de la tabla para la cual se desean recuperar los
	 *            atributos
	 * @throws SQLException
	 *             en caso de que surja un error de conexi�n o interacci�n con
	 *             la base de datos
	 * @throws DataStoreException
	 *             en caso de que ocurra un error de manejos de los DataStore's
	 *             TODO generalizar este m�todo a un n�mero indefinido de
	 *             etiquetas para atributos
	 */
	public void recuperaAtributosBotonSeleccionado() throws SQLException,
			DataStoreException {
		if (botonSeleccionado != -1) {
			for (AttributeButton attButton : attributeButtons)
				if (attButton.getIndex() == botonSeleccionado) {
					if ("General".equalsIgnoreCase(attButton.getLabel()))
						_dsAttributes
								.recuperaAtributosEtiquetaObjetoAplicacion(
										null, row_id, tabla_principal);
					else
						_dsAttributes
								.recuperaAtributosEtiquetaObjetoAplicacion(
										attButton.getLabel(), row_id,
										tabla_principal);
				}
		}
	}

	public int getRow_id() {
		return row_id;
	}

	public void setRow_id(int row_id) {
		this.row_id = row_id;
	}

	public JspListFormDisplayBox getContainer() {
		return attributeContainer;
	}

	public void setContainer(JspListFormDisplayBox container) {
		this.attributeContainer = container;
	}

	public String getTabla_principal() {
		return tabla_principal;
	}

	public void setTabla_principal(String tabla_principal) {
		this.tabla_principal = tabla_principal;
	}

	public void set_dsAtributos(AtributosEntidadModel atributos) {
		_dsAttributes = atributos;
	}

}
