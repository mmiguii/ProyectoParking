package frontend.paneles.pagar;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelSalida extends JPanel {

	public PanelSalida(JFrame frame) {
		setBorder(javax.swing.BorderFactory.createTitledBorder("Panel salida"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));

		JLabel lblNewLabel = new JLabel("GRACIAS");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 97));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel);

		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnSalir.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		add(btnSalir);
	}
}
