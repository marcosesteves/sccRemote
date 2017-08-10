package br.eb.ctex.scc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	AutorizadorInterceptor autorizadorInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(autorizadorInterceptor);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("Login");
		System.out.println("Passei pelo addRedirectViewController!!!!!!!!!!!!!!!!!!!!!!");
		registry.addRedirectViewController("/", "/cadastroComputador/login");
	}  	
}