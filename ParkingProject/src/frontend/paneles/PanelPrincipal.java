package frontend.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
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
import frontend.paneles.acceso.PanelAccesoCliente;
import frontend.paneles.acceso.PanelRecordarDNI;
import frontend.paneles.clientes.acciones.PanelPago;
import frontend.paneles.trabajador.empleado.PanelEmpleado;
import frontend.paneles.trabajador.manager.PanelManager;

public class PanelPrincipal extends JPanel {

	private ServicioPersistenciaBD servicio;
	private JPanel instance;
	private ArrayList<Trabajador> trabajadores;
	
	private static final long serialVersionUID = 1L;
	private JLabel lblHoraActual;
	private JTextField textFieldMatricula;
	private JPasswordField passwordFieldCredenciales;

	public PanelPrincipal(JFrame frame) {

		servicio = new ServicioPersistenciaBD();
		
		instance = this;
		
		trabajadores = servicio.trabajadoresSelect();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
		DateFormat f = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Inicio"));
		setBounds(10, 10, 567, 448);
		setLayout(new GridLayout(1,2));
		
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setBackground(new Color(0, 128, 128));
		

		
		
		JPanel rightPanel = new JPanel(new GridLayout(2,1));
		
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
				
				ArrayList<String> matriculas = obtenerMatriculas(servicio.usuarios());
				
				String matricula = textFieldMatricula.getText().toUpperCase();
			
				if (matricula.length() == 7) {
					String digitos = matricula.substring(0, 4);
					if (digitos.matches("[0-9]*")) {
						String caracteres = matricula.substring(4,7);
						if (caracteres.matches("[A-Z]*") && caracteres.matches("[^AEIOU]*")) {
							
							boolean existeMatricula = matriculas.contains(textFieldMatricula.getText());
							
							if (existeMatricula) {
							
								Usuario usuario = servicio.usuario(textFieldMatricula.getText());
								
								if (usuario instanceof ClienteOrdinario) {
									PanelPago panel = new PanelPago(frame, instance, usuario, lblHoraActual.getText());
									frame.getContentPane().add(panel);
									panel.setVisible(true);
									setVisible(false);
								} else { // usuario instanceof ClienteSubscrito
									
									Date actual = (Date) formatter.parse(lblHoraActual.getText());
							
									// Si la fecha actual es mayor o igual a la maxima establecida por el bono. Borramos y mostramos
									// opcion de si desea volver acceder al parking
									if (new Date(actual.getTime()).getTime() >= new Date(usuario.getFechaSalida()).getTime()) {
										servicio.subscritoDelete(matricula);
										int opcion = JOptionPane.showConfirmDialog(PanelPrincipal.this, "Desea volver acceder al parking?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
										// 0 = Si; 1 = No
										if(opcion == 0) {
											PanelAccesoCliente panel = new PanelAccesoCliente(frame, instance, lblHoraActual.getText(), matricula);
											frame.getContentPane().add(panel);
											setVisible(false);
											panel.setVisible(true);
										}
										else {
											// Si no quiere acceder, muestra la pantalla en la que esta
										}
										
									} else {
												// CLASE PLAZA DEL SUBSCRITO
												// Accede a su plaza (hasta que se le agote el bono)
										// Accede a un panel en el que se le muestra su plaza (con una imagen),
										// y se le muestran las caracteristicas de la plaza, ademas de otras cosas.
										
										// Por lo tanto: Accede a la plaza, puede salir, volver a ingresar, asi
										// sucesivamente hasta que se le agote el tiempo.
										
									}
									
								}
								
								
								
							} else {
								// Si la matricula no se encuentra registrada, se accede al panel de registro
								PanelAccesoCliente panel = new PanelAccesoCliente(frame, instance, lblHoraActual.getText(), matricula);
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
				PanelRecordarDNI panel = new PanelRecordarDNI(frame, instance, trabajadores);
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		lblRecordarCredenciales.setFont(new Font("Lucida Grande", Font.PLAIN, 7));
		lblRecordarCredenciales.setBounds(178, 190, 80, 16);
		rightBottomPanel.add(lblRecordarCredenciales);

		
		
		
		
		
		
		
		
		
		add(leftPanel);
		
		
		
		JLabel lblLogo = new JLabel("New label");
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

		
		
		
		ArrayList<Plaza> plazas = servicio.plazasSelect();
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
		
		add(rightPanel);

//		setBorder(javax.swing.BorderFactory.createTitledBorder("Inicio"));
//		setBounds(10, 10, 567, 448);
//		this.setLayout(new GridLayout(2, 1));
//
//		JPanel topPanel = new JPanel(new GridLayout(2, 1));
//
//		JPanel topTopPanel = new JPanel();
//		topTopPanel.setLayout(new GridBagLayout());
//
//		JPanel bottomTopPanel = new JPanel();
//		bottomTopPanel.setLayout(new GridBagLayout());
//
//		JLabel lblTextoBienvenida = new JLabel("Bienvenido al Parking", SwingConstants.CENTER);
//		lblTextoBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 27));
//		topTopPanel.add(lblTextoBienvenida);
//		topPanel.add(topTopPanel);
//
//		JLabel lblTextoMensaje = new JLabel("¿Es usted trabajador o usuario?", SwingConstants.CENTER);
//		lblTextoMensaje.setFont(new Font("Tahoma", Font.PLAIN, 27));
//		bottomTopPanel.add(lblTextoMensaje);
//		topPanel.add(bottomTopPanel);
//
//		add(topPanel);
//
//		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
//
//		JPanel leftBottomPanel = new JPanel();
//		leftBottomPanel.setLayout(new GridBagLayout());
//
//		JPanel rightBottomPanel = new JPanel();
//		rightBottomPanel.setLayout(new GridBagLayout());
//
//		JButton btnTrabajador = new JButton("Trabajador");
//		btnTrabajador.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				PanelAccesoTrabajador panel = new PanelAccesoTrabajador(frame, instance);
//				frame.getContentPane().add(panel);
//				panel.setVisible(true);
//				setVisible(false);
//			}
//		});
//		leftBottomPanel.add(btnTrabajador);
//		bottomPanel.add(leftBottomPanel);
//
//		JButton btnUsuario = new JButton("Usuario");
//		btnUsuario.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				PanelAccesoCliente panel = new PanelAccesoCliente(frame, instance);
//				frame.getContentPane().add(panel);
//				setVisible(false);
//				panel.setVisible(true);
//			}
//		});
//		rightBottomPanel.add(btnUsuario);
//		bottomPanel.add(rightBottomPanel);
//
//		add(bottomPanel);
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
