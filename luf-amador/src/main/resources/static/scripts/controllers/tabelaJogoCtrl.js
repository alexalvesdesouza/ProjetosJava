var tabelaJogo = angular.module("tabelaJogo", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
tabelaJogo.controller("tabelaJogoController",
        function ($scope,
            $filter,
            $http) {

            const BASE_PATH = "/tabelaJogos";
            const BASE_PATH_CAMPEONATOS = "/campeonatos";

            $scope.tabelaJogos = [];
            $scope.campeonatos = [];
            $scope.tabelaJogo = {};
            $scope.modoEdicao = false;
            $scope.classEdit = false;
            $scope.isEdit = false;

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
                $http
                    .get(BASE_PATH_CAMPEONATOS)
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