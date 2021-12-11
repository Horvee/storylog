package com.horvee.storylog.core;

import java.util.List;

import com.horvee.storylog.core.model.dto.LogInfo;
import com.horvee.storylog.core.model.dto.storydetail.StoryInfo;
import com.horvee.storylog.core.model.dto.storydetail.StoryInfoGroupBase;
import com.horvee.storylog.core.model.dto.storydetail.ThreadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use to print story log data
 *
 * */
public class Printer {

    private static final Logger log = LoggerFactory.getLogger(Printer.class);

    public static void simplePrintBaseLog(StoryInfo storyInfo) {
        String logText = simpleOutPutLogText(storyInfo.getLogList());
        log.error("-----------------------------------------------------------------------");
        log.error("id:" + storyInfo.getId());
        log.error("title:" + storyInfo.getStoryTitle());
        log.error("code:" + storyInfo.getStoryCode());
        log.error("useTime:" + storyInfo.getUseTime());
        log.error(logText);
    }

    /**
     * Create text by simple splice story log
     * */
    public static String simpleOutPutLogText(List<StoryInfoGroupBase> logList) {

        StringBuilder sb = new StringBuilder();

        for (StoryInfoGroupBase item:logList) {
            if (item.getThreadType() == ThreadType.MAIN) {
                sb.append("\n[Main]");
                for (LogInfo logInfoItem:item.getLogInfoList()) {
                    sb.append(String.format("\n%s : %s",logInfoItem.getClassName(),logInfoItem.getMessage()));

                }
                continue;
            }

            //            System.out.println("·········· [Child]");
            sb.append(String.format("\n····· [Child][%s]",item.getLogInfoList().get(0).getThreadName()));
            for (LogInfo logInfoItem:item.getLogInfoList()) {
                sb.append(String.format("\n·········| %s : %s",logInfoItem.getClassName(),logInfoItem.getMessage()));
            }

        }

        return sb.delete(0,1).toString();
    }

}
