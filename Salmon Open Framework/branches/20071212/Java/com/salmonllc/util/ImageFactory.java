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
package com.salmonllc.util;

import java.awt.*;
import java.util.Vector;
/**
 * This utility class is used to create images that can be streamed back to the browser via the GifEncoder class.
 */
public class ImageFactory {
	static boolean _showFrame = false;
	static Frame _frame;
 	
	static {
		_frame=new Frame();
		_frame.addNotify();
	    _frame.setBounds(0,0,200,200);
	    _frame.setVisible(_showFrame);
	}
/**
 * Create a new image factory
 */
public ImageFactory() {
	super();
	//_frame = new Frame();
	//_frame.addNotify();
	//_frame.setBounds(0,0,200,200);
	//_frame.setVisible(true);
}
/**
 * This method creates an oval shaped button.
 * @return java.awt.Image The button image
 * @param width int The width of the button
 * @param height int The height of the button
 * @param f java.awt.Font The font to use to draw the text
 * @param text java.lang.String The font to use to draw the text
 * @param textColor java.awt.Color The color to draw the text in
 * @param backColor java.awt.Color The color for the button background
 * @param topColor java.awt.Color The color for the top and left border
 * @param bottomColor java.awt.Color The color for the bottom and right border
 * @param transparentColor java.awt.Color The color for the areas on the button that need to be transparent
 * @param clicked boolean True if you want the button to appear to be clicked
 * @param enabled boolean True if you want the button to appear to be enabled
 */
public synchronized Image createOvalButton (int width, int height, Font f, String text, Color textColor, Color backColor, Color topColor, Color bottomColor, Color transparentColor, boolean clicked, boolean enabled)  {
  Image img = _frame.createImage(width,height);
  Graphics g = img.getGraphics();
  	
  FontMetrics fm = g.getFontMetrics(f);
  Vector lines = wordWrap(fm,width,text);
   
  int fh = (fm.getHeight() + 1) * lines.size();
  int textY =  (((height -  fh) / 2) + fm.getHeight()) - 4;
  if (clicked)
  	textY += 1;	
  
  width --;
  height --;

  g.setFont(f);

  g.setColor(transparentColor);
  g.fillRect(0,0,width + 1,height + 1);
  
  if (clicked)
	  g.setColor(bottomColor);
  else
	  g.setColor(topColor);
  g.fillOval(0,0,width - 3,height - 3);

   if (clicked)
	  g.setColor(topColor);
  else
	  g.setColor(bottomColor);
  g.fillOval(3,3,width - 3,height - 3);
  
  g.setColor(backColor);
  g.fillOval(1,1,width - 3,height - 3);

  if (enabled)
	  g.setColor(textColor);
  for (int i = 0; i < lines.size(); i++) {
	  String line = (String) lines.elementAt(i);
	  int textX = ((width - fm.stringWidth(line)) / 2)  + (clicked ? 1 : 0);
	  if (enabled)
		  g.drawString(line,textX,textY);
	  else {	  
	   	  g.setColor(topColor);
		  g.drawString(line,textX + 1,textY + 1);
 		  g.setColor(bottomColor);
	  	  g.drawString(line,textX,textY);
	  }	 
	  textY += fm.getHeight() + 1;
  }	  

  if (_showFrame)
	  _frame.getGraphics().drawImage(img,0,height,_frame);
	  
  return img;
}
/**
 * This method creates a rectangular shaped button where the width is based on the size of the text.
 * @return java.awt.Image The button image
 * @param height int The height of the button
 * @param f java.awt.Font The font to use to draw the text
 * @param text java.lang.String The font to use to draw the text
 * @param textColor java.awt.Color The color to draw the text in
 * @param backColor java.awt.Color The color for the button background
 * @param topColor java.awt.Color The color for the top and left border
 * @param bottomColor java.awt.Color The color for the bottom and right border
 * @param transparentColor java.awt.Color The color to use for transparent areas on the button
 * @param clicked boolean True if you want the button to appear to be clicked
 * @param enabled boolean True if you want the button to appear to be enabled
 */
public Image createOvalButton (int height, Font f, String text, Color textColor, Color backColor, Color topColor, Color bottomColor,Color transparentColor, boolean clicked, boolean enabled)  {
  Graphics g = _frame.getGraphics();
  FontMetrics fm = g.getFontMetrics(f);
  int width = fm.stringWidth(text) + 14;
  return createOvalButton(width,height,f,text,textColor,backColor,topColor,bottomColor,transparentColor,clicked,enabled);
}
/**
 * This method creates a rectangular shaped button.
 * @return java.awt.Image The button image
 * @param width int The width of the button
 * @param height int The height of the button
 * @param f java.awt.Font The font to use to draw the text
 * @param text java.lang.String The font to use to draw the text
 * @param textColor java.awt.Color The color to draw the text in
 * @param backColor java.awt.Color The color for the button background
 * @param topColor java.awt.Color The color for the top and left border
 * @param bottomColor java.awt.Color The color for the bottom and right border
 * @param clicked boolean True if you want the button to appear to be clicked
 * @param enabled boolean True if you want the button to appear to be enabled
 */
public synchronized Image createRectangleButton (int width, int height, Font f, String text, Color textColor, Color backColor, Color topColor, Color bottomColor, boolean clicked, boolean enabled)  {
  Image img = _frame.createImage(width,height);
  Graphics g = img.getGraphics();
  	
  FontMetrics fm = g.getFontMetrics(f);
  Vector lines = wordWrap(fm,width,text);
   
  int fh = (fm.getHeight() + 1) * lines.size();
  int textY =  (((height -  fh) / 2) + fm.getHeight()) - 2;

  if (clicked) 
	  textY += 1;
  	
  width --;
  height --;


  g.setColor(backColor);
  g.fillRect(0,0,width,height);
  
  g.setFont(f);

  if (enabled)
	  g.setColor(textColor);
  for (int i = 0; i < lines.size(); i++) {
	  String line = (String) lines.elementAt(i);
	  int textX = ((width - fm.stringWidth(line)) / 2) + (clicked ? 1 : 0) ;
	  if (enabled)
		  g.drawString(line,textX,textY);
	  else {	  
	   	  g.setColor(topColor);
		  g.drawString(line,textX + 1,textY + 1);
 		  g.setColor(bottomColor);
	  	  g.drawString(line,textX,textY);
	  }	  

	  textY += fm.getHeight() + 1;
  }
  if (clicked)
	  g.setColor(bottomColor);
  else	  
	  g.setColor(topColor);
	  
  g.drawLine(0,0,width,0);
  g.drawLine(0,0,0,height);

  if (clicked)
	  g.setColor(topColor);
  else	  
	  g.setColor(bottomColor);
	  
  g.drawLine(width,0,width,height);
  g.drawLine(1,height,width,height);

  if (_showFrame)
	  _frame.getGraphics().drawImage(img,0,height,_frame);
  return img;
}
/**
 * This method creates a rectangular shaped button where the width is based on the size of the text.
 * @return java.awt.Image The button image
 * @param height int The height of the button
 * @param f java.awt.Font The font to use to draw the text
 * @param text java.lang.String The font to use to draw the text
 * @param textColor java.awt.Color The color to draw the text in
 * @param backColor java.awt.Color The color for the button background
 * @param topColor java.awt.Color The color for the top and left border
 * @param bottomColor java.awt.Color The color for the bottom and right border
 * @param clicked boolean True if you want the button to appear to be clicked
 * @param enabled boolean True if you want the button to appear to be enabled
 */
public Image createRectangleButton (int height, Font f, String text, Color textColor, Color backColor, Color topColor, Color bottomColor, boolean clicked, boolean enabled)  {
  Graphics g = _frame.getGraphics();
  FontMetrics fm = g.getFontMetrics(f);
  int width = fm.stringWidth(text) + 10;
  return createRectangleButton(width,height,f,text,textColor,backColor,topColor,bottomColor,clicked,enabled);
}
/**
 * This method creates a tab image.
 * @return java.awt.Image The tab image
 * @param width int The width of the tab
 * @param height int The height of the tab
 * @param f java.awt.Font The font to use to draw the text
 * @param text java.lang.String The font to use to draw the text
 * @param textColor java.awt.Color The color to draw the text in
 * @param backColor java.awt.Color The color for the button background
 * @param borderColor1 java.awt.Color The color for the top and left border
 * @param borderColor2 java.awt.Color The color for the right border
 * @param selected boolean True if you want the button to appear to be selected (no underline)
 */
public Image createTab (int width, int height, Font f, String text, Color textColor, Color backColor, Color borderColor1, Color borderColor2, Color transColor, boolean selected)  {
  Image img = _frame.createImage(width,height);
  Graphics g = img.getGraphics();
  	
  FontMetrics fm = g.getFontMetrics(f);
  Vector lines = wordWrap(fm,width,text);
   
  int fh = (fm.getHeight() + 1) * lines.size();
  int textY =  (((height -  fh) / 2) + fm.getHeight()) - 2;

  g.setColor(backColor);
  g.fillRect(0,0,width,height);

  int tabCornerSize = 6;
  
  g.setColor(transColor);
  g.fillRect(0,0,tabCornerSize,tabCornerSize);
  g.fillRect(width - tabCornerSize,0,tabCornerSize,tabCornerSize);
 
  g.setColor(backColor);
  g.fillArc(0,0,tabCornerSize * 2,tabCornerSize * 2,90,135);	

  Polygon p = new Polygon();
  p.addPoint(width,tabCornerSize);
  p.addPoint(width - tabCornerSize,0);
  p.addPoint(width - tabCornerSize,tabCornerSize);
  g.fillPolygon(p);
  
  g.setFont(f);
  g.setColor(textColor);
  
  for (int i = 0; i < lines.size(); i++) {
	  String line = (String) lines.elementAt(i);
	  int textX = ((width - fm.stringWidth(line)) / 2);
	  g.drawString(line,textX,textY);
	  textY += fm.getHeight() + 1;
  }

  g.setColor(borderColor2);
  g.drawLine(width - tabCornerSize,0,width,tabCornerSize);
  g.drawLine(width - 1,height - 2,width - 1,tabCornerSize);
   
  g.setColor(borderColor1);
  g.drawLine(0,tabCornerSize,0,height);
  g.drawArc(0,0,tabCornerSize * 2,tabCornerSize * 2,90,115);	
  g.drawLine(tabCornerSize,0,width - tabCornerSize,0);
  if (! selected) {
  	  g.drawLine(0,height - 1,width - 1,height - 1);
	  g.setColor(borderColor2);
	  g.drawLine(0,height - 2,width - 2,height - 2);

  }	  
  if (_showFrame)
	  _frame.getGraphics().drawImage(img,0,height,_frame);
  return img;
}
/**
 * This method creates a tab image. The width of the tab is determined by the text width.
 * @return java.awt.Image The tab image
 * @param width int The width of the tab
 * @param height int The height of the tab
 * @param f java.awt.Font The font to use to draw the text
 * @param text java.lang.String The font to use to draw the text
 * @param textColor java.awt.Color The color to draw the text in
 * @param backColor java.awt.Color The color for the button background
 * @param borderColor1 java.awt.Color The color for the top and left border
 * @param borderColor2 java.awt.Color The color for the right border
 * @param selected boolean True if you want the button to appear to be selected (no underline)
 */
public Image createTab (int height, Font f, String text, Color textColor, Color backColor, Color borderColor1, Color borderColor2, Color transColor, boolean selected)  {
  Graphics g = _frame.getGraphics();
  FontMetrics fm = g.getFontMetrics(f);
  int width = fm.stringWidth(text) + 10;
  return createTab(width,height,f,text,textColor,backColor,borderColor1,borderColor2,transColor,selected);

}
protected void finalize() {
	//_frame.dispose();	
}
private Vector wordWrap(FontMetrics fm, int width, String text) {
		String replaceS = "\uFFFD";
 		char replaceC = replaceS.charAt(0);
 		text += replaceC;
 		
		int lastSpace = -1,currentPos = 0,currentWidth = 0,startPos = 0,endPos = 0;
		int availWidth=width - 6;
		int lastPos = text.length();
		char lastChar = ' ';
		String textLine;
		Vector multiLineText = new Vector();
 
		for(currentPos = 0; currentPos < lastPos;currentPos ++) {
			lastChar = text.charAt(currentPos);
			if (lastChar != replaceC)
			   currentWidth += fm.charWidth(lastChar);
			if (currentWidth >= availWidth) {
			   if (lastSpace == -1)
				   endPos = currentPos - 2;
			   else
				   endPos = lastSpace + 1;
			   textLine = text.substring(startPos,endPos);
			   startPos = endPos;
			   currentPos = startPos - 1;
			   multiLineText.addElement(textLine.trim());
			
			   lastSpace = -1;
			   currentWidth = 0;
			}
			else {
			   if (lastChar == ' ')
				  lastSpace = currentPos;
			   if (lastChar == replaceC) {
				  endPos = currentPos;
				  textLine = text.substring(startPos,endPos);
				  startPos = endPos;
				  currentPos = endPos;
				  multiLineText.addElement(textLine.trim());
				  lastSpace = -1;
				  currentWidth = 0;
				  }
			 }
		}

		return multiLineText;	
  }  

/**
 * This method creates a 1 pixel image.
 * @return java.awt.Image The button image
 * @param color java.awt.Color The color to draw the text in
 */
public synchronized Image createOnePixelImage( Color color)  {
  Image img = _frame.createImage(1,1);
  Graphics g = img.getGraphics();
  	
  
  
  g.setColor(color);
  g.fillRect(0,0,1,1);
  
  return img;
}
}
