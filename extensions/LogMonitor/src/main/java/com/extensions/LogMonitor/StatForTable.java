package com.extensions.LogMonitor;

/**
 * Created by E113209D on 24/03/16.
 */

/**
 * Class StatForTable, use in StatTableModel to print method call number
 */
public class StatForTable {
    private String extensionName;
    private String instanceName;
    private String methodName;
    private int nbCall;

    /**
     * Get name of the extension
     * @return
     */
    public String getExtensionName() {
        return extensionName;
    }

    /**
     * Set Name of the extension
     * @param extensionName
     */
    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }


    /**
     * Get Instance Name
     * @return
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * Set Instance Name
     * @param instanceName
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * Get Method Name
     * @return
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Set Method Name
     * @param methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Get Number of method's call
     * @return
     */
    public int getNbCall() {
        return nbCall;
    }
    /**
     * Set Number of method's call
     * @return
     */
    public void setNbCall(int nbCall) {
        this.nbCall = nbCall;
    }

    /**
     * Override equals, because an object is identified by his instanceName & methodName
     * @param obj
     * @return
     */
    public boolean equals(StatForTable obj) {
        return obj.getMethodName().equals(methodName) && obj.getInstanceName().equals(instanceName);
    }

}
