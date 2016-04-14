package br.com.nfsconsultoria.dentalcalendar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Visita extends GenericDomain {

    @Column(nullable = false, length = 300)
    private String acordo;
    
    @Column
    private Boolean email;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Agenda agenda;

    public String getAcordo() {
        return acordo;
    }

    public void setAcordo(String acordo) {
        this.acordo = acordo;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
