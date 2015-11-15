package br.com.furb.trabalhoFinal;

import java.sql.Date;

public class OcorrenciasPoliciais {
	
	private String dsOcorrencia;
	private String dsBairro;
	private Date dtOcorrencia;
	private String dsOcorrenciaInteira;
	private String dsHorario;
	
	public OcorrenciasPoliciais(String dsOcorrencia, String dsBairro,String dsOcorrenciaInteira,String dsHorario) {
		this.dsOcorrencia = dsOcorrencia;
		this.dsBairro = dsBairro;
		this.dsOcorrenciaInteira = dsOcorrenciaInteira;
		this.dsHorario 		= dsHorario;
	}

	public String getDsOcorrencia() {
		return dsOcorrencia;
	}

	public String getDsBairro() {
		return dsBairro;
	}

	public Date getDtOcorrencia() {
		return dtOcorrencia;
	}

	public String getDsOcorrenciaInteira() {
		return dsOcorrenciaInteira;
	}

	public String getDsHorario() {
		return dsHorario;
	}
	
	

}
