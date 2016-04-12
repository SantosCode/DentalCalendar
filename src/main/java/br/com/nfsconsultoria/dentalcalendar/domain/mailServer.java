/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.domain;

import br.com.nfsconsultoria.dentalcalendar.domain.GenericDomain;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author luissantos
 */
@SuppressWarnings("serial")
@Entity
public class mailServer extends GenericDomain {

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(length = 45)
    private String senha;

    @Column(nullable = false, length = 45)
    private String servidor;

    @Column(nullable = false, length = 5)    
    private Integer porta;
    
    @Column
    private Boolean ttls;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }

    public Boolean getTtls() {
        return ttls;
    }

    public void setTtls(Boolean ttls) {
        this.ttls = ttls;
    }
    
    

}
