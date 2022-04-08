package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import modelo.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Crud extends JFrame {

	private JPanel contentPane;
	private JTable tblUsuarios;
	private JTextField txtNombre_usuario;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCorreo;
	private JTextField txtTelefono;
	private JPasswordField txtContra;
	private JPasswordField txtConfirnContra;
	private JTextField txtID;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Crud frame = new Crud();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Crud() {
		Complementos();
		mostrar();
	}
	
	//conexion
	Conexion conx = new Conexion();
	Connection c = conx.conex();
	
	//metodos
	public void cerrar() {
		Principal princi = new Principal();
		princi.setVisible(true);
		this.dispose();
	}
	
	public void limpiar() {
		txtNombre_usuario.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtCorreo.setText("");
		txtTelefono.setText("");
		txtContra.setText("");
		txtConfirnContra.setText("");
	}
	
	public void agregar() {
		
		String pass = String.valueOf(txtContra.getPassword());
		String passConfirn = String.valueOf(txtConfirnContra.getPassword());
		String SQL= "insert into Usuarios (nombre, apellido, nombre_usuario, numero_telefono, correo_electronico, contraseña) values (?, ?, ?, ?, ?, ?)";
		
		try {
			
			if (txtNombre.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta el nombre");
			}
			else if (txtApellido.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta el apellido");
			}
			else if (txtNombre_usuario.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta el usuario");
			}
			else if (passConfirn.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta la confimacion de la contraseña");
			}
			else if (pass.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta la contraseña");
			}
			else if (txtTelefono.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta el telefono");
			}
			else if (txtCorreo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta el Correo electronico");
			} 
			else {
				
				if (pass.equals(passConfirn)) {
					PreparedStatement pst = c.prepareStatement(SQL);
					pst.setString(1, txtNombre.getText());
					pst.setString(2, txtApellido.getText());
					pst.setString(3, txtNombre_usuario.getText());
					pst.setString(4, txtTelefono.getText());
					pst.setString(5, txtCorreo.getText());
					pst.setString(6, pass);
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Registro exitoso");
					limpiar();
					mostrar();
				}
				else {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
				}
			}
			
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Registro no exitoso" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		
		PreparedStatement ps;
		ResultSet rs;
		String SQL= "select id, nombre, apellido, nombre_usuario, contraseña, numero_telefono, correo_electronico from Usuarios where id = ?";
		
		try {
			int fila = tblUsuarios.getSelectedRow();
			int id = Integer.parseInt(tblUsuarios.getValueAt(fila, 0).toString());
			
			ps = c.prepareStatement(SQL);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				txtID.setText(String.valueOf(id));
				txtNombre.setText(rs.getString("nombre"));
				txtApellido.setText(rs.getString("apellido"));
				txtNombre_usuario.setText(rs.getString("nombre_usuario"));
				txtContra.setText(rs.getString("contraseña"));
				txtTelefono.setText(rs.getString("numero_telefono"));
				txtCorreo.setText(rs.getString("correo_electronico"));
			}
		}
		catch (SQLException ex) {
			
			JOptionPane.showMessageDialog(null, ex.toString());
		}
	}
	
	public void mostrar() {
		
		DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
		modelo.setRowCount(0);
		
		PreparedStatement ps;
		ResultSet rs;
		ResultSetMetaData md;
		int columnas;
		
		String SQL= "select * from Usuarios";
		
		
		try {
			
			ps = c.prepareStatement(SQL);
			rs = ps.executeQuery();
			md = rs.getMetaData();	
			columnas = md.getColumnCount();
			
			while (rs.next()) {
				Object[] fila = new Object [columnas];
				
				for (int i = 0; i < columnas; i++) {
					
					fila[i] = rs.getObject(i + 1);
				}
				 modelo.addRow(fila);
			}
			
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}
	}
	
	public void boton_editar( ) {
		
		String pass = String.valueOf(txtContra.getPassword());
		int id = Integer.parseInt(txtID.getText());
		String nombre = txtNombre.getText();
		String apellido = txtApellido.getText();
		String nombre_usuario = txtNombre_usuario.getText();
		String contraseña = pass;
		String numero_telefono = txtTelefono.getText();
		String correo_electronico = txtCorreo.getText();
		String SQL= "update Usuarios set nombre = ?, apellido = ?, nombre_usuario = ?, contraseña = ?, numero_telefono = ?, correo_electronico = ? where id = ?";
		
		try {
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, nombre);
			ps.setString(2, apellido);
			ps.setString(3, nombre_usuario);
			ps.setString(4, contraseña);
			ps.setString(5, numero_telefono);
			ps.setString(6, correo_electronico);
			ps.setInt(7, id);
			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Registro Actualizado");
			limpiar();
			mostrar();
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}
	}
	
	public void borrar() {
		
		int id = Integer.parseInt(txtID.getText());
		String SQL= "delete from Usuarios where id = ?";
		
		try {
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, id);
			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Registro Eliminado");
			limpiar();
			mostrar();
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.toString());
		}
	}
	
	private void Complementos() {
		setTitle("CRUD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 810, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("");
		panel.setBounds(456, 21, 328, 410);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 29, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nombre");
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(10, 82, 46, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Apellido");
		lblNewLabel_1_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1_2.setBounds(10, 131, 46, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Correo");
		lblNewLabel_1_3.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1_3.setBounds(10, 186, 46, 14);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Telefono");
		lblNewLabel_1_4.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1_4.setBounds(10, 243, 49, 14);
		panel.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1_5.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1_5.setBounds(10, 300, 71, 14);
		panel.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("Confirmar");
		lblNewLabel_1_6.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1_6.setBounds(10, 362, 57, 14);
		panel.add(lblNewLabel_1_6);
		
		txtNombre_usuario = new JTextField();
		txtNombre_usuario.setColumns(10);
		txtNombre_usuario.setBounds(91, 21, 224, 33);
		panel.add(txtNombre_usuario);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(91, 74, 224, 33);
		panel.add(txtNombre);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(91, 123, 224, 33);
		panel.add(txtApellido);
		
		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(91, 178, 224, 33);
		panel.add(txtCorreo);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(91, 235, 224, 33);
		panel.add(txtTelefono);
		
		txtContra = new JPasswordField();
		txtContra.setBounds(91, 292, 224, 33);
		panel.add(txtContra);
		
		txtConfirnContra = new JPasswordField();
		txtConfirnContra.setBounds(91, 354, 224, 33);
		panel.add(txtConfirnContra);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregar();
			}
		});
		btnAgregar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnAgregar.setBounds(214, 334, 111, 43);
		contentPane.add(btnAgregar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrar();
			}
		});
		btnBorrar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnBorrar.setBounds(91, 388, 111, 43);
		contentPane.add(btnBorrar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boton_editar();
			}
		});
		btnEditar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnEditar.setBounds(335, 334, 111, 43);
		contentPane.add(btnEditar);
		
		JButton btnCerrarSesion = new JButton("Cerrar sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrar();
			}
		});
		btnCerrarSesion.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCerrarSesion.setBounds(212, 388, 111, 43);
		contentPane.add(btnCerrarSesion);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setBounds(333, 388, 111, 43);
		contentPane.add(btnLimpiar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		scrollPane.setBounds(26, 21, 420, 297);
		contentPane.add(scrollPane);
		
		tblUsuarios = new JTable();
		tblUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editar();
			}
		});
		scrollPane.setViewportView(tblUsuarios);
		tblUsuarios.setFont(new Font("Dialog", Font.BOLD, 11));
		tblUsuarios.setModel(new DefaultTableModel(
			new Object[][] {
				{"", "", null, null, null, null, null},
			},
			new String[] {
				"Id", "Nombre", "Apellido", "Usuario", "Contrase\u00F1a", "Telefono", "Correo Electronico"
			}
		));
		
		txtID = new JTextField();
		txtID.setBounds(52, 334, -1, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);
	}
}
