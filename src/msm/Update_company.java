package msm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Update_company extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Connection connection = null;
	DefaultTableModel model;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	String oldName, id;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Update_company frame = new Update_company();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	class Popup extends JPopupMenu {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Popup(JTable table) {
			JMenuItem edit = new JMenuItem("Edit");
			JMenuItem delete = new JMenuItem("Delete");

			edit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = table.getSelectedRow();
					// JOptionPane.showMessageDialog(edit, index + " CLicked");
					TableModel model = table.getModel();
					String name = (String) model.getValueAt(index, 1);
					String quantity = (String) model.getValueAt(index, 4);
					String price = (String) model.getValueAt(index, 5);
					// JOptionPane.showMessageDialog(edit, name + " " + quantity
					// + " " + price);
					Update_medicine update = new Update_medicine();
					update.set(name, quantity, price);
					update.show();
				}
			});

			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = table.getSelectedRow();
					// JOptionPane.showMessageDialog(edit, index + " CLicked");
					TableModel model = table.getModel();
					String name = (String) model.getValueAt(index, 0);
					// String address = (String)model.getValueAt(index, 1);
					// String phone = (String)model.getValueAt(index, 2);
					// JOptionPane.showMessageDialog(delete, name + " " +
					// address + " " + phone);

					int id = Integer.parseInt(name);

					String sql = "DELETE from company where id = ?";
					try {
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setInt(1, id);
						statement.executeUpdate();
						// load();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			});

			add(edit);
			add(delete);
		}
	}

	/**
	 * Create the frame.
	 */
	public Update_company() {
		setResizable(false);
		setTitle("Update company");
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
		setBounds(100, 100, 758, 463);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(267, 57, 278, 35);
		contentPane.add(textField);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(267, 105, 278, 33);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(266, 151, 279, 37);
		contentPane.add(textField_2);

		JButton button = new JButton("update");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sql = "UPDATE company SET company_name = ?, Company_country = ?, company_email = ?, company_contact_number = ?, address = ? " 
			+ "WHERE id = ?";
				try {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, textField.getText().toString());
					statement.setString(2, textField_1.getText().toString());
					statement.setString(3, textField_2.getText().toString());
					statement.setString(4, textField_3.getText().toString());
					statement.setString(5, textField_4.getText().toString());
					statement.setString(6, id);
					statement.executeUpdate();
				} catch (SQLException e2) {
					// 
					e2.printStackTrace();
				}
				JOptionPane.showMessageDialog(button, "updated successfully");
				Update_company.this.hide();
				Company company2 = new Company();
				company2.anotherLoad();
				company2.load();
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(0, 139, 139));
		button.setBounds(356, 303, 107, 35);
		contentPane.add(button);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(143, 261, 91, 14);
		contentPane.add(lblAddress);

		JLabel lblContactNumber = new JLabel("Contact number");
		lblContactNumber.setBounds(143, 205, 91, 14);
		contentPane.add(lblContactNumber);

		JLabel lblCompanyName = new JLabel("Company email");
		lblCompanyName.setBounds(143, 159, 91, 14);
		contentPane.add(lblCompanyName);

		JLabel label_2 = new JLabel("Company name");
		label_2.setBounds(143, 61, 91, 14);
		contentPane.add(label_2);

		JLabel lblCompanyCountry = new JLabel("Company country");
		lblCompanyCountry.setBounds(143, 107, 91, 14);
		contentPane.add(lblCompanyCountry);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(267, 251, 278, 35);
		contentPane.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(267, 205, 278, 35);
		contentPane.add(textField_4);
	}

	public void set(String id, String name, String country, String email, String number, String address) {
		oldName = name;
		this.id = id;
		this.textField.setText(name);
		this.textField_1.setText(country);
		this.textField_2.setText(email);
		this.textField_3.setText(number);
		this.textField_4.setText(address);
	}
}
