package br.eb.ctex.scc.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.eb.ctex.scc.model.Computador;
import br.eb.ctex.scc.model.UnidadeOrganizacional;
import br.eb.ctex.scc.model.Usuario;
import br.eb.ctex.scc.reporitory.filter.ComputadorFilter;
import br.eb.ctex.scc.service.CadastroComputadorService;
import br.eb.ctex.scc.service.CadastroUnidadeService;
import br.eb.ctex.scc.util.LCAuth;

@Controller
@RequestMapping("/cadastroComputador")
public class ComputadorController {
	
	
	@Autowired
	CadastroComputadorService computadorService;
	
	@Autowired
	CadastroUnidadeService unidadeService;

	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("Login");
		mv.addObject(new Usuario());		
		return mv;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
	  session.invalidate();
	  return "redirect:/cadastroComputador/login";
	}
	
	@RequestMapping("/validaLogin")
	public String validaLogin(@Validated Usuario usuario, Errors erros, RedirectAttributes attributes, HttpSession session) {
		if (erros.hasErrors()) 
			return "Login"; 
		boolean achou = LCAuth.autentica(usuario.getLogin(), usuario.getSenha(), session);
		if (achou)
			return "redirect:/cadastroComputador";
		erros.rejectValue("login", null, "O Login não pode ser efetuado com sucesso!");
		erros.rejectValue("senha", null, "");
		return "Login";
	}
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(Model model) {
		ModelAndView mv = new ModelAndView("CadastroComputador");
		List<UnidadeOrganizacional> listaUnidades = unidadeService.buscarTodasUnidades();
		mv.addObject(new Computador());
		mv.addObject("listaDeUnidades", listaUnidades);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Computador computador, Errors erros, RedirectAttributes attributes, Model model) {
		if (erros.hasErrors()) {
			List<UnidadeOrganizacional> listaUnidades = unidadeService.buscarTodasUnidades();
			model.addAttribute("listaDeUnidades", listaUnidades);
			return "CadastroComputador"; }
		
		try {
			computadorService.salvar(computador);			
			attributes.addFlashAttribute("mensagem", "Computador Salvo com Sucesso");
			return "redirect:/cadastroComputador/novo";
		}
		catch (DataIntegrityViolationException e) {
			erros.rejectValue("dataCadastro", null, "Formato de data inválido");
			return "CadastroComputador";
		}
	}
	
/*	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Computador computador, Errors erros, RedirectAttributes attributes) {
		
		if (erros.hasErrors()) {
			return "CadastroComputador"; }
		
		try {
			computadorService.salvar(computador);			
			attributes.addFlashAttribute("mensagem", "Computador Salvo com Sucesso");
			return "redirect:/cadastroComputador/novo";
		}
		catch (DataIntegrityViolationException e) {
			erros.rejectValue("dataCadastro", null, "Formato de data inválido");
			return "CadastroComputador";
		}
	}
*/
	@ModelAttribute("todosTecnicos")
	public List<String> disponibilizaTecnicos() {
		String tecnicos[] = {"Almir","Marcelo","Castro","Jode Carlos"};
		return Arrays.asList(tecnicos);
	}
	
	@RequestMapping(value="{idComp}")
	public ModelAndView edicao(@PathVariable("idComp") Computador computador) {
		ModelAndView mv = new ModelAndView("CadastroComputador");
		List<UnidadeOrganizacional> listaUnidades = unidadeService.buscarTodasUnidades();
		mv.addObject(computador);
		mv.addObject("listaDeUnidades", listaUnidades);
		return mv;
	}
	
	@RequestMapping(value="{idComp}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long idComp, RedirectAttributes attributes) {
		computadorService.excluir(idComp);
		attributes.addFlashAttribute("mensagem", "Computador excluído com sucesso!");
		return "redirect:/cadastroComputador";
}
	
	@RequestMapping
	public ModelAndView pesquisarComputador(@ModelAttribute("filtro") ComputadorFilter filtro) {
		List<Computador> todosComputadores = computadorService.filtrar(filtro);

		ModelAndView mv = new ModelAndView("PesquisaComputador");
		mv.addObject("todosComputadores", todosComputadores);
		return mv;
	}

}