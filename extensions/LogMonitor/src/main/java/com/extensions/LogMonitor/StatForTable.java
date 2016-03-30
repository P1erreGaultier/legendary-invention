package com.extensions.LogMonitor;

/**
 * Created by E113209D on 24/03/16.
 */
public class StatForTable {
    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    private String extensionName;
    private String instanceName;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getNbCall() {
        return nbCall;
    }

    public void setNbCall(int nbCall) {
        this.nbCall = nbCall;
    }

    private String methodName;
    private int nbCall;

    public boolean equals(StatForTable obj) {
        return obj.getMethodName().equals(methodName) && obj.getInstanceName().equals(instanceName);
    }

}
