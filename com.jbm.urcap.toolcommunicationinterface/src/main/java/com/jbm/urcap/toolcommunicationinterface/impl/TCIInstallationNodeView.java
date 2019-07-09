package com.jbm.urcap.toolcommunicationinterface.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;

public class TCIInstallationNodeView implements SwingInstallationNodeView<TCIInstallationNodeContribution>{

	public TCIInstallationNodeView(ViewAPIProvider apiProvider) {
		// TODO Auto-generated constructor stub
	}
	
	private JCheckBox CHECKBOX_FUNCTIONALITY_ENABLED = new JCheckBox();
	private JLabel LABEL_CONTROL_OF_TOOLIO_STATUS = new JLabel();
	private JLabel LABEL_TCI_CONFIGURATION_STATUS = new JLabel();
	private JLabel LABEL_DAEMON_RUNNING_STATUS = new JLabel();
	
	@Override
	public void buildUI(JPanel panel, TCIInstallationNodeContribution contribution) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(createDescriptionLabel("This URCap assumes control over the Tool I/O, and enables communication over the Tool Communication Interface."));
		panel.add(createDescriptionLabel("Decide whether the functionality of this URCap should be enabled:"));
		panel.add(createFunctionalityEnabledBox("Enable TCI control", contribution));
		panel.add(createSpacer(25));
		panel.add(createStatusLabel("Tool I/O Control:", LABEL_CONTROL_OF_TOOLIO_STATUS));
		panel.add(createStatusLabel("TCI Configuration:", LABEL_TCI_CONFIGURATION_STATUS));
		panel.add(createStatusLabel("TCI Communication Service:", LABEL_DAEMON_RUNNING_STATUS));
	}
	
	public void setFunctionalityEnabledCheckbox(boolean enabled) {
		CHECKBOX_FUNCTIONALITY_ENABLED.setSelected(enabled);
	}
	
	public void setStatusLabel_ToolIOControl(String status) {
		LABEL_CONTROL_OF_TOOLIO_STATUS.setText(status);
	}
	
	public void setStatusLabel_TCIConfiguration(String status) {
		LABEL_TCI_CONFIGURATION_STATUS.setText(status);
	}
	
	public void setStatusLabel_DaemonRunning(String status) {
		LABEL_DAEMON_RUNNING_STATUS.setText(status);
	}
	
	private Box createFunctionalityEnabledBox(String text, final TCIInstallationNodeContribution contribution) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		CHECKBOX_FUNCTIONALITY_ENABLED.setText(text);
		CHECKBOX_FUNCTIONALITY_ENABLED.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				contribution.userSelectedFunctonalityEnabled(CHECKBOX_FUNCTIONALITY_ENABLED.isSelected());
			}
		});
		
		box.add(CHECKBOX_FUNCTIONALITY_ENABLED);
		
		return box;
	}
	
	private Box createStatusLabel(String description, JLabel statusLabel) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		box.setPreferredSize(new Dimension(500, 30));
		
		JLabel descriptionLabel = new JLabel(description);
		
		Dimension labelSize = new Dimension(250, 30);
		
		descriptionLabel.setPreferredSize(labelSize);
		descriptionLabel.setMinimumSize(labelSize);
		
		statusLabel.setPreferredSize(labelSize);
		statusLabel.setMinimumSize(labelSize);
		
		box.add(descriptionLabel);
		box.add(statusLabel);
		
		return box;
	}
	
	private Box createDescriptionLabel(String text) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel descriptionLabel = new JLabel(text);
		
		box.add(descriptionLabel);
		
		return box;
	}
	
	private Component createSpacer(int height) {
		return Box.createRigidArea(new Dimension(0, height));
	}

}
