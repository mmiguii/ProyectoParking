package frontend.panelesAEliminar;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class prueba extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					prueba frame = new prueba();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public prueba() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(227, 124, 287, 254);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnCargar = new JButton("CARGAR");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarTablaBaseDeDatos(1);
			}
		});
		btnCargar.setBounds(61, 229, 89, 23);
		contentPane.add(btnCargar);

		
	}
	
	
	
	public void cargarTablaBaseDeDatos(int numeroPlanta) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Parking.db");
			java.sql.Statement stmt = conn.createStatement();
			String query = "select numero_plaza,numero_planta,estado_plaza, tipo_plaza from plazas where numero_planta=2";
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			
			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for (int i=0; i<cols; i++) {
				colName[i] = rsmd.getColumnName(i+1);
				model.setColumnIdentifiers(colName);
			}
//			String num,numPlanta,estado,tipo,tiempo,importe;
			String num,numPlanta,estado,tipo;
			while (rs.next()) {
				num = rs.getString(1);
				numPlanta = rs.getString(2);
				estado = rs.getString(3);
				tipo = rs.getString(4);
//				tiempo = rs.getString(5);
//				importe = rs.getString(6);
//				String[] row = {num,numPlanta,estado,tipo,tiempo,importe};
				String[] row = {num,numPlanta,estado,tipo};
				model.addRow(row);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
}
