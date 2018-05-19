var campeonato = angular.module("campeonato", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
campeonato.controller("campeonatoController",
                      function ($scope,
                                $filter,
                                $http) {

                          const BASE_PATH = "/campeonatos";
                          const BASE_PATH_AGREMIACOES = "/agremiacoes";

                          $scope.campeonatos = [];
                          $scope.agremiacoesParticipantes = [];
                          $scope.campeonato = {};
                          $scope.modoEdicao = false;
                          $scope.classEdit = false;
                          $scope.isEdit = false;
                          $scope.hideListCampeonatos = false;
                          $scope.campeonatoSelected = false;
                          $scope.filterCampeonato = [];
                          $scope.agremiacoesParticipantesSelecionadas = [];

                          $scope.selecionarCampeonato = function (item) {
                              $scope.filterCampeonato = [];
                              $scope.hideListCampeonatos = true;
                              // $scope.campeonato = item;
                              $scope.campeonatoSelecionado = item.nomeCampeonato;
                              $scope.campeonatoSelected = true;
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

                          $scope.selecionaAgremiacaoInscrever = function (agremiacao) {

                              var index = $scope.agremiacoesParticipantesSelecionadas.indexOf(agremiacao);
                              if (index === -1) {
                                  $scope.agremiacoesParticipantesSelecionadas.push(agremiacao);
                                  return;
                              }
                              $scope.agremiacoesParticipantesSelecionadas.splice(index, 1);
                          };

                          $scope.selecionaAgremiacaoDesinscrever = function (agremiacao) {

                              var index = $scope.agremiacoesParticipantesSelecionadas.indexOf(agremiacao);
                              if (index === -1) {
                                  $scope.agremiacoesParticipantesSelecionadas.push(agremiacao);
                                  return;
                              }
                              $scope.agremiacoesParticipantesSelecionadas.splice(index, 1);
                          };

                          $scope.efetivarInscricaoEmMassa = function () {
                              angular.forEach($scope.agremiacoesParticipantesSelecionadas, function (agremiacao) {
                                  efetivarInscricao(agremiacao);
                              });
                          };

                          $scope.inscreverSelecionados = function () {
                              var campeonato = $scope.campeonatoSelecionado;
                              campeonato.incricoes = $scope.incricoes;
                          };

                          var efetivarInscricao = function (agremiacao) {

                              delete receber.dataCadastro;
                              $http.put("/contas-receber/"+ receber.codigo + "/receber")
                                  .success(function (receber) {
                                      loadCargaDados();
                                      Materialize.toast('Recebimento '+ receber.codigo + ' efetivado com sucesso.', 4000, 'rounded');
                                  })
                                  .error(function (data, status) {
                                      Materialize.toast(data.message, 4000, 'rounded');
                                      console.log(data);
                                  });
                              $scope.isEdit = false;
                          };

                          carregarCampeonatos();
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
    });
