package frontend.paneles;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

import backend.clases.clientes.ClienteOrdinario;
import backend.servicios.ServicioPersistenciaBD;
import frontend.FullPanel;

public class LogInPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ServicioPersistenciaBD BD;

	public LogInPanel(final JFrame frame) {

//		BD = new ServicioPersistenciaBD(); 

		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		// Panel superior
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel labelMatricula = new JLabel("Ingrese numero de matricula ");
		labelMatricula.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.add(labelMatricula);

		JTextField textFieldMatricula = new JTextField(20);
		topPanel.add(textFieldMatricula);

		// Panel central
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());

		Border radioButtonsBorder = BorderFactory.createTitledBorder("TIPO DE USUARIO");
		middlePanel.setBorder(radioButtonsBorder);

		// Para que las opciones sean excluyentes, creamos un grupo
		ButtonGroup radioButtonGroup = new ButtonGroup();
		JRadioButton radioButtonNormal = new JRadioButton("Normal");
		radioButtonGroup.add(radioButtonNormal);
		JRadioButton radioButtonMinusvalido = new JRadioButton("Minusvalido");
		radioButtonGroup.add(radioButtonMinusvalido);
		JRadioButton radioButtonElectrico = new JRadioButton("Electrico/hibrido");
		radioButtonGroup.add(radioButtonElectrico);

		// Anadimos los botones al panel central
		middlePanel.add(radioButtonNormal);
		middlePanel.add(radioButtonMinusvalido);
		middlePanel.add(radioButtonElectrico);

		// Panel inferior
		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());

		JButton btnAcceder = new JButton("ACCEDER");
		btnAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//		ReaderWriter rw = new ReaderWriter();
//		ArrayList<OrdinaryCustomer> ordinaryCustomers = rw.ordinaryCustomerReader();

				ClienteOrdinario nOrdinaryCustomer = new ClienteOrdinario();

				nOrdinaryCustomer.setMatricula(textFieldMatricula.getText());

				// Asignamos el valor del tipo de vehiculo en funcion de lo que el usuario
				// introduzca y le anadimos la tarifa
				String tipoVehiculo = radioButtonGroup.getSelection().getActionCommand();
				double tarifa = 0.00;
				if (radioButtonNormal.isSelected()) {
					tipoVehiculo = "1";
					tarifa = 1.00;
				} else if (radioButtonMinusvalido.isSelected()) {
					tipoVehiculo = "2";
					tarifa = 2.00;
				} else if (radioButtonElectrico.isSelected()) {
					tipoVehiculo = "3";
					tarifa = 3.00;
				}
//		System.out.println(tipoVehiculo);
				nOrdinaryCustomer.setTipoVehiculo(tipoVehiculo); // Lee la opcion seleccionada
//		System.out.println(tarifa);
				nOrdinaryCustomer.setTarifa(tarifa); // Aqui debemos establecer la tarifa en cuestion

				
//				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
//				Date date = formatter.parse();
//				long dateInLong = date.getTime();
//				nOrdinaryCustomer.setFechaEntrada(dateInLong);
//		System.out.println("Hora de inicio de la estancia: " + initialTime);

//		ordinaryCustomers.add(nOrdinaryCustomer); // Anado el nuevo cliente
//		rw.ordinaryCustomerWriter(ordinaryCustomers); // Escribimos el cliente BD

//				int a = 3;
//				
////				mirar fichero o tabla de plazas, si estan llenas en las 2 plantas todo al else
//				if (a==5) {
//					OrdinaryCustomerPanel ordinaryPanel = new OrdinaryCustomerPanel(frame, textFieldMatricula.getText(), initialTime);
//					frame.getContentPane().add(ordinaryPanel);
//					topPanel.setVisible(false);
//					middlePanel.setVisible(false);
//					bottomPanel.setVisible(false);
//
//					ordinaryPanel.setVisible(true);
//				}else {
//					FullPanel fullPanel = new FullPanel(frame, textFieldMatricula.getText());
//					frame.getContentPane().add(fullPanel);
//					topPanel.setVisible(false);
//					middlePanel.setVisible(false);
//					bottomPanel.setVisible(false);
//					fullPanel.setVisible(true);
//					
//				}
				

			}
		});

		leftBottomPanel.add(btnAcceder, new GridBagConstraints());

		JPanel middleBottomPanel = new JPanel();
		middleBottomPanel.setLayout(new GridBagLayout());
		JButton btnAdquirir = new JButton("ADQUIRIR ABONO");
		btnAdquirir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubscriberPanel panel = new SubscriberPanel(frame);
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		
		
		
		
		
		middleBottomPanel.add(btnAdquirir, new GridBagConstraints());

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnPagar = new JButton("PAGAR");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  	
				ZonedDateTime now = ZonedDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
				String initialTime = formatter.format(now);
				
				
				PaymentPanel panel = new PaymentPanel(frame, "normal" ,initialTime,textFieldMatricula.getText());
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightBottomPanel.add(btnPagar, new GridBagConstraints());

		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(middleBottomPanel);
		bottomPanel.add(rightBottomPanel);

		add(topPanel);
		add(middlePanel);
		add(bottomPanel);

	}
}
