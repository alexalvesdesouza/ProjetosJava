var atleta = angular.module("atleta", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
atleta.controller("atletaController",
    function ($scope,
        $filter,
        $http) {

        const BASE_PATH = "/atletas";

        $scope.atletas = [];
        $scope.atletaSelecionado = {};
        $scope.atleta = { };
        $scope.modoEdicao = false;
        $scope.classEdit = false;
        $scope.classEstaticField = true;
        $scope.isEdit = false;

        $scope.salvarAtleta = function (atleta) {
            $http.post(BASE_PATH, atleta)
                .success(function (atletaCadastrado) {
                    limparFormularioAtleta();
                    carregarAtletas();
                    //$scope.formularioAtleta.$setPristine();
                    Materialize.toast('Atleta cadastrado com sucesso.', 4000, 'rounded');
                })
                .error(function (data, status) {
                    Materialize.toast(data.message, 4000, 'rounded');
                });
        };

        $scope.updateAtleta = function (atleta) {
            $http.put(BASE_PATH, atleta)
                .success(function (atletaAtualizado) {
                    limparFormularioAtleta();
                    $scope.modoEdicao = false;
                    carregarAtletas();
                    //$scope.formularioAtleta.$setPristine();
                    Materialize.toast('Atleta atualizado com sucesso.', 4000, 'rounded');
                })
                .error(function (data, status) {
                    Materialize.toast(data.message, 4000, 'rounded');
                });
        };

        var limparFormularioAtleta = function () {
            $scope.atleta = {};
        };

        var carregarAtletas = function () {
            $http
                .get(BASE_PATH)
                .success(function (ret) {
                    $scope.atletas = [];
                    $scope.atletas = ret;
                })
                .error(function (data, status) {
                    Materialize.toast(data.message, 4000, 'rounded');
                });
        };

        $scope.editarAtleta = function (atleta) {
            $scope.classEdit = true;
            $scope.modoEdicao = true;
            $scope.atleta = atleta;
        };

        $scope.deletarAtleta = function (atleta) {
            const codigo = atleta.codigo;
            $http.delete(BASE_PATH + "/" + codigo + "/")
                .success(function (atletaDeletada) {

                    carregarAtletas();
                    //$scope.formularioAtleta.$setPristine();
                    Materialize.toast('Atleta deletada com sucesso.', 4000, 'rounded');
                })
                .error(function (data, status) {
                    Materialize.toast(data.message, 4000, 'rounded');
                });
        };

        $scope.abaSelecionada = function (abaSelecionada) {
            limparFormularioAtleta();
            carregarAtletas();
        };

        carregarAtletas();

    });