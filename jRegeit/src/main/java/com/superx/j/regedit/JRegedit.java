package com.superx.j.regedit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.sun.istack.internal.NotNull;
import com.superx.j.regedit.annation.JRegeditAnnation;
import com.superx.j.regedit.log.JLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * PKG com.superx.j.regeit
 * Created by suj on 2019-08-24.
 * DES:
 * QQ:1553202383
 */
public enum JRegedit {
    API;
    private final static String TAG = JRegedit.class.getName();
    public final static String j_regedit_prefix = "j.regdit.v1";
    private Boolean mInit = null;
    private ConcurrentSkipListSet<String> metaInfos = new ConcurrentSkipListSet<>();
    /**
     * 这里的散列值是接口名称
     */
    private ConcurrentHashMap<String, ServiceDescription> services = new ConcurrentHashMap<>();

    public <T> T findServiceByInterface(Class jrInterfaceClass) {
        Context context = null;

        // 这里是来自于map 的
        ensureInitServicesMap(context);
        // 应该还可以加入一个动态注册的接口

        // 这个可以后边在写
        ensureInitServicesAndImlMap();
        ServiceDescription serviceDescription = services.get(jrInterfaceClass);
        T interfaceClassImlObject = (T) serviceDescription.getInterfaceClassImlObject();
        if (interfaceClassImlObject != null) {
            return interfaceClassImlObject;
        } else {
            interfaceClassImlObject = initInterfaceByName(services, serviceDescription);
        }
        return interfaceClassImlObject;
    }

    private <T> T initInterfaceByName(ConcurrentHashMap<String, ServiceDescription> services, ServiceDescription serviceDescription) {
        if (serviceDescription == null) {
            return null;
        }
        Object formObjectByClassName = formObjectByClassName(serviceDescription.getInterfaceClassIml());
        if (formObjectByClassName == null) {
            return null;
        }
        List<? extends Class> interfaceNames = findInterfaceNames(serviceDescription.getInterfaceClassIml());
        for (Class interfaceName : interfaceNames) {
            ServiceDescription serviceDescription1 = services.get(interfaceName);
            if (serviceDescription1 == null) {
                initService(interfaceName.getName());
            } else {
                serviceDescription1.setInterfaceClassImlObject(formObjectByClassName);
            }

        }
        return (T) formObjectByClassName;

    }

    /**
     * 这里应该懒加载还是急切加载,这里需要判定一下
     * 这里需要维护一个三元组合，第一个
     */

    @NotNull
    private void ensureInitServicesAndImlMap() {
        if (metaInfos == null || metaInfos.isEmpty()) {
            return;
        }
        // 这里是所有的实现
        for (String metaInfo : metaInfos) {
            initService(metaInfo);

        }

    }

    private void initService(String metaInfo) {
        boolean ifMetaInfoLazyInit = ifMetaInfoLazyInit(metaInfo);
        if (ifMetaInfoLazyInit) {
            lazyInitMetaInfo(metaInfo, services);
        } else {
            notLazyInitMetaInfo(metaInfo, services);
        }
    }

    private void notLazyInitMetaInfo(String metaInfo, ConcurrentHashMap<String, ServiceDescription> services) {
        lazyInitMetaInfo(metaInfo, services, false);

    }

    private void lazyInitMetaInfo(String metaInfo, ConcurrentHashMap<String, ServiceDescription> services) {
        lazyInitMetaInfo(metaInfo, services, true);

    }


    private void lazyInitMetaInfo(String metaInfo, ConcurrentHashMap<String, ServiceDescription> services, boolean lazy) {
        if (TextUtils.isEmpty(metaInfo)) {
            return;
        }
        List<? extends Class> interfaceNames = findInterfaceNames(metaInfo);
        Object object = lazy ? null : formObjectByClassName(metaInfo);
        for (Class interfaceName : interfaceNames) {
            ServiceDescription serviceDescription = ServiceDescription.create(interfaceName.getName(), metaInfo, object, lazy);
            if (serviceDescription == null) {
                return;
            }
            services.put(interfaceName.getName(), serviceDescription);
        }
    }

