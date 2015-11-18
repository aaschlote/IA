package br.com.furb.trabalhoFinal;

public enum PeriodoOcorrencia {
	
	NIGHT(1),
	MORNING(2),
	DAWN(3),
	AFTERNOON(4);
	
	private int periodo;

	private PeriodoOcorrencia(int periodo) {
		this.periodo = periodo;
	}

	public int getPeriodo() {
		return periodo;
	}
}
