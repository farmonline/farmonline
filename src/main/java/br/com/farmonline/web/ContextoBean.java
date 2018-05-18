package br.com.farmonline.web;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.farmonline.usuario.Usuario;
import br.com.farmonline.usuario.UsuarioRN;

@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7134541674788233436L;

	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		if (login != null) {
			UsuarioRN usuarioRN = new UsuarioRN();
			Usuario usuario = usuarioRN.buscarPorEmail(login);
			return usuario;
		}
		return null;
	}
}
