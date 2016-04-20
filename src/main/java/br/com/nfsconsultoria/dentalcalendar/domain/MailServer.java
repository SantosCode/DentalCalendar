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
public class MailServer extends GenericDomain {
    
    @Column(nullable = false, length = 30, unique = true)
    private String nome;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(length = 45)
    private String senha;

    @Column(nullable = false, length = 45)
    private String servidor;

    @Column(nullable = false, length = 5)    
    private String porta;
    
    @Column
    private Boolean tssl;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }   

    public Boolean getTssl() {
        return tssl;
    }

    public void setTssl(Boolean tssl) {
        this.tssl = tssl;
    }
}
