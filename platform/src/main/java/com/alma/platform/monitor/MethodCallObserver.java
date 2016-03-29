package com.alma.platform.monitor;

/**
 * Created by E122371M on 29/03/16.
 */
public interface MethodCallObserver {

    void execute(String instance_name, String method_name, int nb_calls);
}
