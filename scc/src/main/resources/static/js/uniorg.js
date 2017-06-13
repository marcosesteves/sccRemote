$('#confirmacaoExclusaoUnidade').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var idUnidade = button.data('id');
	var nomeUnidade = button.data('nomeunidade');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + idUnidade);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir a Unidade <strong>' + nomeUnidade + '</strong>?');
});
$('#confirmacaoExclusaoComputador').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var idComp = button.data('id_comp');
	var nomeCodigo = button.data('nome_codigo');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + idComp);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o computador <strong>' + nomeCodigo + '</strong>?');
});

$(function() {
	$('[rel="tooltip"]').tooltip();
});