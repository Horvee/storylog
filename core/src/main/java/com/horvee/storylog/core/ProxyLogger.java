package com.horvee.storylog.core;

import org.slf4j.Logger;
import org.slf4j.Marker;


/**
 * Proxy slf4j log framework,inside will be out put to story log
 * */
public final class ProxyLogger implements Logger {

    private final Logger proxyObject;

    public ProxyLogger(Logger proxyObject) {
        if (proxyObject == null) {
            throw new NullPointerException("Proxy logger object can not be null");
        }
        this.proxyObject = proxyObject;
    }

    @Override
    public String getName() {
        return proxyObject.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return proxyObject.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        proxyObject.trace(msg);
        if (this.isTraceEnabled()) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        proxyObject.trace(format,arg);
        if (this.isTraceEnabled()) {
            StoryLogger.logBeforeTrace(format,arg);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        proxyObject.trace(format,arg1,arg2);
        if (this.isTraceEnabled()) {
            StoryLogger.logBeforeTrace(format,arg1,arg2);
        }
    }

    @Override
    public void trace(String format, Object... arguments) {
        proxyObject.trace(format,arguments);
        if (this.isTraceEnabled()) {
            StoryLogger.logBeforeTrace(format,arguments);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        proxyObject.trace(msg,t);
        if (this.isTraceEnabled()) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return proxyObject.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        proxyObject.trace(marker,msg);
        if (this.isTraceEnabled(marker)) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        proxyObject.trace(marker,format,arg);
        if (this.isTraceEnabled(marker)) {
            StoryLogger.logBeforeTrace(format,arg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        proxyObject.trace(marker, format, arg1, arg2);
        if (this.isTraceEnabled(marker)) {
            StoryLogger.logBeforeTrace(format,arg1,arg2);
        }
    }

    @Override

    public void trace(Marker marker, String format, Object... argArray) {
        proxyObject.trace(marker,format,argArray);
        if (this.isTraceEnabled(marker)) {
            StoryLogger.logBeforeTrace(format,argArray);
        }
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        proxyObject.trace(marker,msg,t);
        if (this.isTraceEnabled(marker)) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return proxyObject.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        proxyObject.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        proxyObject.debug(format,arg);
        if (this.isDebugEnabled()) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        proxyObject.debug(format, arg1, arg2);
        if (this.isDebugEnabled()) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void debug(String format, Object... arguments) {
        proxyObject.debug(format, arguments);
        if (this.isDebugEnabled()) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void debug(String msg, Throwable t) {
        proxyObject.debug(msg,t);
        if (this.isDebugEnabled()) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return proxyObject.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {
        proxyObject.debug(marker,msg);
        if (this.isDebugEnabled(marker)) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        proxyObject.debug(marker,format,arg);
        if (this.isDebugEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        proxyObject.debug(marker,format,arg1,arg2);
        if (this.isDebugEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        proxyObject.debug(marker,format,arguments);
        if (this.isDebugEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        proxyObject.debug(marker,msg,t);
        if (this.isDebugEnabled(marker)) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isInfoEnabled() {
        return proxyObject.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        proxyObject.info(msg);
        if (this.isInfoEnabled()) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void info(String format, Object arg) {
        proxyObject.info(format,arg);
        if (this.isInfoEnabled()) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        proxyObject.info(format, arg1, arg2);
        if (this.isInfoEnabled()) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void info(String format, Object... arguments) {
        proxyObject.info(format, arguments);
        if (this.isInfoEnabled()) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void info(String msg, Throwable t) {
        proxyObject.info(msg, t);
        if (this.isInfoEnabled()) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return proxyObject.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        proxyObject.info(marker,msg);
        if (this.isInfoEnabled(marker)) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        proxyObject.info(marker, format, arg);
        if (this.isInfoEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        proxyObject.info(marker, format, arg1, arg2);
        if (this.isInfoEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        proxyObject.info(marker, format, arguments);
        if (this.isInfoEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        proxyObject.info(marker, msg, t);
        if (this.isInfoEnabled(marker)) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isWarnEnabled() {
        return proxyObject.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        proxyObject.warn(msg);
        if (this.isWarnEnabled()) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void warn(String format, Object arg) {
        proxyObject.warn(format,arg);
        if (this.isWarnEnabled()) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        proxyObject.warn(format, arguments);
        if (this.isWarnEnabled()) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        proxyObject.warn(format, arg1, arg2);
        if (this.isWarnEnabled()) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void warn(String msg, Throwable t) {
        proxyObject.warn(msg, t);
        if (this.isWarnEnabled()) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return proxyObject.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        proxyObject.warn(marker, msg);
        if (this.isWarnEnabled(marker)) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        proxyObject.warn(marker, format, arg);
        if (this.isWarnEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        proxyObject.warn(marker, format, arg1, arg2);
        if (this.isWarnEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        proxyObject.warn(marker, format, arguments);
        if (this.isWarnEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        proxyObject.warn(marker, msg, t);
        if (this.isWarnEnabled(marker)) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return proxyObject.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        proxyObject.error(msg);
        if (this.isErrorEnabled()) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void error(String format, Object arg) {
        proxyObject.error(format, arg);
        if (this.isErrorEnabled()) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        proxyObject.error(format, arg1, arg2);
        if (this.isErrorEnabled()) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        proxyObject.error(format, arguments);
        if (this.isErrorEnabled()) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void error(String msg, Throwable t) {
        proxyObject.error(msg, t);
        if (this.isErrorEnabled()) {
            StoryLogger.log(msg, t);
        }
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return proxyObject.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        proxyObject.error(marker, msg);
        if (this.isErrorEnabled(marker)) {
            StoryLogger.logBeforeTrace(msg);
        }
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        proxyObject.error(marker, format, arg);
        if (this.isErrorEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg);
        }
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        proxyObject.error(marker, format, arg1, arg2);
        if (this.isErrorEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arg1, arg2);
        }
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        proxyObject.error(marker, format, arguments);
        if (this.isErrorEnabled(marker)) {
            StoryLogger.logBeforeTrace(format, arguments);
        }
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        proxyObject.error(marker, msg, t);
        if (this.isErrorEnabled(marker)) {
            StoryLogger.log(msg, t);
        }
    }

    public Logger getProxyObject() {
        return this.proxyObject;
    }
}
