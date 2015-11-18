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
	
	public void processar(){
		if	(!dsHorario.equalsIgnoreCase("")){
			String dsHorarioAux = dsHorario.substring(dsHorario.indexOf("Horário:")+8);
			
			if	((dsHorarioAux.indexOf(":")) == 0){
				dsHorarioAux = dsHorarioAux.substring(0,1)+":"+dsHorarioAux.substring(2, 3);
			}
			
			dsHorario = dsHorarioAux;
		}
		
		if	(!dsFato.equalsIgnoreCase("")){
			String dsBairroAux = dsBairro.substring(dsBairro.indexOf("Bairro:")+7);
			dsBairro = dsBairroAux; 
		}
		
	}
	

}
