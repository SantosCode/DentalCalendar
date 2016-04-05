/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author luis
 */
public class DentistaDAO extends GenericDAO<Dentista>{
    
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
}