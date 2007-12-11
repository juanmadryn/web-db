package com.salmonllc.remote.server;
/**
 * Created by IntelliJ IDEA.
 * User: Fred Cahill
 * Date: Sep 29, 2004
 * Time: 9:24:36 AM
 * To change this template use Options | File Templates.
 */
/**
 * This interface is used to describe a policy used by the RemoteReflector servlet to see if execution is allowed.
 * The RemoteReflectionSecurityManager class is the default policy for RemoteReflector.
 * Use the setSecurityPolicy method in RemoteReflector to specify a different Security Policy for Remote Reflection.
 * By Default only classes which implement Reflect are allowed to be executed.
 */
public interface RemoteReflectionSecurityPolicy {
    public boolean isInstantiationAllowed(Class cl);
    public boolean isMethodCallAllowed(Object obj,String sMethod);
}
