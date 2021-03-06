package com.horvee.storylog.core;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.horvee.storylog.core.model.dto.LogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horvee.storylog.core.model.dto.TaskInfo;

/**
 * Out put to story log storage
 */
public class StoryLogger {

    private static final Logger logger = LoggerFactory.getLogger(StoryLogger.class);
    private static boolean printLoggerFailStack = false;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2);

    /**
     * create by slf4j logger proxy
     * @param proxyLogger need to be proxy log object
     * @return return logger object use it
     * */
    public static Logger createLog(Logger proxyLogger) {
        return new ProxyLogger(proxyLogger);
    }

    /**
     * Stream to story log logic (data will be save to story log)
     * @param text message or message template
     * @param objects message template value
     * */
    public static void log(String text, Object... objects) {
        StackTraceElement useTrace = Thread.currentThread().getStackTrace()[2];
        log(useTrace, text, objects);
    }

    /**
     * Use to proxy log, will be load upper level two trace
     */
    protected static void logBeforeTrace(String text, Object... objects) {
        StackTraceElement useTrace = Thread.currentThread().getStackTrace()[3];
        log(useTrace, text, objects);
    }

    /**
     * Log message and throwable stack message to story log
     * */
    public static void log(String msg, Throwable throwable) {
        ConcurrentLinkedQueue<LogInfo> logInfoConcurrentLinkedQueue = getLogCollection();
        if (logInfoConcurrentLinkedQueue == null) return;

        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        StackTraceElement failStack = stackTraceElements[0];

        StringBuilder sb = new StringBuilder();
        sb.append(msg).append("\n");
        sb.append(throwable.getMessage()).append("\n");
        for (StackTraceElement element : stackTraceElements) {
            // '    {0-class}.{1-methodName}({2-fileName}:{3:numberType})'
            String elText = MessageFormat.format("    {0}.{1}({2}:{3})",
                    element.getClassName(), element.getMethodName(), element.getFileName(), element.getLineNumber());
            sb.append(elText).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);

        LogInfo logInfo = new LogInfo();
        logInfo.setThreadName(Thread.currentThread().getName());
        logInfo.setClassName(failStack.getClassName());
        logInfo.setMethodName(failStack.getMethodName());
        logInfo.setCodeLocation(failStack.getLineNumber());

        logInfo.setMessage(sb.toString());
        // add log to list
        logInfoConcurrentLinkedQueue.add(logInfo);
    }

    private static void log(StackTraceElement useTrace, String text, Object... objects) {
        ConcurrentLinkedQueue<LogInfo> logInfoConcurrentLinkedQueue = getLogCollection();
        if (logInfoConcurrentLinkedQueue == null) return;

        LogInfo logInfo = new LogInfo();
        logInfo.setThreadName(Thread.currentThread().getName());
        logInfo.setClassName(useTrace.getClassName());
        logInfo.setMethodName(useTrace.getMethodName());
        logInfo.setCodeLocation(useTrace.getLineNumber());

        // disable multiple task handler log objects
        if (objects != null && objects.length > 0) {
//            executorService.submit(() -> {
            logInfo.setMessage(margeMessage(text, objects));
            logInfoConcurrentLinkedQueue.add(logInfo);
//            });
            return;
        }

        logInfo.setMessage(text);
        // add log to list
        logInfoConcurrentLinkedQueue.add(logInfo);
    }

    protected static ConcurrentLinkedQueue<LogInfo> getLogCollection() {
        String orderId;
        TaskInfo taskInfo = StoryLogData.getThreadInfo();
        if (taskInfo != null) {
            orderId = taskInfo.getId();
        } else {
            // This function is use to debug mode check story log program has add proxy thread pool
            // Please don't always set print log fail stack
            if (printLoggerFailStack) {
                logger.error("Can't find stack");
                logger.error(Arrays.toString(Thread.currentThread().getStackTrace()));
            }
            return null;
        }

        ConcurrentLinkedQueue<LogInfo> logInfoConcurrentLinkedQueue = StoryLogData.getOrderLogLinked(orderId);
        if (logInfoConcurrentLinkedQueue == null) {
            logger.error("logInfoConcurrentLinkedQueue is null");
        }
        return logInfoConcurrentLinkedQueue;
    }

    /**
     * Text merge
     * may be need thread pool function,but need to be keep log order and add log data to send data before
     */
    public static String margeMessage(String text, Object... objects) {
        StringBuilder sb = new StringBuilder(text);
        StringBuilder tempsb = new StringBuilder();
        int index = -1;
        int inputTextIndex = 0;
        while ((index = sb.indexOf("{}")) > -1) {
            if (objects.length <= inputTextIndex) {
                break;
            }
            tempsb.append(sb.substring(0, index)).append(objects[inputTextIndex]);
            inputTextIndex++;
            sb.delete(0, index + 2);
        }
        tempsb.append(sb.toString());
        return tempsb.toString();
    }

    public static boolean isPrintLoggerFailStack() {
        return printLoggerFailStack;
    }

    public static void setPrintLoggerFailStack(boolean printLoggerFailStack) {
        StoryLogger.printLoggerFailStack = printLoggerFailStack;
    }
}
