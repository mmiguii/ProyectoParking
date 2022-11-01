package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		JLabel actualTime = new JLabel("Hora de ingreso en el Parking ");
		actualTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel hora = new JLabel(time);
		
		topPanel.add(incomingLabel);
		topPanel.add(plateLabel);
		topPanel.add(actualTime);
		topPanel.add(hora);
		add(topPanel);
		
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
