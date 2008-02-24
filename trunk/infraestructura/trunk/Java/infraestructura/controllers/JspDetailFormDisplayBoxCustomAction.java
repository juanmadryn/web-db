package infraestructura.controllers;

import infraestructura.reglasNegocio.EjecutaReglaNegocio;

import java.sql.SQLException;

import com.salmonllc.html.HtmlPage;
import com.salmonllc.jsp.JspDetailFormDisplayBox;
import com.salmonllc.sql.DataStoreException;

/**
 * An extended version of JspDetailFormDisplayBox which allow to specify
 * custom functionality or behavior for default form buttons (save, add,
 * delete, cancel). 
 * 
 * @author fep
 *
 */
@SuppressWarnings("serial")
public class JspDetailFormDisplayBoxCustomAction extends
		JspDetailFormDisplayBox {
	
	private EjecutaReglaNegocio deleteAction;
	private EjecutaReglaNegocio addAction;
	private EjecutaReglaNegocio cancelAction;
	private EjecutaReglaNegocio editAction;
	private EjecutaReglaNegocio saveAction;

	public JspDetailFormDisplayBoxCustomAction(String name, HtmlPage page) {
		super(name, page);	
	}

	/* (non-Javadoc)
	 * @see com.salmonllc.jsp.JspDetailFormDisplayBox#doAdd()
	 */
	@Override
	public void doAdd() {
		if (addAction != null) {
			try {
				addAction.ejecutaAccion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			super.doAdd();
		}		
	}

	/* (non-Javadoc)
	 * @see com.salmonllc.jsp.JspDetailFormDisplayBox#doCancel()
	 */
	@Override
	public void doCancel() throws Exception {
		if (cancelAction != null) {
			cancelAction.ejecutaAccion();
		} else {
			super.doCancel();
		}
	}

	/* (non-Javadoc)
	 * @see com.salmonllc.jsp.JspDetailFormDisplayBox#doDelete()
	 */
	@Override
	public void doDelete() throws Exception {
		if (deleteAction != null) {
			deleteAction.ejecutaAccion();
		} else {
			super.doDelete();
		}
	}

	/* (non-Javadoc)
	 * @see com.salmonllc.jsp.JspDetailFormDisplayBox#doEdit()
	 */
	@Override
	public void doEdit() throws SQLException, DataStoreException, Exception {
		if (editAction != null) {
			editAction.ejecutaAccion();
		} else {
			super.doEdit();
		}
	}

	/* (non-Javadoc)
	 * @see com.salmonllc.jsp.JspDetailFormDisplayBox#doSave()
	 */
	@Override
	public void doSave() throws Exception {
		if (saveAction != null) {
			saveAction.ejecutaAccion();
		} else {
			super.doSave();
		}
	}

	/**
	 * @param deleteAction the deleteAction to set
	 */
	public void setDeleteAction(EjecutaReglaNegocio deleteAction) {
		this.deleteAction = deleteAction;
	}

	/**
	 * @param addAction the addAction to set
	 */
	public void setAddAction(EjecutaReglaNegocio addAction) {
		this.addAction = addAction;
	}

	/**
	 * @param cancelAction the cancelAction to set
	 */
	public void setCancelAction(EjecutaReglaNegocio cancelAction) {
		this.cancelAction = cancelAction;
	}

	/**
	 * @param editAction the editAction to set
	 */
	public void setEditAction(EjecutaReglaNegocio editAction) {
		this.editAction = editAction;
	}

	/**
	 * @param saveAction the saveAction to set
	 */
	public void setSaveAction(EjecutaReglaNegocio saveAction) {
		this.saveAction = saveAction;
	}
	

}
