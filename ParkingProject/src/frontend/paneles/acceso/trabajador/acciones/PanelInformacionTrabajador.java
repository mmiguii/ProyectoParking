package frontend.paneles.acceso.trabajador.acciones;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.personas.personal.Trabajador;

public class PanelInformacionTrabajador extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JLabel lblDatosTrabajador = new JLabel("DATOS DEL TRABAJADOR");
	private JTextField textFieldDNI;
	private JTextField textFieldFechaComienzo;
	private JTextField textFieldNombreUsuario;
	private JTextField textFieldSalario;
	private JTextField textFieldPuesto;
	private JTextField textFieldCorreo;
	private JTextField textFieldAntiguedad;

	public PanelInformacionTrabajador(JFrame frame, JPanel panel, Trabajador trabajador) {
		setBackground(new Color(0, 128, 128));

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Personal Data Panel");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBounds(10, 10, 567, 448);
		setLayout(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(0, 128, 128));
		mainPanel.setBounds(10, 16, 555, 421);
		add(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0 };
		gbl_mainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_mainPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0 };
		gbl_mainPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -196, -151, 0, -52 };
		mainPanel.setLayout(gbl_mainPanel);
		lblDatosTrabajador.setForeground(new Color(255, 255, 255));
		lblDatosTrabajador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDatosTrabajador.setBounds(183, 31, 201, 41);
		GridBagConstraints gbc_lblDatosTrabajador = new GridBagConstraints();
		gbc_lblDatosTrabajador.gridwidth = 15;
		gbc_lblDatosTrabajador.insets = new Insets(0, 0, 5, 0);
		gbc_lblDatosTrabajador.gridx = 0;
		gbc_lblDatosTrabajador.gridy = 2;
		mainPanel.add(lblDatosTrabajador, gbc_lblDatosTrabajador);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setForeground(new Color(255, 255, 255));
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDNI.setBounds(86, 82, 49, 14);
		GridBagConstraints gbc_lblDNI = new GridBagConstraints();
		gbc_lblDNI.anchor = GridBagConstraints.WEST;
		gbc_lblDNI.gridwidth = 2;
		gbc_lblDNI.insets = new Insets(0, 0, 5, 5);
		gbc_lblDNI.gridx = 2;
		gbc_lblDNI.gridy = 4;
		mainPanel.add(lblDNI, gbc_lblDNI);

		textFieldDNI = new JTextField();
		textFieldDNI.setForeground(new Color(0, 128, 128));
		textFieldDNI.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDNI.setEditable(false);
		textFieldDNI.setBounds(246, 83, 138, 20);
		GridBagConstraints gbc_textFieldDNI = new GridBagConstraints();
		gbc_textFieldDNI.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDNI.gridwidth = 9;
		gbc_textFieldDNI.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDNI.gridx = 5;
		gbc_textFieldDNI.gridy = 4;
		mainPanel.add(textFieldDNI, gbc_textFieldDNI);
		textFieldDNI.setColumns(10);

		textFieldDNI.setText(trabajador.getDni());

		JLabel lblNombreUsuario = new JLabel("Nombre de usuario");
		lblNombreUsuario.setForeground(new Color(255, 255, 255));
		lblNombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNombreUsuario.setBounds(86, 119, 134, 14);
		GridBagConstraints gbc_lblNombreUsuario = new GridBagConstraints();
		gbc_lblNombreUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblNombreUsuario.gridwidth = 2;
		gbc_lblNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreUsuario.gridx = 2;
		gbc_lblNombreUsuario.gridy = 5;
		mainPanel.add(lblNombreUsuario, gbc_lblNombreUsuario);

		textFieldNombreUsuario = new JTextField();
		textFieldNombreUsuario.setForeground(new Color(0, 128, 128));
		textFieldNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombreUsuario.setEditable(false);
		textFieldNombreUsuario.setColumns(10);
		textFieldNombreUsuario.setBounds(246, 118, 138, 20);
		GridBagConstraints gbc_textFieldNombreUsuario = new GridBagConstraints();
		gbc_textFieldNombreUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombreUsuario.gridwidth = 9;
		gbc_textFieldNombreUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombreUsuario.gridx = 5;
		gbc_textFieldNombreUsuario.gridy = 5;
		mainPanel.add(textFieldNombreUsuario, gbc_textFieldNombreUsuario);
		textFieldNombreUsuario.setText(trabajador.getNombreUsuario());

		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setForeground(new Color(255, 255, 255));
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCorreo.setBounds(86, 157, 49, 14);
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.WEST;
		gbc_lblCorreo.gridwidth = 2;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 2;
		gbc_lblCorreo.gridy = 6;
		mainPanel.add(lblCorreo, gbc_lblCorreo);

		textFieldCorreo = new JTextField();
		textFieldCorreo.setForeground(new Color(0, 128, 128));
		textFieldCorreo.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCorreo.setEditable(false);
		textFieldCorreo.setColumns(10);
		textFieldCorreo.setBounds(246, 156, 138, 20);
		GridBagConstraints gbc_textFieldCorreo = new GridBagConstraints();
		gbc_textFieldCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCorreo.gridwidth = 9;
		gbc_textFieldCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCorreo.gridx = 5;
		gbc_textFieldCorreo.gridy = 6;
		mainPanel.add(textFieldCorreo, gbc_textFieldCorreo);
		textFieldCorreo.setText(trabajador.getEmail());

		JLabel lblPuesto = new JLabel("Puesto");
		lblPuesto.setForeground(new Color(255, 255, 255));
		lblPuesto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPuesto.setBounds(86, 241, 49, 14);
		GridBagConstraints gbc_lblPuesto = new GridBagConstraints();
		gbc_lblPuesto.anchor = GridBagConstraints.WEST;
		gbc_lblPuesto.gridwidth = 2;
		gbc_lblPuesto.insets = new Insets(0, 0, 5, 5);
		gbc_lblPuesto.gridx = 2;
		gbc_lblPuesto.gridy = 7;
		mainPanel.add(lblPuesto, gbc_lblPuesto);

		textFieldPuesto = new JTextField();
		textFieldPuesto.setForeground(new Color(0, 128, 128));
		textFieldPuesto.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPuesto.setEditable(false);
		textFieldPuesto.setColumns(10);
		textFieldPuesto.setBounds(246, 240, 138, 20);
		GridBagConstraints gbc_textFieldPuesto = new GridBagConstraints();
		gbc_textFieldPuesto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPuesto.gridwidth = 9;
		gbc_textFieldPuesto.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPuesto.gridx = 5;
		gbc_textFieldPuesto.gridy = 7;
		mainPanel.add(textFieldPuesto, gbc_textFieldPuesto);
		textFieldPuesto.setText(trabajador.getPuesto());

		JLabel lblFecha = new JLabel("Fecha de comienzo");
		lblFecha.setForeground(new Color(255, 255, 255));
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFecha.setBounds(86, 283, 134, 14);
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.WEST;
		gbc_lblFecha.gridwidth = 2;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 2;
		gbc_lblFecha.gridy = 8;
		mainPanel.add(lblFecha, gbc_lblFecha);

		textFieldFechaComienzo = new JTextField();
		textFieldFechaComienzo.setForeground(new Color(0, 128, 128));
		textFieldFechaComienzo.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFechaComienzo.setEditable(false);
		textFieldFechaComienzo.setColumns(10);
		textFieldFechaComienzo.setBounds(246, 282, 138, 20);
		GridBagConstraints gbc_textFieldFechaComienzo = new GridBagConstraints();
		gbc_textFieldFechaComienzo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFechaComienzo.gridwidth = 9;
		gbc_textFieldFechaComienzo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFechaComienzo.gridx = 5;
		gbc_textFieldFechaComienzo.gridy = 8;
		mainPanel.add(textFieldFechaComienzo, gbc_textFieldFechaComienzo);
		textFieldFechaComienzo.setText(sdf.format(new Date(trabajador.getFechaComienzo())));

		JLabel lblAntiguedad = new JLabel("Antiguedad");
		lblAntiguedad.setForeground(new Color(255, 255, 255));
		lblAntiguedad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAntiguedad.setBounds(86, 323, 134, 14);
		GridBagConstraints gbc_lblAntiguedad = new GridBagConstraints();
		gbc_lblAntiguedad.anchor = GridBagConstraints.WEST;
		gbc_lblAntiguedad.gridwidth = 2;
		gbc_lblAntiguedad.insets = new Insets(0, 0, 5, 5);
		gbc_lblAntiguedad.gridx = 2;
		gbc_lblAntiguedad.gridy = 9;
		mainPanel.add(lblAntiguedad, gbc_lblAntiguedad);

		textFieldAntiguedad = new JTextField();
		textFieldAntiguedad.setForeground(new Color(0, 128, 128));
		textFieldAntiguedad.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAntiguedad.setEditable(false);
		textFieldAntiguedad.setText(trabajador.getAntiguedad() + " año/s");
		textFieldAntiguedad.setColumns(10);
		textFieldAntiguedad.setBounds(246, 322, 138, 20);
		GridBagConstraints gbc_textFieldAntiguedad = new GridBagConstraints();
		gbc_textFieldAntiguedad.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAntiguedad.gridwidth = 9;
		gbc_textFieldAntiguedad.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAntiguedad.gridx = 5;
		gbc_textFieldAntiguedad.gridy = 9;
		mainPanel.add(textFieldAntiguedad, gbc_textFieldAntiguedad);
		textFieldAntiguedad.setText(Integer.toString(trabajador.getAntiguedad()));

		JLabel lblSalario = new JLabel("Salario");
		lblSalario.setForeground(new Color(255, 255, 255));
		lblSalario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSalario.setBounds(86, 200, 49, 14);
		GridBagConstraints gbc_lblSalario = new GridBagConstraints();
		gbc_lblSalario.anchor = GridBagConstraints.WEST;
		gbc_lblSalario.gridwidth = 2;
		gbc_lblSalario.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalario.gridx = 2;
		gbc_lblSalario.gridy = 10;
		mainPanel.add(lblSalario, gbc_lblSalario);

		textFieldSalario = new JTextField();
		textFieldSalario.setForeground(new Color(0, 128, 128));
		textFieldSalario.setBackground(new Color(255, 255, 255));
		textFieldSalario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldSalario.setEditable(false);
		textFieldSalario.setColumns(10);
		textFieldSalario.setBounds(246, 199, 138, 20);
		GridBagConstraints gbc_textFieldSalario = new GridBagConstraints();
		gbc_textFieldSalario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSalario.gridwidth = 9;
		gbc_textFieldSalario.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSalario.gridx = 5;
		gbc_textFieldSalario.gridy = 10;
		mainPanel.add(textFieldSalario, gbc_textFieldSalario);
		textFieldSalario.setText(Double.toString(trabajador.getSalario()) + " €");

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setForeground(new Color(0, 128, 128));
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVolver.setBounds(226, 381, 94, 29);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 0);
		gbc_btnVolver.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVolver.gridwidth = 9;
		gbc_btnVolver.gridx = 3;
		gbc_btnVolver.gridy = 13;
		mainPanel.add(btnVolver, gbc_btnVolver);
	}
}
