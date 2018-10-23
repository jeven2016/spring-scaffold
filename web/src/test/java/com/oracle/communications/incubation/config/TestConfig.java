package com.oracle.communications.incubation.config;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.boot.test.context.TestConfiguration;

//@Configuration
@TestConfiguration
public class TestConfig {

  /**
   * By default, the dev and test profiles are enabled for test
   */
  public static final String TEST_PROFILE = "dev,test";

  public static void configureTls() {
    try {
      //make the 'localhost' be credible
      javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);

      // setup ssl context to ignore certificate errors
      SSLContext ctx = SSLContext.getInstance("TLS");
      X509TrustManager tm = new X509TrustManager() {

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
            String authType) throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
            String authType) throws java.security.cert.CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLContext.setDefault(ctx);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
