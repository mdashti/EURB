
package com.sharifpro.matma.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "UserInfoServiceEndpoint", targetNamespace = "http://ws.matma.sharifpro.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UserInfoServiceEndpoint {


    /**
     * 
     * @param username
     */
    @WebMethod
    @RequestWrapper(localName = "killSession", targetNamespace = "http://ws.matma.sharifpro.com/", className = "com.sharifpro.matma.ws.KillSession")
    @ResponseWrapper(localName = "killSessionResponse", targetNamespace = "http://ws.matma.sharifpro.com/", className = "com.sharifpro.matma.ws.KillSessionResponse")
    public void killSession(
        @WebParam(name = "username", targetNamespace = "")
        String username);

    /**
     * 
     * @param username
     */
    @WebMethod
    @RequestWrapper(localName = "updateProfile", targetNamespace = "http://ws.matma.sharifpro.com/", className = "com.sharifpro.matma.ws.UpdateProfile")
    @ResponseWrapper(localName = "updateProfileResponse", targetNamespace = "http://ws.matma.sharifpro.com/", className = "com.sharifpro.matma.ws.UpdateProfileResponse")
    public void updateProfile(
        @WebParam(name = "username", targetNamespace = "")
        String username);

}