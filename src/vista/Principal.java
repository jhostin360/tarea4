package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		initcomponents();
	}
	
	//conexion global
	Conexion conx = new Conexion();
	Connection c = conx.conex();
	
	//metodos
	public void registrarse() {
		Registro registro = new Registro();
		registro.setVisible(true);
		this.dispose();
	}
	
	public void acceso() {
		int result = 0;
		
		String pass = String.valueOf(txtContraseña.getPassword());
		String usu = txtUsuario.getText();
		
		String SQL= "select * from Usuarios where nombre_usuario = '"+usu+"' and contraseña = '"+pass+"' " ;
		
		try {
			Statement  st = c.createStatement();
			ResultSet rs = st.executeQuery(SQL);
			
			if (rs.next()) {
				result = 1;
				
				if (result == 1) {
					
					if (usu.isEmpty() | pass.isEmpty() ) {
						JOptionPane.showMessageDialog(null, "Falta el nombre o la contraseña");
					}
					else {
						Crud cru = new Crud();
						cru.setVisible(true);
						this.dispose();
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Error 413, contraseña o usuario no registrados");
			}
		} 
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error 412");
		}
	}
	
	//codigo de todos los componentes que se muestran en pantalla
	private void initcomponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Iniciar Sesion");
		lblNewLabel.setBounds(5, 5, 424, 23);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 17));
		contentPane.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtUsuario.setBounds(132, 87, 173, 38);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de usuario");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1.setBounds(132, 60, 119, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_2.setBounds(132, 147, 119, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnIniciarSesion = new JButton("Iniciar sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceso();
			}
		});
		btnIniciarSesion.setFont(new Font("Dialog", Font.BOLD, 12));
		btnIniciarSesion.setBounds(132, 221, 173, 38);
		contentPane.add(btnIniciarSesion);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarse();
			}
		});
		btnRegistrarse.setBounds(132, 270, 173, 38);
		contentPane.add(btnRegistrarse);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtContraseña.setBounds(132, 172, 173, 38);
		contentPane.add(txtContraseña);
	}
}
