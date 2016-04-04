package br.com.nfsconsultoria.dentalcalendar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Visita {

	@Column(nullable = false, length= 300)
	private String acordo;
	
	@ManyToOne
    @JoinColumn(nullable = false)
    private Dentista dentista;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Representante representante;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Agenda agenda;

	public String getAcordo() {
		return acordo;
	}

	public void setAcordo(String acordo) {
		this.acordo = acordo;
	}

	public Dentista getDentista() {
		return dentista;
	}

	public void setDentista(Dentista dentista) {
		this.dentista = dentista;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
}

