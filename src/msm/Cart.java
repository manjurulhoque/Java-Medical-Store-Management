package msm;

import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import msm.Company.Popup;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Cart extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;
	JLabel lblTotal;
	int total = 0;

	Connection connection = null;
	Preferences preferences = Preferences.userRoot();
	int n;

	public class DoDelete extends SwingWorker {
		public void done() {
			preferences.remove("user");
			preferences.remove("pass");
		}

		@Override
		protected Object doInBackground() throws Exception {
			return null;
		}
	}
	
	class Popup extends JPopupMenu {
		/**
		 * 
		 */
		
		private static final long serialVersionUID = 1L;

		public Popup(JTable table) {
			JMenuItem delete = new JMenuItem("Delete");
			delete.setIcon(new ImageIcon(getClass().getResource("delete.png")));

			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					n = table.getRowCount();
					int index = table.getSelectedRow();
					String id = (String) model.getValueAt(index, 0);
					String sql2 = "SELECT * from product where id = ?";
					
					JOptionPane.showMessageDialog(delete, "Delete clicked");
					
//					try {
//						PreparedStatement statement = connection.prepareStatement(sql2);
//						statement.setString(1, id);
//						
//						ResultSet set = statement.executeQuery();
//						int price = set.getString("product_name");
//						String name = set.getString("product_name");
//						System.out.println(name);
//						
//						String sql = "DELETE from company where id = ?";
//						statement = connection.prepareStatement(sql);
//						statement.setInt(1, Integer.parseInt(id));
//						statement.executeUpdate();
//						if (n > 0) {
//							anotherLoad();
//							load();
//						}
//					} catch (SQLException e2) {
//						e2.printStackTrace();
//					}
				}
			});
			add(delete);
		}
	}
	
	public void anotherLoad() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}
	}

	
	public Cart() {
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

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cart.this.dispose();
				new Login().show();
			}
		});
		btnLogout.setBackground(new Color(0, 139, 139));
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogout.setBounds(129, 461, 195, 50);
		contentPane.add(btnLogout);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(428, 219, 872, 350);
		contentPane.add(scrollPane);

		table = new JTable();
		table = new JTable(model) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				JComponent jc = (JComponent) c;

				if (!isRowSelected(row)) {
					c.setBackground(row % 2 == 0 ? getBackground() : Color.DARK_GRAY);
					jc.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLUE));
				} else {
					c.setBackground(row % 2 == 0 ? getBackground() : Color.DARK_GRAY);
					jc.setBorder(new MatteBorder(1, 0, 1, 0, Color.RED));
				}

				return c;
			}
		};
		scrollPane.setViewportView(table);
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.WHITE);
		table.setFont(table.getFont().deriveFont(18.0f));
		table.setRowHeight(40);

		Object[] columns = { "Product Id", "Prodcut Name", "Total"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		final Popup popup = new Popup(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		JLabel username = new JLabel("name:");
		username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		username.setBounds(161, 181, 117, 50);
		contentPane.add(username);
		
		load();
		String name = preferences.get("user", "");
		username.setText("Name:" + name);
		
		lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTotal.setBounds(1140, 580, 94, 39);
		contentPane.add(lblTotal);
		lblTotal.setText("Total: " + total);
	}

	public void load() {
		
		String user_id = preferences.get("user_id", "");
		String sql = "select * from cart where user_id=?";
		Object[] row = new Object[3];
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user_id);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				row[0] = set.getString("product_id");
				row[1] = set.getString("product_name");
				row[2] = set.getString("total");
				total += Integer.parseInt(row[2].toString());
				model.addRow(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(total);
		
	}
}
