package com.horvee.storylog.save.springboot.elasticsearch;


import com.horvee.storylog.core.StoryLogSaver;
import com.horvee.storylog.core.model.dto.storydetail.StoryInfo;
import com.horvee.storylog.save.springboot.elasticsearch.dao.StoryLogInfoDao;
import com.horvee.storylog.save.springboot.elasticsearch.model.StoryLogInfo;
import com.horvee.storylog.save.springboot.elasticsearch.model.StoryInfoGroupBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class StoryLogSaveComponent implements StoryLogSaver {

    private final static Logger log = LoggerFactory.getLogger(StoryLogSaveComponent.class);

    @Autowired
    private StoryLogInfoDao storyLogInfoDao;

//    private final StoryLogInfoDao storyLogInfoDao;
//
//    public StoryLogSaveComponent(StoryLogInfoDao storyLogInfoDao) {
//        this.storyLogInfoDao = storyLogInfoDao;
//    }

    @PostConstruct
    private void init() {
        // Register component in binder
        SpringBootElasticSearchStoryLogSaveBinder.registerService(this);
    }

    @Override
    public void saveLog(StoryInfo storyInfo) {
        StoryLogInfo storyLogInfo = new StoryLogInfo();
        BeanUtils.copyProperties(storyInfo,storyLogInfo);

        storyLogInfo.setLogContent(
                storyInfo.getLogList().stream().map(item -> {
                    StoryInfoGroupBase storyInfoGroupBase = new StoryInfoGroupBase();
                    storyInfoGroupBase.setThreadType(item.getThreadType());
                    storyInfoGroupBase.setLogInfoList(item.getLogInfoList());
                    return storyInfoGroupBase;
                }).collect(Collectors.toList())
        );
        storyLogInfoDao.save(storyLogInfo);
    }
}
