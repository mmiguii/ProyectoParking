package frontend;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import backend.ServicioPersistenciaBD;
import backend.customer.OrdinaryCustomer;

public class LogInPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ServicioPersistenciaBD BD;

	public LogInPanel(final JFrame frame) {

//		BD = new ServicioPersistenciaBD(); 

		setBorder(javax.swing.BorderFactory.createTitledBorder("User Wellcoming Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		// Panel superior
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel plateLabel = new JLabel("Ingrese numero de matricula ");
		plateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.add(plateLabel);

		JTextField plateTextField = new JTextField(20);
		topPanel.add(plateTextField);

		// Panel central
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());

		Border radioButtonsBorder = BorderFactory.createTitledBorder("TIPO DE USUARIO");
		middlePanel.setBorder(radioButtonsBorder);

		// Para que las opciones sean excluyentes, creamos un grupo
		ButtonGroup radioButtonGroup = new ButtonGroup();
		JRadioButton radioButton1 = new JRadioButton("Ordinario");
		radioButtonGroup.add(radioButton1);
		JRadioButton radioButton2 = new JRadioButton("Minusvalido");
		radioButtonGroup.add(radioButton2);
		JRadioButton radioButton3 = new JRadioButton("Electrico/hibrido");
		radioButtonGroup.add(radioButton3);

		// Anadimos los botones al panel central
		middlePanel.add(radioButton1);
		middlePanel.add(radioButton2);
		middlePanel.add(radioButton3);

		// Panel inferior
		JPanel bottomPanel = new JPanel(new GridLayout(1, 4));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());

		JButton enterButton = new JButton("ACCEDER");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//		ReaderWriter rw = new ReaderWriter();
//		ArrayList<OrdinaryCustomer> ordinaryCustomers = rw.ordinaryCustomerReader();

				OrdinaryCustomer nOrdinaryCustomer = new OrdinaryCustomer();

				nOrdinaryCustomer.setLicensePlate(plateTextField.getText());

				// Asignamos el valor del tipo de vehiculo en funcion de lo que el usuario
				// introduzca y le anadimos la tarifa
				String tipoVehiculo = radioButtonGroup.getSelection().getActionCommand();
				double tarifa = 0.00;
				if (radioButton1.isSelected()) {
					tipoVehiculo = "1";
					tarifa = 1.00;
				} else if (radioButton2.isSelected()) {
					tipoVehiculo = "2";
					tarifa = 2.00;
				} else if (radioButton3.isSelected()) {
					tipoVehiculo = "3";
					tarifa = 3.00;
				}
//		System.out.println(tipoVehiculo);
				nOrdinaryCustomer.setVehicleType(Integer.parseInt(tipoVehiculo)); // Lee la opcion seleccionada
//		System.out.println(tarifa);
				nOrdinaryCustomer.setFare(tarifa); // Aqui debemos establecer la tarifa en cuestion

				ZonedDateTime now = ZonedDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
				String initialTime = formatter.format(now);
				ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(initialTime, formatter);
				nOrdinaryCustomer.setHoraDeEntrada(zdtWithZoneOffset);
//		System.out.println("Hora de inicio de la estancia: " + initialTime);

//		ordinaryCustomers.add(nOrdinaryCustomer); // Anado el nuevo cliente
//		rw.ordinaryCustomerWriter(ordinaryCustomers); // Escribimos el cliente BD

				int a = 3;
				
//				mirar fichero o tabla de plazas, si estan llenas en las 2 plantas todo al else
				if (a==5) {
					OrdinaryCustomerPanel ordinaryPanel = new OrdinaryCustomerPanel(frame, plateTextField.getText(), initialTime);
					frame.add(ordinaryPanel);
					topPanel.setVisible(false);
					middlePanel.setVisible(false);
					bottomPanel.setVisible(false);

					ordinaryPanel.setVisible(true);
				}else {
					FullPanel fullPanel = new FullPanel(frame, plateTextField.getText());
					frame.add(fullPanel);
					topPanel.setVisible(false);
					middlePanel.setVisible(false);
					bottomPanel.setVisible(false);
					fullPanel.setVisible(true);
					
				}
				

			}
		});

		leftBottomPanel.add(enterButton, new GridBagConstraints());

		JPanel middleBottomPanel = new JPanel();
		middleBottomPanel.setLayout(new GridBagLayout());
		JButton button2 = new JButton("ADQUIRIR ABONO");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubscriberPanel panel = new SubscriberPanel(frame, plateTextField.getText());
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		
		
		
		
		
		middleBottomPanel.add(button2, new GridBagConstraints());

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton button3 = new JButton("PAGAR");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ZonedDateTime now = ZonedDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
				String initialTime = formatter.format(now);
				PaymentPanel panel = new PaymentPanel(frame, "ordinary" ,initialTime,plateTextField.getText());
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightBottomPanel.add(button3, new GridBagConstraints());

		JPanel lastBottomPanel = new JPanel();
		lastBottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WellcomingPanel panel = new WellcomingPanel(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		lastBottomPanel.add(btnVolver);
		
		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(middleBottomPanel);
		bottomPanel.add(rightBottomPanel);
		bottomPanel.add(lastBottomPanel);

		add(topPanel);
		add(middlePanel);
		add(bottomPanel);

	}
}
