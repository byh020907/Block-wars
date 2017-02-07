package com.blockwars.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

public class Network {
	
	public static JSONParser jsonParser=new JSONParser();
	public static Gson gson=new Gson();
	
	public static DatagramSocket socket;
	public static byte[] buffer;
	public static DatagramPacket receivePacket;
	public static InetAddress ia;
	public static int port;
	
	static{
		try {
			socket=new DatagramSocket();
			ia=InetAddress.getByName("10.156.145.131");//10.156.145.131
			port=5000;
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void init(){
		buffer=new byte[65507];
		receivePacket=new DatagramPacket(buffer, buffer.length);
	}
	
	public static void send(JSONObject sendData,InetAddress address,int port){
       	try {
       		DatagramPacket sendPacket=new DatagramPacket(sendData.toString().getBytes(), 0, sendData.toString().getBytes().length, address, port);
			socket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
