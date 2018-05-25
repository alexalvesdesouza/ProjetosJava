var campeonato = angular.module("campeonato", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
campeonato.controller("campeonatoController",
                      function ($scope,
                                $filter,
                                $http) {

                          const BASE_PATH = "/campeonatos";
                          const BASE_PATH_AGREMIACOES = "/agremiacoes";
                          const BASE_PATH_INSCRICOES = "/inscricoes";

                          $scope.campeonatos = [];
                          $scope.agremiacoesIncritas = [];
                          $scope.agremiacoesDisponiveis = [];
                          $scope.campeonato = {
                              inscricoes: []
                          };
                          $scope.campeonatoSelecionado = {};
                          $scope.modoEdicao = false;
                          $scope.classEdit = false;
                          $scope.isEdit = false;
                          $scope.hideListCampeonatos = false;
                          $scope.campeonatoSelected = false;
                          $scope.filterCampeonato = [];
                          $scope.agremiacoesSelecionadasInscrever = [];
                          $scope.inTransfer = false;

                          $scope.selecionarCampeonato = function (item) {
                              $scope.filterCampeonato = [];
                              $scope.hideListCampeonatos = true;
                              $scope.nomeCampeonatoSelectionado = item.nomeCampeonato;
                              $scope.campeonatoSelecionado = item;
                              $scope.campeonato = item;
                              buscarCampeonato();
                              carregarAgremiacoesDisponiveis();
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

                          var buscarCampeonato = function () {
                              const codigo = $scope.campeonato.codigo;
                              $http.get(BASE_PATH + "/" + codigo +  "/busca")
                                  .success(function (ret) {
                                     $scope.campeonato = ret;
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

                          var carregarAgremiacoesDisponiveis = function () {
                              const codigoCampeonato = $scope.campeonato.codigo;
                              $http
                                  .get(BASE_PATH_AGREMIACOES + "/" + codigoCampeonato + "/disponiveis")
                                  .success(function (ret) {
                                      $scope.agremiacoes = [];
                                      $scope.agremiacoes = ret;
                                  })
                                  .error(function (data, status) {
                                      Materialize.toast(data.message, 4000, 'rounded');
                                  });
                          };

                          $scope.efetivarInscricaoCampeonado = function () {

                              var campeonato = $scope.campeonato;

                              $http.put(BASE_PATH + "/agremiacoes-inscrever", campeonato)
                                  .success(function (retorno) {

                                      Materialize.toast('Inscricao efetuada com sucesso.', 4000, 'rounded');
                                  })
                                  .error(function (data, status) {
                                      Materialize.toast(data.message, 4000, 'rounded');
                                  });
                          };

                          $scope.selecionaAgremiacao = function (agremiacao) {

                             var index =  $scope.campeonato.inscricoes.indexOf(agremiacao);
                             //Fazendo inscrição
                             if (index === -1) {
                                 var inscricao = {
                                     agremiacao: agremiacao
                                 }
                                 $scope.campeonato.inscricoes.push(inscricao);
                                 var idx = $scope.agremiacoes.indexOf(agremiacao);
                                 $scope.agremiacoes.splice(idx, 1);
                                 return;
                             }

                             //Desincrevendo
                              $scope.agremiacoes.push(agremiacao.agremiacao);
                              $scope.campeonato.inscricoes.splice(index, 1);

                          };

                          $scope.selecionaAgremiacaoDesinscrever = function (agremiacao) {

                              var index = $scope.agremiacoesIncritas.indexOf(agremiacao);
                              $scope.agremiacoesIncritas.splice(index, 1);
                              $scope.agremiacoes.push((agremiacao));
                          };

                          carregarCampeonatos();
                          //carregarAgremiacoes();

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
