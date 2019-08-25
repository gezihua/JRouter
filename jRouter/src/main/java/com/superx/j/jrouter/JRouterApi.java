package com.superx.j.jrouter;

/**
 * PKG com.superx.j.jrouter
 * Created by suj on 2019-08-25.
 * DES:
 * QQ:1553202383
 */
public enum JRouterApi {
    API;

    public RouterBean build(String s) {
        return JRouter.API.build(s);
    }
}
