package com.salmonllc.xml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/xml/XMLTreeParser.java $
//$Author: Dan $ 
//$Revision: 10 $ 
//$Modtime: 6/11/03 4:53p $ 
/////////////////////////
/**
 * The XML Tree paser is used to parse any XML file specified as Tree.dtd file.
 */

import java.io.*;
import org.w3c.dom.*;

/**
 * @author  demian
 */
public class XMLTreeParser
{

	/** Default parser name. */
	private static final String DEFAULT_PARSER_NAME = "com.salmonllc.xml.DOMParser";

	private static String URIpath= "";

	private static Tree fieldTree = null;
	
	/** Default Encoding */
	private static String PRINTWRITER_ENCODING = "UTF8";

	private static String MIME2JAVA_ENCODINGS[] =
		{
			"Default",
			"UTF-8",
			"US-ASCII",
			"ISO-8859-1",
			"ISO-8859-2",
			"ISO-8859-3",
			"ISO-8859-4",
			"ISO-8859-5",
			"ISO-8859-6",
			"ISO-8859-7",
			"ISO-8859-8",
			"ISO-8859-9",
			"ISO-2022-JP",
			"SHIFT_JIS",
			"EUC-JP",
			"GB2312",
			"BIG5",
			"EUC-KR",
			"ISO-2022-KR",
			"KOI8-R",
			"EBCDIC-CP-US",
			"EBCDIC-CP-CA",
			"EBCDIC-CP-NL",
			"EBCDIC-CP-DK",
			"EBCDIC-CP-NO",
			"EBCDIC-CP-FI",
			"EBCDIC-CP-SE",
			"EBCDIC-CP-IT",
			"EBCDIC-CP-ES",
			"EBCDIC-CP-GB",
			"EBCDIC-CP-FR",
			"EBCDIC-CP-AR1",
			"EBCDIC-CP-HE",
			"EBCDIC-CP-CH",
			"EBCDIC-CP-ROECE",
			"EBCDIC-CP-YU",
			"EBCDIC-CP-IS",
			"EBCDIC-CP-AR2",
			"UTF-16" };

	/** Print writer. */
	protected PrintWriter out;

	/** Canonical output. */
	protected boolean canonical;

public XMLTreeParser(String encoding, boolean canonical) throws UnsupportedEncodingException
{
	out = new PrintWriter(new OutputStreamWriter(System.out, encoding));
	this.canonical = canonical;
	fieldTree = new Tree();
} // <init>(String,boolean)
   //
   // Constructors
   //

   /** Default constructor. */
   public XMLTreeParser(boolean canonical) throws UnsupportedEncodingException {
      this( getWriterEncoding(), canonical);
   }
/**
 * Creation date: (7/20/01 2:03:28 PM)
 * @return java.lang.String
 * @param node org.w3c.dom.Node
 * @param columnName java.lang.String
 */
private boolean getNodeBooleanValue(Node node, String columnName) {
	
	Node temp = node.getAttributes().getNamedItem(columnName);
	if(temp != null)
		return temp.getNodeValue().equals("true");

	return false;
}
/**
 * Creation date: (7/20/01 2:03:28 PM)
 * @return java.lang.String
 * @param node org.w3c.dom.Node
 * @param columnName java.lang.String
 */
private String getNodeValue(Node node, String columnName) {
	
	Node temp = node.getAttributes().getNamedItem(columnName);
	if(temp != null)
		return temp.getNodeValue();

	return null;
}
/**
 * Gets the Tree detailed in XML file
 * Creation date: (8/21/01 5:12:27 PM)
 * @return com.salmonllc.xml.Tree
 */
public static Tree getTree() {
	return fieldTree;
}
   private static String getWriterEncoding( ) {
      return (PRINTWRITER_ENCODING);
   }// getWriterEncoding 
   private static boolean isValidJavaEncoding( String encoding ) {
      for ( int i = 0; i < MIME2JAVA_ENCODINGS.length; i++ )
         if ( encoding.equals( MIME2JAVA_ENCODINGS[i] ) )
            return (true);

      return (false);
   }// isValidJavaEncoding 
   //
   // Main
   //

