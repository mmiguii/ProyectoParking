package frontend.paneles.acceso.clientes;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.acceso.clientes.pago.PanelPago;

public class PanelAccesoSubscritosSeleccionAbono extends JPanel {

	private static final long serialVersionUID = 1L;
//	private ServicioPersistenciaBD servicio;
	private JPanel instance;
	private JTable tPlazas;
	private JScrollPane scrollPane;

	public PanelAccesoSubscritosSeleccionAbono(JFrame frame, JPanel panel, ClienteSubscrito subscrito) {

		instance = this;
//		servicio = new ServicioPersistenciaBD();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

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

		int numeroPlanta = 3;
		List<Plaza> plazas = ServicioPersistenciaBD.plazasSelect(numeroPlanta, subscrito.getTipoVehiculo());
		cargarTabla(plazas);

		GridBagConstraints gbc_tPlazas = new GridBagConstraints();
		gbc_tPlazas.gridheight = 3;
		gbc_tPlazas.gridwidth = 2;
		gbc_tPlazas.insets = new Insets(0, 0, 5, 5);
		gbc_tPlazas.fill = GridBagConstraints.BOTH;
		gbc_tPlazas.gridx = 2;
		gbc_tPlazas.gridy = 1;
		topPanel.add(tPlazas, gbc_tPlazas);

		scrollPane = new JScrollPane(tPlazas);

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		topPanel.add(scrollPane, gbc_scrollPane);

		// Panel inferior
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0 };
		JPanel bottomPanel = new JPanel(gbl_bottomPanel);

		JLabel lblSemana = new JLabel("ABONO SEMANAL");
		GridBagConstraints gbc_lblSemana = new GridBagConstraints();
		gbc_lblSemana.insets = new Insets(0, 0, 5, 5);
		gbc_lblSemana.gridx = 1;
		gbc_lblSemana.gridy = 0;
		bottomPanel.add(lblSemana, gbc_lblSemana);

		JLabel lblMes = new JLabel("ABONO MENSUAL");
		GridBagConstraints gbc_lblMes = new GridBagConstraints();
		gbc_lblMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblMes.gridx = 3;
		gbc_lblMes.gridy = 0;
		bottomPanel.add(lblMes, gbc_lblMes);

		JLabel lblAnyo = new JLabel("ABONO ANUAL");
		GridBagConstraints gbc_lblAnyo = new GridBagConstraints();
		gbc_lblAnyo.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnyo.gridx = 5;
		gbc_lblAnyo.gridy = 0;
		bottomPanel.add(lblAnyo, gbc_lblAnyo);

		JLabel lblDesdeSem = new JLabel("Desde");
		GridBagConstraints gbc_lblDesdeSem = new GridBagConstraints();
		gbc_lblDesdeSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesdeSem.gridx = 1;
		gbc_lblDesdeSem.gridy = 1;
		bottomPanel.add(lblDesdeSem, gbc_lblDesdeSem);

		JLabel lblDesdeMes = new JLabel("Desde");
		GridBagConstraints gbc_lblDesdeMes = new GridBagConstraints();
		gbc_lblDesdeMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesdeMes.gridx = 3;
		gbc_lblDesdeMes.gridy = 1;
		bottomPanel.add(lblDesdeMes, gbc_lblDesdeMes);

		JLabel lblDesdeAn = new JLabel("Desde");
		GridBagConstraints gbc_lblDesdeAn = new GridBagConstraints();
		gbc_lblDesdeAn.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesdeAn.gridx = 5;
		gbc_lblDesdeAn.gridy = 1;
		bottomPanel.add(lblDesdeAn, gbc_lblDesdeAn);

		JLabel lblDeAdorno = new JLabel("......");
		lblDeAdorno.setForeground(SystemColor.window);
		GridBagConstraints gbc_lblDeAdorno = new GridBagConstraints();
		gbc_lblDeAdorno.insets = new Insets(0, 0, 5, 0);
		gbc_lblDeAdorno.gridx = 6;
		gbc_lblDeAdorno.gridy = 1;
		bottomPanel.add(lblDeAdorno, gbc_lblDeAdorno);

		JLabel lblEntradaSem = new JLabel(formatter.format(new Date(subscrito.getFechaEntrada()).getTime()));
		lblEntradaSem.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblEntradaSem = new GridBagConstraints();
		gbc_lblEntradaSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntradaSem.gridx = 1;
		gbc_lblEntradaSem.gridy = 2;
		bottomPanel.add(lblEntradaSem, gbc_lblEntradaSem);

