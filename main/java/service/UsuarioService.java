import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.ProdutoDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_DESCRICAO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umUsuario = "";
		if(tipo != FORM_INSERT) {
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/list/1\">Novo Usuario</a></b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String name, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Usuario";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + usuario.getEmail();
				name = "Atualizar Usuario (Email " + usuario.getEmail() + ")";
				buttonLabel = "Atualizar";
			}
			
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-USUARIO>", umUsuario);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Usuarios</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Usuario> usuarios;
		if (orderBy == FORM_ORDERBY_ID) {                 	usuarios = usuarioDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		usuarios = usuarioDAO.getOrderByDescricao();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			usuarios = usuarioDAO.getOrderByPreco();
		} else {											usuarios = usuarioDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Usuario u : usuarios) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += 
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USUARIO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String mail = String.parse(request.queryParams("email"));
        String password = String.parse(request.queryParams("senha"));
        String name = String.parse(request.queryParams("nome"));
        int residencia = Integer.parseInt(request.queryParams("numRes"));
        String comple = String.parse(request.queryParams("complemento"));
        int cep = Integer.parseInt(request.queryParams("CEP"));
        String telephone = String.parse(request.queryParams("telefone"));
        String card = String.parse(request.queryParams("cartao"));
        String cvc = String.parse(request.queryParams("CVC"));
        String val = String.parse(request.queryParams("validade"));
		
		String resp = "";
		
		Usuario usuario = new Usuario(mail, password, name, residencia, comple, cep, telephone, card, cvc, val);
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Usuario inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
	    String email = String.parse(request.params(":email"));		
		Usuario usuario = (Usuario) usuarioDAO.get(email);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + email + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		String email = String.parse(request.params(":email"));		
		Usuario usuario = (Usuario) usuarioDAO.get(email);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + email + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        String email = String.parse(request.params(":email"));
		Usuario usuario = usuarioDAO.get(email);
        String resp = "";       

        if (usuario != null) {
            usuario.setEmail(String.parse(request.queryParams("email")));
            usuario.setSenha(String.parse(request.queryParams("senha")));
            usuario.setNome(String.parse(request.queryParams("nome")));
            usuario.setNumRes(Integer.parseInt(request.queryParams("numRes")));
            usuario.setComplemento(String.parse(request.queryParams("complemento")));
            usuario.setCEP(Integer.parseInt(request.queryParams("CEP")));
            usuario.setTelefone(String.parse(request.queryParams("telefone")));
            usuario.setCartao(String.parse(request.queryParams("cartao")));
            usuario.setCVC(String.parse(request.queryParams("CVC")));
            usuario.setValidade(String.parse(request.queryParams("validade")));
        	usuario.setDataValidade(LocalDate.parse(request.queryParams("dataValidade")));
        	usuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Usuario" + usuario.getNome() + " atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (ID \" + usuario.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        String email = Integer.parseInt(request.params("email"));
        Usuario usuario = usuarioDAO.get(email);
        String resp = "";       

        if (usuario != null) {
            usuarioDAO.delete(email);
            response.status(200); // success
            resp = "Usuario " + email + "excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario " + email + "não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}