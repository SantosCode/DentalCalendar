/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.RepresentanteDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Representante;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

/**
 *
 * @author luis
 */
@ManagedBean
@SessionScoped
public class AutenticaBean {

    private Representante representante;
    private List<Representante> representantes;
    private String user;
    private String pass;

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public List<Representante> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Representante> representantes) {
        this.representantes = representantes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @PostConstruct
    public void iniciar() {
         
        representante = new Representante();
    }

    public void autenticar() throws IOException {
        try {
            RepresentanteDAO repreDAO = new RepresentanteDAO();
            this.representantes = repreDAO.listar();
            if (user.equals(representante.getLogin())
                    && pass.equals(representante.getSenha())) {
                Faces.redirect("./pages/index.xhtml");
                Messages.addGlobalInfo("Seja bem vindo " + representante.getNome());
            } else {
                Messages.addGlobalError("Usuário ou senha invalida");
            }
        } catch (RuntimeException erro) {
            erro.printStackTrace();
            Messages.addGlobalError(erro.getMessage());
        }
    }
}
