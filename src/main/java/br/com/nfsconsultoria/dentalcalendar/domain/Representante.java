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
public class Representante extends GenericDomain{
    
    @Column(nullable = false, length = 45, unique = true)
    private String nome;
    
    @Column(nullable = true, length = 15)
    private String telcel;
    
    @Column(nullable = false, length = 45)
    private String email;
    
    @Column(nullable = false, length = 20, unique = true)
    private String login;
    
    @Column(nullable = false, length = 32)
    private String senha;

    @Column(nullable = false)
    private String admin;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelcel() {
        return telcel;
    }

    public void setTelcel(String telcel) {
        this.telcel = telcel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
