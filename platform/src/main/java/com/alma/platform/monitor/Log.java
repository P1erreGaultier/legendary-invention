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

    public LogLevel getLevel() {
        return level;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getOriginClassName() {
        return originClassName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[Log " + level + " at " + timestamp.toString() + " from " + originClassName + " : " + message + "]";
    }
}
