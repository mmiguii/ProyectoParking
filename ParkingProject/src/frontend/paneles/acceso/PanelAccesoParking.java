package frontend.paneles.acceso;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import frontend.paneles.acceso.clientes.PanelAccesoOrdinariosSeleccionPlaza;
import frontend.paneles.acceso.clientes.PanelAccesoSubscritosSeleccionAbono;

public class PanelAccesoParking extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel instance;
	private JButton btnComprarBono;
	private JButton btnAcceder;
	private JRadioButton radioButtonOrdinario;
	private JRadioButton radioButtonElectrico;
	private JRadioButton radioButtonMinusvalido;
	private JTextField textFieldHoraEntrada;
	private JTextField textFieldMatricula;
	private JLabel lblBienvenida;
	private JLabel lblSeleccionTipo;
	private JFrame frame;
	private DateFormat formatter;
	private String horaEntrada;

	private static Logger logger = Logger.getLogger(PanelAccesoParking.class.getName());

	public PanelAccesoParking(JFrame frame, JPanel panel, String horaEntrada, String matricula) {

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory
				.createTitledBorder("Panel de acceso al parking");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBackground(new Color(0, 128, 128));
		setBounds(10, 10, 567, 448);
		setLayout(new GridLayout(3, 1));

		instance = this;
		formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

		this.frame = frame;
		this.horaEntrada = horaEntrada;

		// PANEL SUPERIOR
		JPanel topPanel = new JPanel();
		topPanel.setForeground(new Color(255, 255, 255));
		topPanel.setBackground(new Color(0, 128, 128));
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[] { 0, 503, 503, 0 };
		gbl_topPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0 };
		topPanel.setLayout(gbl_topPanel);

		lblBienvenida = new JLabel("Bienvenido a Deusto Parking");
		lblBienvenida.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblBienvenida = new GridBagConstraints();
		gbc_lblBienvenida.gridwidth = 2;
		gbc_lblBienvenida.insets = new Insets(0, 0, 5, 5);
		gbc_lblBienvenida.gridx = 1;
		gbc_lblBienvenida.gridy = 0;
		topPanel.add(lblBienvenida, gbc_lblBienvenida);

		JLabel lblHoraEntrada = new JLabel("Hora de entrada");
		lblHoraEntrada.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblHoraEntrada = new GridBagConstraints();
		gbc_lblHoraEntrada.insets = new Insets(0, 0, 5, 5);
		gbc_lblHoraEntrada.gridx = 1;
		gbc_lblHoraEntrada.gridy = 1;
		topPanel.add(lblHoraEntrada, gbc_lblHoraEntrada);

		textFieldHoraEntrada = new JTextField(horaEntrada);
		textFieldHoraEntrada.setBackground(new Color(255, 255, 255));
		textFieldHoraEntrada.setForeground(new Color(0, 128, 128));
		textFieldHoraEntrada.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldHoraEntrada.setColumns(10);
		textFieldHoraEntrada.setEditable(false);
		GridBagConstraints gbc_textFieldHoraEntrada = new GridBagConstraints();
		gbc_textFieldHoraEntrada.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldHoraEntrada.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldHoraEntrada.gridx = 2;
		gbc_textFieldHoraEntrada.gridy = 1;
		topPanel.add(textFieldHoraEntrada, gbc_textFieldHoraEntrada);

		JLabel lblMatricula = new JLabel("Matricula");
		lblMatricula.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblMatricula = new GridBagConstraints();
		gbc_lblMatricula.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatricula.gridx = 1;
		gbc_lblMatricula.gridy = 2;
		topPanel.add(lblMatricula, gbc_lblMatricula);

		textFieldMatricula = new JTextField(matricula);
		textFieldMatricula.setForeground(new Color(0, 128, 128));
		textFieldMatricula.setBackground(new Color(255, 255, 255));
		textFieldMatricula.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMatricula.setColumns(10);
		textFieldMatricula.setEditable(false);
		GridBagConstraints gbc_textFieldMatricula = new GridBagConstraints();
		gbc_textFieldMatricula.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMatricula.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldMatricula.gridx = 2;
		gbc_textFieldMatricula.gridy = 2;
		topPanel.add(textFieldMatricula, gbc_textFieldMatricula);

		// PANEL CENTRAL
		JPanel middlePanel = new JPanel();
		middlePanel.setForeground(new Color(255, 255, 255));
		middlePanel.setBackground(new Color(0, 128, 128));
		GridBagLayout gbl_middlePanel = new GridBagLayout();
		gbl_middlePanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		middlePanel.setLayout(gbl_middlePanel);
		javax.swing.border.TitledBorder borderMid = javax.swing.BorderFactory.createTitledBorder("Seleccion de plaza");
		borderMid.setTitleColor(Color.WHITE);
		middlePanel.setBorder(borderMid);

		// Se crea un grupo para que las opciones sean excluyentes
		ButtonGroup radioButtonGroup = new ButtonGroup();

		lblSeleccionTipo = new JLabel("Seleccione el tipo de plaza que va ocupar");
		lblSeleccionTipo.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblSeleccionTipo = new GridBagConstraints();
		gbc_lblSeleccionTipo.gridwidth = 2;
		gbc_lblSeleccionTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionTipo.gridx = 0;
		gbc_lblSeleccionTipo.gridy = 0;
		middlePanel.add(lblSeleccionTipo, gbc_lblSeleccionTipo);

		radioButtonOrdinario = new JRadioButton("Ordinario");
		radioButtonOrdinario.setForeground(new Color(255, 255, 255));
		radioButtonOrdinario.setBackground(new Color(0, 128, 128));
		radioButtonOrdinario.addActionListener(this::habilitarBotones);
		radioButtonGroup.add(radioButtonOrdinario); // Anadimos el boton al grupo
		GridBagConstraints gbc_radioButtonOrdinario = new GridBagConstraints();
		gbc_radioButtonOrdinario.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonOrdinario.gridx = 0;
		gbc_radioButtonOrdinario.gridy = 1;
		middlePanel.add(radioButtonOrdinario, gbc_radioButtonOrdinario);

		radioButtonElectrico = new JRadioButton("Electrico");
		radioButtonElectrico.setForeground(new Color(255, 255, 255));
		radioButtonElectrico.setBackground(new Color(0, 128, 128));
		radioButtonElectrico.addActionListener(this::habilitarBotones);
		radioButtonGroup.add(radioButtonElectrico);
		GridBagConstraints gbc_radioButtonElectrico = new GridBagConstraints();
		gbc_radioButtonElectrico.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonElectrico.gridx = 1;
		gbc_radioButtonElectrico.gridy = 1;
		middlePanel.add(radioButtonElectrico, gbc_radioButtonElectrico);

		radioButtonMinusvalido = new JRadioButton("Minusvalido");
		radioButtonMinusvalido.setForeground(new Color(255, 255, 255));
		radioButtonMinusvalido.setBackground(new Color(0, 128, 128));
		radioButtonMinusvalido.addActionListener(this::habilitarBotones);
		radioButtonGroup.add(radioButtonMinusvalido);
		GridBagConstraints gbc_radioButtonMinusvalido = new GridBagConstraints();
		gbc_radioButtonMinusvalido.insets = new Insets(0, 0, 5, 5);
		gbc_radioButtonMinusvalido.gridx = 2;
		gbc_radioButtonMinusvalido.gridy = 1;
		middlePanel.add(radioButtonMinusvalido, gbc_radioButtonMinusvalido);

		// PANEL INFERIOR
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

		// PANEL INFERIOR (IZQ)
		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setBackground(new Color(0, 128, 128));
		leftBottomPanel.setLayout(new GridBagLayout());

		btnAcceder = new JButton("ACCEDER");
		btnAcceder.setForeground(new Color(0, 128, 128));
		btnAcceder.setEnabled(false);
		btnAcceder.addActionListener(this::acceder);
		leftBottomPanel.add(btnAcceder, new GridBagConstraints());

		// PANEL INFERIOR (DCH)
		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setBackground(new Color(0, 128, 128));
		rightBottomPanel.setLayout(new GridBagLayout());

		btnComprarBono = new JButton("COMPRAR ABONO");
		btnComprarBono.setForeground(new Color(0, 128, 128));
		btnComprarBono.addActionListener(this::comprarAbono);
		btnComprarBono.setEnabled(false);
		rightBottomPanel.add(btnComprarBono, new GridBagConstraints());

		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(rightBottomPanel);

		add(topPanel);
		add(middlePanel);
		add(bottomPanel);
	}

	private void comprarAbono(ActionEvent event) {
		try {

			ClienteSubscrito subscrito = new ClienteSubscrito();
			subscrito.setMatricula(textFieldMatricula.getText());

			// Crea un Map que almacena las cuotas de cada tipo de veh??culo
			Map<String, String> cuotas = new HashMap<>();
			cuotas.put(radioButtonOrdinario.getText(), "0.05");
			cuotas.put(radioButtonElectrico.getText(), "0.04");
			cuotas.put(radioButtonMinusvalido.getText(), "0.03");

			// Obtiene el tipo de veh??culo seleccionado y su tarifa correspondiente
			String tipoVehiculo = radioButtonOrdinario.isSelected() ? radioButtonOrdinario.getText()
					: (radioButtonElectrico.isSelected() ? radioButtonElectrico.getText()
							: radioButtonMinusvalido.getText());
			String tipoCuota = cuotas.get(tipoVehiculo);

			// Asigna el tipo de veh??culo y la cuota al objeto subscrito
			subscrito.setTipoVehiculo(tipoVehiculo);
			subscrito.setTipoCuota(tipoCuota);
			subscrito.setFechaEntrada(formatter.parse(horaEntrada).getTime());

			logger.info("Accediendo a la seleccion de la plaza y tiempo de estancia...");

			PanelAccesoSubscritosSeleccionAbono panel = new PanelAccesoSubscritosSeleccionAbono(frame, instance,
					subscrito);
			frame.getContentPane().add(panel);
			panel.setVisible(true);
			setVisible(false);
		} catch (ParseException e1) {
			logger.severe(String.format("%s %s", e1.getMessage(), e1.getCause().getMessage()));
		}
	}

	private void habilitarBotones(ActionEvent event) {
		btnAcceder.setEnabled(true);
		btnComprarBono.setEnabled(true);
	}

	private void acceder(ActionEvent event) {
		try {
			ClienteOrdinario ordinario = new ClienteOrdinario();
			ordinario.setMatricula(textFieldMatricula.getText());

			// Crea un Map que almacena las tarifas de cada tipo de veh??culo
			Map<String, Double> tarifas = new HashMap<>();
			tarifas.put(radioButtonOrdinario.getText(), 0.05);
			tarifas.put(radioButtonElectrico.getText(), 0.04);
			tarifas.put(radioButtonMinusvalido.getText(), 0.03);

			// Obtiene el tipo de veh??culo seleccionado y su tarifa correspondiente
			String tipoVehiculo = radioButtonOrdinario.isSelected() ? radioButtonOrdinario.getText()
					: (radioButtonElectrico.isSelected() ? radioButtonElectrico.getText()
							: radioButtonMinusvalido.getText());
			double tarifa = tarifas.get(tipoVehiculo);

			// Asigna el tipo de veh??culo y la tarifa al objeto ordinario
			ordinario.setTipoVehiculo(tipoVehiculo);
			ordinario.setTarifa(tarifa);
			ordinario.setFechaEntrada(formatter.parse(horaEntrada).getTime());

			logger.info("Accediendo a la seleccion de la plaza...");

			PanelAccesoOrdinariosSeleccionPlaza panel = new PanelAccesoOrdinariosSeleccionPlaza(frame, instance,
					ordinario);
			frame.getContentPane().add(panel);
			panel.setVisible(true);
			setVisible(false);

		} catch (ParseException e1) {
			logger.severe(String.format("%s %s", e1.getMessage(), e1.getCause().getMessage()));
		}
	}

}
