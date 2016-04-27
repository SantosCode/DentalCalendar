/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.domain;


import org.webx.easyreport.annotations.ReportColumn;
import org.webx.easyreport.classTypes.Classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@Entity
public class Dentista extends GenericDomain{
    
    @Column(nullable = false, length = 45, unique = true)
    @ReportColumn(property = "nomme", title = "Dentista", colClass = Classes.STRING)
    private String nome;
    
    @Column(nullable = true, length = 6, unique = true)
    @ReportColumn(property = "cro", title = "CRO-SP", colClass = Classes.STRING)
    private String cro;
    
    @Column(nullable = false, length = 15)
    @ReportColumn(property = "telfixo", title = "Tel Fixo", colClass = Classes.STRING)
    private String telfixo;
    
    @Column(nullable = true, length = 15)
    @ReportColumn(property = "telcel", title = "Tel Celular", colClass = Classes.STRING)
    private String telcel;
    
    @Column(nullable = true, length = 45)
    @ReportColumn(property = "email", title = "E-Mail", colClass = Classes.STRING)
    private String email;
    
    @Column(nullable = false, length = 80)
    @ReportColumn(property = "rua", title = "Rua", colClass = Classes.STRING)
    private String rua;
    
    @Column(nullable = true, length = 45)
    @ReportColumn(property = "complemento", title = "Complemento", colClass = Classes.STRING)
    private String complemento;
    
    @Column(nullable = false, length = 45)
    @ReportColumn(property = "bairro", title = "Bairro", colClass = Classes.STRING)
    private String bairro;
    
    @Column(nullable = true, length = 10)
    @ReportColumn(property = "cep", title = "CEP", colClass = Classes.STRING)
    private String cep;
    
    @Column(nullable = false, length = 45)
    @ReportColumn(property = "cidade", title = "Cidade", colClass = Classes.STRING)
    private String cidade;
    
    @Column(length = 2)
    @ReportColumn(property = "diaNasc", title = "Dia Nasc", colClass = Classes.INTEGER)
    private Integer diaNasc;
    
    @Column(length = 15)
    @ReportColumn(property = "mesNasc", title = "Mês Nasc", colClass = Classes.STRING)
    private String mesNasc;
    
    @Column
    @ReportColumn(property = "cortesia", title = "Cortesia", colClass = Classes.STRING)
    private Boolean cortesia;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    @ReportColumn(property = "especialidade", title = "Especialidade", colClass = Classes.STRING)
    private Especial especialidade;
    
    @ManyToOne
    @JoinColumn(nullable = true)
    private Radiologia radiologia;
    
    @ManyToOne
    @ReportColumn(property = "clinica", title = "Clinica", colClass = Classes.STRING)
    private Clinica clinica;
    
  @ManyToOne
  @JoinColumn(nullable = true)
  @ReportColumn(property = "secretaria1", title = "Secretária", colClass = Classes.STRING)
  private Secretaria secretaria1;
  
  @ManyToOne
  @JoinColumn(nullable = true)
  @ReportColumn(property = "secretaria2", title = "Secretária", colClass = Classes.STRING)
  private Secretaria secretaria2;
  
  @ManyToOne
  @JoinColumn(nullable = true)
  @ReportColumn(property = "secretaria3", title = "Secretária", colClass = Classes.STRING)
  private Secretaria secretaria3;
  
  @ManyToOne
  @JoinColumn(nullable = true)
  @ReportColumn(property = "secretaria4", title = "Secretária", colClass = Classes.STRING)
  private Secretaria secretaria4;
 
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

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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

    public Especial getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especial especialidade) {
        this.especialidade = especialidade;
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

    public Boolean getCortesia() {
        return cortesia;
    }

    public void setCortesia(Boolean cortesia) {
        this.cortesia = cortesia;
    }

    public Radiologia getRadiologia() {
        return radiologia;
    }

    public void setRadiologia(Radiologia radiologia) {
        this.radiologia = radiologia;
    }

    public Secretaria getSecretaria1() {
        return secretaria1;
    }

    public void setSecretaria1(Secretaria secretaria1) {
        this.secretaria1 = secretaria1;
    }

    public Secretaria getSecretaria2() {
        return secretaria2;
    }

    public void setSecretaria2(Secretaria secretaria2) {
        this.secretaria2 = secretaria2;
    }

    public Secretaria getSecretaria3() {
        return secretaria3;
    }

    public void setSecretaria3(Secretaria secretaria3) {
        this.secretaria3 = secretaria3;
    }

    public Secretaria getSecretaria4() {
        return secretaria4;
    }

    public void setSecretaria4(Secretaria secretaria4) {
        this.secretaria4 = secretaria4;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }
}
