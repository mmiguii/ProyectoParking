package frontend.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.Usuario;
import backend.clases.personas.personal.Manager;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.acceso.PanelAccesoParking;
import frontend.paneles.acceso.PanelRecordarCredenciales;
import frontend.paneles.acceso.clientes.pago.PanelPago;
import frontend.paneles.acceso.trabajdor.PanelEmpleado;
import frontend.paneles.acceso.trabajdor.PanelManager;

public class PanelPrincipal extends JPanel {

	private JPanel instance;
	private ArrayList<Trabajador> trabajadores;

	private static final long serialVersionUID = 1L;
	private JLabel lblHoraActual;
	private JTextField textFieldMatricula;
	private JPasswordField passwordFieldCredenciales;
	private JLabel lblXRojo;

	public PanelPrincipal(JFrame frame) {

		new ServicioPersistenciaBD();

		instance = this;

		trabajadores = ServicioPersistenciaBD.trabajadoresSelect();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
		DateFormat f = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

		setBorder(javax.swing.BorderFactory.createTitledBorder("Inicio"));
		setBounds(10, 10, 567, 448);
		setLayout(new GridLayout(1, 2));

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

		JButton btnContinuarPanelCliente = new JButton("Continuar");
		btnContinuarPanelCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> matriculas = obtenerMatriculas(ServicioPersistenciaBD.usuarios());

				String matricula = textFieldMatricula.getText().toUpperCase();

				if (matricula.length() == 7) {
					String digitos = matricula.substring(0, 4);
					if (digitos.matches("[0-9]*")) {
						String caracteres = matricula.substring(4, 7);
						if (caracteres.matches("[A-Z]*") && caracteres.matches("[^AEIOU]*")) {

							boolean existeMatricula = matriculas.contains(textFieldMatricula.getText());

							if (existeMatricula) {

								Usuario usuario = ServicioPersistenciaBD.usuario(textFieldMatricula.getText());

								if (usuario instanceof ClienteOrdinario) {
									PanelPago panel = new PanelPago(frame, instance, usuario, lblHoraActual.getText());
									frame.getContentPane().add(panel);
									panel.setVisible(true);
									setVisible(false);
								} else { // usuario instanceof ClienteSubscrito

									try {

										Date fechaActualDate = f.parse(lblHoraActual.getText());
										Date fechaSalidaDate = new Date(usuario.getFechaSalida());

										// Si la fecha actual es BEFORE (antes) a la maxima establecida por el bono.
										// Accedemos al parking/plaza del usuario en cuestion
										if (fechaActualDate.before(fechaSalidaDate)) {
											// CLASE PLAZA DEL SUBSCRITO
											// Accede a su plaza (hasta que se le agote el bono)
											// Accede a un panel en el que se le muestra su plaza (con una imagen),
											// y se le muestran las caracteristicas de la plaza, ademas de otras cosas.

											// Por lo tanto: Accede a la plaza, puede salir, volver a ingresar, asi
											// sucesivamente hasta que se le agote el tiempo.

										} else {
											// Si la fecha actual es mayor o igual a la maxima establecida por el bono.
											// Borramos y mostramos
											// opcion de si desea volver acceder al parking
											ServicioPersistenciaBD.subscritoDelete(matricula);
											int opcion = JOptionPane.showConfirmDialog(PanelPrincipal.this,
													"Desea volver acceder al parking?", "Confirmación",
													JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
											// 0 = Si; 1 = No
											if (opcion == 0) {
												PanelAccesoParking panel = new PanelAccesoParking(frame, instance,
														lblHoraActual.getText(), matricula);
												frame.getContentPane().add(panel);
												setVisible(false);
												panel.setVisible(true);
											} else {
												// Si no quiere acceder, sale de la aplicacion
												frame.dispose();
											}
										}
									} catch (ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
//									

								}

							} else {
								// Si la matricula no se encuentra registrada, se accede al panel de registro
								PanelAccesoParking panel = new PanelAccesoParking(frame, instance,
										lblHoraActual.getText(), matricula);
								// PanelAccesoCliente panel = new PanelAccesoCliente(frame,
								// instance,lblHoraActual.getText(), matricula);
								frame.getContentPane().add(panel);
								setVisible(false);
								panel.setVisible(true);
							}

						} else {
							// System.out.println("caracteres");
							JOptionPane.showMessageDialog(PanelPrincipal.this, "Ingrese correctamente la matricula");
							textFieldMatricula.setText("");
						}
					} else {
						// System.out.println("digitos");
						JOptionPane.showMessageDialog(PanelPrincipal.this, "Ingrese correctamente la matricula");
						textFieldMatricula.setText("");
					}
				} else {
					// System.out.println("tamanyo");
					JOptionPane.showMessageDialog(PanelPrincipal.this, "Ingrese correctamente la matricula");
					textFieldMatricula.setText("");
				}

			}
		});
		btnContinuarPanelCliente.setBounds(78, 142, 117, 29);
		rightTopPanel.add(btnContinuarPanelCliente);

