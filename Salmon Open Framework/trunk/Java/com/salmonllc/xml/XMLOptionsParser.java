package com.salmonllc.xml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/xml/XMLOptionsParser.java $
//$Author: Dan $ 
//$Revision: 12 $ 
//$Modtime: 6/11/03 4:30p $ 
/////////////////////////
/**
 * The XML Options paser is used to parse any XML file specified as options.dtd file.
 */

import java.io.*;
import org.w3c.dom.*;

/**
 * @author  demian
 */
public class XMLOptionsParser
{

	/** Default parser name. */
	private static final String DEFAULT_PARSER_NAME = "com.salmonllc.xml.DOMParser";

	private static Options _fieldOptions = new Options();
	
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

public XMLOptionsParser(String encoding, boolean canonical) throws UnsupportedEncodingException
{
	out = new PrintWriter(new OutputStreamWriter(System.out, encoding));
	this.canonical = canonical;
	_fieldOptions = new Options();
} // <init>(String,boolean)
   //
   // Constructors
   //

   /** Default constructor. */
   public XMLOptionsParser(boolean canonical) throws UnsupportedEncodingException {
      this( getWriterEncoding(), canonical);
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
 * Gets the Options Object for the XML file read
 * Creation date: (8/1/01 12:22:07 PM)
 * @return com.salmonllc.xml.Options
 */
public Options getOptions() {
	return _fieldOptions;
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
      String  parserName = DEFAULT_PARSER_NAME;
      boolean canonical  = false;
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
               parserName = argv[++i];
               continue;
            }

            if ( arg.equals("-c") ) {
               canonical = true;
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
         parseFile(parserName, arg, canonical, null);
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
  * This method is used to create the Option object out of the XML file. XML file name 
  * is passed as uri and values object is used to upload options given in XML file.
  * 
  */
public static void parseFile(String uri, Options values)
{
	parseFile(DEFAULT_PARSER_NAME, uri, false, values);

} // print(String,String,boolean)
/** Prints the resulting document tree. */
private static void parseFile(String parserWrapperName, String uri, boolean canonical, Options values)
{
	try
		{
			
		DOMParserWrapper parser = (DOMParserWrapper) Class.forName(parserWrapperName).newInstance();
		Document document = parser.parse(uri);
		XMLOptionsParser writer = new XMLOptionsParser(canonical);
		_fieldOptions  = writer.parseNode(document, values); 
	} catch (Exception e)
		{
		e.printStackTrace(System.err);
	}

} // print(String,String,boolean)
/** Prints the specified node, recursively. */
private Options parseNode(Node node, Options values)
{

	// is there anything to do?
	if (node == null)
		{
		return values;
	}

	int type = node.getNodeType();
	switch (type)
		{
		// print document
		case Node.DOCUMENT_NODE :
			parseNode(((Document) node).getDocumentElement(), values);
			break;
			// print element with attributes
		case Node.ELEMENT_NODE :

			if (node.getNodeName().equalsIgnoreCase("Options"))
			{
				NodeList children = node.getChildNodes();
				if (children != null)
				{			
					int len = children.getLength();
					for (int i = 0; i < len; i++)
					{
						Node nodeChild = children.item(i);
						if (nodeChild.getNodeName().equalsIgnoreCase("Option"))
						{
								values.put(
											getNodeValue(nodeChild, "key"),
											getNodeValue(nodeChild, "value"));
						}
					}
				}
			} 
			
			// recursive call
			NodeList children = node.getChildNodes();
			if (children != null)
				{
				int len = children.getLength();
				for (int i = 0; i < len; i++)
					{
					parseNode(children.item(i), values);
				}
			}

			break;

	}

		return values;
} // parseNode(Node)
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
