package frontend.paneles.acceso.trabajador.acciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.servicios.ServicioPersistenciaBD;

public class BajaSubscribersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnBaja;

	@SuppressWarnings("unchecked")
	public BajaSubscribersPanel(JFrame frame, JPanel panel) {

		setBorder(javax.swing.BorderFactory.createTitledBorder("Baja Subscriber Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel labelWellcoming = new JLabel("LISTA DE ABONADOS: ");
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);
		add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(null);
		
//		List<ClienteSubscrito> listaSubscritos = new ArrayList<>();
//		JList<ClienteSubscrito> miLista = new JList<>();
		
		JTable tableSubscritos = new JTable();
		JScrollPane scrollSubscritos = new JScrollPane(tableSubscritos);
		scrollSubscritos.setBounds(25, 25, 500, 100);
		
		Vector<String> cabeceras = new Vector<>(Arrays.asList("Matricula","Tipo Vehiculo","Cuota","Fecha de Entrada"));
		DefaultTableModel modeloSubscritos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);	
		
		Map<String,ClienteSubscrito> mapaSubscritos = ServicioPersistenciaBD.getInstance().subscritosSelect();
		for (Map.Entry<String, ClienteSubscrito> entry : mapaSubscritos.entrySet()) {
		    ClienteSubscrito subscrito = entry.getValue();
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    modeloSubscritos.addRow(new Object[] {subscrito.getMatricula(), subscrito.getTipoVehiculo(),subscrito.getPrecioCuota(), sdf.format(new Date(subscrito.getFechaEntrada()))
		    });
		}	
		tableSubscritos.setModel(modeloSubscritos);
		
		tableSubscritos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (isSelected) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.WHITE);
				}
				return c;
			}
		});
		
		tableSubscritos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
			if (tableSubscritos.getSelectedRow() >= 0) {
			btnBaja.setEnabled(true);
				} else {
					btnBaja.setEnabled(false);
				}
			}
		});
		
		
		tableSubscritos.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
//				int selectedRow = tableSubscritos.getSelectedRow();
//				if (selectedRow >= 0) {
//					String matricula = (String) tableSubscritos.getValueAt(selectedRow, 0);
//					ServicioPersistenciaBD.getInstance().subscritoDelete(matricula);
//					modeloSubscritos.removeRow(selectedRow);
//				}
			}
		});

		
		middlePanel.add(scrollSubscritos);

		add(middlePanel);

		JPanel bottomPanel = new JPanel();
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(340, 67, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		bottomPanel.setLayout(null);
		bottomPanel.add(btnVolver);
		btnBaja = new JButton("DAR DE BAJA");
		btnBaja.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = tableSubscritos.getSelectedRow();
				if (selectedRow >= 0) {
					String matricula = (String) tableSubscritos.getValueAt(selectedRow, 0);
					ServicioPersistenciaBD.getInstance().subscritoDelete(matricula);
					modeloSubscritos.removeRow(selectedRow);
				}
			}
		});
		btnBaja.setBounds(121, 67, 130, 23);
		btnBaja.setEnabled(false);
		bottomPanel.add(btnBaja);
		add(bottomPanel);

	}
	
}
