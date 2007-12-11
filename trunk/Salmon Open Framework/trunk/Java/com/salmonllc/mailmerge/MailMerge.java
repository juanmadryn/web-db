package com.salmonllc.mailmerge;


/////////////////////////
//$Archive: /JADE/SourceCode/com/salmonllc/mailmerge/MailMerge.java $
//$Author: Srufle $ 
//$Revision: 21 $ 
//$Modtime: 7/31/02 6:15p $ 
/////////////////////////

import com.salmonllc.properties.*;
import java.io.*;
import java.util.*;
/**
 * Mail Merge for Winword on Server Requirements: Need Winword installed on server. Need the following properties in properties file, preferably system.properties WinWordPath=d:\\Microsoft Office\\Office WinWordTemplatePath=d:\\Microsoft Office\\Templates WinWordPath is the Path to winword.exe WinWordTemplatePath is the Path to the location of Salmon.dot which is required to be deployed to the server. Deploy Salmon.dot file to the directory specified by WinWordTemplatePath property. Use mailMergePrint to print the mail merged document on the server printer. Use mailMergePrintToFile to print the mail merged document to a file, useful for generating PDF's see below. Use mailMergeSaveAs to save the mail merged document to a doc file, which can be sent back to a browser for client side printing. If you want to print to pdf, you must have Acrobat installed on the server and setup as the default printer and use mailMergePrintToFile method to generate the PDF which can be sent back to a browser for client side printing. Creation date: (1/11/02 2:53:36 PM)
 * @author  : Fred Cahill
 */
