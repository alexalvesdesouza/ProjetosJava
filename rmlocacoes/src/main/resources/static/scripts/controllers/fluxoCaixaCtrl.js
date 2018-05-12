var fluxoCaixa = angular.module("fluxoCaixa", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
fluxoCaixa.controller("fluxoCaixaController",
	function ($scope,
		$filter,
		$http) {

		$scope.app = "Lista de FluxoCaixas";
		$scope.classEdit = false;
		$scope.fluxoCaixaReceitas = {
			"width": "85%"
		};

		$scope.fluxoCaixaDespesas = {
			"width": "10%"
		};
		$scope.contasReceberPagos = 0;
		$scope.contasReceberTotal = 0;
		$scope.contasPagarPagos = 0;
		$scope.contasPagarTotal = 0;
		$scope.contasPagarList = [];
		$scope.folhaPagamentoList = [];
		$scope.contasSelecionadas = [];
		$scope.funconariosList = [];
		$scope.contasReceberSelecionadas = [];
		$scope.contasVinculadasList = [];
		$scope.contasReceberList = [];
		$scope.contaPagar = {};
		$scope.funcionarioSelecionado = '';
		$scope.isEdit = false;
		$scope.classEditConta = false;
		$scope.hideListFuncionario = true;
		$scope.abaSelecionada = '';
		$scope.folhaPagamento = {};

		$scope.hideListConta = true;
		$scope.contaSelecionada = "";

		$scope.selecionarFuncionario = function(item) {
			$scope.filterFuncionario = [];
			$scope.hideListFuncionario = true;
			$scope.funcionario = item;
			$scope.funcionarioSelecionado = item.nome;
		};

		$scope.completeFuncionario = function(param) {
			$scope.hideListFuncionario = false;
			var output = [];
			angular.forEach($scope.funconariosList, function(funcionarioMap){
				if (funcionarioMap.nome === null) {
					return;
				}
				if(funcionarioMap.nome.toLowerCase().indexOf(param.toLowerCase()) >= 0) {
					output.push(funcionarioMap);
				}
			});
			$scope.filterFuncionario = output;
		};
		
		var carregarFolhaPagamento = function () {
			$http.get("/folha-pagamento/list")
				.success(function (ret) {
					$scope.folhaPagamentoList = [];
					$scope.folhaPagamentoList = ret;
				});
		};
		
		var carregarFunconarios = function () {
			$http.get("/carregarFuncionarios")
				.success(function (ret) {
					$scope.funconariosList = [];
					$scope.funconariosList = ret;
				});
		};

		var carregarContasAPagar = function () {
			$http.get("/contas-pagar/list")
				.success(function (ret) {
					$scope.contasPagarList = [];
					$scope.contasPagarList = ret;
				});
		};
		
		var cadastrarContasAPagar = function () {

			if ($scope.contaPagar.codigo) {
				updateContasPagar($scope.contaPagar);
				return;
			}
			
			$http.post("/contas-pagar/cadastrar", $scope.contaPagar)
				.success(function (contaPagar) {
					loadCargaDados();
					limparForm('contasPagarForm');
					Materialize.toast('Conta cadastrada com sucesso.', 4000, 'rounded');
				})
				.error(function (data, status) {
					// Handle HTTP error
					console.log(data);
				});

		};

		var updateContasPagar = function (conta) {

			delete conta.dataCadastro;
			$http.put("/contas-pagar/atualizar", conta)
				.success(function (conta) {
					loadCargaDados();
					limparForm('contasPagarForm');
					Materialize.toast('Conta atualizada com sucesso.', 4000, 'rounded');
				})
				.error(function (data, status) {
					// Handle HTTP error
					console.log(data);
				});
			$scope.isEdit = false;
		};

		var marcarComoPago = function (conta) {

			delete conta.dataCadastro;
			$http.put("/contas-pagar/"+ conta.codigo + "/pagar")
				.success(function (conta) {
					loadCargaDados();
					limparForm('contasPagarForm');
					Materialize.toast('Conta '+ conta.codigo + ' paga com sucesso.', 4000, 'rounded');
				})
				.error(function (data, status) {
					Materialize.toast(data.message, 4000, 'rounded');
					console.log(data);
				});
			$scope.isEdit = false;
		};
		
		var efetivarRecComoPago = function (receber) {
			
			delete receber.dataCadastro;
			$http.put("/contas-receber/"+ receber.codigo + "/receber")
			.success(function (receber) {
				loadCargaDados();			
				Materialize.toast('Recebimento '+ receber.codigo + ' efetivado com sucesso.', 4000, 'rounded');
			})
			.error(function (data, status) {
				Materialize.toast(data.message, 4000, 'rounded');
				console.log(data);
			});
			$scope.isEdit = false;
		};

		var deletarContaPagar = function (conta) {

			delete conta.dataCadastro;
			$http.delete("/contas-pagar/"+ conta.codigo + "/deletar")
				.success(function (conta) {
					loadCargaDados();
					limparForm('contasPagarForm');
					Materialize.toast('Conta '+ conta.codigo + ' deletada com sucesso.', 4000, 'rounded');
				})
				.error(function (data, status) {
					Materialize.toast(data.message, 4000, 'rounded');
					console.log(data);
				});
			$scope.isEdit = false;
		};

		$scope.addFolhaPagamento = function() {
			$scope.folhaPagamento.funcionario = $scope.funcionario;
			$http.post('folha-pagamento/cadastrar', $scope.folhaPagamento)
				.success(function(retorno) {
					carregarFolhaPagamento();
					limparForm('folhaPagamentoForm');
					Materialize.toast('Folha pagamento efetivada com sucesso.', 4000, 'rounded');
				})
				.error(function (data, status) {
					Materialize.toast(data.message, 4000, 'rounded');
					console.log(data);
				});
		};

		$scope.editFolhaPagamento = function() {
			var folha = $scope.folhaPagamento;
			delete folha.dataCadastro;
			delete folha.dataPagamento;
			delete folha.dataVencimento;
			$http.put('folha-pagamento/atualizar', folha)
				.success(function(retorno) {
					carregarFolhaPagamento();
					limparForm('folhaPagamentoForm');
					Materialize.toast('Folha pagamento atualizada com sucesso.', 4000, 'rounded');
				})
				.error(function (data, status) {
					Materialize.toast(data.message, 4000, 'rounded');
					console.log(data);
				});
		};

		$scope.deletaFolhaPagamento = function(folhaPagamento) {
			var codigo = folhaPagamento.codigo;
			$http.delete('/folha-pagamento/' + codigo + '/deletar')
				.success(function(retorno) {
					carregarFolhaPagamento();
					limparForm('folhaPagamentoForm');
					Materialize.toast('Folha pagamento deletada com sucesso.', 4000, 'rounded');
				})
				.error(function (data, status) {
					Materialize.toast(data.message, 4000, 'rounded');
					console.log(data);
				});
		};

		$scope.editarFolhaPagamento = function(folha) {
			$scope.funcionarioSelecionado = folha.funcionario.nome;
			$scope.folhaPagamento = folha;
			$scope.isEdit = true;
			$scope.classEdit = true;
		};

		$scope.deletarContaPagar = function (conta) {
			deletarContaPagar(conta);
		};
		
		$scope.deletarContaEmMassa = function() {
			angular.forEach($scope.contasSelecionadas, function(conta) {
				$scope.deletarContaPagar(conta);
			});
		};

		$scope.pagarContaEmMassa = function() {
			angular.forEach($scope.contasSelecionadas, function(conta) {
				$scope.marcarContaPagarComoPago(conta);
			});
		};
		
		$scope.efetivarRecebimentoEmMassa = function() {
			angular.forEach($scope.contasReceberSelecionadas, function(receber) {
				$scope.efetivarRecebimentoComoPago(receber);
			});
		};

		$scope.marcarContaPagarComoPago = function (conta) {
			marcarComoPago(conta);
		};
		
		$scope.efetivarRecebimentoComoPago = function (receber) {
			efetivarRecComoPago(receber);
		};

		$scope.addItem = function () {
			cadastrarContasAPagar();
		};

		$scope.editarConta = function (conta) {
			$scope.isEdit = true;
			$scope.classEdit = true;
			var valor = formatValor(conta.valor);
			conta.valor = valor;
			$scope.contaPagar = conta;
		};

		$scope.abaSelectecd = function (abaSelecionada) {
			if ('contas-a-receber' === abaSelecionada) {
				$scope.exibeGridClientes = true;
				$scope.abaSelecionada = 'contas-a-receber';
				return;
			}
			if ('contas-a-pagar' === abaSelecionada) {
				carregarContasAPagar();
				$scope.abaSelecionada = 'contas-a-pagar';
				return;
			}
			if ('folha-pagamento' === abaSelecionada) {
				carregarFolhaPagamento();
				carregarFunconarios();
				$scope.abaSelecionada = 'folha-pagamento';
				return;
			}
		};

		$scope.completeConta = function(param) {
			$scope.hideListConta = false;
			var output = [];
			angular.forEach($scope.contasVinculadasList, function(contaMap){
				if (contaMap.banco === null) {
					return;
				}
				if(contaMap.banco.toLowerCase().indexOf(param.toLowerCase()) >= 0) {
					output.push(contaMap);
				}
			});
			$scope.filterConta = output;
		};

		$scope.selecionarConta = function(item) {
			$scope.filterConta = [];
			$scope.hideListConta = true;
			$scope.contaVinculada = item;
			$scope.contaSelecionada = item.banco;
			$scope.classEditConta = true;
		};

		var contasConsolidadas = function() {
			$http.get("/fluxo-caixa/contas-consolidadas")
				.success(function (ret) {
	
					$scope.fluxoCaixaReceitas.width = ret.receber.diferenca + "%";
					$scope.contasReceberPagos = ret.receber.pagos;
					$scope.contasReceberTotal = ret.receber.total;

					$scope.fluxoCaixaDespesas.width = ret.pagar.diferenca + "%";
					$scope.contasPagarPagos = ret.pagar.pagos;
					$scope.contasPagarTotal = ret.pagar.total;
									
			});
		};

		var contasVinculadas = function() {
			$http.get("/fluxo-caixa/contas-vinculadas/load")
				.success(function (ret) {
					$scope.contasVinculadasList = [];
					$scope.contasVinculadasList = ret;
			});
		};

		var carregarContasReceber = function() {
			$http.get("/contas-receber/list")
				.success(function (ret) {
					$scope.contasReceberList = ret;
			});
		};

		$scope.selecionaItem = function(contaPagar) {
			
			var index = $scope.contasSelecionadas.indexOf(contaPagar);
			if (index === -1) {
				$scope.contasSelecionadas.push(contaPagar);
				return;
			}
			$scope.contasSelecionadas.splice(index, 1);
			
		};
		
		$scope.selecionaContaReceber = function(receber) {
			
			var index = $scope.contasReceberSelecionadas.indexOf(receber);
			if (index === -1) {
				$scope.contasReceberSelecionadas.push(receber);
				return;
			}
			$scope.contasReceberSelecionadas.splice(index, 1);
		};

		var formatValor = function (strFormat) {

			var valor = String(strFormat);

			if (valor.length === 1) {
				valor = valor + ".00";
				return valor;
			}

			var valorSplit = valor.split(".");
			if (valorSplit && valorSplit.length === 1) {
				valor = valor + ".00";
				return valor;
			}
			if (valorSplit && valorSplit.length >= 2) {
				var valorFracao = valorSplit[1];

				if (valorFracao && valorFracao.length <= 1) {
					valor = valor + "0";
				}
			}
			return valor;
		};

		var limparForm = function (idForm) {
			$scope.contaPagar = {};
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

		var loadCargaDados = function() {
			carregarContasAPagar();
			contasVinculadas();
			contasConsolidadas();
			carregarContasReceber();
		};

		loadCargaDados();

	}).filter('startFrom', function () {
	return function (input, start) {
		if (!input) {
			return;
		}
		start = +start; // parse to int
		return input.slice(start);
	}
});