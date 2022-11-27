package frontend.paneles.clientes.ordinarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import backend.clases.clientes.ClienteOrdinario;
import backend.servicios.ServicioPersistenciaBD;
import java.awt.Insets;

public class PanelClienteOrdinario extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private static Statement stmt;
	private JTable table;

	public PanelClienteOrdinario(final JFrame frame, JPanel panel, ClienteOrdinario ordinario) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setSize(640, 480);
//		setBounds(10, 10, 567, 448);

//		setBackground(Color.PINK);

		setBorder(javax.swing.BorderFactory.createTitledBorder("Ordinary Customer"));
		this.setLayout(new GridLayout(3, 1));
//
//		setVisible(true);
//		
//		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		JLabel incomingLabel = new JLabel("Bienvenido al Parking usuario: ");
		incomingLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel plateLabel = new JLabel(ordinario.getMatricula());

//		GridBagConstraints gbc_incomingLabel = new GridBagConstraints();
//		gbc_incomingLabel.insets = new Insets(0, 0, 5, 5);
//		gbc_incomingLabel.gridx = 0;
//		gbc_incomingLabel.gridy = 0;
//		topPanel.add(incomingLabel, gbc_incomingLabel);
//		GridBagConstraints gbc_plateLabel = new GridBagConstraints();
//		gbc_plateLabel.insets = new Insets(0, 0, 5, 5);
//		gbc_plateLabel.gridx = 1;
//		gbc_plateLabel.gridy = 0;
//		topPanel.add(plateLabel, gbc_plateLabel);

		topPanel.add(incomingLabel);
		topPanel.add(plateLabel);

//		add(topPanel);

		JLabel actualTime = new JLabel("Hora de ingreso en el Parking ");
		actualTime.setFont(new Font("Tahoma", Font.PLAIN, 14));

//		GridBagConstraints gbc_actualTime = new GridBagConstraints();
//		gbc_actualTime.insets = new Insets(0, 0, 0, 5);
//		gbc_actualTime.gridx = 0;
//		gbc_actualTime.gridy = 1;
//		topPanel.add(actualTime, gbc_actualTime);

		JLabel hora = new JLabel();

//		GridBagConstraints gbc_hora = new GridBagConstraints();
//		gbc_hora.insets = new Insets(0, 0, 0, 5);
//		gbc_hora.gridx = 1;
//		gbc_hora.gridy = 1;
//		topPanel.add(hora, gbc_hora);

		topPanel.add(actualTime);
		topPanel.add(hora);

//		topPanel.setBackground(Color.CYAN);
		add(topPanel);

		JPanel middlePanel = new JPanel();
//		GridBagLayout gbl_middlePanel = new GridBagLayout();
//		gbl_middlePanel.rowWeights = new double[]{1.0, 0.0};
//		gbl_middlePanel.columnWeights = new double[]{1.0, 0.0};
//		middlePanel.setLayout(gbl_middlePanel);
//		middlePanel.setBackground(Color.PINK);

		add(middlePanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(117, 11, 355, 118);
		middlePanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnCargarTabla = new JButton("Cargar Tabla");
		btnCargarTabla.setBounds(10, 11, 95, 23);
		btnCargarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarTablaBaseDeDatos(1);	
			}
			
		});
		
		
		middlePanel.setLayout(null);
//	    DefaultTableModel modelo = new DefaultTableModel();
//		ServicioPersistenciaBD BD = new ServicioPersistenciaBD();
//		// BD.connect("C:\\Users\\Alumno\\git\\ProyectoParking\\ParkingProject\\src\\Parking.db");
//
//		BD.connect();
				
//		table1.setModel(modelo);
//		middlePanel.add(table1);
						GridBagConstraints gbc_btnCargarTabla = new GridBagConstraints();
						gbc_btnCargarTabla.gridx = 1;
						gbc_btnCargarTabla.gridy = 1;
						middlePanel.add(btnCargarTabla);
						

//		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
//		bottomPanel.setBackground(Color.GREEN);

		JButton btnPlanta2 = new JButton("Segunda planta");
		btnPlanta2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton btnBack = new JButton("VOLVER");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true); // Pasamos la referencia del panel y una vez finalicemos con este panel, vuelvo
										// hacer visible el panel que anteriormente estaba visible.
				setVisible(false);
			}
		});

		bottomPanel.add(btnPlanta2, new GridBagConstraints());
		bottomPanel.add(btnBack, new GridBagConstraints());

		add(bottomPanel);
//		
//		

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

// BOTON ACCEDER
//
////ReaderWriter rw = new ReaderWriter();
//ArrayList<ClienteOrdinario> ordinaryCustomers = rw.ordinarioReader();
//
//ClienteOrdinario nOrdinaryCustomer = new ClienteOrdinario();
//
//nOrdinaryCustomer.setMatricula(plateTextField.getText());
//
//// Asignamos el valor del tipo de vehiculo en funcion de lo que el usuario
//// introduzca y le anadimos la tarifa
//String tipoVehiculo = radioButtonGroup.getSelection().getActionCommand();
//double tarifa = 0.00;
//if (radioButton1.isSelected()) {
//	tipoVehiculo = "1";
//	tarifa = 1.00;
//} else if (radioButton2.isSelected()) {
//	tipoVehiculo = "2";
//	tarifa = 2.00;
//} else if (radioButton3.isSelected()) {
//	tipoVehiculo = "3";
//	tarifa = 3.00;
//}
////System.out.println(tipoVehiculo);
//nOrdinaryCustomer.setTipoVehiculo(tipoVehiculo); // Lee la opcion seleccionada
////System.out.println(tarifa);
//nOrdinaryCustomer.setTarifa(tarifa); // Aqui debemos establecer la tarifa en cuestion
//
////ZonedDateTime now = ZonedDateTime.now();
////DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
////String initialTime = formatter.format(now);
////ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(initialTime, formatter);
////nOrdinaryCustomer.setHoraDeEntrada(zdtWithZoneOffset);
//////System.out.println("Hora de inicio de la estancia: " + initialTime);
////
////ordinaryCustomers.add(nOrdinaryCustomer); // Anado el nuevo cliente
////rw.ordinaryCustomerWriter(ordinaryCustomers); // Escribimos el cliente BD
//
////OrdinaryCustomerPanel ordinaryPanel = new OrdinaryCustomerPanel(plateTextField.getText(), initialTime);
////add(ordinaryPanel);
////topPanel.setVisible(false);
////middlePanel.setVisible(false);
////bottomPanel.setVisible(false);
////
////ordinaryPanel.setVisible(true);
