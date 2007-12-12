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
package com.salmonllc.secureid;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/secureid/SecureID.java $
//$Author: Dan $ 
//$Revision: 20 $ 
//$Modtime: 6/11/03 4:28p $ 
/////////////////////////

import java.io.*;

/**
 * This class is used to communicate with secureid server, requires sdClient.dll in Path & ACE Client S/W installed.
 */
public class SecureID implements Runnable
{
	
 public final static int ACM_OK=0;
 public final static int ACM_ACCESS_DENIED=1;
 public final static int ACM_NEXT_CODE_REQUIRED=2;
 public final static int ACM_NEW_PIN_REQUIRED=5;
 public final static int ACM_NEW_PIN_ACCEPTED=6;
 public final static int ACM_NEW_PIN_REJECTED=7;
 public final static int ACM_UNABLE_TO_INITIALIZE=100;
 public final static int ACM_BUSY=150;
 private static boolean _isLoaded=false;
 private String sResponse;
 private byte[] baSdClientStruct;
 private int acmRet;
 private int _action;
 private String _username;
 private String _password;
 private String _newpin;
 private String _nexttoken;
 private boolean bRetValue;

/**
 * Constructs a SecureID object for communication with SecureID Server.
 */
 public SecureID() {
   baSdClientStruct=new byte[getSDClientSize()];
  }  
/**
 * Use this method to Authenticate a New Pin with SecureID Server
 * @param sNewPin A Users New Pin to be used with SecureID card.
 */
 public boolean authenticateNewPin(String sNewPin) throws Exception 
  {
   _action=2;
   _newpin=sNewPin;
   Thread t=new Thread(this);
   t.start();
   while(t.isAlive()) Thread.yield();
   return bRetValue;
  }  
/**
 * Use this method to Authenticate a user with SecureID Server
 * @param sUserName A Users SecureID Username.
 * @param sPassCode Concatenation of a Users Pin & Passcode on a SecureID card.
 */
 public  boolean authenticateNewUser(String sUserName,String sPassCode) throws Exception 
  {
   _action=1;
   _username=sUserName;
   _password=sPassCode;
   Thread t=new Thread(this);
   t.start();
   while(t.isAlive()) Thread.yield();
   return bRetValue;

  }  
/**
 * Use this method to Authenticate Next Token with SecureID Server for Next Token mode.
 * @param sPassCode A Users Passcode on a SecureID card.
 */
 public  boolean authenticateNextToken(String sPassCode) throws Exception
   {
   _action=3;
   _nexttoken=sPassCode;
   Thread t=new Thread(this);
   t.start();
   while(t.isAlive()) Thread.yield();
   return bRetValue;
   }   
/**
 * Use this method to Authenticate a New Pin with SecureID Server
 * @param sNewPin A Users New Pin to be used with SecureID card.
 */
 private static synchronized boolean authorizeNewPin(SecureID sid,String sNewPin) 
  {
   boolean bRetValue=false;
   int acmRet=setNewPin(sNewPin,(byte)0,sid.getSDClientStructure());
   sid.setReturnCode(acmRet);
   switch(acmRet) {
	 case ACM_NEW_PIN_ACCEPTED: 
	   sid.setResponse("New Pin Accepted");
	   System.out.println(sid.getResponse());
	   bRetValue=true;
	   close();
	   break;
	 case ACM_NEW_PIN_REJECTED:
	   sid.setResponse("New Pin Rejected");
	   System.out.println(sid.getResponse());
	   close();
	   break;
	}
   return bRetValue;
  }  
/**
 * Use this method to Authenticate a user with SecureID Server
 * @param sUserName A Users SecureID Username.
 * @param sPassCode Concatenation of a Users Pin & Passcode on a SecureID card.
 */
 private static synchronized boolean authorizeNewUser(SecureID sid,String sUserName,String sPassCode) 
  {
   boolean bRetValue=false;
   int acmRet=-1;
   try {
	 System.err.println("WILL CALL THE NATIVES " );
	 acmRet=initSecureID(sid.getSDClientStructure());
	 System.err.println("In SecureID.authenticateNewUser after initSecureID return code  ==== " + acmRet);
	}
   catch (Error err) {
	 System.err.println(err);
	 //throw (new Exception(err.toString()));
	}
   if (acmRet!=ACM_OK) {
	 System.err.println("Unable to Initialize Client/Server Communication. Returned "+acmRet);
	 sid.setReturnCode(acmRet);
	 return false;
	 //throw (new Exception("Unable to Initialize Client/Server Communication. Returned "+acmRet));
	}

   acmRet=-1;
   
   try {
	 System.err.println("In SecureID.authenticateNewUser before checkSecureID");
	 acmRet=checkSecureID(sPassCode,sUserName,sid.getSDClientStructure());
	 System.err.println("In SecureID.authenticateNewUser after checkSecureID return code  ==== " + acmRet);
	}
   catch (Error err) {
	 System.out.println(err);
	}
   sid.setReturnCode(acmRet);
   switch(acmRet) {
	 case ACM_OK: 
	   sid.setResponse("User Authenticated");
	   System.out.println(sid.getResponse());
	   bRetValue=true;
	   close();
	   break;
	 case ACM_ACCESS_DENIED:
	   sid.setResponse("User Denied");
	   System.out.println(sid.getResponse());
	   close();
	   break;
	 case ACM_NEXT_CODE_REQUIRED:
	   sid.setResponse("User Requires New PassCode");
	   System.out.println(sid.getResponse());
	   break;
	 case ACM_NEW_PIN_REQUIRED:
	   sid.setResponse("User Requires New Pin");
	   System.out.println(sid.getResponse());
	   break;
	 default: 
	   sid.setResponse("Unknown Response");
	   System.out.println(sid.getResponse());
	   close();
	   break;
	}
   return bRetValue;
  }  
/**
 * Use this method to Authenticate Next Token with SecureID Server for Next Token mode.
 * @param sPassCode A Users Passcode on a SecureID card.
 */
 private static synchronized boolean authorizeNextToken(SecureID sid,String sPassCode) 
   {
	boolean bRetValue=false;
	int acmRet=-1;
	try {
	  acmRet=checkNextToken(sPassCode,sid.getSDClientStructure());
	 }
	catch (Error err) {
	  System.out.println(err);
	 }
	sid.setReturnCode(acmRet);
	switch(acmRet) {
	  case ACM_OK: 
		sid.setResponse("User Authenticated");
		System.out.println(sid.getResponse());
		bRetValue=true;
		close();
		break;
	  case ACM_ACCESS_DENIED:
		sid.setResponse("User Denied");
		System.out.println(sid.getResponse());
		close();
		break;
	  default: 
	    sid.setResponse("Unknown Response");
	    System.out.println(sid.getResponse());
	    close();
	    break;
	 }
	return bRetValue;
   }   
 private static native int checkNextToken(String nextcode,byte[] sdclient); 
 private static native int checkSecureID(String passcode,String username,byte[] sdclient); 
/**
 * Use this method to end Communication with SecureID Server. Normally at end of Application.
 */
 private static native void close(); 
/**
 * This method will return the return code from the SecureID Server from executing the above methods.
 */
 public int getAcmReturnCode() {
   return acmRet;
  }  
/**
 * This method will return the response of the above methods.
 */
 public String getResponse() {
   return sResponse;
  }  
 private static native int getSDClientSize(); 
 private byte[] getSDClientStructure() {
   return baSdClientStruct;
 } 
 private static native void initialize(); 
 private static native int initSecureID(byte[] sdclient); 
 /**
  * Returns whether the Secure ID dll is loaded or not.
  * @return boolean Indicates whether Secure ID dll is loaded.
 */
 public static boolean isLoaded() {
	 return _isLoaded;
 } 
 /**
  * This method specifies the JNI Secure ID dll to use.
  * @param sLibrary The name of the dll to be loaded.
  */
 public static synchronized void loadJNILibrary(String sLibrary) {	
   try {
	 System.err.println("Start to load the library");
	 System.loadLibrary(sLibrary);
	 initialize();
	 System.err.println("Library loaded");
	 _isLoaded=true;
	}
   catch (Error err) {
	 System.out.println(err);
	}
  }  
/**
 * This is a main method for testing purposes. Recommended to leave in class.
 */
 public static void main(String args[]) {
   try {
	   SecureID.loadJNILibrary("sdClient");
	 SecureID sd=new SecureID();
	 SecureID sd2=new SecureID();
	 String sResponse;
	 int acmRet=999;
	 sd.authenticateNewUser(args[0],args[1]);
	  if ( args.length > 3)
	 {
		 if (args[2]!=null && args[3]!=null)
		   sd2.authenticateNewUser(args[2],args[3]);
	 }
	 acmRet=sd.getAcmReturnCode();
	 switch(acmRet) {
	   case SecureID.ACM_OK: 
		 sResponse="User Authenticated";
	 System.out.println(sResponse);
	 break;
	   case SecureID.ACM_ACCESS_DENIED:
	 sResponse="User Denied";
	 System.out.println(sResponse);
	 break;
	   case SecureID.ACM_NEXT_CODE_REQUIRED:
	 sResponse="User Requires New PassCode";
	 System.out.println(sResponse);
		 System.out.print("Enter New PassCode: ");
		 InputStreamReader isr=new InputStreamReader(System.in);
		 BufferedReader br=new BufferedReader(isr);
		 String sPassCode=br.readLine();
		 sd.authenticateNextToken(sPassCode);
		 acmRet=sd.getAcmReturnCode();
		 br.close();
		 isr.close();
   	 break;
	   case SecureID.ACM_NEW_PIN_REQUIRED:
		 sResponse="User Requires New Pin";
	 System.out.println(sResponse);
	 System.out.print("Enter New Pin: ");
	 isr=new InputStreamReader(System.in);
		 br=new BufferedReader(isr);
		 String sPin=br.readLine();
		 sd.authenticateNewPin(sPin);
		 acmRet=sd.getAcmReturnCode();
		 br.close();
		 isr.close();
	 break;
	   default: 
		 sResponse="Unknown Response "+acmRet;
		 System.out.println(sResponse);
		 break;
	  }
	 sResponse="ACM Return Code "+acmRet;
	 System.out.println(sResponse);
	}
   catch (Exception e) {
	 System.out.println("Exception: "+e);
	}
   catch (Error err) {
	 System.out.println(err);
	}
	
  }    
 public void run() {
   switch(_action) {
	   case 1: bRetValue=authorizeNewUser(this,_username,_password);
	           break;
	   case 2: bRetValue=authorizeNewPin(this,_newpin);
	           break;
	   case 3: bRetValue=authorizeNextToken(this,_nexttoken);
	           break;
   }      
	 System.err.println("Thread ended");
	 }

 private static native int setNewPin(String pin,byte canceled,byte[] sdclient); 
 private void setResponse(String sResponse) {
	 this.sResponse=sResponse;
 } 
 private void setReturnCode(int retCode) {
   acmRet=retCode;
 } 
}
