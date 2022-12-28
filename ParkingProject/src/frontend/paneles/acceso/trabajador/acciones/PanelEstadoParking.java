package frontend.paneles.acceso.trabajador.acciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.print.attribute.standard.JobMediaSheetsCompleted;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.clases.personas.personal.Empleado;
import backend.clases.personas.personal.Manager;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.acceso.trabajador.PanelTrabajador;

public class PanelEstadoParking extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollOrdinarios;
	private JScrollPane scrollSubscritos;
	private JScrollPane scrollTrabajadores;
	private JScrollPane scroll;
	private JTable table;
	private JTable table1;
	private JTable table2;

	public PanelEstadoParking(JFrame frame, JPanel panel, Trabajador trabajador) {

		setBorder(javax.swing.BorderFactory.createTitledBorder("Parking State Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		GridBagLayout gbl_topPanel = new GridBagLayout();
		gbl_topPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_topPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_topPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_topPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		topPanel.setLayout(gbl_topPanel);
	
		add(topPanel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		menuBar.setBounds(0, 0, 132, 22);
		GridBagConstraints gbc_menuBar = new GridBagConstraints();
		gbc_menuBar.insets = new Insets(0, 0, 5, 5);
		gbc_menuBar.fill = GridBagConstraints.HORIZONTAL;
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
			importItem4 = new JMenuItem("Comprobar tipos de vehÃ­culo");
			importItem5 = new JMenuItem("Comprobar tipos de cliente");
			importItem6 = new JMenuItem("Comprobar trabajadores");
			
			fileJMenu.add(importItem);
			fileJMenu.add(importItem1);
			fileJMenu.add(importItem2);
			fileJMenu.add(importItem3);
			fileJMenu.add(importItem4);
			fileJMenu.add(importItem5);
			fileJMenu.add(importItem6);
			
			importItem6.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
//					tablaOrdinarios.setVisible(false);
//					tablaSubscritos.setVisible(false);
					Vector<String> cabeceras = new Vector<>(Arrays.asList("Usuario","DNI","Contraseña","Email", "Fecha inicio", "Salario"));
					DefaultTableModel modelo2 = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
					
					Map<String,Trabajador> mapaTrabajador = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
					for (Map.Entry<String, Trabajador> entry : mapaTrabajador.entrySet()) {
					    Trabajador trabajador = entry.getValue();
					    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					    modelo2.addRow(new Object[] {trabajador.getNombreUsuario(), trabajador.getDni(),trabajador.getPassword(), trabajador.getEmail(), sdf.format(new Date(trabajador.getFechaComienzo())), 
					    		Double.toString(trabajador.getSalario())
					    });
					}			
		
//					tablaTrabajadores = new JScrollPane(new JTable(modelo));
					table2.setModel(modelo2);
//					scroll = new JScrollPane(table);
					scrollTrabajadores.setBounds(25, 25, 500, 100);
//					middlePanel.add(scrollTrabajadores);
					scrollTrabajadores.setVisible(true);
					scrollOrdinarios.setVisible(false);
					scrollSubscritos.setVisible(false);
				}
			});
			
			// aqui en principo solo es hasta comprobar plazas porque se trata de una empleado pero para poder
//			comprobar ya que el instance of no funciona bien lo meto de momento.
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
				
					frame.dispose();
					ServicioPersistenciaBD.getInstance().disconnect();
					System.exit(0);
				
			}
		});

		JPanel middlePanel = new JPanel();

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Prueba");
		middlePanel.setLayout(null);
		add(middlePanel);
		
		JButton btnBajaAbonado = new JButton("DAR DE BAJA");
		
		btnBajaAbonado.setBounds(433, 108, 112, 23);
		btnBajaAbonado.setVisible(false);
		middlePanel.add(btnBajaAbonado);
		
		table = new JTable();
		scrollOrdinarios = new JScrollPane(table);
		middlePanel.add(scrollOrdinarios);
		table1 = new JTable();
		scrollSubscritos = new JScrollPane(table1);
		middlePanel.add(scrollSubscritos);
		table2 = new JTable();
		scrollTrabajadores = new JScrollPane(table2);
		middlePanel.add(scrollTrabajadores);
		scrollOrdinarios.setVisible(true);
		scrollSubscritos.setVisible(false);
		scrollTrabajadores.setVisible(false);
		
		importItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				tablaSubscritos.setVisible(false);
