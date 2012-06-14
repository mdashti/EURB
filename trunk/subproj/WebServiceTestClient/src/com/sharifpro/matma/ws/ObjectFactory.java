
package com.sharifpro.matma.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sharifpro.matma.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _KillSession_QNAME = new QName("http://ws.matma.sharifpro.com/", "killSession");
    private final static QName _KillSessionResponse_QNAME = new QName("http://ws.matma.sharifpro.com/", "killSessionResponse");
    private final static QName _UpdateProfile_QNAME = new QName("http://ws.matma.sharifpro.com/", "updateProfile");
    private final static QName _UpdateProfileResponse_QNAME = new QName("http://ws.matma.sharifpro.com/", "updateProfileResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sharifpro.matma.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateProfile }
     * 
     */
    public UpdateProfile createUpdateProfile() {
        return new UpdateProfile();
    }

    /**
     * Create an instance of {@link KillSessionResponse }
     * 
     */
    public KillSessionResponse createKillSessionResponse() {
        return new KillSessionResponse();
    }

    /**
     * Create an instance of {@link UpdateProfileResponse }
     * 
     */
    public UpdateProfileResponse createUpdateProfileResponse() {
        return new UpdateProfileResponse();
    }

    /**
     * Create an instance of {@link KillSession }
     * 
     */
    public KillSession createKillSession() {
        return new KillSession();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KillSession }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.matma.sharifpro.com/", name = "killSession")
    public JAXBElement<KillSession> createKillSession(KillSession value) {
        return new JAXBElement<KillSession>(_KillSession_QNAME, KillSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KillSessionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.matma.sharifpro.com/", name = "killSessionResponse")
    public JAXBElement<KillSessionResponse> createKillSessionResponse(KillSessionResponse value) {
        return new JAXBElement<KillSessionResponse>(_KillSessionResponse_QNAME, KillSessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.matma.sharifpro.com/", name = "updateProfile")
    public JAXBElement<UpdateProfile> createUpdateProfile(UpdateProfile value) {
        return new JAXBElement<UpdateProfile>(_UpdateProfile_QNAME, UpdateProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.matma.sharifpro.com/", name = "updateProfileResponse")
    public JAXBElement<UpdateProfileResponse> createUpdateProfileResponse(UpdateProfileResponse value) {
        return new JAXBElement<UpdateProfileResponse>(_UpdateProfileResponse_QNAME, UpdateProfileResponse.class, null, value);
    }

}
