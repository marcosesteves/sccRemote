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

$('#exibicaoComputador').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var idComp = button.data('id_comp');
	var nomeCodigo = button.data('nome_codigo');
	var siglaUnidade = button.data('sigla_unidade');
	var usuario = button.data('nome_usuario');
	var numIp = button.data('num_ip');
	var placaMae = button.data('placa_mae');
	var processador = button.data('processador');
	var memoriaRam = button.data('memoria_ram');
	var hd = button.data('hd');
	var tipoComp = button.data('tipo_comp');
	var sistOper = button.data('sist_oper');
	var tecnico = button.data('tecnico');
	var dataCadastro = button.data('data_cadastro');
	
	var modal = $(this);
	
//	modal.find('.modal-body #idComputador').html('Id do Computador: ' + idComp);
	modal.find('.modal-body #idComputador').html(""+idComp);
	modal.find('.modal-body #nomeCodigo').html("" + nomeCodigo);
	modal.find('.modal-body #siglaUnidade').html("" + siglaUnidade);
	modal.find('.modal-body #usuario').html("" + usuario);
	modal.find('.modal-body #numIp').html("" + numIp);
	modal.find('.modal-body #placaMae').html("" + placaMae);
	modal.find('.modal-body #processador').html("" + processador);
	modal.find('.modal-body #memoriaRam').html("" + memoriaRam);
	modal.find('.modal-body #hd').html("" + hd);
	modal.find('.modal-body #tipoComp').html("" + tipoComp);
	modal.find('.modal-body #sistOper').html("" + sistOper);
	modal.find('.modal-body #tecnico').html("" + tecnico);
	modal.find('.modal-body #dataCadastro').html("" + dataCadastro);
	
});

$(function() {
	$('[rel="tooltip"]').tooltip();
});