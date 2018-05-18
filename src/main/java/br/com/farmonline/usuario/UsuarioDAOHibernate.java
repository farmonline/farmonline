
package br.com.farmonline.usuario;



import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAOHibernate implements UsuarioDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	public void salvar(Usuario usuario) {
		usuario.setAtivo(true);
		usuario.setUsername(usuario.getEmail());
		this.session.save(usuario);
		System.out.println("Usuario Salvo");
		
	}

	public void atualizar(Usuario usuario) {
		System.out.println("Iniciando o processo");
		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			usuario.setPermissao(usuarioPermissao.getPermissao());
			this.session.evict(usuarioPermissao);
		}
		this.session.update(usuario);
	
		System.out.println("Usuario Atualizado");
	}

	public void excluir(Usuario usuario) {
		this.session.delete(usuario);
		System.out.println("Usuario excluído");
		}


	public Usuario carregar(Integer codigo) {
	
		Usuario usuario = (Usuario) this.session.get(Usuario.class, codigo);
		
		System.out.println("Usuario localizado: "+usuario.getNome()+" "+usuario.getSobreNome()+"!");
		return usuario;
	}


	public List<Usuario> listar() {
		
		return this.session.createCriteria(Usuario.class).list();
	}


	/*public Usuario buscarPorLogin(String login) {
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("login", login);
		return (Usuario) consulta.uniqueResult();
	}*/

	@Override
	public Usuario buscarPorCPF(String cpf) {
		/*Usuario usuario =(Usuario) this.session.createCriteria(Usuario.class).add(Restrictions.eq("cpf", cpf)).uniqueResult();
		return usuario;
	*/
		String hql = "select u from Usuario u where u.cpf = :cpf";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("cpf", cpf);
		return (Usuario) consulta.uniqueResult();
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		/*Usuario usuario =  (Usuario) this.session.createCriteria(Usuario.class).add(Restrictions.eq("email", email)).uniqueResult();
		return usuario;*/
		
		String hql = "select u from Usuario u where u.email = :email";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("email", email);
		return (Usuario) consulta.uniqueResult();
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		
		Usuario usuario =  (Usuario) this.session.createCriteria(Usuario.class).add
				(Restrictions.eq("email", email)).add(Restrictions.eq("senha", senha)).uniqueResult();
	
		return usuario;

	}
}
