package frontend.paneles.acceso.trabajador.acciones;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import backend.clases.personas.personal.Trabajador;

public class PersonalDataWorkerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JLabel lblNewLabel = new JLabel("Datos del trabajador");
	private JTextField textFieldDNI;
	private JTextField textFieldFechaComienzo;
	private JTextField textFieldUsername;
	private JTextField textFieldSalario;
	private JTextField textFieldPuesto;
	private JTextField textFieldEMail;
	private JTextField textFieldAntiguedad;

	public PersonalDataWorkerPanel(JFrame frame, JPanel panel, Trabajador trabajador) {

		setBorder(javax.swing.BorderFactory.createTitledBorder("Personal Data Panel"));
		setBounds(10, 10, 567, 448);
		setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(10, 16, 555, 421);
		add(mainPanel);
		mainPanel.setLayout(null);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(183, 31, 201, 41);
		mainPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1_2 = new JLabel("E-mail");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(86, 157, 49, 14);
		mainPanel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Puesto");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_3.setBounds(86, 241, 49, 14);
		mainPanel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Fecha de comienzo");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_3_1.setBounds(86, 283, 134, 14);
		mainPanel.add(lblNewLabel_1_3_1);
		
		JLabel lblNewLabel_1_3_2 = new JLabel("Salario");
		lblNewLabel_1_3_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_3_2.setBounds(86, 200, 49, 14);
		mainPanel.add(lblNewLabel_1_3_2);
		
		JLabel lblNewLabel_1_3_3 = new JLabel("DNI");
		lblNewLabel_1_3_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_3_3.setBounds(86, 82, 49, 14);
		mainPanel.add(lblNewLabel_1_3_3);
		
		JLabel lblNewLabel_1_3_4 = new JLabel("Nombre de usuario");
		lblNewLabel_1_3_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_3_4.setBounds(86, 119, 134, 14);
		mainPanel.add(lblNewLabel_1_3_4);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("Antiguedad");
		lblNewLabel_1_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_3_1_1.setBounds(86, 323, 134, 14);
		mainPanel.add(lblNewLabel_1_3_1_1);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnVolver.setBounds(226, 381, 94, 29);
		mainPanel.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		
		textFieldDNI = new JTextField();
		textFieldDNI.setBounds(246, 83, 138, 20);
		mainPanel.add(textFieldDNI);
		textFieldDNI.setColumns(10);
		
		textFieldFechaComienzo = new JTextField();
		textFieldFechaComienzo.setColumns(10);
		textFieldFechaComienzo.setBounds(246, 282, 138, 20);
		mainPanel.add(textFieldFechaComienzo);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		textFieldUsername.setBounds(246, 118, 138, 20);
		mainPanel.add(textFieldUsername);
		
		textFieldSalario = new JTextField();
		textFieldSalario.setColumns(10);
		textFieldSalario.setBounds(246, 199, 138, 20);
		mainPanel.add(textFieldSalario);
		
		textFieldPuesto = new JTextField();
		textFieldPuesto.setColumns(10);
		textFieldPuesto.setBounds(246, 240, 138, 20);
		mainPanel.add(textFieldPuesto);
		
		textFieldEMail = new JTextField();
		textFieldEMail.setColumns(10);
		textFieldEMail.setBounds(246, 156, 138, 20);
		mainPanel.add(textFieldEMail);
		
		textFieldAntiguedad = new JTextField();
		textFieldAntiguedad.setText("01/01/1970 01:00:00");
		textFieldAntiguedad.setColumns(10);
		textFieldAntiguedad.setBounds(246, 322, 138, 20);
		mainPanel.add(textFieldAntiguedad);
		
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		textFieldDNI.setText(trabajador.getDni());
		textFieldFechaComienzo.setText(sdf.format(new Date(trabajador.getFechaComienzo())));
		textFieldUsername.setText(trabajador.getNombreUsuario());
		textFieldSalario.setText(Double.toString(trabajador.getSalario()));
		textFieldPuesto.setText(trabajador.getPuesto());
		textFieldEMail.setText(trabajador.getEmail());
		textFieldAntiguedad.setText(Integer.toString(trabajador.getAntiguedad()));
	}
}
