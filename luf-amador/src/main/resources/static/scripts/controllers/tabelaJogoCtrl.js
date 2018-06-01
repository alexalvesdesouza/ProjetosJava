var tabelaJogo = angular.module("tabelaJogo", ["ngMessages", "ngResource"]);
tabelaJogo.controller("tabelaJogoController",
        function ($scope,
            $filter,
            $http) {

            const BASE_PATH = "/tabelaJogos";
            const BASE_PATH_CAMPEONATOS = "/campeonatos";

            $scope.tabelaJogos = [];
            $scope.campeonatos = [];
            $scope.inscricoes = [];
            $scope.tabelaJogo = {};
            $scope.agremiacaoB = {};
            $scope.agremiacaoA = {};
            $scope.jogo = {};
            $scope.modoEdicao = false;
            $scope.classEdit = false;
            $scope.isEdit = false;
            $scope.campeonatoSelected = false;

            var carregaInscricoes = function () {
                $scope.inscricoes = $scope.campeonatoSelecionado.inscricoes;
            };

            var carregaLocais = function () {

            };

            $scope.selecionaAgremiacaoA = function (inscricao) {
                var agremiacao = inscricao.agremiacao;
                var index = $scope.inscricoes.valueOf(inscricao);
                $scope.inscricoes.splice(index, 1);
                $scope.agremiacaoA = agremiacao;
            };

            $scope.selecionaAgremiacaoB = function (inscricao) {
                var agremiacao = inscricao.agremiacao;
                var index = $scope.inscricoes.valueOf(inscricao);
                $scope.inscricoes.splice(index, 1);
                $scope.agremiacaoB = agremiacao;
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
                        //$scope.formularioTabelaJogo.$setPristine();
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
                        //$scope.formularioTabelaJogo.$setPristine();
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
                        //$scope.formularioTabelaJogo.$setPristine();
                        Materialize.toast('TabelaJogo deletado com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.abaSelecionada = function (abaSelecionada) {
                $scope.abaSelecionada = abaSelecionada;
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