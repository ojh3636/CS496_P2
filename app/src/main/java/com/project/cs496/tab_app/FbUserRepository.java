package com.project.cs496.tab_app;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

/**
 * Created by q on 2016-07-06.
 */
public class FbUserRepository extends ModelRepository<FbUser> {

    public FbUserRepository() {
        super("fb_user", FbUser.class);
    }

    @Override
    public RestContract createContract() {
        RestContract restContract = super.createContract();

        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/validCheck", "GET"),
                getClassName() + ".validCheck");

        return restContract;
    }

    public void validCheck(String uid, String token, final VoidCallback callback) {
        ImmutableMap params =  ImmutableMap.of("user_id", uid, "user_token", token);

        invokeStaticMethod("validCheck", params, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                callback.onSuccess();
            }

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }
}
