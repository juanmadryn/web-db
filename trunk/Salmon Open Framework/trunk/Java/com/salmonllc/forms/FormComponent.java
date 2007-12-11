package com.salmonllc.forms;

/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/forms/FormComponent.java $
//$Author: Srufle $
//$Revision: 7 $
//$Modtime: 7/31/02 7:13p $
/////////////////////////

import com.salmonllc.html.*;
/**
 * This type is a type safe container for DetailForm Components.
 */
public class FormComponent
{
	private String _captionString;
	private HtmlText _captionTextComp;
	private HtmlComponent _formComponent;
	private int _flags;
/**
 * Constructor
 * @param caption java.lang.String
 * @param component com.salmonllc.html.HtmlComponent
 * @param flags int
 */
public FormComponent(HtmlText captionCompIn, HtmlComponent component, int flags)
{
	if (captionCompIn != null)
	{
		_captionString = captionCompIn.getText();
	}
	else
	{
		_captionString = "";
	}
	_captionTextComp = captionCompIn;
	_formComponent = component;
	_flags = flags;
}
      /**
       * Get the Caption as a String
       */

      public String getCaptionString()
      {
      	return _captionString;
      }
      /**
       * Get the Caption as a HtmlText Component.
       */
      public HtmlText getCaptionTextComp()
      {
      	return _captionTextComp;
      }
      /**
       * Get the flags value.
       */
      public int getFlags()
      {
      	return _flags;
      }
      /**
       * Get the detail HtmlComponent.
       */
      public HtmlComponent getFormComponent()
      {
      	return _formComponent;
      }
}
