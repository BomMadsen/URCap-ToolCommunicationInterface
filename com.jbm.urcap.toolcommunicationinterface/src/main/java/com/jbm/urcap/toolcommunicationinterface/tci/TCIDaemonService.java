package com.jbm.urcap.toolcommunicationinterface.tci;

import java.net.MalformedURLException;
import java.net.URL;

import com.ur.urcap.api.contribution.DaemonContribution;
import com.ur.urcap.api.contribution.DaemonService;

public class TCIDaemonService implements DaemonService {

	private DaemonContribution tciDaemon;
	
	@Override
	public void init(DaemonContribution daemon) {
		this.tciDaemon = daemon;
		try {
			daemon.installResource(new URL("file:daemon/tci/"));
		} catch (MalformedURLException e) {	}
	}

	@Override
	public URL getExecutable() {
		try {
			return new URL("file:daemon/tci/tciDaemonServer.py");	
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public DaemonContribution getDaemonContribution() {
		return this.tciDaemon;
	}

}
