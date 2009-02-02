package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/LookupTag.java $
//$Author: Dan $
//$Revision: 20 $
//$Modtime: 10/01/04 2:23p $
/////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.jsp.Constants;
/**
 * This class creates the HtmlLookupComponent
 * Creation date: (8/21/01 11:50:16 AM)
 * @author : Administrator
 */
public class LookupTag extends BaseEmptyTag {

	private String _fieldName = null;
	private String _fieldBrowseimage =null;
	private String _fieldLookupurl = null;
	private String _fieldDatasource = null;
	private String _fieldSize = null;
	private String _fieldMaxLength = null;
	private String _usePopup = null;
	private String _popupWidth = null;
	private String _popupHeight = null;
	private String _displayFormat = null;
	private String _displayFormatLocaleKey = null;
	private String _descriptionfont = null;
	private String _showdescription = null;
	private String _descriptiondatasource = null;
	private String _popupAttributes=null;
	private String _theme=null;
	private String _editDescription = null;
	private String _useDiv;
	private String _divBorderStyle;
	private String _divScrolling;
	


    /*Claudio Pi - 5/25/04 Added for modal popup windows*/
	private String _useModal=null;

    /*Claudio Pi - 7/1/04 Added popup position*/
    private String _popupLeft = null;
    private String _popupTop = null;
    private String _popupPosition  = null;

    // Juan Manuel Cortez - 01/12/2008 - Added for highlight on focus behavior
    private String _highlightonfocus  = null;
    
	/**
	 * This method creates a HtmlLookUpComponent.
	 */
	public HtmlComponent createComponent() {
		HtmlLookUpComponent comp = new HtmlLookUpComponent(getName(), getLookupurl(), getBrowseimage(), getHelper().getController());
		
		if (_theme != null)
			comp.setTheme(_theme);
		
		if (getSize() != null && !getSize().trim().equals("")) 
			comp.getEditField().setSize(BaseTagHelper.stringToInt(getSize()));
		
		if (getMaxlength() != null && !getMaxlength().trim().equals("")) 
			comp.getEditField().setMaxLength(BaseTagHelper.stringToInt(getMaxlength()));
		

		if (_editDescription != null)
			comp.setEditDescription(BaseTagHelper.stringToBoolean(_editDescription));
			
		/*Claudio Pi - 5/25/04 Added for modal popup windows*/
		if (_usePopup != null)
			comp.setUsePopup(BaseTagHelper.stringToBoolean(_usePopup),BaseTagHelper.stringToBoolean(_useModal,false));

        /*Claudio Pi - 7/1/04 Added popup position*/
        if (_popupPosition != null) {
            comp.setPopupPosition(_popupPosition);
        }

        if (_popupAttributes != null)
        	comp.setPopupAttributes(_popupAttributes);
        
        if (_popupTop!=null || _popupLeft!=null || (_popupPosition != null && _popupPosition.equals(Constants.POPUP_POSITION_CUSTOM))) {

            comp.setPopupPosition(Constants.POPUP_POSITION_CUSTOM);

            if (_popupTop!=null)
                comp.setPopupTop(BaseTagHelper.stringToInt(_popupTop));

            if (_popupLeft!=null) {
                comp.setPopupLeft(BaseTagHelper.stringToInt(_popupLeft));
            }
        }

		if (_popupHeight != null)
			comp.setPopupHeight(BaseTagHelper.stringToInt(_popupHeight));
		
		if (_popupWidth != null)
			comp.setPopupWidth(BaseTagHelper.stringToInt(_popupWidth));
		
		if (_displayFormat != null)
			comp.setDisplayFormat(_displayFormat);
			
		if (_displayFormatLocaleKey != null)
			comp.setDisplayFormatLocaleKey(_displayFormatLocaleKey);
			
		/*Claudio Pi (02-20-2003) Added, the following lines to support the datasource attribute in the LookupTag*/
		if (_fieldDatasource != null) {
			comp.setDataSource(_fieldDatasource);
		}
		
		if (_descriptiondatasource != null)
			comp.setDescriptionDataSource(_descriptiondatasource);
			
		if (_showdescription != null)	
			comp.setShowDescription(BaseTagHelper.stringToBoolean(_showdescription, false));
			
		if (_descriptionfont != null)
			comp.setDescriptionFont(_descriptionfont);
				
		if (_divBorderStyle != null)
			comp.setDivBorderStyle(_divBorderStyle);
		
		if (_divScrolling != null) 
			comp.setDivScrolling(BaseTagHelper.stringToBoolean(_divScrolling));

		if (_useDiv != null)
			comp.setUseDiv(BaseTagHelper.stringToBoolean(_useDiv));

		if(BaseTagHelper.stringToBoolean(_highlightonfocus, true)) {
			comp.getEditField().addOnFocus("if(event.target == null ) {window.event.srcElement.className='selected';} else {event.target.className='selected';}");
			comp.getEditField().addOnLoseFocus("if(event.target == null ) {window.event.srcElement.className='';} else {event.target.className='';}");
		}
		
		return comp;
	}
	/**
	 * Gets the browse image used by Lookup component.
	 * Creation date: (8/23/01 2:45:31 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getBrowseimage() {
		return _fieldBrowseimage;
	}
	/**
	 * Gets the Data source which this field can be bound to
	 * Creation date: (9/5/01 3:53:58 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getDatasource() {
		return _fieldDatasource;
	}
	/**
	 * Gets the Lookup url when Lookup button or image is clicked.
	 * Creation date: (8/23/01 2:45:31 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getLookupurl() {
		return _fieldLookupurl;
	}
	/**
	 * Gets the name of the component.
	 * Creation date: (8/23/01 2:45:31 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getName() {
		return _fieldName;
	}
	/**
	 * Part of the tag library specification. Clears all resources used by the tag.
	 */
	public void release() {
		super.release();
		_usePopup = null;
		_popupWidth = null;
		_popupHeight = null;
		_displayFormat = null;
		_displayFormatLocaleKey = null;
		_fieldName = null;
		_fieldBrowseimage = null;
		_fieldLookupurl = null;
		_fieldDatasource = null;
		_fieldSize = null;
		_fieldMaxLength = null;
	    _descriptionfont = null;
		_showdescription = null;
		_descriptiondatasource = null;
		_popupAttributes=null;
		_popupLeft=null;
		_popupPosition=null;
		_popupTop=null;
		_editDescription=null;
		_useDiv=null;
		_divBorderStyle=null;
		_divScrolling=null;	
		/*Claudio Pi - 5/25/04 Added for modal popup windows*/
		_useModal=null;
		// Juan Manuel Cortez - 01/12/2008 - Added for highlight on focus behavior
		_highlightonfocus = null;
	}
	/**
	 * Sets the image name used by Lookup component.
	 * Creation date: (8/23/01 2:45:31 PM)
	 * @param newBrowseimage java.lang.String
	 */
	public void setBrowseimage(java.lang.String newBrowseimage) {
		_fieldBrowseimage = newBrowseimage;
	}
	/**
	 * Sets the datasource id used by component
	 * Creation date: (9/5/01 3:53:58 PM)
	 * @param newDatasource java.lang.String
	 */
	public void setDatasource(java.lang.String newDatasource) {
		_fieldDatasource = newDatasource;
	}
	/**
	 * Sets the loopup url for the browse image or button when clicked.
	 * Creation date: (8/23/01 2:45:31 PM)
	 * @param newLookupurl java.lang.String
	 */
	public void setLookupurl(java.lang.String newLookupurl) {
		_fieldLookupurl = newLookupurl;
	}
	/**
	 * Sets the name of the component.
	 * Creation date: (8/23/01 2:45:31 PM)
	 * @param newName java.lang.String
	 */
	public void setName(java.lang.String newName) {
		_fieldName = newName;
	}

