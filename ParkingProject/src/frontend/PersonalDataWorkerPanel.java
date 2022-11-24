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
import javax.swing.SwingConstants;

public class PersonalDataWorkerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public PersonalDataWorkerPanel(final JFrame frame, String nombre, String apellido) {
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Personal Data Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		
		JLabel labelWellcoming = new JLabel("DATOS PERSONALES: ");
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		topPanel.add(labelWellcoming);
		add(topPanel);
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());
		add(middlePanel);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WellComingWorkerPanel panel = new WellComingWorkerPanel(frame, nombre, apellido);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		bottomPanel.add(btnVolver);
		add(bottomPanel);
	}

}
