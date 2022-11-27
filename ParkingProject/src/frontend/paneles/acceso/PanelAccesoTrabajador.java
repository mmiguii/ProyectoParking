package frontend.paneles.acceso;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import backend.clases.personas.personal.Empleado;
import backend.clases.personas.personal.Manager;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.trabajador.empleado.PanelEmpleado;
import frontend.paneles.trabajador.manager.PanelManager;

public class PanelAccesoTrabajador extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextField textFieldDNI;
	private JButton btnIniciarSesion;

	private JPanel instance;

	private ServicioPersistenciaBD servicio;
	private ArrayList<Trabajador> trabajadores;

	public PanelAccesoTrabajador(JFrame frame, JPanel panel) {

		instance = this;

		servicio = new ServicioPersistenciaBD();

		trabajadores = servicio.trabajadoresSelect();

		setBorder(javax.swing.BorderFactory.createTitledBorder("General Worker Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel lblTextoLogin = new JLabel("Inicio de sesion del parking");
		topPanel.add(lblTextoLogin);

		add(topPanel);

		JPanel middlePanel = new JPanel(new GridLayout(3, 1));

		JPanel topMiddlePanel = new JPanel();
		topMiddlePanel.setLayout(new GridBagLayout());
		JLabel lblNombre = new JLabel("Nombre: ");
		textFieldNombre = new JTextField(10);
		topMiddlePanel.add(lblNombre);
		topMiddlePanel.add(textFieldNombre);

		JPanel middleMiddlePanel = new JPanel();
		middleMiddlePanel.setLayout(new GridBagLayout());
		JLabel lblApellido = new JLabel("Apellidos: ");
		textFieldApellido = new JTextField(10);
		middleMiddlePanel.add(lblApellido);
		middleMiddlePanel.add(textFieldApellido);

		JPanel bottomMiddlePanel = new JPanel();
		bottomMiddlePanel.setLayout(new GridBagLayout());
		JLabel lblDNI = new JLabel("DNI: ");
		textFieldDNI = new JTextField(10);
		bottomMiddlePanel.add(lblDNI);
		GridBagConstraints gbc_textFieldDNI = new GridBagConstraints();
		gbc_textFieldDNI.gridx = 1;
		bottomMiddlePanel.add(textFieldDNI, gbc_textFieldDNI);

		middlePanel.add(topMiddlePanel);
		middlePanel.add(middleMiddlePanel);
		middlePanel.add(bottomMiddlePanel);

//		JLabel labelNombre = new JLabel("Nombre: ");
//		labelNombre.setBounds(187, 24, 80, 14);
//		JLabel labelApellido = new JLabel("Apellidos: ");
//		labelApellido.setBounds(187, 62, 80, 14);
//		JLabel labelDNI = new JLabel("DNI: ");
//		labelDNI.setBounds(187, 103, 80, 14);
//		
//		textNombre = new JTextField(15);
//		textNombre.setBounds(277, 21, 84, 20);
//		textApellido = new JTextField(15);
//		textApellido.setBounds(277, 59, 84, 20);
//		textDNI = new JTextField(10);
//		textDNI.setBounds(277, 100, 84, 20);

		textFieldNombre.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				processTextField();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				processTextField();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				processTextField();
			}
		});

		textFieldApellido.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				processTextField();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				processTextField();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				processTextField();
			}
		});

		textFieldDNI.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				processTextField();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				processTextField();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				processTextField();
			}
		});

//		middlePanel.setLayout(new GridBagLayout());
//		
//		middlePanel.add(labelNombre);
//		middlePanel.add(textNombre);
//		middlePanel.add(labelApellido);
//		middlePanel.add(textApellido);
//		middlePanel.add(labelDNI);
//		middlePanel.add(textDNI);
		add(middlePanel);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());
		btnIniciarSesion = new JButton("INICIAR SESION");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<Trabajador> trabajadores = servicio.trabajadoresSelect();
				System.out.println(trabajadores.get(0));
				for (Trabajador t : trabajadores) {
					System.out.println(t.getDni());
					if (t.getDni().equals(textFieldDNI.getText())) {
						if (t instanceof Manager) {
							PanelManager panel = new PanelManager(frame, instance, t);
							frame.getContentPane().add(panel);
							setVisible(false);
							panel.setVisible(true);
						} else {
							PanelEmpleado panel = new PanelEmpleado(frame, instance, t);
							frame.getContentPane().add(panel);
							setVisible(false);
							panel.setVisible(true);
						}

					}

				}

			}

		});
		GridBagConstraints gbc_btnIniciarSesion = new GridBagConstraints();
		gbc_btnIniciarSesion.insets = new Insets(0, 0, 5, 0);
		gbc_btnIniciarSesion.gridx = 0;
		gbc_btnIniciarSesion.gridy = 0;
		leftBottomPanel.add(btnIniciarSesion, gbc_btnIniciarSesion);

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
//		btnVolver.setBounds(119, 67, 101, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel); 
				panel.setVisible(true); 
				setVisible(false);
			}
		});
		rightBottomPanel.add(btnVolver);

		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(rightBottomPanel);

//		btnLogIn.doLayout(new FlowLayout(FlowLayout.LEFT));		
//		btnLogIn.setBounds(329, 67, 149, 23);
		btnIniciarSesion.setEnabled(false);
		
		JLabel lblRecuerdoDNI = new JLabel("* Recordar DNI");
		lblRecuerdoDNI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PanelRecordarDNI panel = new PanelRecordarDNI(frame, instance, trabajadores);
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		GridBagConstraints gbc_lblRecuerdoDNI = new GridBagConstraints();
		gbc_lblRecuerdoDNI.gridx = 0;
		gbc_lblRecuerdoDNI.gridy = 2;
		leftBottomPanel.add(lblRecuerdoDNI, gbc_lblRecuerdoDNI);
//		bottomPanel.setLayout(new GridBagLayout());
//		bottomPanel.add(btnVolver, new GridBagConstraints());
//		bottomPanel.add(btnLogIn, new GridBagConstraints());
		add(bottomPanel);
	}

	private void processTextField() {
		if (textFieldNombre.getText().isBlank() || textFieldApellido.getText().isBlank()
				|| textFieldDNI.getText().isBlank()) {
			btnIniciarSesion.setEnabled(false);
		} else {
			btnIniciarSesion.setEnabled(true);
		}
	}

}
