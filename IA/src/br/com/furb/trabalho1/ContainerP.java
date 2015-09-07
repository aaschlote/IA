package br.com.furb.trabalho1;

public class ContainerP {

	private String dsTipo;
	private int nrPorto;
	private String dsAnterior;
	
	public ContainerP(String dsTipo, int nrPorto, String dsAnterior) {
		this.dsTipo = dsTipo;
		this.nrPorto = nrPorto;
		this.dsAnterior = dsAnterior;
	}
	
	public String getDsTipo() {
		return dsTipo;
	}
	
	public int getNrPorto() {
		return nrPorto;
	}
	
	public void setDsAnterior(String dsAnterior) {
		this.dsAnterior = dsAnterior;
	}
	
	public String getDsAnterior() {
		return dsAnterior;
	}

	
	
}
