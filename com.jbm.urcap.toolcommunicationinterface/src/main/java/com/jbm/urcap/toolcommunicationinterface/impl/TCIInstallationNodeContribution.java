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
	
	private static final String 	ENABLE_FUNCTIONALITY_KEY 		= "enable";
	private static final boolean 	ENABLE_FUNCTIONALITY_DEFAULT	= false;
	
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
	
	public void userSelectedFunctonalityEnabled(boolean enabled) {
		setFunctionalityEnabled(enabled);
	}
	
	@Override
	public void openView() {
		view.setFunctionalityEnabledCheckbox(getFunctionalityEnabled());
	}

	@Override
	public void closeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
		
	}

	// DataModel getters and setters to handle whether the URCap's functionality is enabled
	private void setFunctionalityEnabled(boolean enabled) {
		model.set(ENABLE_FUNCTIONALITY_KEY, enabled);
	}
	private boolean getFunctionalityEnabled() {
		return model.get(ENABLE_FUNCTIONALITY_KEY, ENABLE_FUNCTIONALITY_DEFAULT);
	}
	
}
