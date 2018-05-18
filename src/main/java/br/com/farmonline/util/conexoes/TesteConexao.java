package br.com.farmonline.util.conexoes;

import br.com.farmonline.usuario.Usuario;
import br.com.farmonline.usuario.UsuarioRN;

public class TesteConexao {

	public static void main(String[] args) {
		UsuarioRN rn = new UsuarioRN();
		
		//List<Usuario> lista = rn.listar();
		
		//System.out.println(lista.size());
		
		
		Usuario user =new Usuario();
		user = rn.carregar(1);
		user.setAtivo(true);
		//rn.salvar(user);
		
	}
}


