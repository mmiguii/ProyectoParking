package frontend.paneles.acceso.trabajador.acciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.servicios.ServicioPersistenciaBD;

public class BajaSubscribersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnBaja;
	private static Logger logger = Logger.getLogger(BajaSubscribersPanel.class.getName());

	public BajaSubscribersPanel(JFrame frame, JPanel panel) {
		setBackground(new Color(0, 128, 128));

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Baja Subscriber Panel");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 128, 128));
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWeights = new double[] { 1.0 };
		topPanel.setLayout(gbl_topPanel);
 
		JLabel labelWellcoming = new JLabel("LISTA DE ABONADOS");
		labelWellcoming.setForeground(new Color(255, 255, 255));
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);
		add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setBackground(new Color(0, 128, 128));

		JTable tableSubscritos = new JTable();
		JTableHeader header = tableSubscritos.getTableHeader();
		header.setOpaque(true);
		header.setBackground(new Color(255, 222, 173));

		JScrollPane scrollSubscritos = new JScrollPane(tableSubscritos);

		Vector<String> cabeceras = new Vector<>( 
				Arrays.asList("Matricula", "Tipo Vehiculo", "Cuota", "Fecha de Entrada"));
		DefaultTableModel modeloSubscritos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);

		Map<String, ClienteSubscrito> mapaSubscritos = ServicioPersistenciaBD.getInstance().subscritosSelect();		
		mapaSubscritos.forEach((k,v) -> {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			modeloSubscritos.addRow(new Object[] {v.getMatricula(), v.getTipoVehiculo(), v.getPrecioCuota(), 
					sdf.format(new Date(v.getFechaEntrada()))});
		});
		tableSubscritos.setModel(modeloSubscritos);

		tableSubscritos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
				String matricula = (String) table.getValueAt(row, 0);
				ClienteSubscrito subscrito = ServicioPersistenciaBD.getInstance().subscritoSelect(matricula);
				Date fechaActual = Calendar.getInstance().getTime();
				Date fechaFinal = new Date(subscrito.getFechaSalida());
				if (fechaActual.after(fechaFinal)) {
					c.setBackground(Color.BLUE);
				} else {
					if (isSelected) {
						c.setBackground(new Color(205, 92, 92));
					} else {
						c.setBackground(Color.WHITE);
					}
				} 
				return c;
			}
		});

		tableSubscritos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tableSubscritos.getSelectedRow() >= 0) {
					btnBaja.setEnabled(true);
				} else {
					btnBaja.setEnabled(false);
				}
			}
		});

		GridBagLayout gbl_middlePanel = new GridBagLayout();
		gbl_middlePanel.columnWidths = new int[] { 0, 429, 0 };
		gbl_middlePanel.rowWeights = new double[] { 1.0 };
		gbl_middlePanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		middlePanel.setLayout(gbl_middlePanel);

		GridBagConstraints gbc_scrollSubscritos = new GridBagConstraints();
		gbc_scrollSubscritos.gridx = 1;
		gbc_scrollSubscritos.gridy = 0;
		gbc_scrollSubscritos.fill = GridBagConstraints.BOTH;
		middlePanel.add(scrollSubscritos, gbc_scrollSubscritos);

		add(middlePanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(0, 128, 128));
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_bottomPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0 };
		bottomPanel.setLayout(gbl_bottomPanel);
		add(bottomPanel);
		btnBaja = new JButton("DAR DE BAJA");
		btnBaja.setForeground(new Color(0, 128, 128));
		btnBaja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableSubscritos.getSelectedRow();
				if (selectedRow >= 0) {
					logger.info("Dando de baja a cliente subscrito");
					mostrarProgresoPago("Eliminando abonado de la base de datos...");
					String matricula = (String) tableSubscritos.getValueAt(selectedRow, 0);
					ClienteSubscrito subscrito = ServicioPersistenciaBD.getInstance().subscritoSelect(matricula);
					
					Map<Integer, Plaza> plazasMap = ServicioPersistenciaBD.getInstance()
							.plazasSelect();
					Plaza plaza = plazasMap.values().stream()
							.filter(p -> p.getMatricula().equals(subscrito.getMatricula()))
							.findFirst().orElse(null);

					ServicioPersistenciaBD.getInstance().subscritoDelete(matricula);
					ServicioPersistenciaBD.getInstance().updatePlaza(plaza, "DISPONIBLE", "");
					modeloSubscritos.removeRow(selectedRow);
				}
			}
		});
		btnBaja.setBounds(121, 67, 130, 23);
		btnBaja.setEnabled(false);

		GridBagConstraints gbc_btnBaja = new GridBagConstraints();
		gbc_btnBaja.insets = new Insets(0, 0, 5, 5);
		gbc_btnBaja.gridwidth = 3;
		gbc_btnBaja.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBaja.gridx = 1;
		gbc_btnBaja.gridy = 0;
		bottomPanel.add(btnBaja, gbc_btnBaja);

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setForeground(new Color(0, 128, 128));
		btnVolver.setBounds(340, 67, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Volviendo a panel de bienvenida");
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});

		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.gridwidth = 3;
		gbc_btnVolver.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVolver.insets = new Insets(0, 0, 0, 5);
		gbc_btnVolver.gridx = 1;
		gbc_btnVolver.gridy = 1;
		bottomPanel.add(btnVolver, gbc_btnVolver);

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
					pane.setMessage("Transaccion realizada. ¡Gracias!");
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
