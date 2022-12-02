package frontend.panelesAEliminar;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import frontend.paneles.trabajador.empleado.PanelEmpleado;

public class StateParkingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public StateParkingPanel(final JFrame frame) {

		setBorder(javax.swing.BorderFactory.createTitledBorder("Parking State Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel labelWellcoming = new JLabel("DATOS PERSONALES: ");
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);
		add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Prueba");
		JTable table = new JTable();
		table.setModel(modelo);
		middlePanel.add(table);

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
