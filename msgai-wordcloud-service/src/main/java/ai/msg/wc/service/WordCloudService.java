package ai.msg.wc.service;

import ai.msg.wc.es.models.ConversationEvent;
import ai.msg.wc.util.Conversation;
import ai.msg.wc.util.WordCloudBucket;

import java.util.List;
import java.util.Set;

public interface WordCloudService {

    List<WordCloudBucket> findWordClouds(String request);

    List<Conversation> findMessagesByWords(Integer page, Integer limit, Set<String> keywords) throws Exception;

    ConversationEvent saveConversationEvent(ConversationEvent event);

    ConversationEvent saveConversationEvents(List<ConversationEvent> events);

    ConversationEvent updateConversationEvents(List<ConversationEvent> events);

    ConversationEvent updateConversationEvents(List<String> keywords, String intentId);

}
