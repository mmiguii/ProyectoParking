package frontend.paneles.acceso.clientes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.acceso.clientes.pago.PanelPago;

public class PanelAccesoSubscritosSeleccionAbono extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel instance;
	private JTable tPlazas;
	private JScrollPane scrollPane;
	private JFrame frame;
	private JPanel panel;
	private DateFormat formatter;
	private ClienteSubscrito subscrito;
	private List<Plaza> plazas;

	private static Logger logger = Logger.getLogger(PanelAccesoSubscritosSeleccionAbono.class.getName());

	public PanelAccesoSubscritosSeleccionAbono(JFrame frame, JPanel panel, ClienteSubscrito subscrito) {
		setBackground(new Color(0, 128, 128));

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Panel seleccion abono");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));

		ServicioPersistenciaBD.getInstance().connect("Parking.db");

		instance = this;
		formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		plazas = ServicioPersistenciaBD.getInstance().plazasSelect(3, subscrito.getTipoVehiculo());
		cargarTabla(plazas);

		this.frame = frame;
		this.panel = panel;
		this.subscrito = subscrito;

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
		gbl_bottomPanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0 };
		bottomPanel.setLayout(gbl_bottomPanel);

		JLabel lblSemana = new JLabel("ABONO SEMANAL");
		lblSemana.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblSemana = new GridBagConstraints();
		gbc_lblSemana.insets = new Insets(0, 0, 5, 5);
		gbc_lblSemana.gridx = 1;
		gbc_lblSemana.gridy = 0;
		bottomPanel.add(lblSemana, gbc_lblSemana);

		JLabel lblMes = new JLabel("ABONO MENSUAL");
		lblMes.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblMes = new GridBagConstraints();
		gbc_lblMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblMes.gridx = 3;
		gbc_lblMes.gridy = 0;
		bottomPanel.add(lblMes, gbc_lblMes);

		JLabel lblAnyo = new JLabel("ABONO ANUAL");
		lblAnyo.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblAnyo = new GridBagConstraints();
		gbc_lblAnyo.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnyo.gridx = 5;
		gbc_lblAnyo.gridy = 0;
		bottomPanel.add(lblAnyo, gbc_lblAnyo);

		JLabel lblDesdeSem = new JLabel("Desde");
		lblDesdeSem.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblDesdeSem = new GridBagConstraints();
		gbc_lblDesdeSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesdeSem.gridx = 1;
		gbc_lblDesdeSem.gridy = 1;
		bottomPanel.add(lblDesdeSem, gbc_lblDesdeSem);

		JLabel lblDesdeMes = new JLabel("Desde");
		lblDesdeMes.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblDesdeMes = new GridBagConstraints();
		gbc_lblDesdeMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesdeMes.gridx = 3;
		gbc_lblDesdeMes.gridy = 1;
		bottomPanel.add(lblDesdeMes, gbc_lblDesdeMes);

		JLabel lblDesdeAn = new JLabel("Desde");
		lblDesdeAn.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblDesdeAn = new GridBagConstraints();
		gbc_lblDesdeAn.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesdeAn.gridx = 5;
		gbc_lblDesdeAn.gridy = 1;
		bottomPanel.add(lblDesdeAn, gbc_lblDesdeAn);

		JLabel lblDeAdorno = new JLabel("......");
		lblDeAdorno.setForeground(new Color(0, 128, 128));
		GridBagConstraints gbc_lblDeAdorno = new GridBagConstraints();
		gbc_lblDeAdorno.insets = new Insets(0, 0, 5, 0);
		gbc_lblDeAdorno.gridx = 6;
		gbc_lblDeAdorno.gridy = 1;
		bottomPanel.add(lblDeAdorno, gbc_lblDeAdorno);

		JLabel lblEntradaSem = new JLabel(formatter.format(new Date(subscrito.getFechaEntrada()).getTime()));
		lblEntradaSem.setForeground(new Color(255, 255, 255));
		lblEntradaSem.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblEntradaSem = new GridBagConstraints();
		gbc_lblEntradaSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntradaSem.gridx = 1;
		gbc_lblEntradaSem.gridy = 2;
		bottomPanel.add(lblEntradaSem, gbc_lblEntradaSem);

		JLabel lblEntradaMes = new JLabel(formatter.format(new Date(subscrito.getFechaEntrada()).getTime()));
		lblEntradaMes.setForeground(new Color(255, 255, 255));
		lblEntradaMes.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblEntradaMes = new GridBagConstraints();
		gbc_lblEntradaMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntradaMes.gridx = 3;
		gbc_lblEntradaMes.gridy = 2;
		bottomPanel.add(lblEntradaMes, gbc_lblEntradaMes);

		JLabel lblEntradaAn = new JLabel(formatter.format(new Date(subscrito.getFechaEntrada()).getTime()));
		lblEntradaAn.setForeground(new Color(255, 255, 255));
		lblEntradaAn.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblEntradaAn = new GridBagConstraints();
		gbc_lblEntradaAn.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntradaAn.gridx = 5;
		gbc_lblEntradaAn.gridy = 2;
		bottomPanel.add(lblEntradaAn, gbc_lblEntradaAn);

		JLabel lblHastaSem = new JLabel("Hasta");
		lblHastaSem.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblHastaSem = new GridBagConstraints();
		gbc_lblHastaSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblHastaSem.gridx = 1;
		gbc_lblHastaSem.gridy = 3;
		bottomPanel.add(lblHastaSem, gbc_lblHastaSem);

		JLabel lblHastaMes = new JLabel("Hasta");
		lblHastaMes.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblHastaMes = new GridBagConstraints();
		gbc_lblHastaMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblHastaMes.gridx = 3;
		gbc_lblHastaMes.gridy = 3;
		bottomPanel.add(lblHastaMes, gbc_lblHastaMes);

		JLabel lblHastaAn = new JLabel("Hasta");
		lblHastaAn.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblHastaAn = new GridBagConstraints();
		gbc_lblHastaAn.insets = new Insets(0, 0, 5, 5);
		gbc_lblHastaAn.gridx = 5;
		gbc_lblHastaAn.gridy = 3;
		bottomPanel.add(lblHastaAn, gbc_lblHastaAn);

		JLabel lblSalidaSem = new JLabel(formatter.format(new Date(anadirDias(7).getTime().getTime()).getTime()));
		lblSalidaSem.setForeground(new Color(255, 255, 255));
		lblSalidaSem.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSalidaSem = new GridBagConstraints();
		gbc_lblSalidaSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalidaSem.gridx = 1;
		gbc_lblSalidaSem.gridy = 4;
		bottomPanel.add(lblSalidaSem, gbc_lblSalidaSem);

		JLabel lblSalidaMes = new JLabel(formatter.format(new Date(anadirDias(31).getTime().getTime()).getTime()));
		lblSalidaMes.setForeground(new Color(255, 255, 255));
		lblSalidaMes.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSalidaMes = new GridBagConstraints();
		gbc_lblSalidaMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalidaMes.gridx = 3;
		gbc_lblSalidaMes.gridy = 4;
		bottomPanel.add(lblSalidaMes, gbc_lblSalidaMes);

		JLabel lblSalidaAno = new JLabel(formatter.format(new Date(anadirDias(365).getTime().getTime()).getTime()));
		lblSalidaAno.setForeground(new Color(255, 255, 255));
		lblSalidaAno.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSalidaAno = new GridBagConstraints();
		gbc_lblSalidaAno.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalidaAno.gridx = 5;
		gbc_lblSalidaAno.gridy = 4;
		bottomPanel.add(lblSalidaAno, gbc_lblSalidaAno);

		JButton btnSem = new JButton("COMPRAR");
		btnSem.setForeground(new Color(0, 128, 128));
		btnSem.addActionListener(e -> comprarPlaza(7, lblSemana.toString()));
		GridBagConstraints gbc_btnSem = new GridBagConstraints();
		gbc_btnSem.insets = new Insets(0, 0, 5, 5);
		gbc_btnSem.gridx = 1;
		gbc_btnSem.gridy = 5;
		bottomPanel.add(btnSem, gbc_btnSem);

		JButton btnMes = new JButton("COMPRAR");
		btnMes.setForeground(new Color(0, 128, 128));
		btnMes.addActionListener(e -> comprarPlaza(31, lblSalidaMes.toString()));
		GridBagConstraints gbc_btnMes = new GridBagConstraints();
		gbc_btnMes.insets = new Insets(0, 0, 5, 5);
		gbc_btnMes.gridx = 3;
		gbc_btnMes.gridy = 5;
		bottomPanel.add(btnMes, gbc_btnMes);

		JButton btnAn = new JButton("COMPRAR");
		btnAn.setForeground(new Color(0, 128, 128));
		btnAn.addActionListener(e -> comprarPlaza(365, lblSalidaAno.toString()));
		GridBagConstraints gbc_btnAn = new GridBagConstraints();
		gbc_btnAn.insets = new Insets(0, 0, 5, 5);
		gbc_btnAn.gridx = 5;
		gbc_btnAn.gridy = 5;
		bottomPanel.add(btnAn, gbc_btnAn);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setForeground(new Color(0, 128, 128));
		btnCancelar.addActionListener(this::cancelar);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.gridwidth = 5;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 6;
		bottomPanel.add(btnCancelar, gbc_btnCancelar);

		add(topPanel);
		add(bottomPanel);
	}

	public Calendar anadirDias(int numeroDias) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(subscrito.getFechaEntrada()));
		c.add(Calendar.DATE, numeroDias);
		return c;
	}

	public void cargarTabla(List<Plaza> plazas) {
		Vector<String> cabeceras = new Vector<String>(
				Arrays.asList("Numero de planta", "Numero de plaza", "Tipo de plaza", "Estado"));
		DefaultTableModel modelo = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
		tPlazas = new JTable(modelo);

		plazas.forEach(p -> {
			String estado = p.isEstadoPlaza() ? "DISPONIBLE" : "OCUPADO";
			modelo.addRow(new Object[] { p.getNumeroPlanta(), p.getNumeroPlaza(), p.getTipoPlaza(), estado });
		});

		tPlazas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component elementoActual = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
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
	}

	private void cancelar(ActionEvent event) {
		frame.getContentPane().add(panel);
		panel.setVisible(true);
		setVisible(false);
	}

	public void comprarPlaza(int numeroDias, String fechaSalida) {

		try {
			int plazaSeleccionada = tPlazas.getSelectedRow();
			Plaza plaza = plazas.get(plazaSeleccionada);
			if (!plaza.isEstadoPlaza()) {
				JOptionPane.showMessageDialog(PanelAccesoSubscritosSeleccionAbono.this,
						"Seleccione una plaza DISPONIBLE");
			} else {
				subscrito.setPlazaOcupada(plazas.get(plazaSeleccionada));
				Calendar c = Calendar.getInstance();
				c.setTime(new Date(subscrito.getFechaEntrada()));
				c.add(Calendar.DATE, numeroDias);
				subscrito.setFechaSalida(c.getTime().getTime());

				long tiempoTrans = new Date(c.getTime().getTime()).getTime()
						- new Date(subscrito.getFechaEntrada()).getTime();
				long min = TimeUnit.MILLISECONDS.toMinutes(tiempoTrans);
				double importe = (subscrito.getTipoVehiculo().equals("Ordinario") ? 0.50
						: (subscrito.getTipoVehiculo().equals("Electrico") ? 0.40 : 0.30)) * min;
				subscrito.setPrecioCuota(importe);
				subscrito.setImporte(importe);

				ServicioPersistenciaBD.getInstance().subscritoInsert(subscrito);
				ServicioPersistenciaBD.getInstance().updatePlaza(plaza, "OCUPADO", subscrito.getMatricula());
				ServicioPersistenciaBD.getInstance().ingresosPlanta(subscrito.getMatricula(), subscrito.getImporte());

				logger.info("Accediendo a la secccion de pago...");
				PanelPago panel = new PanelPago(frame, instance, subscrito, plaza, fechaSalida);
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}

		} catch (IndexOutOfBoundsException e) {
			logger.severe(String.format("%s %s", e.getMessage(), e.getCause().getMessage()));
			JOptionPane.showMessageDialog(this, "Seleccione una plaza antes de realizar la compra");
		}
	}

}
