package com.project.cs496.tab_app;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

/**
 * Created by q on 2016-07-06.
 */
public class AddressRepository extends ModelRepository<Address> {
    public AddressRepository() {
        super("address",Address.class);
    }
    @Override
    public RestContract createContract() {
        RestContract restContract = super.createContract();

        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/sync", "GET"),
                getClassName() + ".sync");

        return restContract;
    }

    public void sync(String token, String uid, final VoidCallback callback) {
        ImmutableMap params =  ImmutableMap.of("user_token", token, "user_id", uid);

        invokeStaticMethod("sync", params, new Adapter.Callback() {
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
