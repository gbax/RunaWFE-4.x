
package ru.runa.wfe.webservice;

import java.util.List;
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
@WebService(name = "AuthorizationAPI", targetNamespace = "http://impl.service.wfe.runa.ru/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AuthorizationAPI {


    /**
     * 
     * @param batchPresentation
     * @param user
     * @param withPermission
     * @param identifiable
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.WfExecutor>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getExecutorsWithPermission", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetExecutorsWithPermission")
    @ResponseWrapper(localName = "getExecutorsWithPermissionResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetExecutorsWithPermissionResponse")
    public List<WfExecutor> getExecutorsWithPermission(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "identifiable", targetNamespace = "")
        Identifiable identifiable,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation,
        @WebParam(name = "withPermission", targetNamespace = "")
        boolean withPermission);

    /**
     * 
     * @param batchPresentation
     * @param user
     * @param withPermission
     * @param identifiable
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getExecutorsWithPermissionCount", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetExecutorsWithPermissionCount")
    @ResponseWrapper(localName = "getExecutorsWithPermissionCountResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetExecutorsWithPermissionCountResponse")
    public int getExecutorsWithPermissionCount(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "identifiable", targetNamespace = "")
        Identifiable identifiable,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation,
        @WebParam(name = "withPermission", targetNamespace = "")
        boolean withPermission);

    /**
     * 
     * @param performer
     * @param user
     * @param identifiable
     * @return
     *     returns java.util.List<ru.runa.wfe.webservice.Permission>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getIssuedPermissions", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetIssuedPermissions")
    @ResponseWrapper(localName = "getIssuedPermissionsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetIssuedPermissionsResponse")
    public List<Permission> getIssuedPermissions(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "performer", targetNamespace = "")
        WfExecutor performer,
        @WebParam(name = "identifiable", targetNamespace = "")
        Identifiable identifiable);

    /**
     * 
     * @param enablePaging
     * @param persistentClass
     * @param permission
     * @param batchPresentation
     * @param user
     * @param securedObjectTypes
     * @return
     *     returns java.util.List<java.lang.Object>
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "getPersistentObjects", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetPersistentObjects")
    @ResponseWrapper(localName = "getPersistentObjectsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.GetPersistentObjectsResponse")
    public List<Object> getPersistentObjects(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "batchPresentation", targetNamespace = "")
        BatchPresentation batchPresentation,
        @WebParam(name = "persistentClass", targetNamespace = "")
        String persistentClass,
        @WebParam(name = "permission", targetNamespace = "")
        Permission permission,
        @WebParam(name = "securedObjectTypes", targetNamespace = "")
        List<SecuredObjectType> securedObjectTypes,
        @WebParam(name = "enablePaging", targetNamespace = "")
        boolean enablePaging);

    /**
     * 
     * @param permission
     * @param user
     * @param identifiable
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(name = "result", targetNamespace = "")
    @RequestWrapper(localName = "isAllowed", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.IsAllowed")
    @ResponseWrapper(localName = "isAllowedResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.IsAllowedResponse")
    public boolean isAllowed(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "permission", targetNamespace = "")
        Permission permission,
        @WebParam(name = "identifiable", targetNamespace = "")
        Identifiable identifiable);

    /**
     * 
     * @param executorId
     * @param permissions
     * @param user
     * @param identifiable
     */
    @WebMethod
    @RequestWrapper(localName = "setPermissions", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.SetPermissions")
    @ResponseWrapper(localName = "setPermissionsResponse", targetNamespace = "http://impl.service.wfe.runa.ru/", className = "ru.runa.wfe.webservice.SetPermissionsResponse")
    public void setPermissions(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "executorId", targetNamespace = "")
        Long executorId,
        @WebParam(name = "permissions", targetNamespace = "")
        List<Permission> permissions,
        @WebParam(name = "identifiable", targetNamespace = "")
        Identifiable identifiable);

}
