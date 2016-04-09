var tam = 20;

function normalTamanho() {
	tam = 20;
	cssFontSizePrimefaces(tam);
}

function aumentaTamanho() {
	tam += 1;
	cssFontSizePrimefaces(tam);
}

function diminuiTamanho() {
	tam -= 1;
	cssFontSizePrimefaces(tam);
}

function cssFontSizePrimefaces(tamanho) {
	var seletor = '.ui-widget';
	var css = 'font-size:' + tamanho + 'px !important;';
	S = document.styleSheets[document.styleSheets.length - 1];
	var r = (S.cssRules != undefined) ? S.cssRules : S.rules;
	if (S.insertRule)
		S.insertRule(seletor + '{' + css + '}', r.length);
	else if (S.addRule)
		S.addRule(seletor, css, r.length);
}