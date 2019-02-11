package com.jbm.urcap.toolcommunicationinterface.tci;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class TCICommunicatorXMLRPC {

	private final XmlRpcClient client;
	
	public TCICommunicatorXMLRPC(String host, int port) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setEnabledForExtensions(true);
		try {
			config.setServerURL(new URL("http://" + host + ":" + port + "/RPC2"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		config.setConnectionTimeout(1000); //1s
		client = new XmlRpcClient();
		client.setConfig(config);
	}
	
	public boolean isReachable() {
		try {
			client.execute("ping", new ArrayList<String>());
			return true;
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
			return false;
		}
	}
	
	public void write(String data) {
		ArrayList<String> args = new ArrayList<String>();
		args.add(data);
		try {
			client.execute("write", args);
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
			e.printStackTrace();
		}
	}
	
	public String read(int bytesToRead) {
		ArrayList<Integer> args = new ArrayList<Integer>();
		args.add(bytesToRead);
		String result = "";
		try {
			result += (String) client.execute("read", args);
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
		return result;
	}
	
	public Boolean open() {
		Boolean opened = false;
		try {
			Object result = client.execute("open", new ArrayList<String>());
			if(result instanceof Boolean) {
				opened = (Boolean) result;
			}
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
			e.printStackTrace();
		}
		return opened;
	}
	
	public Boolean close() {
		Boolean closed = false;
		try {
			Object result = client.execute("close", new ArrayList<String>());
			if(result instanceof Boolean) {
				closed = (Boolean) result;
			}
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
		return closed;
	}
}