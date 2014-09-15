
package com.sharifpro.matma.wscall.user;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sharifpro.matma.wscall.user package. 
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

    private final static QName _UpdateProfileReturn_QNAME = new QName("http://com.sharifpro.matma.wscall.user", "updateProfileReturn");
    private final static QName _GetProfileReturn_QNAME = new QName("http://com.sharifpro.matma.wscall.user", "getProfileReturn");
    private final static QName _UserName_QNAME = new QName("http://com.sharifpro.matma.wscall.user", "userName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sharifpro.matma.wscall.user
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Profile }
     * 
     */
    public Profile createProfile() {
        return new Profile();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.sharifpro.matma.wscall.user", name = "updateProfileReturn")
    public JAXBElement<Boolean> createUpdateProfileReturn(Boolean value) {
        return new JAXBElement<Boolean>(_UpdateProfileReturn_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.sharifpro.matma.wscall.user", name = "getProfileReturn")
    public JAXBElement<String> createGetProfileReturn(String value) {
        return new JAXBElement<String>(_GetProfileReturn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.sharifpro.matma.wscall.user", name = "userName")
    public JAXBElement<String> createUserName(String value) {
        return new JAXBElement<String>(_UserName_QNAME, String.class, null, value);
    }

}