public class MailMerge implements Runnable {
	private Thread _wakeupthread;
	private MailMergeException _mme;
	private String[] _saCommandArgs;
	private String[] _saEnvironArgs;
	private Process _process;
	private Hashtable _htTextBookmarks=new Hashtable();
	private Hashtable _htPictureBookmarks=new Hashtable();
	private Hashtable _htFileBookmarks=new Hashtable();
	private Hashtable _htOLEBookmarks=new Hashtable();
	private File _fBackground;
	private Float _fLeftMargin;
	private Float _fRightMargin;
	private Float _fTopMargin;
	private Float _fBottomMargin;
	private Float _fGutter;
	private Float _fHeaderDistance;
	private Float _fFooterDistance;
	private Vector _vExtraEnvironArgs=new Vector();
	private String _sPrinter;

/**
 * Creates a MailMerge object.
 * Creation date: (3/15/02 10:44:02 AM)
 */
public MailMerge() {}
private MailMerge(String[] commandArgs,String[] environArgs,Thread wakeupThread) {
	_saCommandArgs=commandArgs;
	_saEnvironArgs=environArgs;
	_wakeupthread=wakeupThread;

}
/**
 * Specifies an OLE File to be inserted at a field location in a word template.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sField java.lang.String Name of a field in a word template file
 * @param fOleFile java.io.File A File object pointing to a OLE File. Examples are .xls,.doc,.ppt
 */
	public void addOLEFileField(String sField,File fOLEFile) throws MailMergeException {
		if (!fOLEFile.exists())
		  throw new MailMergeException("File "+fOLEFile.getAbsolutePath()+" does not exist.");
		_htOLEBookmarks.put(sField,fOLEFile);
	}
/**
 * Specifies a Picture File to be inserted at a field location in a word template.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sField java.lang.String Name of a field in a word template file
 * @param fPicture java.io.File A File object pointing to a Picture File. Examples are .bmp,.gif,.jpg
 */
	public void addPictureField(String sField,File fPicture) throws MailMergeException {
		if (!fPicture.exists())
		  throw new MailMergeException("File "+fPicture.getAbsolutePath()+" does not exist.");
		_htPictureBookmarks.put(sField,fPicture);
	}
/**
 * Specifies a Simple File to be inserted at a field location in a word template.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sField java.lang.String Name of a field in a word template file
 * @param fSimpleFile java.io.File A File object pointing to a Simple File. Examples are .txt
 */
	public void addSimpleFileField(String sField,File fSimpleFile) throws MailMergeException {
		if (!fSimpleFile.exists())
		  throw new MailMergeException("File "+fSimpleFile.getAbsolutePath()+" does not exist.");
		_htFileBookmarks.put(sField,fSimpleFile);
	}
/**
 * Specifies text to be inserted at a field location in a word template.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sField java.lang.String Name of a field in a word template file
 * @param sText java.lang.String The text to be inserted
 */
	public void addTextField(String sField,String sText) {
		_htTextBookmarks.put(sField,sText);
	}
/**
 * Concatenates two word files and prints the result.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sFile1 String Primary word document
 * @param sFile2 String Document to append to primary one.
 */
	public static void concatFilesPrint(String sFile1,String sFile2) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fFile1=new File(sFile1);
		File fFile2=new File(sFile2);
		if (!fFile1.exists())
		  throw new MailMergeException(sFile1+" does not exist.");
		if (!fFile2.exists())
		  throw new MailMergeException(sFile2+" does not exist.");
		String[] saCommandArgs=new String[3];
		String[] saEnvArgs=new String[2];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]= sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mConcatFiles";
		saEnvArgs[0]="MMFILE1="+sFile1;
		saEnvArgs[1]="MMFILE2="+sFile2;
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
/**
 * Concatenates two word files and prints the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sFile1 String Primary word document
 * @param sFile2 String Document to append to primary one.
 * @param sPrintFileName String Name of the generated print file.
 */
	public static void concatFilesPrintToFile(String sFile1,String sFile2,String sPrintFileName) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fFile1=new File(sFile1);
		File fFile2=new File(sFile2);
		if (!fFile1.exists())
		  throw new MailMergeException(sFile1+" does not exist.");
		if (!fFile2.exists())
		  throw new MailMergeException(sFile2+" does not exist.");
		String[] saCommandArgs=new String[3];
		String[] saEnvArgs=new String[3];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]= sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mConcatFiles";
		saEnvArgs[0]="MMFILE1="+sFile1;
		saEnvArgs[1]="MMFILE2="+sFile2;
		saEnvArgs[2]="MMPRINTFILE="+sPrintFileName;
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
/**
 * Concatenates two word files and saves the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sFile1 String Primary word document
 * @param sFile2 String Document to append to primary one.
 * @param sSaveAs String Name of the generated file.
 */
	public static void concatFilesSaveAs(String sFile1,String sFile2,String sSaveAsFileName) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fFile1=new File(sFile1);
		File fFile2=new File(sFile2);
		if (!fFile1.exists())
		  throw new MailMergeException(sFile1+" does not exist.");
		if (!fFile2.exists())
		  throw new MailMergeException(sFile2+" does not exist.");
		String[] saCommandArgs=new String[3];
		String[] saEnvArgs=new String[3];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]= sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mConcatFiles";
		saEnvArgs[0]="MMFILE1="+sFile1;
		saEnvArgs[1]="MMFILE2="+sFile2;
		saEnvArgs[2]="MMSAVEAS="+sSaveAsFileName;
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
	private MailMergeException getMailMergeException() {
		return _mme;
	}
	private Process getMailMergeProcess() {
		return _process;
	}
/**
 * Mail Merges a template file with the values in DataSource File and prints the result.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sDataSourceFileName java.lang.String Name of a DataSoure File usually a .csv file.
 */
	public static void mailMergePrint(String sTemplateFileName,String sDataSourceFileName) throws MailMergeException {
		mailMergePrint(sTemplateFileName,sDataSourceFileName,null);
	}
