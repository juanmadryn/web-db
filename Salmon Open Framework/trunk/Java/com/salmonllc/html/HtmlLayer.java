//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2002, Salmon LLC
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License version 2
// as published by the Free Software Foundation;
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// 
// For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.html;

/**
 * Implements a div tag or layer tag in Html.
 */
public class HtmlLayer extends HtmlContainer {
    private int _containerWidth = -1;
    private int _containerHeight = -1;
    private int _containerTop = -9999;
    private int _containerLeft = -9999;
    private int _browserType;
    private int _browserVersion;
    private HtmlStyle _style;
    private boolean _useLayerTag = false ;
    private boolean _enableClip = false ;
    private String _position = "absolute";
    private boolean _useBuffer = false;


    /**
     * HtmlLayer constructor.
     * @param name java.lang.String
     * @param p com.salmonllc.html.HtmlPage
     */
    public HtmlLayer(String name, HtmlPage p) {
        super(name, p);
        _browserType = p.getBrowserType();
        _browserVersion = p.getBrowserVersion();

    }

    /**
     * HtmlLayer constructor.
     * @param name java.lang.String
     * @param style com.salmonllc.html.HtmlStyle
     * @param p com.salmonllc.html.HtmlPage
     */
    public HtmlLayer(String name, HtmlStyle style, HtmlPage p) {
        super(style != null?style.getStyleName():name, p);
        _style = style;
        _browserType = p.getBrowserType();
        _browserVersion = p.getBrowserVersion();
        String sStyle = _style.getStyle();
        int topIndex = sStyle.toUpperCase().indexOf("TOP");
        int leftIndex = sStyle.toUpperCase().indexOf("LEFT");
        int widthIndex = sStyle.toUpperCase().indexOf("WIDTH");
        int heightIndex = sStyle.toUpperCase().indexOf("HEIGHT");
        int positionIndex = sStyle.toUpperCase().indexOf("POSITION");
        if (topIndex >= 0) {
            String sTop = sStyle.substring(topIndex);
            int colonIndex = sTop.indexOf(":");
            int semicolonIndex = sTop.indexOf(";");
            String sValue = sTop.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerTop = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (leftIndex >= 0) {
            String sLeft = sStyle.substring(leftIndex);
            int colonIndex = sLeft.indexOf(":");
            int semicolonIndex = sLeft.indexOf(";");
            String sValue = sLeft.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerLeft = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (widthIndex >= 0) {
            String sWidth = sStyle.substring(widthIndex);
            int colonIndex = sWidth.indexOf(":");
            int semicolonIndex = sWidth.indexOf(";");
            String sValue = sWidth.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerWidth = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (heightIndex >= 0) {
            String sHeight = sStyle.substring(heightIndex);
            int colonIndex = sHeight.indexOf(":");
            int semicolonIndex = sHeight.indexOf(";");
            String sValue = sHeight.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerHeight = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (positionIndex >= 0) {
            String sPosition = sStyle.substring(positionIndex);
            int colonIndex = sPosition.indexOf(":");
            int semicolonIndex = sPosition.indexOf(";");
            _position = sPosition.substring(colonIndex + 1, semicolonIndex).trim();
        }


    }

    public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {
        if (_browserType != HtmlPageBase.BROWSER_MICROSOFT && _browserType != HtmlPageBase.BROWSER_NETSCAPE)
            return;
        if (_browserVersion < 4)
            return;

        if (!getVisible())
            return;
        if (_center)
            p.print("<CENTER>");

        String layerHeading = "";
        String attributes = "";
        String sName = getFullName();
        if (_browserType == HtmlPage.BROWSER_MICROSOFT && _useBuffer) {
            p.println("<IFRAME STYLE=\"display:none\" NAME=\"" + sName + "BUFFER\"></IFRAME>");
        }
        boolean styleProvided = false;
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            styleProvided = true;
        }
        layerHeading = "<DIV id=\"" + sName + "\"";
        if (!styleProvided)
            attributes = " STYLE=\"position:" + _position + ";";

        if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE) {
            layerHeading = "<LAYER NAME=\"" + sName + "\"";
            attributes = "";
        }
        if (_containerWidth > -1) {
            if (!_useLayerTag && !styleProvided)
                attributes += "width:" + _containerWidth + "px;";
            else if (_useLayerTag)
                attributes += " WIDTH=\"" + _containerWidth + "\"";
//		if (_sizeOption == SIZE_PERCENT)
//		{
//			tableHeading += "%";
//		}
//		tableHeading += "\"";
        }
        if (_containerHeight > -1) {
            if (!_useLayerTag && !styleProvided)
                attributes += "height:" + _containerHeight + "px;";
            else if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE)
                attributes += " HEIGHT=\"" + _containerHeight + "\"";
        }
        if (_containerTop > -9999) {
            if (!_useLayerTag && !styleProvided)
                attributes += "top:" + _containerTop + "px;";
            else if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE)
                attributes += " TOP=\"" + _containerTop + "\"";
        }
        if (_containerLeft > -9999) {
            if (!_useLayerTag && !styleProvided)
                attributes += "left:" + _containerLeft + "px;";
            else if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE)
                attributes += " LEFT=\"" + _containerLeft + "\"";
        }
        if (_enableClip && _containerHeight > -1 && _containerWidth > -1) {
            if (!_useLayerTag && !styleProvided)
                attributes += "clip:rect(0px," + _containerWidth + "px," + _containerHeight + "px,0px);";
        }
        if (!_useLayerTag && !styleProvided)
            attributes += "\"";
        layerHeading += attributes;

/*	if (_bgColor != null)
	{
		if (!_bgColor.equals(""))
		{
			tableHeading += " BGCOLOR=\"" + _bgColor + "\"";
		}
	}*/
/*	if (_align != null)
	{
		if (_align.equals(ALIGN_LEFT) || _align.equals(ALIGN_RIGHT) || _align.equals(ALIGN_CENTER))
			tableHeading += " align=\"" + _align + "\"";
	}*/
        layerHeading += ">";
        p.println(layerHeading);
        super.generateHTML(p, rowNo);

