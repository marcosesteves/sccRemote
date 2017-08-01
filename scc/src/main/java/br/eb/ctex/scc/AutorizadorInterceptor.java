package br.eb.ctex.scc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AutorizadorInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller)
			throws Exception {
		String uri = request.getRequestURI();
		if (uri.endsWith("login") || uri.endsWith("validaLogin") || uri.contains("resources")) {
			return true;
		}
		if (request.getSession().getAttribute("usuarioLogado") != null) {
			return true;
		}
		response.sendRedirect("/cadastroComputador/login");
		return false;
	}

}
