package com.jbm.urcap.toolcommunicationinterface.impl;

import java.awt.Component;
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
	
	@Override
	public void buildUI(JPanel panel, TCIInstallationNodeContribution contribution) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(createDescriptionLabel("This URCap assumes control over the Tool I/O, and enables communication over the Tool Communication Interface."));
		panel.add(createDescriptionLabel("Decide whether the functionality of this URCap should be enabled:"));
		panel.add(createFunctionalityEnabledBox("Enable TCI control", contribution));
	}
	
	public void setFunctionalityEnabledCheckbox(boolean enabled) {
		CHECKBOX_FUNCTIONALITY_ENABLED.setSelected(enabled);
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
	
	private Box createDescriptionLabel(String text) {
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel descriptionLabel = new JLabel(text);
		
		box.add(descriptionLabel);
		
		return box;
	}

}
