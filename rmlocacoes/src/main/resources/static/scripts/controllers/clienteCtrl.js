angular.module("cliente", ["ngMessages", "ngResource", "ngMask"]);
angular.module("cliente")
	.controller("clienteController", 
	function ($scope, 
		$filter, 
		$http, 
		myResource) {

		$scope.app = "Lista de Clientes";
		$scope.detalhesCliente = {};
		$scope.pessoaFisica = 'Pessoa Física';
		$scope.pessoaJuridica = 'Pessoa Jurídica';		
		$scope.cliente = {
			contatos: [],
			enderecos: []
		};
		$scope.endereco = {
			funcFim: "",
			funcInicio: "",
			enderecoEntrega: true,
			enderecoComercial: false
		};
		$scope.contato = {};
		$scope.enderecoComercial = {
			funcFim: "",
			funcInicio: "",
			enderecoEntrega: false,
			enderecoComercial: true
		};
		$scope.dadosBancarios = {};
		$scope.cep = {};
		$scope.enderecos = [];
		$scope.clientes = [];
		$scope.contatos = [];
		$scope.isAtualiza = false;
		$scope.isCopiarEndereco = false;
		$scope.classCep = false;
		$scope.classLogradouro = false;
		$scope.classComplemento = false;
		$scope.classNumero = false;
		$scope.classBairro = false;
		$scope.classCidade = false;
		$scope.classEstado = false;
		$scope.classHorarioFim = false;
		$scope.classHorarioInicio = false;
		$scope.isListaClientes = false;
		$scope.isEnderecoResidencial = false;
		$scope.classEdit = false;
		$scope.isEdit = false;
		$scope.exibeGridClientes = true;
		$scope.isFormPedido = false;

		$scope.pageNumbers = [];
			$scope.activePage = 1;

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
			 * Retorna a quantidade de clientes para poder calcular a paginação.
			 * Referências: https://codepen.io/dustinmyers/pen/QEpLXa
			 * Referências: https://codepen.io/khilnani/pen/qEWojX
			 */
			$scope.getClientes = function () {
				// needed for the pagination calc
				// https://docs.angularjs.org/api/ng/filter/filter
				return $filter('filter')($scope.clientes, $scope.q)
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
				var total = Math.ceil($scope.getClientes().length / $scope.pageSize);
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
		
		$scope.exibirClientes = function(value) {
			$scope.isListaClientes = value;
		};

		var carregarClientes = function () {
			$http.get("/carregarClientes/")
				.success(function (ret) {
					$scope.clientes = [];
					$scope.clientes = ret;
					numberOfPages();
				});
		};

		var carregarEndereco = function (cep) {
			$http.get("https://viacep.com.br/ws/" + cep + "/json/")
				.success(function (ret) {
					if (ret.erro) {
						Materialize.toast('Cep Incorreto/Não encontrado.', 4000, 'rounded')
						return;
					}
					
					if (!$scope.isCopiarEndereco && $scope.isEnderecoResidencial) {
						$scope.endereco = {}
						$scope.endereco.logradouro = ret.logradouro;
						$scope.endereco.bairro = ret.bairro;
						$scope.endereco.cep = ret.cep;
						$scope.endereco.cidade = ret.localidade;
						$scope.endereco.estado = ret.uf;
						$scope.endereco.enderecoEntrega = true;
						$scope.endereco.enderecoComercial = false;
						alteraEstadoCamposPreenchidosAutomaticamente();
					}
					
					if (!$scope.isCopiarEndereco && !$scope.isEnderecoResidencial) {
						
						$scope.enderecoComercial = {}
						$scope.enderecoComercial.logradouro = ret.logradouro;
						$scope.enderecoComercial.bairro = ret.bairro;
						$scope.enderecoComercial.cep = ret.cep;
						$scope.enderecoComercial.cidade = ret.localidade;
						$scope.enderecoComercial.estado = ret.uf;
						$scope.enderecoComercial.enderecoEntrega = false;
						$scope.enderecoComercial.enderecoComercial = true;
						alteraEstadoCamposPreenchidosAutomaticamente();
						return;
					}
					
					$scope.isEnderecoResidencia = false;
				});
		};

		var alteraEstadoCamposPreenchidosAutomaticamente = function () {

			$scope.clienteForm.logradouro.$touched = true;
			$scope.clienteForm.logradouro.$untouched = false;
			$scope.clienteForm.bairro.$touched = true;
			$scope.clienteForm.bairro.$untouched = false;
			$scope.clienteForm.cep.$touched = true;
			$scope.clienteForm.cep.$untouched = false;
			$scope.clienteForm.cidade.$touched = true;
			$scope.clienteForm.cidade.$untouched = false;
			$scope.clienteForm.estado.$touched = true;
			$scope.clienteForm.estado.$untouched = false;

			$scope.classCep = true;
			$scope.classLogradouro = true;
			$scope.classComplemento = true;
			$scope.classNumero = true;
			$scope.classBairro = true;
			$scope.classCidade = true;
			$scope.classEstado = true;
		}

		$scope.addClassFieldDatePicker = function (variavel) {
			if (variavel === "classHorarioInicio") {
				$scope.classHorarioInicio = true;
			}
			if (variavel === "classHorarioFim") {
				$scope.classHorarioFim = true;
			}

		};

		$scope.permiteCopiarEndereco = function () {

			if ($scope.endereco.cep && $scope.endereco.cidade &&
				$scope.endereco.logradouro && $scope.endereco.bairro) {
				return true;
			}

			return false;
		};

		$scope.copiarEndereco = function (event) {

			var horarioInicio = document.getElementById('horarioInicio').value;
			var horarioFim = document.getElementById('horarioFim').value;

			$scope.endereco.funcInicio = horarioInicio;
			$scope.endereco.funcFim = horarioFim;
			$scope.endereco.enderecoEntrega = true;
			$scope.endereco.enderecoComercial = false;
			var endereco = $scope.endereco;

			if (event) {
				$scope.isCopiarEndereco = true;
				$scope.enderecoComercial.cep = endereco.cep;
				$scope.enderecoComercial.logradouro = endereco.logradouro;
				$scope.enderecoComercial.numero = endereco.numero;
				$scope.enderecoComercial.complemento = endereco.complemento;
				$scope.enderecoComercial.bairro = endereco.bairro;
				$scope.enderecoComercial.cidade = endereco.cidade;
				$scope.enderecoComercial.estado = endereco.estado;
				$scope.enderecoComercial.funcInicio = endereco.funcInicio;
				$scope.enderecoComercial.funcFim = endereco.funcFim;
				$scope.enderecoComercial.enderecoEntrega = false;
				$scope.enderecoComercial.enderecoComercial = true;

				$scope.enderecos.push(endereco);
				$scope.enderecos.push($scope.enderecoComercial);
				$scope.addClassFieldDatePicker("classHorarioInicio");
				$scope.addClassFieldDatePicker("classHorarioFim");
				return;
			}

			var item = $scope.enderecoComercial;
			var pos = $scope.enderecos.indexOf(item);
			$scope.enderecos.splice(pos);
			$scope.enderecoComercial = {};
			$scope.isCopiarEndereco = false;

		};

		$scope.addContato = function (contato) {
			$scope.contatos.push(contato);
			$scope.contato = {};
		};

		$scope.removeContato = function (contato) {
			delete contato.$$hashKey;
			var index = $scope.contatos.indexOf(contato);
			$scope.contatos.splice(index, 1);
		};
		
		$scope.editarCliente = function(cliente) {
			
			$scope.cliente = cliente;
			delete $scope.cliente.dataNascimento;
			document.getElementById('dataNascimento').value = cliente.dtaNascimento;
			
			var enderecos = cliente.enderecos;
			if (enderecos) {
				angular.forEach(enderecos, function(value, key) {
					  if (value.enderecoEntrega) {
						  $scope.endereco = value;
					  }
					  if (!value.enderecoEntrega) {
						  $scope.enderecoComercial = value;
					  }
					});
			}
			
			$scope.contatos = cliente.contatos;			
			$scope.isListaClientes = false;
			$scope.classEdit = true;
			$scope.isEdit = true;
			
		}
		
		$scope.addItem = function() {
			cadastrarCliente();
		};
		
		$scope.editItem = function() {
			cadastrarCliente();
		};

		var isFormValid = function() {
			return $scope.clienteForm.$valid;
		};

		var cadastrarCliente = function () {

			var pessoaJuridica = $scope.cliente.pessoaJuridica || false;
			var dtaNascimento = document.getElementById('dataNascimento').value;			
						
			var nome = $scope.cliente.nome;
			var cpf = $scope.cliente.cpf;
			var cnpj = $scope.cliente.cnpj;
			var telefoneCelular = $scope.cliente.telefoneCelular;

			if (!pessoaJuridica) {
				if ( (!nome || nome === '') || 
				(!cpf || cpf === '') || 
				(!telefoneCelular || telefoneCelular === '')){
				Materialize.toast('Campo(s) nome/cpf/telefone são obrigatórios.', 4000, 'rounded')
				return;
				}
			}			
			if (pessoaJuridica) {
				if ( (!nome || nome === '') || 
				(!cnpj || cnpj === '') || 
				(!telefoneCelular || telefoneCelular === '')){
				Materialize.toast('Campo(s) nome/cnpj/telefone são obrigatórios.', 4000, 'rounded')
				return;
				}
			}				

			if ($scope.endereco.logradouro && $scope.endereco.cep) {
				$scope.enderecos.push($scope.endereco);
			}
			if ($scope.enderecoComercial.logradouro && $scope.enderecoComercial.cep) {
				$scope.enderecos.push($scope.enderecoComercial);
			}						

			$scope.cliente.contatos = $scope.contatos;
			$scope.cliente.enderecos = $scope.enderecos;
			
			var cliente = $scope.cliente;
			delete cliente.$$hashKey;
			delete cliente.dataCadastro;
			delete cliente.dataNascimento;
			cliente.dtaNascimento = dtaNascimento;			
			
			if (cliente == null) {
				alert("Cliente nullo");
			} else {
				if (cliente.codigo) {
					$http.put("/editarCliente/", cliente)
					.success(function (clienteCadastrado) {
						
						limparForm('clienteForm');
						Materialize.toast('Cliente atualizado com sucesso.', 4000, 'rounded');
						
					})
					.error(function(data, status) {
				        // Handle HTTP error
						console.log(data);
				    });
					$scope.isEdit = false;
					return;
				}
				$http.post("/cadastrarCliente/", cliente)
					.success(function (clienteCadastrado) {
						delete $scope.cliente;
						$scope.clienteForm.$setPristine();
						limparForm('clienteForm');
						Materialize.toast('Cliente cadastrado com sucesso.', 4000, 'rounded');
					})
					.error(function(data, status) {
				        // Handle HTTP error
						console.log(data);
				    });

			}
			
			$scope.isEdit = false;
		};

		var limparForm = function (idForm) {
			$scope.cliente = {
				contatos: [],
				enderecos: []
			};
			$scope.endereco = {
				funcFim: "",
				funcInicio: ""
			};
			$scope.contato = {};
			$scope.enderecoComercial = {
				funcFim: "",
				funcInicio: ""
			};
			$scope.dadosBancarios = {};
			$scope.cep = {};
			$scope.enderecos = [];
			$scope.clientes = [];
			$scope.contatos = [];
			$scope.isAtualiza = false;
			$scope.isCopiarEndereco = false;
			$scope.classCep = false;
			$scope.classLogradouro = false;
			$scope.classComplemento = false;
			$scope.classNumero = false;
			$scope.classBairro = false;
			$scope.classCidade = false;
			$scope.classEstado = false;
			$scope.classHorarioFim = false;
			$scope.classHorarioInicio = false;
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
		   carregarClientes();
		};

		$scope.buscarCep = function (endereco, value) {
			
			if ('isEnderecoResidencial' === value) {
				var cep = $scope.endereco.cep.replace("-", "");
				carregarEndereco(cep);
				$scope.isEnderecoResidencial = true;
				return;
			}
			
			if ('isEnderecoComercial' === value) {
				var cep = $scope.enderecoComercial.cep.replace("-", "");
				carregarEndereco(cep);
				$scope.isEnderecoResidencial = false;
			}
			
		};

		$scope.removerclientes = function (clientes) {

			angular.forEach(clientes, function (value, key) {
				if (value.selecionado) {

					var id = value.idCliente;
					$http.delete("/removerCliente" + id).success(function (cliente) {
						carregarClientes();
					});

				}

			});
			carregarClientes();
		};

		$scope.atualizaClientes = function (cliente) {
			$http.put("/rmlocacoes/atualizarCliente/", cliente)
				.success(function (cliente) {
					delete $scope.cliente;
					$scope.clienteForm.$setPristine();
					$scope.isAtualiza = false;
					$scope.cliente = null;
					carregarClientes();
				});
		}

		$scope.isclientesSelecionados = function (clientes) {
			/* A função some é equivalente a um filter */
			return clientes.some(function (cliente) {
				return cliente.selecionado;
			});
		};

		$scope.ordernarPor = function (campo) {
			$scope.criterioDeOrdenacao = campo;
			$scope.direcaoDaOrdenacao = !$scope.direcaoDaOrdenacao;
		};
		
		$scope.abaSelectecd = function(abaSelecionada) {
			if ('principal' === abaSelecionada)  {
				$scope.exibeGridClientes = true;
				return;
			}
			$scope.exibeGridClientes = false;
		};

		$scope.exibirDetalhesDoCliente = function(cliente) {
			$scope.classEdit = true;
			cliente.enderecoComercial = null;
			cliente.enderecoEntrega = null;
			
			angular.forEach(cliente.enderecos, function(value, key) {
				if (value.enderecoComercial) {
					cliente.enderecoComercial = value;					
				} else {
					cliente.enderecoEntrega = value;		
				}
			});
			$scope.detalhesCliente = cliente;
		};

		carregarClientes();
			
})
.filter('startFrom', function () {
	return function (input, start) {
		if (!input) {
			return;
		}
		start = +start; // parse to int
		return input.slice(start);
	}
})
.factory('myResource', function ($resource) {
		var rest = $resource(
			'https://viacep.com.br/ws/:cep/json/', {
				'cep': ''
			}
		);
		return rest;
});