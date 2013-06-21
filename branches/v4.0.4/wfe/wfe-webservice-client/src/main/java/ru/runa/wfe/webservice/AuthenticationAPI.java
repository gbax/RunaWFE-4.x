
package ru.runa.wfe.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-12/14/2009 02:16 PM(ramkris)-
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AuthenticationAPI", targetNamespace = "http://impl.service.wfe.runa.ru/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AuthenticationAPI {


    /**
     * 
     * @return
     *     returns ru.runa.wfe.webservice.User
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "authenticateByCallerPrincipal", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AuthenticateByCallerPrincipal")
    @ResponseWrapper(localName = "authenticateByCallerPrincipalResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AuthenticateByCallerPrincipalResponse")
    public User authenticateByCallerPrincipal();

    /**
     * 
     * @param arg0
     * @return
     *     returns ru.runa.wfe.webservice.User
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "authenticateByKerberos", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AuthenticateByKerberos")
    @ResponseWrapper(localName = "authenticateByKerberosResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AuthenticateByKerberosResponse")
    public User authenticateByKerberos(
        @WebParam(name = "arg0", targetNamespace = "")
        byte[] arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns ru.runa.wfe.webservice.User
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "authenticateByLoginPassword", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AuthenticateByLoginPassword")
    @ResponseWrapper(localName = "authenticateByLoginPasswordResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.AuthenticateByLoginPasswordResponse")
    public User authenticateByLoginPassword(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}