	public String getSize() {
		return _fieldSize;
	}

	public void setSize(String _fieldSize) {
		this._fieldSize = _fieldSize;
	}

	public String getMaxlength() {
		return _fieldMaxLength;
	}

	public void setMaxlength(String _fieldMaxLength) {
		this._fieldMaxLength = _fieldMaxLength;
	}
	/**
	 * @param string
	 */
	public void setDisplayformat(String string) {
		_displayFormat = string;
	}

	/**
	 * @param string
	 */
	public void setDisplayformatlocalekey(String string) {
		_displayFormatLocaleKey = string;
	}

	/**
	 * @param string
	 */
	public void setPopupheight(String string) {
		_popupHeight = string;
	}

	/**
	 * @param string
	 */
	public void setPopupwidth(String string) {
		_popupWidth = string;
	}

	/**
	 * @param string
	 */
	public void setUsepopup(String string) {
		_usePopup = string;
	}

	/**
	 * @param string
	 */
	public void setDescriptiondatasource(String string) {
		_descriptiondatasource = string;
	}

	/**
	 * @param string
	 */
	public void setDescriptionfont(String string) {
		_descriptionfont = string;
	}

	/**
	 * @param string
	 */
	public void setShowdescription(String string) {
		_showdescription = string;
	}


    /*Claudio Pi - 5/25/04 Added for modal popup windows*/
	public void setUsemodal(String useModal) {
		_useModal= useModal;
	}

    /*Claudio Pi - 7/1/04 Added popup position*/
    public void setPopupleft(String _popupLeft) {
        this._popupLeft = _popupLeft;
    }


    public void setPopuptop(String _popupTop) {
        this._popupTop = _popupTop;
    }

    public void setPopupposition(String _position) {
        this._popupPosition = _position;
    }


	/**
	 * @param popupAttributes The popupAttributes to set.
	 */
	public void setPopupattributes(String popupAttributes) {
		_popupAttributes = popupAttributes;
	}
	/**
	 * @param theme The theme to set.
	 */
	public void setTheme(String theme) {
		_theme = theme;
	}
	/**
	 * @param editDescription The editDescription to set.
	 */
	public void setEditdescription(String editDescription) {
		_editDescription = editDescription;
	}
	/**
	 * @return Returns the divBorderStyle.
	 */
	public String getDivBorderStyle() {
		return _divBorderStyle;
	}
	/**
	 * @param divBorderStyle The divBorderStyle to set.
	 */
	public void setDivborderstyle(String divBorderStyle) {
		_divBorderStyle = divBorderStyle;
	}

	/**
	 * @param divScrolling The divScrolling to set.
	 */
	public void setDivscrolling(String divScrolling) {
		_divScrolling = divScrolling;
	}
	
	/**
	 * @param useDiv The useDiv to set.
	 */
	public void setUsediv(String useDiv) {
		_useDiv = useDiv;
	}
	
	public void setHighlightonfocus(String highlightOnFocus) {
		this._highlightonfocus = highlightOnFocus;
	}
}
