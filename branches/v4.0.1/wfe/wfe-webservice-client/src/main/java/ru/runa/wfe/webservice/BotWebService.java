
package ru.runa.wfe.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-12/14/2009 02:16 PM(ramkris)-
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "BotWebService", targetNamespace = "http://impl.service.wfe.runa.ru/", wsdlLocation = "http://localhost:8080/runawfe-wfe-service-4.0.0/BotServiceBean?wsdl")
public class BotWebService
    extends Service
{

    private final static URL BOTWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException BOTWEBSERVICE_EXCEPTION;
    private final static QName BOTWEBSERVICE_QNAME = new QName("http://impl.service.wfe.runa.ru/", "BotWebService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/runawfe-wfe-service-4.0.0/BotServiceBean?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BOTWEBSERVICE_WSDL_LOCATION = url;
        BOTWEBSERVICE_EXCEPTION = e;
    }

    public BotWebService() {
        super(__getWsdlLocation(), BOTWEBSERVICE_QNAME);
    }

    public BotWebService(WebServiceFeature... features) {
        super(__getWsdlLocation(), BOTWEBSERVICE_QNAME, features);
    }

    public BotWebService(URL wsdlLocation) {
        super(wsdlLocation, BOTWEBSERVICE_QNAME);
    }

    public BotWebService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BOTWEBSERVICE_QNAME, features);
    }

    public BotWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BotWebService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns BotAPI
     */
    @WebEndpoint(name = "BotAPIPort")
    public BotAPI getBotAPIPort() {
        return super.getPort(new QName("http://impl.service.wfe.runa.ru/", "BotAPIPort"), BotAPI.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BotAPI
     */
    @WebEndpoint(name = "BotAPIPort")
    public BotAPI getBotAPIPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.service.wfe.runa.ru/", "BotAPIPort"), BotAPI.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BOTWEBSERVICE_EXCEPTION!= null) {
            throw BOTWEBSERVICE_EXCEPTION;
        }
        return BOTWEBSERVICE_WSDL_LOCATION;
    }

}
