var tjdu = angular.module("tjdu", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
tjdu.controller("tjduController",
                  function ($scope,
                            $filter,
                            $http) {

                      const BASE_PATH = "/tjdus";
                      
                      $scope.tjdus = [];
                      $scope.tjduSelecionado = {};
                      $scope.tjdu = {
                          categoria: "editais"
                      };
                      $scope.modoEdicao = false;
                      $scope.categoria = "editais";
                      $scope.classEdit = false;
                      $scope.classEstaticField = true;
                      $scope.isEdit = false;

                      $scope.salvarTjdu = function (tjdu) {
                          tjdu.categoria = $scope.categoria;
                          $http.post(BASE_PATH, tjdu)
                              .success(function (tjduCadastrada) {
                                  limparFormularioTjdu();
                                  carregarTjdus();
                                  //$scope.formularioTjdu.$setPristine();
                                  Materialize.toast($scope.categoria + ' do TDJU cadastrado(a) com sucesso.', 4000, 'rounded');
                              })
                              .error(function (data, status) {
                                  Materialize.toast(data.message, 4000, 'rounded');
                              });
                      };

                      $scope.updateTjdu = function (tjdu) {
                          $http.put(BASE_PATH, tjdu)
                              .success(function (tjduCadastrada) {
                                  limparFormularioTjdu();
                                  $scope.modoEdicao = false;
                                  carregarTjdus();
                                  //$scope.formularioTjdu.$setPristine();
                                  Materialize.toast($scope.categoria + ' do TDJU Atualizada com sucesso.', 4000, 'rounded');
                              })
                              .error(function (data, status) {
                                  Materialize.toast(data.message, 4000, 'rounded');
                              });
                      };

                      var limparFormularioTjdu = function () {
                          $scope.tjdu = {
                              categoria: "editais",
                              link: "",
                              numero: "",
                              codigo: null
                          };
                      };

                      var carregarTjdus = function () {
                          $http
                              .get(BASE_PATH + '/' + $scope.categoria)
                              .success(function (ret) {
                                  $scope.tjdus = [];
                                  $scope.tjdus = ret;
                              })
                              .error(function (data, status) {

                              });
                      };

                      $scope.editarTjdu = function (tjdu) {
                          $scope.classEdit = true;
                          $scope.modoEdicao = true;
                          $scope.tjdu = tjdu;
                      };

                      $scope.deletarTjdu = function (tjdu) {
                          const codigo = tjdu.codigo;
                          $http.delete(BASE_PATH + "/" + codigo + "/")
                              .success(function (tjduDeletada) {

                                  carregarTjdus();
                                  //$scope.formularioTjdu.$setPristine();
                                  Materialize.toast($scope.categoria +  ' do TJDU deletada com sucesso.', 4000, 'rounded');
                              })
                              .error(function (data, status) {
                                  // Handle HTTP error
                                  console.log(data);
                              });
                      };

                      $scope.abaSelecionada = function (abaSelecionada) {
                          $scope.categoria = abaSelecionada;
                          $scope.tjdu.categoria = $scope.categoria;
                          limparFormularioTjdu();
                          carregarTjdus();
                      };

                      carregarTjdus();

                  });
