package com.salmonllc.remote;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Fred Cahill
 * Date: Sep 14, 2004
 * Time: 10:07:41 AM
 * To change this template use Options | File Templates.
 */
/**
 * This interface is used to get the session key that references an object in the session on the server. Used in association with RemoteProxy.
 */
public interface RemoteReflection extends Serializable {
    public String getSessionKey();
}
