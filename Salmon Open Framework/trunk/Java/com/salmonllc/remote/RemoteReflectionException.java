package com.salmonllc.remote;
/**
 * Created by IntelliJ IDEA.
 * User: Fred Cahill
 * Date: Sep 14, 2004
 * Time: 11:04:10 AM
 * To change this template use Options | File Templates.
 */
public class RemoteReflectionException extends Exception {

    private Throwable _rootCause;

    /**
     * Constructs an Exception with no specified detail message.
     */
    public RemoteReflectionException() {
        super();
    }

    /**
     * Constructs an Exception with the specified detail message.
     * @param s the detail message.
     */
    public RemoteReflectionException(String s) {
        super(s);
    }

     /**
     * Constructs an Exception with the specified detail message.
     * @param s the detail message.
     */
    public RemoteReflectionException(String s, Throwable root) {
        super(s);
        _rootCause = root;
    }

    /**
     * @return  The root cause of the exception if there is one or null if not
     */

    public Throwable getRootCause() {
        return _rootCause;
    }

}
