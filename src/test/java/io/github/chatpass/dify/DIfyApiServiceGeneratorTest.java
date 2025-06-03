package io.github.chatpass.dify;

import io.github.chatpass.dify.api.callback.StreamCallback;
import io.github.chatpass.dify.exception.DifyApiException;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DIfyApiServiceGeneratorTest {

    @BeforeAll
    public static void setup() {
        // Configure the shared client with custom settings
        OkHttpClient customClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .pingInterval(15, TimeUnit.SECONDS)
                .build();
        DIfyApiServiceGenerator.configureHttpClient(customClient);
    }

    public interface TestService{
        // Define service methods if needed
    }

    @Test
    public void testCreateService() {
        // Create a service instance
        TestService service = DIfyApiServiceGenerator.createService(TestService.class, "your_api_key", "https://api.example.com/");
        assertNotNull(service);
    }

    @Test
    public void testExecuteSyncSuccess() throws IOException {
        // Mock a successful response
        Call<String> mockCall = mock(Call.class);
        when(mockCall.execute()).thenReturn(Response.success("Success"));

        // Execute the call
        String result = DIfyApiServiceGenerator.executeSync(mockCall);

        // Verify the result
        assertEquals("Success", result);
    }

    @Test
    public void testExecuteSyncFailure() throws IOException {
        // Mock a failed response
        Call<String> mockCall = mock(Call.class);
        Response<String> mockResponse = Response.error(400, ResponseBody.create(null, "Error"));
        when(mockCall.execute()).thenReturn(mockResponse);

        // Execute the call and expect an exception
        assertThrows(DifyApiException.class, () -> DIfyApiServiceGenerator.executeSync(mockCall));
    }

    @Test
    public void testSetHttpProxy() {
        // Set a proxy
        DIfyApiServiceGenerator.setHttpProxy("proxy.example.com", 8080, "username", "password");

        // Verify that the shared client is configured with the proxy
        // This is a bit tricky to test directly, but you can verify by checking the client configuration
        OkHttpClient client = DIfyApiServiceGenerator.createDefaultHttpClient();
        assertNotNull(client);
    }

    @Test
    public void testProcessStreamLine() {
        // Mock callback and event processor
        StreamCallback mockCallback = mock(StreamCallback.class);
        DIfyApiServiceGenerator.EventProcessor mockProcessor = mock(DIfyApiServiceGenerator.EventProcessor.class);

        // Test processing a valid line
        boolean result = DIfyApiServiceGenerator.processStreamLine("data: {\"event\":\"test\"}", mockCallback, mockProcessor);
        assertTrue(result);

        // Test processing a done marker
        result = DIfyApiServiceGenerator.processStreamLine("data: [DONE]", mockCallback, mockProcessor);
        assertFalse(result);
    }
}