/**
 * Mail Merges a template file with the values in DataSource File and a Hashtable and prints the result.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sDataSourceFileName java.lang.String Name of a DataSoure File usually a .csv file.
 * @param htFields java.util.Hashtable A hashtable of field names & values to be used to populate template.
 */
	public static void mailMergePrint(String sTemplateFileName,String sDataSourceFileName,Hashtable htFields) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fTemplate=new File(sTemplateFileName);
		File fDatasource=null;
		if (sDataSourceFileName!=null && !sDataSourceFileName.trim().equals(""))
			fDatasource=new File(sDataSourceFileName);
		if (!fTemplate.exists())
		  throw new MailMergeException(sTemplateFileName+" does not exist.");
		if (fDatasource!=null && !fDatasource.exists())
		  throw new MailMergeException(sDataSourceFileName+" does not exist.");
		Vector vBookmarks=new Vector();
		Vector vBookmarkValues=new Vector();
		if (htFields!=null) {
			Enumeration enumBookmarks=htFields.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				String sValue=(String)htFields.get(sBookmark);
				vBookmarks.addElement("MMBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMBOOKMARKVALUE"+i+"="+sValue);
				i++;
			}
		}
		int iStartIndex=fDatasource==null?1:2;
		String[] saCommandArgs=new String[3];
		String[] saEnvArgs=new String[iStartIndex+(vBookmarks.size()*2)];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]=sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mMailMerge";
		if (fDatasource!=null)
			saEnvArgs[0]="MMDATASOURCE="+sDataSourceFileName;
		saEnvArgs[iStartIndex-1]="MMTEMPLATE="+sTemplateFileName;
		for (int k=iStartIndex;k<saEnvArgs.length;k+=2) {
			saEnvArgs[k]=(String)vBookmarks.elementAt((k-iStartIndex)/2);
			saEnvArgs[k+1]=(String)vBookmarkValues.elementAt((k-iStartIndex)/2);
		}
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
/**
 * Mail Merges a template file with the values in the Hastable and prints the result.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param htFields java.util.Hashtable A hashtable of field names & values to be used to populate template.
 */
	public static void mailMergePrint(String sTemplateFileName,Hashtable htFields) throws MailMergeException {
		mailMergePrint(sTemplateFileName,null,htFields);
	}
/**
 * Mail Merges a template file with the values in DataSource File and prints the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sDataSourceFileName java.lang.String Name of a DataSoure File usually a .csv file.
 * @param sPrintFileName java.lang.String Name of the printed file output
 */
	public static void mailMergePrintToFile(String sTemplateFileName,String sDataSourceFileName,String sPrintFileName) throws MailMergeException {
		mailMergePrintToFile(sTemplateFileName,sDataSourceFileName,sPrintFileName,null);
	}
/**
 * Mail Merges a template file with the values in DataSource File and Hashtable and prints the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sDataSourceFileName java.lang.String Name of a DataSoure File usually a .csv file.
 * @param sPrintFileName java.lang.String Name of the printed file output
 * @param htFields java.util.Hashtable A hashtable of field names & values to be used to populate template.
 */
	public static void mailMergePrintToFile(String sTemplateFileName,String sDataSourceFileName,String sPrintFileName,Hashtable htFields) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fTemplate=new File(sTemplateFileName);
		File fDatasource=null;
		if (sDataSourceFileName!=null && !sDataSourceFileName.trim().equals(""))
			fDatasource=new File(sDataSourceFileName);
		if (!fTemplate.exists())
		  throw new MailMergeException(sTemplateFileName+" does not exist.");
		if (fDatasource!=null && !fDatasource.exists())
		  throw new MailMergeException(sDataSourceFileName+" does not exist.");
		Vector vBookmarks=new Vector();
		Vector vBookmarkValues=new Vector();
		if (htFields!=null) {
			Enumeration enumBookmarks=htFields.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				String sValue=(String)htFields.get(sBookmark);
				vBookmarks.addElement("MMBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMBOOKMARKVALUE"+i+"="+sValue);
				i++;
			}
		}
		int iStartIndex=fDatasource==null?2:3;
		String[] saCommandArgs=new String[3];
		String[] saEnvArgs=new String[iStartIndex+(vBookmarks.size()*2)];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]=sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mMailMerge";
		if (fDatasource!=null)
			saEnvArgs[0]="MMDATASOURCE="+sDataSourceFileName;
		saEnvArgs[iStartIndex-2]="MMPRINTFILE="+sPrintFileName;
		saEnvArgs[iStartIndex-1]="MMTEMPLATE="+sTemplateFileName;
		for (int k=iStartIndex;k<saEnvArgs.length;k+=2) {
			saEnvArgs[k]=(String)vBookmarks.elementAt((k-iStartIndex)/2);
			saEnvArgs[k+1]=(String)vBookmarkValues.elementAt((k-iStartIndex)/2);
		}
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
/**
 * Mail Merges a template file with the values in the Hastable and prints the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sPrintFileName java.lang.String Name of the printed file output
 * @param htFields java.util.Hashtable A hashtable of field names & values to be used to populate template.
 */
	public static void mailMergePrintToFile(String sTemplateFileName,String sPrintFileName,Hashtable htFields) throws MailMergeException {
		mailMergePrintToFile(sTemplateFileName,null,sPrintFileName,htFields);
	}