        if (_browserType == HtmlPageBase.BROWSER_MICROSOFT || !_useLayerTag)
            p.print("</DIV>");
        else
            p.print("</LAYER>");

/*	if (_containerHeight>-1 || _containerWidth>-1 || _containerLeft>-9999 || _containerTop>-1) {
		p.print("<SCRIPT LANGUAGE=\"JavaScript1.2\">");
		p.print("function "+getName()+"Draw() {");
		if (_containerHeight>-1)
		  p.print("document."+getName()+".height="+_containerHeight+";");
		if (_containerWidth>-1)
		  p.print("document."+getName()+".width="+_containerWidth+";");
		if (_containerTop>-9999)
		  p.print("document."+getName()+".top="+_containerTop+";");
		if (_containerLeft>-9999)
		  p.print("document."+getName()+".left="+_containerLeft+";");
		p.print("};");
		//p.print("setTimeout(\""+getName()+"Draw();\",100);");
		p.print("</SCRIPT>");
	  }*/
        if (_center)
            p.print("</CENTER>");
        p.println("");
    }

    public void generateHTML(java.io.PrintWriter p, int rowStart, int rowEnd) throws Exception {
        if (_browserType != HtmlPageBase.BROWSER_MICROSOFT && _browserType != HtmlPageBase.BROWSER_NETSCAPE)
            return;
        if (_browserVersion < 4)
            return;

        if (!getVisible())
            return;
        if (_center)
            p.print("<CENTER>");

        String layerHeading = "";
        String attributes = "";
        String sName = getFullName();
        if (_browserType == HtmlPage.BROWSER_MICROSOFT && _useBuffer) {
            p.println("<IFRAME STYLE=\"display:none\" NAME=\"" + sName + "BUFFER\"></IFRAME>");
        }
        boolean styleProvided = false;
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            styleProvided = true;
        }
        layerHeading = "<DIV id=\"" + sName + "\"";
        if (!styleProvided)
            attributes = " STYLE=\"position:" + _position + ";";

        if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE) {
            layerHeading = "<LAYER NAME=\"" + sName + "\"";
            attributes = "";
        }
        if (_containerWidth > -1) {
            if (!_useLayerTag && !styleProvided)
                attributes += "width:" + _containerWidth + "px;";
            else if (_useLayerTag)
                attributes += " WIDTH=\"" + _containerWidth + "\"";
//		if (_sizeOption == SIZE_PERCENT)
//		{
//			tableHeading += "%";
//		}
//		tableHeading += "\"";
        }
        if (_containerHeight > -1) {
            if (!_useLayerTag && !styleProvided)
                attributes += "height:" + _containerHeight + "px;";
            else if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE)
                attributes += " HEIGHT=\"" + _containerHeight + "\"";
        }
        if (_containerTop > -9999) {
            if (!_useLayerTag && !styleProvided)
                attributes += "top:" + _containerTop + "px;";
            else if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE)
                attributes += " TOP=\"" + _containerTop + "\"";
        }
        if (_containerLeft > -9999) {
            if (!_useLayerTag && !styleProvided)
                attributes += "left:" + _containerLeft + "px;";
            else if (_useLayerTag && _browserType == HtmlPage.BROWSER_NETSCAPE)
                attributes += " LEFT=\"" + _containerLeft + "\"";
        }
        if (_enableClip && _containerHeight > -1 && _containerWidth > -1) {
            if (!_useLayerTag && !styleProvided)
                attributes += "clip:rect(0px," + _containerWidth + "px," + _containerHeight + "px,0px);";
        }
        if (!_useLayerTag && !styleProvided)
            attributes += "\"";
        layerHeading += attributes;
