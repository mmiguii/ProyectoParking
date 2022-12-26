package frontend.paneles.acceso.clientes.pago;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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
	private JTextField textFieldTipoUsuario;
	private DateFormat formatter;
	private Usuario usuario;
	private Plaza plaza;

	private static Logger logger = Logger.getLogger(PanelPago.class.getName());

	public PanelPago(JFrame frame, JPanel panel, Usuario usuario, Plaza plaza, String horaActual) {
		setBorder(javax.swing.BorderFactory.createTitledBorder("Panel Pago"));
		setBounds(10, 10, 567, 448);
		setLayout(null);

		ServicioPersistenciaBD.getInstance().connect("Parking.db");

		formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
	
		this.usuario = usuario;
		this.plaza = plaza;


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

		// Crea el campo de texto textFieldTipoUsuario y establece sus propiedades.
		textFieldTipoUsuario = new JTextField();
		textFieldTipoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTipoUsuario.setBounds(238, 89, 175, 26);
		textFieldTipoUsuario.setColumns(10);
		add(textFieldTipoUsuario);
		textFieldTipoUsuario.setEditable(false);
		String tipoUsuario = (usuario instanceof ClienteOrdinario) ? "Cliente ordinario" : "Cliente subscrito";
		textFieldTipoUsuario.setText(tipoUsuario);

		// Crea el campo de texto textFieldFechaEntrada y establece sus propiedades.
		textFieldFechaEntrada = new JTextField();
		textFieldFechaEntrada.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFechaEntrada.setBounds(238, 122, 175, 26);
		textFieldFechaEntrada.setColumns(10);
		add(textFieldFechaEntrada);
		textFieldFechaEntrada.setEditable(false);
		textFieldFechaEntrada.setText(formatter.format(new Date(usuario.getFechaEntrada()).getTime()));

		// Crea el campo de texto textFieldFechaSalida y establece sus propiedades.
		textFieldFechaSalida = new JTextField();
		textFieldFechaSalida.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldFechaSalida.setColumns(10);
		textFieldFechaSalida.setBounds(238, 169, 175, 26);
		add(textFieldFechaSalida);
		textFieldFechaSalida.setEditable(false);
		if (usuario instanceof ClienteOrdinario) {
			textFieldFechaSalida.setText(horaActual);
		} else {
			textFieldFechaSalida.setText(formatter.format(new Date(usuario.getFechaSalida()).getTime()));
		}

		try {

			// Crea el campo de texto textFieldTiempoTranscurrido y establece sus
			// propiedades.
			textFieldTiempoTranscurrido = new JTextField();
			textFieldTiempoTranscurrido.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTiempoTranscurrido.setColumns(10);
			textFieldTiempoTranscurrido.setBounds(238, 204, 175, 26);
			add(textFieldTiempoTranscurrido);
			textFieldTiempoTranscurrido.setEditable(false);

			// Crea el campo de texto textFieldImporteTotal y establece sus propiedades.
			textFieldImporteTotal = new JTextField();
			textFieldImporteTotal.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldImporteTotal.setColumns(10);
			textFieldImporteTotal.setBounds(238, 294, 175, 26);
			add(textFieldImporteTotal);
			textFieldImporteTotal.setEditable(false);

			// Calcula el tiempo transcurrido.
			long time = (usuario instanceof ClienteOrdinario)
					? formatter.parse(horaActual).getTime() - new Date(usuario.getFechaEntrada()).getTime()
					: new Date(usuario.getFechaSalida()).getTime() - new Date(usuario.getFechaEntrada()).getTime();
			long hours = TimeUnit.MILLISECONDS.toHours(time);
			long min = TimeUnit.MILLISECONDS.toMinutes(time)
					- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
			long sec = TimeUnit.MILLISECONDS.toSeconds(time)
					- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
			textFieldTiempoTranscurrido.setText(String.format("%02dh %02dm %02ds", hours, min, sec));

			// Obtiene la tarifa a aplicar y calcula el importe total.
			double tarifa = usuario.getTipoVehiculo().equals("Ordinario") ? 0.50
					: (usuario.getTipoVehiculo().equals("Electrico") ? 0.40 : 0.30);
			double importe = tarifa * TimeUnit.MILLISECONDS.toMinutes(time);
			textFieldImporteTotal.setText(String.format("%.2f €", importe));

		} catch (ParseException e1) {	
			logger.severe(String.format("%s %s", e1.getMessage(), e1.getCause().getMessage()));
		}

		// Crea el campo de texto textFieldTipoVehiculo y establece sus propiedades.
		textFieldTipoVehiculo = new JTextField();
		textFieldTipoVehiculo.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTipoVehiculo.setColumns(10);
		textFieldTipoVehiculo.setBounds(238, 248, 175, 26);
		add(textFieldTipoVehiculo);
		textFieldTipoVehiculo.setEditable(false);
		textFieldTipoVehiculo.setText(usuario.getTipoVehiculo());

		JButton btnPagar = new JButton("PAGAR");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int opcion = JOptionPane.showConfirmDialog(null, "Confirmar transaccion.", "Confirmacion",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (opcion == 0) {
					if (usuario instanceof ClienteOrdinario) {
						ServicioPersistenciaBD.getInstance().updatePlaza(plazaSel(), "DISPONIBLE", "");
						ServicioPersistenciaBD.getInstance().ordinarioDelete(usuario.getMatricula());
						logger.info("Cerrando aplicacion...");
						frame.dispose();
						ServicioPersistenciaBD.getInstance().disconnect();
						System.exit(0);

					} else {
						logger.info("Cerrando aplicacion...");
						frame.dispose();
						ServicioPersistenciaBD.getInstance().disconnect();
						System.exit(0);
					}
				} else {
					logger.info("Transaccion cancelada");
				}
			}
		});
		btnPagar.setBounds(186, 364, 89, 23);
		add(btnPagar);

		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(415, 364, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServicioPersistenciaBD.getInstance().subscritoDelete(usuario.getMatricula());
				ServicioPersistenciaBD.getInstance().updatePlaza(plaza, "DISPONIBLE", "");
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});

		add(btnVolver);
	}

	public Plaza plazaSel() {
		Map<Integer, Plaza> plazasMap = ServicioPersistenciaBD.getInstance().plazasSelect();
		for (Plaza p : plazasMap.values()) {
			if (p.getMatricula().equals(usuario.getMatricula())) {
				return p;
			}
		}
		return null;
	}

}
