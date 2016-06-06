package br.com.upf.qualidadetestesoft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cadastro {
	String apelido;
	String cpf;
	String email;
	String confirmaEmail;
	String senha;
	String confirmaSenha;
	
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfirmaEmail() {
		return confirmaEmail;
	}
	public void setConfirmaEmail(String confirmaEmail) {
		this.confirmaEmail = confirmaEmail;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	public boolean validaEmail(){
		System.out.println("Metodo de validacao de email");
	    Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
	    Matcher m = p.matcher(this.email); 
	    if (m.find()){
	      System.out.println("O email "+this.email+" e valido");
	      return true;
	    }
	    else{
	      System.out.println("O E-mail "+this.email+" é inválido");
	      return false;
	    }
	}
	
	public boolean validaCpf(){
		return new CPFUtil(this.cpf).isValido();
	}
	
	public boolean validaSenha(){
		if(this.senha.equals(this.confirmaSenha) && this.senha.length() >= 6 && this.confirmaSenha.length() >= 6){
			return true;
		} else {
			return false;
		}
		
	}
}
