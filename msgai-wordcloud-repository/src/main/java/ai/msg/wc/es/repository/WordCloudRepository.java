package ai.msg.wc.es.repository;


import ai.msg.wc.es.models.ConversationEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WordCloudRepository extends ElasticsearchRepository<ConversationEvent, String> {

    public ConversationEvent findByConversationId(String id);

}
