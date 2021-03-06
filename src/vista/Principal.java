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
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrase?a;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/imagenes/mugiwara-logo.png")));
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
		
		String pass = String.valueOf(txtContrase?a.getPassword());
		String usu = txtUsuario.getText();
		
		String SQL= "select * from Usuarios where nombre_usuario = '"+usu+"' and contrase?a = '"+pass+"' " ;
		
		try {
			Statement  st = c.createStatement();
			ResultSet rs = st.executeQuery(SQL);
			
			if (rs.next()) {
				result = 1;
				
				if (result == 1) {
					
					if (usu.isEmpty() | pass.isEmpty() ) {
						JOptionPane.showMessageDialog(null, "Falta el nombre o la contrase?a");
					}
					else {
						Crud cru = new Crud();
						cru.setVisible(true);
						this.dispose();
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Error 413, contrase?a o usuario no registrados");
			}
		} 
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error 412");
		}
	}
	
	//codigo de todos los componentes que se muestran en pantalla
	private void initcomponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 703, 421);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Iniciar Sesion");
		lblNewLabel.setBounds(10, 11, 178, 31);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		contentPane.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtUsuario.setBounds(10, 111, 173, 38);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de usuario");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 77, 119, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 170, 119, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnIniciarSesion = new JButton("Iniciar sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceso();
			}
		});
		btnIniciarSesion.setFont(new Font("Dialog", Font.BOLD, 12));
		btnIniciarSesion.setBounds(10, 257, 173, 38);
		contentPane.add(btnIniciarSesion);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarse();
			}
		});
		btnRegistrarse.setBounds(10, 306, 173, 38);
		contentPane.add(btnRegistrarse);
		
		txtContrase?a = new JPasswordField();
		txtContrase?a.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtContrase?a.setBounds(10, 196, 173, 38);
		contentPane.add(txtContrase?a);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 51, 51));
		panel.setBounds(231, 0, 456, 382);
		contentPane.add(panel);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Principal.class.getResource("/imagenes/569ceaf28defb915e5ff6c53cc7665aa.png")));
		panel.add(lblNewLabel_3);
	}
}
