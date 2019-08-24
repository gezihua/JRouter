package com.superx.j.regedit;

/**
 * PKG com.superx.j.regedit
 * Created by suj on 2019-08-24.
 * DES:
 * QQ:1553202383
 */
public class ServiceDescription {
    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getInterfaceClassIml() {
        return interfaceClassIml;
    }

    public void setInterfaceClassIml(String interfaceClassIml) {
        this.interfaceClassIml = interfaceClassIml;
    }

    public Object getInterfaceClassImlObject() {
        return interfaceClassImlObject;
    }

    public void setInterfaceClassImlObject(Object interfaceClassImlObject) {
        this.interfaceClassImlObject = interfaceClassImlObject;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    private String interfaceClass;
    private String interfaceClassIml;
    private Object interfaceClassImlObject;
    private boolean lazyInit = false;

    public static ServiceDescription create(String interfaceName, String metaInfo, Object o, boolean b) {
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.interfaceClass = interfaceName;
        serviceDescription.interfaceClassIml = metaInfo;
        serviceDescription.interfaceClassImlObject = o;
        serviceDescription.lazyInit = b;
        return serviceDescription;
    }

    public static ServiceDescription create(String interfaceName, String metaInfo, Object o) {
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.interfaceClass = interfaceName;
        serviceDescription.interfaceClassIml = metaInfo;
        serviceDescription.interfaceClassImlObject = o;
        serviceDescription.lazyInit = true;
        return serviceDescription;
    }
}
