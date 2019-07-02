package com.jbm.urcap.toolcommunicationinterface.tci;

import com.ur.urcap.api.domain.resource.ResourceModel;
import com.ur.urcap.api.domain.resource.tooliointerface.ToolIOInterface;
import com.ur.urcap.api.domain.resource.tooliointerface.control.ToolIOInterfaceControlEvent;
import com.ur.urcap.api.domain.resource.tooliointerface.control.ToolIOInterfaceControllable;
import com.ur.urcap.api.domain.resource.tooliointerface.control.ToolIOInterfaceController;
import com.ur.urcap.api.domain.system.capability.CapabilityManager;

public class ToolIOController implements ToolIOInterfaceController{
	
	private final CapabilityManager capabilityManager;					// For validating, that the right capabilities (TCI) are supported
	private final ToolIOInterface tooIOInterface;						// The read-only instance of the Tool IO Interface
	private ToolIOInterfaceControllable toolIOInterfaceControllable;	// The controllable instance of the Tool IO Interface
	
	public ToolIOController(ResourceModel resourceModel, CapabilityManager capabilityManager) {
		this.capabilityManager = capabilityManager;
		this.tooIOInterface = resourceModel.getToolIOInterface();
	}

	@Override
	public void onControlGranted(ToolIOInterfaceControlEvent event) {
		toolIOInterfaceControllable = event.getControllableResource();
	}

	@Override
	public void onControlToBeRevoked(ToolIOInterfaceControlEvent event) {
		
	}

}
