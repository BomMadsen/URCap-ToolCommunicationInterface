package com.jbm.urcap.toolcommunicationinterface.impl;

import com.jbm.urcap.toolcommunicationinterface.tci.TCIDaemonService;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class TCIInstallationNodeContribution implements InstallationNodeContribution {

	public TCIInstallationNodeContribution(InstallationAPIProvider apiProvider,
			TCIInstallationNodeView view, DataModel model,
			TCIDaemonService tciDaemonService) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void openView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
		
	}

}
