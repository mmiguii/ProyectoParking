package frontend.paneles.acceso.clientes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
//	private ServicioPersistenciaBD servicio;

	private JTable tPlazas1;
	private JTable tPlazas2;

	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;

	private JButton btnCargarPlanta1;
	private JButton btnAceptar;
	private JButton btnCargarPlanta2;
	private DefaultTableModel modelo1;
	private DefaultTableModel modelo2;

	public PanelAccesoOrdinariosSeleccionPlaza(JFrame frame, JPanel panel, ClienteOrdinario ordinario) {

//		servicio = new ServicioPersistenciaBD();
//		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

		int p1 = 1;
		List<Plaza> plazas1 = ServicioPersistenciaBD.plazasSelect(p1, ordinario.getTipoVehiculo());

		int p2 = 2;
		List<Plaza> plazas2 = ServicioPersistenciaBD.plazasSelect(p2, ordinario.getTipoVehiculo());

		setBorder(javax.swing.BorderFactory.createTitledBorder("Panel seleccion abono"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));

		// Panel superior
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

		Vector<String> cabeceras1 = new Vector<String>(
				Arrays.asList("Numero de planta", "Numero de plaza", "Tipo de plaza", "Estado"));
		modelo1 = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras1);
		tPlazas1 = new JTable(modelo1);

		Vector<String> cabeceras2 = new Vector<String>(
				Arrays.asList("Numero de planta", "Numero de plaza", "Tipo de plaza", "Estado"));
		modelo2 = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras2);
		tPlazas2 = new JTable(modelo2);

		cargarTabla(plazas1, modelo1, tPlazas1);
		cargarTabla(plazas2, modelo2, tPlazas2);
//		cargarTabla1(plazas1);
//		cargarTabla2(plazas2);

		tPlazas1.setVisible(true);

		tPlazas2.setVisible(false);

		GridBagConstraints gbc_tPlazas1 = new GridBagConstraints();
		gbc_tPlazas1.gridheight = 3;
		gbc_tPlazas1.gridwidth = 2;
		gbc_tPlazas1.insets = new Insets(0, 0, 5, 5);
		gbc_tPlazas1.fill = GridBagConstraints.BOTH;
		gbc_tPlazas1.gridx = 2;
		gbc_tPlazas1.gridy = 1;
		topPanel.add(tPlazas1, gbc_tPlazas1);

		scrollPane1 = new JScrollPane(tPlazas1);
		scrollPane1.setVisible(true);

		GridBagConstraints gbc_scrollPane1 = new GridBagConstraints();
		gbc_scrollPane1.gridheight = 3;
		gbc_scrollPane1.gridwidth = 2;
		gbc_scrollPane1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane1.gridx = 2;
		gbc_scrollPane1.gridy = 1;
		topPanel.add(scrollPane1, gbc_scrollPane1);

		GridBagConstraints gbc_tPlazas2 = new GridBagConstraints();
		gbc_tPlazas2.gridheight = 3;
		gbc_tPlazas2.gridwidth = 2;
		gbc_tPlazas2.insets = new Insets(0, 0, 5, 5);
		gbc_tPlazas2.fill = GridBagConstraints.BOTH;
		gbc_tPlazas2.gridx = 2;
		gbc_tPlazas2.gridy = 1;
		topPanel.add(tPlazas2, gbc_tPlazas2);

		scrollPane2 = new JScrollPane(tPlazas2);
		scrollPane2.setVisible(false);

		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.gridheight = 3;
		gbc_scrollPane2.gridwidth = 2;
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane2.gridx = 2;
		gbc_scrollPane2.gridy = 1;
		topPanel.add(scrollPane2, gbc_scrollPane2);

		// Panel inferior
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_bottomPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_bottomPanel.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_bottomPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		JPanel bottomPanel = new JPanel(gbl_bottomPanel);

		btnCargarPlanta1 = new JButton("Primera Planta");
		btnCargarPlanta1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tPlazas1.setVisible(true);
				scrollPane1.setVisible(true);
				tPlazas2.setVisible(false);
				scrollPane2.setVisible(false);

			}
		});
		GridBagConstraints gbc_btnCargarPlanta1 = new GridBagConstraints();
		gbc_btnCargarPlanta1.fill = GridBagConstraints.VERTICAL;
		gbc_btnCargarPlanta1.insets = new Insets(0, 0, 5, 5);
		gbc_btnCargarPlanta1.gridx = 0;
		gbc_btnCargarPlanta1.gridy = 1;
		bottomPanel.add(btnCargarPlanta1, gbc_btnCargarPlanta1);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int plazaSeleccionada = tPlazas1.isVisible() ? tPlazas1.getSelectedRow() : tPlazas2.getSelectedRow();
				Plaza p = tPlazas1.isVisible() ? plazas1.get(plazaSeleccionada) : plazas2.get(plazaSeleccionada);
				
				String estado = "Ocupado";
				
				ServicioPersistenciaBD.ordinarioInsert(ordinario);
				ServicioPersistenciaBD.update(p, estado, ordinario.getMatricula());
				
				JOptionPane.showMessageDialog(PanelAccesoOrdinariosSeleccionPlaza.this, "Gracias");
				
				frame.dispose();
				
				
				
