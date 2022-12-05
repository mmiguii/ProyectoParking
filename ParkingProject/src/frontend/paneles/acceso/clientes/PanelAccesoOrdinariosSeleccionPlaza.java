package frontend.paneles.acceso.clientes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	private boolean listaAUsar; // true (plazas1), false (plazas2)

	public PanelAccesoOrdinariosSeleccionPlaza(JFrame frame, JPanel panel, ClienteOrdinario ordinario) {

		setBorder(javax.swing.BorderFactory.createTitledBorder("Panel seleccion abono"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));

		this.frame = frame;
		this.panel = panel;
		this.ordinario = ordinario;

		// Cargamos las plazas de la primera planta
		plazas1 = ServicioPersistenciaBD.plazasSelect(1, ordinario.getTipoVehiculo());

		// Cargamos las plazas de la segunda planta
		plazas2 = ServicioPersistenciaBD.plazasSelect(2, ordinario.getTipoVehiculo());

		// PANEL SUPERIOR
		JPanel topPanel = new JPanel();
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_topPanel.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0 };
		gbl_topPanel.columnWidths = new int[] { 0, 0, 503, 503, 0 };
		gbl_topPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0 };
		topPanel.setLayout(gbl_topPanel);

		JLabel lblTextoSeleccion = new JLabel("Seleccione una plaza");
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
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_bottomPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_bottomPanel.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_bottomPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		bottomPanel.setLayout(gbl_bottomPanel);

		btnCargarPlanta1 = new JButton("PLANTA 1");
		btnCargarPlanta1.addActionListener(this::cargarPrimeraPlanta);
		GridBagConstraints gbc_btnCargarPlanta1 = new GridBagConstraints();
		gbc_btnCargarPlanta1.fill = GridBagConstraints.VERTICAL;
		gbc_btnCargarPlanta1.insets = new Insets(0, 0, 5, 5);
		gbc_btnCargarPlanta1.gridx = 0;
		gbc_btnCargarPlanta1.gridy = 1;
		bottomPanel.add(btnCargarPlanta1, gbc_btnCargarPlanta1);

		btnCargarPlanta2 = new JButton("PLANTA 2");
		btnCargarPlanta2.addActionListener(this::cargarSegundaPlanta);
		GridBagConstraints gbc_btnCargarPlanta2 = new GridBagConstraints();
		gbc_btnCargarPlanta2.fill = GridBagConstraints.VERTICAL;
		gbc_btnCargarPlanta2.insets = new Insets(0, 0, 5, 0);
		gbc_btnCargarPlanta2.gridx = 2;
		gbc_btnCargarPlanta2.gridy = 1;
		bottomPanel.add(btnCargarPlanta2, gbc_btnCargarPlanta2);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this::aceptar);
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.VERTICAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 1;
		bottomPanel.add(btnAceptar, gbc_btnAceptar);

		btnCancelar = new JButton("Cancelar");
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
		plazas.forEach(p -> {
			String estadoPlaza = p.isEstadoPlaza() ? "Ocupado" : "Disponible";
			modelo.addRow(new Object[] { p.getNumeroPlanta(), p.getNumeroPlaza(), p.getTipoPlaza(), estadoPlaza });
		});
	}

	private void cargarPrimeraPlanta(ActionEvent event) {
		listaAUsar = true;
		// Limpia el modelo de tabla
		modelo.setNumRows(0);
		// Carga los datos de la primera lista de plazas en el modelo de tabla
		cargarTabla(plazas1, modelo, tPlazas);
		// Actualiza la JTable para mostrar los datos recién cargados
		tPlazas.updateUI();
	}

	private void cargarSegundaPlanta(ActionEvent event) {
		listaAUsar = false;
		// Limpia el modelo de tabla
		modelo.setNumRows(0);
		// Carga los datos de la segunda lista en el modelo de tabla
		cargarTabla(plazas2, modelo, tPlazas);
		// Actualiza la JTable para mostrar los datos recién cargados
		tPlazas.updateUI();
	}

	private void aceptar(ActionEvent event) {
		int plazaSeleccionada = tPlazas.getSelectedRow();
		// Cargamos "plazas" con la lista de la planta correspondiente
		List<Plaza> plazas = listaAUsar ? plazas1 : plazas2;
		Plaza plaza = plazas.get(plazaSeleccionada);
		ServicioPersistenciaBD.ordinarioInsert(ordinario);
		ServicioPersistenciaBD.update(plaza, "Ocupado", ordinario.getMatricula());
		JOptionPane.showMessageDialog(PanelAccesoOrdinariosSeleccionPlaza.this, "Gracias");
		frame.dispose();
	}

	private void cancelar(ActionEvent event) {
		frame.getContentPane().add(panel);
		panel.setVisible(true);
		setVisible(false);
	}

}