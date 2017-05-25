package msm;

import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Medicin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Medicin frame = new Medicin();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	class Popup extends JPopupMenu {
		public Popup(JTable table) {
			JMenuItem edit = new JMenuItem("Edit");
			JMenuItem delete = new JMenuItem("Delete");
			edit.setIcon(new ImageIcon(getClass().getResource("edit.png")));
			delete.setIcon(new ImageIcon(getClass().getResource("delete.png")));

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
					String id = (String) model.getValueAt(index, 0);
					// String address = (String)model.getValueAt(index, 1);
					// String phone = (String)model.getValueAt(index, 2);
					JOptionPane.showMessageDialog(delete, id);

					String sql = "DELETE from product where id = ?";
					try {
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setInt(1, Integer.parseInt(id));
						statement.executeUpdate();
						load();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					// load();
				}
			});

			add(edit);
			add(delete);
		}
	}

	/**
	 * Create the frame.
	 */
	public Medicin() {
		setTitle("Medicine");
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		setBounds(0, 0, rectangle.width, rectangle.height - 35);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnInsetMedicines = new JButton("Inset Medicines");
		btnInsetMedicines.setForeground(new Color(255, 255, 255));
		btnInsetMedicines.setBackground(new Color(0, 139, 139));
		btnInsetMedicines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Insert_medicine frm = new Insert_medicine();
				frm.setVisible(true);
			}
		});
		btnInsetMedicines.setBounds(10, 63, 135, 56);
		contentPane.add(btnInsetMedicines);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(159, 60, 1133, 559);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBackground(Color.DARK_GRAY);
		table.setForeground(Color.WHITE);
		table.setFont(table.getFont().deriveFont(18.0f));
		table.setRowHeight(40);

		Object[] columns = { "Product Id", "Prodcut Name", "Company name", "Product Category", "Quantity",
				"Price per unit" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);

		final Popup popup = new Popup(table);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Medicin.this.hide();
				new Main().show();
			}
		});
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(new Color(0, 139, 139));
		btnBack.setBounds(10, 563, 135, 56);
		contentPane.add(btnBack);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		load();
	}

	public void load() {
		String sql = "select * from product";
		Object[] row = new Object[6];
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
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
	}
}
