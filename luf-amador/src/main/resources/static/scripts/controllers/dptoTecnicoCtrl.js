var departamentoTecnico = angular.module("departamentoTecnico", ["ngMessages", "ngResource"]);
departamentoTecnico
    .controller("departamentoTecnicoController",
        function ($scope,
            $filter,
            $http) {

            const BASE_PATH = "/departamento-tecnico";

            $scope.departamentoTecnicos = [];
            $scope.departamentoTecnicoSelecionado = {};
            $scope.departamentoTecnico = {
                categoria: "editais"
            };
            $scope.departamentoTecnicoDeletar = {
                categoria: "editais"
            };
            $scope.modoEdicao = false;
            $scope.categoria = "editais";
            $scope.classEdit = false;
            $scope.classEstaticField = true;
            $scope.isEdit = false;

            $scope.salvarDepartamentoTecnico = function (departamentoTecnico) {
                departamentoTecnico.categoria = $scope.categoria;
                $http.post(BASE_PATH, departamentoTecnico)
                    .success(function (departamentoTecnicoCadastrada) {
                        limparFormularioDepartamentoTecnico();
                        carregarDepartamentoTecnicos();
                        //$scope.formularioDepartamentoTecnico.$setPristine();
                        Materialize.toast($scope.categoria +
                            ' do Departamento Técnico cadastrado(a) com sucesso.',
                            4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.updateDepartamentoTecnico = function (departamentoTecnico) {
                $http.put(BASE_PATH, departamentoTecnico)
                    .success(function (departamentoTecnicoCadastrada) {
                        limparFormularioDepartamentoTecnico();
                        $scope.modoEdicao = false;
                        carregarDepartamentoTecnicos();
                        //$scope.formularioDepartamentoTecnico.$setPristine();
                        Materialize.toast($scope.categoria +
                            ' do Departamento Técnico Atualizada com sucesso.',
                            4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            var limparFormularioDepartamentoTecnico = function () {
                $scope.departamentoTecnico = {
                    numero: "",
                    categoria: "",
                    link: "",
                    descricao: "",
                    codigo: null
                };
            };

            var carregarDepartamentoTecnicos = function () {
                $http
                    .get(BASE_PATH + '/' + $scope.categoria)
                    .success(function (ret) {
                        $scope.departamentoTecnicos = [];
                        $scope.departamentoTecnicos = ret;
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.editarDepartamentoTecnico = function (departamentoTecnico) {
                $scope.classEdit = true;
                $scope.modoEdicao = true;
                $scope.departamentoTecnico = departamentoTecnico;
            };

            $scope.selecionaEntidadeDeletar = function (entidadeSeleconad) {
                $scope.departamentoTecnicoDeletar = entidadeSeleconad
            };

            $scope.deletarDepartamentoTecnico = function (departamentoTecnico) {
                const codigo = departamentoTecnico.codigo;
                $http.delete(BASE_PATH + "/" + codigo + "/")
                    .success(function (departamentoTecnicoDeletada) {

                        carregarDepartamentoTecnicos();
                        //$scope.formularioDepartamentoTecnico.$setPristine();
                        Materialize.toast($scope.categoria +
                            ' do Departamento Técnico deletada com sucesso.',
                            4000, 'rounded');
                    })
                    .error(function (data, status) {
                        Materialize.toast(data.message, 4000, 'rounded');
                    });
            };

            $scope.abaSelecionada = function (abaSelecionada) {
                $scope.modoEdicao = false;
                $scope.categoria = abaSelecionada;
                $scope.departamentoTecnico.categoria = $scope.categoria;
                limparFormularioDepartamentoTecnico();
                carregarDepartamentoTecnicos();
            };

            carregarDepartamentoTecnicos();

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