/**
 * Mail Merges a template file with the values in DataSource File and saves the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sDataSourceFileName java.lang.String Name of a DataSoure File usually a .csv file.
 * @param sSaveAsFileName java.lang.String Name of the saved file
 */
	public static void mailMergeSaveAs(String sTemplateFileName,String sDataSourceFileName,String sSaveAsFileName) throws MailMergeException {
		mailMergeSaveAs(sTemplateFileName,sDataSourceFileName,sSaveAsFileName,null);
	}
/**
 * Mail Merges a template file with the values in DataSource File and Hashtable and saves the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sDataSourceFileName java.lang.String Name of a DataSoure File usually a .csv file.
 * @param sSaveAsFileName java.lang.String Name of the saved file
 * @param htFields java.util.Hashtable A hashtable of field names & values to be used to populate template.
 */
	public static void mailMergeSaveAs(String sTemplateFileName,String sDataSourceFileName,String sSaveAsFileName,Hashtable htFields) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fTemplate=new File(sTemplateFileName);
		File fDatasource=null;
		if (sDataSourceFileName!=null && !sDataSourceFileName.trim().equals(""))
			fDatasource=new File(sDataSourceFileName);
		if (!fTemplate.exists())
		  throw new MailMergeException(sTemplateFileName+" does not exist.");
		if (fDatasource!=null && !fDatasource.exists())
		  throw new MailMergeException(sDataSourceFileName+" does not exist.");
		Vector vBookmarks=new Vector();
		Vector vBookmarkValues=new Vector();
		if (htFields!=null) {
			Enumeration enumBookmarks=htFields.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				String sValue=(String)htFields.get(sBookmark);
				vBookmarks.addElement("MMBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMBOOKMARKVALUE"+i+"="+sValue);
				i++;
			}
		}
		int iStartIndex=fDatasource==null?2:3;
		String[] saCommandArgs=new String[3];
		String[] saEnvArgs=new String[iStartIndex+(vBookmarks.size()*2)];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]= sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mMailMerge";
		if (fDatasource!=null)
			saEnvArgs[0]="MMDATASOURCE="+sDataSourceFileName;
		saEnvArgs[iStartIndex-2]="MMSAVEAS="+sSaveAsFileName;
		saEnvArgs[iStartIndex-1]="MMTEMPLATE="+sTemplateFileName;
		for (int k=iStartIndex;k<saEnvArgs.length;k+=2) {
			saEnvArgs[k]=(String)vBookmarks.elementAt((k-iStartIndex)/2);
			saEnvArgs[k+1]=(String)vBookmarkValues.elementAt((k-iStartIndex)/2);
		}
		
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
/**
 * Mail Merges a template file with the values in the Hastable and saves the result to a file.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of a word template file
 * @param sSaveAsFileName java.lang.String Name of the saved file
 * @param htFields java.util.Hashtable A hashtable of field names & values to be used to populate template.
 */
	public static void mailMergeSaveAs(String sTemplateFileName,String sSaveAsFileName,Hashtable htFields) throws MailMergeException {
		mailMergeSaveAs(sTemplateFileName,null,sSaveAsFileName,htFields);
	}
