package br.com.nfsconsultoria.dentalcalendar.domain;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class Clinica extends GenericDomain {

    @Column(nullable = false, unique = true)
    private String nome;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "clinica")
//    @JoinColumn(nullable = true)
    private List<Dentista> dentista;

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Dentista> getDentista() {
        return dentista;
    }

    public void setDentista(List<Dentista> dentista) {
        this.dentista = dentista;
    }
}
