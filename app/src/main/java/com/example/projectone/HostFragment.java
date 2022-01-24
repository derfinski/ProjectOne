package com.example.projectone;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.projectone.databinding.FragmentSecondBinding;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HostFragment extends Fragment {
    public static Thread serverThread;
    private FragmentSecondBinding binding;
    private String ip;
    public Handler myTextHandler;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    try(final DatagramSocket socket = new DatagramSocket()){
                        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                        ip = socket.getLocalAddress().getHostAddress();
                    }
                    binding.hostIP.setText(ip);

                } catch (SocketException | UnknownHostException e) {
                    //@TODO add handling
                }
            }
        }).start();
        NetworkManager nwmng = null;
        nwmng = new NetworkManager("127.0.0.1", true, this);
        new Thread(nwmng).start();
        myTextHandler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(android.os.Message stringMessage) {

                binding.clientsConnected.append((String) stringMessage.obj + "\n");
                return true;
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
