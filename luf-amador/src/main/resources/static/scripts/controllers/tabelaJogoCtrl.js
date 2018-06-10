var tabelaJogo = angular.module("tabelaJogo", ["ngMessages", "ngResource"]);
tabelaJogo.controller("tabelaJogoController",
        function ($scope,
            $filter,
            $http) {

            const BASE_PATH = "/tabela-jogos";
            const BASE_PATH_CAMPEONATOS = "/campeonatos";

            $scope.tabelaJogos = [];
            $scope.campeonatos = [];
            $scope.inscricoes = [];
            $scope.tabelaJogo = {};
            $scope.agremiacaoB = {};
            $scope.agremiacaoA = {};
            $scope.rodada = {};
            $scope.chave = {};
            $scope.dataPartida = {};
            $scope.horarioPartida = {};
            $scope.jogo = {};
            $scope.modoEdicao = false;
            $scope.classEdit = false;
            $scope.isEdit = false;
            $scope.campeonatoSelected = false;
            $scope.hasEquipeASelecionada = false;
            $scope.hasEquipeBSelecionada = false;
            $scope.hasConfronto = false;

            var carregaInscricoes = function () {
            	$scope.inscricoes = [];
                $scope.inscricoes = $scope.campeonatoSelecionado.inscricoes;
            };

            var carregaLocais = function () {

            };


            $scope.selecionarCampeonato = function (item) {
                $scope.filterCampeonato = [];
                $scope.hideListCampeonatos = true;
                $scope.nomeCampeonatoSelectionado = item.nomeCampeonato;
                $scope.campeonatoSelecionado = item;
                $scope.campeonato = item;
                $scope.campeonatoSelected = true;
                carregaInscricoes();
            };

            $scope.completeCampeonato = function (param) {
                $scope.hideListCampeonatos = false;
                var output = [];
                angular.forEach($scope.campeonatos, function (campeonatoMap) {
                    if (campeonatoMap.nomeCampeonato === null) {
                        return;
                    }
                    if (campeonatoMap.nomeCampeonato.toLowerCase().indexOf(param.toLowerCase()) >= 0) {
                        output.push(campeonatoMap);
                    }
                });
                $scope.filterCampeonato = output;
            };

            $scope.salvarTabelaJogo = function (tabelaJogo) {
                $http.post(BASE_PATH, tabelaJogo)
                    .success(function (tabelaJogoCadastrada) {
                        limparFormularioTabelaJogo();
                        carregarTabelaJogos();
                        // $scope.formularioTabelaJogo.$setPristine();
                        Materialize.toast('TabelaJogo cadastrada com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.atualizarTabelaJogo = function (tabelaJogo) {
                $http.put(BASE_PATH, tabelaJogo)
                    .success(function (tabelaJogoAtualizada) {
                        limparFormularioTabelaJogo();
                        $scope.modoEdicao = false;
                        carregarTabelaJogos();
                        // $scope.formularioTabelaJogo.$setPristine();
                        Materialize.toast('TabelaJogo atualizada com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            var limparFormularioTabelaJogo = function () {
                $scope.tabelaJogo = {};
            };

            var carregarTabelaJogos = function () {
                $http
                    .get(BASE_PATH)
                    .success(function (ret) {
                        $scope.tabelaJogos = [];
                        $scope.tabelaJogos = ret;
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            var carregarCampeonatos = function () {
                const inscAbertas = true;
                $http
                    .get(BASE_PATH_CAMPEONATOS + "/" + inscAbertas + "/")
                    .success(function (ret) {
                        $scope.campeonatos = [];
                        $scope.campeonatos = ret;
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.editarTabelaJogo = function (tabelaJogo) {
                $scope.classEdit = true;
                $scope.modoEdicao = true;
                $scope.tabelaJogo = tabelaJogo;
            };

            $scope.deletarTabelaJogo = function (tabelaJogo) {
                const codigo = tabelaJogo.codigo;
                $http.delete(BASE_PATH + "/" + codigo + "/")
                    .success(function (tabelaJogoDeletado) {

                        carregarTabelaJogos();
                        limparFormularioTabelaJogo();
                        // $scope.formularioTabelaJogo.$setPristine();
                        Materialize.toast('TabelaJogo deletado com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };
            
            $scope.salvarJogosRodada = function () {
            	var tabelaJogos = {};
            	tabelaJogos.jogos = $scope.tabelaJogos;
            	tabelaJogos.campeonato = $scope.campeonato;
            	$http.post(BASE_PATH, tabelaJogos)
            	.success(function (tabela) {
            		
            		carregarTabelaJogos();
            		limparFormularioTabelaJogo();
            		// $scope.formularioTabelaJogo.$setPristine();
            		Materialize.toast('TabelaJogo deletado com sucesso.', 4000, 'rounded');
            	})
            	.error(function (data, status) {
            		Materialize.toast(data.message, 4000, 'rounded');
            	});
            };

            $scope.abaSelecionada = function (abaSelecionada) {
                $scope.abaSelecionada = abaSelecionada;
            };
            
            $scope.addJogo = function () {

            	jogo = {};
            	jogo.agremiacaoA = $scope.agremiacaoA;
            	jogo.agremiacaoB = $scope.agremiacaoB;
            	jogo.dataPartida = $scope.dataPartida;
            	jogo.horarioPartida = $scope.horarioPartida;
            	jogo.chave = $scope.chave;
            	jogo.rodada = $scope.rodada;
            	jogo.local = $scope.local;
            	
            	$scope.tabelaJogos.push(jogo);
            	limparForm('tabelaJogoForm');
            };
            

            $scope.selecionaAgremiacaoA = function (inscricao) {
            	if ($scope.hasEquipeASelecionada) {
            		Materialize.toast('Já existe uma Agremiação A selecionada. Seleciona agora a Agremiação B.', 4000, 'rounded');
            	} else {
	                var agremiacao = inscricao.agremiacao;
	                var index = $scope.inscricoes.indexOf(inscricao);
	                $scope.inscricoes.splice(index, 1);
	                $scope.agremiacaoA = agremiacao;
	                $scope.hasEquipeASelecionada = true;
	                if ($scope.hasEquipeASelecionada && $scope.hasEquipeBSelecionada) {
	                	 $scope.hasConfronto = true;
	                	 return;
	                }
	                $scope.hasConfronto = false;
            	}
            };

            $scope.selecionaAgremiacaoB = function (inscricao) {
            	if ($scope.hasEquipeBSelecionada) {
            		Materialize.toast('Já existe uma Agremiação B selecionada. Seleciona agora a Agremiação A.', 4000, 'rounded');
            	} else {
	                var agremiacao = inscricao.agremiacao;
	                var index = $scope.inscricoes.indexOf(inscricao);
	                $scope.inscricoes.splice(index, 1);
	                $scope.agremiacaoB = agremiacao;
	                $scope.hasEquipeBSelecionada = true;
	                if ($scope.hasEquipeASelecionada && $scope.hasEquipeBSelecionada) {
	                	 $scope.hasConfronto = true;
	                	 return;
	                }
	                $scope.hasConfronto = false;
            	}
            };
            
            var limparForm = function (idForm) {
            	
            	$scope.agremiacaoA = {};
            	$scope.agremiacaoB = {};
            	$scope.jogo = {};
            	$scope.hasConfronto = false; 
            	
            	$scope.hasEquipeBSelecionada = false;
                $scope.hasEquipeASelecionada = false;
                $scope.hasConfronto = false;
               
            	$scope.jogo =  $scope.jogoCopy;
    		   
//    		   var form = document.getElementById(idForm);
//    		   if (form === null) {
//    				return;
//    			}
//
//    		   var inputs = form.querySelectorAll('input');
//    		   for (var i = 0; i < inputs.length; i++) {
//    			   if (inputs[i].type != 'checkbox' && inputs[i].type != 'radio') {
//    				   inputs[i].value = '';
//    			   }
//    		   }
//    	   
//    		   var textarea = form.querySelectorAll('textarea');
//    		   for (var i = 0; i < textarea.length; i++) {
//    			   textarea[i].value = '';
//    		   }
//    	   
//    		   inputs = form.querySelectorAll('input[type=checkbox], input[type=radio]');
//    		   for (i = 0; i < inputs.length; i++) {
//    			   inputs[i].checked = false;
//    		   }
//    	   
//    		   var selects = form.querySelectorAll('select');
//    		   for (i = 0; i < selects.length; i++) {
//    			   var options = selects[i].querySelectorAll('option');
//    			   if (options.length > 0) {
//    				   selects[i].value = options[0].value;
//    			   }
//    		   }
    		};

            carregarTabelaJogos();
            carregarCampeonatos();

        })
    .filter('startFrom', function () {
        return function (input, start) {
            if (!input) {
                return;
            }
            start = +start; // parse to int
            return input.slice(start);
        }
    });