    private Object formObjectByClassName(String metaInfo) {
        if (TextUtils.isEmpty(metaInfo)) {
            return null;
        }
        Class<?> aClass = formClassWithExecption(metaInfo);
        if (aClass == null) {
            return null;
        }
        try {
            return aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    private List<? extends Class> findInterfaceNames(String metaInfo) {
        List<Class> interfaces = new ArrayList<>();
        if (TextUtils.isEmpty(metaInfo)) {
            return null;
        }
        List<? extends Class> interfaceNamesByAnnations = findInterfaceNamesByAnnations(metaInfo);
        if (interfaceNamesByAnnations != null) {
            return interfaceNamesByAnnations;
        }
        JLog.e(TAG, "metainfo :" + metaInfo + "with out annoation,form found interface");
        interfaces.addAll(findInterfaceNamesBySupperClass(metaInfo));
        return interfaces;
    }

    private Collection<Class> findInterfaceNamesBySupperClass(String metaInfo) {
        if (TextUtils.isEmpty(metaInfo)) {
            return new ArrayList<>();
        }
        Class<?> aClass = formClassWithExecption(metaInfo);
        if (aClass == null) {
            return new ArrayList<>();
        }
        Class<?>[] interfaces = aClass.getInterfaces();
        if (interfaces == null) {
            return new ArrayList<>();
        }
        ArrayList<Class> objects = new ArrayList<>();
        for (Class<?> anInterface : interfaces) {
            objects.add(anInterface);
        }
        return objects;

    }

    private Class<?> formClassWithExecption(String metaInfo) {
        try {
            return Class.forName(metaInfo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<? extends Class> findInterfaceNamesByAnnations(String metaInfo) {

        JRegeditAnnation jRegeditAnnationWithClassName = findJRegeditAnnationWithClassName(metaInfo);
        ArrayList<Class> strings = new ArrayList<>();
        if (jRegeditAnnationWithClassName == null) {
            return strings;
        }
        Class[] interfaces = jRegeditAnnationWithClassName.interfaces();
        if (interfaces == null) {
            return strings;
        }
        for (Class anInterface : interfaces) {
            strings.add(anInterface);
        }
        return strings;
    }

    private boolean ifMetaInfoLazyInit(String metaInfo) {
        if (TextUtils.isEmpty(metaInfo)) {
            return false;
        }
        Class<?> aClass = null;
        try {
            aClass = Class.forName(metaInfo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JLog.e(TAG, metaInfo + " is not found");
            return false;
        }
        if (aClass == null) {
            return false;
        }

        JRegeditAnnation annotation = aClass.getAnnotation(JRegeditAnnation.class);
        if (annotation == null) {
            JLog.e(TAG, metaInfo + "can not find annoation,so it will lazy load");
            return false;
        }
        boolean lazyInit = annotation.lazyInit();

        return lazyInit;

    }


    private JRegeditAnnation findJRegeditAnnationWithClassName(String metaInfo) {
        if (TextUtils.isEmpty(metaInfo)) {
            return null;
        }
        Class<?> aClass = null;
        try {
            aClass = Class.forName(metaInfo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JLog.e(TAG, metaInfo + " is not found");
            return null;
        }
        JRegeditAnnation annotation = aClass.getAnnotation(JRegeditAnnation.class);
        return annotation;
    }


    private void ensureInitServicesMap(Context context) {
        if (context == null) {
            JLog.e(TAG, "context is none ");
            return;
        }

        if (mInit == null) {
            synchronized (JRegedit.class) {
                if (mInit == null) {
                    findRegeitFromMetaData(context);

                    mInit = Boolean.TRUE;
                }
            }
        }


    }

    private void findRegeitFromMetaData(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;
            Set<String> strings = metaData.keySet();
            if (strings != null) {
                return;
            }
            for (String string : strings) {
                if (TextUtils.isEmpty(string)) {
                    continue;
                }
                String metaDataString = metaData.getString(string);
                if (TextUtils.isEmpty(metaDataString)) {
                    continue;
                }
                metaInfos.add(metaDataString);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
