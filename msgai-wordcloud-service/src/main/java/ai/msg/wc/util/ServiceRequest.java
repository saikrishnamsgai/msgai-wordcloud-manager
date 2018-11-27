package ai.msg.wc.util;

import ai.msg.domain.api.model.ContextualAttributeServiceObject;
import ai.msg.domain.requests.pi.APIMetaInfo;
import ai.msg.domain.requests.pi.IntegrationRequest;

import java.util.Map;

public class ServiceRequest {

    private APIMetaInfo apiMetaInfo;
    private IntegrationRequest integrationRequest;
    private Map<String, ContextualAttributeServiceObject> piParameters;

}
