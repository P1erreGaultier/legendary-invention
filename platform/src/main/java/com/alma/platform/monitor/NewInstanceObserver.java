package com.alma.platform.monitor;

/**
 * Created by E122371M on 29/03/16.
 */
public interface NewInstanceObserver {

    void execute(String extension_name, String instance_name);
}
