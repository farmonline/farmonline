package br.com.farmonline.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;

import br.com.farmonline.usuario.Usuario;
import br.com.farmonline.usuario.UsuarioRN;
import br.com.farmonline.util.conexoes.HibernateUtil;

@ManagedBean(name="usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {


	private static final long serialVersionUID = 5378234700750836293L;
	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;
	private boolean logado = false;

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String logout() {
		 Session sessao = HibernateUtil.getSessionFactory().getCurrentSession();
		 
		 this.usuario= new Usuario();
		 this.usuario.getPermissao().remove("ROLE_ADMINISTRADOR");
		 this.usuario.getPermissao().remove("ROLE_USUSARIO");
		 this.destinoSalvar = "index";
		 
		
		return this.destinoSalvar;
	}
	
	public String logar() {
		UsuarioRN usuarioRN = new UsuarioRN();
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuarioLogin = usuarioRN.autenticar(this.usuario.getEmail(), this.usuario.getSenha());
		
		if (usuarioLogin.getCodigo() == null || usuarioLogin.getCodigo() == 0) {
			FacesMessage facesMessage = new FacesMessage("Usuário não localizado, tente novamente!");
			context.addMessage(null, facesMessage);
			this.setLogado(false);
			return null;

		} else {
			this.destinoSalvar = "";
			this.setLogado(true);

			this.setUsuario(usuarioLogin);

			if (this.usuario.getPermissao().contains("ROLE_ADMINISTRADOR")) {
				this.destinoSalvar = "/home";
			}
			if (this.usuario.getPermissao().contains("ROLE_USUARIO")) {
				this.destinoSalvar = "/home";
			}
			

		}

		Usuario logado = usuarioRN.autenticar(this.usuario.getEmail(), this.usuario.getSenha());

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário inválido", "Usuário inválido");

		if (logado.getCodigo() == null) {
			context.addMessage(null, msg);
		} else {
			this.usuario = logado;
			this.logado = true;

			if (this.usuario.getPermissao().contains("ROLE_ADMINISTRADOR")) {
				this.destinoSalvar = "home";
			} else {
				this.destinoSalvar = "home";
			}
		}

		return this.destinoSalvar;

	}
	
	public String gerencia() {
		if(this.usuario.getPermissao().contains("ROLE_ADMINISTRADOR")) {
			this.destinoSalvar = "gerencia-usuario";
		}else {
			this.destinoSalvar = null;
		}
		return this.destinoSalvar;
	}
	
	public String mapa() {
		if(this.usuario.getPermissao().contains("ROLE_USUARIO")) {
			this.destinoSalvar = "list-map";
		}else {
			this.destinoSalvar = null;
		}
		return this.destinoSalvar;
	}
	public String chamaLogin() {
		if(this.usuario.getCodigo()!=null&& this.usuario.getPermissao().contains("ROLE_USUARIO")) {
			this.destinoSalvar = "/home";
		}else {
			this.destinoSalvar = "login";
		}
		return this.destinoSalvar;
	}
	
	public String novo() {
		System.out.println("Novo usuário solicitado");
		if (this.usuario.getCodigo() == null || this.usuario.getCodigo() == 0) {
			this.destinoSalvar = "/usuario";
			this.usuario = new Usuario();

		} else {
			this.destinoSalvar = "/index";
		}
		return this.destinoSalvar;
	}

	public String editar() {

		this.destinoSalvar = "editar-usuario";

		return this.destinoSalvar;
	}

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		UsuarioRN rn = new UsuarioRN();
		String senha = this.usuario.getSenha();
		if (!senha.equals(this.confirmarSenha)) {
			this.destinoSalvar = null;
			FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente");
			context.addMessage(null, facesMessage);

		} else {
 
			if (this.usuario.getPermissao().contains("ROLE_ADMINISTRADOR")|| this.usuario.getPermissao().contains("ROLE_USUARIO")) {
				this.destinoSalvar = "home";
				rn.salvar(this.usuario);
			} else {
				
				this.usuario.getPermissao().add("ROLE_USUARIO");
				this.destinoSalvar = "home";
				
				rn.salvar(this.usuario);
			}
			
		
		}
		
		return this.destinoSalvar;
	}

	public String atualizar() {
		
		return "usuario";

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmarSenha == null) ? 0 : confirmarSenha.hashCode());
		result = prime * result + (confirmarTermos ? 1231 : 1237);
		result = prime * result + ((destinoSalvar == null) ? 0 : destinoSalvar.hashCode());
		result = prime * result + ((lista == null) ? 0 : lista.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioBean other = (UsuarioBean) obj;
		if (confirmarSenha == null) {
			if (other.confirmarSenha != null)
				return false;
		} else if (!confirmarSenha.equals(other.confirmarSenha))
			return false;
		if (confirmarTermos != other.confirmarTermos)
			return false;
		if (destinoSalvar == null) {
			if (other.destinoSalvar != null)
				return false;
		} else if (!destinoSalvar.equals(other.destinoSalvar))
			return false;
		if (lista == null) {
			if (other.lista != null)
				return false;
		} else if (!lista.equals(other.lista))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String excluir() {
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.excluir(this.usuario);
		this.lista = null;
		return null;
	}

	public boolean confirmarTermos;

	public boolean confirmar() {
		if (this.confirmarTermos == true) {
			this.confirmarTermos = false;
		} else {
			this.confirmarTermos = true;
		}

		return this.confirmarTermos;

	}

	public boolean isConfirmarTermos() {
		return confirmarTermos;
	}

	public void setConfirmarTermos(boolean confirmarTermos) {
		this.confirmarTermos = confirmarTermos;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public String ativar() {
		if (this.usuario.isAtivo())
			this.usuario.setAtivo(false);
		else
			this.usuario.setAtivo(true);

		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);
		return null;
	}

	public List<Usuario> getLista() {
		if (this.lista == null) {
			UsuarioRN usuarioRN = new UsuarioRN();
			this.lista = usuarioRN.listar();
		}
		return this.lista;
	}

	public String atribuiPermissao(Usuario usuario, String permissao) {
		this.usuario = usuario;
		UsuarioRN rn = new UsuarioRN();
		
		java.util.Set<String> permissoes = this.usuario.getPermissao();
		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
			rn.salvar(this.usuario);
		} else {
			permissoes.add(permissao);
			rn.salvar(this.usuario);
		}
		
		return null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

}
