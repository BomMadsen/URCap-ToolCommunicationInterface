package com.jbm.urcap.toolcommunicationinterface.tci;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.AsyncCallback;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class TCICommunicatorXMLRPC {

	private final XmlRpcClient client;
	
	public TCICommunicatorXMLRPC() {
		String host = "127.0.0.1"; 			// Using localhost as default
		int port = 25000;					// Using port 25000 as default
		this.client = createXmlRpcClient(host, port);
	}
	
	public TCICommunicatorXMLRPC(String host, int port) {
		this.client = createXmlRpcClient(host, port);
	}
	
	private XmlRpcClient createXmlRpcClient(String host, int port) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setEnabledForExtensions(true);
		try {
			config.setServerURL(new URL("http://" + host + ":" + port + "/RPC2"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		config.setConnectionTimeout(1000); //1s
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}
	
	public Boolean isReachable() {
		try {
			Object result = client.execute("ping", new ArrayList<String>());
			if(result instanceof String) {
				return ((String) result).equals("pong");
			}
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
		return false;
	}
	
	public void isReachable(final XmlRpcCallback<Boolean> callback) {
		try {
			client.executeAsync("ping", new ArrayList<String>(), new AsyncCallback() {
				
				@Override
				public void handleResult(XmlRpcRequest arg0, Object arg1) {
					if(arg1 instanceof String) {
						callback.getResult(((String) arg1).equals("pong"));
					}
				}
				
				@Override
				public void handleError(XmlRpcRequest arg0, Throwable arg1) {
					System.out.println("XMLRPC Error in isReachable callback");
				}
			});
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
	}
	
	public Boolean isOpen() {
		Boolean open = false;
		try {
			Object result = client.execute("isOpen", new ArrayList<String>());
			if(result instanceof Boolean) {
				open = (Boolean) result;
			}
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
		return open;
	}
	
	public void isOpen(final XmlRpcCallback<Boolean> callback) {
		try {
			client.executeAsync("isOpen", new ArrayList<String>(), new AsyncCallback() {
				
				@Override
				public void handleResult(XmlRpcRequest arg0, Object arg1) {
					if(arg1 instanceof Boolean) {
						callback.getResult((Boolean) arg1);
					}
				}
				
				@Override
				public void handleError(XmlRpcRequest arg0, Throwable arg1) {
					System.out.println("XMLRPC Error in isOpen callback");
				}
			});
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
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
	
	public void read(int bytesToRead, final XmlRpcCallback<String> callback) {
		ArrayList<Integer> args = new ArrayList<Integer>();
		args.add(bytesToRead);
		
		try {
			client.executeAsync("read", args, new AsyncCallback() {
				
				@Override
				public void handleResult(XmlRpcRequest arg0, Object arg1) {
					if(arg1 instanceof String) {
						callback.getResult((String) arg1);
					}
				}
				
				@Override
				public void handleError(XmlRpcRequest arg0, Throwable arg1) {
					System.out.println("XMLRPC Error in read callback");
				}
			});
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
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
	
	public void open(final XmlRpcCallback<Boolean> callback) {
		try {
			client.executeAsync("open", new ArrayList<String>(), new AsyncCallback() {
				
				@Override
				public void handleResult(XmlRpcRequest arg0, Object arg1) {
					if(arg1 instanceof Boolean) {
						callback.getResult((Boolean) arg1);
					}
				}
				
				@Override
				public void handleError(XmlRpcRequest arg0, Throwable arg1) {
					System.out.println("XMLRPC Error in open callback");
				}
			});
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
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
	
	public void close(final XmlRpcCallback<Boolean> callback) {
		try {
			client.executeAsync("close", new ArrayList<String>(), new AsyncCallback() {
				
				@Override
				public void handleResult(XmlRpcRequest arg0, Object arg1) {
					if(arg1 instanceof Boolean) {
						callback.getResult((Boolean) arg1);
					}
				}
				
				@Override
				public void handleError(XmlRpcRequest arg0, Throwable arg1) {
					System.out.println("XMLRPC Error in close callback");
				}
			});
		} catch (XmlRpcException e) {
			System.out.println("Caught XMLRPC exception");
		}
	}
	
}
