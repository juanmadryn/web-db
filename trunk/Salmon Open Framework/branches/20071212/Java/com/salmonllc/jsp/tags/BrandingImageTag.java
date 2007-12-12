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
package com.salmonllc.jsp.tags;

////////////////////////////////////////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/BrandingImageTag.java $
//$Author: Srufle $ 
//$Revision: 10 $ 
//$Modtime: 4/15/03 1:56a $ 
////////////////////////////////////////////////////////////


import com.salmonllc.html.HtmlComponent;
import com.salmonllc.jsp.JspBrandingImage;
import com.salmonllc.util.Util;

/**
 * This tag is used to create a branding image
 * @see com.salmonllc.jsp.JspBrandingImage
 */
public class BrandingImageTag extends BaseEmptyTag
{
    private String _fTableName;
    private String _fImageColumn;
    private String _fImageAltColumn;
    private String _fImageDir;
/**
 *	This method is used to create the Branding Image, and set the appropriate
 *	parameters or values that are used in constructing this component.
 */

public HtmlComponent createComponent()
{
    BaseTagHelper h = getHelper();
    JspBrandingImage ret = new JspBrandingImage(getName(), h.getController());

    String tableName = getTablename();
    if (tableName != null)
        {
        ret.setTableName(tableName);
    }

    String imageColumn = getImagecolumn();
    if (imageColumn != null)
        {
        ret.setImageColumn(imageColumn);
    }

    String imageAltColumn = getImagealtcolumn();
    if (imageAltColumn != null)
        {
        ret.setImageAltColumn(imageAltColumn);
    }

    String imageDir = getImagedir();
    if (imageDir != null && !Util.isEmpty(imageDir))
        {
        ret.setImageDir(imageDir);
    }

    return ret;
}
/**
 * This method is used to get the name of the DataBase Table column that 
 * stores the alt String for each image from the optional tag "imagealtcolumn".
 * @return java.lang.String
 */
public String getImagealtcolumn() 
{
	return _fImageAltColumn;
}
/**
 * This method is used to get the name of the DataBase Table column that 
 * stores the names of the images from the optional tag "imagecolumn".
 * @return java.lang.String
 */
public String getImagecolumn() 
{
	return _fImageColumn;
}
/**
 * This methos gets the directory in which images are stored on the filesystem
 * from the optional tag "imagedir".
 * @return java.lang.String
 */
public java.lang.String getImagedir() {
	return _fImageDir;
}
/**
 * This method gets the name of the DataBase Table from the optional tag
 * "tablename".
 * @return java.lang.String
 */
public java.lang.String getTablename() 
{
	return _fTableName;
}
/**
 * This method sets the name of the DataBase Table's column used to hold the 
 * alt String for each branding image by getting the value from the optional 
 * tag "imagealtcolumn".
 * @param ImageColumn java.lang.String
 */
public void setImagealtcolumn(String ImageAltColumn)
{
	_fImageAltColumn = ImageAltColumn;
}
/**
 * This method sets the name of the DataBase Table's column used to hold the 
 * names of the branding images by getting the value from the optional 
 * tag "imagecolumn".
 * @param ImageColumn java.lang.String
 */
public void setImagecolumn(String ImageColumn)
{
	_fImageColumn = ImageColumn;
}
/**
 * This method is used to set the directory of the images to the value in the
 * optional tag "imagedir".
 * @param newImageDir java.lang.String
 */
public void setImagedir(java.lang.String newImageDir) {
	_fImageDir = newImageDir;
}
/**
 * This method is used to set the DataBase Table Name from the value in
 * the optional tag "tablename"
 * @param TableName java.lang.String
 */
public void setTablename(java.lang.String TableName) 
{
	_fTableName = TableName;
}
}
