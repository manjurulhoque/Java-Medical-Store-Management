package msm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class Sale_record extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;
	Connection connection = null;
	private JTextField textField;
	int index ;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Sale_record frame = new Sale_record();
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
			JMenuItem delete = new JMenuItem("Delete");

			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					index = table.getSelectedRow();
					//JOptionPane.showMessageDialog(edit, index + " CLicked");
					TableModel model = table.getModel();
					String name = (String)model.getValueAt(index, 0);
					//String address = (String)model.getValueAt(index, 1);
					//String phone = (String)model.getValueAt(index, 2);
					//JOptionPane.showMessageDialog(delete, name);
					
					int id = Integer.parseInt(name);
					
					String sql = "DELETE from sale where id = ?";
					try {
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setInt(1, id);
						statement.executeUpdate();
						((DefaultTableModel) model).removeRow(index);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			});

			add(delete);
		}
	}

	/**
	 * Create the frame.
	 */
	public Sale_record() {
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
		setTitle("Sale Records");
		setBounds(200, 100, 1100, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(163, 139, 840, 215);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table = new JTable(model) {
			// Returning the Class of each column will allow different
			// renderers to be used based on Class

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
		table.setForeground(Color.WHITE);
		table.setFont(table.getFont().deriveFont(18.0f));
		table.setRowHeight(40);
		
		Object[] columns = {"Sales Id", "Prodcut Name", "Company name", "Date of sale", "Quantity", "Price per unit"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		
		final Popup popup = new Popup(table);
		
		textField = new HintTextField("Search anything");
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				search(textField.getText().toString());
			}
		});
		textField.setToolTipText("Search by name");
		textField.setBounds(351, 69, 406, 32);
		contentPane.add(textField);
		textField.setColumns(10);

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
	
	public void load()
	{
		String sql = "select * from sale";
		Object[] row = new Object[6];
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			//table.setModel(DbUtils.resultSetToTableModel(set));
			while(set.next())
			{
				row[0] = set.getString("id");
				row[1] = set.getString("product_name");
				row[2] = set.getString("company_name");
				row[3] = set.getString("date");
				row[4] = set.getString("quantity");
				row[5] = set.getString("price");
				//table.setModel(model);
				model.addRow(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void search(String query)
	{
		TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(rowSorter);
		
		rowSorter.setRowFilter(RowFilter.regexFilter(query));
		
	}
}
