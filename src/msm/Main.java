package msm;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import net.proteanit.sql.DbUtils;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;

	Connection connection = null;
	private JTextField textField;
	Preferences preferences = Preferences.userRoot();

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// Main frame = new Main();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }
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
	public Main() {
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

		JButton btnMedicines = new JButton("Medicines");
		btnMedicines.setRolloverEnabled(true);
		btnMedicines.setBackground(new Color(0, 139, 139));
		btnMedicines.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMedicines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.this.hide();
				Medicin frm = new Medicin();
				frm.setVisible(true);
				frm.show(true);
			}
		});
		btnMedicines.setBounds(133, 219, 195, 50);
		contentPane.add(btnMedicines);

		JButton btnCompanies = new JButton("Companies");
		btnCompanies.setBackground(new Color(0, 139, 139));
		btnCompanies.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCompanies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Company company = new Company();
				company.setVisible(true);
				company.show();
			}
		});
		btnCompanies.setBounds(133, 291, 195, 50);
		contentPane.add(btnCompanies);

		JButton btnVieSaleRecords = new JButton("View Sale Records");
		btnVieSaleRecords.setBackground(new Color(0, 139, 139));
		btnVieSaleRecords.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVieSaleRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sale_record record = new Sale_record();
				record.setVisible(true);
				record.show();
			}
		});
		btnVieSaleRecords.setBounds(133, 362, 195, 50);
		contentPane.add(btnVieSaleRecords);

		JButton btnNewSales = new JButton("New Sales");
		btnNewSales.setBackground(new Color(0, 139, 139));
		btnNewSales.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				New_sale sale = new New_sale();
				sale.setVisible(true);
				sale.show();
			}
		});
		btnNewSales.setBounds(133, 438, 195, 50);
		contentPane.add(btnNewSales);

		JButton btnChangePassword = new JButton("Logout");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//DoDelete delete = new DoDelete();
				//delete.execute();
				Main.this.dispose();
				new Login().show();
			}
		});
		btnChangePassword.setBackground(new Color(0, 139, 139));
		btnChangePassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnChangePassword.setBounds(133, 512, 195, 50);
		contentPane.add(btnChangePassword);

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

		Object[] columns = { "Product Id", "Prodcut Name", "Company name", "Product Category", "Quantity",
				"Price per unit" };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);

		textField = new HintTextField("Search anything here");
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				search(textField.getText().toString());
			}
		});
		textField.setBounds(506, 123, 376, 43);
		textField.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(textField);
		textField.setColumns(15);

		JLabel lblSearchAnythingHere = new JLabel("Search Anything Here");
		lblSearchAnythingHere.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSearchAnythingHere.setBounds(506, 83, 252, 29);
		contentPane.add(lblSearchAnythingHere);
		load();
	}

	public void load() {
		String sql = "select * from product";
		Object[] row = new Object[6];
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
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

	public void search(String query) {
		TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(rowSorter);

		rowSorter.setRowFilter(RowFilter.regexFilter(query));

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
