package com.lalamove.huolala.client.offline_web.util;

import android.util.Log;

import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpsUtils {

    public static KeyStore sKeyStore = null;
    public static SSLParams sSslParams = null;

    public static class SSLParams {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
        public KeyManager[] keyManagers;
        public KeyStore keyStore;
    }

    public static SSLParams getSslSocketFactory(InputStream[] certificates, InputStream bksFile, String password) {
        if (null != sSslParams && null != sSslParams.sSLSocketFactory) {
            return sSslParams;
        }
        sSslParams = new SSLParams();
        TrustManager[] trustManagers = prepareTrustManager(certificates);
        sSslParams.keyManagers = prepareKeyManager(bksFile, password);
        SSLContext sslContext;
        X509TrustManager trustManager = null;
        try {
//            if (BuildConfig.is_prd) {
//                trustManager = chooseTrustManager(trustManagers);
//            } else {
            trustManager = new UnSafeTrustManager();
//            }
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(sSslParams.keyManagers, new TrustManager[]{trustManager}, null);
            sSslParams.sSLSocketFactory = sslContext.getSocketFactory();
            sSslParams.trustManager = trustManager;
            sSslParams.keyStore = sKeyStore;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSslParams;
    }

    private static class UnSafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length < 1) {
            return null;
        }
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            sKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            sKeyStore.load(null, null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                sKeyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(sKeyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) {
                return null;
            }

            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    public static HostnameVerifier getAndroidHostnameVerifier() {
        return HttpsURLConnection.getDefaultHostnameVerifier();
    }

    public static X509HostnameVerifier getCustomStrictHostnameVerifier() {
        return org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
    }

    public static SSLSocketFactory getCustomSocketFactory() {
        return HttpsURLConnection.getDefaultSSLSocketFactory();
    }

    public static SSLSocketFactory getCustomTLSSocketFactory() {
        return getCustomSSLSocketFactory("TLS");
    }

    public static SSLSocketFactory getCustomSSLSocketFactory() {
        return getCustomSSLSocketFactory("SSL");
    }

    public static SSLSocketFactory getCustomSSLSocketFactory(String protocol) {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance(protocol);
            HttpsUtils.CustomX509TrustManager customX509TrustManager = null;
            customX509TrustManager = new HttpsUtils.CustomX509TrustManager();
            sc.init(null, new TrustManager[]{customX509TrustManager}, new SecureRandom());
            return sc.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class CustomX509TrustManager implements X509TrustManager {
        public static final String TAG = "CustomX509TrustManager";
        X509TrustManager sunJSSEX509TrustManager;

        public CustomX509TrustManager() throws Exception {
            KeyStore ks = null;
            try {
                ks = KeyStore.getInstance("JKS");
            } catch (Exception e) {
                Log.e(TAG, "[CustomX509TrustManager] KeyStore.getInstance Exception:" + e.toString());
            }
            TrustManager[] tms = {};
            if (ks != null) {
                FileInputStream in = null;
                try {
                    in = new FileInputStream("trustedCerts");
                    ks.load(in, "passphrase".toCharArray());
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
                    tmf.init(ks);
                    tms = tmf.getTrustManagers();
                    in.close();
                } catch (Exception e) {
                    Log.e(TAG, "[CustomX509TrustManager] getTrustManagers Exception:" + e.toString());
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception e) {
                            Log.e(TAG, "[CustomX509TrustManager] in.close Exception:" + e.toString());
                        }
                    }
                }
            } else {
                TrustManagerFactory tmf = TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init((KeyStore) null);
                tms = tmf.getTrustManagers();

            }
            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof X509TrustManager) {
                    sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                    return;
                }
            }
            throw new Exception("Couldn't initialize");
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return sunJSSEX509TrustManager.getAcceptedIssuers();
        }
    }
}
