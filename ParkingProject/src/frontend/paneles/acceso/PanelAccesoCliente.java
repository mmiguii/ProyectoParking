package frontend.paneles.acceso;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.clases.personas.clientes.Usuario;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.clientes.acciones.PanelPago;
import frontend.paneles.clientes.ordinarios.PanelClienteOrdinario;
import frontend.paneles.clientes.subscritos.PanelClienteSubscrito;

public class PanelAccesoCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private ServicioPersistenciaBD servicio;
	private JPanel instance;
	private JTextField textFieldMatricula;
	private JTextField textFieldHorarioActual;
	private JButton btnAdquirir;
	private JButton btnAcceder;
	private JButton btnPagar;
	private JButton btnVolver;
	private JRadioButton radioButtonOrdinario;
	private JRadioButton radioButtonElectrico;
	private JRadioButton radioButtonMinusvalido;

	public PanelAccesoCliente(JFrame frame, JPanel panel) {

		servicio = new ServicioPersistenciaBD();

		instance = this;

		ZonedDateTime horaDeEntrada = ZonedDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

		setBorder(javax.swing.BorderFactory.createTitledBorder("User Wellcoming Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		// Panel superior
		JPanel topPanel = new JPanel();
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWeights = new double[] { 0.0, 1.0 };
		topPanel.setLayout(gbl_topPanel);

		// Labels del panel superior
		JLabel lblHoraActual = new JLabel("Hora actual:");
		GridBagConstraints gbc_lblHoraActual = new GridBagConstraints();
		gbc_lblHoraActual.anchor = GridBagConstraints.EAST;
		gbc_lblHoraActual.insets = new Insets(0, 0, 5, 5);
		gbc_lblHoraActual.gridx = 0;
		gbc_lblHoraActual.gridy = 0;
		topPanel.add(lblHoraActual, gbc_lblHoraActual);

		JLabel lblMatricula = new JLabel("Ingrese su numero de matricula ");
		lblMatricula.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblMatricula = new GridBagConstraints();
		gbc_lblMatricula.insets = new Insets(0, 0, 0, 5);
		gbc_lblMatricula.gridx = 0;
		gbc_lblMatricula.gridy = 1;
		topPanel.add(lblMatricula, gbc_lblMatricula);

		// TextFields del panel superior
		textFieldHorarioActual = new JTextField(formatter.format(horaDeEntrada));
		GridBagConstraints gbc_textFieldHorarioActual = new GridBagConstraints();
		gbc_textFieldHorarioActual.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldHorarioActual.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHorarioActual.gridx = 1;
		gbc_textFieldHorarioActual.gridy = 0;
		topPanel.add(textFieldHorarioActual, gbc_textFieldHorarioActual);
		textFieldHorarioActual.setColumns(10);
		textFieldHorarioActual.setEditable(false);

		textFieldMatricula = new JTextField(20);
		textFieldMatricula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> matriculas = new ArrayList<String>();
				ArrayList<Usuario> usuarios = servicio.usuarios();

				for (int i = 0; i < usuarios.size(); i++) {
					if (!matriculas.contains(usuarios.get(i).getMatricula())) {
						matriculas.add(usuarios.get(i).getMatricula());
					}
				}

				boolean existeMatricula = matriculas.contains(textFieldMatricula.getText());

				if (existeMatricula) {
					btnPagar.setEnabled(true);
					btnAcceder.setEnabled(false);
					btnAdquirir.setEnabled(false);
					radioButtonOrdinario.setEnabled(false);
					radioButtonElectrico.setEnabled(false);
					radioButtonMinusvalido.setEnabled(false);

				} else {
					btnPagar.setEnabled(false);
					btnAcceder.setEnabled(true);
					btnAdquirir.setEnabled(true);
					radioButtonOrdinario.setEnabled(true);
					radioButtonElectrico.setEnabled(true);
					radioButtonMinusvalido.setEnabled(true);
				}
			}
		});
		GridBagConstraints gbc_textFieldMatricula = new GridBagConstraints();
		gbc_textFieldMatricula.gridx = 1;
		gbc_textFieldMatricula.gridy = 1;
		topPanel.add(textFieldMatricula, gbc_textFieldMatricula);

		// Panel central
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());

		Border radioButtonsBorder = BorderFactory.createTitledBorder("Tipo de vehiculo");
		middlePanel.setBorder(radioButtonsBorder);

		// Para que las opciones sean excluyentes, creamos un grupo
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonOrdinario = new JRadioButton("Ordinario");
		radioButtonGroup.add(radioButtonOrdinario);
		radioButtonOrdinario.setEnabled(false);
		radioButtonElectrico = new JRadioButton("Electrico");
		radioButtonGroup.add(radioButtonElectrico);
		radioButtonElectrico.setEnabled(false);
		radioButtonMinusvalido = new JRadioButton("Minusvalido");
		radioButtonGroup.add(radioButtonMinusvalido);
		radioButtonMinusvalido.setEnabled(false);

		// Anadimos los botones al panel central
		middlePanel.add(radioButtonOrdinario);
		middlePanel.add(radioButtonElectrico);
		middlePanel.add(radioButtonMinusvalido);

		// Panel inferior
		JPanel bottomPanel = new JPanel(new GridLayout(1, 4));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());

		btnAcceder = new JButton("ACCEDER");
		btnAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					ClienteOrdinario ordinario = new ClienteOrdinario();
					ordinario.setMatricula(textFieldMatricula.getText());

					String tipoVehiculo = radioButtonGroup.getSelection().getActionCommand();
					double tarifa = 0.00;
					if (radioButtonOrdinario.isSelected()) {
						tipoVehiculo = "ordinario";
						tarifa = 0.50;
					} else if (radioButtonElectrico.isSelected()) {
						tipoVehiculo = "electrico";
						tarifa = 0.40;
					} else if (radioButtonMinusvalido.isSelected()) {
						tipoVehiculo = "minusvalido";
						tarifa = 0.30;
					}
					ordinario.setTipoVehiculo(tipoVehiculo);
					ordinario.setTarifa(tarifa);

					// Convierto fecha en long
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
					Date date = formatter.parse(textFieldHorarioActual.getText());
					long dateInLong = date.getTime();
					ordinario.setFechaEntrada(dateInLong);
					servicio.ordinarioInsert(ordinario);

					PanelClienteOrdinario panel = new PanelClienteOrdinario(frame, instance, topPanel, middlePanel, bottomPanel, ordinario);
					frame.getContentPane().add(panel);
					panel.setVisible(true);
					topPanel.setVisible(false);
					middlePanel.setVisible(false);
					bottomPanel.setVisible(false);
					setVisible(false);

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		leftBottomPanel.add(btnAcceder, new GridBagConstraints());

		JPanel middleLeftBottomPanel = new JPanel();
		middleLeftBottomPanel.setLayout(new GridBagLayout());
		btnAdquirir = new JButton("ADQUIRIR ABONO");
		btnAdquirir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					ClienteSubscrito subscrito = new ClienteSubscrito();
					subscrito.setMatricula(textFieldMatricula.getText()); // 1

					String tipoVehiculo = radioButtonGroup.getSelection().getActionCommand();
					String tipoCuota = "0.00";
					if (radioButtonOrdinario.isSelected()) {
						tipoVehiculo = "ordinario";
						tipoCuota = "0.50";
					} else if (radioButtonElectrico.isSelected()) {
						tipoVehiculo = "electrico";
						tipoCuota = "0.40";
					} else if (radioButtonMinusvalido.isSelected()) {
						tipoVehiculo = "minusvalido";
						tipoCuota = "0.30";
					}
					subscrito.setTipoVehiculo(tipoVehiculo); // 2
					subscrito.setTipoCuota(tipoCuota); // 3
					// Convierto fecha en long
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
					Date date = formatter.parse(textFieldHorarioActual.getText());
					long dateInLong = date.getTime();
					subscrito.setFechaEntrada(dateInLong); // 4
					// servicio.ordinarioInsert(ordinario);

					PanelClienteSubscrito panel = new PanelClienteSubscrito(frame, instance, subscrito);
					frame.getContentPane().add(panel);
					panel.setVisible(true);
					topPanel.setVisible(false);
					middlePanel.setVisible(false);
					bottomPanel.setVisible(false);
					setVisible(false);

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		middleLeftBottomPanel.add(btnAdquirir, new GridBagConstraints());

		JPanel middleRightBottomPanel = new JPanel();
		middleRightBottomPanel.setLayout(new GridBagLayout());
		btnPagar = new JButton("PAGAR");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Usuario usuario = servicio.usuario(textFieldMatricula.getText());

				PanelPago panel = new PanelPago(frame, instance, usuario);
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);

//				ZonedDateTime now = ZonedDateTime.now();
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
//				String initialTime = formatter.format(now);
//				PaymentPanel panel = new PaymentPanel(frame, "ordinary" ,initialTime,plateTextField.getText());
//				frame.add(panel);
//				setVisible(false);
//				panel.setVisible(true);
			}
		});
		middleRightBottomPanel.add(btnPagar, new GridBagConstraints());

//		JPanel rightBottomPanel = new JPanel();
//		rightBottomPanel.setLayout(new GridBagLayout());
//		btnVolver = new JButton("VOLVER");
//		btnVolver.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame.getContentPane().add(panel);
//				panel.setVisible(true);
//				setVisible(false);
//			}
//		});
//		rightBottomPanel.add(btnVolver);

		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(middleLeftBottomPanel);
		bottomPanel.add(middleRightBottomPanel);
//		bottomPanel.add(rightBottomPanel);

		btnAcceder.setEnabled(false);
		btnAdquirir.setEnabled(false);
		btnPagar.setEnabled(false);

		add(topPanel);
		add(middlePanel);
		add(bottomPanel);

	}
}
