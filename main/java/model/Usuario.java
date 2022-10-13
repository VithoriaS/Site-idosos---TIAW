public class Usuario {
    private String email;
    private String senha;
    private String nome;
    private int numRes;
    private String complemento;
    private int CEP;
    private String telefone;
    private String cartao;
    private String CVC;
    private String validade;
	
	public Usuario() {
		email = "";
        senha = "";
        nome = "";
        numRes = 0;
        complemento = "";
        CEP = 0;
        telefone = "";
        cartao = "";
        CVC = "";
        validade = "";
	}

	public Usuario(String mail, String password, String name, int residencia, String comple, int cep, String telephone, String card, String cvc, String val) 
    {
        setEmail(mail);
        setSenha(password);
        setNome(name);
        setNumRes(residencia);
        setComplemento(comple);
        setCEP(cep);
        setTelefone(telephone);
        setCartao(card);
        setCVC(cvc);
        setValidade(val);
	}

    public void setEmail(String mail){email = mail;}
    public String getEmail(){return email;}

    public void setSenha(String password){senha = password;}
    public String getSenha(){return senha;}

    public void setNome(String name){nome = name;}
    public String getNome(){return nome;}

    public void setNumRes(int residencia){numRes = residencia;}
    public int getNumRes(){return numRes;}

    public void setComplemento(String comple){complemento = comple;}
    public String getComplemento(){return complemento;}

    public void setCEP(int cep){CEP = cep;}
    public int getCEP(){return CEP;}

    public void setTelefone(String telephone){telefone = telephone;}
    public String getTelefone(){return telefone;}

    public void setCartao(String card){cartao = card;}
    public String getCartao(){return cartao;}

    public void setCVC(String cvc){CVC = cvc;}
    public String getCVC(){return CVC;}

    public void setValidade(String val){validade = val;}
    public String getValidade(){return validade;}

	@Override
	public String toString() {
		return "Email: " + email + "Senha: " + senha + "Nome: " + nome + "Num. Residencia: " + numRes + "Complemento: " + complemento + "CEP: " + CEP + "Telefone: " + telefone + "Cartao: " + cartao + "CVC: " + CVC + "Validade: " + validade;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((this.getEmail() == ((Usuario) obj).getEmail()) && (this.getSenha() == ((Usuario) obj).getSenha()));
	}	
}
