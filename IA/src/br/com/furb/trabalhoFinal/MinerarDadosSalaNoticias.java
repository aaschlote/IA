package br.com.furb.trabalhoFinal;

import java.io.File;
import java.io.FileWriter;
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
			Document document = Jsoup.connect("http://www.saladenoticias.net/?s=atividade%20operacionais&submit=Pesquisar").timeout(10*1000).get();
			Elements searchResults = document.select(".post > h2 > a");
			for (Element result : searchResults) {
	              String link = result.attr("href");
	              listaString.add(link);
			}
			
			for (int i = 2; i <= qtPages; i++){
				Document documentSub = Jsoup.connect("http://www.saladenoticias.net/?s=atividade+operacionais&submit=Pesquisar&paged="+i).timeout(10*1000).get();
				Elements searchResultsSub = documentSub.select(".post > h2 > a");
				for (Element resultSub : searchResultsSub) {
		              String link = resultSub.attr("href");
		              listaString.add(link);
		              System.out.println(link);
				}
			}
			
		} catch (SocketTimeoutException s){
			System.out.println(s.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			System.out.println("QUANTIDADE DE LINKS " + listaString.size());
		}
		
		try {
			for (String dsLink : listaString){
				extraiarOcorrencias(dsLink);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			System.out.println("QUANTIDADES DE FATOS EXTRA�DOS " + listaOcorrenciasPolic.size());
		}
		
		try {
			
			File arquivo = new File("DADOS_MON.arff");
			
			if	(arquivo.exists()){
				arquivo.delete();
			}
			arquivo.createNewFile();
			
			FileWriter writter = new FileWriter(arquivo);
			insereCabecalhoArq(writter);
			
			for (OcorrenciasPoliciais ocorrencias : listaOcorrenciasPolic){
				ocorrencias.processar();
				writter.write(	ocorrencias.getAcao()[0] + ","+
								ocorrencias.getAcao()[1] + ","+
								ocorrencias.getAcao()[2] + ","+
								ocorrencias.getGravidadeOcorr().toString() + "\n");
			}
			
			writter.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		
		
	}
	
	private void extraiarOcorrencias(String dsLink) throws Exception{
		Document document = Jsoup.connect(dsLink).timeout(10*1000).get();
		Elements searchResults = document.select(".post-entry > p");
		Elements searchDtOcorrencoa = document.select(".heading-date");
		String dsData = searchDtOcorrencoa.text();
		
		String dsHorario = "";
		String dsLocal = "";
		String dsFato = "";
		System.out.println(dsLink);
		for (Element result : searchResults) {
			
			if	(result.text().trim().equalsIgnoreCase("�")){
				continue;
			}
			
			if	(result.text().equalsIgnoreCase("Ocorr�ncias de destaque:")){
				continue;
			}
			
			if	(result.text().equalsIgnoreCase(". Ocorr�ncias de destaque:")){
				continue;
			}
			
			if	(result.text().contentEquals("UTILIDADE P�BLICA")){
				break;
			}
			
			if	((result.text().length() > 17) &&
				(result.text().trim().substring(0, 17).equalsIgnoreCase("UTILIDADE P�BLICA"))){
				break;
			}
			
			if	((result.text().length() > 19) &&
				(result.text().trim().substring(0, 19).equalsIgnoreCase("� UTILIDADE P�BLICA"))){
				break;
			}
			
			if	((result.text().length() > 8) &&
				(result.text().substring(0, 8).equalsIgnoreCase("Hor�rio:"))){
				
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
	
	private void insereCabecalhoArq(FileWriter writter) throws Exception{
		writter.write("@relation spambase \n");
		writter.write("% spam, non-spam classe \n");
		writter.write("@attribute acao_policial {foi,auxili,atend,prend,apreend,efetu,registr,abord,nao-identifi} \n");
		writter.write("@attribute crime_policial {furt,assalt,roub,perturb,traf,agred,pris,prisa,arromb,homicidi,poss,nao-identifi} \n");
		writter.write("@attribute objeto_policial {motociclet,veicul,carr,mot,cas,caminha,contain,residenc,estabelec,consulto,empr,sala,pad,biciclet,imovel,crech,bar,loj,nao-identifi} \n");
		writter.write("@attribute classe {BAIXA,MEDIA,ALTA,INEXISTENTE} \n");
		writter.write("@data \n");
	}
	

}
