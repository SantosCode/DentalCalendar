/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.Agenda;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * @author luis
 */
public class AgendaDAO extends GenericDAO<Agenda> {

    public List<Agenda> listar(Long codigo, Date data) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Agenda.class);
            consulta.add(Restrictions.eq("representante.codigo", codigo));
            consulta.add(Restrictions.eq("dia", data));
            List<Agenda> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }

    public List<Agenda> listarRep(Long codigo) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Agenda.class);
            consulta.add(Restrictions.eq("representante.codigo", codigo));
            List<Agenda> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }

}
