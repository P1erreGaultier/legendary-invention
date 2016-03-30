package com.alma.platform.monitor;

import java.util.Date;

/**
 * Classe représentant un log émis par une extension
 */
public class Log {

    private LogLevel level;
    private Date timestamp;
    private String originClassName;
    private String message;

    public Log(LogLevel lvl, String origin, String msg) {
        level = lvl;
        timestamp = new Date();
        originClassName = origin;
        message = msg;
    }

    /**
     * Accesseur sur le niveau du Log
     * @return
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Accesseur sur le timestamp du log
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Accesseur sur le nom de la classe à l'origine du log
     * @return
     */
    public String getOriginClassName() {
        return originClassName;
    }

    /**
     * Accesseur sur le message du log
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Méthode qui renvoie l'objet au format chaîne de caractères
     * @return
     */
    @Override
    public String toString() {
        return "[Log " + level + " at " + timestamp.toString() + " from " + originClassName + " : " + message + "]";
    }
}
