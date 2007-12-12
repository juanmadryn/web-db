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
package com.salmonllc.email;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/email/SMTP.java $
//$Author: Dan $ 
//$Revision: 33 $ 
//$Modtime: 8/24/04 4:33p $ 
/////////////////////////

import java.io.*;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.salmonllc.util.ApplicationContext;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
 /**
  * This class abstracts and simplifies the process of sending email to via an SMTP server
  */
public class SMTP implements Runnable {
    private static final int _DefaultPort = 25;
    private String _SMTPHost;
    private int _SMTPPort;
    private Vector _vRecipients = new Vector();

    // BYD - 6/30/03 - Added functionality to enable user to send e-amil to CC and BCC
    private Vector _vCCRecipients = new Vector();
    private Vector _vBCCRecipients = new Vector();
    public static final int iTO = 0;
    public static final int iCC = 1;
    public static final int iBCC = 2;
    // --

    private Vector _vFileNames = new Vector();
    private String _SenderHost;
    private String _From = null;
    private String _sException = null;
    private boolean _MailSent = false;
    private String _Body = null;
    private String _EndDataString = "\r\n.\r\n";
    private String _Subject = null;
    private String _OpenConnResponse = "220";
    private String _OkResponse = "250";
    private String _DataResponse = "354";
    private String _CloseConnResponse = "221";
    private String _ErrorResponse = "500";
    private String _BodyMimeType = "text/plain";
    private String _TimeStampFormat = "EEE, dd MMM yyyy hh:mm:ss (z)";

    private static final String _CRLF = "\r\n";
    public java.util.Properties _fieldSMTPServers;
    public javax.mail.Session _mailSession;
    public Thread _interruptThread;

     /*Claudio Pi (04-02-2003)*/
     /*Use SMTP Authentication ?*/
     private boolean _SMTPAuth=false;
     /*SMTP Authentication Username*/
     private String _SMTPAuthUser=null;
     /*SMTP Authentication Password*/
     private String _SMTPAuthPass=null;

/**
 * Licensed Material - Property of Salmon LLC
 * (C) Copyright Salmon LLC. 1999 - All Rights Reserved
 * For more information go to www.salmonllc.com
 * 
 * *************************************************************************
 * DISCLAIMER:
 * The following code has been created by Salmon LLC. The code is provided
 * 'AS IS' , without warranty of any kind unless covered in another agreement
 * between your corporation and Salmon LLC.  Salmon LLC shall not be liable
 * for any damages arising out of your use of this, even if they have been
 * advised of the possibility of such damages.
 * *************************************************************************
 * 
 * This create an SMTP object using the Specified Port iPort.
 * @param sHost The Hostname of the SMTP server
 * @param iPort The Port Number the SMTP server listens on.
 */
/**
 * This create an SMTP object using the Standard Port 25.
 * @param sHost The Hostname of the SMTP server.
 */
public SMTP(String sHost) {
    this(sHost, _DefaultPort);
}
/**
 * This create an SMTP object using the Specified Port iPort.
 * @param sHost The Hostname of the SMTP server
 * @param iPort The Port Number the SMTP server listens on.
 */
public SMTP(String sHost, int iPort) {

    Properties props = new Properties();
    props.put("Server1", sHost);
    props.put("Port1", String.valueOf(iPort));
    setSMTPServers(props);
}

     /**
      * Claudio Pi (04-02-2003)
      * This method creates an SMTP object using the specified host, port, authentication username and password.
      * @param sHost The Hostname of the SMTP server
      * @param iPort The Port Number the SMTP server listens on.
      * @param smtpAuthUser The username to use for authentication.
      * @param smtpAuthPass The password to use for authentication.
      */

     public SMTP(String sHost, int iPort, String smtpAuthUser,
                 String smtpAuthPass) {
         this(sHost, iPort);
         this._SMTPAuth=true;
         this._SMTPAuthUser=smtpAuthUser;
         this._SMTPAuthPass=smtpAuthPass;
     }

