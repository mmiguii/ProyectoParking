package frontend.paneles.clientes.subscritos;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.salida.PanelSalida;

public class PanelClienteSubscrito extends JPanel {

	private static final long serialVersionUID = 1L;

	ServicioPersistenciaBD servicio;
	private JPanel bottomPanel;
	private JPanel middlePanel;
	private JPanel topPanel;

	public PanelClienteSubscrito(JFrame frame, JPanel panel, ClienteSubscrito subscrito) {

		servicio = new ServicioPersistenciaBD();

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

		setBorder(javax.swing.BorderFactory.createTitledBorder("Subscriber Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));

		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		JLabel lblTextoSeleccion = new JLabel("SELECCIONA EL TIPO DE ABONO QUE DESEA");
		lblTextoSeleccion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.add(lblTextoSeleccion);
		add(topPanel);

		middlePanel = new JPanel(new GridLayout(1, 3));

		JPanel leftMiddlePanel = new JPanel();
		leftMiddlePanel.setLayout(new GridBagLayout());
		JButton btnSemana = new JButton("SEMANAL");
		btnSemana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				Ordinario: 0.5, Electrico: 0.4, Minusvalido: 0.3
				double tipoCuota = Double.parseDouble(subscrito.getTipoCuota());
//				Precio cuota: (1+tipoTarifa) * dias que le corresponda
				double precioCuota = 7 * (1 + tipoCuota);
				subscrito.setPrecioCuota(precioCuota);

//				ME FALTA NUMERO DE PLAZA OCUPADA CORREGIRLO
				Plaza p = new Plaza(10, false, subscrito.getTipoVehiculo());
				subscrito.setPlazaOcupada(p);
//				HASTA AQUI CORREGIR

				// Obtengo fecha de salida maxima
				long fechaEntrada = subscrito.getFechaEntrada();
				Date d = new Date(fechaEntrada);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.add(Calendar.DATE, 7); // Anado 7 dias
				d = c.getTime();
				long dateInLong = d.getTime();
				subscrito.setFechaSalida(dateInLong);

				servicio.subscritoInsert(subscrito);

				PanelSalida panel = new PanelSalida(frame);
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				topPanel.setVisible(false);
				middlePanel.setVisible(false);
				bottomPanel.setVisible(false);
				setVisible(false);

			}
		});
		leftMiddlePanel.add(btnSemana);

		JPanel middleMiddlePanel = new JPanel();
		middleMiddlePanel.setLayout(new GridBagLayout());
		JButton btnMes = new JButton("MENSUAL");
		btnMes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				Ordinario: 0.5, Electrico: 0.4, Minusvalido: 0.3
				double tipoCuota = Double.parseDouble(subscrito.getTipoCuota());
//				Precio cuota: (1+tipoTarifa) * dias que le corresponda
				double precioCuota = 30 * (1 + tipoCuota);
				subscrito.setPrecioCuota(precioCuota);

//				ME FALTA NUMERO DE PLAZA OCUPADA CORREGIRLO
				Plaza p = new Plaza(110, false, subscrito.getTipoVehiculo());
				subscrito.setPlazaOcupada(p);
//				HASTA AQUI CORREGIR

				// Obtengo fecha de salida maxima
				long fechaEntrada = subscrito.getFechaEntrada();
				Date d = new Date(fechaEntrada);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.add(Calendar.MONTH, 1); // Anado 1 mes
				d = c.getTime();
				long dateInLong = d.getTime();
				subscrito.setFechaSalida(dateInLong);

				servicio.subscritoInsert(subscrito);

				PanelSalida panel = new PanelSalida(frame);
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				topPanel.setVisible(false);
				middlePanel.setVisible(false);
				bottomPanel.setVisible(false);
				setVisible(false);
			}
		});
		middleMiddlePanel.add(btnMes);

		JPanel rightMiddlePanel = new JPanel();
		rightMiddlePanel.setLayout(new GridBagLayout());
		JButton btnAno = new JButton("ANUAL");
		btnAno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Ordinario: 0.5, Electrico: 0.4, Minusvalido: 0.3
				double tipoCuota = Double.parseDouble(subscrito.getTipoCuota());
//				Precio cuota: (1+tipoTarifa) * dias que le corresponda
				double precioCuota = 30 * (1 + tipoCuota);
				subscrito.setPrecioCuota(precioCuota);

//				ME FALTA NUMERO DE PLAZA OCUPADA CORREGIRLO
				Plaza p = new Plaza(210, false, subscrito.getTipoVehiculo());
				subscrito.setPlazaOcupada(p);
//				HASTA AQUI CORREGIR

				// Obtengo fecha de salida maxima
				long fechaEntrada = subscrito.getFechaEntrada();
				Date d = new Date(fechaEntrada);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.add(Calendar.YEAR, 1); // anado 1 ano
				d = c.getTime();
				long dateInLong = d.getTime();
				subscrito.setFechaSalida(dateInLong);

				servicio.subscritoInsert(subscrito);

				PanelSalida panel = new PanelSalida(frame);
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				topPanel.setVisible(false);
				middlePanel.setVisible(false);
				bottomPanel.setVisible(false);
				setVisible(false);
			}
		});
		rightMiddlePanel.add(btnAno);

		middlePanel.add(leftMiddlePanel);
		middlePanel.add(middleMiddlePanel);
		middlePanel.add(rightMiddlePanel);
		add(middlePanel);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true); // Pasamos la referencia del panel y una vez finalicemos con este panel, vuelvo
										// hacer visible el panel que anteriormente estaba visible.
				setVisible(false);
			}
		});

		bottomPanel.add(btnVolver);
		add(bottomPanel);
	}

}
