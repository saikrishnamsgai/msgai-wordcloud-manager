package ai.msg.wc.gateway.controllers;

import ai.msg.domain.responses.ServiceResponse;
import ai.msg.wc.es.models.ConversationEvent;
import ai.msg.wc.service.ConversationBuilderService;
import ai.msg.wc.service.WordCloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/conversations")
@Slf4j
public class ConversationEventController {

    @Autowired
    private WordCloudService wordCloudService;

    @Autowired
    private ConversationBuilderService conversationBuilderService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ServiceResponse> modifyWordCloud( @RequestHeader HttpHeaders headers) throws
            Throwable {
        ServiceResponse serviceResponse = new ServiceResponse();
        conversationBuilderService.buildConversations();
        serviceResponse.setStatusCode("SUCCESS");
        serviceResponse.setStatusMessage("Bot Created successfully");
        serviceResponse.setPayload("SUCCESS");
        return new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/re-index", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ServiceResponse> reindexCOnversation( @RequestHeader HttpHeaders headers) throws
            Throwable {
        ServiceResponse serviceResponse = new ServiceResponse();
        conversationBuilderService.reindexConversations("conversation_event", "conversation_event_v1", 1000);
        serviceResponse.setStatusCode("SUCCESS");
        serviceResponse.setStatusMessage("Bot Created successfully");
        serviceResponse.setPayload("SUCCESS");
        return new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.OK);

    }

//    @RequestMapping(value = "/bots/{botId}", method = RequestMethod.PUT, produces = "application/json")
//    public ResponseEntity<ServiceResponse> updateBody(@RequestBody Bot bot,@PathVariable(value="botId") String botId,
//                                                      @RequestHeader HttpHeaders headers) throws Throwable {
//        ServiceResponse serviceResponse = new ServiceResponse();
//        Bot updatedBot = botManagementProcessor.updateBot(botId, bot);
//        serviceResponse.setStatusCode("SUCCESS");
//        serviceResponse.setStatusMessage("Bot Updated successfully");
//        serviceResponse.setPayload(updatedBot);
//        return new ResponseEntity<ServiceResponse>(serviceResponse, HttpStatus.OK);
//    }

}
