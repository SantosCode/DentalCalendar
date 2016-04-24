package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.Visita;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class VisitaDAO extends GenericDAO<Visita>{

    public List<Visita> listarRep(Long codigo) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Visita.class);
            consulta.add(Restrictions.eq("representante.codigo", codigo));
            List<Visita> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }

}
