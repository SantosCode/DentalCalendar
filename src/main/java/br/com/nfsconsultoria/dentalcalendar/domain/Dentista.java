/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.domain;

import java.util.List;

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
    
    @Column(length = 2)
    private Integer diaNasc;
    
    @Column(length = 8)
    private String mesNasc;
    
    @Column
    private Boolean cortesia;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Especial especialidade;
    
    @ManyToOne
    @JoinColumn(nullable = true)
    private Radiologia radiologia;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    List<Clinica> clinicas;
    
  @ManyToOne
  @JoinColumn(nullable = true)
  private Secretaria secretaria1;
  
  @ManyToOne
  @JoinColumn(nullable = true)
  private Secretaria secretaria2;
  
  @ManyToOne
  @JoinColumn(nullable = true)
  private Secretaria secretaria3;
  
  @ManyToOne
  @JoinColumn(nullable = true)
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

	public List<Clinica> getClinicas() {
		return clinicas;
	}

	public void setClinicas(List<Clinica> clinicas) {
		this.clinicas = clinicas;
	}

}
