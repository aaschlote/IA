package br.com.furb.trabalhoFinal;

import java.util.Collection;

public class OcorrenciasPoliciais {
	
	private String dsFato;
	private String dsBairro;
	private String dsDtOcorrencia;
	private String dsHorario;
	private Collection<Word> words;
	private WordTokenizer tokenizer = new WordTokenizer();
	private String[] acao = {"nao-identifi","nao-identifi","nao-identifi"};
	private PeriodoOcorrencia periodo;
	
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
	
	public void processar() throws Exception{
		if	(!dsHorario.equalsIgnoreCase("")){
			String dsHorarioAux = dsHorario.substring(dsHorario.indexOf("Horário:")+8);
			
			if	((dsHorarioAux.indexOf(":")) == 0){
				dsHorarioAux = dsHorarioAux.substring(0,1)+":"+dsHorarioAux.substring(2, 3);
			}
			
			dsHorario = dsHorarioAux.replace(" ", "");
			
			if	(dsHorario.length() > 2) {
				
				int qtHora = -1;
				
				try {
					qtHora = Integer.valueOf(dsHorario.substring(0,1));
				} catch (Exception e) {
					try {
						qtHora = Integer.valueOf(dsHorario.substring(1,2));
					} catch (Exception e2) {
						qtHora = -1;
					}
				}
				
				
				if	((qtHora >= 0) &&
					(qtHora <= 5)) {
					periodo = PeriodoOcorrencia.DAWN;
				}else if((qtHora >= 6) &&
					(qtHora <= 12)) {
					periodo = PeriodoOcorrencia.MORNING;
				}else if ((qtHora >= 12) &&
					(qtHora <= 18)) {
					periodo = PeriodoOcorrencia.AFTERNOON;
				}else if ((qtHora >= 12) &&
						(qtHora <= 18)) {
					periodo = PeriodoOcorrencia.NIGHT;
				}
			}
			
			
		}
		
		if	(!dsBairro.equalsIgnoreCase("")){
			String dsBairroAux = dsBairro.substring(dsBairro.indexOf("Bairro:")+7);
			dsBairroAux = dsBairroAux.replace(",", "");
			dsBairroAux = dsBairroAux.replace(".", "");
			if	(dsBairroAux.length() > 18){
				dsBairroAux = dsBairroAux.substring(0,18); 
			}
			
			dsBairro = dsBairroAux;
		}
		
		
		setWords(tokenizer.getWords(dsFato));
		int qtPassagens = 0;
		
		for (Word word : words) {
			if	(qtPassagens == 0){
				if	(obterAcaoPolicial(word.toString())){
					acao[qtPassagens] = word.toString();
					qtPassagens++;
				}
				continue;
			}
			
			if	(qtPassagens == 1){
				if	(obterCrimePolicial(word.toString())){
					acao[qtPassagens] = word.toString();
					qtPassagens++;
				}
				continue;
			}
			
			if	(qtPassagens == 2){
				if	(obterObjetoPolicial(word.toString())){
					acao[qtPassagens] = word.toString();
					qtPassagens++;
				}
				continue;
			}
			
			if	(qtPassagens == 3){
				break;
			}
			
		}
		
	}

	public Collection<Word> getWords() {
		return words;
	}

	public void setWords(Collection<Word> words) {
		this.words = words;
	}
	
	public boolean obterAcaoPolicial(String acao){
		return acao.equalsIgnoreCase("foi") ||
				acao.equalsIgnoreCase("auxili") ||
				acao.equalsIgnoreCase("atend") ||
				acao.equalsIgnoreCase("prend") ||
				acao.equalsIgnoreCase("apreend") ||
				acao.equalsIgnoreCase("efetu") ||
				acao.equalsIgnoreCase("registr") ||
				acao.equalsIgnoreCase("abord");
	}
	
	public boolean obterCrimePolicial(String acao){
		return acao.equalsIgnoreCase("furt") ||
				acao.equalsIgnoreCase("assalt") ||
				acao.equalsIgnoreCase("roub") ||
				acao.equalsIgnoreCase("perturb") ||
				acao.equalsIgnoreCase("traf") ||
				acao.equalsIgnoreCase("agred") ||
				acao.equalsIgnoreCase("pris") ||
				acao.equalsIgnoreCase("prisa") ||
				acao.equalsIgnoreCase("arromb") ||
				acao.equalsIgnoreCase("homicidi") ||
				acao.equalsIgnoreCase("poss");
	}
	
	public boolean obterObjetoPolicial(String acao){
		return acao.equalsIgnoreCase("motociclet") ||
		acao.equalsIgnoreCase("veicul") ||
		acao.equalsIgnoreCase("carr") ||
		acao.equalsIgnoreCase("mot") ||
		acao.equalsIgnoreCase("cas") ||
		acao.equalsIgnoreCase("caminha") ||
		acao.equalsIgnoreCase("contain") ||
		acao.equalsIgnoreCase("residenc") ||
		acao.equalsIgnoreCase("estabelec") ||
		acao.equalsIgnoreCase("consulto") ||
		acao.equalsIgnoreCase("empr") ||
		acao.equalsIgnoreCase("sala") ||
		acao.equalsIgnoreCase("pad") ||
		acao.equalsIgnoreCase("biciclet") ||
		acao.equalsIgnoreCase("imovel") ||
		acao.equalsIgnoreCase("crech") ||
		acao.equalsIgnoreCase("bar") ||
		acao.equalsIgnoreCase("loj");
	}

	public String[] getAcao() {
		return acao;
	}

	public PeriodoOcorrencia getPeriodo() {
		return periodo;
	}

	
}
