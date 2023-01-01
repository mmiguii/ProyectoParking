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
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
	private JTable tableOrdinarios;
	private JTable tableSubscritos;
	private JTable tableTrabajadores;
	private JTable tablePlazas;

	public PanelEstadoParking(JFrame frame, JPanel panel, Trabajador trabajador) {
		setBackground(new Color(0, 128, 128));

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Parking State Panel");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 128, 128));
	
		add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setBackground(new Color(0, 128, 128));
		add(middlePanel);
		
		GridBagLayout gbl_middlePanel = new GridBagLayout();
		gbl_middlePanel.columnWidths = new int[]{557, 0};
		gbl_middlePanel.rowHeights = new int[]{27, 108, 0};
		gbl_middlePanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_middlePanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		middlePanel.setLayout(gbl_middlePanel);
		
		JLabel lblConsulta = new JLabel("");
		lblConsulta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblConsulta = new GridBagConstraints();
		gbc_lblConsulta.insets = new Insets(0, 0, 5, 5);
		gbc_lblConsulta.fill = GridBagConstraints.BOTH;
		gbc_lblConsulta.gridx = 0;
		gbc_lblConsulta.gridy = 0;
		middlePanel.add(lblConsulta, gbc_lblConsulta);
		
		tableSubscritos = new JTable();
		scrollSubscritos = new JScrollPane(tableSubscritos);
		GridBagConstraints gbc_scrollSubscritos = new GridBagConstraints();
		gbc_scrollSubscritos.insets = new Insets(0, 0, 5, 5);
		gbc_scrollSubscritos.fill = GridBagConstraints.BOTH;
		gbc_scrollSubscritos.gridx = 0;
		gbc_scrollSubscritos.gridy = 1;
		middlePanel.add(scrollSubscritos, gbc_scrollSubscritos);
		
		
		tableTrabajadores = new JTable();
		scrollTrabajadores = new JScrollPane(tableTrabajadores);
		GridBagConstraints gbc_scrollTrabajadores = new GridBagConstraints();
		gbc_scrollTrabajadores.insets = new Insets(0, 0, 5, 5);
		gbc_scrollTrabajadores.fill = GridBagConstraints.BOTH;
		gbc_scrollTrabajadores.gridx = 0;
		gbc_scrollTrabajadores.gridy = 1;
		middlePanel.add(scrollTrabajadores, gbc_scrollTrabajadores);
		
		tablePlazas= new JTable();
		scrollPlazas = new JScrollPane(tablePlazas);
		GridBagConstraints gbc_scrollPlazas = new GridBagConstraints();
		gbc_scrollPlazas.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPlazas.fill = GridBagConstraints.BOTH;
		gbc_scrollPlazas.gridx = 0;
		gbc_scrollPlazas.gridy = 1;
		middlePanel.add(scrollPlazas, gbc_scrollPlazas);
		
		tableOrdinarios = new JTable();
		scrollOrdinarios = new JScrollPane(tableOrdinarios);
		GridBagConstraints gbc_scrollOrdinarios = new GridBagConstraints();
		gbc_scrollOrdinarios.insets = new Insets(0, 0, 5, 5);
		gbc_scrollOrdinarios.fill = GridBagConstraints.BOTH;
		gbc_scrollOrdinarios.gridx = 0;
		gbc_scrollOrdinarios.gridy = 1;
		middlePanel.add(scrollOrdinarios, gbc_scrollOrdinarios);
				
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.columnWidths = new int[]{69, 63, 0};
		gbl_topPanel.rowHeights = new int[]{22, 0};
		gbl_topPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_topPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		topPanel.setLayout(gbl_topPanel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		menuBar.setBounds(0, 0, 62, 22);
		GridBagConstraints gbc_menuBar = new GridBagConstraints();
		gbc_menuBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_menuBar.fill = GridBagConstraints.BOTH;
		gbc_menuBar.gridx = 0;
		gbc_menuBar.gridy = 0;
		topPanel.add(menuBar, gbc_menuBar);
		
		JMenu fileJMenu = new JMenu("Consultas");
		menuBar.add(fileJMenu);		
		
		JMenuItem importItem = null;
		JMenuItem importItem1 = null;
		JMenuItem importItem2 = null;
		JMenuItem importItem3 = null;
		JMenuItem importItem4 = null;
		JMenuItem importItem5 = null;
		JMenuItem importItem6 = null;

		
		if(trabajador.getDni().equals("12345678A")) {
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
					mostrarProgresoPago("Consultando listado de ingresos y gastos...");
					
					double salario = ServicioPersistenciaBD.getInstance().salarioSelect();
					JLabel label = new JLabel("Gastos totales en salarios: ");
					label.setBounds(25,25,50,50);
					JTextField textSalario = new JTextField();
					textSalario.setText(Double.toString(salario));
					textSalario.setBounds(80,20,30,30);

					scrollTrabajadores.setVisible(false);
					scrollOrdinarios.setVisible(false);
					scrollSubscritos.setVisible(false);
					scrollPlazas.setVisible(false);
					
					middlePanel.add(label);
					middlePanel.add(textSalario);
				}
			});
			
			
			importItem6.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					mostrarProgresoPago("Consultando listado de trabajadores...");
					Vector<String> cabeceras = new Vector<>(Arrays.asList("Usuario","DNI","Password","Email", "Fecha inicio", "Salario"));
					DefaultTableModel modeloTrabajadores = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
					logger.info("Cargando informacion de trabajadores");
					
					Map<String,Trabajador> mapaTrabajador = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
					for (Map.Entry<String, Trabajador> entry : mapaTrabajador.entrySet()) {
					    Trabajador trabajador = entry.getValue();
					    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					    modeloTrabajadores.addRow(new Object[] {trabajador.getNombreUsuario(), trabajador.getDni(),trabajador.getPassword(), trabajador.getEmail(), sdf.format(new Date(trabajador.getFechaComienzo())), 
					    		Double.toString(trabajador.getSalario())
					    });
					}			
		
					lblConsulta.setText("Trabajadores");
					
					tableTrabajadores.setModel(modeloTrabajadores);
					scrollTrabajadores.setBounds(25, 25, 500, 100);
					scrollTrabajadores.setVisible(true);
					scrollOrdinarios.setVisible(false);
					scrollSubscritos.setVisible(false);
					scrollPlazas.setVisible(false);
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
				mostrarProgresoPago("Consultando listado de clientes ordinarios...");
				Vector<String> cabeceras = new Vector<>(Arrays.asList("Matricula","Tipo Vehiculo","Tarifa","Fecha de Entrada"));
				DefaultTableModel modeloOrdinarios = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
				logger.info("Cargando datos de clientes ordinarios");
				
				Map<String,ClienteOrdinario> mapaOrdinarios = ServicioPersistenciaBD.getInstance().ordinariosSelect();
				for (Map.Entry<String, ClienteOrdinario> entry : mapaOrdinarios.entrySet()) {
				    ClienteOrdinario ordinario = entry.getValue();
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				    modeloOrdinarios.addRow(new Object[] {ordinario.getMatricula(), ordinario.getTipoVehiculo(),ordinario.getTarifa(), sdf.format(new Date(ordinario.getFechaEntrada()))
				    });
				}			
				
				lblConsulta.setText("Clientes ordinarios");
				
				tableOrdinarios.setModel(modeloOrdinarios);
				scrollOrdinarios.setBounds(25, 25, 500, 100);
				scrollOrdinarios.setVisible(true);
				scrollSubscritos.setVisible(false);
				scrollTrabajadores.setVisible(false);
				scrollPlazas.setVisible(false);
			}
		});
		
		importItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrarProgresoPago("Consultando listado de clientes subscritos...");
				Vector<String> cabeceras = new Vector<>(Arrays.asList("Matricula","Tipo Vehiculo","Cuota","Fecha de Entrada"));
				DefaultTableModel modeloSubscritos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
				logger.info("Cargando datos de clientes subscritos");
				
				Map<String,ClienteSubscrito> mapaSubscritos = ServicioPersistenciaBD.getInstance().subscritosSelect();
				for (Map.Entry<String, ClienteSubscrito> entry : mapaSubscritos.entrySet()) {
				    ClienteSubscrito subscrito = entry.getValue();
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				    modeloSubscritos.addRow(new Object[] {subscrito.getMatricula(), subscrito.getTipoVehiculo(),subscrito.getPrecioCuota(), sdf.format(new Date(subscrito.getFechaEntrada()))
				    });
				}			

				lblConsulta.setText("Clientes subscritos");
				
				tableSubscritos.setModel(modeloSubscritos);
				scrollSubscritos.setBounds(25, 25, 500, 100);
				scrollSubscritos.setVisible(true);
				scrollOrdinarios.setVisible(false);
				scrollTrabajadores.setVisible(false);
				scrollPlazas.setVisible(false);
			}
		});
		
		importItem2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarProgresoPago("Comprobando estado de las plazas del parking");
				Vector<String> cabeceras = new Vector<>(Arrays.asList("Planta","Plaza","Tipo de plaza","Estado","Matricula"));
				DefaultTableModel modeloPlazas = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
				logger.info("Cargando datos del estado de las plazas de aparcamiento");
				
				Map<Integer,Plaza> mapaPlazas = ServicioPersistenciaBD.getInstance().plazasSelect();
				for (Map.Entry<Integer,Plaza> entry : mapaPlazas.entrySet()) {
				    Plaza plaza = entry.getValue();
				    modeloPlazas.addRow(new Object[] {plaza.getNumeroPlanta(),plaza.getNumeroPlaza(),plaza.getTipoPlaza(),plaza.isEstadoPlaza()?"DISPONIBLE":"OCUPADO",plaza.getMatricula()});
				}			
	
				lblConsulta.setText("Estado de las plazas");
				
				tablePlazas.setModel(modeloPlazas);
				
				tablePlazas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
							int row, int column) {
						Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
						if (tablePlazas.getValueAt(row, 3).equals("OCUPADO")) {
							c.setBackground(Color.RED);
						} else if (tablePlazas.getValueAt(row, 3).equals("DISPONIBLE")) {
							c.setBackground(Color.GREEN);
						}
						return c;
					}
				});
				scrollPlazas.setBounds(25, 25, 500, 100);
				scrollPlazas.setVisible(true);
				scrollSubscritos.setVisible(false);
				scrollOrdinarios.setVisible(false);
				scrollTrabajadores.setVisible(false);
			}
		});
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(0, 128, 128));
		bottomPanel.setLayout(new GridBagLayout());
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
		bottomPanel.add(btnVolver);
		add(bottomPanel);
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
					dialog.dispose();
//					pane.setMessage("Transaccion realizada. ¡Gracias!");
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

