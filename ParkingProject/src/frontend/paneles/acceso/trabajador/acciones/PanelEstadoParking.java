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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;

public class PanelEstadoParking extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PanelEstadoParking.class.getName());

	private JScrollPane scrollOrdinarios;
	private JScrollPane scrollSubscritos;
	private JScrollPane scrollTrabajadores;
	private JScrollPane scrollPlazas;
	private JScrollPane scrollMargen;
	private JScrollPane scrollNumPlazas;
	private JScrollPane scrollTipo;
	private JTable tableOrdinarios;
	private JTable tableSubscritos;
	private JTable tableTrabajadores;
	private JTable tablePlazas;
	private JTable tableMargen;
	private JTable tableNumPlazas;
	private JTable tableTipo;

	private JPanel middlePanel;
	private double ingresoTotal;
	private JLabel lblConsulta;
	private JLabel lblVacio;
	private JLabel lblVacioBis;
	private JButton btnObservarGraficaResultados;
	private JButton btnObservarGraficaVehiculos;
	private JButton btnObservarGraficaOcupacionYClientes;
	private JFrame graficaIngresosYGastos = new JFrame("Ingresos y Gastos del Parking");
	private JFrame graficaVehiculos = new JFrame("Tipos de vehiculo actualmente en el parking");
	private JFrame graficaOcupacionYClientes = new JFrame("Ocupacion y clientes del parking");;

	public PanelEstadoParking(JFrame frame, JPanel panel, Trabajador trabajador) {
		setBackground(new Color(0, 128, 128));

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Parking State Panel");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		graficaIngresosYGastos.setVisible(false);
		graficaVehiculos.setVisible(false);
		graficaOcupacionYClientes.setVisible(false);

		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 128, 128));
		add(topPanel);

		middlePanel = new JPanel();
		middlePanel.setBackground(new Color(0, 128, 128));
		add(middlePanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(0, 128, 128));
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[] { 165, 206, 0 };
		gbl_bottomPanel.rowHeights = new int[] { 44, 23, 23, 0 };
		gbl_bottomPanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_bottomPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		bottomPanel.setLayout(gbl_bottomPanel);

		btnObservarGraficaResultados = new JButton("OBSERVAR GRAFICA");
		btnObservarGraficaResultados.setForeground(new Color(0, 128, 128));
		btnObservarGraficaResultados.setVisible(false);
		GridBagConstraints gbc_btnObservarGraficaResultados = new GridBagConstraints();
		gbc_btnObservarGraficaResultados.anchor = GridBagConstraints.NORTH;
		gbc_btnObservarGraficaResultados.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnObservarGraficaResultados.insets = new Insets(0, 0, 5, 0);
		gbc_btnObservarGraficaResultados.gridx = 1;
		gbc_btnObservarGraficaResultados.gridy = 1;
		bottomPanel.add(btnObservarGraficaResultados, gbc_btnObservarGraficaResultados);
		add(bottomPanel);

		btnObservarGraficaVehiculos = new JButton("OBSERVAR GRAFICA");
		btnObservarGraficaVehiculos.setForeground(new Color(0, 128, 128));
		btnObservarGraficaVehiculos.setVisible(false);
		GridBagConstraints gbc_btnObservarGraficaVehiculos = new GridBagConstraints();
		gbc_btnObservarGraficaVehiculos.anchor = GridBagConstraints.NORTH;
		gbc_btnObservarGraficaVehiculos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnObservarGraficaVehiculos.insets = new Insets(0, 0, 5, 0);
		gbc_btnObservarGraficaVehiculos.gridx = 1;
		gbc_btnObservarGraficaVehiculos.gridy = 1;
		bottomPanel.add(btnObservarGraficaVehiculos, gbc_btnObservarGraficaVehiculos);
		add(bottomPanel);

		btnObservarGraficaOcupacionYClientes = new JButton("OBSERVAR GRAFICA");
		btnObservarGraficaOcupacionYClientes.setForeground(new Color(0, 128, 128));
		btnObservarGraficaOcupacionYClientes.setVisible(false);
		GridBagConstraints gbc_btnObservarGraficaOcupacionYClientes = new GridBagConstraints();
		gbc_btnObservarGraficaOcupacionYClientes.anchor = GridBagConstraints.NORTH;
		gbc_btnObservarGraficaOcupacionYClientes.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnObservarGraficaOcupacionYClientes.insets = new Insets(0, 0, 5, 0);
		gbc_btnObservarGraficaOcupacionYClientes.gridx = 1;
		gbc_btnObservarGraficaOcupacionYClientes.gridy = 1;
		bottomPanel.add(btnObservarGraficaOcupacionYClientes, gbc_btnObservarGraficaOcupacionYClientes);
		add(bottomPanel);

		GridBagLayout gbl_middlePanel = new GridBagLayout();
		gbl_middlePanel.columnWidths = new int[] { 557, 0 };
		gbl_middlePanel.rowHeights = new int[] { 108, 0 };
		gbl_middlePanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_middlePanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		middlePanel.setLayout(gbl_middlePanel);

		tableSubscritos = new JTable();
		scrollSubscritos = new JScrollPane(tableSubscritos);
		JTableHeader headerSubscritos = tableSubscritos.getTableHeader();
		headerSubscritos.setOpaque(true);
		headerSubscritos.setBackground(new Color(255, 222, 173));
		GridBagConstraints gbc_scrollSubscritos = new GridBagConstraints();
		gbc_scrollSubscritos.fill = GridBagConstraints.BOTH;
		gbc_scrollSubscritos.gridx = 0;
		gbc_scrollSubscritos.gridy = 0;
		middlePanel.add(scrollSubscritos, gbc_scrollSubscritos);

		tableTrabajadores = new JTable();
		scrollTrabajadores = new JScrollPane(tableTrabajadores);
		JTableHeader headerTrabajadores = tableTrabajadores.getTableHeader();
		headerTrabajadores.setOpaque(true);
		headerTrabajadores.setBackground(new Color(255, 222, 173));
		GridBagConstraints gbc_scrollTrabajadores = new GridBagConstraints();
		gbc_scrollTrabajadores.fill = GridBagConstraints.BOTH;
		gbc_scrollTrabajadores.gridx = 0;
		gbc_scrollTrabajadores.gridy = 0;
		middlePanel.add(scrollTrabajadores, gbc_scrollTrabajadores);

		tablePlazas = new JTable();
		scrollPlazas = new JScrollPane(tablePlazas);
		JTableHeader headerPlazas = tablePlazas.getTableHeader();
		headerPlazas.setOpaque(true);
		headerPlazas.setBackground(new Color(255, 222, 173));
		GridBagConstraints gbc_scrollPlazas = new GridBagConstraints();
		gbc_scrollPlazas.fill = GridBagConstraints.BOTH;
		gbc_scrollPlazas.gridx = 0;
		gbc_scrollPlazas.gridy = 0;
		middlePanel.add(scrollPlazas, gbc_scrollPlazas);

		tableOrdinarios = new JTable();
		scrollOrdinarios = new JScrollPane(tableOrdinarios);
		JTableHeader headerOrdinarios = tableOrdinarios.getTableHeader();
		headerOrdinarios.setOpaque(true);
		headerOrdinarios.setBackground(new Color(255, 222, 173));
		GridBagConstraints gbc_scrollOrdinarios = new GridBagConstraints();
		gbc_scrollOrdinarios.fill = GridBagConstraints.BOTH;
		gbc_scrollOrdinarios.gridx = 0;
		gbc_scrollOrdinarios.gridy = 0;
		middlePanel.add(scrollOrdinarios, gbc_scrollOrdinarios);

		tableMargen = new JTable();
		scrollMargen = new JScrollPane(tableMargen);
		JTableHeader headerMargen = tableMargen.getTableHeader();
		headerMargen.setOpaque(true);
		headerMargen.setBackground(new Color(255, 222, 173));
		GridBagConstraints gbc_scrollMargen = new GridBagConstraints();
		gbc_scrollMargen.fill = GridBagConstraints.BOTH;
		gbc_scrollMargen.gridx = 0;
		gbc_scrollMargen.gridy = 0;
		middlePanel.add(scrollMargen, gbc_scrollMargen);

		tableNumPlazas = new JTable();
		scrollNumPlazas = new JScrollPane(tableNumPlazas);
		JTableHeader headerNumPlazas = tableNumPlazas.getTableHeader();
		headerNumPlazas.setOpaque(true);
		headerNumPlazas.setBackground(new Color(255, 222, 173));
		GridBagConstraints gbc_scrollNumPlazas = new GridBagConstraints();
		gbc_scrollNumPlazas.fill = GridBagConstraints.BOTH;
		gbc_scrollNumPlazas.gridx = 0;
		gbc_scrollNumPlazas.gridy = 0;
		middlePanel.add(scrollNumPlazas, gbc_scrollNumPlazas);

		tableTipo = new JTable();
		scrollTipo = new JScrollPane(tableTipo);
		JTableHeader headerTipo = tableTipo.getTableHeader();
		headerTipo.setOpaque(true);
		headerTipo.setBackground(new Color(255, 222, 173));
		GridBagConstraints gbc_scrollTipo = new GridBagConstraints();
		gbc_scrollTipo.fill = GridBagConstraints.BOTH;
		gbc_scrollTipo.gridx = 0;
		gbc_scrollTipo.gridy = 0;
		middlePanel.add(scrollTipo, gbc_scrollTipo);

		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[] { 284, 0 };
		gbl_topPanel.rowHeights = new int[] { 22, 0, 0, 0, 0 };
		gbl_topPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_topPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		topPanel.setLayout(gbl_topPanel);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		menuBar.setBounds(0, 0, 62, 22);
		GridBagConstraints gbc_menuBar = new GridBagConstraints();
		gbc_menuBar.anchor = GridBagConstraints.WEST;
		gbc_menuBar.insets = new Insets(0, 0, 5, 0);
		gbc_menuBar.fill = GridBagConstraints.VERTICAL;
		gbc_menuBar.gridx = 0;
		gbc_menuBar.gridy = 0;
		topPanel.add(menuBar, gbc_menuBar);

		JMenu fileJMenu = new JMenu("Consultas");
		fileJMenu.setBackground(new Color(255, 255, 255));
		menuBar.add(fileJMenu);

		JMenuItem importItem = null;
		JMenuItem importItem1 = null;
		JMenuItem importItem2 = null;
		JMenuItem importItem3 = null;
		JMenuItem importItem4 = null;
		JMenuItem importItem5 = null;
		JMenuItem importItem6 = null;

		if (trabajador.getDni().equals("12345678A")) {
			importItem = new JMenuItem("Consultar clientes ordinarios");
			importItem1 = new JMenuItem("Consultar clientes subscritos");
			importItem2 = new JMenuItem("Comprobar plazas");
			importItem3 = new JMenuItem("Comprobar ingresos y gastos");
			importItem4 = new JMenuItem("Comprobar tipos de vehículo");
			importItem5 = new JMenuItem("Comprobar tipos de cliente");
			importItem6 = new JMenuItem("Comprobar trabajadores");

			fileJMenu.add(importItem);
			fileJMenu.add(importItem1);
			fileJMenu.add(importItem2);
			fileJMenu.add(importItem3);
			fileJMenu.add(importItem4);
			fileJMenu.add(importItem5);
			fileJMenu.add(importItem6);

			importItem3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mostrarProgresoConsultas("Consultando listado de ingresos y gastos...");

					Vector<String> cabeceras = new Vector<>(Arrays.asList("Ingresos", "Gastos", "Margen de beneficio"));
					DefaultTableModel modeloMargen = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
					logger.info("Cargando informacion del resultado");

					List<Double> ingresos = ServicioPersistenciaBD.getInstance().ingresosTotales();
					ingresos.forEach(ingreso -> ingresoTotal = +ingreso);
					double gastos = ServicioPersistenciaBD.getInstance().salarioSelect();
					double margen = ingresoTotal - gastos;

					modeloMargen.addRow(new Object[] { ingresoTotal, gastos, margen });
					lblConsulta.setText("Resultado");

					tableMargen.setModel(modeloMargen);
					tableMargen.getColumnModel().getColumn(2)
							.setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
								Component c = new DefaultTableCellRenderer().getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
								double valor = (double) value;
								if (valor < 0) {
									c.setForeground(new Color(205, 92, 92));
								} else {
									c.setForeground(new Color(144, 238, 144));
								}
								return c;
							});

					scrollMargen.setBounds(25, 25, 500, 100);
					scrollMargen.setVisible(true);
					scrollTipo.setVisible(false);
					scrollNumPlazas.setVisible(false);
					scrollTrabajadores.setVisible(false);
					scrollOrdinarios.setVisible(false);
					scrollSubscritos.setVisible(false);
					scrollPlazas.setVisible(false);

					btnObservarGraficaResultados.setVisible(true);
					btnObservarGraficaVehiculos.setVisible(false);
					btnObservarGraficaOcupacionYClientes.setVisible(false);

					btnObservarGraficaResultados.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							logger.info("Cargando informacion de ingresos y gastos en la grafica...");
							DefaultPieDataset datasetResultado = new DefaultPieDataset();
							datasetResultado.setValue("Ingresos", ingresoTotal);
							datasetResultado.setValue("Gastos", gastos);

							JFreeChart ingresosYGastos = ChartFactory.createPieChart("Ingresos y Gastos del Parking",
									datasetResultado, true, true, false);
							ChartPanel panelIngresosGastos = new ChartPanel(ingresosYGastos);

							if (!graficaIngresosYGastos.isVisible()) {
								graficaIngresosYGastos.setContentPane(panelIngresosGastos);
								graficaIngresosYGastos.pack();
								RefineryUtilities.centerFrameOnScreen(graficaIngresosYGastos);
								graficaIngresosYGastos.setVisible(true);
							} else if (graficaIngresosYGastos.isVisible()) {
								JOptionPane.showMessageDialog(panel, "Ya tiene abierto el grafico de resultados");
							}
						}
					});
				}
			});

			importItem4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarProgresoConsultas("Consultando estadisticas de tipos de vehiculos...");
					Vector<String> cabeceras = new Vector<>(
							Arrays.asList("Ocupadas", "Ordinarios", "Electricos", "Minusvalidos"));
					DefaultTableModel modeloTipo = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
					logger.info("Cargando informacion del numero de plazas");

					List<Integer> listaPlazasTipo = ServicioPersistenciaBD.getInstance().numeroUsuarios();
					List<Integer> listaTipo = ServicioPersistenciaBD.getInstance().numeroUsuariosPorVehiculo();
					modeloTipo.addRow(new Object[] { listaPlazasTipo.get(0), listaTipo.get(0), listaTipo.get(1),
							listaTipo.get(2) });
					lblConsulta.setText("Numero de plazas por tipo de vehiculo");

					tableTipo.setModel(modeloTipo);
					scrollTipo.setBounds(25, 25, 500, 100);
					scrollTipo.setVisible(true);
					scrollNumPlazas.setVisible(false);
					scrollMargen.setVisible(false);
					scrollTrabajadores.setVisible(false);
					scrollOrdinarios.setVisible(false);
					scrollSubscritos.setVisible(false);
					scrollPlazas.setVisible(false);

					btnObservarGraficaResultados.setVisible(false);
					btnObservarGraficaVehiculos.setVisible(true);
					btnObservarGraficaOcupacionYClientes.setVisible(false);

					btnObservarGraficaVehiculos.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							logger.info("Cargando informacion de los tipos de vehiculos en la grafica...");
							DefaultPieDataset datasetVehiculos = new DefaultPieDataset();
							datasetVehiculos.setValue("Ordinarios", listaTipo.get(0));
							datasetVehiculos.setValue("Electricos", listaTipo.get(1));
							datasetVehiculos.setValue("Minusvalidos", listaTipo.get(2));

							JFreeChart vehiculos = ChartFactory.createPieChart(
									"Tipos de vehiculo actualmente en el parking", datasetVehiculos, true, true, false);
							ChartPanel panelTipoVehiculo = new ChartPanel(vehiculos);

							if (!graficaVehiculos.isVisible()) {
								graficaVehiculos.setContentPane(panelTipoVehiculo);
								graficaVehiculos.pack();
								RefineryUtilities.centerFrameOnScreen(graficaVehiculos);
								graficaVehiculos.setVisible(true);
							} else if (graficaVehiculos.isVisible()) {
								JOptionPane.showMessageDialog(panel, "Ya tiene abierto el grafico de vehiculos");
							}
						}
					});

				}
			});

			importItem5.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarProgresoConsultas("Consultando estadisticas de tipos de clientes...");
					Vector<String> cabeceras = new Vector<>(
							Arrays.asList("Disponibles", "Ocupadas", "Ordinarios", "Subscritos"));
					DefaultTableModel modeloNumPlazas = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
					logger.info("Cargando informacion del numero de plazas");

					List<Integer> listaPlazas = ServicioPersistenciaBD.getInstance().ocupacionPlazas();
					List<Integer> listaPlazasTipo = ServicioPersistenciaBD.getInstance().numeroUsuarios();
					modeloNumPlazas.addRow(new Object[] { listaPlazas.get(0), listaPlazas.get(1),
							listaPlazasTipo.get(1), listaPlazasTipo.get(2) });
					lblConsulta.setText("Numero de plazas por tipo de cliente");

					tableNumPlazas.setModel(modeloNumPlazas);
					scrollNumPlazas.setBounds(25, 25, 500, 100);
					scrollNumPlazas.setVisible(true);
					scrollTipo.setVisible(false);
					scrollMargen.setVisible(false);
					scrollTrabajadores.setVisible(false);
					scrollOrdinarios.setVisible(false);
					scrollSubscritos.setVisible(false);
					scrollPlazas.setVisible(false);

					btnObservarGraficaResultados.setVisible(false);
					btnObservarGraficaVehiculos.setVisible(false);
					btnObservarGraficaOcupacionYClientes.setVisible(true);

					btnObservarGraficaOcupacionYClientes.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							logger.info("Cargando informacion de ocupacion y tipos de clientes en graficas...");
							DefaultPieDataset datasetOcupacion = new DefaultPieDataset();
							datasetOcupacion.setValue("Disponible", listaPlazas.get(0));
							datasetOcupacion.setValue("Ocupadas", listaPlazas.get(1));

							DefaultPieDataset datasetClientes = new DefaultPieDataset();
							datasetClientes.setValue("Ordinarios", listaPlazasTipo.get(1));
							datasetClientes.setValue("Subscritos", listaPlazasTipo.get(2));

							JPanel panelOcupacionYCLientes = new JPanel();
							panelOcupacionYCLientes.setLayout(new BoxLayout(panelOcupacionYCLientes, BoxLayout.X_AXIS));

							JFreeChart ocupacion = ChartFactory.createPieChart("Ocupacion actual del parking",
									datasetOcupacion, true, true, false);
							ChartPanel panelOcupacion = new ChartPanel(ocupacion);
							panelOcupacionYCLientes.add(panelOcupacion);

							JFreeChart clientes = ChartFactory.createPieChart("Clientes que hay en el parking",
									datasetClientes, true, true, false);
							ChartPanel panelClientes = new ChartPanel(clientes);
							panelOcupacionYCLientes.add(panelClientes);

							if (!graficaOcupacionYClientes.isVisible()) {
								graficaOcupacionYClientes.setContentPane(panelOcupacionYCLientes);
								graficaOcupacionYClientes.pack();
								RefineryUtilities.centerFrameOnScreen(graficaOcupacionYClientes);
								graficaOcupacionYClientes.setVisible(true);
							} else if (graficaOcupacionYClientes.isVisible()) {
								JOptionPane.showMessageDialog(panel,
										"Ya tiene abierto el grafico de ocupacion y clientes");
							}

						}
					});
				}
			});

			importItem6.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mostrarProgresoConsultas("Consultando listado de trabajadores...");
					Vector<String> cabeceras = new Vector<>(
							Arrays.asList("Usuario", "DNI", "Password", "Email", "Fecha inicio", "Salario"));
					DefaultTableModel modeloTrabajadores = new DefaultTableModel(new Vector<Vector<Object>>(),
							cabeceras);
					logger.info("Cargando informacion de trabajadores");

					Map<String, Trabajador> mapaTrabajador = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
					mapaTrabajador.forEach((k, v) -> {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						modeloTrabajadores
								.addRow(new Object[] { v.getNombreUsuario(), v.getDni(), v.getPassword(), v.getEmail(),
										sdf.format(new Date(v.getFechaComienzo())), Double.toString(v.getSalario()) });
					});

					lblConsulta.setText("Trabajadores");

					tableTrabajadores.setModel(modeloTrabajadores);
					scrollTrabajadores.setBounds(25, 25, 500, 100);
					scrollTrabajadores.setVisible(true);
					scrollNumPlazas.setVisible(false);
					scrollTipo.setVisible(false);
					scrollMargen.setVisible(false);
					scrollOrdinarios.setVisible(false);
					scrollSubscritos.setVisible(false);
					scrollPlazas.setVisible(false);

					btnObservarGraficaResultados.setVisible(false);
					btnObservarGraficaVehiculos.setVisible(false);
					btnObservarGraficaOcupacionYClientes.setVisible(false);
				}
			});

		} else {
			importItem = new JMenuItem("Consultar clientes ordinarios");
			importItem1 = new JMenuItem("Consultar clientes subscritos");
			importItem2 = new JMenuItem("Comprobar plazas");

			fileJMenu.add(importItem);
			fileJMenu.add(importItem1);
			fileJMenu.add(importItem2);
		}

		JMenuItem exitItem = new JMenuItem("Salir");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.CTRL_DOWN_MASK));
		fileJMenu.add(exitItem);

		lblVacio = new JLabel(" ");
		GridBagConstraints gbc_lblVacio = new GridBagConstraints();
		gbc_lblVacio.insets = new Insets(0, 0, 5, 0);
		gbc_lblVacio.gridx = 0;
		gbc_lblVacio.gridy = 1;
		topPanel.add(lblVacio, gbc_lblVacio);

		lblVacioBis = new JLabel(" ");
		GridBagConstraints gbc_lblVacioBis = new GridBagConstraints();
		gbc_lblVacioBis.insets = new Insets(0, 0, 5, 0);
		gbc_lblVacioBis.gridx = 0;
		gbc_lblVacioBis.gridy = 2;
		topPanel.add(lblVacioBis, gbc_lblVacioBis);

		lblConsulta = new JLabel("REALICE ALGUNA CONSULTA");
		lblConsulta.setForeground(new Color(255, 255, 255));
		lblConsulta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblConsulta = new GridBagConstraints();
		gbc_lblConsulta.gridx = 0;
		gbc_lblConsulta.gridy = 3;
		topPanel.add(lblConsulta, gbc_lblConsulta);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Cerrando programa");
				frame.dispose();
				ServicioPersistenciaBD.getInstance().disconnect();
				System.exit(0);
			}
		});

		importItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrarProgresoConsultas("Consultando listado de clientes ordinarios...");
				Vector<String> cabeceras = new Vector<>(
						Arrays.asList("Matricula", "Tipo Vehiculo", "Tarifa", "Fecha de Entrada"));
				DefaultTableModel modeloOrdinarios = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
				logger.info("Cargando datos de clientes ordinarios");

				Map<String, ClienteOrdinario> mapaOrdinarios = ServicioPersistenciaBD.getInstance().ordinariosSelect();
				mapaOrdinarios.forEach((k, v) -> {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					modeloOrdinarios.addRow(new Object[] { v.getMatricula(), v.getTipoVehiculo(), v.getTarifa(),
							sdf.format(new Date(v.getFechaEntrada())) });
				});

				lblConsulta.setText("Clientes ordinarios");

				tableOrdinarios.setModel(modeloOrdinarios);
				scrollOrdinarios.setBounds(25, 25, 500, 100);
				scrollOrdinarios.setVisible(true);
				scrollSubscritos.setVisible(false);
				scrollTrabajadores.setVisible(false);
				scrollNumPlazas.setVisible(false);
				scrollTipo.setVisible(false);
				scrollMargen.setVisible(false);
				scrollPlazas.setVisible(false);

				btnObservarGraficaResultados.setVisible(false);
				btnObservarGraficaVehiculos.setVisible(false);
				btnObservarGraficaOcupacionYClientes.setVisible(false);
			}
		});

		importItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrarProgresoConsultas("Consultando listado de clientes subscritos...");
				Vector<String> cabeceras = new Vector<>(
						Arrays.asList("Matricula", "Tipo Vehiculo", "Cuota", "Fecha de Entrada"));
				DefaultTableModel modeloSubscritos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
				logger.info("Cargando datos de clientes subscritos");

				Map<String, ClienteSubscrito> mapaSubscritos = ServicioPersistenciaBD.getInstance().subscritosSelect();
				mapaSubscritos.forEach((k, v) -> {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					modeloSubscritos.addRow(new Object[] { v.getMatricula(), v.getTipoVehiculo(), v.getPrecioCuota(),
							sdf.format(new Date(v.getFechaEntrada())) });
				});

				lblConsulta.setText("Clientes subscritos");

				tableSubscritos.setModel(modeloSubscritos);
				scrollSubscritos.setBounds(25, 25, 500, 100);
				scrollSubscritos.setVisible(true);
				scrollOrdinarios.setVisible(false);
				scrollTrabajadores.setVisible(false);
				scrollNumPlazas.setVisible(false);
				scrollTipo.setVisible(false);
				scrollMargen.setVisible(false);
				scrollPlazas.setVisible(false);

				btnObservarGraficaResultados.setVisible(false);
				btnObservarGraficaVehiculos.setVisible(false);
				btnObservarGraficaOcupacionYClientes.setVisible(false);

			}
		});

		importItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarProgresoConsultas("Comprobando estado de las plazas del parking");
				Vector<String> cabeceras = new Vector<>(
						Arrays.asList("Planta", "Plaza", "Tipo de plaza", "Estado", "Matricula"));
				DefaultTableModel modeloPlazas = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
				logger.info("Cargando datos del estado de las plazas de aparcamiento");

				Map<Integer, Plaza> mapaPlazas = ServicioPersistenciaBD.getInstance().plazasSelect();
				mapaPlazas.forEach((k, v) -> {
					modeloPlazas.addRow(new Object[] { v.getNumeroPlanta(), v.getNumeroPlaza(), v.getTipoPlaza(),
							v.isEstadoPlaza() ? "DISPONIBLE" : "OCUPADO", v.getMatricula() });
				});

				lblConsulta.setText("Estado de las plazas");

				tablePlazas.setModel(modeloPlazas);

				tablePlazas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

					private static final long serialVersionUID = 1L;

					@Override
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
								column);
						if (tablePlazas.getValueAt(row, 3).equals("OCUPADO")) {
							c.setBackground(new Color(205, 92, 92));
						} else if (tablePlazas.getValueAt(row, 3).equals("DISPONIBLE")) {
							c.setBackground(new Color(144, 238, 144));
						}
						return c;
					}
				});
				scrollPlazas.setBounds(25, 25, 500, 100);
				scrollPlazas.setVisible(true);
				scrollSubscritos.setVisible(false);
				scrollOrdinarios.setVisible(false);
				scrollNumPlazas.setVisible(false);
				scrollTipo.setVisible(false);
				scrollMargen.setVisible(false);
				scrollTrabajadores.setVisible(false);

				btnObservarGraficaResultados.setVisible(false);
				btnObservarGraficaVehiculos.setVisible(false);
				btnObservarGraficaOcupacionYClientes.setVisible(false);

			}
		});

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setForeground(new Color(0, 128, 128));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Volviendo a panel de bienvenida");
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.anchor = GridBagConstraints.NORTH;
		gbc_btnVolver.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVolver.gridx = 1;
		gbc_btnVolver.gridy = 2;
		bottomPanel.add(btnVolver, gbc_btnVolver);
	}

	public void mostrarProgresoConsultas(String message) {
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
