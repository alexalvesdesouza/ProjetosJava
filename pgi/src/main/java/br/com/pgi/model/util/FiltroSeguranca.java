package br.com.pgi.model.util;


import java.io.IOException;



import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebFilter;
import br.com.pgi.model.controller.LoginController;


@WebFilter(urlPatterns = "/privado/*")
public class FiltroSeguranca implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		HttpSession sessao = httpReq.getSession();
		String contextPath = httpReq.getContextPath();
		LoginController loginController = (LoginController) sessao
				.getAttribute("loginController");
		if (loginController == null
				|| loginController.getUsuarioLogado() == null) {
			httpRes.sendRedirect(contextPath + "/login.xhtml");
			return;
		} else {
			String pagina = httpReq.getRequestURL().toString();
			//System.out.println("Pagina acessada: " + pagina);
			if (pagina.contains("/privado/promocao/cadastrarPromocao")
					|| pagina
							.contains("/privado/promocao/cadastrarPromocaoParaCliente")
					|| pagina.contains("/privado/promocao/gerenciarPromocao")
					|| pagina.contains("/privado/usuario/bloquearUsuario")
					|| pagina.contains("/privado/usuario/cadastroUsuario")) {
				if (!loginController.getUsuarioLogado().getPerfilOperador()
						.equals("Administrador")) {
					httpRes.sendRedirect(contextPath + "/naoAutorizado.xhtml");
					return;
				}
			}
		}
		
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
