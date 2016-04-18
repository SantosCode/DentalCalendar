package br.com.nfsconsultoria.dentalcalendar.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
public class Clinica extends GenericDomain{

	@Column(nullable = false)
	String nome;
	
	@OneToMany(mappedBy = "clinicas")
	@JoinColumn(nullable = true)
	List<Dentista> dentistas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Dentista> getDentsitas() {
		return dentistas;
	}

	public void setDentsitas(List<Dentista> dentistas) {
		this.dentistas = dentistas;
	}
	
	
}
