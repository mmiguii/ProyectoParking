package frontend.paneles;

import java.awt.Button;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frontend.paneles.acceso.PanelAccesoCliente;
import frontend.paneles.acceso.PanelAccesoTrabajador;

public class PanelPrincipal extends JPanel {

	private JPanel instance;

	private static final long serialVersionUID = 1L;

	public PanelPrincipal(final JFrame frame) {

		instance = this;

		setBorder(javax.swing.BorderFactory.createTitledBorder("Inicio"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));

		JPanel topPanel = new JPanel(new GridLayout(2, 1));

		JPanel topTopPanel = new JPanel();
		topTopPanel.setLayout(new GridBagLayout());

		JPanel bottomTopPanel = new JPanel();
		bottomTopPanel.setLayout(new GridBagLayout());

		JLabel lblTextoBienvenida = new JLabel("Bienvenido al Parking", SwingConstants.CENTER);
		lblTextoBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 27));
		topTopPanel.add(lblTextoBienvenida);
		topPanel.add(topTopPanel);

		JLabel lblTextoMensaje = new JLabel("¿Es usted trabajador o usuario?", SwingConstants.CENTER);
		lblTextoMensaje.setFont(new Font("Tahoma", Font.PLAIN, 27));
		bottomTopPanel.add(lblTextoMensaje);
		topPanel.add(bottomTopPanel);

		add(topPanel);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());

		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());

		JButton btnTrabajador = new JButton("Trabajador");
		btnTrabajador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelAccesoTrabajador panel = new PanelAccesoTrabajador(frame, instance);
				frame.getContentPane().add(panel);
				panel.setVisible(true);
				setVisible(false);
			}
		});
		leftBottomPanel.add(btnTrabajador);
		bottomPanel.add(leftBottomPanel);

		JButton btnUsuario = new JButton("Usuario");
		btnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelAccesoCliente panel = new PanelAccesoCliente(frame, instance);
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightBottomPanel.add(btnUsuario);
		bottomPanel.add(rightBottomPanel);

		add(bottomPanel);
	}
}
