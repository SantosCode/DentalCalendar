package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luissantos on 26/04/16.
 */
@ManagedBean
@ViewScoped
public class RelatorioBean {

    public void Dentista() {

        String jrxml = "/relatorios/dentista.jrxml";
        Map<String, Dentista> parametros = new HashMap<>();

    }

}
