package frontend;

import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LogInWorker extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textDNI;
	private JButton btnLogIn;
	
	public LogInWorker(final JFrame frame) {
	
		setBorder(javax.swing.BorderFactory.createTitledBorder("General Worker Panel"));
		setBounds(10, 10, 567, 448);
		this.setLayout(new GridLayout(3, 1));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		JLabel labelLogIn = new JLabel("LOG-IN PARKING");
//		labelLogIn.setBounds(209, 51, 153, 25);
		
		topPanel.add(labelLogIn);
		add(topPanel);
		
		
		JPanel middlePanel = new JPanel(new GridLayout(3, 1));
		
		JPanel topMiddlePanel = new JPanel();
		topMiddlePanel.setLayout(new GridBagLayout());
		JLabel labelNombre = new JLabel("Nombre: ");
		textNombre = new JTextField(10);
		topMiddlePanel.add(labelNombre);
		topMiddlePanel.add(textNombre);
		
		JPanel middleMiddlePanel = new JPanel();
		middleMiddlePanel.setLayout(new GridBagLayout());
		JLabel labelApellido = new JLabel("Apellidos: ");
		textApellido = new JTextField(10);
		middleMiddlePanel.add(labelApellido);
		middleMiddlePanel.add(textApellido);
		
		
		JPanel bottomMiddlePanel = new JPanel();
		bottomMiddlePanel.setLayout(new GridBagLayout());
		JLabel labelDNI = new JLabel("DNI: ");
		textDNI = new JTextField(10);
		bottomMiddlePanel.add(labelDNI);
		GridBagConstraints gbc_textDNI = new GridBagConstraints();
		gbc_textDNI.gridx = 1;
		bottomMiddlePanel.add(textDNI, gbc_textDNI);
		
		
		middlePanel.add(topMiddlePanel);
		middlePanel.add(middleMiddlePanel);
		middlePanel.add(bottomMiddlePanel);
		
		
//		JLabel labelNombre = new JLabel("Nombre: ");
//		labelNombre.setBounds(187, 24, 80, 14);
//		JLabel labelApellido = new JLabel("Apellidos: ");
//		labelApellido.setBounds(187, 62, 80, 14);
//		JLabel labelDNI = new JLabel("DNI: ");
//		labelDNI.setBounds(187, 103, 80, 14);
//		
//		textNombre = new JTextField(15);
//		textNombre.setBounds(277, 21, 84, 20);
//		textApellido = new JTextField(15);
//		textApellido.setBounds(277, 59, 84, 20);
//		textDNI = new JTextField(10);
//		textDNI.setBounds(277, 100, 84, 20);
		
		textNombre.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				processTextField();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				processTextField();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				processTextField();
			}
		});
		
		textApellido.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				processTextField();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				processTextField();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				processTextField();
			}
		});
		
		textDNI.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				processTextField();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				processTextField();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				processTextField();
			}
		});
		
		
//		middlePanel.setLayout(new GridBagLayout());
//		
//		middlePanel.add(labelNombre);
//		middlePanel.add(textNombre);
//		middlePanel.add(labelApellido);
//		middlePanel.add(textApellido);
//		middlePanel.add(labelDNI);
//		middlePanel.add(textDNI);
		add(middlePanel);
		
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());
		btnLogIn = new JButton("INICIAR SESION"); 
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int cantidad = 3;
				if (textNombre.getText().equals("miguel")) {
					WellComingWorkerPanel panel = new WellComingWorkerPanel(frame, textNombre.getText(), textApellido.getText());
					frame.getContentPane().add(panel);
					setVisible(false);
					panel.setVisible(true);
				}else {
					WellcomingManagerPanel panel = new WellcomingManagerPanel(frame, textNombre.getText(), textApellido.getText());
					frame.getContentPane().add(panel);
					setVisible(false);
					panel.setVisible(true);
				}	
			}
		});
		leftBottomPanel.add(btnLogIn);
		
		JPanel rightBottomPanel = new JPanel();
		rightBottomPanel.setLayout(new GridBagLayout());
		JButton btnVolver = new JButton("VOLVER");
//		btnVolver.setBounds(119, 67, 101, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WellcomingPanel panel = new WellcomingPanel(frame);
				frame.getContentPane().add(panel);
				setVisible(false);
				panel.setVisible(true);
			}
		});
		rightBottomPanel.add(btnVolver);
		
		bottomPanel.add(leftBottomPanel);
		bottomPanel.add(rightBottomPanel);

//		btnLogIn.doLayout(new FlowLayout(FlowLayout.LEFT));		
//		btnLogIn.setBounds(329, 67, 149, 23);
		btnLogIn.setEnabled(false);
//		bottomPanel.setLayout(new GridBagLayout());
//		bottomPanel.add(btnVolver, new GridBagConstraints());
//		bottomPanel.add(btnLogIn, new GridBagConstraints());
		add(bottomPanel);
	}
	
	
	private void processTextField() {
		if (textNombre.getText().isBlank() || textApellido.getText().isBlank() || textDNI.getText().isBlank()) {
			btnLogIn.setEnabled(false);
		}else {
			btnLogIn.setEnabled(true);
		}
	}

}
