package com.jbm.urcap.toolcommunicationinterface.tci;

import com.ur.urcap.api.domain.resource.ResourceModel;
import com.ur.urcap.api.domain.resource.tooliointerface.ToolIOInterface;
import com.ur.urcap.api.domain.resource.tooliointerface.AnalogInputModeConfig;
import com.ur.urcap.api.domain.resource.tooliointerface.CommunicationInterfaceConfig;
import com.ur.urcap.api.domain.resource.tooliointerface.CommunicationInterfaceConfig.BaudRate;
import com.ur.urcap.api.domain.resource.tooliointerface.CommunicationInterfaceConfig.Parity;
import com.ur.urcap.api.domain.resource.tooliointerface.CommunicationInterfaceConfig.StopBits;
import com.ur.urcap.api.domain.resource.tooliointerface.ToolIOInterface.OutputVoltage;
import com.ur.urcap.api.domain.resource.tooliointerface.control.AnalogInputModeConfigFactory;
import com.ur.urcap.api.domain.resource.tooliointerface.control.ToolIOInterfaceControlEvent;
import com.ur.urcap.api.domain.resource.tooliointerface.control.ToolIOInterfaceControllable;
import com.ur.urcap.api.domain.resource.tooliointerface.control.ToolIOInterfaceController;
import com.ur.urcap.api.domain.system.capability.CapabilityManager;

// Import this to use in CapabilityManager
import static com.ur.urcap.api.domain.system.capability.tooliointerface.ToolIOCapability.COMMUNICATION_INTERFACE_MODE;

public class ToolIOController implements ToolIOInterfaceController{
	
	/*****
	 * DESIRED TCI SETTINGS
	 */
	private static final BaudRate 		BAUDRATE = 		BaudRate.BAUD_115200;
	private static final Parity 		PARITY = 		Parity.NONE;
	private static final StopBits 		STOPBITS = 		StopBits.ONE;
	private static final double			TXIDLECHARS = 	1.5;
	private static final double			RXIDLECHARS = 	3.5;
	private static final float			TOLERANCE = 	0.1f;
	private static final OutputVoltage 	OUTPUTVOLTAGE = OutputVoltage.OUTPUT_VOLTAGE_24V;
	
	
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
		
		powerOnTool();
		setupTCI();
	}

	@Override
	public void onControlToBeRevoked(ToolIOInterfaceControlEvent event) {
		
	}
	
	public boolean hasControlOverTCI() {
		if(toolIOInterfaceControllable!=null) {
			return toolIOInterfaceControllable.hasControl();
		} else {
			return false;
		}
	}
	
	public boolean isTCIConfiguredCorrectly() {
		// First, we check that this robot actually support TCI
		if(capabilityManager.hasCapability(COMMUNICATION_INTERFACE_MODE)) {
			// Then we check if the analog inputs are configured to TCI-mode
			if(tooIOInterface.getAnalogInputModeConfig().getConfigType().equals(AnalogInputModeConfig.ConfigType.TOOL_COMMUNICATION_INTERFACE)) {
				// Lastly, we validate whether 
				CommunicationInterfaceConfig appliedConfig = (CommunicationInterfaceConfig) tooIOInterface.getAnalogInputModeConfig();
				return 		appliedConfig.getBaudRate().equals(BAUDRATE) 							&&
							appliedConfig.getParity().equals(PARITY) 								&&
							appliedConfig.getStopBits().equals(STOPBITS) 							&&
							Math.abs(appliedConfig.getTxIdleChars() - TXIDLECHARS) < TOLERANCE		&&
							Math.abs(appliedConfig.getRxIdleChars() - RXIDLECHARS) < TOLERANCE;
			}
		} 
		return false;
	}
	
	private void powerOnTool() {
		toolIOInterfaceControllable.setOutputVoltage(OUTPUTVOLTAGE);
	}

	private void setupTCI() {
		if(capabilityManager.hasCapability(COMMUNICATION_INTERFACE_MODE)) {
			AnalogInputModeConfigFactory factory = toolIOInterfaceControllable.getAnalogInputModeConfigFactory();
			CommunicationInterfaceConfig config = factory.createCommunicationInterfaceConfig(BAUDRATE, PARITY, STOPBITS, RXIDLECHARS, TXIDLECHARS);
			toolIOInterfaceControllable.setAnalogInputModeConfig(config);
		}
	}
	
}