//				if (tPlazas1.isVisible()) {
//
//					int plazaSeleccionada = tPlazas1.getSelectedRow();
//					Plaza p = plazas1.get(plazaSeleccionada);
//					String estado = "Ocupado";
//					ServicioPersistenciaBD.ordinarioInsert(ordinario);
//					ServicioPersistenciaBD.update(p, estado, ordinario.getMatricula());
//
//				} else {
//					int plazaSeleccionada = tPlazas2.getSelectedRow();
//					Plaza p = plazas2.get(plazaSeleccionada);
//					String estado = "Ocupado";
//					ServicioPersistenciaBD.ordinarioInsert(ordinario);
//					ServicioPersistenciaBD.update(p, estado, ordinario.getMatricula());
//
//				}
//
//				JOptionPane.showMessageDialog(PanelAccesoOrdinariosSeleccionPlaza.this, "Gracias");
//				frame.dispose();

			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.VERTICAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 1;
		bottomPanel.add(btnAceptar, gbc_btnAceptar);

		btnCargarPlanta2 = new JButton("Segunda Planta");
		btnCargarPlanta2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tPlazas1.setVisible(false);
				scrollPane1.setVisible(false);
				tPlazas2.setVisible(true);
				scrollPane2.setVisible(true);

			}
		});
		GridBagConstraints gbc_btnCargarPlanta2 = new GridBagConstraints();
		gbc_btnCargarPlanta2.fill = GridBagConstraints.VERTICAL;
		gbc_btnCargarPlanta2.insets = new Insets(0, 0, 5, 0);
		gbc_btnCargarPlanta2.gridx = 2;
		gbc_btnCargarPlanta2.gridy = 1;
		bottomPanel.add(btnCargarPlanta2, gbc_btnCargarPlanta2);

		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
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
		  for (Plaza p : plazas) {
		    String estado = p.isEstadoPlaza() ? "Ocupado" : "Disponible";
		    modelo.addRow(new Object[] { p.getNumeroPlanta(), p.getNumeroPlaza(), p.getTipoPlaza(), estado });
		  }
		  tabla.setModel(modelo);
		}

	
	
//	public void cargarTabla1(List<Plaza> plazas) {
//		for (Plaza p : plazas) {
//			String estado;
//			if (!p.isEstadoPlaza()) {
//				estado = "Disponible";
//			} else {
//				estado = "Ocupado";
//			}
//			modelo1.addRow(new Object[] { p.getNumeroPlanta(), p.getNumeroPlaza(), p.getTipoPlaza(), estado });
//		}
//		tPlazas1.setModel(modelo1);
//	}
//
//	public void cargarTabla2(List<Plaza> plazas) {
//		for (Plaza p : plazas) {
//			String estado;
//			if (!p.isEstadoPlaza()) {
//				estado = "Disponible";
//			} else {
//				estado = "Ocupado";
//			}
//			modelo2.addRow(new Object[] { p.getNumeroPlanta(), p.getNumeroPlaza(), p.getTipoPlaza(), estado });
//		}
//		tPlazas2.setModel(modelo2);
//	}

}
