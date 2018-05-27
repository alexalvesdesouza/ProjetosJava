angular.module("cliente", ["ngMessages", "ngResource", "ngMask"]);
angular.module("cliente")
	.controller("clienteController", 
	function ($scope, 
		$filter, 
		$http, 
		myResource) {

		$scope.app = "Lista de Clientes";
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

		var carregarClientes = function () {
			$http.get("http://localhost:9090/carregarClientes/")
				.success(function (ret) {
					$scope.clientes = ret;
				});
		};

		var carregarEndereco = function (cep) {
			$http.get("https://viacep.com.br/ws/" + cep + "/json/")
				.success(function (ret) {
					if (ret.erro) {
						Materialize.toast('Cep Incorreto/Não encontrado.', 4000, 'rounded')
						return;
					}
					$scope.endereco = {}
					$scope.endereco.logradouro = ret.logradouro;
					$scope.endereco.bairro = ret.bairro;
					$scope.endereco.cep = ret.cep;
					$scope.endereco.cidade = ret.localidade;
					$scope.endereco.estado = ret.uf;
					alteraEstadoCamposPreenchidosAutomaticamente();

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
			var pos = $scope.contatos.indexOf(contato);
			$scope.contatos.splice(pos);
		};

		$scope.cadastrarClientes = function () {

			$scope.cliente.dtaNascimento = document.getElementById('dataNascimento').value;

			$scope.cliente.contatos = $scope.contatos;
			$scope.cliente.enderecos = $scope.enderecos;
			$scope.cliente.dadosBancarios = $scope.dadosBancarios;
			var cliente = $scope.cliente;

			if (cliente == null) {
				alert("Cliente nullo");
			} else {

				$http.post("http://localhost:9090/cadastrarCliente/", cliente)
					.success(function (clienteCadastrado) {
						delete $scope.cliente;
						$scope.clienteForm.$setPristine();
						carregarClientes();
						limparForm();
						Materialize.toast('Cliente cadastrado com sucesso.', 4000, 'rounded')
					});

			}
		};

		var limparForm = function () {
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
		};

		$scope.buscarCep = function (endereco) {
			var cep = $scope.endereco.cep.replace("-", "");
			carregarEndereco(cep);
		};

		$scope.removerclientes = function (clientes) {

			angular.forEach(clientes, function (value, key) {
				if (value.selecionado) {

					var id = value.idCliente;
					$http.delete("http://localhost:9090/removerCliente" + id).success(function (cliente) {
						carregarClientes();
					});

				}

			});
			carregarClientes();
		};

		$scope.onEdit = function (prod) {
			$scope.cliente = prod;
			$scope.isAtualiza = true;
		}

		$scope.atualizaClientes = function (cliente) {
			$http.put("http://localhost:9090/rmlocacoes/atualizarCliente/", cliente)
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

		$scope.classe1 = "selecionado";
		$scope.classe2 = "negrito";

		carregarClientes();

	})
	.factory('myResource', function ($resource) {
		var rest = $resource(
			'https://viacep.com.br/ws/:cep/json/', {
				'cep': ''
			}
		);
		return rest;
	});