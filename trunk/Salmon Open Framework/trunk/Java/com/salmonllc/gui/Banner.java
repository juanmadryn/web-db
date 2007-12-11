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
package com.salmonllc.gui;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/gui/Banner.java $
//$Author: Srufle $ 
//$Revision: 7 $ 
//$Modtime: 4/15/03 2:24p $ 
/////////////////////////



import com.salmonllc.html.*;
import com.salmonllc.util.TwoObjectContainer;
import java.util.Vector;

/**
 * Create banner of the following form <br> <br> &lt;table border=&quot;0&quot; cellspacing=&quot;0&quot; cellpadding=&quot;0&quot; width=&quot;100%&quot;&gt; &lt;tr bgcolor=&quot;330000&quot;&gt;<br> &lt;td height=&quot;52&quot; rowspan=&quot;5&quot; width=&quot;800&quot;&gt;&lt;img src=&quot;../../Objectstore/app_name/top_banner.gif&quot; width=&quot;800&quot; height=&quot;52&quot;&gt;&lt;/td&gt;<br> &lt;td height=&quot;2&quot; width=&quot;100%&quot;&gt;&lt;img src=&quot;../../Objectstore/app_name/spacer.gif&quot; width=&quot;12&quot; height=&quot;2&quot;&gt;&lt;/td&gt;<br> &lt;/tr&gt;<br> &lt;tr bgcolor=&quot;660000&quot;&gt;<br> &lt;td height=&quot;21&quot; width=&quot;100%&quot;&gt;&lt;img src=&quot;../../Objectstore/app_name/spacer.gif&quot; width=&quot;12&quot; height=&quot;21&quot;&gt;&lt;/td&gt;<br> &lt;/tr&gt;<br> &lt;tr bgcolor=&quot;330000&quot;&gt;<br> &lt;td width=&quot;100%&quot; height=&quot;1&quot;&gt;&lt;img src=&quot;../../Objectstore/app_name/spacer.gif&quot; width=&quot;12&quot; height=&quot;1&quot;&gt;&lt;/td&gt;<br> &lt;/tr&gt;<br> &lt;tr bgcolor=&quot;CC3333&quot;&gt;<br> &lt;td height=&quot;1&quot; width=&quot;100%&quot;&gt;&lt;img src=&quot;../../Objectstore/app_name/spacer.gif&quot; width=&quot;12&quot; height=&quot;1&quot;&gt;&lt;/td&gt;<br> &lt;/tr&gt;<br> &lt;tr bgcolor=&quot;990000&quot;&gt;<br> &lt;td height=&quot;27&quot; width=&quot;100%&quot;&gt;&lt;img src=&quot;../../Objectstore/app_name/spacer.gif&quot; width=&quot;12&quot; height=&quot;27&quot;&gt;&lt;/td&gt;<br> &lt;/tr&gt;<br> &lt;/table&gt;<br>
 */