   /** Main program entry point. */
   public static void main(String argv[]) {

      // is there anything to do?
      if ( argv.length == 0 ) {
         printUsage();
         System.exit(1);
      }

      // vars

      String  encoding   = "UTF8"; // default encoding

      // check parameters
      for ( int i = 0; i < argv.length; i++ ) {
         String arg = argv[i];

         // options
         if ( arg.startsWith("-") ) {
            if ( arg.equals("-p") ) {
               if ( i == argv.length - 1 ) {
                  System.err.println("error: missing parser name");
                  System.exit(1);
               }
               continue;
            }

            if ( arg.equals("-c") ) {
               continue;
            }

            if ( arg.equals("-h") ) {
               printUsage();
               System.exit(1);
            }

            if ( arg.equals("-e") ) {
               if ( i == argv.length - 1 ) {
                  System.err.println("error: missing encoding name");
                  printValidJavaEncoding();
                  System.exit(1);
               } else {
                  encoding = argv[++i];
                  if ( isValidJavaEncoding( encoding ) )
                     setWriterEncoding( encoding );
                  else {
                     printValidJavaEncoding();
                     System.exit( 1 );
                  }
               }
               continue;
            }

         }

         // print uri
         System.err.println(arg+':');
         //parseFile(parserName, arg, canonical );
         System.err.println();
      }

   } // main(String[])
   /** Normalizes the given string. */
   protected String normalize(String s) {
      StringBuffer str = new StringBuffer();

      int len = (s != null) ? s.length() : 0;
      for ( int i = 0; i < len; i++ ) {
         char ch = s.charAt(i);
         switch ( ch ) {
            case '<': {
                  str.append("&lt;");
                  break;
               }
            case '>': {
                  str.append("&gt;");
                  break;
               }
            case '&': {
                  str.append("&amp;");
                  break;
               }
            case '"': {
                  str.append("&quot;");
                  break;
               }
            case '\r':
            case '\n': {
                  if ( canonical ) {
                     str.append("&#");
                     str.append(Integer.toString(ch));
                     str.append(';');
                     break;
                  }
                  // else, default append char
               }
            default: {
                  str.append(ch);
               }
         }
      }

      return (str.toString());

   } // normalize(String):String
/** 
  * This method is used to create the Tree object out of the XML file. XML file name 
  * is passed as uri. Method getTree is used after this method to get the Tree object.
  * 
  */
public static void parseFile(String uri) throws Exception
{
	parseFile(DEFAULT_PARSER_NAME, uri, false);

} // print(String,String,boolean)
/** Prints the resulting document tree. */
private static void parseFile(String parserWrapperName, String uri, boolean canonical) throws Exception
{
	int nUriLastSlashIndex = uri.lastIndexOf("\\");
	boolean anypath=true;
	if (nUriLastSlashIndex == -1)
	{
		anypath = false;
		nUriLastSlashIndex = uri.lastIndexOf("/");
		if(nUriLastSlashIndex != -1)
		{
			anypath = true;
		}
	}

	if(anypath)
		URIpath = uri.substring(0, nUriLastSlashIndex);
		
	DOMParserWrapper parser = (DOMParserWrapper) Class.forName(parserWrapperName).newInstance();
	Document document = parser.parse(uri);
	XMLTreeParser writer = new XMLTreeParser(canonical);
	writer.parseNode(document);
	System.out.println("Done" + getTree());
} // print(String,String,boolean)
/** Prints the specified node, recursively. */
private void parseNode(Node node)
{

	// is there anything to do?
	if (node == null)
		{
		return;
	}

	int type = node.getNodeType();
	switch (type)
		{
		// print document
		case Node.DOCUMENT_NODE :
			parseNode(((Document) node).getDocumentElement());
			break;
			// print element with attributes
		case Node.ELEMENT_NODE :

			if (node.getNodeName().equalsIgnoreCase("Tree"))
			{
				NodeList children = node.getChildNodes();
				if (children != null)
				{
					if(getTree()== null)
						setTree(new Tree());

					getTree().setName(getNodeValue(node, "name"));
					getTree().setExpandImage(getNodeValue(node, "expandImage"));
					getTree().setContractImage(getNodeValue(node, "contractImage"));
					getTree().setNullImage(getNodeValue(node, "nullImage"));
					getTree().setSelectMode(getNodeValue(node, "selectMode"));
					getTree().setTreeMode(getNodeValue(node, "treeMode"));
					getTree().setShowRoot(getNodeBooleanValue(node, "showRoot"));
					getTree().setTarget(getNodeValue(node, "target"));
					
					int len = children.getLength();
					for (int i = 0; i < len; i++)
					{
						Node nodeChild = children.item(i);
						if (nodeChild.getNodeName().equalsIgnoreCase("TreeItem"))
						{
							parseNode(nodeChild, getTree());
						}
					}
				}
			}

			break;

	}
}
/** Prints the specified node, recursively. */
private Tree parseNode(Node node, Tree tree)
{
	int type = node.getNodeType(); 
	switch (type)
		{
		// print document
		case Node.DOCUMENT_NODE :
			parseNode(((Document) node).getDocumentElement());
			break;
			// print element with attributes
		case Node.ELEMENT_NODE :

			if (node.getNodeName().equalsIgnoreCase("TreeItem"))
			{
				NodeList children = node.getChildNodes();
				if (children != null)
				{
					TreeItem item = new TreeItem();
					item.setText(getNodeValue(node, "text"));
					item.setHref(getNodeValue(node, "href"));
					item.setImgSource(getNodeValue(node, "imgsource"));
					item.setImgExpSource(getNodeValue(node, "imgexpsource"));
					item.setImgHref(getNodeValue(node, "imghref"));
		
					if (tree.getChilds()== null)
						tree.setChilds(new java.util.Vector());
					else
						tree.getChilds().add(item);
			
					int len = children.getLength();
					for (int i = 0; i < len; i++)
					{
						Node nodeChild = children.item(i);
						if(nodeChild.getNodeName().equalsIgnoreCase("TreeItem"))
							parseNode(nodeChild, item);
						if(nodeChild.getNodeName().equalsIgnoreCase("TreeItemAddImage"))
						{
							if(item.getAddImages() == null)
							{
								item.setAddImages(new java.util.Vector());
							}
							// Create a Add image Object
								TreeItemAddImage addImage = new TreeItemAddImage();
								addImage.setHref(getNodeValue(nodeChild, "href"));
								addImage.setName(getNodeValue(nodeChild, "name"));
								addImage.setSrc(getNodeValue(nodeChild, "src"));
								addImage.setText(getNodeValue(nodeChild, "text"));
							// add it to a Tree Item - Node
							item.getAddImages().add(addImage);
							
						}
					}
				}
			}

			break;
	}

	return tree;
}
   /** Prints the usage. */
   private static void printUsage() {

      System.err.println("usage: java dom.DOMWriter (options) uri ...");
      System.err.println();
      System.err.println("options:");
      System.err.println("  -p name  Specify DOM parser wrapper by name.");
      System.err.println("           Default parser: "+DEFAULT_PARSER_NAME);
      System.err.println("  -c       Canonical XML output.");
      System.err.println("  -h       This help screen.");
      System.err.println("  -e       Output Java Encoding.");
      System.err.println("           Default encoding: UTF-8");

   } // printUsage()
   private static void printValidJavaEncoding() {
      System.err.println( "    ENCODINGS:" );
      System.err.print( "   " );
      for( int i = 0;
                     i < MIME2JAVA_ENCODINGS.length; i++) {
         System.err.print( MIME2JAVA_ENCODINGS[i] + " " );
      if( (i % 7 ) == 0 ){
         System.err.println();
         System.err.print( "   " );
         }
      }

   } // printJavaEncoding()            
/**
 * Creation date: (8/21/01 5:12:27 PM)
 * @param newTree com.salmonllc.xml.Tree
 */
private static void setTree(Tree newTree) {
	fieldTree = newTree;
}
   private static void  setWriterEncoding( String encoding ) {
      if( encoding.equalsIgnoreCase( "DEFAULT" ) )
         PRINTWRITER_ENCODING  = "UTF8";
      else if( encoding.equalsIgnoreCase( "UTF-16" ) )
         PRINTWRITER_ENCODING  = "Unicode";
      //else
       //  PRINTWRITER_ENCODING = MIME2Java.convert( encoding ); 
   }// setWriterEncoding 
   /** Returns a sorted list of attributes. */
   protected Attr[] sortAttributes(NamedNodeMap attrs) {

      int len = (attrs != null) ? attrs.getLength() : 0;
      Attr array[] = new Attr[len];
      for ( int i = 0; i < len; i++ ) {
         array[i] = (Attr)attrs.item(i);
      }
      for ( int i = 0; i < len - 1; i++ ) {
         String name  = array[i].getNodeName();
         int    index = i;
         for ( int j = i + 1; j < len; j++ ) {
            String curName = array[j].getNodeName();
            if ( curName.compareTo(name) < 0 ) {
               name  = curName;
               index = j;
            }
         }
         if ( index != i ) {
            Attr temp    = array[i];
            array[i]     = array[index];
            array[index] = temp;
         }
      }

      return (array);

   } // sortAttributes(NamedNodeMap):Attr[]
}
