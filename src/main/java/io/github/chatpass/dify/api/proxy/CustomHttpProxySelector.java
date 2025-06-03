package io.github.chatpass.dify.api.proxy;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;

public class CustomHttpProxySelector extends ProxySelector {
    private final Proxy proxy;

    public CustomHttpProxySelector(String host, int port) {
        this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
    }

    @Override
    public List<Proxy> select(URI uri) {
        return Collections.singletonList(proxy);
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        // Do something here
    }


}
