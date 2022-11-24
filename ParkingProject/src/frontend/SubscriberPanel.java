package frontend;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SubscriberPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public SubscriberPanel (final JFrame frame, String matricula) {
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Subscriber Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		JLabel label = new JLabel("¿SELECCIONE EL TIPO DE ABONO QUE DESEE?");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topPanel.add(label);
		add(topPanel);
		
		JPanel middlePanel = new JPanel(new GridLayout(1,3));
		
		JPanel leftMiddlePanel = new JPanel();
		leftMiddlePanel.setLayout(new GridBagLayout());
		JButton btnSemanal = new JButton("SEMANAL");
		btnSemanal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubscriberPaymentPanel panel = new SubscriberPaymentPanel(frame, matricula, btnSemanal.getText());
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		leftMiddlePanel.add(btnSemanal);
		
		JPanel middleMiddlePanel = new JPanel();
		middleMiddlePanel.setLayout(new GridBagLayout());
		JButton btnMensual = new JButton("MENSUAL");
		btnMensual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubscriberPaymentPanel panel = new SubscriberPaymentPanel(frame, matricula, btnMensual.getText());
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		middleMiddlePanel.add(btnMensual);
		
		JPanel rightMiddlePanel = new JPanel();
		rightMiddlePanel.setLayout(new GridBagLayout());
		JButton btnAnual = new JButton("ANUAL");
		btnAnual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubscriberPaymentPanel panel = new SubscriberPaymentPanel(frame, matricula, btnAnual.getText());
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightMiddlePanel.add(btnAnual);

		middlePanel.add(leftMiddlePanel);
		middlePanel.add(middleMiddlePanel);
		middlePanel.add(rightMiddlePanel);
		add(middlePanel);
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
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
