package msm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Insert_medicine extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	JComboBox<String> comboBox;
	JComboBox<String> comboBox_1;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Insert_medicine frame = new Insert_medicine();
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
	public Insert_medicine() {
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
		setTitle("Insert Medicine");
		setResizable(false);
		setBounds(100, 100, 819, 433);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProductName = new JLabel("Product Name:");
		lblProductName.setBounds(211, 70, 97, 14);
		contentPane.add(lblProductName);
		
		textField = new JTextField();
		textField.setBounds(356, 67, 224, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblProductQuantity = new JLabel("Product Quantity");
		lblProductQuantity.setBounds(211, 213, 97, 14);
		contentPane.add(lblProductQuantity);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(356, 207, 224, 34);
		contentPane.add(textField_1);
		
		JLabel lblProductPrice = new JLabel("Product Price:");
		lblProductPrice.setBounds(211, 263, 97, 14);
		contentPane.add(lblProductPrice);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(356, 255, 220, 31);
		contentPane.add(textField_2);
		
		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setBounds(211, 126, 97, 14);
		contentPane.add(lblCompanyName);
		
		JLabel lblProductCategory = new JLabel("Product Category");
		lblProductCategory.setBounds(211, 165, 97, 14);
		contentPane.add(lblProductCategory);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(356, 119, 222, 28);
		contentPane.add(comboBox);
		
		comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(356, 158, 224, 28);
		contentPane.add(comboBox_1);
		loadCombo();
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.setForeground(new Color(255, 255, 255));
		btnInsert.setBackground(new Color(0, 139, 139));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO product(product_name,company_name, product_category, quantity, price)"
						+ " VALUES(?,?,?,?,?)";
				try {
					final PreparedStatement statement2 = connection.prepareStatement(sql);
					
					statement2.setString(1, textField.getText().toString());
					statement2.setString(2, comboBox.getSelectedItem().toString());
					statement2.setString(3, comboBox_1.getSelectedItem().toString());
					statement2.setString(4, textField_1.getText().toString());
					statement2.setString(5, textField_2.getText().toString());
					statement2.executeUpdate();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				JOptionPane.showMessageDialog(btnInsert, "Records created successfully");
			}
		});
		btnInsert.setBounds(414, 315, 89, 37);
		contentPane.add(btnInsert);
		
		JButton btnAddNewCompany = new JButton("Add new company");
		btnAddNewCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Insert_medicine.this.dispose();
				new Insert_company().show();
			}
		});
		btnAddNewCompany.setForeground(new Color(255, 255, 255));
		btnAddNewCompany.setBackground(new Color(0, 139, 139));
		btnAddNewCompany.setBounds(605, 119, 139, 28);
		contentPane.add(btnAddNewCompany);
	}
	
	public void loadCombo()
	{
		String sql = "select * from company";
		String query = "select * from product_cat";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			
			while(set.next())
			{
				String name = set.getString("company_name");
				comboBox.addItem(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			statement = connection.prepareStatement(query);
			ResultSet set = statement.executeQuery();
			
			while(set.next())
			{
				String name = set.getString("product_category");
				comboBox_1.addItem(name);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