		textFieldMatricula = new JTextField();
		textFieldMatricula.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMatricula.setText("Ingrese su matricula");
		textFieldMatricula.setBounds(65, 95, 145, 35);
		rightTopPanel.add(textFieldMatricula);
		textFieldMatricula.setColumns(10);
		rightPanel.add(rightBottomPanel);

		passwordFieldCredenciales = new JPasswordField();
		passwordFieldCredenciales.setBounds(65, 94, 146, 35);
		rightBottomPanel.add(passwordFieldCredenciales);

		JButton btnAcceder = new JButton("Continuar");
		btnAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// CREDENCIALES SON EL DNI POR AHORA
				// List<Trabajador> trabajadores = servicio.trabajadoresSelect();
				// System.out.println(trabajadores.get(0));
				for (Trabajador t : trabajadores) {
					// System.out.println(t.getDni());
					if (t.getDni().equals(String.valueOf(passwordFieldCredenciales.getPassword()))) {
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
		btnAcceder.setBounds(75, 141, 123, 29);
		rightBottomPanel.add(btnAcceder);

		JLabel lblRecordarCredenciales = new JLabel("* Recordar credenciales");
		lblRecordarCredenciales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// CAMBIARLO A PANEL RECORDAR CREDENCIALES
				PanelRecordarCredenciales panel = new PanelRecordarCredenciales(frame, instance, trabajadores);
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		lblRecordarCredenciales.setFont(new Font("Lucida Grande", Font.PLAIN, 7));
		lblRecordarCredenciales.setBounds(178, 190, 80, 16);
		rightBottomPanel.add(lblRecordarCredenciales);

		add(leftPanel);

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

		// lblHoraActual = new JLabel(formatter.format(ZonedDateTime.now()));
		lblHoraActual = new JLabel();
		lblHoraActual.setForeground(new Color(255, 255, 255));
		lblHoraActual.setBackground(new Color(0, 128, 128));
		lblHoraActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoraActual.setBounds(43, 300, 188, 26);
		leftPanel.add(lblHoraActual);

		/** Hilo que me mostrando la hora en tiempo real */
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
						lblHoraActual.setText(formatter.format(LocalDateTime.now()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread hilo = new Thread(runnable);
		hilo.start();

		ArrayList<Plaza> plazas = ServicioPersistenciaBD.plazasSelect();
		String estado;
		if (plazas.size() == 90) {
			estado = "Completo";
		} else {
			estado = "Disponible";

		}

		JLabel lblEstadoParking = new JLabel("Estado actual: " + estado);
		lblEstadoParking.setBackground(new Color(0, 128, 128));
		lblEstadoParking.setForeground(new Color(255, 255, 255));
		lblEstadoParking.setBounds(59, 354, 172, 16);
		leftPanel.add(lblEstadoParking);

		lblXRojo = new JLabel("");
		lblXRojo.setVisible(false);
		lblXRojo.setBounds(240, 354, 31, 16);
		lblXRojo.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/XRojo.png")));
		if (estado.equals("Completo")) {
			lblXRojo.setVisible(true);
		} else {
			lblXRojo.setVisible(false);
		}
		leftPanel.add(lblXRojo);

		add(rightPanel);

	}

	private static ArrayList<String> obtenerMatriculas(ArrayList<Usuario> servUsuarios) {
		List<String> matriculas = new ArrayList<String>();
		ArrayList<Usuario> usuarios = servUsuarios;

		for (Usuario u : usuarios) {
			if (!matriculas.contains(u.getMatricula())) {
				matriculas.add(u.getMatricula());
			}
		}
		return (ArrayList<String>) matriculas;
	}
}
