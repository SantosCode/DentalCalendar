/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@Entity
public class Dentista extends GenericDomain{
    
    @Column(nullable = false, length = 45, unique = true)
    private String nome;
    
    @Column(nullable = true, length = 6, unique = true)
    private String cro;
    
    @Column(nullable = false, length = 15)
    private String telfixo;
    
    @Column(nullable = true, length = 15)
    private String telcel;
    
    @Column(nullable = true, length = 45)
    private String email;
    
    @Column(nullable = false, length = 80)
    private String rua;
    
    @Column(nullable = true, length = 45)
    private String complemento;
    
    @Column(nullable = false, length = 45)
    private String bairro;
    
    @Column(nullable = true, length = 10)
    private String cep;
    
    @Column(nullable = false, length = 45)
    private String cidade;
    
    @Column(nullable = true, length = 45)
    private String nomeSec;
   
    @Column(nullable = true, length = 15)
    private String telCelSec;
    
    @Column(length = 2)
    private Integer diaNasc;
    
    @Column(length = 10)
    private String mesNasc;
    
    @Column(length = 2)
    private Integer diaNascSec;
    
    @Column(length = 10)
    private String mesNascSec;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Especial especialidade;
    
    @ManyToOne
    @JoinColumn(nullable = true)
    private Radiologia radiologia;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public String getTelfixo() {
        return telfixo;
    }

    public void setTelfixo(String telfixo) {
        this.telfixo = telfixo;
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

    public String getRua() {
        return rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNomeSec() {
        return nomeSec;
    }

    public void setNomeSec(String nomeSec) {
        this.nomeSec = nomeSec;
    }

    public String getTelCelSec() {
        return telCelSec;
    }

    public void setTelCelSec(String telCelSec) {
        this.telCelSec = telCelSec;
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

    public Integer getDiaNascSec() {
        return diaNascSec;
    }

    public void setDiaNascSec(Integer diaNascSec) {
        this.diaNascSec = diaNascSec;
    }

    public String getMesNascSec() {
        return mesNascSec;
    }

    public void setMesNascSec(String mesNascSec) {
        this.mesNascSec = mesNascSec;
    }

    public Especial getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especial especialidade) {
        this.especialidade = especialidade;
    }

    public Radiologia getRadiologia() {
        return radiologia;
    }

    public void setRadiologia(Radiologia radiologia) {
        this.radiologia = radiologia;
    }
}
