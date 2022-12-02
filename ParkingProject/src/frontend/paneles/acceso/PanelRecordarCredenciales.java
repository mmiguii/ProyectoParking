package frontend.paneles.acceso;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import backend.clases.email.EnvioEmail;
import backend.clases.personas.personal.Trabajador;

public class PanelRecordarCredenciales extends JPanel {
	
	private JTextField txtNombreDeUsuario;

	public PanelRecordarCredenciales(JFrame frame, JPanel panel, ArrayList<Trabajador> trabajadores) {
		setLayout(null);
		
		JLabel label = new JLabel("En breve recibiras un email en tu correo");
		label.setBounds(85, 233, 299, 35);
		add(label);
		
		JLabel label1 = new JLabel("indicando tu dni actual");
		label1.setBounds(150, 739, 299, -29);
		add(label1);
		
		
		
		txtNombreDeUsuario = new JTextField();
		txtNombreDeUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtNombreDeUsuario.setText("");
			}
		});
		txtNombreDeUsuario.setBounds(86, 117, 298, 26);
		txtNombreDeUsuario.setText("Nombre de usuario (por ahora)");
		add(txtNombreDeUsuario);
		txtNombreDeUsuario.setColumns(10);
		
		JLabel lblDNI = new JLabel("Introduzca su DNI\n");
		lblDNI.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 17));
		lblDNI.setBounds(137, 85, 236, 20);
		add(lblDNI);
		
		JButton btnRecuperarContrasea = new JButton("Recuperar credenciales");
		btnRecuperarContrasea.setBounds(231, 180, 218, 29);
		btnRecuperarContrasea.setBackground(new Color(255, 102, 102));
		add(btnRecuperarContrasea);
		
		
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				frame.getContentPane().add(panel); 
				panel.setVisible(true); 
				setVisible(false);
			}
		});
		btnVolver.setBounds(30, 180, 201, 29);
		btnVolver.setBackground(new Color(152, 240, 153));
		add(btnVolver);
 
		
		btnRecuperarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreTrabajador =txtNombreDeUsuario.getText();
				boolean encontrado = false;
				Trabajador trabajador = null;
				for(Trabajador t: trabajadores){
					if(t.getNombre().equals(nombreTrabajador))
					{
						encontrado = true;
						trabajador = t;
						break;
					}
				}
				if(encontrado==false) {
					JOptionPane.showMessageDialog(PanelRecordarCredenciales.this, "El nombre del trabajador introducido no existe. Intentelo de nuevo.");
				}else {
					
					EnvioEmail.bienvenida(trabajador.getEmail(),trabajador.getNombre(), trabajador.getDni());
					JOptionPane.showMessageDialog(PanelRecordarCredenciales.this, "El mensage ha sido enviado con exito."
							+ "\n El mensage de recuperacion ha sido enviado al siguiente correo: "+ trabajador.getEmail());
					frame.dispose();
				}
			}
		});
	}
	

}