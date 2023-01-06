package frontend.paneles.acceso.clientes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import backend.clases.personas.clientes.Usuario;
import backend.servicios.ServicioPersistenciaBD;
import frontend.paneles.PanelPrincipal;

public class PanelPlazaParking extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private static Logger logger = Logger.getLogger(PanelPlazaParking.class.getName());

	public PanelPlazaParking(JFrame frame, Usuario usuario) {

		javax.swing.border.TitledBorder border = javax.swing.BorderFactory.createTitledBorder("Aparcamiento");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		setBounds(10, 10, 567, 448);
		setBackground(new Color(0, 128, 128));
		setLayout(null);

		this.frame = frame;

		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(this::salir);
		btnSalir.setVisible(false);
		btnSalir.setBounds(112, 380, 327, 29);
		add(btnSalir);

		JLabel lblAnimacionEntrada = new JLabel("");
		lblAnimacionEntrada.setVisible(true); // muestra el componente lblAnimacionEntrada
		lblAnimacionEntrada.setBounds(5, 45, 574, 330);
		lblAnimacionEntrada.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/EntradaParking.gif")));
		add(lblAnimacionEntrada);

		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(122, 79, 292, 238);
		if (usuario.getTipoVehiculo().equals("Ordinario")) {
			logger.info("Accediendo al aparcamiento ordinario...");
			lblImagen.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/plazaOrdinaria.jpeg")));
		} else if (usuario.getTipoVehiculo().equals("Minusvalido")) {
			logger.info("Accediendo al aparcamiento minusvalido...");
			lblImagen.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/plazaMinusvalido.jpeg")));
		} else {
			logger.info("Accediendo al aparcamiento electrico...");
			lblImagen.setIcon(new ImageIcon(PanelPrincipal.class.getResource("/plazaElectrico.jpeg")));
		}
		add(lblImagen);

		// Crea un temporizador que ejecutará una tarea después de 10 segundos
		Timer timer = new Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSalir.setVisible(true);
				lblImagen.setVisible(true); // muestra el resultado
				lblAnimacionEntrada.setVisible(false); // oculta lblAnimacionEntrada
			}
		});
		timer.setRepeats(false); // configura el temporizador para que sólo se ejecute una vez
		timer.start(); // inicia el temporizador
	}

	private void salir(ActionEvent event) {
		logger.info("Cerrando aplicacion...");
		frame.dispose();
		ServicioPersistenciaBD.getInstance().disconnect();
		System.exit(0);
	}

}
