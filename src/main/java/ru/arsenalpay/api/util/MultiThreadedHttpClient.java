package ru.arsenalpay.api.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import ru.arsenalpay.api.exception.HttpClientInitializingException;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <p>Singleton Apache Http Client class configured for concurrency environment.</p>
 *
 * @author adamether
 */
public final class MultiThreadedHttpClient {

    public static final String USER_AGENT = "JAVA-SDK";

    /**
     * Setting timeout in seconds
     * */
    public static final String DEFAULT_SOCKET_TIMEOUT = "30";
    public static final String DEFAULT_CONNECTION_TIMEOUT = "30";

    public static final int FACTOR = 1000;

    private final CloseableHttpClient httpClient;

    /**
     * Private constructor prevents instantiation from other classes
     */
    private MultiThreadedHttpClient() {
        /** configured connection manager for ignoring SSL certificate (trust all mode) */
        HttpClientConnectionManager connectionManager = getСonfiguredConnectionManager();

        RequestConfig config = getDefaultRequestConfig();

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .setUserAgent(USER_AGENT)
                .build();
    }

    /**
     * <p>Return default request config instance with custom socketTimeout and connectionTimeout
     * @return requestConfig instance {@link org.apache.http.client.config.RequestConfig}</p>
     */
    private RequestConfig getDefaultRequestConfig() {
        int socketTimeout = Integer.valueOf(
                Configuration.getProp("socket_timeout", DEFAULT_SOCKET_TIMEOUT)
        );
        int connectionTimeout = Integer.valueOf(
                Configuration.getProp("connection_timeout", DEFAULT_CONNECTION_TIMEOUT)
        );

        return RequestConfig.custom()
                    .setSocketTimeout(socketTimeout * FACTOR)
                    .setConnectTimeout(connectionTimeout * FACTOR)
                    .build();
    }

    /**
     * <p>Create configured connection manager with:</p>
     * <p>Now it is ThreadSafeClientConnManager implementation of HttpClientConnectionManager interface.
     * This connection manager used for if more than one thread will be using the HttpClient.</p>
     * <p><b>The first.</b> Actions for ignoring SSL certificate (trust all mode).
     * In the near future we will replace in server side the self-signed certificate on a solid and this code
     * will be deleted. At this point further ArsenalPay certificate does not make sense, since trust is built
     * in java resources certified official CA, such as GTE CyberTrust Solutions, Inc.</p>
     * <p><b>The second.</b>We have fixed total max connection size.</p>
     */
    private HttpClientConnectionManager getСonfiguredConnectionManager() {
        try {
            SSLContextBuilder builder = SSLContexts.custom();
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    return true;
                }
            });
            SSLContext sslContext = builder.build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create().register("https", sslsf)
                    .build();

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry
            );

            final String connectionSize = Configuration.getProp("max_total_connection_size");
            connectionManager.setMaxTotal(Integer.valueOf(connectionSize));

            return connectionManager;
        } catch (NoSuchAlgorithmException e) {
            throw new HttpClientInitializingException();
        } catch (KeyStoreException e) {
            throw new HttpClientInitializingException();
        } catch (KeyManagementException e) {
            throw new HttpClientInitializingException();
        }
    }

    /**
     * SingletonHolder is loaded on the first execution of MultiThreadedHttpClient.getInstance()
     * or the first access to MultiThreadedHttpClient.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private static final MultiThreadedHttpClient INSTANCE = new MultiThreadedHttpClient();
    }

    /**
     * Get singleton multithreaded http client wrapper
     * @return type {@link ru.arsenalpay.api.util.MultiThreadedHttpClient}
     */
    public static MultiThreadedHttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Get configured closeable http client
     * @return object of type {@link org.apache.http.impl.client.CloseableHttpClient}
     */
    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Manually finalizing of system resources witch were allocated for http client
     * <p>In exceptional situations you can call this method, but usually lifetime http client lifetime equals
     * your application lifetime. Allocating and cleaning of resources will occur with the launch and completion
     * of your application process.</p>
     * @throws IOException
     */
    public void close() throws IOException {
        httpClient.close();
    }

}
