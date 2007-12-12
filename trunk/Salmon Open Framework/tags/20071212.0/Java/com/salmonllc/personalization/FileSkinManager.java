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
package com.salmonllc.personalization;

import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.VectorSort;
import java.io.*;


/**
 * This class loads and saves skins to a text files. It uses the following key SKIN_PATH from the property file to determine the location of the files. The naming convention for each file should be skin_name.skin.
 */

public class FileSkinManager extends SkinManager {

    String _appName = null;
    String _skinPath = null;

    void init(String appName) {
        Props p = Props.getProps(appName, null);
        _appName = appName;
        _skinPath = p.getProperty(Props.SKIN_PATH, Props.getPropsPath());
    }

    /*Loads the data for a skin with the specified name and append its attributes to the current skin*/
    public void load(String name, Skin skin) {
  		try {
			File f = new File(_skinPath + File.separatorChar + name + ".skin");
			if (! f.exists())
				return;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = in.readLine();
			int pos = 0;
			while (line != null) {
				if (line.length() > 0 && line.charAt(0) != '#') {
					pos = line.indexOf('=');
					if (pos > -1) {
						String att = line.substring(0,pos).trim();
						String val = line.substring(pos + 1).trim();
                        boolean classAtt = false;
                        boolean instAtt = false;
                        if (att.startsWith("class."))
                            classAtt = true;
                        else if (att.startsWith("instance."))
                            instAtt = true;

                        if (classAtt || instAtt) {
                            int ndx = att.lastIndexOf(".");
                            int ndx2 = att.indexOf(".");
                            if (ndx == ndx2)
                                continue;
                            String attName = att.substring(ndx + 1);
                            String attClass = att.substring(ndx2 + 1, ndx);
                            if (classAtt)
                                skin.setClassAttribute(attClass, attName, val);
                            else
                                skin.setInstanceAttribute(attClass, attName, val);
                        } else {
                            skin.setProperty(att, val);
                        }

					}
				}
				line = in.readLine();
			}
			in.close();
			return;
		}
		catch (Exception e) {
			MessageLog.writeErrorMessage("loadData",e,this);
		}
    }

    /*Saves a skin under the specified name, if it exists it is overwritten*/
    public void save(String name, Skin skin) {
        try {
            File f = new File(_skinPath + File.separatorChar + name + ".skin");
            PrintWriter pw= new PrintWriter(new FileOutputStream(f));
            Attribute att[] = skin.getAllAttributes();
            for (int i = 0; i < att.length; i++)
                pw.println(att[i].getAttribute() + "=" + att[i].getValue());
            pw.close();
        } catch (Exception e) {
            MessageLog.writeErrorMessage("save()", e, this);
        }
    }

    /*Gets an array of available skin names*/
    public String[] getSkinNames() {
        VectorSort v = new VectorSort() {
            public boolean compare(Object o1, Object o2) {
                return ((String) o1).compareTo((String)o2) < 0;
            }
            ;
        };
        File f = new File(_skinPath);
        File list[] = f.listFiles();
        for (int i = 0; i < list.length;i++) {
            String name = list[i].getName();
            if (name.endsWith(".skin"))
                v.add(name.substring(0,name.length() - 5));
        }
        v.sort();
        String ret[] = new String[v.size()];
        v.copyInto(ret);
        return ret;
    }

}
