package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.Visita;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class VisitaDAO extends GenericDAO<Visita>{
    
    public List<Visita> listarAgen(Long codigo) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Visita.class);
            consulta.add(Restrictions.eq("agenda.codigo", codigo));
            List<Visita> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }

}
