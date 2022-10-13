package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO extends DAO {	
	public UsuarioDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Usuario Usuario) {
		boolean status = false;
		try {
			String sql = "INSERT INTO usuario (email, senha , nome, CEP, numero residencial, complemento, telefone, cartao de credito, CVC, validade do cartao) "
		               + "VALUES ('" + Usuario.getEmail() + "', " + usuario.getSenha() + ", "
		               + Usuario.getNome() + ", " + Usuario.getCEP() + ", " + Usuario.getNumRes() + ", "
					   + Usuario.getComplemento() + ", " + Usuario.getTelefone() + ", " +  Usuario.getCartao() + "," 
					   + Usuario.getCVC() + ", " + Usuario.getValidade();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Usuario get(String email) {
		Usuario Usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Usuario WHERE email="+email;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 Usuario = new Usuario(rs.getString("email"), rs.getString("senha"), rs.getString("nome"), rs.getInt("CEP"), rs.getInt("numero residencial"), rs.getString("complemento"), rs.getString("telefone"), rs.getString("cartao"), rs.getString("CVC"), rs.getString("validade"))  ;
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return Usuario;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}
	
	
	public List<Usuario> getOrderByemail() {
		return get("email");		
	}
	
	
	public List<Usuario> getOrderBynome() {
		return get("nome");		
	}
	
	
	
	public boolean update(Usuario Usuario) {
		boolean status = false;
		try {  
			String sql = "UPDATE Usuario SET email = '" + Usuario.getEmail() + "', senha =" + usuario.getSenha() + ",nome="
			+ Usuario.getNome() + ", CEP= " + Usuario.getCEP() + ", Numero residencial= " + Usuario.getNumRes() + ", Complemento = "
			+ Usuario.getComplemento() + ", Telefone = " + Usuario.getTelefone() + ", Cartao de Crédito = " +  Usuario.getCartao() + ", CVC = " 
			+ Usuario.getCVC() + ", Validade = " + Usuario.getValidade() + "WHERE email = " + Usuario.getEmail() ;
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(String email) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM Usuario WHERE email = " + email);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}