//				tablaTrabajadores.setVisible(false);
				Vector<String> cabeceras = new Vector<>(Arrays.asList("Matricula","Tipo Vehiculo","Tarifa","Fecha de Entrada"));
				DefaultTableModel modelo = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
				
				Map<String,ClienteOrdinario> mapaOrdinarios = ServicioPersistenciaBD.getInstance().ordinariosSelect();
				for (Map.Entry<String, ClienteOrdinario> entry : mapaOrdinarios.entrySet()) {
				    ClienteOrdinario ordinario = entry.getValue();
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				    modelo.addRow(new Object[] {ordinario.getMatricula(), ordinario.getTipoVehiculo(),ordinario.getTarifa(), sdf.format(new Date(ordinario.getFechaEntrada()))
				    });
				}			
	
//				tablaOrdinarios = new JScrollPane(new JTable(modelo));
//				tablaOrdinarios.setBounds(25, 25, 500, 100);
//				middlePanel.add(tablaOrdinarios);
				
				table.setModel(modelo);
//				scroll = new JScrollPane(table);
				scrollOrdinarios.setBounds(25, 25, 500, 100);
				scrollOrdinarios.setVisible(true);
				scrollSubscritos.setVisible(false);
				scrollTrabajadores.setVisible(false);
			}
		});
		
		importItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				tablaOrdinarios.setVisible(false);
//				tablaTrabajadores.setVisible(false);
				Vector<String> cabeceras = new Vector<>(Arrays.asList("Matricula","Tipo Vehiculo","Cuota","Fecha de Entrada"));
				DefaultTableModel modelo1 = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
				
				Map<String,ClienteSubscrito> mapaSubscritos = ServicioPersistenciaBD.getInstance().subscritosSelect();
				for (Map.Entry<String, ClienteSubscrito> entry : mapaSubscritos.entrySet()) {
				    ClienteSubscrito subscrito = entry.getValue();
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				    modelo1.addRow(new Object[] {subscrito.getMatricula(), subscrito.getTipoVehiculo(),subscrito.getPrecioCuota(), sdf.format(new Date(subscrito.getFechaEntrada()))
				    });
				}			
	
//				tablaSubscritos = new JScrollPane(new JTable(modelo));
				table1.setModel(modelo1);
//				scroll = new JScrollPane(table);
				scrollSubscritos.setBounds(25, 25, 500, 100);
				middlePanel.add(scrollSubscritos);
				scrollSubscritos.setVisible(true);
				scrollOrdinarios.setVisible(false);
				scrollTrabajadores.setVisible(false);
			}
		});
		
//		importItem6.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				Vector<String> cabeceras = new Vector<>(Arrays.asList("Usuario","DNI","Contraseña","Email", "Fecha inicio", "Salario"));
//				DefaultTableModel modelo2 = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
//				
//				Map<String,Trabajador> mapaTrabajador = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
//				for (Map.Entry<String, Trabajador> entry : mapaTrabajador.entrySet()) {
//				    Trabajador trabajador = entry.getValue();
//				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//				    modelo2.addRow(new Object[] {trabajador.getNombreUsuario(), trabajador.getDni(),trabajador.getPassword(), trabajador.getEmail(), sdf.format(new Date(trabajador.getFechaComienzo())), 
//				    		Double.toString(trabajador.getSalario())
//				    });
//				}			
//	
//
//				table2.setModel(modelo2);
//
//				scrollTrabajadores.setBounds(25, 25, 500, 100);
//				if (trabajador.getDni().equals("12345678A")) {
//					middlePanel.add(scrollTrabajadores);
//				}
//				
////				middlePanel.add(scrollTrabajadores);
//				scrollTrabajadores.setVisible(true);
//				scrollOrdinarios.setVisible(false);
//				scrollSubscritos.setVisible(false);
//			}
//		});
		
		if (trabajador.getDni().equals("12345678A")) {
			middlePanel.add(scrollTrabajadores);
		}
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		bottomPanel.add(btnVolver);
		add(bottomPanel);
	}
}

