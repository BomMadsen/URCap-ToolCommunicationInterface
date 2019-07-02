package com.jbm.urcap.toolcommunicationinterface.impl;

import com.jbm.urcap.toolcommunicationinterface.tci.TCIDaemonService;
import com.jbm.urcap.toolcommunicationinterface.tci.ToolIOController;
import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.resource.ControllableResourceModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class TCIInstallationNodeContribution implements InstallationNodeContribution {

	private final InstallationAPIProvider apiProvider;
	private final TCIInstallationNodeView view;
	private final DataModel model;
	private final TCIDaemonService tciDaemon;
	private final ToolIOController toolIOController;
	
	public TCIInstallationNodeContribution(InstallationAPIProvider apiProvider,
			TCIInstallationNodeView view, DataModel model,
			TCIDaemonService tciDaemonService) {
		this.apiProvider = apiProvider;
		this.view = view;
		this.model = model;
		this.tciDaemon = tciDaemonService;
		
		ControllableResourceModel resourceModel = apiProvider.getInstallationAPI().getControllableResourceModel();
		this.toolIOController = new ToolIOController(resourceModel, apiProvider.getSystemAPI().getCapabilityManager());
		resourceModel.requestControl(this.toolIOController);
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
