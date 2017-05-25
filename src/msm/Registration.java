package msm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Registration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	Connection connection = null;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Registration frame = new Registration();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Registration() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        	URL path = getClass().getResource("medical.png");
            File file = new File(path.toURI());
			BufferedImage image = ImageIO.read(file);
			setIconImage(image);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setResizable(false);
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:medical.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		//System.out.println("Opened database successfully");
		setTitle("Registration form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(49, 47, 345, 176);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Password:");
		label.setBounds(10, 78, 78, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Username:");
		label_1.setBounds(10, 47, 78, 14);
		panel.add(label_1);
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(88, 36, 247, 28);
		panel.add(username);
		
		JButton button = new JButton("Login");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				Registration.this.hide();
				login.show();
			}
		});
		button.setBounds(217, 122, 118, 41);
		panel.add(button);
		
		JButton button_1 = new JButton("Registration");
		contentPane.getRootPane().setDefaultButton(button_1);
		button.requestFocus();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typedPassword;
				char [] tempPass = password.getPassword();
				typedPassword = new String(tempPass);
				if(!username.getText().equals("") && !typedPassword.equals(""))
				{
					String sql = "INSERT INTO users(username,password) VALUES(?,?)";
					try {
						PreparedStatement statement = connection.prepareStatement(sql);
						
						statement.setString(1, username.getText());
						statement.setString(2, typedPassword);
						statement.executeUpdate();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(panel, "Registration succesfull");
					System.out.println("Records created successfully");
				}
				else
				{
					JOptionPane.showMessageDialog(panel, "Fields can not be empty");
				}
			}
		});
		button_1.setBounds(88, 122, 119, 41);
		panel.add(button_1);
		
		password = new JPasswordField();
		password.setBounds(88, 75, 247, 28);
		panel.add(password);
	}
}
