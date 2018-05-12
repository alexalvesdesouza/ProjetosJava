$(document).ready(
	function () {

		$(".dropdown-button").dropdown();
		$('select').material_select();
		$('.modal').modal();
		$('input#input_text, textarea#textarea1').characterCounter();
		$('.collapsible').collapsible();

		$('input.autocomplete').autocomplete({
			data: {},
			limit: 20, // The max amount of results that can be shown
			// at once. Default: Infinity.
			onAutocomplete: function (val) {
				// Callback function when value is autcompleted.
			},
			minLength: 1, // The minimum length of the input for the
			// autocomplete to start. Default: 1.
		});

		$('.timepicker').pickatime({
			default: 'now', // Set default time: 'now', '1:30AM', '16:30'
			fromnow: 0, // set default time to * milliseconds from now (using with default = 'now')
			twelvehour: false, // Use AM/PM or 24-hour format
			donetext: 'OK', // text for done-button
			cleartext: 'Limpar', // text for clear-button
			canceltext: 'Cancelar', // Text for cancel-button
			autoclose: true, // automatic close timepicker
			ampmclickable: true, // make AM PM clickable
			aftershow: function () {} //Function for after opening timepicker
		});

		$('.datepicker')
			.pickadate({
				selectYears: 100,
				format: 'dd/mm/yyyy',
				labelMonthNext: 'Próximo mês',
				labelMonthPrev: 'Mês anterior',
				labelMonthSelect: 'Selecione o mês',
				labelYearSelect: 'Selecione o ano',
				monthsFull: ['Janeiro', 'Fevereiro',
					'Março', 'Abril', 'Maio',
					'Junho', 'Julho', 'Agosto',
					'Setembro', 'Outubro',
					'Novembro', 'Dezembro'
				],
				monthsShort: ['Jan', 'Fev', 'Mar',
					'Abr', 'Mai', 'Jun', 'Jul',
					'Ago', 'Set', 'Out', 'Nov',
					'Dez'
				],
				weekdaysFull: ['Domingo', 'Segunda',
					'Terça', 'Quarta', 'Quinta',
					'Sexta', 'Sabado'
				],
				weekdaysShort: ['Dom', 'Seg', 'Ter',
					'Qua', 'Qui', 'Sex', 'Sab'
				],
				weekdaysLetter: ['D', 'S', 'T', 'Q',
					'Q', 'S', 'S'
				],
				today: 'Hoje',
				clear: 'Limpar',
				closeOnSelect: true,
				close: 'Fechar'
			});

	});