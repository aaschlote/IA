package br.com.furb.trabalhoFinal;

import java.sql.Date;

public class OcorrenciasPoliciais {
	
	private String dsFato;
	private String dsBairro;
	private String dsDtOcorrencia;
	private String dsHorario;
	
	public OcorrenciasPoliciais(String dsFato, String dsBairro,String dsHorario, String dsDtOcorrencia) {
		this.dsFato = dsFato;
		this.dsBairro = dsBairro;
		this.dsHorario 		= dsHorario;
		this.dsDtOcorrencia = dsDtOcorrencia;
	}

	public String getDsFato() {
		return dsFato;
	}

	public String getDsBairro() {
		return dsBairro;
	}

	public String dsDtOcorrencia() {
		return dsDtOcorrencia;
	}

	public String getDsHorario() {
		return dsHorario;
	}
	
	

}