     /**
      * Claudio Pi (04-02-2003)
      * This method creates an SMTP object using the specified host, authentication username and password.
      * @param sHost The Hostname of the SMTP server
      * @param smtpAuthUser The username to use for authentication.
      * @param smtpAuthPass The password to use for authentication.
      */
     public SMTP(String sHost,String smtpAuthUser,
                 String smtpAuthPass) {
         this(sHost);
         this._SMTPAuth=true;
         this._SMTPAuthUser=smtpAuthUser;
         this._SMTPAuthPass=smtpAuthPass;
     }


/**
 * This create an SMTP object using the Informations from a Properties object.
 * @param props java.util.Properties The Properties object to specify the SMTP settings.
 */
public SMTP(Properties props) {

    setSMTPServers(props);
} 

/*
 * This method is Private to the SMTP object for accepting responses from the SMTP server.
 * @param br This is the BufferInputReader of the Inputstream from the SMTP server.
 * @param sAccept This is the String representing what you sholg get back from the SMTP server if successful.
private void acceptResponse(java.io.BufferedInputStream bis, String sAccept)
    throws java.io.IOException, SMTPException {
    int len = 256;
 
    byte[] bStream = new byte[len];
    String sLine = "";
    while (true) {
        int i = bis.read(bStream, 0, len);
        //	System.err.println("read "+i+" bytes");
        sLine = new String(bStream, 0, i);
        //	System.err.println(sLine);
        if (sLine.startsWith(sAccept))
            return;
        else if (sLine.startsWith(_ErrorResponse)) {
            int iQuote = sLine.indexOf('\'');
            String sTmp = sLine.substring(iQuote + 1);
            iQuote = sTmp.indexOf('\'');
            if (iQuote != 0)
                break;
        }
        else
            break;
    }
    _sException = "SMTP Server Invalid Response: " + sLine;
    throw new SMTPException(_sException);
}*/

/**
 * This method attaches to the SMTP object for use in sending mail to.
 * @param sFile A Full Specified Path to a File.
 */
public void addFile(String sFile) throws SMTPException {
    File fFile = new File(sFile);
    if (fFile.exists())
        _vFileNames.addElement(sFile);
    else
        throw new SMTPException(sFile + " is not a valid file.");
}

/**
 * This method adds Recipients to the SMTP object for use in sending mail to.
 * @param sAddress A Valid Email of a Recipient.
 */
 public void addRecipient(String sAddress) throws SMTPException {
    addRecipient(sAddress, iTO);
 }

/**
 * This method adds Recipients to the SMTP object for use in sending mail to.
 * @param sAddress A Valid Email of a Recipient.
 */
public void addRecipient(String sAddress, int iRecipientType) throws SMTPException {
    if (validEMailAddress(sAddress))
    {
        switch(iRecipientType)
        {
            default:
            case iTO:
                _vRecipients.addElement(sAddress.trim());
                break;

            case iCC:
                _vCCRecipients.addElement(sAddress.trim());
                break;

            case iBCC:
                _vBCCRecipients.addElement(sAddress.trim());
                break;
        }
    }
    else
        throw new SMTPException(sAddress + " is an Invalid E-Mail Address.");
}

/**
 * This method adds Recipients to the SMTP object for use in sending mail to.
 * @param sCommaSepRecipients A Comma Separated List of valid E-Mail Addresses of Recipients.
 */
public void addRecipients(String sCommaSepRecipients) throws SMTPException {
    addRecipients(sCommaSepRecipients, iTO);
}

/**
 * This method adds Recipients to the SMTP object for use in sending mail to.
 * @param sCommaSepRecipients A Comma Separated List of valid E-Mail Addresses of Recipients.
 * @param iRecipientType int - the type of recipient (TO, CC or BCC)
 */
public void addRecipients(String sCommaSepRecipients, int iRecipientType) throws SMTPException {
    int iCommaPos = sCommaSepRecipients.indexOf(',');
    if (iCommaPos != -1) {
        String sRecipients = sCommaSepRecipients;
        while (iCommaPos != -1) {
            String sRecipient = sRecipients.substring(0, iCommaPos);
            addRecipient(sRecipient, iRecipientType);
            sRecipients = sRecipients.substring(iCommaPos + 1);
            iCommaPos = sRecipients.indexOf(',');
        }
        addRecipient(sRecipients, iRecipientType);
    }
    else
        addRecipient(sCommaSepRecipients, iRecipientType);
}

/**
* Returns the Properties object containing the SMTP settings
* @return java.util.Properties A Properties object with the SMTP settings
*/
public java.util.Properties getSMTPServers() {
    return _fieldSMTPServers;
}
/**
 * This is a main program for testing the SMTP class.
 * @param args java.lang.String[] Parameters for the SMTP test.
 */
public static void main(String args[]) {

    if (args.length < 2) {
        System.out.println("Must have two parameters, host & recipient E-Mail");
        return;
    }
    try {
        SMTP smtp;
        smtp = new SMTP(args[0]);
        smtp.setSubject("Test EMail");
        smtp.setFromAddress("Test@salmonllc.com");
        smtp.addRecipient(args[1]);
        smtp.setBodyMimeType("text/plain");
        smtp.setBody("<html><body><b>deepak</b></body></html>");
        if (args.length > 2)
            smtp.addFile(args[2]);
        smtp.sendMail();
    }
    catch (Exception e) {
        System.out.println(e);
    }

}
/**
 * This Method does the Coummunication with the SMTP server and sends the mail.
 * This Method should not be called by the developer, but only from calling sendMail.
 */
public void run() {
    String sHost = "";
    String sPort = "";
    boolean bSendMail = false;

    for (int j = 0; j < getSMTPServers().size(); j++) {
        if (bSendMail)
            break;

        sHost = getSMTPServers().getProperty("Server" + (j + 1));

        sPort = getSMTPServers().getProperty("Port" + (j + 1));
        if (sPort == null)
            sPort = String.valueOf(_DefaultPort);

        try {
            Properties props = System.getProperties();
            if (sHost != null) {
                /*Claudio Pi (04-02-2003)*/
                if (_SMTPAuth) {
                    props.put("mail.smtp.auth", "true");
                }

                props.put("mail.smtp.host", sHost);
                props.put("mail.smtp.port", sPort);
                _mailSession = Session.getDefaultInstance(props, null);
            }
            else
                break;

            // BYD - 6/30/03 - If there are no recipient in the TO:, CC: AND BCC: fields then return
            if ((_vRecipients.size() <= 0) && (_vCCRecipients.size() <= 0) && (_vBCCRecipients.size() <= 0))
                break;

            MessageLog.writeDebugMessage("SMTP HOST: " + sHost, this);
            MessageLog.writeDebugMessage("SMTP PORT: " + sPort, this);
            Message mailMessage = new MimeMessage(_mailSession);

            // Set the Form Addresss
            InternetAddress addressFrom = new InternetAddress();
            addressFrom.setAddress(_From);
            mailMessage.setFrom(addressFrom);

            // BYD - 7/01/03 - Concatenate all recipients for use with the transport sendMessage method
            InternetAddress[] recpAllAddresses = new InternetAddress[_vRecipients.size()+_vCCRecipients.size()+_vBCCRecipients.size()];
            int iAddr = 0;

            // BYD - 6/30/03 - Set the recipients
            InternetAddress[] recpAddress = new InternetAddress[_vRecipients.size()];
            if (_vRecipients.size() > 0)
            {
                for (int i = 0; i < _vRecipients.size(); i++) {
                    recpAddress[i] = new InternetAddress((String) _vRecipients.elementAt(i));
                    recpAllAddresses[iAddr++] = recpAddress[i];
                    MessageLog.writeDebugMessage(
                        "Recipient " + i + ": " + (String) _vRecipients.elementAt(i),
                        this);
                }
                mailMessage.setRecipients(Message.RecipientType.TO, recpAddress);
            }

            // BYD - 6/30/03 - Set the CC recipients
            InternetAddress[] recpCCAddress = new InternetAddress[_vCCRecipients.size()];
            if (_vCCRecipients.size() > 0)
            {
                for (int i = 0; i < _vCCRecipients.size(); i++) {
                    recpCCAddress[i] = new InternetAddress((String) _vCCRecipients.elementAt(i));
                    recpAllAddresses[iAddr++] = recpCCAddress[i];
                    MessageLog.writeDebugMessage(
                        "CC Recipient " + i + ": " + (String) _vCCRecipients.elementAt(i),
                        this);
                }
                mailMessage.setRecipients(Message.RecipientType.CC, recpCCAddress);
            }

            // BYD - 6/30/03 - Set the BCC recipients
            InternetAddress[] recpBCCAddress = new InternetAddress[_vBCCRecipients.size()];
            if (_vBCCRecipients.size() > 0)
            {
                for (int i = 0; i < _vBCCRecipients.size(); i++) {
                    recpBCCAddress[i] = new InternetAddress((String) _vBCCRecipients.elementAt(i));
                    recpAllAddresses[iAddr++] = recpBCCAddress[i];
                    MessageLog.writeDebugMessage(
                        "BCC Recipient " + i + ": " + (String) _vBCCRecipients.elementAt(i),
                        this);
                }
                mailMessage.setRecipients(Message.RecipientType.BCC, recpBCCAddress);
            }

            mailMessage.setSubject(_Subject);
            mailMessage.setSentDate(new Date(System.currentTimeMillis()));

            // Add the attachments if any
            if (_vFileNames.size() > 0) {

                Multipart multipart = new MimeMultipart();

                MimeBodyPart mbpBody = new MimeBodyPart();
                mbpBody.setContent(_Body, _BodyMimeType);
                multipart.addBodyPart(mbpBody);

                for (int i = 0; i < _vFileNames.size(); i++) {
                    MimeBodyPart mbp = new MimeBodyPart();

                    // attach the file to the message
                    FileDataSource fds = new FileDataSource((String) _vFileNames.elementAt(i));
                    mbp.setDataHandler(new DataHandler(fds));
                    mbp.setFileName(fds.getName());

                    multipart.addBodyPart(mbp);
                }

                mailMessage.setContent(multipart);
            }
            else {
                mailMessage.setContent(_Body, _BodyMimeType);
            }
            /*Claudio Pi (04-02-2003)*/
            Transport tr=_mailSession.getTransport("smtp");
            if (_SMTPAuth) {
                tr.connect(sHost,Util.stringToInt(sPort), _SMTPAuthUser,_SMTPAuthPass);
            }
            else {
                tr.connect();
            }

            tr.sendMessage(mailMessage,recpAllAddresses);

            //Transport.send(mailMessage);
            bSendMail = true;
        }
        catch (Exception e) {
            bSendMail = false;
            _sException = e.getMessage();
            MessageLog.writeErrorMessage("run() for Server " + sHost, e, this);
        }
        _MailSent = bSendMail;
        if (_interruptThread != null)
            _interruptThread.interrupt();
    }
}
/**
 * This Method starts the Thread which Communicates with the SMTP server.
 * This is the Method to be called when sending mail.
 */
public void sendMail() throws SMTPException {
    if (_From == null)
        throw new SMTPException("EMail must contain a From Address");
    if ((_vRecipients.size() == 0) && (_vCCRecipients.size() == 0) && (_vBCCRecipients.size() == 0))
        throw new SMTPException("EMail must contain Recipients");
    if (_Body == null)
        throw new SMTPException("EMail must contain a Body");
    Thread t = ApplicationContext.createThreadWithContextClone(this);
    t.start();
}
/**
 * This method sets the body of the mail message.
 * @param sBody A String representing the body of the message.
 */
public void setBody(String sBody) {
    String sTemp = sBody;
    int iNewLine = sTemp.indexOf('\n');
    int offset = 0;
    while (iNewLine != -1) {
        if (iNewLine - 1 >= 0) {
            if (sTemp.charAt(iNewLine - 1) != '\r') {
                sTemp = sTemp.substring(0, iNewLine) + _CRLF + sTemp.substring(iNewLine + 1);
                offset = iNewLine + 2;
            }
            else
                offset = iNewLine + 1;
        }
        else {
            sTemp = _CRLF + sTemp.substring(1);
            offset = 2;
        }
        iNewLine = sTemp.indexOf('\n', offset);
    }
    _Body = sTemp;
}
/** 
* Sets the mime type of the body content of the e-mail
* @param sMimeType java.lang.String MimeType of the body of the e-mail.
*/
public void setBodyMimeType(String sMimeType) {
    _BodyMimeType = sMimeType;
}
/**
 * Sets the Close Response of the SMTP protocol.
 * @param sClose java.lang.String The Close Response of the SMTP protocol
 */
public void setCloseResponse(String sClose) {
    _CloseConnResponse = sClose;
}
/**
 * Sets the Data Response of the SMTP protocol.
 * @param sData java.lang.String The Data Response of the SMTP protocol
 */
public void setDataResponse(String sData) {
    _DataResponse = sData;
}
/**
 * This method sets the Ending Character String to inicate the end of the message body.
 * @param sEndDataString This String Indicates the end of the Body of Message which the server is Expecting.
 */
public void setEndDataString(String sEndDataString) {
    _EndDataString = sEndDataString;
}
/**
 * Sets the Error Response of the SMTP protocol.
 * @param sError java.lang.String The Error Response of the SMTP protocol
 */
public void setErrorResponse(String sError) {
    _ErrorResponse = sError;
}
/**
 * This method sets the EMail address of the person the message is from.
 * @param sAddress A valid EMail Address.
 */
public void setFromAddress(String sAddress) throws SMTPException {
    if (validEMailAddress(sAddress))
        _From = sAddress;
    else
        throw new SMTPException(sAddress + " is an Invalid E-Mail Address.");
}
/**
 * This sets the Hostname of the SMTP Server.
 * @param sHost The hostname of the SMTP server
 */
public void setHost(String sHost) {
    _SMTPHost = sHost;
}
/**
 * Sets the OK Response of the SMTP protocol.
 * @param sOk java.lang.String The OK Response of the SMTP protocol
 */
public void setOkResponse(String sOk) {
    _OkResponse = sOk;
}
/**
 * Sets the Open Response of the SMTP protocol.
 * @param sOpen java.lang.String The Open Response of the SMTP protocol
 */
public void setOpenResponse(String sOpen) {
    _OpenConnResponse = sOpen;
}
/**
 * This sets the port number of the SMTP server.
 * @param iPort The port of the SMTP Server.
 */
public void setPort(int iPort) {
    _SMTPPort = iPort;
}
/**
 * This method sets the Sender Host of the message.
 * @param sSenderHost A Hostname representing the sending machine.
 */
public void setSenderHost(String sSenderHost) {
    _SenderHost = sSenderHost;
}
/**
 * Sets the Properties object containing the information about SMTP settings.
 * @param newSMTPServers java.util.Properties Properties Object with the SMTP settings
 */
public void setSMTPServers(java.util.Properties newSMTPServers) {
    _fieldSMTPServers = newSMTPServers;
}
/**
 * Sets the Subject of the E-Mail.
 * @param sSubject java.lang.String The subject of the e-mail
 */
public void setSubject(String sSubject) {
    _Subject = sSubject;
}
/**
 * Sets the TimeStampFormat used in the e-mail.
 * @param sFormat java.lang.String The format of the timestamp for the e-mail
 */
public void setTimeStampFormat(String sFormat) {
    _TimeStampFormat = sFormat;
}
/**
 * This method checks to see if sEmailAddress matches the format for an EMail.
 * @return boolean Returns false if sEMailAddress is not valid.
 * @param sEMailAddress java.lang.String
 */
private boolean validEMailAddress(String sEMailAddress) {
    boolean bRet = false;
    int iAtPos = sEMailAddress.indexOf('@');
    if (iAtPos != -1) {
        String sHost = sEMailAddress.substring(iAtPos + 1);
        if (sHost.indexOf('@') == -1)
            if (sHost.indexOf('.') != -1)
                bRet = true;
    }
    return bRet;
}
/**
 * This method waits iSeconds number of seconds to check to see if mail was sent successfully or not.
 * @return boolean Returns true if Successfully sent.
 */
public boolean waitForMailSendToComplete(int iSeconds) throws SMTPException {
    _interruptThread = Thread.currentThread();
    try {
        Thread.sleep(iSeconds * 1000);
    }
    catch (Exception e) {
    }
    if (_sException != null)
        throw new SMTPException(_sException);
    return _MailSent;
}

     /**
      * fmc 04/03/03.
      * This sets the SMTP Auth User of the SMTP Server.
      * @param sAuthUser The hostname of the SMTP server
      */
     public void setAuthUser(String sAuthUser) {
         _SMTPAuthUser = sAuthUser;
         if (_SMTPAuthUser!=null && !_SMTPAuthUser.equals(""))
           _SMTPAuth=true;
         else
           _SMTPAuth=false;
     }


     /**
      * fmc 04/03/03.
      * This sets the SMTP Auth Password of the SMTP Server.
      * @param sAuthPass The hostname of the SMTP server
      */
     public void setAuthPassword(String sAuthPass) {
         _SMTPAuthPass = sAuthPass;
     }

}
