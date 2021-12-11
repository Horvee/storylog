package com.horvee.storylog.save.springboot.elasticsearch.dao;

import com.horvee.storylog.save.springboot.elasticsearch.model.StoryLogInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StoryLogInfoDao extends ElasticsearchRepository<StoryLogInfo, String> {

}
