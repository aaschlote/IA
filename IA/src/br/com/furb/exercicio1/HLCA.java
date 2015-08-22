package br.com.furb.exercicio1;

import java.util.LinkedList;
import java.util.List;

import busca.BuscaLargura;
import busca.Nodo;
import busca.Estado;

public class HLCA implements Estado{
	
	final char homem, lobo, carneiro, alface;
	
	final String op; //Ação executada para chegar nesse estado
	
	public HLCA(char h, char l, char c, char a, String o){
		
		homem = h;
		lobo = l;
		carneiro = c;
		alface = a;
		op = o;
	}
	
	public static void main (String[] a){
		HLCA estadoInicial = new HLCA('e', 'e', 'e', 'e', "inicial");
		
		BuscaLargura busca = new BuscaLargura();
		
		Nodo nodo = busca.busca(estadoInicial);
		
		if	(nodo == null){
			System.out.println("Problema sem solução");
		}else{
			System.out.println("Solução: \n" + nodo.montaCaminho());
			System.out.println("Solução: \n" + nodo.getProfundidade());
		}
		
	}

	@Override
	public int custo() {
		return 1;
	}

	@Override
	public boolean ehMeta() {
		return homem == 'd' && lobo == 'd' && carneiro  == 'd' && alface  == 'd';
	}

	@Override
	public List<Estado> sucessores() {
		List<Estado> suc = new LinkedList<Estado>();
		levarLobo(suc);
		levarCarneiro(suc);
		levarAlface(suc);
		levarNada(suc);
		
		return suc;
	}
	
	private void levarLobo(List<Estado> suc){
		if (lobo == homem){
			char novaMargem = ladoOposto(homem);
			HLCA novoEstado = new HLCA(novaMargem, novaMargem, carneiro, alface, "levarLobo- " + homem + novaMargem);
			
			if	(novoEstado.ehValido()){
				suc.add(novoEstado);
			}
			
		}
		
	}
	
	private void levarCarneiro(List<Estado> suc){
		if (carneiro == homem){
			char novaMargem = ladoOposto(homem);
			HLCA novoEstado = new HLCA(novaMargem, lobo, novaMargem, alface, "levarCarneiro- " + homem + novaMargem);
		
			if	(novoEstado.ehValido()){
				suc.add(novoEstado);
			}
		}
		
	}
	
	private void levarAlface(List<Estado> suc){
		if (alface == homem){
			char novaMargem = ladoOposto(homem);
			HLCA novoEstado = new HLCA(novaMargem, lobo, carneiro, novaMargem, "levarAlface- " + homem + novaMargem);
			
			if	(novoEstado.ehValido()){
				suc.add(novoEstado);
			}
		}
	}
	
	private void levarNada(List<Estado> suc){
		char novaMargem = ladoOposto(homem);
		HLCA novoEstado = new HLCA(novaMargem, lobo, carneiro, alface, "levarNada- " + homem + novaMargem);
		
		if	(novoEstado.ehValido()){
			suc.add(novoEstado);
		}
	}
	
	private boolean ehValido(){
		
		if	(lobo == carneiro && lobo != homem){
			return false;
		}
		
		if	(carneiro == alface && carneiro != homem){
			return false;
		}
		
		return true;
	}
	
	private char ladoOposto(char lado){
		if	(lado == 'e'){
			return 'd';
		}
		return 'e';
	}
	
	public boolean equals(Object o){
		if	(o instanceof HLCA){
			HLCA e = (HLCA) o;
			return e.homem == this.homem && e.lobo == this.lobo &&
					e.carneiro == this.carneiro && e.alface == this.alface;
		}
		return false;
		
	}
	
	public int hashCode(){		
		return ("" + homem + lobo + carneiro + alface).hashCode();
	}
	
	public String toString(){

		String dir = "";
		String esq = "";
		
		if	(homem == 'd'){
			dir += 'h';
		}else{
			esq += 'h';
		}
		
		if	(lobo == 'd'){
			dir += 'l';
		}else{
			esq += 'l';
		}
		
		if	(carneiro == 'd'){
			dir += 'c';
		}else{
			esq += 'c';
		}
		
		if	(alface == 'd'){
			dir += 'a';
		}else{
			esq += 'a';
		}
		
		return esq + "--" + dir + "(" + op + ")\n";
		
	}

}

