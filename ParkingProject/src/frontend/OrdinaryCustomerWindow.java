package frontend;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import backend.ReaderWriter;
import backend.customer.OrdinaryCustomer;

import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class OrdinaryCustomerWindow extends JFrame {

	private JTextField plateTextField;

	public OrdinaryCustomerWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Login Ordinary Customer");
		setVisible(true);

		getContentPane().setLayout(new GridLayout(3, 1)); // 3 filas x 1 columna

		JPanel topPanel = new JPanel();
		// topPanel.setBackground(Color.RED); // Truco para ver que tengo los tres
		// paneles
		topPanel.setLayout(new GridBagLayout()); //
		JLabel plateLabel = new JLabel("Ingrese número de matrícula ");
		plateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		plateTextField = new JTextField(20);

		topPanel.add(plateLabel);
		topPanel.add(plateTextField);
		getContentPane().add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout()); //

		JRadioButton radioButton1 = new JRadioButton("Ordinario");
		JRadioButton radioButton2 = new JRadioButton("Minusválido");
		JRadioButton radioButton3 = new JRadioButton("Eléctrico/híbrido");

		ButtonGroup radioButtonGroup = new ButtonGroup(); // para que las opciones sean excluyentes
		radioButtonGroup.add(radioButton1);
		radioButtonGroup.add(radioButton2);
		radioButtonGroup.add(radioButton3);

//      middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		middlePanel.add(radioButton1);
		middlePanel.add(radioButton2);
		middlePanel.add(radioButton3);

		Border radioButtonsBorder = BorderFactory.createTitledBorder("TIPO DE USUARIO");
		middlePanel.setBorder(radioButtonsBorder);
		getContentPane().add(middlePanel);

//        JSplitPane bottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// Modificamos despues de leftbottom, rightbotton...

		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());
		JButton button1 = new JButton("ACCEDER");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReaderWriter rw = new ReaderWriter();
				ArrayList<OrdinaryCustomer> listaOrdinaria = rw.ordinaryCustomerReader();
				OrdinaryCustomer customer1 = new OrdinaryCustomer();
				String licensePlate = plateTextField.getText();
				customer1.setLicensePlate(licensePlate);
				customer1.setVehicleType(radioButtonGroup.getButtonCount()); // hacer que lea lo seleccionado.
				customer1.setFare(19);
				listaOrdinaria.add(customer1);
				rw.ordinaryCustomerWriter(listaOrdinaria);
				
				ZonedDateTime now = ZonedDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
				String initialTime = formatter.format(now);
				System.out.println("Hora de inicio de la estancia: "+initialTime);
				OrdinaryCustomerPanel ordinaryPanel = new OrdinaryCustomerPanel(licensePlate, initialTime);
				add(ordinaryPanel);
				topPanel.setVisible(false);
				middlePanel.setVisible(false);
				bottomPanel.setVisible(false);
				ordinaryPanel.setVisible(true);
				
//				String horaEntrada = metodo de conseguir la hora local
			}
		});
		
		
		
		leftBottomPanel.add(button1, new GridBagConstraints());

		JPanel middleBottomPanel = new JPanel();
		middleBottomPanel.setLayout(new GridBagLayout());
		JButton button2 = new JButton("ADQUIRIR ABONO");
		middleBottomPanel.add(button2, new GridBagConstraints());

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton button3 = new JButton("PAGAR");
		rightBottomPanel.add(button3, new GridBagConstraints());

		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(middleBottomPanel);
		bottomPanel.add(rightBottomPanel);
		getContentPane().add(bottomPanel);

	}

}
