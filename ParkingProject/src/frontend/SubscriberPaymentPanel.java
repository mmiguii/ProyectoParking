package frontend;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SubscriberPaymentPanel extends JPanel{


	private static final long serialVersionUID = 1L;
	
	public SubscriberPaymentPanel(final JFrame frame, String matricula, String tipoAbono) {
		setBorder(javax.swing.BorderFactory.createTitledBorder("Subscriber Payment Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		
		JLabel labelWellcoming = new JLabel("BIENVENIDO: ", SwingConstants.CENTER);
		labelWellcoming.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JTextField textMatricula = new JTextField(matricula);
		topPanel.add(labelWellcoming);
		topPanel.add(textMatricula);
		add(topPanel);
		
		JPanel middlePanel = new JPanel(new GridLayout(1,2));
		JPanel leftMiddlePanel = new JPanel(new GridLayout(3,1));
		JPanel rightMiddlePanel = new JPanel(new GridLayout(3,1));
		
		JPanel topLeftMiddlePanel = new JPanel();
		topLeftMiddlePanel.setLayout(new GridBagLayout());
		JLabel labelTipoAbono = new JLabel("TIPO DE ABONO", SwingConstants.RIGHT);
		topLeftMiddlePanel.add(labelTipoAbono);
		
		JPanel middleLeftMiddlePanel = new JPanel();
		middleLeftMiddlePanel.setLayout(new GridBagLayout());
		JLabel labelTipoCliente = new JLabel("TIPO DE CLIENTE");
		middleLeftMiddlePanel.add(labelTipoCliente);
		
		JPanel bottomLeftMiddlePanel = new JPanel();
		bottomLeftMiddlePanel.setLayout(new GridBagLayout());
		JLabel labelImporte = new JLabel("IMPORTE TOTAL");
		bottomLeftMiddlePanel.add(labelImporte);
		
		
		leftMiddlePanel.add(topLeftMiddlePanel);
		leftMiddlePanel.add(middleLeftMiddlePanel);
		leftMiddlePanel.add(bottomLeftMiddlePanel);
		
		
//		JPanel rightMiddlePanel = new JPanel(new GridLayout(3,1));
		JPanel topRightMiddlePanel = new JPanel();
		topRightMiddlePanel.setLayout(new GridBagLayout());
		JTextField textTipoAbono = new JTextField(tipoAbono);
		topRightMiddlePanel.add(textTipoAbono);
		
		JPanel middleRightMiddlePanel = new JPanel();
		middleRightMiddlePanel.setLayout(new GridBagLayout());
		JTextField textTipoCliente = new JTextField(10);
		middleRightMiddlePanel.add(textTipoCliente);
		
		JPanel bottomRightMiddlePanel = new JPanel();
		bottomRightMiddlePanel.setLayout(new GridBagLayout());
		JTextField textImporte = new JTextField(10);
		bottomRightMiddlePanel.add(textImporte);
		
		
		rightMiddlePanel.add(topRightMiddlePanel);
		rightMiddlePanel.add(middleRightMiddlePanel);
		rightMiddlePanel.add(bottomRightMiddlePanel);
		
		
		middlePanel.add(leftMiddlePanel);
		middlePanel.add(rightMiddlePanel);
		
		add(middlePanel);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1,2));
		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());
		JButton btnPagar = new JButton("PAGAR");
		leftBottomPanel.add(btnPagar);
		
		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubscriberPanel panel = new SubscriberPanel(frame, matricula);
				frame.add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightBottomPanel.add(btnVolver);
		
		
		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(rightBottomPanel);
		add(bottomPanel);
		
	}

}
