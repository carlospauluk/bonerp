/**
 * ServerWsrcsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class ServerWsrcsLocator extends org.apache.axis.client.Service implements com.bonsucesso.servipa.ws.ServerWsrcs {

    public ServerWsrcsLocator() {
    }


    public ServerWsrcsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServerWsrcsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServerWsrcsPort
    private java.lang.String ServerWsrcsPort_address; // = "https://sistema.ocrediario.com.br:443/wsrcs.php";

    public java.lang.String getServerWsrcsPortAddress() {
        return ServerWsrcsPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServerWsrcsPortWSDDServiceName = "server.wsrcsPort";

    public java.lang.String getServerWsrcsPortWSDDServiceName() {
        return ServerWsrcsPortWSDDServiceName;
    }

    public void setServerWsrcsPortWSDDServiceName(java.lang.String name) {
        ServerWsrcsPortWSDDServiceName = name;
    }

    public com.bonsucesso.servipa.ws.ServerWsrcsPortType getServerWsrcsPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServerWsrcsPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServerWsrcsPort(endpoint);
    }

    public com.bonsucesso.servipa.ws.ServerWsrcsPortType getServerWsrcsPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.bonsucesso.servipa.ws.ServerWsrcsBindingStub _stub = new com.bonsucesso.servipa.ws.ServerWsrcsBindingStub(portAddress, this);
            _stub.setPortName(getServerWsrcsPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServerWsrcsPortEndpointAddress(java.lang.String address) {
        ServerWsrcsPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.bonsucesso.servipa.ws.ServerWsrcsPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.bonsucesso.servipa.ws.ServerWsrcsBindingStub _stub = new com.bonsucesso.servipa.ws.ServerWsrcsBindingStub(new java.net.URL(ServerWsrcsPort_address), this);
                _stub.setPortName(getServerWsrcsPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("server.wsrcsPort".equals(inputPortName)) {
            return getServerWsrcsPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:wsrcs", "server.wsrcs");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:wsrcs", "server.wsrcsPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServerWsrcsPort".equals(portName)) {
            setServerWsrcsPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
