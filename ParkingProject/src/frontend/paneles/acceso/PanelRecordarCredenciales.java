package frontend.paneles.acceso;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

	public PanelRecordarCredenciales(JFrame frame, JPanel panel, List<Trabajador> trabajadores) {
		setLayout(null);

		textFieldNombreUsuario = new JTextField();
		textFieldNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombreUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textFieldNombreUsuario.setText("");
			}
		});
		textFieldNombreUsuario.setBounds(86, 99, 298, 26);
		textFieldNombreUsuario.setText("Nombre de usuario");
		add(textFieldNombreUsuario);
		textFieldNombreUsuario.setColumns(10);

		JLabel lblTexto = new JLabel("Introduzca su usuario y password");
		lblTexto.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 17));
		lblTexto.setBounds(73, 51, 312, 20);
		add(lblTexto);

		JButton btnRecuperarContrasea = new JButton("Recuperar credenciales");
		btnRecuperarContrasea.setBounds(226, 207, 218, 29);
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
		btnCancelar.setBounds(30, 207, 201, 29);
		btnCancelar.setBackground(new Color(152, 240, 153));
		add(btnCancelar);

		passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setBounds(87, 137, 298, 26);
		add(passwordFieldPassword);

		btnRecuperarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreTrabajador = textFieldNombreUsuario.getText();
				String passwordTrabajador = String.valueOf(passwordFieldPassword.getPassword());
				boolean encontrado = false;
				Trabajador trabajador = null;
				for (Trabajador t : trabajadores) {

					if (t.getNombreUsuario().equals(nombreTrabajador)) {
						// Usuario existe
						if (t.getPassword().equals(passwordTrabajador)) {
							// Usuario encontrado
							encontrado = true;
							trabajador = t;
							break;
						}
					}

				}
				if (encontrado == false) {
					JOptionPane.showMessageDialog(PanelRecordarCredenciales.this,
							"El nombre de usuario y password introducido no coinciden. Intentelo de nuevo.");
				} else {
					JOptionPane.showMessageDialog(PanelRecordarCredenciales.this,
							"En breve recibiras un email en tu correo.");
					EnvioEmail.bienvenida(trabajador.getEmail(), trabajador.getNombreUsuario(), trabajador.getDni());

					JOptionPane.showMessageDialog(PanelRecordarCredenciales.this,
							"\nEl mensage ha sido enviado con exito."
									+ "\nEl mensage de recuperacion de credenciales ha sido enviado al siguiente correo: "
									+ trabajador.getEmail());
					frame.dispose();
					ServicioPersistenciaBD.disconnect();
					System.exit(0);
				}
			}
		});
	}

}