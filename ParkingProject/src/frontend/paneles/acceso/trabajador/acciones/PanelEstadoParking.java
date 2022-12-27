package frontend.paneles.acceso.trabajador.acciones;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import backend.servicios.ServicioPersistenciaBD;

public class PanelEstadoParking extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelEstadoParking(JFrame frame, JPanel panel) {

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
		
		JMenu fileJMenu = new JMenu("Fichero");
		menuBar.add(fileJMenu);
		
		JMenuItem importItem = new JMenuItem("Importar...");
		fileJMenu.add(importItem);
		
		importItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros CSV", "csv");
				fileChooser.setFileFilter(filter); 
				
				int status = fileChooser.showOpenDialog(PanelEstadoParking.this);
				if (status == JFileChooser.APPROVE_OPTION) {					
					try {

//						List<Plaza> plazas = readFromFile(MyListModel.getPlazas(), fileChooser.getSelectedFile());
//					
//						
////						updateUI();
////						
						JOptionPane.showMessageDialog(PanelEstadoParking.this, "Datos importados correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(PanelEstadoParking.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
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
		middlePanel.setLayout(new GridBagLayout());

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Prueba");
		JTable table = new JTable();
		table.setModel(modelo);
		middlePanel.add(table);

		add(middlePanel);

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