		JLabel lblEntradaMes = new JLabel(formatter.format(new Date(subscrito.getFechaEntrada()).getTime()));
		lblEntradaMes.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblEntradaMes = new GridBagConstraints();
		gbc_lblEntradaMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntradaMes.gridx = 3;
		gbc_lblEntradaMes.gridy = 2;
		bottomPanel.add(lblEntradaMes, gbc_lblEntradaMes);

		JLabel lblEntradaAn = new JLabel(formatter.format(new Date(subscrito.getFechaEntrada()).getTime()));
		lblEntradaAn.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblEntradaAn = new GridBagConstraints();
		gbc_lblEntradaAn.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntradaAn.gridx = 5;
		gbc_lblEntradaAn.gridy = 2;
		bottomPanel.add(lblEntradaAn, gbc_lblEntradaAn);

		JLabel lblHastaSem = new JLabel("Hasta");
		GridBagConstraints gbc_lblHastaSem = new GridBagConstraints();
		gbc_lblHastaSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblHastaSem.gridx = 1;
		gbc_lblHastaSem.gridy = 3;
		bottomPanel.add(lblHastaSem, gbc_lblHastaSem);

		JLabel lblHastaMes = new JLabel("Hasta");
		GridBagConstraints gbc_lblHastaMes = new GridBagConstraints();
		gbc_lblHastaMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblHastaMes.gridx = 3;
		gbc_lblHastaMes.gridy = 3;
		bottomPanel.add(lblHastaMes, gbc_lblHastaMes);

		JLabel lblHastaAn = new JLabel("Hasta");
		GridBagConstraints gbc_lblHastaAn = new GridBagConstraints();
		gbc_lblHastaAn.insets = new Insets(0, 0, 5, 5);
		gbc_lblHastaAn.gridx = 5;
		gbc_lblHastaAn.gridy = 3;
		bottomPanel.add(lblHastaAn, gbc_lblHastaAn);

		// Obtengo fecha de salida maxima
		Date fechaEntrada = new Date(subscrito.getFechaEntrada());

		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaEntrada);
		c1.add(Calendar.DATE, 7); // Anado 7 dias
		long s1 = c1.getTime().getTime();

		JLabel lblSalidaSem = new JLabel(formatter.format(new Date(s1).getTime()));
		lblSalidaSem.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSalidaSem = new GridBagConstraints();
		gbc_lblSalidaSem.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalidaSem.gridx = 1;
		gbc_lblSalidaSem.gridy = 4;
		bottomPanel.add(lblSalidaSem, gbc_lblSalidaSem);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaEntrada);
		c2.add(Calendar.MONTH, 1); // Anado 1 mes

		JLabel lblSalidaMes = new JLabel(formatter.format(new Date(c2.getTime().getTime()).getTime()));
		lblSalidaMes.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSalidaMes = new GridBagConstraints();
		gbc_lblSalidaMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalidaMes.gridx = 3;
		gbc_lblSalidaMes.gridy = 4;
		bottomPanel.add(lblSalidaMes, gbc_lblSalidaMes);

		Calendar c3 = Calendar.getInstance();
		c3.setTime(fechaEntrada);
		c3.add(Calendar.YEAR, 1); // Anado 1 mes

		JLabel lblSalidaAno = new JLabel(formatter.format(new Date(c3.getTime().getTime()).getTime()));
		lblSalidaAno.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSalidaAno = new GridBagConstraints();
		gbc_lblSalidaAno.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalidaAno.gridx = 5;
		gbc_lblSalidaAno.gridy = 4;
		bottomPanel.add(lblSalidaAno, gbc_lblSalidaAno);

		JButton btnSem = new JButton("COMPRAR");
		btnSem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int seleccionado = tPlazas.getSelectedRow();
				subscrito.setPlazaOcupada(plazas.get(seleccionado));

				Date ent = new Date(subscrito.getFechaEntrada());
				Calendar cal = Calendar.getInstance();
				cal.setTime(ent);
				cal.add(Calendar.DATE, 7); // Anado 7 dias
				long sal = cal.getTime().getTime();

				subscrito.setFechaSalida(sal);
				System.out.println(s1);
				
				
				long tiempoTrans = new Date(sal).getTime() - new Date(subscrito.getFechaEntrada()).getTime();
				long min = TimeUnit.MILLISECONDS.toMinutes(tiempoTrans)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tiempoTrans));

				
				double tarifa;
				if (subscrito.getTipoVehiculo().equals("Ordinario")) {
					tarifa = 0.50;
				} else if (subscrito.getTipoVehiculo().equals("Electrico")) {
					tarifa = 0.40;
				} else {
					tarifa = 0.30;
				}
				double importe = tarifa * min;
				subscrito.setPrecioCuota(importe);				
				ServicioPersistenciaBD.subscritoInsert(subscrito);

