/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.domain;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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
    
    @OneToMany(mappedBy = "secretaria1", fetch = FetchType.EAGER)
    Set<Dentista> dentista1;
    
    @OneToMany(mappedBy = "secretaria2" ,fetch = FetchType.EAGER)
    Set<Dentista> dentista2;
    
    @OneToMany(mappedBy = "secretaria3", fetch = FetchType.EAGER)
    Set<Dentista> dentista3;
    
    @OneToMany(mappedBy = "secretaria4", fetch = FetchType.EAGER)
    Set<Dentista> dentista4;
    
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

    public Set<Dentista> getDentista1() {
        return dentista1;
    }

    public void setDentista1(Set<Dentista> dentista1) {
        this.dentista1 = dentista1;
    }

    public Set<Dentista> getDentista2() {
        return dentista2;
    }

    public void setDentista2(Set<Dentista> dentista2) {
        this.dentista2 = dentista2;
    }

    public Set<Dentista> getDentista3() {
        return dentista3;
    }

    public void setDentista3(Set<Dentista> dentista3) {
        this.dentista3 = dentista3;
    }

    public Set<Dentista> getDentista4() {
        return dentista4;
    }

    public void setDentista4(Set<Dentista> dentista4) {
        this.dentista4 = dentista4;
    }
}
