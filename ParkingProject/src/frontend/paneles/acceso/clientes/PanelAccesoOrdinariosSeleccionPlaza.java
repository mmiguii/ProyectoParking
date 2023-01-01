package frontend.paneles.acceso.clientes;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.servicios.ServicioPersistenciaBD;

public class PanelAccesoOrdinariosSeleccionPlaza extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultTableModel modelo;
	private JTable tPlazas;

	private JScrollPane scrollPane;

	private JButton btnCargarPlanta1;
	private JButton btnCargarPlanta2;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JFrame frame;
	private JPanel panel;
	private List<Plaza> plazas1;
	private List<Plaza> plazas2;
	private ClienteOrdinario ordinario;
	private boolean listaAUsar;

	private static Logger logger = Logger.getLogger(PanelAccesoOrdinariosSeleccionPlaza.class.getName());

	public PanelAccesoOrdinariosSeleccionPlaza(JFrame frame, JPanel panel, ClienteOrdinario ordinario) {
		setBackground(new Color(0, 128, 128));

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Panel seleccion plaza");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));

		ServicioPersistenciaBD.getInstance().connect("Parking.db");
		// Cargamos las plazas de la primera planta
		plazas1 = ServicioPersistenciaBD.getInstance().plazasSelect(1, ordinario.getTipoVehiculo());
		// Cargamos las plazas de la segunda planta
		plazas2 = ServicioPersistenciaBD.getInstance().plazasSelect(2, ordinario.getTipoVehiculo());

		this.frame = frame;
		this.panel = panel;
		this.ordinario = ordinario;

		// PANEL SUPERIOR
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 128, 128));
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_topPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0 };
		gbl_topPanel.columnWidths = new int[] { 0, 0, 503, 503, 0 };
		gbl_topPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0 };
		topPanel.setLayout(gbl_topPanel);

		JLabel lblTextoSeleccion = new JLabel("Seleccione una plaza");
		lblTextoSeleccion.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblTextoSeleccion = new GridBagConstraints();
		gbc_lblTextoSeleccion.anchor = GridBagConstraints.WEST;
		gbc_lblTextoSeleccion.gridwidth = 2;
		gbc_lblTextoSeleccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblTextoSeleccion.gridx = 2;
		gbc_lblTextoSeleccion.gridy = 0;
		topPanel.add(lblTextoSeleccion, gbc_lblTextoSeleccion);

		Vector<String> cabeceras = new Vector<String>(
				Arrays.asList("Numero de planta", "Numero de plaza", "Tipo de plaza", "Estado"));
		modelo = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
		tPlazas = new JTable(modelo);
		GridBagConstraints gbc_tPlazas = new GridBagConstraints();
		gbc_tPlazas.gridheight = 3;
		gbc_tPlazas.gridwidth = 2;
		gbc_tPlazas.insets = new Insets(0, 0, 5, 5);
		gbc_tPlazas.fill = GridBagConstraints.BOTH;
		gbc_tPlazas.gridx = 2;
		gbc_tPlazas.gridy = 1;
		topPanel.add(tPlazas, gbc_tPlazas);

		JTableHeader header = tPlazas.getTableHeader();
		header.setOpaque(true);
		header.setBackground(new Color(255, 222, 173));

		scrollPane = new JScrollPane(tPlazas);
		scrollPane.setVisible(true);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		topPanel.add(scrollPane, gbc_scrollPane);

		// PANEL INFERIOR
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(0, 128, 128));
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_bottomPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_bottomPanel.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_bottomPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		bottomPanel.setLayout(gbl_bottomPanel);

		btnCargarPlanta1 = new JButton("PLANTA 1");
		btnCargarPlanta1.setForeground(new Color(0, 128, 128));
		btnCargarPlanta1.addActionListener(this::cargarPrimeraPlanta);
		GridBagConstraints gbc_btnCargarPlanta1 = new GridBagConstraints();
		gbc_btnCargarPlanta1.fill = GridBagConstraints.VERTICAL;
		gbc_btnCargarPlanta1.insets = new Insets(0, 0, 5, 5);
		gbc_btnCargarPlanta1.gridx = 0;
		gbc_btnCargarPlanta1.gridy = 1;
		bottomPanel.add(btnCargarPlanta1, gbc_btnCargarPlanta1);

		btnCargarPlanta2 = new JButton("PLANTA 2");
		btnCargarPlanta2.setForeground(new Color(0, 128, 128));
		btnCargarPlanta2.addActionListener(this::cargarSegundaPlanta);
		GridBagConstraints gbc_btnCargarPlanta2 = new GridBagConstraints();
		gbc_btnCargarPlanta2.fill = GridBagConstraints.VERTICAL;
		gbc_btnCargarPlanta2.insets = new Insets(0, 0, 5, 0);
		gbc_btnCargarPlanta2.gridx = 2;
		gbc_btnCargarPlanta2.gridy = 1;
		bottomPanel.add(btnCargarPlanta2, gbc_btnCargarPlanta2);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(new Color(0, 128, 128));
		btnAceptar.addActionListener(this::aceptar);
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.VERTICAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 1;
		bottomPanel.add(btnAceptar, gbc_btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(new Color(0, 128, 128));
		btnCancelar.addActionListener(this::cancelar);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.gridwidth = 3;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 2;
		bottomPanel.add(btnCancelar, gbc_btnCancelar);

		add(topPanel);
		add(bottomPanel);
	}

	public void cargarTabla(List<Plaza> plazas, DefaultTableModel modelo, JTable tabla) {

		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component elementoActual = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				// Verifica el estado de la plaza en la fila actual y cambia el color de fondo
				// en consecuencia
				if (table.getValueAt(row, 3).toString().equals("OCUPADO")) {
					((JComponent) elementoActual).setOpaque(true);
					elementoActual.setBackground(new Color(205, 92, 92));
				} else {
					((JComponent) elementoActual).setOpaque(true);
					elementoActual.setBackground(new Color(144, 238, 144));
				}
		
				if (isSelected) {
					elementoActual.setBackground(Color.BLUE);
				}
				
				return elementoActual;
			}
		});
		// Agrega una fila por cada plaza en la tabla
		plazas.forEach(plaza -> {
			String estadoPlaza = plaza.isEstadoPlaza() ? "DISPONIBLE" : "OCUPADO";
			modelo.addRow(new Object[] { plaza.getNumeroPlanta(), plaza.getNumeroPlaza(), plaza.getTipoPlaza(),
					estadoPlaza });
		});
	}

	private void cargarPrimeraPlanta(ActionEvent event) {
		listaAUsar = true;
		mostrarProgresoPlanta("Cargando primera planta ...");
		// Limpia el modelo de tabla
		modelo.setNumRows(0);
		// Carga los datos de la primera lista de plazas en el modelo de tabla
		cargarTabla(plazas1, modelo, tPlazas);
		// Actualiza la JTable para mostrar los datos recién cargados
		tPlazas.updateUI();
	}

	private void cargarSegundaPlanta(ActionEvent event) {
		listaAUsar = false;
		mostrarProgresoPlanta("Cargando segunda planta ...");
		// Limpia el modelo de tabla
		modelo.setNumRows(0);
		// Carga los datos de la segunda lista en el modelo de tabla
		cargarTabla(plazas2, modelo, tPlazas);
		// Actualiza la JTable para mostrar los datos recién cargados
		tPlazas.updateUI();
	}

	private void aceptar(ActionEvent event) {
		int plazaSeleccionada = tPlazas.getSelectedRow();
		if (plazaSeleccionada != -1) {
			// Cargamos "plazas" con la lista de la planta correspondiente
			List<Plaza> plazas = listaAUsar ? plazas1 : plazas2;
			Plaza plaza = plazas.get(plazaSeleccionada);
			// Si la plaza se encuentra OCUPADA..
			if (!plaza.isEstadoPlaza()) {
				logger.info("Seleccione una plaza disponible");
				JOptionPane.showMessageDialog(PanelAccesoOrdinariosSeleccionPlaza.this,
						"Seleccione una plaza DISPONIBLE");
			} else {
				ServicioPersistenciaBD.getInstance().ordinarioInsert(ordinario);
				ServicioPersistenciaBD.getInstance().updatePlaza(plaza, "OCUPADO", ordinario.getMatricula());
				JOptionPane.showMessageDialog(PanelAccesoOrdinariosSeleccionPlaza.this, "Gracias");
				logger.info("Cerrando aplicacion...");
				frame.dispose();
				ServicioPersistenciaBD.getInstance().disconnect();
				System.exit(0);
			}
		} else {
			logger.info("Seleccione una plaza y no acepte antes de haberla seleccionado");
			JOptionPane.showMessageDialog(PanelAccesoOrdinariosSeleccionPlaza.this, "Seleccione una plaza");
		}

	}

	private void cancelar(ActionEvent event) {
		frame.getContentPane().add(panel);
		panel.setVisible(true);
		setVisible(false);
	}

	public void mostrarProgresoPlanta(String message) {
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
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					logger.severe(String.format("%s %s", e1.getMessage(), e1.getCause().getMessage()));
				}
			}
		}).start();
		dialog.setVisible(true);
	}
}
