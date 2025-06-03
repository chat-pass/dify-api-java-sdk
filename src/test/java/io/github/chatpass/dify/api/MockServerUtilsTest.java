package io.github.chatpass.dify.api;

import io.github.chatpass.dify.DifyApiFactory;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class MockServerUtilsTest {

    public static MockWebServer mockWebServer;
    public static String baseUrl;
    public static String apiKey;
    public static DifyBaseApi difyBaseApi;
    public static DifyChatApi difyChatApi;

    public static DifyChatFlowApi difyChatFlowApi;
    public static DifyCompletionApi difyCompletionApi;

    public static DifyDatasetsApi difyDatasetsApi;
    public static DifyWorkflowApi difyWorkflowApi;

    @BeforeEach
    public void setUp() throws IOException {

        mockWebServer = new MockWebServer();
        mockWebServer.start();
        baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        apiKey = "df4UIkhjdli48574654SDsdf55s4";
        DifyApiFactory factory = DifyApiFactory.newInstance(baseUrl,apiKey);

        difyBaseApi = factory.newDifyBaseApi();
        difyChatApi = factory.newDifyChatApi();
        difyChatFlowApi = factory.newDifyChatFlowApi();
        difyCompletionApi = factory.newDifyCompletionApi();
        difyDatasetsApi = factory.newDifyDatasetsApi();
        difyWorkflowApi = factory.newDifyWorkflowApi();
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.close();
        mockWebServer.shutdown();
    }
}
