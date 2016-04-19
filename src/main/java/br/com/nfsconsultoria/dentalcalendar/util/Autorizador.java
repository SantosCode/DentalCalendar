/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.util;

import br.com.nfsconsultoria.dentalcalendar.bean.AutenticaBean;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;
import org.omnifaces.util.Messages;
import sun.awt.AppContext;

/**
 *
 * @author luis
 */
public class Autorizador implements PhaseListener{

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext contexto = event.getFacesContext();
        String currentPage = contexto.getViewRoot().getViewId();
        boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1);
        HttpSession sessao = (HttpSession) contexto.getExternalContext().getSession(true);
        AutenticaBean representanteLogado = (AutenticaBean) sessao.getAttribute("representanteLogado");
        AutenticaBean login = contexto.getApplication().evaluateExpressionGet(contexto, "#{autenticaBean.representanteLogado.nome}", AutenticaBean.class);
        
        if (!isLoginPage && representanteLogado != null && !representanteLogado.isLogado() ) {
            Messages.addGlobalError("Acesso Negado");
            NavigationHandler nh = contexto.getApplication().getNavigationHandler();
            nh.handleNavigation(contexto, null, "/pages/login.xhtml");
        }
    }

    @Override
    public void beforePhase(PhaseEvent arg0) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
}
