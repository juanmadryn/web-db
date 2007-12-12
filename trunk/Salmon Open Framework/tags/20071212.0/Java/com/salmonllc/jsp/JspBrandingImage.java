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
package com.salmonllc.jsp;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/JspBrandingImage.java $
//$Author: Srufle $
//$Revision: 14 $
//$Modtime: 4/15/03 2:24p $
/////////////////////////

import java.util.Vector;

import com.salmonllc.html.HtmlImage;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.sql.DataStore;

/**
 *	This class is used to display a differet image from a series of images on every request to 
 *  the page.  It cycles through the different images to display a new image
 *	wherever this tag is placed on the page, so as to give a more dynamic look
 *	and feel to the website.
 */

public class JspBrandingImage extends HtmlImage implements PageListener
{
    private String _tableName = "BRAND";
    private String _imageColumn = "IMAGE";
    private String _imageAltColumn = "IMAGE_ALT";
    private String _fImageDir = "images";

 	/*
 	 * This inner class is used to hold and manipulate the list of branding
 	 * images as stored in the database. 
	 */
	private class BrandImageList
    {
        private Vector _images = new Vector();
        private int _nextImage = 0;

        /**
         * BrandImageList constructor.  This inner class is meant to be a
         * container of all of the branding images stored in the database.
         * This class acts on a Vector to store the list of images.
         */
        public BrandImageList()
        {
            super();
        }
        /**
         * This method adds a String array containing the added image's url
         * and alt tag
         * @param url java.lang.String
         * @param alt java.lang.String
         */
        public void addImage(String url, String alt)
        {
            String s[] = new String[2];
            s[0] = url;
            s[1] = alt;
            _images.addElement(s);
        }
        /**
         * This method returns the current image's alt value
         * @return java.lang.String
         */
        public String getCurrentAlt()
        {
            if (_images.size() == 0)
                return null;

            String s[] = (String[]) _images.elementAt(_nextImage);
            return s[1];
        }
        /**
         * This method returns the current image's url.
         * @return java.lang.String
         */
        public String getCurrentURL()
        {
            if (_images.size() == 0)
                return null;

            String s[] = (String[]) _images.elementAt(_nextImage);
            return s[0];
        }

        /**
         *	This method increases the counter for the next image by 1,
         * 	and sets the Vector to be on the first element if you want to
         *	move up from the last element in the Vector.
         * 	@return boolean
         */
        public boolean nextImage()
        {
            if (_images.size() == 0)
                return false;

            if (_nextImage >= (_images.size() - 1))
                _nextImage = 0;
            else
                _nextImage++;

            return true;
        }
    }

/**
 * BrandingImage constructor comment.
 * @param name java.lang.String
 * @param p com.salmonllc.jsp.JspController
 */
public JspBrandingImage(String name, JspController p) {
	super("",name, p);
	p.addPageListener(this);
}
/**
 * This method is used to get the name of the DataBase Table's column that holds
 * the value of the image's alt tag.
 * @return java.lang.String
 */
public String getImageAltColumn()
{
	return _imageAltColumn;
}
/**
 * This method is used to get the name of the DataBase Table's column that holds
 * the name of the images.
 * @return java.lang.String
 */
public String getImageColumn()
{
	return _imageColumn;
}
/**
 * This method is used to get the directory in which images are stored.
 * @return java.lang.String
 */
public java.lang.String getImageDir() {
	return _fImageDir;
}
/**
 * This method is used to get the name of the DataBase Table.
 * @return java.lang.String
 */
public java.lang.String getTableName()
{
	return _tableName;
}
/**
 * This method assumes that you have a Database Table named "brand", with the
 * columns "image" and "image_alt".  It also assumes that all images are stored
 * in the folder "JSP/AppName/images".  You can also change the Table name from
 * using the "TableName" attribute in the Brand tag.
 * Creation date: (2/6/02 10:12:03 AM)
 */
public BrandImageList loadImages(JspController cont) throws Exception
{
    BrandImageList l = new BrandImageList();

    DataStore ds = new DataStore(cont.getApplicationName());
    ds.addColumn(getTableName(), getImageColumn(), DataStore.DATATYPE_STRING);
    ds.addColumn(getTableName(), getImageAltColumn(), DataStore.DATATYPE_STRING);
    ds.retrieve();

    for (int i = 0; i < ds.getRowCount(); i++)
        l.addImage(getImageDir() + "/" + ds.getString(i, getTableName() + "." + getImageColumn()), ds.getString(i, getTableName() + "." + getImageAltColumn()));

    return l;
}
/**
 * 	This method/event will get fired each time a page is requested by the browser.
 *	@param p PageEvent
 *	@throws Exception
 */
public void pageRequested(PageEvent p) throws Exception
{
 	JspController cont = (JspController) p.getPage();
 	javax.servlet.http.HttpSession sess = cont.getSession();
	BrandImageList l = (BrandImageList) sess.getAttribute("BrandingImageList");
	if (l == null)
	{
		l = loadImages(cont);
		sess.setAttribute("BrandingImageList",l);
	}
	else
		l.nextImage();

 	if (l.getCurrentURL() == null)
 		setVisible(false);
 	else
 	{
 		setVisible(true);
 		setSource(l.getCurrentURL());
 		setAlt(l.getCurrentAlt());
 	}

}
/**
 * This event will get fired each time a page is requested by the browser.
 */
public void pageRequestEnd(com.salmonllc.html.events.PageEvent p) throws java.lang.Exception {}
/**
 * This method occurs eaxh time a page is sumbitted.
 */
public void pageSubmitEnd(com.salmonllc.html.events.PageEvent p) {}
/**
 * This method occurs eaxh time a page is sumbitted.
 */
public void pageSubmitted(com.salmonllc.html.events.PageEvent p) {}
/**
 * This method sets the name of the column in the DataBase that corresponds to
 * image alts, as this column holds the alt value for the images.
 * @param imageAltColumn java.lang.String
 */
public void setImageAltColumn(String imageAltColumn)
{
	_imageAltColumn = imageAltColumn;
}
/**
 * This method sets the name of the column in the DataBase that correspond to
 * images, as this column holds the name of the images.
 * @param imageColumn java.lang.String
 */
public void setImageColumn(String imageColumn)
{
	_imageColumn = imageColumn;
}
/**
 * This method sets the directory that the branding images reside in.
 * @param newImageDir java.lang.String
 */
public void setImageDir(java.lang.String newImageDir) {
	_fImageDir = newImageDir;
}
/**
 * This method sets the name of the DataBase Table.
 * @param TableName java.lang.String
 */
public void setTableName(java.lang.String TableName)
{
	_tableName = TableName;
}
}
