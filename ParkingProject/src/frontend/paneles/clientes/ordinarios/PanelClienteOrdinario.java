package frontend.paneles.clientes.ordinarios;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import backend.clases.personas.clientes.ClienteOrdinario;
import backend.servicios.ServicioPersistenciaBD;

public class PanelClienteOrdinario extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table1;

	public PanelClienteOrdinario(JFrame frame, JPanel panel, JPanel tPanel, JPanel mPanel, JPanel bPanel, ClienteOrdinario ordinario) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setSize(640, 480);
//		setBounds(10, 10, 567, 448);

//		setBackground(Color.PINK);

		setBorder(javax.swing.BorderFactory.createTitledBorder("Ordinary Customer"));
		this.setLayout(new GridLayout(3, 1));
//
//		setVisible(true);
//		
//		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel incomingLabel = new JLabel("Bienvenido al Parking usuario: ");
		incomingLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel plateLabel = new JLabel("license");

//		GridBagConstraints gbc_incomingLabel = new GridBagConstraints();
//		gbc_incomingLabel.insets = new Insets(0, 0, 5, 5);
//		gbc_incomingLabel.gridx = 0;
//		gbc_incomingLabel.gridy = 0;
//		topPanel.add(incomingLabel, gbc_incomingLabel);
//		GridBagConstraints gbc_plateLabel = new GridBagConstraints();
//		gbc_plateLabel.insets = new Insets(0, 0, 5, 5);
//		gbc_plateLabel.gridx = 1;
//		gbc_plateLabel.gridy = 0;
//		topPanel.add(plateLabel, gbc_plateLabel);

		topPanel.add(incomingLabel);
		topPanel.add(plateLabel);

//		add(topPanel);

		JLabel actualTime = new JLabel("Hora de ingreso en el Parking ");
		actualTime.setFont(new Font("Tahoma", Font.PLAIN, 14));

//		GridBagConstraints gbc_actualTime = new GridBagConstraints();
//		gbc_actualTime.insets = new Insets(0, 0, 0, 5);
//		gbc_actualTime.gridx = 0;
//		gbc_actualTime.gridy = 1;
//		topPanel.add(actualTime, gbc_actualTime);

		JLabel hora = new JLabel("time");

//		GridBagConstraints gbc_hora = new GridBagConstraints();
//		gbc_hora.insets = new Insets(0, 0, 0, 5);
//		gbc_hora.gridx = 1;
//		gbc_hora.gridy = 1;
//		topPanel.add(hora, gbc_hora);

		topPanel.add(actualTime);
		topPanel.add(hora);

		topPanel.setBackground(Color.CYAN);
		add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());
		middlePanel.setBackground(Color.PINK);

		table1 = new JTable();
		DefaultTableModel modelo = new DefaultTableModel();
		ServicioPersistenciaBD BD = new ServicioPersistenciaBD();
		// BD.connect("C:\\Users\\Alumno\\git\\ProyectoParking\\ParkingProject\\src\\Parking.db");

		table1.setModel(modelo);
		middlePanel.add(table1);

		add(middlePanel);

//		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanel.setBackground(Color.GREEN);

		JButton btnPlanta2 = new JButton("Segunda planta");
		btnPlanta2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true); 
				tPanel.setVisible(true);
				mPanel.setVisible(true);
				bPanel.setVisible(true);
				setVisible(false);
			}
		});

		bottomPanel.add(btnPlanta2, new GridBagConstraints());
		bottomPanel.add(btnVolver, new GridBagConstraints());

		add(bottomPanel);
//		
//		

	}
}
