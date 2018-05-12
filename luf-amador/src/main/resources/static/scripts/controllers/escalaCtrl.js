var escala = angular.module("escala", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
escala.controller("escalaController",
                  function ($scope,
                            $filter,
                            $http) {

                      const BASE_PATH = "/escalas";

                      $scope.escalas = [];
                      $scope.escalaSelecionado = {};
                      $scope.escalaArbitros = {};
                      $scope.salvarEscala = {};
                      $scope.modoEdicao = false;

                      $scope.salvarEscala = function (escalaArbitros) {
                          $http.post(BASE_PATH, escalaArbitros)
                              .success(function (escalaCadastrada) {
                                  delete $scope.escalaArbitros;

                                  carregarEscalas();
                                  $scope.formularioEscala.$setPristine();
                                  Materialize.toast('Escala dadastrada com sucesso.', 4000, 'rounded');
                              })
                              .error(function (data, status) {
                                  // Handle HTTP error
                                  console.log(data);
                              });
                      };

                      $scope.updateEscala = function (escalaArbitros) {
                          $http.put(BASE_PATH, escalaArbitros)
                              .success(function (escalaCadastrada) {
                                  delete $scope.escalaArbitros;
                                  $scope.modoEdicao = false;
                                  carregarEscalas();
                                  $scope.formularioEscala.$setPristine();
                                  Materialize.toast('Escala Atualizada com sucesso.', 4000, 'rounded');
                              })
                              .error(function (data, status) {
                                  // Handle HTTP error
                                  console.log(data);
                              });
                      };

                      var carregarEscalas = function () {
                          $http
                              .get(BASE_PATH)
                              .success(function (ret) {
                                  $scope.escalas = [];
                                  $scope.escalas = ret;
                              })
                              .error(function (data, status) {

                              });
                      };

                      $scope.editarEscala = function (escala) {
                          $scope.modoEdicao = true;
                          $scope.escalaArbitros = escala;
                      };

                      $scope.deletarEscala = function (escala) {
                          const codigo = escala.codigo;
                          $http.delete(BASE_PATH + "/" + codigo + "/")
                              .success(function (escalaDeletada) {

                                  carregarEscalas();
                                  $scope.formularioEscala.$setPristine();
                                  Materialize.toast('Escala deletada com sucesso.', 4000, 'rounded');
                              })
                              .error(function (data, status) {
                                  // Handle HTTP error
                                  console.log(data);
                              });
                      };

                      carregarEscalas();

                  });
