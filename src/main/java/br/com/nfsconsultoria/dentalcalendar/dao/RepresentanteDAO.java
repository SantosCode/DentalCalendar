/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.Representante;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luis
 */
public class RepresentanteDAO extends GenericDAO<Representante>{
    public Representante autenticar(String login, String senha){
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Representante.class);
            
            consulta.add(Restrictions.eq("login", login));
            
            SimpleHash hash = new SimpleHash("md5", senha);
            consulta.add(Restrictions.eq("senha", hash.toHex()));
            
            Representante resultado = (Representante) consulta.uniqueResult();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }
   
}
