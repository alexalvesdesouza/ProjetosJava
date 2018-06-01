var atleta = angular.module("atleta", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
atleta.controller("atletaController",
        function ($scope,
            $filter,
            $http,
            myResource) {

            const BASE_PATH = "/atletas";
            const BASE_PATH_AGREMIACOES = "/agremiacoes";

            $scope.atletas = [];
            $scope.agremiacoes = [];
            $scope.atleta = {};
            $scope.endereco = {};
            $scope.modoEdicao = false;
            $scope.classEdit = false;
            $scope.isEdit = false;
            $scope.hideListAgremiacoes = false;
            $scope.filterAgremiacao = [];
            $scope.atletaDeletar = {};

            $scope.selecionaAtletaDeletar = function (atleta) {
                $scope.atletaDeletar = atleta;
            };

            $scope.selecionarAgremiacao = function (item) {
                $scope.filterAgremiacao = [];
                $scope.hideListAgremiacoes = true;
                $scope.agremiacao = item;
                $scope.agremiacaoSelecionada = item.nome;
            };

            $scope.completeAgremiacao = function (param) {
                $scope.hideListAgremiacoes = false;
                var output = [];
                angular.forEach($scope.agremiacoes, function (agremiacaoMap) {
                    if (agremiacaoMap.nome === null) {
                        return;
                    }
                    if (agremiacaoMap.nome.toLowerCase().indexOf(param.toLowerCase()) >= 0) {
                        output.push(agremiacaoMap);
                    }
                });
                $scope.filterAgremiacao = output;
            };

            $scope.salvarAtleta = function (atleta) {
                atleta.endereco = $scope.endereco;
                atleta.agremiacao = $scope.agremiacao;
                $http.post(BASE_PATH, atleta)
                    .success(function (atletaCadastrada) {
                        limparFormularioAtleta();
                        carregarAtletas();
                        //$scope.formularioAtleta.$setPristine();
                        Materialize.toast('Atleta cadastrada com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.updateAtleta = function (atleta) {
                if ($scope.endereco) {
                    atleta.endereco = $scope.endereco;
                }
                if ($scope.agremiacao) {
                    atleta.agremiacao = $scope.agremiacao;
                }
                $http.put(BASE_PATH, atleta)
                    .success(function (atletaAtualizada) {
                        limparFormularioAtleta();
                        $scope.modoEdicao = false;
                        carregarAtletas();
                        //$scope.formularioAtleta.$setPristine();
                        Materialize.toast('Atleta atualizada com sucesso.', 4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            var limparFormularioAtleta = function () {
                $scope.atleta = {};
                $scope.endereco = {};
                $scope.agremiacaoSelecionada = '';
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

            var carregarAgremiacoes = function () {
                $http
                    .get(BASE_PATH_AGREMIACOES)
                    .success(function (ret) {
                        $scope.agremiacoes = [];
                        $scope.agremiacoes = ret;
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.editarAtleta = function (atleta) {
                $scope.classEdit = true;
                $scope.modoEdicao = true;
                $scope.atleta = atleta;
                $scope.endereco = atleta.endereco;
                $scope.agremiacaoSelecionada = atleta.agremiacao.nome;
            };

            $scope.deletarAtleta = function (atleta) {
                const codigo = atleta.codigo;
                $http.delete(BASE_PATH + "/" + codigo + "/")
                    .success(function (atletaDeletada) {

                        carregarAtletas();
                        limparFormularioAtleta();
                        //$scope.formularioAtleta.$setPristine();
                        Materialize.toast('Atleta deletada com sucesso.', 4000, 'rounded');
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

                $scope.atletaForm.logradouro.$touched = true;
                $scope.atletaForm.logradouro.$untouched = false;
                $scope.atletaForm.bairro.$touched = true;
                $scope.atletaForm.bairro.$untouched = false;
                $scope.atletaForm.cep.$touched = true;
                $scope.atletaForm.cep.$untouched = false;
                $scope.atletaForm.cidade.$touched = true;
                $scope.atletaForm.cidade.$untouched = false;
                $scope.atletaForm.estado.$touched = true;
                $scope.atletaForm.estado.$untouched = false;

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

            carregarAtletas();
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
    }).directive('capitalize', function () {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, modelCtrl) {
                var capitalize = function (inputValue) {
                    if (inputValue == undefined) inputValue = '';
                    var capitalized = inputValue.toUpperCase();
                    if (capitalized !== inputValue) {
                        // see where the cursor is before the update so that we can set it back
                        var selection = element[0].selectionStart;
                        modelCtrl.$setViewValue(capitalized);
                        modelCtrl.$render();
                        // set back the cursor after rendering
                        element[0].selectionStart = selection;
                        element[0].selectionEnd = selection;
                    }
                    return capitalized;
                }
                modelCtrl.$parsers.push(capitalize);
                capitalize(scope[attrs.ngModel]); // capitalize initial value
            }
        };
    });