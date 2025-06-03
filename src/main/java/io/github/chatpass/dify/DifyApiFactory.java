package io.github.chatpass.dify;

import io.github.chatpass.dify.api.*;
import io.github.chatpass.dify.api.*;

public class DifyApiFactory {
    private final String baseUrl;
    private final String apiKey;

    private DifyApiFactory(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public static DifyApiFactory newInstance(String baseUrl, String apiKey) {
        return new DifyApiFactory(baseUrl,apiKey);
    }

    public DifyBaseApi newDifyBaseApi(){
        return new DifyBaseApi(baseUrl,apiKey);
    }

    public DifyChatApi newDifyChatApi(){
        return new DifyChatApi(baseUrl,apiKey);
    }

    public DifyChatFlowApi newDifyChatFlowApi() {
        return new DifyChatFlowApi(baseUrl, apiKey);
    }

    public DifyCompletionApi newDifyCompletionApi() {
        return new DifyCompletionApi(baseUrl, apiKey);
    }

    public DifyDatasetsApi newDifyDatasetsApi() {
        return new DifyDatasetsApi(baseUrl, apiKey);
    }

    public DifyWorkflowApi newDifyWorkflowApi() {
        return new DifyWorkflowApi(baseUrl, apiKey);
    }
}
