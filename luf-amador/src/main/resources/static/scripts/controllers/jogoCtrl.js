var jogo = angular.module("jogo", ["ngMessages", "ngResource", "ngMask"]);
jogo.controller("jogoController",
    function ($scope,
        $filter,
        $http) {

        const BASE_PATH = "/jogos";
        const BASE_PATH_CLASSIFICACAO = "/classificacoes";

        $scope.tabelaJogosTempoReal = [];
        $scope.classificacoes = [];
        $scope.tabelaCampeonato = {};
        $scope.jogoSelecionado = {};

        var carregarJogosTempoReal = function () {
            $http
                .get(BASE_PATH + '/tempo-real')
                .success(function (ret) {
                    $scope.tabelaJogosTempoReal = [];
                    $scope.tabelaJogosTempoReal = ret;
                })
                .error(function (data, status) {

                });
        };
        
        var carregarClassificacao = function () {
        	$http
        	.get(BASE_PATH_CLASSIFICACAO)
        	.success(function (ret) {
        		$scope.classificacoes = [];
        		$scope.classificacoes = ret;
        	})
        	.error(function (data, status) {
        		
        	});
        };

        var atualizarResultadoParcialJogo = function () {
            var jogo = $scope.jogoSelecionado;
            delete jogo.dataAtualizacao;
            delete jogo.dataCriacao;
            delete jogo.agremiacaoA.dataAtualizacao;
            delete jogo.agremiacaoA.dataCriacao;
            delete jogo.agremiacaoB.dataAtualizacao;
            delete jogo.agremiacaoB.dataCriacao;
            
            $http.put(BASE_PATH + '/tempo-real/atualizar', jogo)
                .success(function (jogoAtualizado) {
                    delete $scope.jogoSelecionado;
                    carregarJogosTempoReal();
                    limparForm('formTempoReal');
                    Materialize.toast('Resultado jogo atualizado sucesso', 4000, 'rounded');
                })
                .error(function (data, status) {
                    Materialize.toast(data.message, 4000, 'rounded');
                })
        };

        var encerrarJogoRodada = function () {
            var jogo = $scope.jogoSelecionado;

            $http.put(BASE_PATH + '/tempo-real/atualizar', jogo)
                .success(function (jogoAtualizado) {
                    delete $scope.jogoSelecionado;
                    carregarJogosTempoReal();
                    limparForm('formTempoReal');
                    Materialize.toast('Resultado jogo atualizado sucesso', 4000, 'rounded');
                })
                .error(function (data, status) {
                    Materialize.toast(data.message, 4000, 'rounded');
                })
        };

        $scope.editarJogo = function (jogo) {
            $scope.jogoSelecionado = {};
            $scope.jogoSelecionado = jogo;
        };

        $scope.mudarPlacarJogo = function (jogo) {
            $scope.jogoSelecionado = jogo;
            atualizarResultadoParcialJogo();
        };
        
        $scope.abaSelecionada = function(abaSelecionada) {
        	if (abaSelecionada === 'classificacao') {
        		carregarClassificacao();
        	}
        };

        carregarJogosTempoReal();

    });