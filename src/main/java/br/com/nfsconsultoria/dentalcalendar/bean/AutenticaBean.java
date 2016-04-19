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
    private Representante representanteLogado;
   
    private List<Representante> representantes;
    
    public AutenticaBean(){
        RepresentanteDAO repreDAO = new RepresentanteDAO();
        this.representantes = repreDAO.listar();
    }

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

    public Representante getRepresentanteLogado() {
        return representanteLogado;
    }

    public void setRepresentanteLogado(Representante representanteLogado) {
        this.representanteLogado = representanteLogado;
    }
    
    public boolean isLogado(){
        return false;
    }

    @PostConstruct
    public void iniciar() {
         
        representante = new Representante();
        representanteLogado = new Representante();
    }

    public void autenticar(){
        try {
            RepresentanteDAO repreDAO = new RepresentanteDAO();
            representanteLogado = repreDAO.autenticar(representante.getLogin(), representante.getSenha());
           
            if (representanteLogado == null){
                  
                Messages.addGlobalError(" Representante e/ou senha incorretos ");
                return;
            } else {
                Messages.addGlobalInfo("Seja bem vindo " + representante.getNome());
              isLogado();
                Faces.redirect("./pages/index.xhtml");
            }
        } catch (IOException erro) {
            erro.printStackTrace();
            Messages.addGlobalError(erro.getMessage());
        }
    }
}
