package com.horvee.storylog.core;

import com.horvee.storylog.core.exceptions.InterceptorObjectException;
import com.horvee.storylog.core.annotation.StoryTag;
import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Proxy object then will be start and end story log
 *
 * */
public class ProxyObjectInterceptor {

    private final static Logger log = LoggerFactory.getLogger(ProxyObjectInterceptor.class);
    private final static StoryLogSender storyLogSender = StoryLogSendDriver.getDrive();

    /**
     * Just work by has 'StoryTag' (You must be use 'StoryTag' to interface,if you don't do it then can't be work)
     *
     * */
    public static <T> Object create(T obj) {
        Class<?> targetObjClass = obj.getClass();
        if (targetObjClass.isInterface()) {
            return Proxy.newProxyInstance(
                    obj.getClass().getClassLoader(),
                    new Class[]{targetObjClass},
                    new ObjectProxyInvocationHandler(obj)
            );
        }

        Class<?>[] targetInterface = targetObjClass.getInterfaces();
        if (targetInterface.length <= 0) {
            throw new InterceptorObjectException("Unsupported target,this class is unknown interface!");
        }
        return Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                targetInterface,
                new ObjectProxyInvocationHandler(obj)
        );
    }

    /**
     * Just work by has 'StoryTag' (You must be use 'StoryTag' to interface,if you don't do it then can't be work)
     *
     * */
    public static <T> T create(T obj,Class<T> targetObjClass) {
        if (!targetObjClass.isInterface()) {
            throw new InterceptorObjectException("This target object class is not interface");
        }
        if (!targetObjClass.isInstance(obj)) {
            throw new InterceptorObjectException("This target object class is not instance object");
        }
        return targetObjClass.cast(
                Proxy.newProxyInstance(
                        obj.getClass().getClassLoader(),
                        new Class[]{targetObjClass},
                        new ObjectProxyInvocationHandler(obj)
                )
        );
    }

    /**
     * Work by object interface
     *
     * */
    public static <T> Object fullProxy(int storyCode, String storyTitle, T obj) {
        Class<?> targetObjClass = obj.getClass();
        if (targetObjClass.isInterface()) {
            return Proxy.newProxyInstance(
                    obj.getClass().getClassLoader(),
                    new Class[]{targetObjClass},
                    new ObjectProxyInvocationHandler(obj)
            );
        }

        Class<?>[] targetInterface = targetObjClass.getInterfaces();
        if (targetInterface.length <= 0) {
            throw new InterceptorObjectException("Unsupported target,this class is unknown interface!");
        }
        return Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                targetInterface,
                new FullProxyInvocationHandler(obj,storyCode,storyTitle)
        );
    }

    /**
     * Work by target object interface
     *
     * */
    public static <T> T fullProxy(int storyCode, String storyTitle, T obj,Class<T> targetObjClass) {
        if (!targetObjClass.isInterface()) {
            throw new InterceptorObjectException("This target object class is not interface");
        }
        if (!targetObjClass.isInstance(obj)) {
            throw new InterceptorObjectException("This target object class is not instance object");
        }
        return targetObjClass.cast(
                Proxy.newProxyInstance(
                        obj.getClass().getClassLoader(),
                        new Class[]{targetObjClass},
                        new FullProxyInvocationHandler(obj,storyCode,storyTitle)
                )
        );
    }

    static class ObjectProxyInvocationHandler implements InvocationHandler {

        private final Object proxyObj;
        private final Class<?> proxyObjClass;

        public ObjectProxyInvocationHandler(Object proxyObj) {
            this.proxyObj = proxyObj;
            this.proxyObjClass = proxyObj.getClass();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object feedBackValue;
//            Method trueMethod;
//            if (args == null) {
//                trueMethod = proxyObjClass.getMethod(method.getName());
//            } else {
//                trueMethod = proxyObjClass.getMethod(method.getName(), Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
//            }
//            StoryTag storyTag = trueMethod.getAnnotation(StoryTag.class);
            StoryTag storyTag = method.getAnnotation(StoryTag.class);

            if (storyTag == null) {
                return method.invoke(proxyObj,args);
            }

            // If now thread has task,then not be create
            if (StoryLogData.hasOrderInfo()) {
                if (StoryLogger.isPrintLoggerFailStack()) {
                    log.error("StoryLog task is create! \nTaskInfo:{} \nInvokeMethod:{}#{}   "
                            ,StoryLogData.getThreadInfo()
                            ,proxyObj.getClass().getName()
                            ,method.getName());
                }
                return method.invoke(proxyObj,args);
            }

            // has tag
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setId(UUID.randomUUID().toString().replace("-",""));
            taskInfo.setOrderThreadName(Thread.currentThread().getName());
            taskInfo.setStoryCode(storyTag.code());
            taskInfo.setStoryTitle(storyTag.title());
            taskInfo.setStartTime(System.currentTimeMillis());

            StoryLogData.createThreadInfo(taskInfo);
            try {
                feedBackValue = method.invoke(proxyObj, args);
            } catch (InvocationTargetException e) {
                StoryLogger.log("InvocationTargetException",e.getTargetException());
                throw e.getTargetException();
            } finally {
                taskInfo.setEndTime(System.currentTimeMillis());
                ConcurrentLinkedQueue<LogInfo> logInfos = StoryLogData.getOrderLogLinked(taskInfo.getId());
                StoryLogData.resetThreadInfo();
                StoryLogData.tryRemoveToOrderLogLinked(taskInfo.getId());
                // send data | This send will be use Kafka、MQ、Http
                storyLogSender.send(taskInfo,logInfos);
            }

            return feedBackValue;
        }
    }

    static class FullProxyInvocationHandler implements InvocationHandler {

        private final Object proxyObj;
        private final int code;
        private final String title;

        public FullProxyInvocationHandler(Object proxyObj, int code, String title) {
            this.proxyObj = proxyObj;
            this.code = code;
            this.title = title;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // If now thread has task,then not be create
            if (StoryLogData.hasOrderInfo()) {
                if (StoryLogger.isPrintLoggerFailStack()) {
                    log.error("StoryLog task is create! \nTaskInfo:{} \nInvokeMethod:{}#{}   "
                            ,StoryLogData.getThreadInfo()
                            ,proxyObj.getClass().getName()
                            ,method.getName());
                }
                return method.invoke(proxyObj,args);
            }

            Object feedBackValue;

            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setId(UUID.randomUUID().toString().replace("-",""));
            taskInfo.setOrderThreadName(Thread.currentThread().getName());
            taskInfo.setStoryCode(code);
            taskInfo.setStoryTitle(title);
            taskInfo.setStartTime(System.currentTimeMillis());

            StoryLogData.createThreadInfo(taskInfo);
            try {
                feedBackValue = method.invoke(proxyObj, args);
            } catch (InvocationTargetException e) {
                StoryLogger.log("InvocationTargetException",e.getTargetException());
                throw e.getTargetException();
            } finally {
                taskInfo.setEndTime(System.currentTimeMillis());
                ConcurrentLinkedQueue<LogInfo> logInfos = StoryLogData.getOrderLogLinked(taskInfo.getId());
                StoryLogData.resetThreadInfo();
                StoryLogData.tryRemoveToOrderLogLinked(taskInfo.getId());
                // send data | This send will be use Kafka、MQ、Http
                storyLogSender.send(taskInfo,logInfos);
            }

            return feedBackValue;
        }
    }

}
