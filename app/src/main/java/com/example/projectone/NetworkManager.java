package com.example.projectone;

import static java.lang.String.*;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NetworkManager implements Runnable{
    private Socket client;
    private String ip;
    private boolean isHost;
    private ServerSocket serverSocket;
    private ArrayList<Socket> connectedClients = new ArrayList<Socket>();
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


        }

    }

    private void bindSocket() {
        try {
            serverSocket = new ServerSocket(1812);
        } catch (IOException e) {
            Log.e("Network", "Failed to create Server");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true){
                        Socket socket = serverSocket.accept();
                        Log.i("Network","Client connected");
                        connectedClients.add(socket);
                        OutputStream outputStream = socket.getOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                        String sender = "SERVER";
                        String typ = MessageTypes.SUCCESS.name();
                        int clientID = connectedClients.size();
                        dataOutputStream.writeUTF(format("%s:%s:%d",sender,typ,clientID));

                    }
                }catch (IOException e){
                    //empty
                }
            }
        }).start();

    }

    //region Client

    public void createSocket(String ip){
        try {
            client = new Socket();
            client.connect(new InetSocketAddress(Inet4Address.getByName(ip), 1812), 1000);
        }catch(UnknownHostException unk){
            Log.e(LogTags.Network.toString(),"Unk Host");
        } catch (SocketTimeoutException timeoutException){
            Log.e(LogTags.Network.toString(), "Timeout");
        } catch (IOException e) {
            Log.e(LogTags.Network.toString(),"Socket error " + e.getLocalizedMessage());
        }
        Log.i(LogTags.Network.toString(), "Socket created successfully");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        DataInputStream dIn = new DataInputStream(client.getInputStream());
                        String message = dIn.readUTF();
                        Message msg = Message.parseMessage(message);
                        Log.i(LogTags.MessageResieved.toString(), message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    //endRegion
}
