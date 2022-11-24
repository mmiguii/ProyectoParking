package frontend;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WellComingWorkerPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public WellComingWorkerPanel(final JFrame frame, String nombre, String apellido) {
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Worker Wellcoming Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		
		JLabel labelWellcoming = new JLabel("BIENVENIDO: ", SwingConstants.CENTER);
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);
		
		JTextField text = new JTextField(nombre + " " + apellido);
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
				PersonalDataWorkerPanel panel = new PersonalDataWorkerPanel(frame, nombre, apellido);
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
				StateParkingPanel panel = new StateParkingPanel(frame);
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
				BajaSubscribersPanel panel = new BajaSubscribersPanel(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		leftBottomPanel.add(btnDarBaja);
		
		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInWorker panel = new LogInWorker(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightBottomPanel.add(btnVolver);
		
		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(rightBottomPanel);
		add(bottomPanel);
	}
}
