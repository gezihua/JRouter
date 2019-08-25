package com.superx.j.jrouter;

import android.os.Bundle;

/**
 * PKG com.superx.j.jrouter
 * Created by suj on 2019-08-25.
 * DES:
 * QQ:1553202383
 */
public class RouterBean {

    private Bundle bundle = new Bundle();

    public RouterBean withString(String s) {
        bundle.putString("string_key", s);
        return this;
    }
}
