var agremiacao = angular.module("agremiacao", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
agremiacao.controller("agremiacaoController",
        function ($scope,
            $filter,
            $http,
            myResource) {

            const BASE_PATH = "/agremiacoes";

            $scope.agremiacoes = [];
            $scope.agremiacao = {};
            $scope.endereco = {};
            $scope.modoEdicao = false;
            $scope.classEdit = false;
            $scope.isEdit = false;

            $scope.salvarAgremiacao = function (agremiacao) {
                agremiacao.endereco = $scope.endereco;
                $http.post(BASE_PATH, agremiacao)
                    .success(function (agremiacaoCadastrada) {
                        limparFormularioAgremiacao();
                        carregarAgremiacoes();
                        //$scope.formularioAgremiacao.$setPristine();
                        Materialize.toast('Agremiacao cadastrada com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.updateAgremiacao = function (agremiacao) {

                $http.put(BASE_PATH, agremiacao)
                    .success(function (agremiacaoAtualizada) {
                        limparFormularioAgremiacao();
                        $scope.modoEdicao = false;
                        carregarAgremiacoes();
                        //$scope.formularioAgremiacao.$setPristine();
                        Materialize.toast('Agremiacao atualizada com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            var limparFormularioAgremiacao = function () {
                $scope.agremiacao = {};
                $scope.endereco = {};
            };

            var carregarAgremiacoes = function () {
                $http
                    .get(BASE_PATH)
                    .success(function (ret) {
                        $scope.agremiacoes = [];
                        $scope.agremiacoes = ret;
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.editarAgremiacao = function (agremiacao) {
                $scope.classEdit = true;
                $scope.modoEdicao = true;
                $scope.agremiacao = agremiacao;
                $scope.endereco = agremiacao.endereco;
            };

            $scope.deletarAgremiacao = function (agremiacao) {
                const codigo = agremiacao.codigo;
                $http.delete(BASE_PATH + "/" + codigo + "/")
                    .success(function (agremiacaoDeletada) {

                        carregarAgremiacoes();
                        //$scope.formularioAgremiacao.$setPristine();
                        Materialize.toast('Agremiação deletada com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.abaSelecionada = function (abaSelecionada) {
               $scope.abaSelecionada = abaSelecionada;
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

                $scope.agremiacaoForm.logradouro.$touched = true;
                $scope.agremiacaoForm.logradouro.$untouched = false;
                $scope.agremiacaoForm.bairro.$touched = true;
                $scope.agremiacaoForm.bairro.$untouched = false;
                $scope.agremiacaoForm.cep.$touched = true;
                $scope.agremiacaoForm.cep.$untouched = false;
                $scope.agremiacaoForm.cidade.$touched = true;
                $scope.agremiacaoForm.cidade.$untouched = false;
                $scope.agremiacaoForm.estado.$touched = true;
                $scope.agremiacaoForm.estado.$untouched = false;

                $scope.classCep = true;
                $scope.classLogradouro = true;
                $scope.classComplemento = true;
                $scope.classNumero = true;
                $scope.classBairro = true;
                $scope.classCidade = true;
                $scope.classEstado = true;
            };

            $scope.buscarCep = function (endereco) {
                var cep = $scope.endereco.cep.replace("-", "");
                carregarEndereco(cep);
            };

            carregarAgremiacoes();

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