package com.horvee.storylog.springboot.interceptor;


import com.horvee.storylog.core.StoryLogData;
import com.horvee.storylog.core.model.dto.TaskInfo;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@ConditionalOnClass(Feign.class)
@ConditionalOnProperty(prefix = "storylog.interceptor", name = "tryLoadParentInfo", havingValue = "true")
@Configuration
@Component
public class TaskRequestLinkFeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // if unknown task info then next server cannot get else task id
        TaskInfo taskInfo = StoryLogData.getThreadInfo();
        if (taskInfo == null) {
            return;
        }

        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (IllegalStateException e) {
            // 当无请求触发或异步触发Feign时,将无法获取当前线程请求对象,该情况将终止操作
            // 如在其它线程进行请求且需要附带请求链,可在相关线程执行 RequestContextHolder.setRequestAttributes()
            // Unknown request object , nothing to do!
        }

        if (request != null) {
            // form network request
            String taskTopId = request.getHeader(RequestHeaderConstants.TASK_TOP_ID);
            // add next request
            if (taskTopId == null || taskTopId.isEmpty()) {
                template.header(RequestHeaderConstants.TASK_TOP_ID, taskInfo.getId());
            } else {
                template.header(RequestHeaderConstants.TASK_TOP_ID, taskTopId);
                template.header(RequestHeaderConstants.TASK_PARENT_ID, taskInfo.getId());
            }
        } else {
            // form kafka、mq...
            template.header(RequestHeaderConstants.TASK_TOP_ID, taskInfo.getId());
        }
    }
}
