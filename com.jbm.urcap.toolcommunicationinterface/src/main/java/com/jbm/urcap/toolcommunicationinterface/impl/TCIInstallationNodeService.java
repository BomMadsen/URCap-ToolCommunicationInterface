package com.jbm.urcap.toolcommunicationinterface.impl;

import java.util.Locale;

import com.jbm.urcap.toolcommunicationinterface.tci.TCIDaemonService;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class TCIInstallationNodeService implements SwingInstallationNodeService<TCIInstallationNodeContribution, TCIInstallationNodeView>{

	private TCIDaemonService tciDaemonService;
	
	public TCIInstallationNodeService(TCIDaemonService tciDaemonService) {
		this.tciDaemonService = tciDaemonService;
	}
	
	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		
	}

	@Override
	public String getTitle(Locale locale) {
		return "TCI Sample";
	}

	@Override
	public TCIInstallationNodeView createView(ViewAPIProvider apiProvider) {
		return new TCIInstallationNodeView(apiProvider);
	}

	@Override
	public TCIInstallationNodeContribution createInstallationNode(InstallationAPIProvider apiProvider,
			TCIInstallationNodeView view, DataModel model, CreationContext context) {
		return new TCIInstallationNodeContribution(apiProvider, view, model, this.tciDaemonService);
	}

}
