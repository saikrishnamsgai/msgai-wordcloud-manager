package ai.msg.wc.gateway.controllers;

import ai.msg.domain.constants.Constants;
import ai.msg.domain.exceptions.NonRecoverableException;
import ai.msg.domain.responses.ServiceResponse;
import ai.msg.wc.es.models.ConversationEvent;
import ai.msg.wc.service.WordCloudService;
import ai.msg.wc.util.Conversation;
import ai.msg.wc.util.InternalSearchRequest;
import ai.msg.wc.util.WordCloudBucket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wordcloud")
@Slf4j
public class WordCloudController {

    @Autowired
    private WordCloudService wordCloudService;

    @RequestMapping(value = "/words", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ServiceResponse> getWordclouds(@RequestBody String request, @RequestHeader HttpHeaders headers) throws Throwable {
        ServiceResponse serviceResponse = new ServiceResponse();
        //XContentBuilder searchSourceBuilder = new ObjectMapper().readValue(request, XContentBuilder.class);

        List<WordCloudBucket> events = wordCloudService.findWordClouds(request);
        serviceResponse.setStatusCode("SUCCESS");
        serviceResponse.setStatusMessage("Fetched bot details successfully");
        serviceResponse.setPayload(events);
        return new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ServiceResponse> getmessageByWords(@RequestHeader HttpHeaders headers) throws Throwable {
        ServiceResponse serviceResponse = new ServiceResponse();
        List<Conversation> events = wordCloudService.findMessagesByWords(0,100, Sets.newHashSet());
        serviceResponse.setStatusCode("SUCCESS");
        serviceResponse.setStatusMessage("Fetched bot details successfully");
        serviceResponse.setPayload(events);
        return new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.OK);
    }

}
