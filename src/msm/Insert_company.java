package msm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

public class Insert_company extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// Insert_company frame = new Insert_company();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public Insert_company() {
		setTitle("Add new Company");
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
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:medical.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		setBounds(400, 200, 688, 470);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new HintTextField("Enter company name");
		textField.setColumns(10);
		textField.setBounds(304, 56, 207, 28);
		contentPane.add(textField);

		JLabel lblCompanyName = new JLabel("Company Name");
		lblCompanyName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCompanyName.setBounds(159, 64, 119, 14);
		contentPane.add(lblCompanyName);

		textField_1 = new HintTextField("Enter country name");
		textField_1.setColumns(10);
		textField_1.setBounds(304, 107, 207, 28);
		contentPane.add(textField_1);

		JLabel lblCompanyCountry = new JLabel("Company Country");
		lblCompanyCountry.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCompanyCountry.setBounds(143, 113, 135, 14);
		contentPane.add(lblCompanyCountry);

		textField_2 = new HintTextField("Enter email");
		textField_2.setColumns(10);
		textField_2.setBounds(304, 152, 207, 28);
		contentPane.add(textField_2);

		JLabel lblCompanyEmail = new JLabel("Company EMAIL");
		lblCompanyEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCompanyEmail.setBounds(143, 153, 135, 14);
		contentPane.add(lblCompanyEmail);

		textField_3 = new HintTextField("Enter contact number");
		textField_3.setColumns(10);
		textField_3.setBounds(304, 198, 207, 28);
		contentPane.add(textField_3);

		JLabel lblCompanyContactNo = new JLabel("Company Contact No.");
		lblCompanyContactNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCompanyContactNo.setBounds(123, 201, 155, 14);
		contentPane.add(lblCompanyContactNo);

		textField_4 = new HintTextField("Enter address");
		textField_4.setColumns(10);
		textField_4.setBounds(304, 245, 207, 28);
		contentPane.add(textField_4);

		JLabel lblCompanyAddress = new JLabel("Company Address");
		lblCompanyAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCompanyAddress.setBounds(143, 250, 135, 14);
		contentPane.add(lblCompanyAddress);

		JButton btnInsert = new JButton("Insert");
		btnInsert.setBackground(new Color(0, 139, 139));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().equals("") && !textField_1.getText().equals("")
						&& !textField_2.getText().equals("") && !textField_3.getText().equals("")
						&& !textField_4.getText().equals("")) {
					String sql = "INSERT INTO company(company_name, Company_country, company_email, company_contact_number,"
							+ "address)" + " VALUES(?,?,?,?,?)";
					try {
						final PreparedStatement statement2 = connection.prepareStatement(sql);

						statement2.setString(1, textField.getText().toString());
						statement2.setString(2, textField_1.getText().toString());
						statement2.setString(3, textField_2.getText().toString());
						statement2.setString(4, textField_3.getText().toString());
						statement2.setString(5, textField_4.getText().toString());
						statement2.executeUpdate();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(btnInsert, "Company inserted");
					Insert_company.this.show(false);
					new Company().model.fireTableDataChanged();
				}
				else{
					JOptionPane.showMessageDialog(btnInsert, "Fields can not be empty");
				}
			}
		});
		btnInsert.setBounds(341, 320, 97, 35);
		contentPane.add(btnInsert);
	}

	public class HintTextField extends JTextField {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public HintTextField(String hint) {
			_hint = hint;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (getText().length() == 0) {
				int h = getHeight();
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				Insets ins = getInsets();
				FontMetrics fm = g.getFontMetrics();
				int c0 = getBackground().getRGB();
				int c1 = getForeground().getRGB();
				int m = 0xfefefefe;
				int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
				g.setColor(new Color(c2, true));
				g.drawString(_hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
			}
		}

		private final String _hint;
	}
}
