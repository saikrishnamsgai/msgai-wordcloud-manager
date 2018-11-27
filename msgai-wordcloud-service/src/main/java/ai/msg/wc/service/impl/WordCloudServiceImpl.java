package ai.msg.wc.service.impl;


import ai.msg.wc.es.models.ConversationEvent;
import ai.msg.wc.es.repository.WordCloudRepository;
import ai.msg.wc.service.WordCloudService;

import ai.msg.wc.util.ConstantUtils;
import ai.msg.wc.util.Conversation;
import ai.msg.wc.util.WordCloudBucket;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
public class WordCloudServiceImpl implements WordCloudService {

    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    WordCloudRepository wordCloudRepository;

    @Override
    public List<WordCloudBucket> findWordClouds(String request){
        List<WordCloudBucket> bucketList = Lists.newArrayList();
        BoolQueryBuilder queryBuilder = tokenizeAndBuildQuery(null);
        TermsBuilder termsBuilder = new TermsBuilder("keywords");
        termsBuilder.field("keywords");
        termsBuilder.exclude(ConstantUtils.excludeWords);
        termsBuilder.size(1000);
        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch().setQuery(queryBuilder).addAggregation(termsBuilder).execute().actionGet();
        Terms terms = response.getAggregations().get("keywords");
        List<Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            bucketList.add(new WordCloudBucket(bucket.getKeyAsString(), bucket.getDocCount()));
        }
        return bucketList;
    }

    @Override
    public List<Conversation> findMessagesByWords(Integer page, Integer limit, Set<String> keywords) throws IOException {
        BoolQueryBuilder queryBuilder = tokenizeAndBuildQuery(keywords);
        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch().setQuery(queryBuilder).setFrom(page).setSize(limit).execute().actionGet();
        List<Conversation> events = Lists.newArrayList();
        if(response.getHits().getTotalHits() <= 0){
            return events;
        }
        for(SearchHit hit:  response.getHits()){
            ObjectMapper objectMapper = new ObjectMapper();
            Conversation conversation = new Conversation(objectMapper.readValue(hit.getSourceAsString(), ConversationEvent.class));
            conversation.setScore(hit.getScore());
            events.add(conversation);
        }
        return events;
    }


    private BoolQueryBuilder tokenizeAndBuildQuery(Set<String> keywords) {
        BoolQueryBuilder builder = new BoolQueryBuilder();
        if(CollectionUtils.isEmpty(keywords)) {
            return builder;
        }
        for(String query : keywords) {
            builder.must(QueryBuilders.queryStringQuery(query).field("keywords"));
        }
        return builder;
    }


//    public ConversationEvent updateConversationEvent(List<Conversation> conversations){
//
//    }

    @Override
    public ConversationEvent saveConversationEvent(ConversationEvent event) {
        wordCloudRepository.save(event);
        return null;
    }

    @Override
    public ConversationEvent saveConversationEvents(List<ConversationEvent> events) {
        wordCloudRepository.save(events);
        return null;
    }

    @Override
    public ConversationEvent updateConversationEvents(List<ConversationEvent> events) {
        return null;
    }

    @Override
    public ConversationEvent updateConversationEvents(List<String> keywords, String intentId) {
        return null;
    }
}
