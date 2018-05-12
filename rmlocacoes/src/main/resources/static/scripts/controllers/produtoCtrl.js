var app = angular.module("produto", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
app.controller("produtoController",
	function ($scope,
		$filter,
		$http) {

		$scope.app = "Lista de Produtos";
		$scope.produto = {};
		$scope.produtoEstoque = {};
		$scope.produtos = [];
		$scope.classEdit = false;
		$scope.idEditQtdCat = true;
		$scope.refreshProdutos = false;

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
		 * Retorna a quantidade de produtos para poder calcular a paginação.
		 * Referências: https://codepen.io/dustinmyers/pen/QEpLXa
		 * Referências: https://codepen.io/khilnani/pen/qEWojX
		 */
		$scope.getProdutos = function () {
			// needed for the pagination calc
			// https://docs.angularjs.org/api/ng/filter/filter
			return $filter('filter')($scope.produtos, $scope.q)
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
			var total = Math.ceil($scope.getProdutos().length / $scope.pageSize);
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
				$scope.produto.categoria = newValue;
			}
			$scope.selectedItem = "";
		};

		$scope.exibirProdutos = function (value) {
			$scope.isListaProdutos = value;
		};

		var carregarProdutos = function () {
			$http.get("/carregarProdutos/")
				.success(function (ret) {
					$scope.refreshProdutos = true;
					$scope.produtos = [];
					$scope.produtos = ret;
					numberOfPages();
				});
		};

		var editarProduto = function (produto) {

			$scope.produto = produto;
			delete $scope.produto.dataNascimento;
			document.getElementById('dataNascimento').value = produto.dtaNascimento;

			var enderecos = produto.enderecos;
			if (enderecos) {
				angular.forEach(enderecos, function (value, key) {
					if (value.enderecoEntrega) {
						$scope.endereco = value;
					}
					if (!value.enderecoEntrega) {
						$scope.enderecoComercial = value;
					}
				});
			}

			$scope.contatos = produto.contatos;
			$scope.isListaProdutos = false;
			$scope.classEdit = true;

		}

		$scope.carregarProdutoAjustar = function (produto) {
			$scope.classEdit = true;
			$scope.produtoEstoque = produto;
		};

		$scope.atualizarEstoque = function (produtoEstoque) {
			$scope.produto = produtoEstoque;
			$scope.produto.quantidade = produtoEstoque.novaQuantidade;
			delete $scope.produto.novaQuantidade;
			ajustarEstoque();
		};

		var ajustarEstoque = function () {
			var produto = $scope.produto;

			$http.put("/estoque-produto/atualizar/", produto)
				.success(function (produtoCadastrado) {
					delete $scope.produto;
					delete $scope.produtoEstoque;
					carregarProdutos();
					limparForm('formularioProduto');
					Materialize.toast('Estoque atualizado com sucesso', 4000, 'rounded');
				})
				.error(function (data, status) {
					// Handle HTTP error
					console.log(data);
					Materialize.toast(data.message, 4000, 'rounded');
				})
		};

		var cadastrarProduto = function () {

			if (!$scope.formularioProduto.$valid) {
				Materialize.toast('Todos os campos do cadastro de Produtos são Obrigatórios.', 4000, 'rounded');
				return;
			}

			var preco = $scope.produto.preco.replace(",", ".");
			var precoFormatado = parseFloat(preco);

			$scope.produto.preco = precoFormatado;
			var produto = $scope.produto;
			delete produto.$$hashKey;

			if (produto == null) {
				Materialize.toast('Produto nulo.', 4000, 'rounded')
			} else {
				if (produto.codigo) {
					delete produto.dataCadastro;
					$http.put("/editarProduto/", produto)
						.success(function (produtoCadastrado) {
							carregarProdutos();
							limparForm('formularioProduto');
							Materialize.toast('Produto atualizado com sucesso.', 4000, 'rounded');

						})
						.error(function (data, status) {
							// Handle HTTP error
							console.log(data);
						});
					$scope.isEdit = false;
					$scope.idEditQtdCat = true;
					return;
				}
				$http.post("/cadastrarProduto/", produto)
					.success(function (produtoCadastrado) {
						delete $scope.produto;
						$scope.refreshProdutos = false;
						carregarProdutos();
						$scope.formularioProduto.$setPristine();
						limparForm('formularioProduto');
						Materialize.toast('Produto cadastrado com sucesso.', 4000, 'rounded');
					})
					.error(function (data, status) {
						// Handle HTTP error
						console.log(data);
					});

			}
			$scope.classEdit = false;
		};

		var limparForm = function (idForm) {
			$scope.produto = {};
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

		$scope.removerprodutos = function (produtos) {

			angular.forEach(produtos, function (value, key) {
				if (value.selecionado) {

					var id = value.idProduto;
					$http.delete("/removerProduto" + id).success(function (produto) {
						carregarProdutos();
					});

				}

			});
			carregarProdutos();
		};

		$scope.onEdit = function (prod) {
			$scope.produto = {};
			$scope.isAtualiza = true;
			$scope.classEdit = true;
			var preco = formatPreco(prod.preco);
			prod.preco = preco;
			$scope.produto = prod;
			$scope.isEdit = true;
			$scope.idEditQtdCat = false;
		}

		var formatPreco = function (strFormat) {

			var preco = String(strFormat);

			if (preco.length === 1) {
				preco = preco + ".00";
				return preco;
			}

			var precoSplit = preco.split(".");
			if (precoSplit && precoSplit.length === 1) {
				preco = preco + ".00";
				return preco;
			}
			if (precoSplit && precoSplit.length >= 2) {
				var precoFracao = precoSplit[1];

				if (precoFracao && precoFracao.length <= 1) {
					preco = preco + "0";
				}
			}
			return preco;
		};

		$scope.addItem = function () {
			cadastrarProduto();
		};

		$scope.editItem = function () {
			cadastrarProduto();
		};

		$scope.classe1 = "selecionado";
		$scope.classe2 = "negrito";

		carregarProdutos();

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