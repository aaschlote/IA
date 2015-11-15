package br.com.furb.trabalho1;

public class ContainerP {

	private String dsTipo;
	private int nrPorto;
	private String dsIdentificacao;
	
	public ContainerP(String dsTipo, int nrPorto, String dsIdentificacao) {
		this.dsTipo = dsTipo;
		this.nrPorto = nrPorto;
		this.dsIdentificacao = dsIdentificacao;
	}
	
	public String getDsTipo() {
		return dsTipo;
	}
	
	public int getNrPorto() {
		return nrPorto;
	}
	
	public void setDsAnterior(String dsIdentificacao) {
		this.dsIdentificacao = dsIdentificacao;
	}
	
	public String getDsIdentificacao() {
		return dsIdentificacao;
	}

	
	
}
