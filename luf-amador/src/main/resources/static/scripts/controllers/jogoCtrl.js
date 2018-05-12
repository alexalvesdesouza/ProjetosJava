var jogo = angular.module("jogo", ["ngMessages", "ngResource", "ngMask", "app.directive.format"]);
jogo.controller("jogoController",
                function ($scope,
                          $filter,
                          $http) {

                    const BASE_PATH = "/jogos";

                    $scope.tabelaJogosTempoReal = [];
                    $scope.tabelaCampeonato = {};
                    $scope.jogoSelecionado = {};

                    var carregarJogosTempoReal = function () {
                        $http
                            .get(BASE_PATH + '/tempo-real')
                            .success(function (ret) {
                                $scope.tabelaJogosTempoReal = [];
                                $scope.tabelaJogosTempoReal = ret;
                            })
                            .error(function (data, status) {

                            });
                    };

                    var atualizarResultadoParcialJogo = function () {
                        var jogo = $scope.jogoSelecionado;

                        $http.put(BASE_PATH + '/tempo-real/atualizar', jogo)
                            .success(function (jogoAtualizado) {
                                delete $scope.jogoSelecionado;
                                carregarJogosTempoReal();
                                limparForm('formTempoReal');
                                Materialize.toast('Resultado jogo atualizado sucesso', 4000, 'rounded');
                            })
                            .error(function (data, status) {
                                Materialize.toast(data.message, 4000, 'rounded');
                            })
                    };

                    var encerrarJogoRodada = function () {
                        var jogo = $scope.jogoSelecionado;

                        $http.put(BASE_PATH + '/tempo-real/atualizar', jogo)
                            .success(function (jogoAtualizado) {
                                delete $scope.jogoSelecionado;
                                carregarJogosTempoReal();
                                limparForm('formTempoReal');
                                Materialize.toast('Resultado jogo atualizado sucesso', 4000, 'rounded');
                            })
                            .error(function (data, status) {
                                Materialize.toast(data.message, 4000, 'rounded');
                            })
                    };

                    $scope.editarJogo = function (jogo) {
                        $scope.jogoSelecionado = {};
                        $scope.jogoSelecionado = jogo;
                    };

                    $scope.mudarPlacarJogo = function (jogo) {
                        $scope.jogoSelecionado = jogo;
                        atualizarResultadoParcialJogo();
                    };

                    carregarJogosTempoReal();

                });
