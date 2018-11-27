package ai.msg.wc.service;

public interface ConversationBuilderService {

    void buildConversations() throws Exception;

    void reindexConversations(String sourceIndex, String destinationIndex, int size) throws Exception;
}
