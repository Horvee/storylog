package com.horvee.storylog.springboot.interceptor;

import com.horvee.storylog.core.StoryLogData;
import com.horvee.storylog.core.StoryLogSendDriver;
import com.horvee.storylog.core.annotation.StoryTag;
import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.TaskInfo;
import com.horvee.storylog.springboot.configuration.config.interceptor.StoryLogHandlerInterceptorProperties;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StoryLogInterceptor implements HandlerInterceptor {

    private final StoryLogHandlerInterceptorProperties properties;

    public StoryLogInterceptor(StoryLogHandlerInterceptorProperties properties) {
        this.properties = properties;
    }

    @SuppressWarnings({"All"})
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // if is story mode then create order id
        boolean isHandlerMethod = handler instanceof HandlerMethod;
        if (!isHandlerMethod) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        StoryTag storyTag = handlerMethod.getMethod().getAnnotation(StoryTag.class);
        if (storyTag == null) return true;

        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId(UUID.randomUUID().toString().replace("-",""));
        taskInfo.setOrderThreadName(Thread.currentThread().getName());
        taskInfo.setStoryCode(storyTag.code());
        taskInfo.setStoryTitle(storyTag.title());
        taskInfo.setStartTime(System.currentTimeMillis());

        // check need to parent id value config
        if (properties.isTryLoadParentInfo()) {
            String requestTaskParentId = request.getHeader(RequestHeaderConstants.TASK_PARENT_ID);
            String requestTaskTopId = request.getHeader(RequestHeaderConstants.TASK_TOP_ID);

            if (requestTaskTopId == null || requestTaskTopId.isEmpty()) {
                taskInfo.setTopCallId(taskInfo.getId());
            } else {
                taskInfo.setParentCallId(requestTaskParentId);
                taskInfo.setTopCallId(requestTaskTopId);
            }
        }

        StoryLogData.createThreadInfo(taskInfo);

        // anyway is true
        return true;
    }

    @SuppressWarnings({"All"})
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!StoryLogData.hasOrderInfo()) {
            return;
        }

        // end story
        TaskInfo taskInfo = StoryLogData.getThreadInfo();
        taskInfo.setEndTime(System.currentTimeMillis());
        ConcurrentLinkedQueue<LogInfo> logInfos = StoryLogData.getOrderLogLinked(taskInfo.getId());
        StoryLogData.resetThreadInfo();
        StoryLogData.tryRemoveToOrderLogLinked(taskInfo.getId());
        // send data | This send will be use Kafka、MQ、Http
        StoryLogSendDriver.getDrive().send(taskInfo,logInfos);
    }

}
