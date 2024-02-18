package com.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConexionHTTP 
{
	private static final int PUERTO = 5000;
	
    public static void main( String[] args )
    {

    	try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
    		System.out.println("Servidor escuchando en el puerto "+PUERTO);
    		while(true) {
    			Socket clienteSocket = serverSocket.accept();
    			ConexionCliente cc = new ConexionCliente(clienteSocket);
    			new Thread(cc).start();
    		}
    	}catch(IOException e) {
    		System.out.println(e.getMessage());
    	}
    }
}
