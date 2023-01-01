package frontend.paneles.acceso;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.email.EnvioEmail;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;

public class PanelRecordarCredenciales extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreUsuario;
	private JPasswordField passwordFieldPassword; 

	private static Logger logger = Logger.getLogger(PanelRecordarCredenciales.class.getName());

	public PanelRecordarCredenciales(JFrame frame, JPanel panel, Map<String, Trabajador> trabajadores) {
		setBackground(new Color(0, 128, 128));
		setLayout(null);
		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Panel de recuperacion de credenciales");
		border.setTitleColor(Color.WHITE);
		setBorder(border);

		textFieldNombreUsuario = new JTextField();
		textFieldNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombreUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textFieldNombreUsuario.setText("");
			}
		});
		textFieldNombreUsuario.setBounds(138, 171, 298, 26);
		textFieldNombreUsuario.setText("Nombre de usuario");
		add(textFieldNombreUsuario);
		textFieldNombreUsuario.setColumns(10);

		passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setBounds(138, 242, 298, 26);
		add(passwordFieldPassword);

		JLabel lblTexto = new JLabel("Introduzca su usuario y DNI");
		lblTexto.setForeground(new Color(255, 255, 255));
		lblTexto.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 17));
		lblTexto.setBounds(156, 77, 312, 20);
		add(lblTexto);

		JButton btnRecuperarContrasea = new JButton("Recuperar credenciales");
		btnRecuperarContrasea.setBounds(294, 322, 218, 29);
		btnRecuperarContrasea.setBackground(new Color(255, 102, 102));
		add(btnRecuperarContrasea);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		btnCancelar.setBounds(61, 322, 201, 29);
		btnCancelar.setBackground(new Color(152, 240, 153));
		add(btnCancelar);

		btnRecuperarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Crea una instancia de la clase Thread y le pasa una instancia de una clase
				// anonima que implemente la interface Runnable
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						// Aquí va el código que quieres que se ejecute en el thread
						String nombreTrabajador = textFieldNombreUsuario.getText();
						String dniTrabajador = String.valueOf(passwordFieldPassword.getPassword());
						boolean encontrado = false;

						for (Map.Entry<String, Trabajador> entry : trabajadores.entrySet()) {
							Trabajador trabajador = entry.getValue();
							if (trabajador.getNombreUsuario().equals(nombreTrabajador)
									&& trabajador.getDni().equals(dniTrabajador)) {
								encontrado = true;
//								ServicioPersistenciaBD.getInstance().connect("Parking.db");
								String nuevoPass = ServicioPersistenciaBD.getInstance()
										.trabajadoresUpdate(trabajador.getDni());
								try {
									EnvioEmail.bienvenida(trabajador.getEmail(), "Recuperación de credenciales",
											"Su usuario es: " + nombreTrabajador + " y su contraseña es: " + nuevoPass);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									logger.info("El fichero de propiedades no existe");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									logger.info("No se ha leido correctamente del fichero de propiedades");
								}
								logger.info("Mensaje enviado.");
								JOptionPane.showMessageDialog(null,
										"Se ha enviado un email con sus credenciales al correo: "
												+ trabajador.getEmail());
								setVisible(false);
								panel.setVisible(true);
								break;
							}
						}

						if (!encontrado) {
							logger.info("No se ha encontrado ningun trabajador con los datos introducidos");
							JOptionPane.showMessageDialog(null,
									"No se ha encontrado ningun trabajador con ese nombre de usuario y DNI introducidos");
						}
					}
				});

				// Inicia el thread
				thread.start();
			}
		});
	}

}
