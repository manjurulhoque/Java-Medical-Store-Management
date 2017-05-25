package msm;

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
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import msm.Medicin.Popup;
import net.proteanit.sql.DbUtils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

public class Company extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	Connection connection = null;
	DefaultTableModel model;
	int n;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Company frame = new Company();
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
					String id = (String) model.getValueAt(index, 0);
					String name = (String) model.getValueAt(index, 1);
					String country = (String) model.getValueAt(index, 2);
					String email = (String) model.getValueAt(index, 3);
					String number = (String) model.getValueAt(index, 4);
					String address = (String) model.getValueAt(index, 5);
					// JOptionPane.showMessageDialog(edit, name + " " + quantity
					// + " " + price);
					Update_company update = new Update_company();
					update.set(id, name, country, email, number, address);
					update.show();
				}
			});

			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					n = table.getRowCount();
					int index = table.getSelectedRow();
					// JOptionPane.showMessageDialog(edit, index + " CLicked");
					// TableModel model = table.getModel();
					String id = (String) model.getValueAt(index, 0);
					// String address = (String)model.getValueAt(index, 1);
					// String phone = (String)model.getValueAt(index, 2);
					// JOptionPane.showMessageDialog(delete, id);

					String sql = "DELETE from company where id = ?";
					try {
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setInt(1, Integer.parseInt(id));
						statement.executeUpdate();
						// JOptionPane.showMessageDialog(delete, n);
						if (n > 0) {
							anotherLoad();
							load();
						}
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

	public void anotherLoad() {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}
	}

	/**
	 * Create the frame.
	 */
	public Company() {
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
		setTitle("Company");
		setBounds(100, 100, 1131, 467);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnInsertCompany = new JButton("Insert Company");
		btnInsertCompany.setForeground(new Color(255, 255, 255));
		btnInsertCompany.setBackground(new Color(0, 139, 139));
		btnInsertCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Insert_company company = new Insert_company();
				company.setVisible(true);
				company.show(true);
			}
		});
		btnInsertCompany.setBounds(10, 100, 122, 45);
		contentPane.add(btnInsertCompany);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(167, 100, 938, 302);
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

				// Color row based on a cell value
				// Alternate row color

				if (!isRowSelected(row)) {
					c.setBackground(row % 2 == 0 ? getBackground() : Color.DARK_GRAY);
					jc.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLUE));
				} else {
					c.setBackground(row % 2 == 0 ? getBackground() : Color.DARK_GRAY);
					jc.setBorder(new MatteBorder(1, 0, 1, 0, Color.RED));
				}

				// Use bold font on selected row

				return c;
			}
		};
		scrollPane.setViewportView(table);
		table.setBackground(Color.LIGHT_GRAY);
		// MatteBorder border = new MatteBorder(2, 1, 1, 1, Color.RED);
		// table.setBorder(border);
		table.setForeground(Color.WHITE);
		table.setFont(table.getFont().deriveFont(18.0f));
		table.setRowHeight(40);
		scrollPane.setViewportView(table);

		Object[] columns = { "Company Id", "Company Name", "Company country", "Company email", "Company contact number",
				"Address" };
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
		load();
	}

	public void load() {
		String sql = "select * from company";
		Object[] row = new Object[6];
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			// table.setModel(DbUtils.resultSetToTableModel(set));
			while (set.next()) {
				row[0] = set.getString("id");
				row[1] = set.getString("company_name");
				row[2] = set.getString("Company_country");
				row[3] = set.getString("company_email");
				row[4] = set.getString("company_contact_number");
				row[5] = set.getString("address");
				// table.setModel(model);
				model.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
