package com.jbm.urcap.toolcommunicationinterface.tci;

public class TCITester {

	/***
	 * Small tester class, to validate 
	 * 
	 */
	
	public static void main(String[] args) {
		System.out.println("Creating connection");
		TCICommunicatorXMLRPC tci = new TCICommunicatorXMLRPC();
		System.out.println("Created, checking connection");
		System.out.println("Got pong back from server: " + tci.isReachable());
		
		System.out.println("Opening port...");
		boolean openedPort = tci.open();
		System.out.println("Opened: "+openedPort);
		
		System.out.println("Trying write");
		tci.write("Hello tool-device");
		System.out.println("Sent data");
		
		System.out.println("Trying read");
		String result = tci.read(3);
		System.out.println("Got response: "+result);
		
		System.out.println("Closing port...");
		boolean closedPort = tci.close();
		System.out.println("Closed: "+closedPort);
	}
	
}
