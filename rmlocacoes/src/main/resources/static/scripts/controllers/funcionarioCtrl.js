var app = angular.module("funcionario", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
app.controller("funcionarioController",
	function ($scope,
		$filter,
		$http) {

		$scope.app = "Lista de Funcionarios";
		$scope.funcionario = {};
		$scope.funcionarioEstoque = {};
		$scope.funcionarios = [];
		$scope.classEdit = false;
		$scope.idEditQtdCat = true;
		$scope.refreshFuncionarios = false;

		$scope.pageNumbers = [];
		$scope.activePage = 1;
		$scope.selectedItem = "";
		$scope.isFormPedido = false;
		$scope.categorias = [{
				"key": "REVENDA",
				"value": "Revenda"
			},
			{
				"key": "COMODATO",
				"value": "Comodato"
			}
		];

		/*******************************************************************
		 * Seta o valor da página ativa e ajusta os itens quando clicado no
		 * numero da página.
		 */
		$scope.setActivePage = function (index) {
			$scope.activePage = index + 1;
			$scope.currentPage = index;
		}

		/*******************************************************************
		 * Carrega lista de pageNumbers para ser gerado a quantidade de
		 * forma dinâmica de acordo com a quantidade de itens.
		 */
		var carregarPageNumbers = function () {

			var totalPaginas = $scope.totalPaginas;
			var total = 0;
			if ($scope.pageNumbers.length) {
				return;
			}
			for (var i = 0; i < totalPaginas; i++) {

				var page = {
					pageNumber: i + 1
				};
				$scope.pageNumbers.push(page);
			}

		};

		$scope.currentPage = 0;
		$scope.pageSize = 5;
		$scope.data = [];
		$scope.q = '';
		$scope.totalPaginas = 0;
		$scope.jumpNextPage = true;
		$scope.jumpPreviewsPage = true;


		/*******************************************************************
		 * Retorna a quantidade de funcionarios para poder calcular a paginação.
		 * Referências: https://codepen.io/dustinmyers/pen/QEpLXa
		 * Referências: https://codepen.io/khilnani/pen/qEWojX
		 */
		$scope.getFuncionarios = function () {
			// needed for the pagination calc
			// https://docs.angularjs.org/api/ng/filter/filter
			return $filter('filter')($scope.funcionarios, $scope.q)
			/*
			 * // manual filter // if u used this, remove the filter from
			 * html, remove above line and replace data with getData()
			 * 
			 * var arr = []; if($scope.q == '') { arr = $scope.data; } else {
			 * for(var ea in $scope.data) {
			 * if($scope.data[ea].indexOf($scope.q) > -1) { arr.push(
			 * $scope.data[ea] ); } } } return arr;
			 */
		}

		/*******************************************************************
		 * Calcula o númmero de páginas que o paginador irá ter.
		 */
		var numberOfPages = function () {
			var total = Math.ceil($scope.getFuncionarios().length / $scope.pageSize);
			$scope.totalPaginas = total;
			if ($scope.totalPaginas) {
				carregarPageNumbers();
			}
			return total;
		}

		/*******************************************************************
		 * Ajusta o grid para a próxima pagina quando clicado em nextPage, e
		 * já garante o bloqueio de ida para próxima pagina, quando este
		 * atingir o fim da lista.
		 */
		$scope.nextPage = function (index) {

			if (($scope.currentPage + 1) >= $scope.totalPaginas) {

				$scope.jumpNextPage = !$scope.jumpNextPage;
				return;
			}
			$scope.currentPage = $scope.currentPage + 1;
			$scope.activePage = $scope.activePage + 1;
			$scope.jumpNextPage = true;

		};

		/*******************************************************************
		 * Ajusta o grid para a pagina anterior quando clicado em
		 * previewPage, e já garante o bloqueio de ida para próxima pagina,
		 * quando este atingir o fim da lista.
		 */
		$scope.previewPage = function (index) {

			if (($scope.totalPaginas - $scope.currentPage) === $scope.totalPaginas) {
				$scope.jumpPreviewsPage = !$scope.jumpPreviewsPage;
				return;
			}

			$scope.currentPage = $scope.currentPage - 1;
			$scope.activePage = $scope.activePage - 1;
			$scope.jumpPreviewsPage = true;

		};

		$scope.showSelectValue = function (newValue) {

			if ("" !== newValue) {
				$scope.funcionario.categoria = newValue;
			}
			$scope.selectedItem = "";
		};


		var carregarFuncionarios = function () {
			$http.get("/carregarFuncionarios/")
				.success(function (ret) {
					$scope.refreshFuncionarios = true;
					$scope.funcionarios = [];
					$scope.funcionarios = ret;
					numberOfPages();
				});
		};

		var editarFuncionario = function (funcionario) {

			$scope.funcionario = funcionario;

			$scope.contatos = funcionario.contatos;
			$scope.isListaFuncionarios = false;
			$scope.classEdit = true;

		}

		var ajustarEstoque = function () {
			var funcionario = $scope.funcionario;

			$http.put("/estoque-funcionario/atualizar/", funcionario)
				.success(function (funcionarioCadastrado) {
					delete $scope.funcionario;
					delete $scope.funcionarioEstoque;
					carregarFuncionarios();
					limparForm('formularioFuncionario');
					Materialize.toast('Estoque atualizado com sucesso', 4000, 'rounded');
				})
				.error(function (data, status) {
					// Handle HTTP error
					console.log(data);
					Materialize.toast(data.message, 4000, 'rounded');
				})
		};

		var cadastrarFuncionario = function () {

			if (!$scope.formularioFuncionario.$valid) {
				Materialize.toast('Todos os campos do cadastro de Funcionarios são Obrigatórios.', 4000, 'rounded');
				return;
			}

			var funcionario = $scope.funcionario;
			delete funcionario.$$hashKey;

			if (funcionario == null) {
				Materialize.toast('Funcionario nulo.', 4000, 'rounded')
			} else {
				if (funcionario.codigo) {
					delete funcionario.dataCadastro;
					$http.put("/editarFuncionario", funcionario)
						.success(function (funcionarioCadastrado) {
							carregarFuncionarios();
							limparForm('formularioFuncionario');
							Materialize.toast('Funcionario atualizado com sucesso.', 4000, 'rounded');

						})
						.error(function (data, status) {
							// Handle HTTP error
							console.log(data);
						});
					$scope.isEdit = false;
					$scope.idEditQtdCat = true;
					return;
				}
				$http.post("/cadastrarFuncionario/", funcionario)
					.success(function (funcionarioCadastrado) {
						delete $scope.funcionario;
						$scope.refreshFuncionarios = false;
						carregarFuncionarios();
						$scope.formularioFuncionario.$setPristine();
						limparForm('formularioFuncionario');
						Materialize.toast('Funcionario cadastrado com sucesso.', 4000, 'rounded');
					})
					.error(function (data, status) {
						// Handle HTTP error
						console.log(data);
					});

			}
			$scope.classEdit = false;
		};

		var limparForm = function (idForm) {
			$scope.funcionario = {};
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

		
		$scope.onEdit = function (prod) {
			$scope.funcionario = {};
			$scope.isAtualiza = true;
			$scope.classEdit = true;
			$scope.funcionario = prod;
			$scope.isEdit = true;
			$scope.idEditQtdCat = false;
		}

		$scope.addItem = function () {
			cadastrarFuncionario();
		};

		$scope.editItem = function () {
			cadastrarFuncionario();
		};

		carregarFuncionarios();

		$('input.autocomplete').autocomplete({
			data: {},
			limit: 20, // The max amount of results that can be shown
			// at once. Default: Infinity.
			onAutocomplete: function (val) {
				// Callback function when value is autcompleted.
			},
			minLength: 1, // The minimum length of the input for the
			// autocomplete to start. Default: 1.
		});

	}).filter('startFrom', function () {
	return function (input, start) {
		if (!input) {
			return;
		}
		start = +start; // parse to int
		return input.slice(start);
	}
});