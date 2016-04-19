/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.util;

import br.com.nfsconsultoria.dentalcalendar.bean.AutenticaBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author luissantos
 */
public class LoginFilter implements Filter{
    
    
    @Override
    public void init(FilterConfig args0) throws ServletException {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        //Captura o ManagedBean chamado autenticaBean
        AutenticaBean autentica = (AutenticaBean)((HttpServletRequest) 
                request).getSession().getAttribute("autenticaBean");
        
        //Verifica se o Managed ainda não foi instanciado
        if (autentica == null || !autentica.getIsLogado()) {
            String contexto = ((HttpServletRequest) request).getContextPath();
            //Redireciona o usuario imediatamente para a pagina de login
            ((HttpServletResponse) response).sendRedirect(contexto + "/login.xhtml");
        } else  {
                // Caso esteja logado segue o fluxo
            chain.doFilter(request, response);
            }
            
        }
        

    @Override
    public void destroy() {
        //To change body of generated methods, choose Tools | Templates.
    }
    
}
