/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@Entity
public class Datas extends GenericDomain{
    
    @Column(nullable = false, length = 45)
    private String evento;
    
    @Column(nullable = false, length = 10)
    private String dia;
    
    @Column(nullable = false, length = 10)
    private String mes;
    
    @Column(nullable = false, length = 45, unique = true)
    private String nome;

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
}
