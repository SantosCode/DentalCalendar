/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 *
 * @author luis
 */
public class DentistaDAO extends GenericDAO<Dentista> {

    @SuppressWarnings("unchecked")
    public List<Dentista> listarOrdenado() {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            String hql = "select codigo from Dentista codigo join fetch codigo.dias";
            Query query = sessao.createQuery(hql);
            return query.list();
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }

    public List<Dentista> listarLazy() {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            @SuppressWarnings("deprecation")
			Criteria consulta = sessao.createCriteria(Dentista.class)
                    .setFetchMode("dentista", FetchMode.EAGER);
            @SuppressWarnings("unchecked")
			List<Dentista> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }

}
