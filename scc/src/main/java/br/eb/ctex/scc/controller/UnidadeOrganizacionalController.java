package br.eb.ctex.scc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.eb.ctex.scc.model.UnidadeOrganizacional;
import br.eb.ctex.scc.reporitory.filter.UnidadeOrganizacionalFilter;
import br.eb.ctex.scc.service.CadastroUnidadeService;

@Controller
@RequestMapping("/unidadeOrganizacional")
public class UnidadeOrganizacionalController {

	@Autowired
	CadastroUnidadeService unidadeService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroUnidadeOrganizacional");
		mv.addObject(new UnidadeOrganizacional());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated UnidadeOrganizacional unidadeOrganizacional, Errors erros,
			RedirectAttributes attributes) {
		if (erros.hasErrors())
			return "CadastroUnidadeOrganizacional";
		unidadeService.salvar(unidadeOrganizacional);
		attributes.addFlashAttribute("mensagem", "Unidade Organizacional Salva com Sucesso");
		return "redirect:/unidadeOrganizacional/novo";

	}

	@RequestMapping(value = "{idUnidade}")
	public ModelAndView edicao(@PathVariable("idUnidade") UnidadeOrganizacional unidade) {
		ModelAndView mv = new ModelAndView("CadastroUnidadeOrganizacional");
		mv.addObject(unidade);
		return mv;
	}

	@RequestMapping(value = "{idUnidade}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long idUnidade, RedirectAttributes attributes) {
		unidadeService.excluir(idUnidade);

		attributes.addFlashAttribute("mensagem", "Unidade excluída com sucesso!");
		return "redirect:/unidadeOrganizacional";
	}

	@RequestMapping
	public ModelAndView pesquisarUnidade(@ModelAttribute("filtro") UnidadeOrganizacionalFilter filtro) {
		List<UnidadeOrganizacional> todasUnidades = unidadeService.filtrar(filtro);

		ModelAndView mv = new ModelAndView("PesquisaUnidade");
		mv.addObject("todasUnidades", todasUnidades);
		return mv;
	}

}
