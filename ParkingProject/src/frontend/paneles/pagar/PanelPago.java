package frontend.paneles.pagar;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.Usuario;
import backend.servicios.ServicioPersistenciaBD;

public class PanelPago extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldFechaEntrada;
	private JTextField textFieldFechaSalida;
	private JTextField textFieldTiempoTranscurrido;
	private JTextField textFieldTipoVehiculo;
	private JTextField textFieldImporteTotal;

	private ServicioPersistenciaBD servicio;
	private JTextField textFieldTipoUsuario;

	public PanelPago(JFrame frame, JPanel panel, Usuario usuario, String horaActual) {
		setBorder(javax.swing.BorderFactory.createTitledBorder("Payment Panel"));
		setBounds(10, 10, 567, 448);
		setLayout(null);

		servicio = new ServicioPersistenciaBD();
		List<Plaza> plazas = servicio.plazasSelect();
		

		

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

		textFieldFechaEntrada = new JTextField(formatter.format(new Date(usuario.getFechaEntrada()).getTime()));
		textFieldFechaEntrada.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFechaEntrada.setBounds(238, 122, 175, 26);
		add(textFieldFechaEntrada);
		textFieldFechaEntrada.setColumns(10);
		textFieldFechaEntrada.setEditable(false);

		if (usuario instanceof ClienteOrdinario) {
			textFieldFechaSalida = new JTextField(horaActual);
		} else {
			textFieldFechaSalida = new JTextField(formatter.format(new Date(usuario.getFechaSalida()).getTime()));
		}

		textFieldFechaSalida.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFechaSalida.setColumns(10);
		textFieldFechaSalida.setBounds(238, 169, 175, 26);
		add(textFieldFechaSalida);
		textFieldFechaSalida.setEditable(false);

		try {

			Date date = formatter.parse(textFieldFechaSalida.getText());

			if (usuario instanceof ClienteOrdinario) {

				long dOrd = formatter.parse(horaActual).getTime();
				long tOrd = new Date(dOrd).getTime() - new Date(usuario.getFechaEntrada()).getTime();
				long hours = TimeUnit.MILLISECONDS.toHours(tOrd);
				long min = TimeUnit.MILLISECONDS.toMinutes(tOrd)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tOrd));
				long sec = TimeUnit.MILLISECONDS.toSeconds(tOrd)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tOrd));
				textFieldTiempoTranscurrido = new JTextField(String.format("%02dh %02dm %02ds", hours, min, sec));
				textFieldTiempoTranscurrido.setHorizontalAlignment(SwingConstants.CENTER);
				textFieldTiempoTranscurrido.setColumns(10);
				textFieldTiempoTranscurrido.setBounds(238, 204, 175, 26);
				add(textFieldTiempoTranscurrido);
				textFieldTiempoTranscurrido.setEditable(false);

				double tarifa;
				if (usuario.getTipoVehiculo().equals("ordinario")) {
					tarifa = 0.50;
				} else if (usuario.getTipoVehiculo().equals("electrico")) {
					tarifa = 0.40;
				} else {
					tarifa = 0.30;
				}

				double importe = 0.00;
				if (usuario instanceof ClienteOrdinario) {
					importe = tarifa * TimeUnit.MILLISECONDS.toMinutes(tOrd);
				} else {
					importe = tarifa * TimeUnit.MILLISECONDS.toMinutes(tOrd);
				}

				textFieldImporteTotal = new JTextField(importe + " €");
				textFieldImporteTotal.setHorizontalAlignment(SwingConstants.CENTER);
				textFieldImporteTotal.setColumns(10);
				textFieldImporteTotal.setBounds(238, 294, 175, 26);
				add(textFieldImporteTotal);
				textFieldImporteTotal.setEditable(false);

			} else {
				long time = new Date(usuario.getFechaSalida()).getTime()
						- new Date(usuario.getFechaEntrada()).getTime();
				long hours = TimeUnit.MILLISECONDS.toHours(time);
				long min = TimeUnit.MILLISECONDS.toMinutes(time)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
				long sec = TimeUnit.MILLISECONDS.toSeconds(time)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));

				textFieldTiempoTranscurrido = new JTextField(String.format("%02dh %02dm %02ds", hours, min, sec));
				textFieldTiempoTranscurrido.setHorizontalAlignment(SwingConstants.CENTER);
				textFieldTiempoTranscurrido.setColumns(10);
				textFieldTiempoTranscurrido.setBounds(238, 204, 175, 26);
				add(textFieldTiempoTranscurrido);
				textFieldTiempoTranscurrido.setEditable(false);

				double tarifa;
				if (usuario.getTipoVehiculo().equals("ordinario")) {
					tarifa = 0.50;
				} else if (usuario.getTipoVehiculo().equals("electrico")) {
					tarifa = 0.40;
				} else {
					tarifa = 0.30;
				}

				double importe = 0.00;
				if (usuario instanceof ClienteOrdinario) {
					importe = tarifa * TimeUnit.MILLISECONDS.toMinutes(time);
				} else {
					importe = tarifa * TimeUnit.MILLISECONDS.toMinutes(time);
				}

				textFieldImporteTotal = new JTextField(importe + " €");
				textFieldImporteTotal.setHorizontalAlignment(SwingConstants.CENTER);
				textFieldImporteTotal.setColumns(10);
				textFieldImporteTotal.setBounds(238, 294, 175, 26);
				add(textFieldImporteTotal);
				textFieldImporteTotal.setEditable(false);

			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		textFieldTipoVehiculo = new JTextField(usuario.getTipoVehiculo());
		textFieldTipoVehiculo.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTipoVehiculo.setColumns(10);
		textFieldTipoVehiculo.setBounds(238, 248, 175, 26);
		add(textFieldTipoVehiculo);
		textFieldTipoVehiculo.setEditable(false);

		JLabel lblTitulo = new JLabel("PAGO DE PARKING");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(214, 41, 138, 49);
		add(lblTitulo);

		JLabel lblHoraEntrada = new JLabel("Hora de entrada");
		lblHoraEntrada.setBounds(90, 128, 132, 14);
		add(lblHoraEntrada);

		JLabel lblHoraDeSalida = new JLabel("Hora de salida");
		lblHoraDeSalida.setBounds(90, 169, 112, 14);
		add(lblHoraDeSalida);

		JLabel lblTiempoTranscurrido = new JLabel("Tiempo transcurrido");
		lblTiempoTranscurrido.setBounds(90, 210, 144, 14);
		add(lblTiempoTranscurrido);

		JLabel lblTipoDeCliente = new JLabel("Tipo de vehiculo");
		lblTipoDeCliente.setBounds(90, 254, 144, 14);
		add(lblTipoDeCliente);

		JLabel lblImporteTotal = new JLabel("Importe Total");
		lblImporteTotal.setBounds(90, 300, 144, 14);
		add(lblImporteTotal);

		JButton btnPagar = new JButton("PAGAR");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int opcion = JOptionPane.showConfirmDialog(null, "Confirmar transaccion.", "Confirmacion",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opcion == 0) {
					if (usuario instanceof ClienteOrdinario) {
						
						int numeroPlaza = 0;
						for (Plaza p : plazas) {
							if (p.getMatricula() == usuario.getMatricula()) {
								numeroPlaza = p.getNumeroPlaza();
								
							}
						}
						
						servicio.updateDel(servicio.getPlaza(usuario.getMatricula()),"Disponible");
						servicio.ordinarioDelete(usuario.getMatricula());
						PanelSalida panel = new PanelSalida(frame);
						frame.getContentPane().add(panel);
						setVisible(false);
						panel.setVisible(true);
					} else {
						servicio.subscritoDelete(usuario.getMatricula());
						// servicio.subscritoDelete(usuario.getMatricula());
						PanelSalida panel = new PanelSalida(frame);
						frame.getContentPane().add(panel);
						setVisible(false);
						panel.setVisible(true);
					}
				} else {
					// No sucede nada
				}

			}
		});
		btnPagar.setBounds(186, 364, 89, 23);
		add(btnPagar);

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(415, 364, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				panel.setVisible(true); // Pasamos la referencia del panel y una vez finalicemos con este panel, vuelvo
										// hacer visible el panel que anteriormente estaba visible.
				setVisible(false);
			}
		});

		add(btnVolver);

		JLabel lblTipoUsuario = new JLabel("Tipo de usuario");
		lblTipoUsuario.setBounds(90, 95, 132, 14);
		add(lblTipoUsuario);

		String tipoUsuario = "";
		if (usuario instanceof ClienteOrdinario) {
			tipoUsuario = "Cliente ordinario";
		} else {
			tipoUsuario = "Cliente subscrito";
		}

		textFieldTipoUsuario = new JTextField(tipoUsuario);
		textFieldTipoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTipoUsuario.setEditable(false);
		textFieldTipoUsuario.setColumns(10);
		textFieldTipoUsuario.setBounds(238, 89, 175, 26);
		add(textFieldTipoUsuario);

	}
}
