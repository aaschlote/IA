package br.com.furb.trabalhoFinal;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MinerarDadosSalaNoticias {
	
	ArrayList<OcorrenciasPoliciais> listaOcorrenciasPolic = new ArrayList<OcorrenciasPoliciais>();
	ArrayList<String> listaString = new ArrayList<String>();
	private static final int qtPages = 31;
	
	public void extrair() {
		try {
			Document document = Jsoup.connect("http://www.saladenoticias.net/?s=atividade%20operacionais&submit=Pesquisar").get();
			Elements searchResults = document.select(".post > h2 > a");
			for (Element result : searchResults) {
	              String link = result.attr("href");
	              listaString.add(link);
	              Thread.sleep(2000);
			}
			
			for (int i = 2; i <= qtPages; i++){
				System.out.println("http://www.saladenoticias.net/?s=atividade+operacionais&submit=Pesquisar&paged="+i);
				Document documentSub = Jsoup.connect("http://www.saladenoticias.net/?s=atividade+operacionais&submit=Pesquisar&paged="+i).get();
				Elements searchResultsSub = documentSub.select(".post > h2 > a");
				for (Element resultSub : searchResultsSub) {
		              String link = resultSub.attr("href");
		              listaString.add(link);
		              Thread.sleep(2000);
				}
			}
		} catch (SocketTimeoutException s){
			System.out.println("QUANTIDADE DE LINKS " + listaString.size());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		try {
			Thread.sleep(10000);
			
			for (String dsLink : listaString){
				extraiarOcorrencias(dsLink);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		for (OcorrenciasPoliciais ocorrencias : listaOcorrenciasPolic){
			ocorrencias.processar();
			System.out.println(ocorrencias.getDsHorario() + "=" + ocorrencias.getDsBairro());
		}
		
	}
	
	private void extraiarOcorrencias(String dsLink) throws Exception{
		Document document = Jsoup.connect(dsLink).get();
		Elements searchResults = document.select(".post-entry > p");
		Elements searchDtOcorrencoa = document.select(".heading-date");
		String dsData = searchDtOcorrencoa.text();
		
		String dsHorario = "";
		String dsLocal = "";
		String dsFato = "";
		System.out.println(dsLink);
		for (Element result : searchResults) {
			
			if	(result.text().trim().equalsIgnoreCase(" ")){
				continue;
			}
			
			if	(result.text().equalsIgnoreCase("Ocorrências de destaque:")){
				continue;
			}
			
			if	(result.text().equalsIgnoreCase(". Ocorrências de destaque:")){
				continue;
			}
			
			if	(result.text().contentEquals("UTILIDADE PÚBLICA")){
				break;
			}
			
			if	((result.text().length() > 17) &&
				(result.text().trim().substring(0, 17).equalsIgnoreCase("UTILIDADE PÚBLICA"))){
				break;
			}
			
			if	((result.text().length() > 19) &&
				(result.text().trim().substring(0, 19).equalsIgnoreCase("  UTILIDADE PÚBLICA"))){
				break;
			}
			
			if	((result.text().length() > 8) &&
				(result.text().substring(0, 8).equalsIgnoreCase("Horário:"))){
				
				if	(result.text().indexOf("Local:") > 0){
					dsHorario = result.text().substring(0,result.text().indexOf("Local:"));
				}else{
					dsHorario = result.text();
				}
				 
				
				if	(result.text().indexOf("Local:") > 0){
					
					int qtFato = result.text().indexOf("Fato:");
					
					if	(qtFato <= 0){
						qtFato = result.text().length();
					}
					
					dsLocal = result.text().substring(result.text().indexOf("Local:"),qtFato);
				}
				
				if	(result.text().indexOf("Fato:") > 0){
					dsFato = result.text().substring(result.text().indexOf("Fato:"),result.text().length());
				}
				
			}
			
			if	((dsLocal.equalsIgnoreCase("")) &&
				(result.text().length() > 6) &&
				(result.text().substring(0, 6).equalsIgnoreCase("Local:"))){
				dsLocal = result.text(); 
			}
			
			if	((dsFato.equalsIgnoreCase("")) &&
				(result.text().length() > 5) &&
					(result.text().substring(0, 5).equalsIgnoreCase("Fato:"))){
				dsFato = result.text(); 
			}
			
			if	(getTodosCamposPreenchidos(dsHorario,dsLocal,dsFato)){
				OcorrenciasPoliciais ocorrenciasPoliciais = new OcorrenciasPoliciais(dsFato, dsLocal, dsHorario,dsData);
				listaOcorrenciasPolic.add(ocorrenciasPoliciais);
				dsHorario = "";
				dsLocal = "";
				dsFato = "";
			}
		}
		
	}
	
	public boolean getTodosCamposPreenchidos(String dsHorario,String dsLocal,String dsFato){
		return !dsHorario.equalsIgnoreCase("") &&
				!dsLocal.equalsIgnoreCase("") &&
				!dsFato.equalsIgnoreCase("");
	}
	

}
