package com.jbm.urcap.toolcommunicationinterface.impl;

import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import com.jbm.urcap.toolcommunicationinterface.tci.TCIDaemonService;
import com.jbm.urcap.toolcommunicationinterface.tci.ToolIOController;
import com.ur.urcap.api.contribution.DaemonContribution.State;
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
	
	private Timer updateTimer;
	
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
		if(getFunctionalityEnabled()) {
			enabledFunctionalityViewUpdate();
		} else {
			disabledFunctionalityViewUpdate();
		}
	}
	
	@Override
	public void openView() {
		view.setFunctionalityEnabledCheckbox(getFunctionalityEnabled());
		
		// Will not change while in this view, so just update on openView
		if(toolIOController.hasControlOverTCI()) {
			view.setStatusLabel_ToolIOControl("Controlling Tool I/O");
		} else {
			view.setStatusLabel_ToolIOControl("Not controlling Tool I/O");
		}
		
		// Will not change while in this view, so just update on openView
		if(toolIOController.isTCIConfiguredCorrectly()) {
			view.setStatusLabel_TCIConfiguration("Compatible configuration");
		} else {
			view.setStatusLabel_TCIConfiguration("Configuration not compatible");
		}
		
		if(getFunctionalityEnabled()) {
			enabledFunctionalityViewUpdate();
		} else {
			disabledFunctionalityViewUpdate();
		}
	}

	@Override
	public void closeView() {
		stopTimer();
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		// TODO Auto-generated method stub
		
	}
	
	// Dynamic UI control
	private void enabledFunctionalityViewUpdate() {
		updateTimer = new Timer(true);
		updateTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(isDaemonRunning()) {
							view.setStatusLabel_DaemonRunning("Service running");
						} else {
							view.setStatusLabel_DaemonRunning("Service not running");
						}
					}
				});
			}
		}, 0, 500);
	}
	
	private void disabledFunctionalityViewUpdate() {
		view.setStatusLabel_DaemonRunning("Functionality disabled");
		stopTimer();
	}
	
	private void stopTimer() {
		if(updateTimer!=null) {
			updateTimer.cancel();
		}
	}

	// DataModel getters and setters to handle whether the URCap's functionality is enabled
	private void setFunctionalityEnabled(boolean enabled) {
		model.set(ENABLE_FUNCTIONALITY_KEY, enabled);
	}
	private boolean getFunctionalityEnabled() {
		return model.get(ENABLE_FUNCTIONALITY_KEY, ENABLE_FUNCTIONALITY_DEFAULT);
	}
	
	// Handlers to control daemon
	private boolean shouldRunDaemon() {
		return getFunctionalityEnabled() && toolIOController.isTCIConfiguredCorrectly();
	}
	private boolean isDaemonRunning() {
		return tciDaemon.getDaemonContribution().getState().equals(State.RUNNING);
	}
	private void runDaemon() {
		tciDaemon.getDaemonContribution().start();
	}
	private void stopDaemon() {
		tciDaemon.getDaemonContribution().stop();
	}
	
}
