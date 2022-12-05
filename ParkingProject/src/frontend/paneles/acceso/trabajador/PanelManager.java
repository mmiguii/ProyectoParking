package frontend.paneles.acceso.trabajador;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.personas.personal.Trabajador;
import frontend.paneles.acceso.trabajador.acciones.BajaSubscribersPanel;
import frontend.paneles.acceso.trabajador.acciones.PanelEstadoParking;
import frontend.paneles.acceso.trabajador.acciones.PersonalDataWorkerPanel;

public class PanelManager extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel instance;

	public PanelManager(JFrame frame, JPanel panel, Trabajador trabajador) {

		instance = this;

		setBorder(javax.swing.BorderFactory.createTitledBorder("Manager Wellcoming Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel labelWellcoming = new JLabel("BIENVENIDO: ", SwingConstants.CENTER);
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);

		JTextField text = new JTextField(trabajador.getNombreUsuario() + " " + trabajador.getPassword());
		text.setFont(new Font("Tahoma", Font.PLAIN, 20));
		text.setEditable(false);
		text.setBorder(null);
		topPanel.add(text);
		add(topPanel);

		JPanel middlePanel = new JPanel(new GridLayout(1, 2));

		JPanel leftMiddlePanel = new JPanel();
		leftMiddlePanel.setLayout(new GridBagLayout());
		JButton btnConsultarDatos = new JButton("CONSULTAR DATOS PERSONALES");
		btnConsultarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalDataWorkerPanel panel = new PersonalDataWorkerPanel(frame, instance, trabajador);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		leftMiddlePanel.add(btnConsultarDatos);

		JPanel rightMiddlePanel = new JPanel();
		rightMiddlePanel.setLayout(new GridBagLayout());
		JButton btnFichero = new JButton("CONSULTAR FICHEROS");
		btnFichero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelEstadoParking panel = new PanelEstadoParking(frame, instance);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightMiddlePanel.add(btnFichero);

		middlePanel.add(leftMiddlePanel);
		middlePanel.add(rightMiddlePanel);
		add(middlePanel);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());
		JButton btnDatosParking = new JButton("CONSULTAR DATOS DEL PARKING");
		btnDatosParking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BajaSubscribersPanel panel = new BajaSubscribersPanel(frame, instance);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		leftBottomPanel.add(btnDatosParking);

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

}
