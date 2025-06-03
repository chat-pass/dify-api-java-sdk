package io.github.chatpass.dify.api.proxy;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class CustomProxyAuthenticator implements Authenticator {

    private final String CREDENTIALS;

    public CustomProxyAuthenticator(final String username, final String password) {
        CREDENTIALS = Credentials.basic(username, password);
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
        return response.request().newBuilder()
                .header("Proxy-Authorization", CREDENTIALS)
                .build();
    }
}
