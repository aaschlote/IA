package br.com.furb.trabalhoFinal;

import java.util.Collection;

import ptstemmer.Stemmer;
import ptstemmer.implementations.OrengoStemmer;

public class TesteStopWords {
	
	public static void main(String[] args) {
		try {
			WordTokenizer tokenizer = new WordTokenizer();
			Collection<Word> words = tokenizer.getWords("Fato: A Polícia Militar foi acionada para verificação de um furto de bicicleta ocorrido na garagem de um edifício, sendo que chegando ao local constatou que havia sido furtada a bicicleta de marca GIOS na cor branca de 21 marchas com freios a disco. Foi realizado o Boletim de Ocorrência Policial e orientado o síndico do prédio. Qualquer informação que leve a Polícia Militar a elucidar o fato, entrar em contato com o telefone 190.");
			String[] acao = {"","",""};
			int qtPassagens = 0;
			for (Word word : words) {
				System.out.println(word.toString());
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
			
			for (String string : acao) {
				System.out.println(string);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static boolean obterAcaoPolicial(String acao){
		return acao.equalsIgnoreCase("foi") ||
				acao.equalsIgnoreCase("auxili") ||
				acao.equalsIgnoreCase("atend") ||
				acao.equalsIgnoreCase("prend") ||
				acao.equalsIgnoreCase("apreend") ||
				acao.equalsIgnoreCase("efetu") ||
				acao.equalsIgnoreCase("registr") ||
				acao.equalsIgnoreCase("abord");
	}
	
	public static boolean obterCrimePolicial(String acao){
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
	
	public static boolean obterObjetoPolicial(String acao){
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

}
