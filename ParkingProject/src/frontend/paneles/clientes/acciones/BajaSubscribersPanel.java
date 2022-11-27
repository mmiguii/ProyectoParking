package frontend.paneles.clientes.acciones;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import backend.clases.clientes.ClienteSubscrito;
import frontend.paneles.trabajador.empleado.PanelEmpleado;

public class BajaSubscribersPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public BajaSubscribersPanel(final JFrame frame) {

		setBorder(javax.swing.BorderFactory.createTitledBorder("Baja Subscriber Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel labelWellcoming = new JLabel("LISTA DE ABONADOS: ");
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);
		add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());
		JList<ClienteSubscrito> miLista = new JList();
		DefaultListModel modeloLista = new DefaultListModel<>();
		// ClienteSubscrito sc = new ClienteSubscrito(2000, 1);
		// modeloLista.addElement(sc);
		miLista.setModel(modeloLista);
		middlePanel.add(miLista);

		JButton btnBaja = new JButton("DAR DE BAJA");
		middlePanel.add(btnBaja);
		add(middlePanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelEmpleado panel = new PanelEmpleado(frame, null, null);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		bottomPanel.add(btnVolver);
		add(bottomPanel);

	}
}