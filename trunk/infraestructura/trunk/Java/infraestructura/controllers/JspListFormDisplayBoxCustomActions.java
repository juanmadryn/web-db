package infraestructura.controllers;

import infraestructura.reglasNegocio.EjecutaReglaNegocio;

import com.salmonllc.html.HtmlContainer;
import com.salmonllc.html.HtmlHiddenField;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.HtmlValidatorText;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.html.events.ValueChangedEvent;
import com.salmonllc.html.events.ValueChangedListener;
import com.salmonllc.jsp.JspController;
import com.salmonllc.jsp.JspListFormDisplayBox;

/**
 * An extended version of JspDetailFormDisplayBox which allow to specify
 * custom functionality or behavior for default form buttons (save, add,
 * delete, cancel). 
 * 
 * @author fep
 *
 */
@SuppressWarnings("serial")
public class JspListFormDisplayBoxCustomActions extends JspListFormDisplayBox {
	
	private String _okToDeleteQuestion = "pomelo!";
	private HtmlHiddenField _okToDeleteValue;	
	private EjecutaReglaNegocio buttonDeleteAction;
	private HtmlSubmitButton _deleteButton; 
	private HtmlSubmitButton _okToEditButton, _okToDeleteButton, _okToCancelButton, _okToAddButton, _cancelMessageButton;
	private HtmlContainer _okToEdit, _okToDelete, _okToCancel, _okToAdd;
	private String _deleteButtonAccessKey;
	private String _deleteButtonCaption;
	private boolean _confirmDelete = true;
	
	private class CancelMessageAction implements SubmitListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			return true;
		}
	}
	
	private class DeleteAction implements SubmitListener, ValueChangedListener {
		public boolean submitPerformed(SubmitEvent e) throws Exception {
			doDelete();
			return true;
		}

        public boolean valueChanged(ValueChangedEvent e) throws Exception {
            if (_okToDeleteValue.getValue() != null && _okToDeleteValue.getValue().equals("1")) {
                doDelete();
            }
            return true;
        }
    }
    	
	public JspListFormDisplayBoxCustomActions(String name, Integer mode, HtmlPage page) {
		this(name, mode, null, page);
	}

	public JspListFormDisplayBoxCustomActions(String name, Integer mode, String theme, HtmlPage page) {
		super(name, mode, theme, page);
		
		_okToDelete = new HtmlContainer("dfoktodeletequestion", page);
		
		DeleteAction deleteAction = new DeleteAction();
		
		_cancelMessageButton = new MessageButton(getPage(), "dfcancelmessage", _cancelButtonCap, new CancelMessageAction());		
		_okToDeleteButton = new MessageButton(getPage(), "dfoktodeletemessage", _okButtonCap, deleteAction);	
		
		_okToDeleteValue = new HtmlHiddenField("jsoktodeletevalue", "0", page);
        _okToDeleteValue.addValueChangedListener(deleteAction);
        add(_okToDeleteValue, TYPE_COMP);
        _hidden.add(_okToDeleteValue);
		
		_okToDelete.add(_okToDeleteButton);
		_okToDelete.add(_cancelMessageButton);
		
		_messageButtons.add(_okToDeleteButton);
		_messageButtons.add(_cancelMessageButton);
		
		_deleteButton = new HtmlSubmitButton(name + "deleteButton", "Delete", theme, page);
		_deleteButton.addSubmitListener(this);
		_buttons.add(_deleteButton);
		add(_deleteButton, TYPE_COMP);		
		
		setUpButtons();
	}
	
	@Override
	public void autoBindComponents() throws Exception {	
		super.autoBindComponents();
		if (_validator == null) {
			_validator = new HtmlValidatorText(getName() + "validator", (JspController) getPage());			
			_validator.setBreaksBefore(0);
			_validator.setBreaksAfter(2);
			_validatorBuiltInternally = true;
			add(_validator, TYPE_COMP);
		}
	}
	
	protected void setUpButtons() {
        super.setUpButtons();
        if (_deleteButton != null) {
            if (_deleteButtonCaption != null)
                _deleteButton.setDisplayName(_deleteButtonCaption);
            if (_deleteButtonAccessKey != null)
                _deleteButton.setAccessKey(_deleteButtonAccessKey);
        }
        if (_okToDeleteButton != null && _okButtonCap != null)
			_okToDeleteButton.setDisplayName(_okButtonCap);
        if (_cancelMessageButton != null && _cancelButtonCap != null)
			_cancelMessageButton.setDisplayName(_cancelButtonCap);
    }
	
	/**
     * Sets the delete button visible or not
     */
    public void setDeleteButtonVisible(boolean visible) {
        if (_deleteButton != null)
            _deleteButton.setVisible(visible);
    }
    
    /**
     * Sets the text to display on the delete button
     */
    public void setSaveButtonCaption(String caption) {
        if (_deleteButton != null)
            _deleteButton.setDisplayName(caption);
    }

    /**
     * Returns the delete button visible or not
     */
    public boolean isSaveButtonVisible() {
        if (_deleteButton == null)
            return false;
        else
            return _deleteButton.getVisible();
    }
    /**
     * Returns the text to display on the delete button
     */
    public String getSaveButtonCaption() {
        if (_deleteButton == null)
            return null;
        else
            return _deleteButton.getDisplayName();
    }
    
    @Override
    // TODO: implementarme
    protected void processLocaleInfo() {
    	super.processLocaleInfo();
    }
    
    @Override
    public boolean submitPerformed(SubmitEvent e) throws Exception {
    	
    	if (e.getComponent() == _deleteButton)
            tryDelete();
    
    	return super.submitPerformed(e);
    }
    
    /**
     * Set the access key for the delete button
     */
    public void setDeleteButtonAccessKey(String key) {
        _deleteButton.setAccessKey(key);
    }
    
    /**
     * @return the delete button used by this component
     */
    public HtmlSubmitButton getDeleteButton() {
        return _deleteButton;
    }

	/**
	 * Implementa funcionalidad de eliminado
	 * @throws Exception
	 */
	public void tryDelete() throws Exception {
		if (_confirmDelete) {
            if (getValidator().getUseAlertsForErrors()) {
                addConfirmScript(_okToDeleteQuestion, _okToDeleteValue);
            }
            else {
                _validator.setErrorMessage(_okToDeleteQuestion, null, -1, _okToDelete);
            }
		}
        else {
        	 doDelete();
        }
	}
	
	/**
	 * This method gets fired when the user clicks the delete button. Subclasses can override it to customize behavior
	 */
	public void doDelete() {
		try {
            //deleteAction.ejecutaAccion();
        	System.out.println("---> deleteAction.ejecutaAccion() should be executed at this point!");
        } catch (Exception e1) {
            _validator.setErrorMessage(e1.getMessage());
        }
	}
	
	/**
	 * @param deleteAction the deleteAction to set
	 */
	public void setDeleteAction(EjecutaReglaNegocio deleteAction) {
		this.buttonDeleteAction = deleteAction;
	}
	
	/**
	 * @return whether or not the form will confirm deletes
	 */
	public boolean getConfirmDelete() {
		return _confirmDelete;
	}

	/**
	 * sets whether or not the form will confirm deletes
	 */
	public void setConfirmDelete(boolean b) {
		_confirmDelete = b;
	}

}
