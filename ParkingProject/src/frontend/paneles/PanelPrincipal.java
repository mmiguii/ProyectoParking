package frontend.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.Usuario;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.acceso.PanelAccesoParking;
import frontend.paneles.acceso.PanelRecordarCredenciales;
import frontend.paneles.acceso.clientes.PanelPlazaParking;
import frontend.paneles.acceso.clientes.pago.PanelPago;
import frontend.paneles.acceso.trabajador.PanelTrabajador;

public class PanelPrincipal extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblHoraActual;
	private JTextField textFieldMatricula;
	private JPasswordField passwordFieldCredenciales;
	private JPanel instance;
	private Map<String, Trabajador> trabajadores;

	private static Logger logger = Logger.getLogger(PanelPrincipal.class.getName());

	public PanelPrincipal(JFrame frame) {

		{
			setBorder(javax.swing.BorderFactory.createTitledBorder("Inicio"));
			setBounds(10, 10, 567, 448);
			setLayout(new GridLayout(1, 2));
		}

		ServicioPersistenciaBD.getInstance().connect("Parking.db");

		instance = this;
		trabajadores = ServicioPersistenciaBD.getInstance().trabajadoresSelect();

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setBackground(new Color(0, 128, 128));

		JPanel rightPanel = new JPanel(new GridLayout(2, 1));

		JPanel rightTopPanel = new JPanel();
		rightTopPanel.setLayout(null);
		rightTopPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Area cliente"));

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(null);
		rightBottomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Area Trabajador"));

		rightPanel.add(rightTopPanel);

		JButton btnContinuarPanelCliente = new JButton("CONTINUAR");
		btnContinuarPanelCliente.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Map<String, Usuario> usuarios = ServicioPersistenciaBD.getInstance().getAllUsuarios();
				List<String> matriculas = usuarios.keySet().stream().collect(Collectors.toList());
				String matricula = textFieldMatricula.getText();

				if (matricula.substring(matricula.length() - 3).toUpperCase()
						.equals(matricula.substring(matricula.length() - 3))) {
					if (matricula.length() == 7) {
						String digitos = matricula.substring(0, 4);

						if (digitos.matches("[0-9]*")) {
							String caracteres = matricula.substring(4, 7);

							if (caracteres.matches("[A-Z]*") && caracteres.matches("[^AEIOU]*")) {
								boolean existeMatricula = matriculas.contains(textFieldMatricula.getText());

								if (existeMatricula) {
									logger.info("El vehiculo se encuentra actualmente registrado en la BD del parking");
									Usuario usuario = ServicioPersistenciaBD.getInstance()
											.getUsuario(textFieldMatricula.getText());

									if (usuario instanceof ClienteOrdinario) {
										logger.info("¡Es hora de pagar!");
										mostrarProgresoAcceso("Accediendo a la maquina de pago...");
										PanelPago panel = new PanelPago(frame, instance, usuario, null,
												lblHoraActual.getText());
										frame.getContentPane().add(panel);
										panel.setVisible(true);
										setVisible(false);

									} else {
										try {
											logger.info("¡Bienvenido de nuevo!");
											SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
											Date fechaActualDate = f.parse(lblHoraActual.getText());
											Date fechaSalidaDate = new Date(usuario.getFechaSalida());

											if (fechaActualDate.before(fechaSalidaDate)) {
												logger.info("Accediendo a la plaza...");
												mostrarProgresoAcceso("Accediendo a la plaza ...");
												PanelPlazaParking panel = new PanelPlazaParking(frame, usuario);
												frame.getContentPane().add(panel);
												setVisible(false);
												panel.setVisible(true);

											} else {
												logger.info("Lo sentimos. Su tiempo ha expirado.");
												Map<Integer, Plaza> plazasMap = ServicioPersistenciaBD.getInstance()
														.plazasSelect();
												Plaza plaza = plazasMap.values().stream()
														.filter(p -> p.getMatricula().equals(usuario.getMatricula()))
														.findFirst().orElse(null);

												ServicioPersistenciaBD.getInstance().subscritoDelete(matricula);
												ServicioPersistenciaBD.getInstance().updatePlaza(plaza, "DISPONIBLE",
														"");

												int opcion = JOptionPane.showConfirmDialog(PanelPrincipal.this,
														"Desea volver acceder al parking?", "Confirmación",
														JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

												if (opcion == 0) {
													logger.info("Nuevo acceso al parking");
													mostrarProgresoAcceso("Accediendo nuevamente al parking ...");
													PanelAccesoParking panel = new PanelAccesoParking(frame, instance,
															lblHoraActual.getText(), matricula);
													frame.getContentPane().add(panel);
													setVisible(false);
													panel.setVisible(true);

												} else {
													logger.info("Cerrando aplicacion...");
													frame.dispose();
													ServicioPersistenciaBD.getInstance().disconnect();
													System.exit(0);
												}
											}
										} catch (ParseException e1) {
											logger.severe(String.format("%s %s", e1.getMessage(),
													e1.getCause().getMessage()));
										}
									}

								} else {
									logger.info(
											"El vehiculo no se encuentra actualmente registrado en la BD del parking");
									mostrarProgresoAcceso("Accediendo al parking ...");
									PanelAccesoParking panel = new PanelAccesoParking(frame, instance,
											lblHoraActual.getText(), matricula);
									frame.getContentPane().add(panel);
									setVisible(false);
									panel.setVisible(true);
								}

							} else {
								logger.info("Ingrese correctamente la matricula (Caracteres = 3 && Consonantes)");
								JOptionPane.showMessageDialog(PanelPrincipal.this,
										"Ingrese correctamente la matricula (Caracteres = 3 && Consonantes)");
								textFieldMatricula.setText("");
							}

						} else {
							logger.info("Ingrese correctamente la matricula (Digitos = 4)");
							JOptionPane.showMessageDialog(PanelPrincipal.this,
									"Ingrese correctamente la matricula (Digitos = 4)");
							textFieldMatricula.setText("");
						}

					} else {
						logger.info("Ingrese correctamente la matricula (Longitud = 7)");
						JOptionPane.showMessageDialog(PanelPrincipal.this,
								"Ingrese correctamente la matricula (Longitud = 7)");
						textFieldMatricula.setText("");
					}

				} else {
					logger.info("Ingrese correctamente la matricula (Caracteres MAYUSCULAS)");
					JOptionPane.showMessageDialog(PanelPrincipal.this,
							"Ingrese correctamente la matricula (Caracteres MAYUSCULAS)");
					textFieldMatricula.setText("");
				}
			}
		});
		btnContinuarPanelCliente.setBounds(78, 142, 117, 29);
		rightTopPanel.add(btnContinuarPanelCliente);

		JButton btnAcceder = new JButton("CONTINUAR");
		btnAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean encontrado = false;
				for (Trabajador trabajador : trabajadores.values()) {

					if (trabajador.getPassword().equals(String.valueOf(passwordFieldCredenciales.getPassword()))) {
						logger.info("Accediendo a la seccion 'Trabajador'.");
						mostrarProgresoAcceso("Accediendo a la plataforma del trabajador ...");
						PanelTrabajador panel = new PanelTrabajador(frame, instance, trabajador);
						frame.getContentPane().add(panel);
						setVisible(false);
						panel.setVisible(true);
						encontrado = true;
						break;
					}
				}
				if (!encontrado) {
					logger.info("Credenciales no coincidentes");
					JOptionPane.showMessageDialog(PanelPrincipal.this, "Credenciales incorrectas");
				}
			}
		});
		btnAcceder.setBounds(75, 141, 131, 29);
		rightBottomPanel.add(btnAcceder);

		textFieldMatricula = new JTextField();
		textFieldMatricula.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMatricula.setText("DDDDCCC");
		textFieldMatricula.setBounds(65, 95, 145, 35);
		rightTopPanel.add(textFieldMatricula);
		textFieldMatricula.setColumns(10);

		passwordFieldCredenciales = new JPasswordField();
		passwordFieldCredenciales.setBounds(65, 94, 154, 35);
		rightBottomPanel.add(passwordFieldCredenciales);

		lblHoraActual = new JLabel();
		lblHoraActual.setForeground(new Color(255, 255, 255));
		lblHoraActual.setBackground(new Color(0, 128, 128));
		lblHoraActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoraActual.setBounds(43, 300, 188, 26);
		leftPanel.add(lblHoraActual);

		JLabel lblTextoIngreso = new JLabel("Ingrese su matricula");
		lblTextoIngreso.setHorizontalAlignment(SwingConstants.CENTER);
		lblTextoIngreso.setBounds(65, 67, 145, 16);
		rightTopPanel.add(lblTextoIngreso);
		rightPanel.add(rightBottomPanel);

		JLabel lblRecordarCredenciales = new JLabel("* Recordar credenciales");
		lblRecordarCredenciales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PanelRecordarCredenciales panel = new PanelRecordarCredenciales(frame, instance, trabajadores);
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		lblRecordarCredenciales.setFont(new Font("Lucida Grande", Font.PLAIN, 7));
		lblRecordarCredenciales.setBounds(178, 190, 80, 16);
		rightBottomPanel.add(lblRecordarCredenciales);

		JLabel lblIngreseSusCredenciales = new JLabel("Ingrese sus credenciales");
		lblIngreseSusCredenciales.setHorizontalAlignment(SwingConstants.CENTER);
		lblIngreseSusCredenciales.setBounds(65, 76, 154, 16);
		rightBottomPanel.add(lblIngreseSusCredenciales);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(59, 133, 145, 133);
		lblLogo.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/LogoParking.png")));
		leftPanel.add(lblLogo);

		JLabel lblNombre = new JLabel("Parking Deusto");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font(".AppleSystemUIFont", Font.ITALIC, 24));
		lblNombre.setBounds(43, 71, 213, 64);
		leftPanel.add(lblNombre);

		JLabel lblCopyright = new JLabel("©");
		lblCopyright.setBounds(6, 402, 11, 16);
		leftPanel.add(lblCopyright);

		JLabel lblEstadoParking = new JLabel("Estado actual: " + obtenerEstado());
		lblEstadoParking.setBackground(new Color(0, 128, 128));
		lblEstadoParking.setForeground(new Color(255, 255, 255));
		lblEstadoParking.setBounds(59, 354, 172, 16);
		leftPanel.add(lblEstadoParking);

		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(240, 354, 31, 16);
		if (obtenerEstado().equals("Completo")) {
			logger.info("Parking completo");
			lblImagen.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/XRojo.png")));
		} else {
			logger.info("Parking con plazas disponibles");
			lblImagen.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/Verde.jpeg")));
		}
		leftPanel.add(lblImagen);

		/** Hilo que me mostrando la hora en tiempo real */
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
						Thread.sleep(500);
						lblHoraActual.setText(formatter.format(LocalDateTime.now()));
					} catch (InterruptedException e) {
						logger.severe(String.format("%s %s", e.getMessage(), e.getCause().getMessage()));
					}
				}
			}
		};
		Thread hilo = new Thread(runnable);
		hilo.start();

		add(leftPanel);
		add(rightPanel);
	}

	private static String obtenerEstado() {
		Map<Integer, Plaza> plazas = ServicioPersistenciaBD.getInstance().plazasSelect();
		String estado = (plazas.size() == 90) ? "Completo" : "Disponible";
		return estado;
	}

	public void mostrarProgresoAcceso(String message) {
		JOptionPane pane = new JOptionPane();
		pane.setMessage(message);
		JProgressBar jProgressBar = new JProgressBar(1, 100);
		jProgressBar.setStringPainted(true);
		jProgressBar.setValue(1);
		pane.add(jProgressBar, 1);
		JDialog dialog = pane.createDialog(pane, "Information message");
		new Thread(() -> {
			for (int i = 0; i <= 100; i++) {
				jProgressBar.setValue(i);
				if (i == 100) {
					dialog.dispose();
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					logger.severe(String.format("%s %s", e1.getMessage(), e1.getCause().getMessage()));
				}
			}
		}).start();
		dialog.setVisible(true);
	}
}
