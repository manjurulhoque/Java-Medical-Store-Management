package msm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JCheckBox;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	Connection connection = null;
	private JPasswordField password;
	//Preferences preferences = Preferences.userRoot().node(this.getClass().getName());;
	Preferences preferences = Preferences.userRoot();
	JCheckBox chckbxRememberMe;
	String typedPassword;
	String userName = preferences.get("user", "");
	String passes = preferences.get("pass", "");
	// System.out.println(userName.toString());
	// System.out.println(passes.toString());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class DoLogin extends SwingWorker {
		public void done() {
			// On successfull login save user data
			preferences.put("user", username.getText().toString());
			preferences.put("pass", typedPassword.toString());
		}

		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public class DoDelete extends SwingWorker {
		public void done() {
			// On successfull login save user data
			preferences.remove("user");
			preferences.remove("pass");
		}

		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:medical.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		//System.out.println("Opened database successfully");
		//login();
		try {
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
		}
		setResizable(false);
		setTitle("Login form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Sign in", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(44, 42, 349, 196);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 78, 78, 14);
		panel.add(lblPassword);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 43, 78, 14);
		panel.add(lblUsername);

		username = new JTextField();
		username.setBounds(88, 36, 247, 28);
		panel.add(username);
		username.setColumns(10);

		JButton button = new JButton("Login");
		button.setIcon(new ImageIcon(getClass().getResource("check.png")));
		// button.setVerticalTextPosition(AbstractButton.CENTER);
		button.setHorizontalTextPosition(AbstractButton.RIGHT);
		contentPane.getRootPane().setDefaultButton(button);
		button.requestFocus();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				char[] tempPass = password.getPassword();
				typedPassword = new String(tempPass);

				if (!username.getText().equals("") && !typedPassword.equals("")) {

					String sql = "SELECT username, password FROM users where username=? and password=?";
					try {
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setString(1, username.getText().toString());
						statement.setString(2, typedPassword);

						ResultSet set = statement.executeQuery();

						int count = 0;
						while (set.next()) {
							count++;
						}

						if (count == 1) {
							JOptionPane.showMessageDialog(button, "Login success");
							if (chckbxRememberMe.isSelected()) {
								DoLogin log = new DoLogin();
								log.execute();
							} else {
								DoDelete delete = new DoDelete();
								delete.execute();
							}
							Login.this.hide();
							Main frMain = new Main();
							frMain.setVisible(true);
							frMain.show();

						} else if (count > 1) {
							System.out.println("Duplicate");
						} else {
							System.out.println("not Exists");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(panel, "Fields can not be empty");
				}
			}
		});
		button.setBounds(88, 142, 118, 41);
		panel.add(button);

		JButton button_1 = new JButton("Registration");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Registration frm = new Registration();
				frm.setVisible(true);
				Login.this.hide();
				frm.show(true);
			}
		});
		button_1.setBounds(216, 142, 119, 41);
		panel.add(button_1);

		password = new JPasswordField();
		password.setBounds(88, 67, 247, 28);
		panel.add(password);

		chckbxRememberMe = new JCheckBox("Remember me");
		chckbxRememberMe.setBounds(88, 102, 134, 23);
		panel.add(chckbxRememberMe);

		if (!userName.equals("") && !passes.equals("")) {
			username.setText(userName.toString());
			password.setText(passes.toString());
		}
	}

	public void login() {
		if (!userName.equals("") && !passes.equals("")) {

			String sql = "SELECT username, password FROM users where username=? and password=?";
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, userName);
				statement.setString(2, passes);

				ResultSet set = statement.executeQuery();

				int count = 0;
				while (set.next()) {
					count++;
				}

				if (count == 1) {
					// JOptionPane.showMessageDialog(button, "Login success");
					Login.this.setVisible(false);
					Main frMain = new Main();
					frMain.setVisible(true);
					frMain.show();
					Login.this.dispose();
					Login.this.setVisible(false);
					contentPane.removeAll();

				} else if (count > 1) {
					System.out.println("Duplicate");
				} else {
					System.out.println("Not Exists");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			// JOptionPane.showMessageDialog(panel, "Fields can not be empty");
		}
	}
}
