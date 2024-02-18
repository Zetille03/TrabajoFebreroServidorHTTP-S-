package secureserverhttp;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;



public class ConexionHTTPSegura {
	private static String KEY_PASSWORD = "keypass";
	private static int PUERTO_SERVIDOR = 5000;
	
	public static void main(String[] args) {
        try {
            // Configurar el keystore
            char[] password = KEY_PASSWORD.toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keystoreStream = new FileInputStream("keystore.jks");
            keyStore.load(keystoreStream, password);

            // Configurar el KeyManagerFactory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password);

            // Configurar el SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Crear el servidor SSL
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(PUERTO_SERVIDOR);
            System.out.println("Server seguro escuchando en el puerto "+PUERTO_SERVIDOR);
            
            while (true) {
                // Aceptar conexiones seguras
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                
                ConexionClienteSegura cc = new ConexionClienteSegura(sslSocket);
    			new Thread(cc).start();
            }

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