//				System.out.println(plazas.get(seleccionado));
				PanelPago panel = new PanelPago(frame, instance, subscrito, lblSalidaSem.toString());
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);

			}
		});
		GridBagConstraints gbc_btnSem = new GridBagConstraints();
		gbc_btnSem.insets = new Insets(0, 0, 5, 5);
		gbc_btnSem.gridx = 1;
		gbc_btnSem.gridy = 5;
		bottomPanel.add(btnSem, gbc_btnSem);

		JButton btnMes = new JButton("COMPRAR");
		btnMes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int seleccionado = tPlazas.getSelectedRow();
				subscrito.setPlazaOcupada(plazas.get(seleccionado));

				Date ent = new Date(subscrito.getFechaEntrada());
				Calendar cal = Calendar.getInstance();
				cal.setTime(ent);
				cal.add(Calendar.MONTH, 1); // Anado 7 dias
				long sal = cal.getTime().getTime();

				subscrito.setFechaSalida(sal);
				System.out.println(s1);
				
				long tiempoTrans = new Date(sal).getTime() - new Date(subscrito.getFechaEntrada()).getTime();
				long min = TimeUnit.MILLISECONDS.toMinutes(tiempoTrans)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tiempoTrans));

				
				double tarifa;
				if (subscrito.getTipoVehiculo().equals("Ordinario")) {
					tarifa = 0.50;
				} else if (subscrito.getTipoVehiculo().equals("Electrico")) {
					tarifa = 0.40;
				} else {
					tarifa = 0.30;
				}
				double importe = tarifa * min;

				subscrito.setPrecioCuota(importe);				
				ServicioPersistenciaBD.subscritoInsert(subscrito);

//				System.out.println(plazas.get(seleccionado));
				PanelPago panel = new PanelPago(frame, instance, subscrito, lblSalidaSem.toString());
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);

			}
		});
		GridBagConstraints gbc_btnMes = new GridBagConstraints();
		gbc_btnMes.insets = new Insets(0, 0, 5, 5);
		gbc_btnMes.gridx = 3;
		gbc_btnMes.gridy = 5;
		bottomPanel.add(btnMes, gbc_btnMes);

		JButton btnAn = new JButton("COMPRAR");
		btnAn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int seleccionado = tPlazas.getSelectedRow();
				subscrito.setPlazaOcupada(plazas.get(seleccionado));

				Date ent = new Date(subscrito.getFechaEntrada());
				Calendar cal = Calendar.getInstance();
				cal.setTime(ent);
				cal.add(Calendar.YEAR, 1); // Anado 7 dias
				long sal = cal.getTime().getTime();

				subscrito.setFechaSalida(sal);
				System.out.println(s1);
				
				long tiempoTrans = new Date(sal).getTime() - new Date(subscrito.getFechaEntrada()).getTime();
				long min = TimeUnit.MILLISECONDS.toMinutes(tiempoTrans)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tiempoTrans));

				
				double tarifa;
				if (subscrito.getTipoVehiculo().equals("Ordinario")) {
					tarifa = 0.50;
				} else if (subscrito.getTipoVehiculo().equals("Electrico")) {
					tarifa = 0.40;
				} else {
					tarifa = 0.30;
				}
				double importe = tarifa * min;
				subscrito.setPrecioCuota(importe);				
				ServicioPersistenciaBD.subscritoInsert(subscrito);

//				System.out.println(plazas.get(seleccionado));
				PanelPago panel = new PanelPago(frame, instance, subscrito, lblSalidaSem.toString());
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnAn = new GridBagConstraints();
		gbc_btnAn.insets = new Insets(0, 0, 5, 5);
		gbc_btnAn.gridx = 5;
		gbc_btnAn.gridy = 5;
		bottomPanel.add(btnAn, gbc_btnAn);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
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

	public void cargarTabla(List<Plaza> plazas) {
		Vector<String> cabeceras = new Vector<String>(
				Arrays.asList("Numero de planta", "Numero de plaza", "Tipo de plaza", "Estado"));
		DefaultTableModel modelo = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
		tPlazas = new JTable(modelo);
		for (Plaza p : plazas) {
			String estado;
			if (!p.isEstadoPlaza()) {
				estado = "Disponible";
			} else {
				estado = "Ocupado";
			}
			modelo.addRow(new Object[] { p.getNumeroPlanta(), p.getNumeroPlaza(), p.getTipoPlaza(), estado });
		}
	}

}
