package frontend;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class PaymentPanel extends JPanel{


	private static final long serialVersionUID = 1L;
	private JTextField textHoraEntrada;
	private JTextField textHoraSalida;
	private JTextField textTiempo;
	private JTextField textTipoCliente;
	private JTextField textImporte;

	public PaymentPanel(final JFrame frame, String tipoCliente, String fechaInicio, String matricula) {
		setBorder(javax.swing.BorderFactory.createTitledBorder("Payment Panel"));
		setBounds(10, 10, 567, 448);
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("PAGO DE PARKING");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(214, 41, 138, 63);
		add(lblTitulo);
		
		JLabel lblNewLabel = new JLabel("Hora de entrada");
		lblNewLabel.setBounds(163, 128, 80, 14);
		add(lblNewLabel);
		
		JLabel lblHoraDeSalida = new JLabel("Hora de salida");
		lblHoraDeSalida.setBounds(163, 169, 71, 14);
		add(lblHoraDeSalida);
		
		JLabel lblTiempoTranscurrido = new JLabel("Tiempo transcurrido");
		lblTiempoTranscurrido.setBounds(135, 210, 99, 14);
		add(lblTiempoTranscurrido);
		
		JLabel lblTipoDeCliente = new JLabel("Tipo de cliente");
		lblTipoDeCliente.setBounds(154, 254, 80, 14);
		add(lblTipoDeCliente);
		
		JLabel lblImporteTotal = new JLabel("Importe Total");
		lblImporteTotal.setBounds(163, 300, 71, 14);
		add(lblImporteTotal);
		
		JButton btnPagar = new JButton("PAGAR");
		btnPagar.setBounds(230, 364, 89, 23);
		add(btnPagar);
		
		textHoraEntrada = new JTextField();
		textHoraEntrada.setBounds(256, 125, 96, 20);
		textHoraEntrada.setText(fechaInicio);
		add(textHoraEntrada);
		textHoraEntrada.setColumns(10);
		
		textHoraSalida = new JTextField();
		textHoraSalida.setColumns(10);
		textHoraSalida.setBounds(256, 166, 96, 20);
		add(textHoraSalida);
		
		textTiempo = new JTextField();
		textTiempo.setColumns(10);
		textTiempo.setBounds(256, 207, 96, 20);
		textTiempo.setText(fechaInicio);
		add(textTiempo);
		
		textTipoCliente = new JTextField();
		textTipoCliente.setColumns(10);
		textTipoCliente.setBounds(256, 251, 96, 20);
		textTipoCliente.setText(tipoCliente);
		add(textTipoCliente);
		
		textImporte = new JTextField();
		textImporte.setColumns(10);
		textImporte.setBounds(256, 297, 96, 20);
		add(textImporte);
		
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(415, 364, 89, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPanel panel = new LogInPanel(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		
		
		add(btnVolver);
		
	}
}
