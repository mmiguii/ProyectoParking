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
import java.util.Date;

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

public class OrdinaryCustomerWindow extends JFrame {

	private ReaderWriter rw;

	public OrdinaryCustomerWindow() {

		rw = new ReaderWriter();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Login Ordinary Customer");
		setVisible(true);
		getContentPane().setLayout(new GridLayout(3, 1)); // Dividimos el panel en 3 filas x 1 columna

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
		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());

		JButton enterButton = new JButton("ACCEDER");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ReaderWriter rw = new ReaderWriter();
				ArrayList<OrdinaryCustomer> ordinaryCustomers = rw.ordinaryCustomerReader();

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
//				System.out.println(tipoVehiculo);
				nOrdinaryCustomer.setVehicleType(Integer.parseInt(tipoVehiculo)); // Lee la opcion seleccionada
//				System.out.println(tarifa);
				nOrdinaryCustomer.setFare(tarifa); // Aqui debemos establecer la tarifa en cuestion

				ZonedDateTime now = ZonedDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
				String initialTime = formatter.format(now);
				ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(initialTime, formatter);
				nOrdinaryCustomer.setHoraDeEntrada(zdtWithZoneOffset);
//				System.out.println("Hora de inicio de la estancia: " + initialTime);

				ordinaryCustomers.add(nOrdinaryCustomer); // Anado el nuevo cliente
				rw.ordinaryCustomerWriter(ordinaryCustomers); // Escribimos el cliente BD

				OrdinaryCustomerPanel ordinaryPanel = new OrdinaryCustomerPanel(plateTextField.getText(), initialTime);
				add(ordinaryPanel);
				topPanel.setVisible(false);
				middlePanel.setVisible(false);
				bottomPanel.setVisible(false);
				ordinaryPanel.setVisible(true);


			}
		});

		leftBottomPanel.add(enterButton, new GridBagConstraints());

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

		getContentPane().add(topPanel);
		getContentPane().add(middlePanel);
		getContentPane().add(bottomPanel);

	}

}
