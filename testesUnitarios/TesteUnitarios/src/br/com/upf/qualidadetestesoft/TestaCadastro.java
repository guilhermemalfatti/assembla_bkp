package br.com.upf.qualidadetestesoft;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestaCadastro {
	private Cadastro cad;
	
	//Cria um objeto Cadastro antes de rodar os testes
	@Before
	public void instanciarCadastro(){
		cad = new Cadastro();
		cad.setApelido("Apelido");
		cad.setCpf("49883241763");
		
		cad.setEmail("teste@gmail.com");
		cad.setConfirmaEmail("teste@gmail.com");
		
		cad.setSenha("123456");
		cad.setConfirmaSenha("123456");
	}
	
	//Sinaliza ao coletor de lixo que o objeto já pode ser destruido
	@After
	public void destruirCadastro(){
		cad = null;
	}
	
	@Test
	public void testValidaEmail() {
		assertTrue("O e-mail "+cad.getEmail()+" e invalido", cad.validaEmail());
	}

	@Test
	public void testValidaCpf() {
		assertTrue("O CPF "+cad.getCpf()+" e invalido", cad.validaCpf());
	}

	@Test
	public void testValidaSenha() {
		assertTrue("O campo senha ou confirmacao de senha estao incorretos", cad.validaSenha());
	}

}
