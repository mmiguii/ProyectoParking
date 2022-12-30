package frontend.paneles.acceso.trabajador;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.personas.personal.Trabajador;
import frontend.paneles.acceso.trabajador.acciones.BajaSubscribersPanel;
import frontend.paneles.acceso.trabajador.acciones.PanelEstadoParking;
import frontend.paneles.acceso.trabajador.acciones.PersonalDataWorkerPanel;

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
		topPanel.setLayout(new GridBagLayout());

		JLabel labelWellcoming = new JLabel("BIENVENIDO: ", SwingConstants.CENTER);
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);

		JTextField text = new JTextField(trabajador.getNombreUsuario());
		text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		text.setEditable(false);
		text.setBorder(null);
		topPanel.add(text);
		add(topPanel);

		JPanel middlePanel = new JPanel(new GridLayout(1, 2));

		JPanel leftMiddlePanel = new JPanel();
		leftMiddlePanel.setLayout(new GridBagLayout());
		JButton btnConsultarDatos = new JButton("DATOS PERSONALES");
		btnConsultarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarProgresoPago("Consultando los datos a la base de datos...");
				PersonalDataWorkerPanel panel = new PersonalDataWorkerPanel(frame, instance, trabajador);
				logger.info("Accediendo a la consulta de los datos personales");
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		leftMiddlePanel.add(btnConsultarDatos);

		JPanel rightMiddlePanel = new JPanel();
		rightMiddlePanel.setLayout(new GridBagLayout());
		JButton btnEstado = new JButton("ESTADO DEL PARKING");
		btnEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelEstadoParking panel = new PanelEstadoParking(frame, instance, trabajador);
				logger.info("Accediendo a los datos del aparcamiento");
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightMiddlePanel.add(btnEstado);

		middlePanel.add(leftMiddlePanel);
		middlePanel.add(rightMiddlePanel);
		add(middlePanel);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());
		JButton btnDarBaja = new JButton("DAR DE BAJA A ABONADOS");
		btnDarBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BajaSubscribersPanel panel = new BajaSubscribersPanel(frame, instance);
				logger.info("Accediendo a la seccion de bajas de clientes con subscripcion");
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		leftBottomPanel.add(btnDarBaja);

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("CERRAR SESION");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Cerrando sesión de empleado");
				mostrarProgresoPago("Cerrando la sesion actual...");
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		rightBottomPanel.add(btnVolver);

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
				if (i == 100 && message.equals("Cerrando la sesion actual...")) {
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
