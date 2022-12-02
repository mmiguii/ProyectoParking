package frontend.paneles.clientes.acciones;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import frontend.panelesAEliminar.PanelAccesoCliente;

public class PanelParkingFull extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel instance;

	public PanelParkingFull(JFrame frame, String matricula) {

		instance = this;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

		setBorder(javax.swing.BorderFactory.createTitledBorder("Full Panel"));
		setBounds(10, 10, 567, 448);

		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		JLabel label1 = new JLabel("LO SENTIMOS");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JTextField textMatricula = new JTextField(matricula);
		textMatricula.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(label1);
		topPanel.add(textMatricula);
		add(topPanel);

		JPanel middlePanel = new JPanel();
		JLabel label2 = new JLabel("NO HAY PLAZAS DISPONIBLES");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JList<String> listaPlazas = new JList<>();
		middlePanel.add(label2);
		middlePanel.add(listaPlazas);
		add(middlePanel);

		JPanel bottomPanel = new JPanel();
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelAccesoCliente panel = new PanelAccesoCliente(frame, instance,formatter.format(LocalDate.now()),matricula);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		bottomPanel.add(btnVolver);
		add(bottomPanel);

	}

}
