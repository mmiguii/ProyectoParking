package frontend.paneles.acceso;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
import frontend.VentanaPrincipal;

public class PanelRecordarCredenciales extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel panel;
	private Map<String, Trabajador> trabajadores;
	private JTextField textFieldNombreUsuario;
	private JPasswordField passwordFieldPassword;

	private static Logger logger = Logger.getLogger(PanelRecordarCredenciales.class.getName());

	public PanelRecordarCredenciales(JFrame frame, JPanel panel, Map<String, Trabajador> trabajadores) {

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory
				.createTitledBorder("Panel de recuperacion de credenciales");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBackground(new Color(0, 128, 128));
		setLayout(null);

		this.frame = frame;
		this.panel = panel;
		this.trabajadores = trabajadores;

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

		JButton btnRecuperarPassword = new JButton("Recuperar credenciales");
		btnRecuperarPassword.addActionListener(this::recuperarPassword);
		btnRecuperarPassword.setBounds(294, 322, 218, 29);
		btnRecuperarPassword.setForeground(new Color(0, 128, 128));
		add(btnRecuperarPassword);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this::cancelar);
		btnCancelar.setBounds(61, 322, 201, 29);
		btnCancelar.setForeground(new Color(0, 128, 128));
		add(btnCancelar);

	}

	private void recuperarPassword(ActionEvent event) {
		// Crea una instancia de la clase Thread y le pasa una instancia de una clase
		// anonima que implemente la interface Runnable
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// Aqu?? va el c??digo que quieres que se ejecute en el thread
				String nombreTrabajador = textFieldNombreUsuario.getText();
				String dniTrabajador = String.valueOf(passwordFieldPassword.getPassword());
				boolean encontrado = false;

				for (Map.Entry<String, Trabajador> entry : trabajadores.entrySet()) {
					Trabajador trabajador = entry.getValue();
					if (trabajador.getNombreUsuario().equals(nombreTrabajador)
							&& trabajador.getDni().equals(dniTrabajador)) {
						encontrado = true;
						String nuevoPass = ServicioPersistenciaBD.getInstance().trabajadoresUpdate(trabajador.getDni());
						try {
							EnvioEmail.bienvenida(trabajador.getEmail(), "Recuperaci??n de credenciales",
									"Su usuario es: " + nombreTrabajador + " y su contrase??a es: " + nuevoPass);
						} catch (FileNotFoundException e) {
							logger.info("El fichero de propiedades no existe");
						} catch (IOException e) {
							logger.info("No se ha leido correctamente del fichero de propiedades");
						}
						logger.info("Mensaje enviado.");
						JOptionPane.showMessageDialog(null,
								"Se ha enviado un email con sus credenciales al correo: " + trabajador.getEmail());
						setVisible(false);
						frame.dispose();
						frame = new VentanaPrincipal();
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

	private void cancelar(ActionEvent event) {
		logger.info("Cancelando ...");
		frame.getContentPane().add(panel);
		panel.setVisible(true);
		setVisible(false);
	}

}