/*	if (_style != null && _style.getStyleName()!=null && !_style.getStyleName().trim().equals(""))
		layerHeading += " CLASS=\"" + _style.getStyleName() + "\"";*/

/*	if (_bgColor != null)
	{
		if (!_bgColor.equals(""))
		{
			tableHeading += " BGCOLOR=\"" + _bgColor + "\"";
		}
	}*/
/*	if (_align != null)
	{
		if (_align.equals(ALIGN_LEFT) || _align.equals(ALIGN_RIGHT) || _align.equals(ALIGN_CENTER))
			tableHeading += " align=\"" + _align + "\"";
	}*/
        layerHeading += ">";
        p.println(layerHeading);
        super.generateHTML(p, rowStart, rowEnd);

        if (_browserType == HtmlPageBase.BROWSER_MICROSOFT || !_useLayerTag)
            p.print("</DIV>");
        else
            p.print("</LAYER>");
/*	if (_containerHeight>-1 || _containerWidth>-1 || _containerLeft>-9999 || _containerTop>-1) {
		p.print("<SCRIPT LANGUAGE=\"JavaScript1.2\">");
		p.print("function "+getName()+"Draw() {");
		if (_containerHeight>-1)
		  p.print("document."+getName()+".height="+_containerHeight+";");
		if (_containerWidth>-1)
		  p.print("document."+getName()+".width="+_containerWidth+";");
		if (_containerTop>-9999)
		  p.print("document."+getName()+".top="+_containerTop+";");
		if (_containerLeft>-9999)
		  p.print("document."+getName()+".left="+_containerLeft+";");
		p.print("};");
		//p.print("setTimeout(\""+getName()+"Draw();\",100);");
		p.print("</SCRIPT>");
	  }*/

        if (_center)
            p.print("</CENTER>");
        p.println("");
    }

    /**
     * Returns the I-Frame Buffer name. Only valid if buffer being used.
     * @return java.lang.String The name of the I-Frame buffer.
     */
    public String getBufferName() {
        return getFullName() + "BUFFER";
    }

    /**
     * Returns whether a clip:rect description is to be used to describe the div/layer.
     * @return boolean Indicates if clip:rect description is to be used in Html Generation.
     */
    public boolean getEnableClip() {
        return _enableClip;
    }

    /**
     * Returns the full name of the component for scripting purposes.
     * @return java.lang.String The fullname of the component
     */
    public String getFullName() {
        if (_style != null)
            return _style.getStyleName();
        else if (_useLayerTag || _browserType == HtmlPage.BROWSER_NETSCAPE)
            return super.getName();
        else
            return super.getFullName();
    }

    /**
     * This method gets the minimum height of the table in pixels.
     */
    public int getHeight() {
        return _containerHeight;
    }

    /**
     * This method gets the left position of the layer in pixels.
     * @return int The number of pixels from the Left of the window the Layer is.
     */
    public int getLeft() {
        return _containerLeft;
    }

    /**
     * This method gets a position attribute of the layer.
     * @return java.lang.String Position Attribute of the layer.
     */
    public String getPosition() {
        return _position;
    }

    /**
     * This method gets the top position of the layer in pixels.
     * @return int The number of pixels from the Top of the window the Layer is.
     */
    public int getTop() {
        return _containerTop;
    }

    /**
     * This method returns whether a Buffer is to be used.
     * @return boolean Indicates if a buffer is being used by the layer.
     */
    public boolean getUseBuffer() {
        return _useBuffer;
    }

    /**
     * This method returns the width of the table.
     */
    public int getWidth() {
        return _containerWidth;
    }

    /**
     * Specifies whether to use the clip:rect syntax in the Html Generation.
     * @param enableClip boolean Indicates whether to use the clip:rect syntax or not.
     */
    public void setEnableClip(boolean enableClip) {
        _enableClip = enableClip;
    }

    /**
     * This method sets the minimum height of the table in pixels.
     */
    public void setHeight(int height) {
        _containerHeight = height;
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            String sStyle = _style.getStyle();
            int heightIndex = sStyle.toUpperCase().indexOf("HEIGHT");
            if (heightIndex >= 0) {
                String sHeight = sStyle.substring(heightIndex);
                int colonIndex = heightIndex + sHeight.indexOf(":");
                int semicolonIndex = heightIndex + sHeight.indexOf(";");
                sStyle = sStyle.substring(0, colonIndex + 1) + height + sStyle.substring(semicolonIndex);
                _style.setStyle(sStyle);
            } else {
                if (sStyle.endsWith(";"))
                    sStyle = sStyle + "height:" + height + ";";
                else
                    sStyle = sStyle + ";height:" + height + ";";
                _style.setStyle(sStyle);
            }
        }
    }

    /**
     * Sets the Left position of the layer in pixels.
     * @param left int The left position in pixels.
     */
    public void setLeft(int left) {
        _containerLeft = left;
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            String sStyle = _style.getStyle();
            int leftIndex = sStyle.toUpperCase().indexOf("LEFT");
            if (leftIndex >= 0) {
                String sLeft = sStyle.substring(leftIndex);
                int colonIndex = leftIndex + sLeft.indexOf(":");
                int semicolonIndex = leftIndex + sLeft.indexOf(";");
                sStyle = sStyle.substring(0, colonIndex + 1) + left + sStyle.substring(semicolonIndex);
                _style.setStyle(sStyle);
            } else {
                if (sStyle.endsWith(";"))
                    sStyle = sStyle + "left:" + left + ";";
                else
                    sStyle = sStyle + ";left:" + left + ";";
                _style.setStyle(sStyle);
            }
        }
    }

    /**
     * Sets the position attribute of the layer.
     * @param position java.lang.String The position of the layer.
     */
    public void setPosition(String position) {
        _position = position;
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            String sStyle = _style.getStyle();
            int positionIndex = sStyle.toUpperCase().indexOf("POSITION");
            if (positionIndex >= 0) {
                String sPosition = sStyle.substring(positionIndex);
                int colonIndex = positionIndex + sPosition.indexOf(":");
                int semicolonIndex = positionIndex + sPosition.indexOf(";");
                sStyle = sStyle.substring(0, colonIndex + 1) + position + sStyle.substring(semicolonIndex);
                _style.setStyle(sStyle);
            } else {
                if (sStyle.endsWith(";"))
                    sStyle = sStyle + "position:" + position + ";";
                else
                    sStyle = sStyle + ";position:" + position + ";";
                _style.setStyle(sStyle);
            }
        }
    }

    /**
     * Specifies the layer should used the passed style to indicate the layer's properties.
     * @param style com.salmonllc.html.HtmlStyle
     */
    public void setStyle(HtmlStyle style) {
        _style = style;
        if (_style == null)
            return;
        setName(style.getStyleName());
        String sStyle = _style.getStyle();
        int topIndex = sStyle.toUpperCase().indexOf("TOP");
        int leftIndex = sStyle.toUpperCase().indexOf("LEFT");
        int widthIndex = sStyle.toUpperCase().indexOf("WIDTH");
        int heightIndex = sStyle.toUpperCase().indexOf("HEIGHT");
        int positionIndex = sStyle.toUpperCase().indexOf("POSITION");
        if (topIndex >= 0) {
            String sTop = sStyle.substring(topIndex);
            int colonIndex = sTop.indexOf(":");
            int semicolonIndex = sTop.indexOf(";");
            String sValue = sTop.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerTop = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (leftIndex >= 0) {
            String sLeft = sStyle.substring(leftIndex);
            int colonIndex = sLeft.indexOf(":");
            int semicolonIndex = sLeft.indexOf(";");
            String sValue = sLeft.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerLeft = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (widthIndex >= 0) {
            String sWidth = sStyle.substring(widthIndex);
            int colonIndex = sWidth.indexOf(":");
            int semicolonIndex = sWidth.indexOf(";");
            String sValue = sWidth.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerWidth = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (heightIndex >= 0) {
            String sHeight = sStyle.substring(heightIndex);
            int colonIndex = sHeight.indexOf(":");
            int semicolonIndex = sHeight.indexOf(";");
            String sValue = sHeight.substring(colonIndex + 1, semicolonIndex).trim();
            try {
                _containerHeight = Integer.parseInt(sValue);
            } catch (Exception e) {
                ;
            }
        }
        if (positionIndex >= 0) {
            String sPosition = sStyle.substring(positionIndex);
            int colonIndex = sPosition.indexOf(":");
            int semicolonIndex = sPosition.indexOf(";");
            _position = sPosition.substring(colonIndex + 1, semicolonIndex).trim();
        }
    }

    /**
     * Sets the Top position of the layer in pixels.
     * @param left int The top position in pixels.
     */
    public void setTop(int top) {
        _containerTop = top;
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            String sStyle = _style.getStyle();
            int topIndex = sStyle.toUpperCase().indexOf("TOP");
            if (topIndex >= 0) {
                String sTop = sStyle.substring(topIndex);
                int colonIndex = topIndex + sTop.indexOf(":");
                int semicolonIndex = topIndex + sTop.indexOf(";");
                sStyle = sStyle.substring(0, colonIndex + 1) + top + sStyle.substring(semicolonIndex);
                _style.setStyle(sStyle);
            } else {
                if (sStyle.endsWith(";"))
                    sStyle = sStyle + "top:" + top + ";";
                else
                    sStyle = sStyle + ";top:" + top + ";";
                _style.setStyle(sStyle);
            }
        }
    }

    /**
     * Specifies whether to use a buffer with the layer.
     * @param useBuffer boolean Indicates whether to use a buffer or not.
     */
    public void setUseBuffer(boolean useBuffer) {
        _useBuffer = useBuffer;
    }

    /**
     * Specifies whether to use the layer tag instead of div.
     * @param useLayerTag boolean Indicates whether to use a the layer tag or not.
     */
    public void setUseLayerTag(boolean useLayerTag) {
        _useLayerTag = useLayerTag;

    }

    /**
     * This method sets the minimum width of the table in either pixels or percent depending on size option.
     */
    public void setWidth(int width) {
        _containerWidth = width;
        if (_style != null && _style.getStyleName() != null && !_style.getStyleName().trim().equals("")) {
            String sStyle = _style.getStyle();
            int widthIndex = sStyle.toUpperCase().indexOf("WIDTH");
            if (widthIndex >= 0) {
                String sWidth = sStyle.substring(widthIndex);
                int colonIndex = widthIndex + sWidth.indexOf(":");
                int semicolonIndex = widthIndex + sWidth.indexOf(";");
                sStyle = sStyle.substring(0, colonIndex + 1) + width + sStyle.substring(semicolonIndex);
                _style.setStyle(sStyle);
            } else {
                if (sStyle.endsWith(";"))
                    sStyle = sStyle + "width:" + width + ";";
                else
                    sStyle = sStyle + ";width:" + width + ";";
                _style.setStyle(sStyle);
            }
        }
    }
}
