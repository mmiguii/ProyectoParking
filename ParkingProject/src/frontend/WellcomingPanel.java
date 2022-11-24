package frontend;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class WellcomingPanel extends JPanel {


	private static final long serialVersionUID = 1L;

	public WellcomingPanel(final JFrame frame) {
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Wellcoming Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(2, 1));
		
		JPanel topPanel = new JPanel(new GridLayout(2,1));
		
		JPanel topTopPanel = new JPanel();
		topTopPanel.setLayout(new GridBagLayout());
		JLabel incomingLabel = new JLabel("Bienvenido al Parking", SwingConstants.CENTER);
		incomingLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
		topTopPanel.add(incomingLabel);
		
		JPanel bottomTopPanel = new JPanel();
		bottomTopPanel.setLayout(new GridBagLayout());
		JLabel incomingLabel2 = new JLabel("¿Es usted un trabajador o un usuario?", SwingConstants.CENTER);
		incomingLabel2.setFont(new Font("Tahoma", Font.PLAIN, 27));
		bottomTopPanel.add(incomingLabel2);
		
//		topPanel.setLayout(new GridBagLayout());
		
		
		topPanel.add(topTopPanel);
		topPanel.add(bottomTopPanel);
		
		add(topPanel);
		
		JPanel midPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel leftMidPanel = new JPanel();
		leftMidPanel.setLayout(new GridBagLayout());
		JButton btnWorker = new JButton("TRABAJADOR");
		btnWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInWorker panel = new LogInWorker(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		leftMidPanel.add(btnWorker);
		midPanel.add(leftMidPanel);
		
		
		JPanel rightMidPanel = new JPanel();
		rightMidPanel.setLayout(new GridBagLayout());
		JButton btnUser = new JButton("USUARIO");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPanel panel = new LogInPanel(frame);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightMidPanel.add(btnUser);
		midPanel.add(rightMidPanel);
		
//		midPanel.add(btnWorker);
//		midPanel.add(btnUser);
		add(midPanel);
	}
}
