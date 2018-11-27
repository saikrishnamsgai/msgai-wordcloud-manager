package ai.msg.wc.service.impl;

import ai.msg.wc.es.models.ConversationEvent;
import ai.msg.wc.service.ConversationBuilderService;
import ai.msg.wc.service.WordCloudService;
import ai.msg.wc.util.PunctuationAnalyzer;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


@Service
public class ConversationBuilderServiceImpl implements ConversationBuilderService {

    private static final String INPUT_FILE = "/App/raw_emails.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversationBuilderServiceImpl.class);

    @Autowired
    WordCloudService wordCloudService;

    @Override
    public void buildConversations() throws Exception{
        File file = new File(INPUT_FILE);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String readLine = readLine = bufferedReader.readLine();
        List<ConversationEvent> conversationEvents = Lists.newArrayList();
        while((readLine = bufferedReader.readLine()) != null) {
            if(!StringUtils.isEmpty(readLine)) {
                ConversationEvent conversationEvent = new ConversationEvent();
                conversationEvent.setConversation(readLine);
                conversationEvent.setUCId(UUID.randomUUID().toString());
                conversationEvent.setId(conversationEvent.getUCId());
                TokenStream tokenStream = PunctuationAnalyzer.instance.getAnalyzer().tokenStream(null, readLine);
                CharTermAttribute cattr = tokenStream.addAttribute(CharTermAttribute.class);
                tokenStream.reset();
                Set<String> keywords = Sets.newHashSet();
                while (tokenStream.incrementToken()) {
                    keywords.add(cattr.toString());
                }
                tokenStream.end();
                tokenStream.close();
                conversationEvent.setKeywords(keywords);
                conversationEvents.add(conversationEvent);
            }
            if(conversationEvents.size() >= 1000){
                LOGGER.info("Saving 1000 messages to ES: " + readLine);
                wordCloudService.saveConversationEvents(conversationEvents);
                conversationEvents = Lists.newArrayList();
            }
        }

        LOGGER.info("Saving "+ conversationEvents.size() + " messages to ES");
        wordCloudService.saveConversationEvents(conversationEvents);
    }

    @Override
    public void reindexConversations(String sourceIndex, String destinationIndex, int size) throws Exception {
       //
    }


}
