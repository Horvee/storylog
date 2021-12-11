package com.horvee.storylog.springboot.configuration.config;

import com.horvee.storylog.core.StoryLogger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "storylog")
public class StoryLogConfigProperties {

    /**
     * 是否打印调用了StoryLogger,找不到调用盏的信息
     *
     * @see StoryLogger
     */
    private Boolean printLoggerFailStack = false;

    /**
     * Use transfer component,default value is unable
     * Because transfer component,work by send and accept service,if you use micro service then accept service default unable
     */
    private Boolean transferAcceptMessage = false;

    /**
     * Send message protect,if out of handler message!then create new message just will be print log framework
     * */
    private int senderProtectNum = Integer.MAX_VALUE;

    public Boolean getPrintLoggerFailStack() {
        return printLoggerFailStack;
    }

    public void setPrintLoggerFailStack(Boolean printLoggerFailStack) {
        this.printLoggerFailStack = printLoggerFailStack;
        if (printLoggerFailStack) {
            StoryLogger.setPrintLoggerFailStack(printLoggerFailStack);
        }
    }

    public Boolean getTransferAcceptMessage() {
        return transferAcceptMessage;
    }

    public void setTransferAcceptMessage(Boolean transferAcceptMessage) {
        this.transferAcceptMessage = transferAcceptMessage;
    }

    public int getSenderProtectNum() {
        return senderProtectNum;
    }

    public void setSenderProtectNum(int senderProtectNum) {
        this.senderProtectNum = senderProtectNum;
    }
}