/**
 * Prints a Template File after Mail Merging with a Datasource.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of the word template file
 * @param sDataSourceFileName java.lang.String Name of the Data Source File usually a .csv file
 */
	public void print(String sTemplateFileName,String sDataSourceFileName) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fTemplate=new File(sTemplateFileName);
		File fDatasource=null;
		if (sDataSourceFileName!=null && !sDataSourceFileName.trim().equals(""))
			fDatasource=new File(sDataSourceFileName);
		if (!fTemplate.exists())
		  throw new MailMergeException(sTemplateFileName+" does not exist.");
		if (fDatasource!=null && !fDatasource.exists())
		  throw new MailMergeException(sDataSourceFileName+" does not exist.");
		Vector vBookmarks=new Vector();
		Vector vBookmarkValues=new Vector();
		if (_htTextBookmarks!=null) {
			Enumeration enumBookmarks=_htTextBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				String sValue=(String)_htTextBookmarks.get(sBookmark);
				vBookmarks.addElement("MMBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMBOOKMARKVALUE"+i+"="+sValue);
				i++;
			}
		}
		if (_htPictureBookmarks!=null) {
			Enumeration enumBookmarks=_htPictureBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htPictureBookmarks.get(sBookmark);
				vBookmarks.addElement("MMPICTUREBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMPICTUREBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		if (_htFileBookmarks!=null) {
			Enumeration enumBookmarks=_htFileBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htFileBookmarks.get(sBookmark);
				vBookmarks.addElement("MMFILEBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMFILEBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		if (_htOLEBookmarks!=null) {
			Enumeration enumBookmarks=_htOLEBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htOLEBookmarks.get(sBookmark);
				vBookmarks.addElement("MMOLEFILEBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMOLEFILEBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		int iStartIndex=fDatasource==null?1:2;
		String[] saCommandArgs=new String[3];
		int iSize=iStartIndex+(vBookmarks.size()*2)+_vExtraEnvironArgs.size();
		String[] saEnvArgs=new String[iSize];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]=sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mMailMerge";
		if (fDatasource!=null)
			saEnvArgs[0]="MMDATASOURCE="+sDataSourceFileName;
		saEnvArgs[iStartIndex-1]="MMTEMPLATE="+sTemplateFileName;
		int k=iStartIndex;
		for (;k<iSize-_vExtraEnvironArgs.size();k+=2) {
			saEnvArgs[k]=(String)vBookmarks.elementAt((k-iStartIndex)/2);
			saEnvArgs[k+1]=(String)vBookmarkValues.elementAt((k-iStartIndex)/2);
		}
		for (;k<iSize;k++) {
			saEnvArgs[k]=(String)_vExtraEnvironArgs.elementAt(k-(iSize-_vExtraEnvironArgs.size()));
		} 
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
/**
 * Prints to a File a Template File after Mail Merging with a Datasource.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of the word template file
 * @param sDataSourceFileName java.lang.String Name of the Data Source File usually a .csv file
 * @param sPrintFileName java.lang.String Name of the Print File Output.
 */
	public void printToFile(String sTemplateFileName,String sDataSourceFileName,String sPrintFileName) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fTemplate=new File(sTemplateFileName);
		File fDatasource=null;
		if (sDataSourceFileName!=null && !sDataSourceFileName.trim().equals(""))
			fDatasource=new File(sDataSourceFileName);
		if (!fTemplate.exists())
		  throw new MailMergeException(sTemplateFileName+" does not exist.");
		if (fDatasource!=null && !fDatasource.exists())
		  throw new MailMergeException(sDataSourceFileName+" does not exist.");
		Vector vBookmarks=new Vector();
		Vector vBookmarkValues=new Vector();
		if (_htTextBookmarks!=null) {
			Enumeration enumBookmarks=_htTextBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				String sValue=(String)_htTextBookmarks.get(sBookmark);
				vBookmarks.addElement("MMBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMBOOKMARKVALUE"+i+"="+sValue);
				i++;
			}
		}
		if (_htPictureBookmarks!=null) {
			Enumeration enumBookmarks=_htPictureBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htPictureBookmarks.get(sBookmark);
				vBookmarks.addElement("MMPICTUREBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMPICTUREBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		if (_htFileBookmarks!=null) {
			Enumeration enumBookmarks=_htFileBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htFileBookmarks.get(sBookmark);
				vBookmarks.addElement("MMFILEBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMFILEBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		if (_htOLEBookmarks!=null) {
			Enumeration enumBookmarks=_htOLEBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htOLEBookmarks.get(sBookmark);
				vBookmarks.addElement("MMOLEFILEBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMOLEFILEBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		int iStartIndex=fDatasource==null?2:3;
		String[] saCommandArgs=new String[3];
		int iSize=iStartIndex+(vBookmarks.size()*2)+_vExtraEnvironArgs.size();
		String[] saEnvArgs=new String[iSize];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]=sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mMailMerge";
		if (fDatasource!=null)
			saEnvArgs[0]="MMDATASOURCE="+sDataSourceFileName;
		saEnvArgs[iStartIndex-2]="MMPRINTFILE="+sPrintFileName;
		saEnvArgs[iStartIndex-1]="MMTEMPLATE="+sTemplateFileName;
		int k=iStartIndex;
		for (;k<iSize-_vExtraEnvironArgs.size();k+=2) {
			saEnvArgs[k]=(String)vBookmarks.elementAt((k-iStartIndex)/2);
			saEnvArgs[k+1]=(String)vBookmarkValues.elementAt((k-iStartIndex)/2);
		}
		for (;k<iSize;k++) {
			saEnvArgs[k]=(String)_vExtraEnvironArgs.elementAt(k-(iSize-_vExtraEnvironArgs.size()));
		} 
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
	public void run() {
			try {
				_process=Runtime.getRuntime().exec(_saCommandArgs,_saEnvironArgs);
				_process.waitFor();
				_process=null;
			}
			catch (Exception e) {
				StringBuffer sbCommand=new StringBuffer();
				for (int i=0;i<_saCommandArgs.length;i++)
				  sbCommand.append(_saCommandArgs[i]+" ");
				_mme= new MailMergeException("Unable to execute "+sbCommand.toString()+" for processing: "+e.getMessage());
				if (_process!=null) 
					_process.destroy();
			}
			_wakeupthread.interrupt();
		}
/**
 * Saves to a File a Template File after Mail Merging with a Datasource.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sTemplateFileName java.lang.String Name of the word template file
 * @param sDataSourceFileName java.lang.String Name of the Data Source File usually a .csv file
 * @param sSaveAsFileName java.lang.String Name of the Saved File.
 */
	public void saveAs(String sTemplateFileName,String sDataSourceFileName,String sSaveAsFileName) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonTemplate=new File(sWinWordTemplatePath+"Salmon.dot");
		if (!fSalmonTemplate.exists()) 
			throw new MailMergeException("Salmon.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		File fTemplate=new File(sTemplateFileName);
		File fDatasource=null;
		if (sDataSourceFileName!=null && !sDataSourceFileName.trim().equals(""))
			fDatasource=new File(sDataSourceFileName);
		if (!fTemplate.exists())
		  throw new MailMergeException(sTemplateFileName+" does not exist.");
		if (fDatasource!=null && !fDatasource.exists())
		  throw new MailMergeException(sDataSourceFileName+" does not exist.");
		Vector vBookmarks=new Vector();
		Vector vBookmarkValues=new Vector();
		if (_htTextBookmarks!=null) {
			Enumeration enumBookmarks=_htTextBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				String sValue=(String)_htTextBookmarks.get(sBookmark);
				vBookmarks.addElement("MMBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMBOOKMARKVALUE"+i+"="+sValue);
				i++;
			}
		}
		if (_htPictureBookmarks!=null) {
			Enumeration enumBookmarks=_htPictureBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htPictureBookmarks.get(sBookmark);
				vBookmarks.addElement("MMPICTUREBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMPICTUREBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		if (_htFileBookmarks!=null) {
			Enumeration enumBookmarks=_htFileBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htFileBookmarks.get(sBookmark);
				vBookmarks.addElement("MMFILEBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMFILEBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		if (_htOLEBookmarks!=null) {
			Enumeration enumBookmarks=_htOLEBookmarks.keys();
			int i=1;
			while (enumBookmarks.hasMoreElements()) {
				String sBookmark=(String)enumBookmarks.nextElement();
				File fValue=(File)_htOLEBookmarks.get(sBookmark);
				vBookmarks.addElement("MMOLEFILEBOOKMARK"+i+"="+sBookmark);
				vBookmarkValues.addElement("MMOLEFILEBOOKMARKVALUE"+i+"="+fValue.getAbsolutePath());
				i++;
			}
		}
		int iStartIndex=fDatasource==null?2:3;
		String[] saCommandArgs=new String[3];
		int iSize=iStartIndex+(vBookmarks.size()*2)+_vExtraEnvironArgs.size();
		String[] saEnvArgs=new String[iSize];
		saCommandArgs[0]="\""+sWinWordPath+"winword\"";
		saCommandArgs[1]= sWinWordTemplatePath+"Salmon.dot";
		saCommandArgs[2]="-mMailMerge";
		if (fDatasource!=null)
			saEnvArgs[0]="MMDATASOURCE="+sDataSourceFileName;
		saEnvArgs[iStartIndex-2]="MMSAVEAS="+sSaveAsFileName;
		saEnvArgs[iStartIndex-1]="MMTEMPLATE="+sTemplateFileName;
		int k=iStartIndex;
		for (;k<iSize-_vExtraEnvironArgs.size();k+=2) {
			saEnvArgs[k]=(String)vBookmarks.elementAt((k-iStartIndex)/2);
			saEnvArgs[k+1]=(String)vBookmarkValues.elementAt((k-iStartIndex)/2);
		}
		for (;k<iSize;k++) {
			saEnvArgs[k]=(String)_vExtraEnvironArgs.elementAt(k-(iSize-_vExtraEnvironArgs.size()));
		} 
		MailMerge mm=new MailMerge(saCommandArgs,saEnvArgs,Thread.currentThread());
		Thread thmm=new Thread(mm);
		thmm.start();
		boolean bSuccessful=false;
		try {
			Thread.sleep(60000);	
		}
		catch (Exception e) {
			if (mm.getMailMergeException()==null)
				bSuccessful=true;
		}
		if (!bSuccessful) {
			Process pMm=mm.getMailMergeProcess();
			if (pMm!=null) 
			  pMm.destroy();
			throw mm.getMailMergeException();
		}
	}
/**
 * Indicates that if the document is view that it should auto print.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param bAutoPrint boolean If true document will automatically print if viewed.
 */
	public void setAutoPrint(boolean bAutoPrint) throws MailMergeException {
		String sWinWordTemplatePath=Props.getSystemProps().getProperty(Props.WINWORD_TEMPLATE_PATH);
		String sWinWordPath=Props.getSystemProps().getProperty(Props.WINWORD_PATH);
		if (sWinWordTemplatePath==null)
			throw new MailMergeException(Props.WINWORD_TEMPLATE_PATH+" is missing from properties file.");
		if (sWinWordPath==null)
			throw new MailMergeException(Props.WINWORD_PATH+" is missing from properties file.");
		if (!sWinWordPath.endsWith(File.separator))
		  sWinWordPath+=File.separator;
		if (!sWinWordTemplatePath.endsWith(File.separator))
		  sWinWordTemplatePath+=File.separator;
		File fSalmonAutoMacrosTemplate=new File(sWinWordTemplatePath+"SalmonAutoMacros.dot");
		if (!fSalmonAutoMacrosTemplate.exists()) 
			throw new MailMergeException("SalmonAutoMacros.dot was not deployed to winword template directory specified by "+Props.WINWORD_TEMPLATE_PATH+" in properties file.");
		if (!bAutoPrint) {
		  int iIndex=_vExtraEnvironArgs.indexOf("MMAUTOPRINT="+sWinWordTemplatePath+"SalmonAutoMacros.dot");
		  if (iIndex>=0)
		    _vExtraEnvironArgs.remove("MMAUTOPRINT="+sWinWordTemplatePath+"SalmonAutoMacros.dot");
		  return;
		}
		_vExtraEnvironArgs.addElement("MMAUTOPRINT="+sWinWordTemplatePath+"SalmonAutoMacros.dot");
	}
/**
 * Sets the Background of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fBackground java.io.File A File pointing to a background for the document
 */
	public void setBackground(File fBackground) throws MailMergeException {
		if (!fBackground.exists())
		  throw new MailMergeException("File "+fBackground.getAbsolutePath()+" does not exist.");
		_fBackground=fBackground;
		_vExtraEnvironArgs.addElement("MMBACKGROUND="+fBackground.getAbsolutePath());
	}
/**
 * Sets the Bottom Margin of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fBottomMargin float Bottom Margin in Inches
 */
	public void setBottomMargin(float fBottomMargin) {
		_fBottomMargin=new Float(fBottomMargin);
		_vExtraEnvironArgs.addElement("MMBOTTOMMARGIN="+fBottomMargin);
	}
/**
 * Sets the Footer Distance of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fFooterDistance float Footer Distance in Inches
 */
	public void setFooterDistance(float fFooterDistance) {
		_fFooterDistance=new Float(fFooterDistance);
		_vExtraEnvironArgs.addElement("MMFOOTERDISTANCE="+fFooterDistance);
	}
/**
 * Sets the Gutter of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fGutter float Gutter in Inches
 */
	public void setGutter(float fGutter) {
		_fGutter=new Float(fGutter);
		_vExtraEnvironArgs.addElement("MMGUTTER="+fGutter);
	}
/**
 * Sets the Header Distance of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fHeaderDistance float Header Distance in Inches
 */
	public void setHeaderDistance(float fHeaderDistance) {
		_fHeaderDistance=new Float(fHeaderDistance);
		_vExtraEnvironArgs.addElement("MMHEADERDISTANCE="+fHeaderDistance);
	}
/**
 * Sets the Left Margin of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fLeftMargin float Left Margin in Inches
 */
	public void setLeftMargin(float fLeftMargin) {
		_fLeftMargin=new Float(fLeftMargin);
		_vExtraEnvironArgs.addElement("MMLEFTMARGIN="+fLeftMargin);
	}
/**
 * Sets the Printer for a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param sPrinter java.lang.String A String representing the printer to print to.
 */
	public void setPrinter(String sPrinter) {
		_sPrinter=sPrinter;
		_vExtraEnvironArgs.addElement("MMPRINTER="+sPrinter);
	}
/**
 * Sets the Right Margin of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fRightMargin float Right Margin in Inches
 */
	public void setRightMargin(float fRightMargin) {
		_fRightMargin=new Float(fRightMargin);
		_vExtraEnvironArgs.addElement("MMRIGHTMARGIN="+fRightMargin);
	}
/**
 * Sets the Top Margin of a Word Document.
 * Creation date: (3/15/02 10:44:02 AM)
 * @param fTopMargin float Top Margin in Inches
 */
	public void setTopMargin(float fTopMargin) {
		_fTopMargin=new Float(fTopMargin);
		_vExtraEnvironArgs.addElement("MMTOPMARGIN="+fTopMargin);
	}
}
