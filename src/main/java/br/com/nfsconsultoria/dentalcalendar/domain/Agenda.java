/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@Entity
public class Agenda extends GenericDomain{
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dia;
    
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date hora;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Dentista dentista;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Representante representante;

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }
}
