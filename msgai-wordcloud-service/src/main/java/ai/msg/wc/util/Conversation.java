package ai.msg.wc.util;


import ai.msg.wc.es.models.ConversationEvent;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.common.collect.Tuple;

@Setter
@Getter
public class Conversation {

    private String message;

    private String ucId;

    private float score;

    private Tuple<Integer, Integer> highlightIndices;

    private String intentId;

    private String clusterName;

    private String clusterId;

    private Boolean isTagged = Boolean.FALSE;

    public Conversation(){

    }

    public Conversation(ConversationEvent conversationEvent){
        this.message = conversationEvent.getConversation();
        this.ucId = conversationEvent.getUCId();
        this.intentId = conversationEvent.getMetaIntentId();
    }

}
