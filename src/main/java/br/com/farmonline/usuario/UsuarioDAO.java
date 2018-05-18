package br.com.farmonline.usuario;

import java.util.List;

public interface UsuarioDAO {
	public void salvar(Usuario usuario);

	public void atualizar(Usuario usuario);

	public void excluir(Usuario usuario);

	public Usuario carregar(Integer codigo);

	public Usuario buscarPorCPF(String cpf);
	
	
	public Usuario buscarPorEmail(String email);
	
	
	public Usuario autenticar(String email, String senha);

	public List<Usuario> listar();
}
