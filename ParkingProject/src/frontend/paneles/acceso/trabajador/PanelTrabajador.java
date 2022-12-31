package frontend.paneles.acceso.trabajador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import backend.clases.personas.personal.Trabajador;
import frontend.paneles.acceso.trabajador.acciones.BajaSubscribersPanel;
import frontend.paneles.acceso.trabajador.acciones.PanelEstadoParking;
import frontend.paneles.acceso.trabajador.acciones.PanelInformacionTrabajador;

public class PanelTrabajador extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel instance;
	private static Logger logger = Logger.getLogger(PanelTrabajador.class.getName());

	public PanelTrabajador(JFrame frame, JPanel panel, Trabajador trabajador) {

		instance = this;

		setBorder(javax.swing.BorderFactory.createTitledBorder("Worker Wellcoming Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 128, 128));
		topPanel.setForeground(new Color(255, 255, 255));
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWeights = new double[]{1.0};
		topPanel.setLayout(gbl_topPanel);
				
				JLabel lblHoraActual = new JLabel("");
				lblHoraActual.setForeground(new Color(255, 255, 255));
				GridBagConstraints gbc_lblHoraActual = new GridBagConstraints();
				gbc_lblHoraActual.anchor = GridBagConstraints.EAST;
				gbc_lblHoraActual.insets = new Insets(0, 0, 5, 0);
				gbc_lblHoraActual.gridx = 0;
				gbc_lblHoraActual.gridy = 0;
				topPanel.add(lblHoraActual, gbc_lblHoraActual);
				
				
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
				
						JLabel lblBienvenido = new JLabel("BIENVENIDO", SwingConstants.CENTER);
						lblBienvenido.setForeground(new Color(255, 255, 255));
						lblBienvenido.setFont(new Font("Tahoma", Font.PLAIN, 20));
						GridBagConstraints gbc_lblBienvenido = new GridBagConstraints();
						gbc_lblBienvenido.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblBienvenido.insets = new Insets(0, 0, 5, 0);
						gbc_lblBienvenido.gridx = 0;
						gbc_lblBienvenido.gridy = 1;
						topPanel.add(lblBienvenido, gbc_lblBienvenido);
				
				JLabel lblVacio = new JLabel(" ");
				GridBagConstraints gbc_lblVacio = new GridBagConstraints();
				gbc_lblVacio.insets = new Insets(0, 0, 5, 0);
				gbc_lblVacio.gridx = 0;
				gbc_lblVacio.gridy = 2;
				topPanel.add(lblVacio, gbc_lblVacio);
		add(topPanel);
		
				JLabel nombreUsuario = new JLabel(trabajador.getNombreUsuario());
				nombreUsuario.setForeground(new Color(255, 255, 255));
				nombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 20));
				nombreUsuario.setBorder(null);
				GridBagConstraints gbc_nombreUsuario = new GridBagConstraints();
				gbc_nombreUsuario.gridx = 0;
				gbc_nombreUsuario.gridy = 3;
				topPanel.add(nombreUsuario, gbc_nombreUsuario);

		JPanel middlePanel = new JPanel(new GridLayout(1, 2));

		JPanel leftMiddlePanel = new JPanel();
		leftMiddlePanel.setBackground(new Color(0, 128, 128));
		leftMiddlePanel.setForeground(new Color(255, 255, 255));
		leftMiddlePanel.setLayout(new GridBagLayout());
		JButton btnConsultarDatos = new JButton("DATOS PERSONALES");
		btnConsultarDatos.setForeground(new Color(0, 128, 128));
		btnConsultarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarProgresoPago("Consultando los datos a la base de datos...");
				PanelInformacionTrabajador panel = new PanelInformacionTrabajador(frame, instance, trabajador);
				logger.info("Accediendo a la consulta de los datos personales");
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnConsultarDatos = new GridBagConstraints();
		gbc_btnConsultarDatos.fill = GridBagConstraints.BOTH;
		gbc_btnConsultarDatos.gridheight = 3;
		gbc_btnConsultarDatos.gridx = 0;
		gbc_btnConsultarDatos.gridy = 0;
		leftMiddlePanel.add(btnConsultarDatos, gbc_btnConsultarDatos);

		JPanel rightMiddlePanel = new JPanel();
		rightMiddlePanel.setBackground(new Color(0, 128, 128));
		rightMiddlePanel.setForeground(new Color(255, 255, 255));
		rightMiddlePanel.setLayout(new GridBagLayout());
		JButton btnEstado = new JButton("ESTADO DEL PARKING");
		btnEstado.setBackground(new Color(0, 128, 128));
		btnEstado.setForeground(new Color(0, 128, 128));
		btnEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelEstadoParking panel = new PanelEstadoParking(frame, instance, trabajador);
				logger.info("Accediendo a los datos del aparcamiento");
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnEstado = new GridBagConstraints();
		gbc_btnEstado.fill = GridBagConstraints.VERTICAL;
		gbc_btnEstado.gridheight = 3;
		gbc_btnEstado.gridx = 0;
		gbc_btnEstado.gridy = 0;
		rightMiddlePanel.add(btnEstado, gbc_btnEstado);

		middlePanel.add(leftMiddlePanel);
		middlePanel.add(rightMiddlePanel);
		add(middlePanel);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setForeground(new Color(255, 255, 255));
		leftBottomPanel.setBackground(new Color(0, 128, 128));
		leftBottomPanel.setLayout(new GridBagLayout());
		JButton btnAnularAbonados = new JButton("ANULAR ABONADOS");
		btnAnularAbonados.setBackground(new Color(255, 255, 255));
		btnAnularAbonados.setForeground(new Color(0, 128, 128));
		btnAnularAbonados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarProgresoPago("Cargando listado de abonados...");
				BajaSubscribersPanel panel = new BajaSubscribersPanel(frame, instance);
				logger.info("Accediendo a la seccion de bajas de clientes con subscripcion");
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnAnularAbonados = new GridBagConstraints();
		gbc_btnAnularAbonados.fill = GridBagConstraints.VERTICAL;
		gbc_btnAnularAbonados.gridheight = 3;
		gbc_btnAnularAbonados.gridx = 0;
		gbc_btnAnularAbonados.gridy = 0;
		leftBottomPanel.add(btnAnularAbonados, gbc_btnAnularAbonados);

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setForeground(new Color(255, 255, 255));
		rightBottomPanel.setBackground(new Color(0, 128, 128));
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnCerrarSesion = new JButton("ABANDONAR SESION ");
		btnCerrarSesion.setForeground(new Color(0, 128, 128));
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Cerrando sesión de empleado");
				mostrarProgresoPago("Cerrando la sesion actual...");
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCerrarSesion = new GridBagConstraints();
		gbc_btnCerrarSesion.fill = GridBagConstraints.VERTICAL;
		gbc_btnCerrarSesion.gridheight = 3;
		gbc_btnCerrarSesion.gridx = 0;
		gbc_btnCerrarSesion.gridy = 0;
		rightBottomPanel.add(btnCerrarSesion, gbc_btnCerrarSesion);

		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(rightBottomPanel);
		add(bottomPanel);
	}
	
	
	public void mostrarProgresoPago(String message) {
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
//					pane.setMessage("Transaccion realizada. ¡Gracias!");
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					logger.severe(String.format("%s %s", e1.getMessage(), e1.getCause().getMessage()));
				}
			}
		}).start();
		dialog.setVisible(true);
		dialog.dispose();
	}
}
