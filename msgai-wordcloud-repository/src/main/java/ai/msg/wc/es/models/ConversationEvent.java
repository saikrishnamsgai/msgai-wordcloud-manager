package ai.msg.wc.es.models;

import ai.msg.domain.requests.druid.data.ConversationEventData;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@Document(indexName = "conversation_event", type = "conversation_event")
public class ConversationEvent implements Serializable {

    @Id
    private String id;
    private String conversationId;
    private String uCId;
    private String conversation;
    private Set<String> keywords;
    private Map<String, String> posTagMap;
    private String botId;
    private String metaIntentId;
    private String clusterId;
    private Boolean isTagged;

    private Timestamp updatedAt;
    private Timestamp createdAt;



}
