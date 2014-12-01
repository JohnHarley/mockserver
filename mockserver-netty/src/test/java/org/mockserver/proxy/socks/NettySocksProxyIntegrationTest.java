package org.mockserver.proxy.socks;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.mockserver.client.proxy.ProxyClient;
import org.mockserver.echo.EchoServer;
import org.mockserver.integration.proxy.AbstractClientProxyIntegrationTest;
import org.mockserver.proxy.Proxy;
import org.mockserver.proxy.ProxyBuilder;
import org.mockserver.socket.PortFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jamesdbloom
 */
@Ignore
public class NettySocksProxyIntegrationTest extends AbstractClientProxyIntegrationTest {

    private final static Logger logger = LoggerFactory.getLogger(NettySocksProxyIntegrationTest.class);

    private final static Integer SERVER_HTTP_PORT = PortFactory.findFreePort();
    private final static Integer SERVER_HTTPS_PORT = PortFactory.findFreePort();
    private final static Integer PROXY_HTTP_PORT = PortFactory.findFreePort();
    private final static Integer PROXY_DIRECT_PORT = PortFactory.findFreePort();
    private static EchoServer echoServer;
    private static Proxy httpProxy;
    private static ProxyClient proxyClient;

    @BeforeClass
    public static void setupFixture() throws Exception {
        logger.debug("SERVER_HTTP_PORT = " + SERVER_HTTP_PORT);
        logger.debug("SERVER_HTTPS_PORT = " + SERVER_HTTPS_PORT);
        logger.debug("PROXY_HTTP_PORT = " + PROXY_HTTP_PORT);
        logger.debug("PROXY_DIRECT_PORT = " + PROXY_DIRECT_PORT);

        // start server
        echoServer = new EchoServer(SERVER_HTTP_PORT);

        // start proxy
        httpProxy = new ProxyBuilder()
                .withLocalPort(PROXY_HTTP_PORT)
                .build();

        // start client
        proxyClient = new ProxyClient("localhost", PROXY_HTTP_PORT);
    }

    @AfterClass
    public static void shutdownFixture() {
        // stop server
        echoServer.stop();

        // stop proxy
        httpProxy.stop();
    }

    @Before
    public void resetProxy() {
        proxyClient.reset();
    }

    @Override
    public int getProxyPort() {
        return PROXY_HTTP_PORT;
    }

    @Override
    public int getServerPort() {
        return SERVER_HTTP_PORT;
    }
}
