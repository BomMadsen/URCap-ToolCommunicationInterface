package com.jbm.urcap.toolcommunicationinterface.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.jbm.urcap.toolcommunicationinterface.tci.TCIDaemonService;
import com.ur.urcap.api.contribution.DaemonService;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;

/**
 * Hello world activator for the OSGi bundle URCAPS contribution
 *
 */
public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		
		// Register TCI Daemon
		TCIDaemonService tciDaemonService = new TCIDaemonService();
		bundleContext.registerService(DaemonService.class, tciDaemonService, null);
		
		// Register Installation node and pass in DaemonService
		bundleContext.registerService(SwingInstallationNodeService.class, new TCIInstallationNodeService(tciDaemonService), null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Activator says Goodbye World!");
	}
}

