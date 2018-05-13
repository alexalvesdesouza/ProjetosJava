var campeonato = angular.module("campeonato", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
campeonato.controller("campeonatoController",
        function ($scope,
            $filter,
            $http) {

            const BASE_PATH = "/campeonatos";

            $scope.campeonatos = [];
            $scope.campeonato = {};
            $scope.modoEdicao = false;
            $scope.classEdit = false;
            $scope.isEdit = false;

            $scope.salvarCampeonato = function (campeonato) {
                $http.post(BASE_PATH, campeonato)
                    .success(function (campeonatoCadastrado) {
                        limparFormularioCampeonato();
                        carregarCampeonatos();
                        //$scope.formularioCampeonato.$setPristine();
                        Materialize.toast('Campeonato cadastrado com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.atualizarCampeonato = function (campeonato) {
                $http.put(BASE_PATH, campeonato)
                    .success(function (campeonatoAtualizado) {
                        limparFormularioCampeonato();
                        $scope.modoEdicao = false;
                        carregarCampeonatos();
                        //$scope.formularioCampeonato.$setPristine();
                        Materialize.toast('Campeonato atualizado com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            var limparFormularioCampeonato = function () {
                $scope.campeonato = {};
            };

            var carregarCampeonatos = function () {
                $http
                    .get(BASE_PATH)
                    .success(function (ret) {
                        $scope.campeonatos = [];
                        $scope.campeonatos = ret;
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.editarCampeonato = function (campeonato) {
                $scope.classEdit = true;
                $scope.modoEdicao = true;
                $scope.campeonato = campeonato;
            };

            $scope.deletarCampeonato = function (campeonato) {
                const codigo = campeonato.codigo;
                $http.delete(BASE_PATH + "/" + codigo + "/")
                    .success(function (campeonatoDeletado) {

                        carregarCampeonatos();
                        limparFormularioCampeonato();
                        //$scope.formularioCampeonato.$setPristine();
                        Materialize.toast('Campeonato deletado com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.abaSelecionada = function (abaSelecionada) {
                $scope.abaSelecionada = abaSelecionada;
            };

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