package frontend.paneles.acceso.clientes;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.clases.personas.clientes.Usuario;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.PanelPrincipal;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelPlazaParking extends JPanel {

	public PanelPlazaParking(JFrame frame, Usuario usuario) {

		setBorder(javax.swing.BorderFactory.createTitledBorder("Aparcamiento"));
		setBounds(10, 10, 567, 448);
		setLayout(null);

		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ServicioPersistenciaBD.disconnect();
				System.exit(0);
			}
		});
		btnSalir.setBounds(112, 372, 327, 29);
		add(btnSalir);

		if (usuario.getTipoVehiculo().equals("Ordinario")) {
			JLabel lblOrd = new JLabel("");
			lblOrd.setBounds(122, 79, 292, 238);
			lblOrd.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/plazaOrdinaria.jpeg")));
			add(lblOrd);
		} else if (usuario.getTipoVehiculo().equals("Minusvalido")) {
			JLabel lblMin = new JLabel("");
			lblMin.setBounds(125, 107, 292, 238);
			lblMin.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/plazaMinusvalido.jpeg")));
			add(lblMin);
		} else {
			JLabel lblEl = new JLabel("");
			lblEl.setBounds(122, 79, 292, 238);
			lblEl.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/plazaElectrico.jpeg")));
			add(lblEl);
		}

	}
}
