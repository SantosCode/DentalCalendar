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
 * @author luissantos
 */
@SuppressWarnings("serial")
@Entity
public class Secretaria extends GenericDomain{
    
    @Column(nullable = false, length = 45, unique = true)
    private String nome;
    
    @Column(length = 15)
    private String telefone;
    
    @Column(length = 45)
    private String email;
    
    @Column(length = 2)
    private Integer diaNasc;
    
    @Column(length = 15)
    private String mesNasc;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDiaNasc() {
        return diaNasc;
    }

    public void setDiaNasc(Integer diaNasc) {
        this.diaNasc = diaNasc;
    }

    public String getMesNasc() {
        return mesNasc;
    }

    public void setMesNasc(String mesNasc) {
        this.mesNasc = mesNasc;
    }

}
