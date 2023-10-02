package pe.finanty.servDepenFinanty;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.net.ssl.*;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.SecureRandom;

public class UtilsHttpClientSendAPI {

    public static CloseableHttpResponse sendHttpClientPOST(String url,String data, SSLConnectionSocketFactory ssl) {

        CloseableHttpResponse response = null;
        try {
            StringEntity customerEntity = new StringEntity(data);
            response = sendHttpClientPOST(ssl, customerEntity,url);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public static SSLConnectionSocketFactory getSSLConnection(String urlP12, String passP12, String urlJks, String passJks) {
        SSLContext sslContext1= null;
        try {

            KeyStore clientStore = KeyStore.getInstance("PKCS12");

            InputStream inputStream = new ClassPathResource(urlP12).getInputStream();

            System.out.println("inputStream --- >" + inputStream);

            clientStore.load(inputStream,
                    passP12.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, passP12.toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();

            KeyStore trustStore = KeyStore.getInstance("JKS");

            InputStream inputStream2 = new ClassPathResource(urlJks).getInputStream();

            System.out.println("inputStream2 --- >" + inputStream2);
            trustStore.load(
                    inputStream2,
                    passJks.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            TrustManager[] tms = tmf.getTrustManagers();

            sslContext1 = SSLContext.getInstance("TLS");
            sslContext1.init(keyManagers, tms, new SecureRandom());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new SSLConnectionSocketFactory(sslContext1);
    }


    private static CloseableHttpResponse sendHttpClientPOST(SSLConnectionSocketFactory ssl,  StringEntity customerEntity, String url) {

        CloseableHttpClient httpClient = null;
        try {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            if (customerEntity != null) {
                httpPost.setEntity(customerEntity);
            }
            httpClient = HttpClients.custom().setSSLSocketFactory(ssl).build();
            return  httpClient.execute(httpPost);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