public class Banner extends HtmlContainer
{
	private HtmlTable _imageTable;
	//
	private HtmlImage _bannerImage;
	private String _bannerImageURL;
	private int _bannerWidth;
	private int _bannerHeight;
	//
	private String _spacerImageURL;
	//
	private Vector _rows = new Vector();
/**
 * Banner constructor
 * @param name java.lang.String
 * @param p com.salmonllc.html.HtmlPage
 * @param spacerImageURL java.lang.String URL of image to use as a spacer.
 */
public Banner(String name, HtmlPage p, String spacerImageURL)
{
	super(name, p);
	_spacerImageURL = spacerImageURL;
	//
	_imageTable = new HtmlTable("_imageTable", p);
	_imageTable.setCellPadding(0);
	_imageTable.setCellSpacing(0);
	_imageTable.setSizeOption(HtmlTable.SIZE_PERCENT);
	_imageTable.setWidth(100);
	_imageTable.setAllowNullCells(false);
	add(_imageTable);
}
/**
 * This methos sets the color and height of the right side rows.
 * @param color java.lang.String
 * @param height int
 */
public void addRowInfo(String color, int height)
{
	TwoObjectContainer cont = new TwoObjectContainer(color, new Integer(height));
	_rows.addElement(cont);
}
/**
 * Builds the banner according to the information provided prevoiusly. 
 * This should be the last method called.
 */
public void build()
{
	HtmlTableRowProperties tempRowProp = null;
	HtmlTableCellProperties tempCellProp = null;
	HtmlImage spacerImage = null;
	// row 0 has to be done manually
	HtmlImage bannerImage = new HtmlImage(_bannerImageURL, getPage());
	bannerImage.setWidth(_bannerWidth);
	bannerImage.setHeight(_bannerHeight);
	_bannerImage = bannerImage;
	//
	tempCellProp = new HtmlTableCellProperties();
	tempCellProp.setRowSpan(_rows.size());
	tempCellProp.setCellWidth(_bannerWidth, HtmlTable.SIZE_PIXELS);
	tempCellProp.setCellHeight(_bannerHeight, HtmlTable.SIZE_PIXELS);
	//
	tempRowProp = new HtmlTableRowProperties();
	tempRowProp.setBackgroundColor(getRowColor(0));
	_imageTable.setRowProperty(0, tempRowProp);
	_imageTable.setComponentAt(0, 0, bannerImage, tempCellProp);
	spacerImage = new HtmlImage(_spacerImageURL, getPage());
	spacerImage.setWidth(10);
	spacerImage.setHeight(getRowHeight(0));
	//
	tempCellProp = new HtmlTableCellProperties();
	tempCellProp.setCellWidth(100, HtmlTable.SIZE_PERCENT);
	tempCellProp.setCellHeight(getRowHeight(0), HtmlTable.SIZE_PIXELS);
	//
	_imageTable.setComponentAt(0, 1, spacerImage, tempCellProp);

	//
	int imageCellHeight = -1;
	String rowColor = null;
	// rows 1 to max transitions can be done in the loop
	// for the number of transitions
	for (int i = 1; i < _rows.size(); i++)
	{
		imageCellHeight = getRowHeight(i);
		rowColor = getRowColor(i);
		tempRowProp = new HtmlTableRowProperties();
		tempRowProp.setBackgroundColor(rowColor);
		_imageTable.setRowProperty(i, tempRowProp);
		//
		spacerImage = new HtmlImage(_spacerImageURL, getPage());
		spacerImage.setWidth(10);
		spacerImage.setHeight(imageCellHeight);
		//
		tempCellProp = new HtmlTableCellProperties();
		tempCellProp.setCellWidth(100, HtmlTable.SIZE_PERCENT);
		tempCellProp.setCellHeight(imageCellHeight, HtmlTable.SIZE_PIXELS);
		//
		_imageTable.setComponentAt(i, 1, spacerImage, tempCellProp);
	}
}
/**
 * This methos sets the color and height of the right side rows.
 * @param color java.lang.String
 * @param height int
 */
public HtmlImage getBannerImage()
{
	return _bannerImage;
}
/**
 * This methos sets the color and height of the right side rows.
 * @param color java.lang.String
 * @param height int
 */
public String getRowColor(int index)
{
	if (index >= _rows.size())
	{
		return null;
	}
	TwoObjectContainer cont = (TwoObjectContainer) _rows.elementAt(index);
	return (String) cont.getObject1();
}
/**
 * This methos sets the color and height of the right side rows.
 * @param color java.lang.String
 * @param height int
 */
public int getRowHeight(int index)
{
	if (index >= _rows.size())
	{
		return -1;
	}
	TwoObjectContainer cont = (TwoObjectContainer) _rows.elementAt(index);
	return ((Integer) cont.getObject2()).intValue();
}
/**
 * Adds a new html component to the container
 * @param comp com.salmonllc.html.HtmlComponent
 */
public void setBannerImage(String bannerImageURL, int width, int height)
{
	_bannerImageURL = bannerImageURL;
	_bannerWidth = width;
	_bannerHeight = height;
}
/**
 * This methos sets the color and height of the right side rows.
 * @param color java.lang.String
 * @param height int
 */
public void setRowInfo(String color, int height, int index)
{
	if (index >= _rows.size())
	{
		return;
	}
	TwoObjectContainer cont = new TwoObjectContainer(color, new Integer(height));
	_rows.setElementAt(cont, index);
}
}
