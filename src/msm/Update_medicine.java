package msm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.EventQueue;

public class Update_medicine extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField quantity;
	private JTextField price;
	Connection connection = null;
	private JButton btnUpdate;
	private JTextField name;
	private JLabel lblProductQuantity;
	private JLabel lblProductPrice;
	String oldName;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Update_medicine frame = new Update_medicine();
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
	public Update_medicine() {
		setResizable(false);
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
		setTitle("Update Medicine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 293);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setBounds(52, 46, 91, 14);
		contentPane.add(lblProductName);

		quantity = new JTextField();
		quantity.setBounds(175, 79, 278, 33);
		contentPane.add(quantity);
		quantity.setColumns(10);

		price = new JTextField();
		price.setColumns(10);
		price.setBounds(174, 125, 279, 37);
		contentPane.add(price);

		btnUpdate = new JButton("update");
		btnUpdate.setForeground(new Color(255, 255, 255));
		btnUpdate.setBackground(new Color(0, 139, 139));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "UPDATE product SET product_name = ?, quantity = ?, price = ? " + "WHERE product_name = ?";
				try {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, name.getText().toString());
					statement.setString(2, quantity.getText().toString());
					statement.setString(3, price.getText().toString());
					statement.setString(4, oldName);
					statement.executeUpdate();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				JOptionPane.showMessageDialog(btnUpdate, "updated successfully");
				Update_medicine.this.hide();

			}
		});
		btnUpdate.setBounds(264, 184, 107, 35);
		contentPane.add(btnUpdate);

		name = new JTextField();
		name.setBounds(175, 31, 278, 35);
		contentPane.add(name);
		name.setColumns(10);

		lblProductQuantity = new JLabel("Product Quantity");
		lblProductQuantity.setBounds(52, 92, 91, 14);
		contentPane.add(lblProductQuantity);

		lblProductPrice = new JLabel("Product Price");
		lblProductPrice.setBounds(52, 142, 91, 14);
		contentPane.add(lblProductPrice);

	}

	public void set(String name, String quantity, String price) {
		oldName = name;
		this.name.setText(name.toString());
		this.quantity.setText(quantity.toString());
		this.price.setText(price.toString());
	}

}
