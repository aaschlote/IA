package br.com.furb.trabalhoFinal;

public enum GravidadeOcorr {
	
	BAIXA(1),
	MEDIA(2),
	ALTA(3),
	INEXISTENTE(4);
	
	private int nivel;

	private GravidadeOcorr(int nivel) {
		this.nivel = nivel;
	}

	public int getNivel() {
		return nivel;
	}

}
