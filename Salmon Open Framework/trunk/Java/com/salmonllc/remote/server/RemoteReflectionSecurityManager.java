package com.salmonllc.remote.server;

import com.salmonllc.remote.Reflect;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Fred Cahill
 * Date: Sep 29, 2004
 * Time: 9:27:06 AM
 * To change this template use Options | File Templates.
 */
/*
 * This class is the default RemoteReflectionSecurityPolicy instance assigned to RemoteReflector servlet.
 */
public class RemoteReflectionSecurityManager implements RemoteReflectionSecurityPolicy {

    private class ClassMethod {
        String _cls;
        String _method;

        ClassMethod(String sClass,String sMethod) {
          _cls=sClass;
          _method=sMethod;
        }

        public boolean equals(Object obj) {
            if (obj!=null && obj instanceof ClassMethod) {
                return (((ClassMethod)obj).getClassName().equals(_cls) && ((ClassMethod)obj).getMethod().equals(_method));
            }
            return false;
        }

        public String getClassName() {
            return _cls;
        }

        public String getMethod() {
            return _method;
        }
    }

    private Vector vAllowedClasses=new Vector();
    private Vector vAllowedClassesMethods=new Vector();

    /*
     * Checks to see if Instantiation is allowed for the passed class.
     * @param cl java.lang.Class The class to check to see if allowed to instantiate.
     * @return boolean indicates wheather the class is allowed to be instantiated by the RemoteReflector Servlet
     */
    public boolean isInstantiationAllowed(Class cl) {
        Class[] interfaces=cl.getInterfaces();
        if (interfaces!=null) {
            for (int i=0;i<interfaces.length;i++) {
               if (interfaces[i].getName().equals(Reflect.class.getName()))
                 return true;
               if (vAllowedClasses.contains(cl.getName()))
                 return true;
            }
        }
        return false;
    }

    /*
     * Checks to see if Instantiation is allowed for the passed class.
     * @param obj java.lang.Object The object for which you want to execute the method on.
     * @param sMethod java.lang.String The method you want to check if you are allowed to execute.
     * @return boolean indicates wheather the method is allowed to be executed on the passed object by the RemoteReflector Servlet
     */
    public boolean isMethodCallAllowed(Object obj, String sMethod) {
        if (obj instanceof Reflect)
          return true;
        if (vAllowedClassesMethods.contains(new ClassMethod(obj.getClass().getName(),sMethod)))
          return true;
        return false;
    }

    /*
     * Specifies a class to allow instantiation of via RemoteReflector.
     * @param cl java.lang.Class The class to allow to instantiate.
     */
    public void allowInstantiation(Class cl) {
        if (!vAllowedClasses.contains(cl.getName()))
            vAllowedClasses.addElement(cl.getName());
    }

    /*
     * Specifies a class and method to allow execution of via RemoteReflector.
     * @param cl java.lang.Class The class to allow to execute specified method on.
     * @param sMethod java.lang.String The method to which to allow execution of.
     */
    public void allowMethodCall(Class cl,String sMethod) {
        ClassMethod cm=new ClassMethod(cl.getName(),sMethod);
        if (!vAllowedClassesMethods.contains(cm))
            vAllowedClassesMethods.addElement(cm);
    }

    /*
     * Removes the specified class from the allowed classes to be instantiated via RemoteReflector.
     * @param cl java.lang.Class The class to remove from allowed classes.
     */
    public void removeInstantiation(Class cl) {
        vAllowedClasses.removeElement(cl.getName());
    }

    /*
     * Specifies a class and method to remove from allowed class/methods.
     * @param cl java.lang.Class The class to which the method belongs.
     * @param sMethod java.lang.String The method to remove from allowed.
     */
    public void removeMethodCall(Class cl,String sMethod) {
        vAllowedClassesMethods.removeElement(new ClassMethod(cl.getName(),sMethod));
    }

	/**
	 * @return Returns the Vector of Allowed classes.
	 */
	public Vector getAllowedClasses() {
		return vAllowedClasses;
	}
	/**
	 * @param Sets the Vector of allowed classes
	 */
	public void setAllowedClasses(Vector allowedClasses) {
		vAllowedClasses = allowedClasses;
	}
	/**
	 * @return Returns the vector of Allowed Class Methods.
	 */
	public Vector getAllowedClassesMethods() {
		return vAllowedClassesMethods;
	}
	/**
	 * @param allowedClassesMethods A vector of Allowed Class Methods to set.
	 */
	public void setAllowedClassesMethods(Vector allowedClassesMethods) {
		vAllowedClassesMethods = allowedClassesMethods;
	}
}
