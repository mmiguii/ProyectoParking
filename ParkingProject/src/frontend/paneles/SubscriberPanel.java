package frontend.paneles;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SubscriberPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public SubscriberPanel (final JFrame frame) {
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Subscriber Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));
		
		JPanel topPanel = new JPanel();
		JLabel label = new JLabel("�SELECCIONE EL TIPO DE ABONO QUE DESEE?");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.add(label);
		add(topPanel);
		
		JPanel middlePanel = new JPanel();
		JButton btnSemanal = new JButton("SEMANAL");
		JButton btnMensual = new JButton("MENSUAL");
		JButton btnAnual = new JButton("ANUAL");
		
		middlePanel.add(btnSemanal, new GridBagConstraints());
		middlePanel.add(btnMensual, new GridBagConstraints());
		middlePanel.add(btnAnual, new GridBagConstraints());
		add(middlePanel);
		
		
		JPanel bottomPanel = new JPanel();
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPanel panel = new LogInPanel(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		
		
		bottomPanel.add(btnVolver);
		add(bottomPanel);
	}
}
