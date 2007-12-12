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

import java.io.IOException;
import java.io.OutputStream;

/**
 * This interface is implemented by components that dynamically generate gif images that are sent back to the browser. The implementing class can use the ImageFactory and GifEncoder to generate an image. The generated images will be served up and cached by the ObjectStore servlet (Any Image with a dgif extension).
 * @see com  com.salmonllc.util.ImageFactory
 * @see com  com.salmonllc.util.GifEncoder
 */
public interface ImageGenerator {
    /**
     * This method should be implemented by any class that generates an image. The implementing class is responsible for actually generating
     * an image based on the name passed
     * @param imageName Each implementing class can decide for itself what image to generate based on the passed name.
     * @param out The output stream to write the generated image to
     */
    public void generateImage(String imageName, OutputStream out) throws IOException;

    /**
	 * This method is called by the Objectstore servlet to decide whether or not an image should be regenerated or loaded from cache. It should return the value set in the setCacheKey method.
	 * @uml.property  name="cacheKey"
	 */
    public long getCacheKey();

    /**
     * This method is called by the Objectstore servlet to decide whether or not to cache generated images.
     */

    public boolean getUseCache();

    /**
	 * This method is used by the framework to decide whether or not to generate the image or get it from cache. The implementing class should store the passed value in an instance variable.
	 * @uml.property  name="cacheKey"
	 */
    public void setCacheKey(long key);
}
