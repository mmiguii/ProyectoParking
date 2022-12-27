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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import backend.clases.personas.personal.Empleado;
import backend.clases.personas.personal.Manager;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.acceso.trabajador.PanelTrabajador;

public class PanelEstadoParking extends JPanel {

	private static final long serialVersionUID = 1L;

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

		
		if(trabajador instanceof Empleado) {
			importItem = new JMenuItem("Consultar clientes ordinarios");
			importItem1 = new JMenuItem("Consultar clientes subscritos");
			importItem2 = new JMenuItem("Comprobar plazas");
			
			fileJMenu.add(importItem);
			fileJMenu.add(importItem1);
			fileJMenu.add(importItem2);
		} else if (trabajador instanceof Manager){
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
		
		importItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Vector<String> cabeceras = new Vector<>(Arrays.asList("Matricula","Tipo Vehiculo","Tarifa","Fecha de Entrada"));
				DefaultTableModel modelo = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
				
				Map<String,ClienteOrdinario> mapaOrdinarios = ServicioPersistenciaBD.getInstance().ordinariosSelect();
				for (Map.Entry<String, ClienteOrdinario> entry : mapaOrdinarios.entrySet()) {
				    ClienteOrdinario ordinario = entry.getValue();
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				    modelo.addRow(new Object[] {ordinario.getMatricula(), ordinario.getTipoVehiculo(),ordinario.getTarifa(), sdf.format(new Date(ordinario.getFechaEntrada()))
				    });
				}			
	
				JScrollPane tablaOrdinarios = new JScrollPane(new JTable(modelo));
				tablaOrdinarios.setBounds(25, 25, 500, 100);
				middlePanel.add(tablaOrdinarios);
			}
		});
		
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

