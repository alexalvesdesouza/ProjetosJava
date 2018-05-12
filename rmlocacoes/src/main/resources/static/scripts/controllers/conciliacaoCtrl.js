var conciliacao = angular.module("conciliacao", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
conciliacao.controller("conciliacaoController",
	function ($scope,
		$filter,
		$http) {

		$scope.app = "Conciliação App";
		$scope.classEdit = false;
		$scope.isEdit = false;
		$scope.contaVinculada = {};
		$scope.contasVinculadasList = [];
		$scope.path = '/conciliacao-bancaria';



		var carregarContasVinculadas = function() {
			$http.get($scope.path + '/contas-vinculadas/list')
			.success(function(ret) {
				$scope.contasVinculadasList = ret;
			})
			.error(function(data, status) {
				console.log(data);
				Materialize.toast(data.message, 4000, 'rounded');
			});
		};

		var cadastrarContaVinculada = function() {
			var conta = $scope.contaVinculada;
			if (conta.codigo) {
				atualizarContaVinculada(conta);
				return;
			}
			$http.post($scope.path + '/contas-vinculadas/cadastrar', conta)
				.success(function(ret) {
					carregarContasVinculadas();
					limparForm('formularioContas');
					$scope.isEdit = false;
					Materialize.toast('Conta cadastrada com sucesso!', 4000, 'rounded');
				})
				.error(function(data, status) {
					console.log(data);
					Materialize.toast(data.message, 4000, 'rounded');
				});

		};

		var atualizarContaVinculada = function(conta) {
			$http.put($scope.path + '/contas-vinculadas/atualizar', conta)
				.success(function(ret) {
					carregarContasVinculadas();
					limparForm('formularioContas');
					$scope.isEdit = false;
					Materialize.toast('Conta atualizada com sucesso!', 4000, 'rounded');
				})
				.error(function(data, status) {
					console.log(data);
					Materialize.toast(data.message, 4000, 'rounded');
				});

		};

		$scope.addItem = function() {
			cadastrarContaVinculada();
		};

		$scope.onEdit = function(item) {
			$scope.isEdit = true;
			$scope.classEdit = true;
			$scope.contaVinculada = item;
		};

		var limparForm = function (idForm) {
			$scope.itens = [];
			$scope.item = {};
			$scope.pedido = {};
		   
		   var form = document.getElementById(idForm);
		   if (form === null) {
			   return;
		   }

		   // limpa todos os inputs do tipo text, password, etc...
		   var inputs = form.querySelectorAll('input');
		   for (var i = 0; i < inputs.length; i++) {
			   if (inputs[i].type != 'checkbox' && inputs[i].type != 'radio') {
				   inputs[i].value = '';
			   }
		   }
	   
		   // limpa todas as textareas
		   var textarea = form.querySelectorAll('textarea');
		   for (var i = 0; i < textarea.length; i++) {
			   textarea[i].value = '';
		   }
	   
		   // desmarca todos os checkboxes e radios
		   inputs = form.querySelectorAll('input[type=checkbox], input[type=radio]');
		   for (i = 0; i < inputs.length; i++) {
			   inputs[i].checked = false;
		   }
	   
		   // seleciona a primeira opcao de todos os selects
		   var selects = form.querySelectorAll('select');
		   for (i = 0; i < selects.length; i++) {
			   var options = selects[i].querySelectorAll('option');
			   if (options.length > 0) {
				   selects[i].value = options[0].value;
			   }
		   }
	   };

		carregarContasVinculadas();

	}).filter('startFrom', function () {
	return function (input, start) {
		if (!input) {
			return;
		}
		start = +start; // parse to int
		return input.slice(start);
	}
});