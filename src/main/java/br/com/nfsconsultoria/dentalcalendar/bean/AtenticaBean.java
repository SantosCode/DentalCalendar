/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.domain.Representante;
import java.io.IOException;
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
public class AtenticaBean {

    private Representante representante;

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    @PostConstruct
    public void iniciar() {
        representante = new Representante();
    }

    public void autenticar() {
        try {
            Faces.redirect("./pages/principal.xhtml");
        } catch (IOException erro) {
            erro.printStackTrace();
            Messages.addGlobalError(erro.getMessage());
        }
    }
}
