package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Conexion;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registro extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCorreo;
	private JTextField txtTelefono;
	private JTextField txtNombre_usuario;
	private JPasswordField txtContra;
	private JPasswordField txtConfirnContra;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public Registro() {
		Componentes();
	}
	
	//conexion
	Conexion conx = new Conexion();
	Connection c = conx.conex();
	
	//metodos
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
		String SQL= "insert into Usuarios (nombre, apellido, nombre_usuario, numero_telefono, correo_electronico, contrase�a) values (?, ?, ?, ?, ?, ?)";
		
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
				JOptionPane.showMessageDialog(null, "Falta la confimacion de la contrase�a");
			}
			else if (pass.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Falta la contrase�a");
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
					
					
					Crud cru = new Crud();
					cru.setVisible(true);
					this.dispose();
				
				}
				else {
					JOptionPane.showMessageDialog(null, "Las contrase�as no coinciden");
					limpiar();
				}
			}
			
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Registro no exitoso" + ex.getMessage());
			limpiar();
		}
		
	}
	
	private void Componentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(108, 157, 224, 33);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(108, 201, 224, 33);
		contentPane.add(txtApellido);
		
		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(108, 245, 224, 33);
		contentPane.add(txtCorreo);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(108, 289, 224, 33);
		contentPane.add(txtTelefono);
		
		txtNombre_usuario = new JTextField();
		txtNombre_usuario.setColumns(10);
		txtNombre_usuario.setBounds(108, 113, 224, 33);
		contentPane.add(txtNombre_usuario);
		
		JLabel lblNewLabel = new JLabel("Registro");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel.setBounds(176, 50, 88, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setBounds(52, 122, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nombre");
		lblNewLabel_1_1.setBounds(52, 166, 46, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Apellido");
		lblNewLabel_1_2.setBounds(52, 210, 46, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Correo");
		lblNewLabel_1_3.setBounds(52, 254, 46, 14);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Telefono");
		lblNewLabel_1_4.setBounds(52, 298, 46, 14);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1_5.setBounds(41, 342, 57, 14);
		contentPane.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("Confirmar");
		lblNewLabel_1_6.setBounds(41, 386, 57, 14);
		contentPane.add(lblNewLabel_1_6);
		
		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregar();
			}
		});
		btnNewButton.setBounds(342, 333, 89, 77);
		contentPane.add(btnNewButton);
		
		txtContra = new JPasswordField();
		txtContra.setBounds(108, 339, 224, 33);
		contentPane.add(txtContra);
		
		txtConfirnContra = new JPasswordField();
		txtConfirnContra.setBounds(108, 383, 224, 33);
		contentPane.add(txtConfirnContra);
	}
}