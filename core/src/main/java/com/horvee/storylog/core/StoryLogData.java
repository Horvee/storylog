package com.horvee.storylog.core;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * All memory temp save data will be in this
 *
 * */
public class StoryLogData {

    // save now thread info sample to: id,thread name,story code,story title,start and end time
    private static final TransmittableThreadLocal<TaskInfo> orderInfoThreadLocal = new TransmittableThreadLocal<>();

    private static final Map<String, ConcurrentLinkedQueue<LogInfo>> orderLogDataMap = new ConcurrentHashMap<>();

    public static void resetThreadInfo() {
        orderInfoThreadLocal.remove();
    }

    public static void createThreadInfo(TaskInfo taskInfo) {
        orderInfoThreadLocal.set(taskInfo);
        orderLogDataMap.put(taskInfo.getId(),new ConcurrentLinkedQueue<>());
    }

    public static boolean hasOrderInfo() {
        return orderInfoThreadLocal.get() != null;
    }

    public static TaskInfo getThreadInfo() {
        return orderInfoThreadLocal.get();
    }

    public static ConcurrentLinkedQueue<LogInfo> getOrderLogLinked(String orderId) {
        return orderLogDataMap.get(orderId);
    }

    public static void tryRemoveToOrderLogLinked(String orderId) {
        if (orderId == null) {
            return;
        }
        orderLogDataMap.remove(orderId);
    }

}
