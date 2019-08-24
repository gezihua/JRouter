package com.superx.j.router;

import android.util.Log;

import com.superx.j.regedit.annation.JRegeditAnnation;

/**
 * PKG com.superx.j.router
 * Created by suj on 2019-08-24.
 * DES:
 * QQ:1553202383
 */

@JRegeditAnnation(interfaces = {JrInterface.class})
public class JrInterfaceIml implements JrInterface {
    @Override
    public void sayHello() {
        Log.e("JrInterfaceIml", "hello ni 好");
    }
}
