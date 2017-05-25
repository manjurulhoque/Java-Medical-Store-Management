package msm;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class New_sale extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	Connection connection = null;
	JComboBox<String> comboBox;
	private JTable table;
	DefaultTableModel model;
	DefaultTableModel model2;
	private JTable table_1;
	JButton btnAddToCart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					New_sale frame = new New_sale();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public New_sale() {
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
		setTitle("New Sale");
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:medical.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		setBounds(100, 100, 982, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(378, 27, 176, 33);
		contentPane.add(comboBox);

		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProductName.setBounds(247, 37, 106, 14);
		contentPane.add(lblProductName);

		JLabel lblEnterQuantity = new JLabel("Enter quantity");
		lblEnterQuantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterQuantity.setBounds(247, 88, 106, 14);
		contentPane.add(lblEnterQuantity);

		textField = new JTextField();
		textField.setBounds(378, 78, 176, 33);
		contentPane.add(textField);
		textField.setColumns(10);

		btnAddToCart = new JButton("Add to cart");
		btnAddToCart.setBackground(new Color(0, 139, 139));
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sql = "select company_name, quantity, price from product where product_name = ?";

				int n = Integer.parseInt(textField.getText().toString());

				try {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, comboBox.getSelectedItem().toString());
					ResultSet set = statement.executeQuery();

					int quantity = Integer.parseInt(set.getString("quantity"));
					int price = (set.getInt("price"));
					int totalPrice;
					String name = set.getString("company_name");
					// int n = Integer.parseInt(textField.getText().toString());
					if (quantity >= n) {
						DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date date = new Date();
						totalPrice = n * price;
						// JOptionPane.showMessageDialog(btnAddToCart,
						// dateFormat.format(date));
						String sql2 = "INSERT INTO sale(product_name,company_name, date, quantity, price)"
								+ " VALUES(?,?,?,?,?)";
						try {
							PreparedStatement statement2 = connection.prepareStatement(sql2);

							statement2.setString(1, comboBox.getSelectedItem().toString());
							statement2.setString(2, name);
							statement2.setString(3, dateFormat.format(date).toString());
							statement2.setString(4, Integer.toString(quantity));
							statement2.setString(5, Integer.toString(price));
							statement2.executeUpdate();
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						Object[] row = new Object[3];
						row[0] = comboBox.getSelectedItem().toString();
						row[1] = Integer.toString(totalPrice);
						row[2] = Integer.toString(n);
						model2.addRow(row);
						JOptionPane.showMessageDialog(btnAddToCart, "Added");
						update(quantity - n);
					} else {
						JOptionPane.showMessageDialog(btnAddToCart, "Stock not available");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnAddToCart.setBounds(611, 78, 125, 30);
		contentPane.add(btnAddToCart);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(96, 140, 826, 136);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		Object[] columns = { "Product Id", "Prodcut Name", "Company name", "Product category", "Quantity",
				"Price per unit" };
		model = new DefaultTableModel();
		table.setBackground(Color.CYAN);
		model.setColumnIdentifiers(columns);
		table.setModel(model);

		JButton btnShow = new JButton("Show");
		btnShow.setBackground(new Color(0, 139, 139));
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		btnShow.setBounds(611, 27, 125, 35);
		contentPane.add(btnShow);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(96, 323, 512, 94);
		contentPane.add(scrollPane_1);

		table_1 = new JTable();
		table.setBackground(Color.CYAN);
		scrollPane_1.setViewportView(table_1);

		Object[] columns2 = { "Product Name", "Bill", "Quantity" };
		model2 = new DefaultTableModel();
		model2.setColumnIdentifiers(columns2);
		table_1.setModel(model2);

		loadCombo();
	}

	public void update(int quantity) {
		String sql = "UPDATE product SET quantity = ? " + "WHERE product_name = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, Integer.toString(quantity));
			statement.setString(2, comboBox.getSelectedItem().toString());
			statement.executeUpdate();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// JOptionPane.showMessageDialog(btnAddToCart, "updated successfully");
	}

	public void load() {
		int rows = table.getRowCount();
		// JOptionPane.showMessageDialog(comboBox, rows);
		if (rows == 0) {
			// JOptionPane.showMessageDialog(comboBox, "From combo");
			String sql = "select * from product where product_name = ?";
			Object[] row = new Object[6];
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, comboBox.getSelectedItem().toString());
				ResultSet set = statement.executeQuery();
				// table.setModel(DbUtils.resultSetToTableModel(set));
				while (set.next()) {
					row[0] = set.getString("id");
					row[1] = set.getString("product_name");
					row[2] = set.getString("company_name");
					row[3] = set.getString("product_category");
					row[4] = set.getString("quantity");
					row[5] = set.getString("price");
					// table.setModel(model);
					model.addRow(row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			model.removeRow(rows - 1);
			String sql = "select * from product where product_name = ?";
			Object[] row = new Object[6];
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, comboBox.getSelectedItem().toString());
				ResultSet set = statement.executeQuery();
				// table.setModel(DbUtils.resultSetToTableModel(set));
				while (set.next()) {
					row[0] = set.getString("id");
					row[1] = set.getString("product_name");
					row[2] = set.getString("company_name");
					row[3] = set.getString("product_category");
					row[4] = set.getString("quantity");
					row[5] = set.getString("price");
					// table.setModel(model);
					model.addRow(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void loadCombo() {
		String sql = "select * from product";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				String name = set.getString("product_name");
				comboBox.addItem(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
