package frontend;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WellcomingPanel extends JPanel {


	private static final long serialVersionUID = 1L;

	public WellcomingPanel(final JFrame frame) {
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Wellcoming Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));
		
		JPanel topPanel = new JPanel();
		JLabel incomingLabel = new JLabel("Bienvenido al Parking");
		JLabel incomingLabel2 = new JLabel("¿Es usted un trabajador o un usuario?");
		topPanel.add(incomingLabel);
		topPanel.add(incomingLabel2);
		
		add(topPanel);
		
		JPanel midPanel = new JPanel();
		JButton btnWorker = new JButton("TRABAJADOR");
		JButton btnUser = new JButton("USUARIO");
		
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPanel panel = new LogInPanel(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		
		midPanel.add(btnWorker);
		midPanel.add(btnUser);
		add(midPanel);
	}
}
