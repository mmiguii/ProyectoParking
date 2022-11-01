package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class OrdinaryCustomerPanel extends JPanel{

	public OrdinaryCustomerPanel(String license, String time) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setBackground(Color.PINK);
		this.setLayout(new GridLayout(3, 1));

		setVisible(true);
		
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		JLabel incomingLabel = new JLabel("Bienvenido al Parking usuario: ");
		incomingLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel plateLabel = new JLabel(license);
		
		GridBagConstraints gbc_incomingLabel = new GridBagConstraints();
		gbc_incomingLabel.insets = new Insets(0, 0, 5, 5);
		gbc_incomingLabel.gridx = 0;
		gbc_incomingLabel.gridy = 0;
		topPanel.add(incomingLabel, gbc_incomingLabel);
		GridBagConstraints gbc_plateLabel = new GridBagConstraints();
		gbc_plateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_plateLabel.gridx = 1;
		gbc_plateLabel.gridy = 0;
		topPanel.add(plateLabel, gbc_plateLabel);
		add(topPanel);
		JLabel actualTime = new JLabel("Hora de ingreso en el Parking ");
		actualTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_actualTime = new GridBagConstraints();
		gbc_actualTime.insets = new Insets(0, 0, 0, 5);
		gbc_actualTime.gridx = 0;
		gbc_actualTime.gridy = 1;
		topPanel.add(actualTime, gbc_actualTime);
		JLabel hora = new JLabel(time);
		GridBagConstraints gbc_hora = new GridBagConstraints();
		gbc_hora.insets = new Insets(0, 0, 0, 5);
		gbc_hora.gridx = 1;
		gbc_hora.gridy = 1;
		topPanel.add(hora, gbc_hora);
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());
		middlePanel.setBackground(Color.PINK);
		add(middlePanel);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanel.setBackground(Color.GREEN);
		add(bottomPanel);
		
		
		
		
		
	}
}
