package com.example.projectone;

import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class NetworkManager implements Runnable{
    private Socket client;
    private String ip;
    private boolean isHost;
    private ServerSocket serverSocket;
    public NetworkManager(String ip, boolean isHost){
        this.ip = ip;
        this.isHost = isHost;
    }


    @Override
    public void run() {
        Log.i("Network", "Networkmanager started");
        if(!isHost) {
            createSocket(ip);
        }else{
            bindSocket();
            try{
                while(true){
                    Socket socket = serverSocket.accept();
                    Log.i("Network","Client connected");
                }
            }catch (IOException e){
                //empty
            }

        }

    }

    private void bindSocket() {
        try {
            serverSocket = new ServerSocket(1812);
        } catch (IOException e) {
            Log.e("Network", "Failed to create Server");
        }
    }

    public void createSocket(String ip){
        try {
            client = new Socket();
            client.connect(new InetSocketAddress(Inet4Address.getByName(ip), 1812), 1000);
        }catch(UnknownHostException unk){
            Log.e("Network","Unk Host");
        } catch (SocketTimeoutException timeoutException){
            Log.e("network", "Timeout");
        } catch (IOException e) {
            Log.e("Network","Socket error " + e.getLocalizedMessage());
        }
        Log.i("Network", "Socket created successfully");

    }
}
