package com.superx.j.regedit;

import android.content.Context;

/**
 * PKG com.superx.j.regedit
 * Created by suj on 2019-08-24.
 * DES:
 * QQ:1553202383
 */
public enum JRegeditApi {
    API;

    public <T> T findServiceByInterface(Context context, Class<T> jrInterfaceClass) {
        return JRegedit.API.findServiceByInterface(context, jrInterfaceClass);
    }

}
