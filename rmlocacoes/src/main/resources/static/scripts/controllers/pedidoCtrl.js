var pedido = angular.module("pedido", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
pedido.controller("pedidoController",
		function ($scope,
			$filter,
			$http) {
	
			$scope.meses = ["janeiro", "fevereiro", "março", "abril", "Maio", "junho", "agosto", "outubro", "novembro", "dezembro"];
			
			$scope.app = "Lista de Pedidos";
			$scope.pedido = {};
			$scope.pagamento = {};
			$scope.pedidos = [];
			$scope.produtos = [];
			$scope.clientes - [];
			$scope.itens = [];
			$scope.classEdit = false;
			$scope.classControll = true;
			$scope.hideList = true;
			$scope.hideListCliente = true;
			$scope.produtoSelecionado = "";
			$scope.clienteSelecionado = "";
			$scope.produto = {};
			$scope.item = {};
			$scope.produtoPedido = {};
			$scope.mostrarItensAbaPrincipal = true;
			$scope.formMod = false;
			$scope.modoFormulario = false;
			$scope.classContract = true;
			$scope.contrato = {
				title: 'Contrato de Locação',
				pedidoSelecionado: {
					cliente: {}
				},
				locador: {
					nome: 'RM Locações',
					endereco: 'AV. Judéia n° 567',
					bairro: 'Canaã',
					fone: '(034) 3237-5780'
				},
				locatario: {
					enderecoContrato: {}
				},
				pagamento: {},
				itens: [],
				totalProdutos: 0,
				totalComodato: 0,
				clausula1: '',
				clausula2: '',
				clausula3: '',
				clausula4: ''
			};

			$scope.pageNumbers = [];
			$scope.activePage = 1;
			$scope.detalhePedido = {};
			$scope.numeroPedido = 0;
			$scope.pedidoSelecionado = {};
			$scope.efetuouPagamento = false;
			$scope.isFormPedido = true;
			$scope.contratoImpressao = "";
			$scope.dataAtualFormatada = "";
			$scope.vlrTotalFinalPedido = 0;
			$scope.realizandoPedido = false;
			$scope.itemPedidoSelecionado = {};
			$scope.qtdIndisponivel = false;
			$scope.pedidoMaiorQueEstoque = false;
			$scope.qtdItemPermitida = true;
			$scope.modoReimpressaoContrato = false;
			
			$scope.$watch('item.quantidade', function (newValue, oldValue, scope) {
				
				if (newValue === null) {
					$scope.qtdIndisponivel = false;
					$scope.pedidoMaiorQueEstoque = false;
					$scope.vlrQuantidadeIncorreta = false;
					$scope.qtdItemPermitida = true;
					return;
				}				
				
				if (newValue <= 0) {
					$scope.vlrQuantidadeIncorreta = true;	
					$scope.qtdItemPermitida = false;
					return;
				}				
				var quantidadeEstoque = scope.produtoPedido.quantidade;
				if (quantidadeEstoque <= 0) {
					$scope.qtdIndisponivel = true;		
					$scope.qtdItemPermitida = false;
					return;
				}
			    if ($scope.item.quantidade > quantidadeEstoque) {
			    	$scope.pedidoMaiorQueEstoque = true;
			    	$scope.qtdItemPermitida = false;
			    	return;
			    }
			    $scope.qtdIndisponivel = false;
				$scope.pedidoMaiorQueEstoque = false;
				$scope.vlrQuantidadeIncorreta = false;
				$scope.qtdItemPermitida = true;
			  });  
			
			var carregaDataAtual = function() {
				
				var local = new Date();
				var dia = local.getDate();
				var mes = local.getMonth();
				var ano = local.getFullYear();
				
				var mesFormatado = $scope.meses[mes];
				
				$scope.dataAtualFormatada = dia + ' de ' + mesFormatado + ' ' + ano;
			};

			$scope.printToCart = function(printSectionId) {
				var innerContents = document.getElementById(printSectionId).innerHTML;
				$scope.contratoImpressao = '<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">' + innerContents + '</html>';
				var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
				popupWinindow.document.open();
				popupWinindow.document.write($scope.contratoImpressao);
				popupWinindow.document.close();
				if ($scope.modoReimpressaoContrato) {
					$scope.modoReimpressaoContrato = false;
					return;
				}
				cadastrarPedido();
			};

			$scope.efetuarDevolucao = function(pedido) {
				$scope.pedidoSelecionado.devolucaoAvariada = pedido.devolucaoAvariada;
				$scope.pedidoSelecionado.devolucaoParcial = pedido.devolucaoParcial;

				if (pedido.devolucaoParcial) {
					$scope.pedidoSelecionado.pagamento.vlrMultaExtravio = pedido.pagamento.vlrMultaExtravio;
				}
				if (pedido.devolucaoAvariada) {
					$scope.pedidoSelecionado.pagamento.vlrMultaAvaria = pedido.pagamento.vlrMultaAvaria;
				}
								
				$scope.pedido = {};
				$scope.pedido = $scope.pedidoSelecionado;
				updatePedido();
			}

			$scope.lerContrato = function(pedido) {
				$scope.contrato.pedidoSelecionado = pedido;
				var itensPedidoContrato = $scope.itens;
				$scope.contrato.itens = itensPedidoContrato;
				$scope.contrato.pedidoSelecionado.cliente = $scope.cliente;
				$scope.contrato.pagamento = $scope.pagamento;
				var enderecos = $scope.contrato.pedidoSelecionado.cliente.enderecos;

				angular.forEach(enderecos, function(enderecoMap){
					if(enderecoMap.enderecoEntrega) {
						$scope.contrato.locatario.enderecoContrato = enderecoMap;
						return;
					}
				});
				$scope.contrato.totalProdutos = 0;
				$scope.contrato.totalProdutos = 0;
				angular.forEach(itensPedidoContrato, function(item){
					if('COMODATO' === item.produto.categoria) {
						var quantidade = item.quantidade;
						var preco = item.produto.preco;
						var total = quantidade * preco;
						$scope.contrato.totalComodato = $scope.contrato.totalComodato + total;
					}
					if('REVENDA' === item.produto.categoria) {
						var quantidade = item.quantidade;
						var preco = item.produto.preco;
						var total = quantidade * preco;
						$scope.contrato.totalProdutos = $scope.contrato.totalProdutos + total;
					}
				});
				
				$scope.vlrTotalFinalPedido = calculaVlrTotalFinalPedido();
			};

			$scope.reimpressaoContrato = function(pedido) {

				$scope.contrato.pedidoSelecionado = pedido;
				var itensPedidoContrato = pedido.itens;
				$scope.itens = itensPedidoContrato;
				$scope.contrato.itens = itensPedidoContrato;
				$scope.contrato.pedidoSelecionado.cliente = pedido.cliente;
				$scope.contrato.pagamento = pedido.pagamento;
				var enderecos = pedido.cliente.enderecos;

				angular.forEach(enderecos, function(enderecoMap){
					if(enderecoMap.enderecoEntrega) {
						$scope.contrato.locatario.enderecoContrato = enderecoMap;
						return;
					}
				});
				$scope.contrato.totalProdutos = 0;
				$scope.contrato.totalProdutos = 0;
				angular.forEach(itensPedidoContrato, function(item){
					if('COMODATO' === item.produto.categoria) {
						var quantidade = item.quantidade;
						var preco = item.produto.preco;
						var total = quantidade * preco;
						$scope.contrato.totalComodato = $scope.contrato.totalComodato + total;
					}
					if('REVENDA' === item.produto.categoria) {
						var quantidade = item.quantidade;
						var preco = item.produto.preco;
						var total = quantidade * preco;
						$scope.contrato.totalProdutos = $scope.contrato.totalProdutos + total;
					}
				});
				
				$scope.vlrTotalFinalPedido = calculaVlrTotalFinalPedido();
				$scope.modoReimpressaoContrato = true;
			};

			var formatPreco = function(strFormat) {
				
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
			
			var calculaVlrTotalFinalPedido = function() {

				var frete = formatPreco($scope.contrato.pagamento.vlrTxEntrega);
				frete = frete.replace(",", ".")
				var vlrTxEntrega = parseFloat(frete);
				$scope.vlrFrete = vlrTxEntrega;
				var totalProdutos = $scope.contrato.totalProdutos;
				var totalComodato = $scope.contrato.totalComodato;
				var totalFinal =  totalProdutos + totalComodato + vlrTxEntrega;
									
				return totalFinal;
				 
			};

			$scope.devolverPedido = function(pedido) {
				$scope.pedidoSelecionado = pedido;
				var numPedido = pedido.codigo;
				$scope.numeroPedido = numPedido;
			}			
			
			$scope.detalhesPedido = function(pedido) {
				$scope.detalhePedido = pedido;
			};
			
			$scope.abaSelecionada = function(abaSelecionada) {
				if ('principal' === abaSelecionada)  {
					$scope.mostrarItensAbaPrincipal = true;
					return;
				}
				$scope.mostrarItensAbaPrincipal = false;
			};

			$scope.addItemPedido = function() {
				$scope.item.produto = $scope.produtoPedido;
				delete $scope.item.produto.$$hashKey;
				delete $scope.item.produto.dataCadastro;
				$scope.itens.push($scope.item);
				$scope.produtoPedido = {};
				$scope.produtoSelecionado = "";
				$scope.item = {};
				$scope.mostrarItensAbaPrincipal = false;
			};

			$scope.voltarBotaoAdiconarItens = function() {
				$scope.mostrarItensAbaPrincipal = true;
			};

			$scope.removeItem = function (item) {
				var index = $scope.itens.indexOf(item);
				$scope.itens.splice(index, 1);
			};

			$scope.selecionarItem = function(item) {
				$scope.filterProduto = [];
				$scope.hideList = true;
				$scope.produtoPedido = item;
				$scope.produtoSelecionado = item.nome;
			};

			$scope.selecionarCliente = function(item) {
				$scope.filterCliente = [];
				$scope.hideListCliente = true;
				$scope.cliente = item;
				$scope.clienteSelecionado = item.nome;
			};

			$scope.complete = function(param) {
				$scope.qtdIndisponivel = false;
				$scope.pedidoMaiorQueEstoque = false;
				$scope.vlrQuantidadeIncorreta = false;
				$scope.qtdItemPermitida = true;
				$scope.hideList = false;
				var output = [];
				angular.forEach($scope.produtos, function(produtoMap){
					if (produtoMap.nome === null) {
						return;
					}
					if(produtoMap.nome.toLowerCase().indexOf(param.toLowerCase()) >= 0) {
						output.push(produtoMap);
					}
				});
				$scope.filterProduto = output;
			};

			$scope.completeCliente = function(param) {
				$scope.hideListCliente = false;
				var output = [];
				angular.forEach($scope.clientes, function(clienteMap){
					if (clienteMap.nome === null) {
						return;
					}
					if(clienteMap.nome.toLowerCase().indexOf(param.toLowerCase()) >= 0) {
						output.push(clienteMap);
					}
				});
				$scope.filterCliente = output;
			};
		
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
			 * Retorna a quantidade de pedidos para poder calcular a paginação.
			 * Referências: https://codepen.io/dustinmyers/pen/QEpLXa
			 * Referências: https://codepen.io/khilnani/pen/qEWojX
			 */
			$scope.getPedidos = function () {
				// needed for the pagination calc
				// https://docs.angularjs.org/api/ng/filter/filter
				return $filter('filter')($scope.pedidos, $scope.q)
				/*
				 * // manual filter // if u used this, remove the filter from
				 * html, remove above line and replace data with getData()
				 * 
				 * var arr = []; if($scope.q == '') { arr = $scope.data; } else {
				 * for(var ea in $scope.data) {
				 * if($scope.data[ea].indexOf($scope.q) > -1) { arr.push(
				 * $scope.data[ea] ); } } } return arr;
				 */
			};

			/*******************************************************************
			 * Calcula o númmero de páginas que o paginador irá ter.
			 */
			var numberOfPages = function () {
				var total = Math.ceil($scope.pedidos.length / $scope.pageSize);
				$scope.totalPaginas = total;
				if ($scope.totalPaginas) {
					carregarPageNumbers();
				}
				return total;
			};
									
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
			
			var carregarProdutos = function () {
				$http.get("/carregarProdutos/")
					.success(function (ret) {
						$scope.produtos = ret;
					});
			};

			var carregarClientes = function () {
				$http.get("/carregarClientes/")
					.success(function (ret) {
						$scope.clientes = ret;
					});
			};
		
			$scope.exibirPedidos = function (value) {
				$scope.isListaPedidos = value;
			};

			var carregarPedidos = function () {
				$http.get("/carregarPedidos/")
					.success(function (ret) {
						$scope.pedidos = [];
						$scope.pedidos = ret;
						numberOfPages();
					});
			};

			
			var editarPedido = function (pedido) {

				$scope.pedido = pedido;
				delete $scope.pedido.dataNascimento;
				document.getElementById('dataNascimento').value = pedido.dtaNascimento;

				var enderecos = pedido.enderecos;
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

				$scope.contatos = pedido.contatos;
				$scope.isListaPedidos = false;
				$scope.classEdit = true;

			}
			
			var updatePedido = function() {
				
				var pedido = $scope.pedido;
				delete pedido.$$hashKey;	
				delete pedido.cliente.$$hashKey;
				delete pedido.dataCadastro;
				delete pedido.cliente.dataCadastro;
				delete pedido.cliente.dataNascimento;

				delete pedido.cliente.$$hashKey;
				delete pedido.dataEntrega;
				delete pedido.dataPedido;
				delete pedido.dataDevolucao;
				delete pedido.pagamento.dataCriacao;
				delete pedido.pagamento.dataVencimento;
				delete pedido.pagamento.dataPagamento;

				$http.put("/pedido/finalizar", pedido)
					.success(function (pedidoFinalizado) {

						carregarPedidos();
						limparForm('pedidoForm');
						Materialize.toast('Pedido Finalizado com sucesso.', 4000, 'rounded');

					})
					.error(function (data, status) {
						// Handle HTTP error
						console.log(data);
						Materialize.toast(data.message, 4000, 'rounded');
					});
				$scope.isEdit = false;
			};

			var cadastrarPedido = function () {

				$scope.realizandoPedido = true;
				$scope.pedido.cliente = $scope.cliente;
				$scope.pagamento.vlrTxEntrega = $scope.vlrFrete;
				$scope.pedido.pagamento = $scope.pagamento;
				$scope.pedido.itens = $scope.itens;

				var pedido = $scope.pedido;
				delete pedido.$$hashKey;	
				delete pedido.cliente.$$hashKey;
				var itensPedido = [];
				
				angular.forEach(pedido.itens, function (value, key) {
					
					delete value.$$hashKey;
					itensPedido.push(value);


				});

				delete pedido.itens;
				delete pedido.cliente.dataCadastro;
				delete pedido.cliente.dataNascimento;
				pedido.itens = itensPedido;
				pedido.contratoImpressao = $scope.contratoImpressao;

				if (pedido == null) {
					Materialize.toast('Pedido nulo.', 4000, 'rounded')
				} else {
					if (pedido.codigo) {
						delete pedido.dataCadastro;
						$http.put("/editarPedido/", pedido)
							.success(function (pedidoCadastrado) {

								carregarPedidos();
								limparForm('pedidoForm');
								$scope.realizandoPedido = false;
								Materialize.toast('Pedido atualizado com sucesso.', 4000, 'rounded');

							})
							.error(function (data, status) {
								// Handle HTTP error
								console.log(data);
								Materialize.toast(data.message, 4000, 'rounded');
							});
						$scope.isEdit = false;
						return;
					}
					$http.post("/cadastrarPedido/", pedido)
						.success(function (pedidoCadastrado) {
							delete $scope.pedido;
							$scope.pedidoForm.$setPristine();							
							limparForm('pedidoForm');
							$scope.realizandoPedido = false;
							Materialize.toast('Pedido cadastrado com sucesso.', 4000, 'rounded');
						})
						.error(function (data, status) {
							// Handle HTTP error
							console.log(data);
							$scope.realizandoPedido = false;
							Materialize.toast(data.message, 4000, 'rounded');
						});

				}
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
				carregarPedidos();
			};

			$scope.registrarEntrega = function(pedido) {
				var codigo = pedido.codigo;
				$http.put("/pedido/"+ codigo + "/registrar-entrega")
						.success(function (pedidoCadastrado) {
						
							carregarPedidos();
							limparForm('pedidoForm');
							Materialize.toast('Registro de entrega efetuado com sucesso.', 4000, 'rounded');
						})
						.error(function (data, status) {
							// Handle HTTP error
							console.log(data);
							Materialize.toast(data.message, 4000, 'rounded');
						});
			};

			$scope.removerpedidos = function (pedidos) {

				angular.forEach(pedidos, function (value, key) {
					if (value.selecionado) {

						var id = value.idPedido;
						$http.delete("/removerPedido" + id).success(function (pedido) {
							carregarPedidos();
						});

					}

				});
				carregarPedidos();
			};

			$scope.onEdit = function (prod) {
				$scope.isAtualiza = true;
				$scope.classEdit = true;
				$scope.pedido = prod;	
				$scope.isEdit = true;
			}

			$scope.addItem = function () {
				cadastrarPedido();
			};

			$scope.editItem = function () {
				cadastrarPedido();
			};

			
			$scope.classe1 = "selecionado";
			$scope.classe2 = "negrito";

			carregarPedidos();			
			carregarProdutos();
			carregarClientes();		
			carregaDataAtual();
							
		}).filter('startFrom', function () {
		return function (input, start) {
			if (!input) {
				return;
			}
			start = +start; // parse to int
			return input.slice(start);
		}
	});