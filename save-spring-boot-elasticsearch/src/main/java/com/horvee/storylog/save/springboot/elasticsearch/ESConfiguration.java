package com.horvee.storylog.save.springboot.elasticsearch;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories
//@EnableElasticsearchRepositories("com.mh.storylog.save.springboot.elasticsearch")
public class ESConfiguration {

}
