package br.com.furb.trabalho1;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import busca.BuscaLargura;
import busca.BuscaProfundidade;
import busca.Estado;
import busca.Nodo;

public class Baia implements Estado{
	
	final int capacidadeAtual;
	final String containerAnt;
	final int porto;
	final String op;
	static ArrayList<ContainerP> listContainer = new ArrayList<ContainerP>();
	
	public Baia (int capacidadeAtual, int porto, String containerAnt, String op){
		this.capacidadeAtual = capacidadeAtual;
		this.containerAnt	 = containerAnt;
		this.porto			= porto;
		this.op = op;
	}
	
	public static void main(String[] args) {
		
		adicionaContainer();
		
		Baia baia = new Baia(0, 0, "", "inicial");
		
	    // chama busca em largura
        System.out.println("busca em largura");
        Nodo n = new BuscaLargura().busca(baia);
        if (n == null) {
            System.out.println("sem solucao!");
        } else {
        	System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
        }
        
        adicionaContainer();
        
        System.out.println("busca em profundidado");
        Nodo n2 = new BuscaProfundidade().busca(baia);
        if (n2 == null) {
            System.out.println("sem solucao!");
        } else {
        	System.out.println("solucao:\n" + n2.montaCaminho() + "\n\n");
        }

	}

	@Override
	public int custo() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean ehMeta() {
		return capacidadeAtual == 480;
	}

	@Override
	public List<Estado> sucessores() {
		List<Estado> suc = new LinkedList<Estado>();
		
		ContainerP container = listContainer.get(0);
		
		String containerAnt = "";
		String movimentacao = "";
		int capacidade = capacidadeAtual;
		
		if	(lancaContainer(this.containerAnt) == 0){
			containerAnt = "40C";
			movimentacao = "MOVEU FEU";
			capacidade += 40;
		}else if	(lancaContainer(this.containerAnt) == 1){
			containerAnt = "20D";
			movimentacao = "MOVEU TEU";
			capacidade += 20;
		}else if	(lancaContainer(this.containerAnt) == 2){
			containerAnt = "20E";
			movimentacao = "MOVEU TEU";
			capacidade += 20;
		}
		
		suc.add(new Baia(capacidade, container.getNrPorto(), containerAnt, movimentacao));
		
		listContainer.remove(0);
		
		return suc;
	}
	
	
	public int lancaContainer(String containerAnt){
		if	(containerAnt.equalsIgnoreCase("")){
			return 0;
		}else if (containerAnt.equalsIgnoreCase("40C")){
			return 1;
		}
		else if (containerAnt.equalsIgnoreCase("20D")){
			return 2;
		}else if (containerAnt.equalsIgnoreCase("20E")){
			return 0;
		}
		return -1;
	}
	
	public int hashCode() {
        return (capacidadeAtual + "," + containerAnt + "," + porto).hashCode();
    }
	
	public boolean equals(Object o) {
        if (o instanceof Baia) {
            Baia e = (Baia)o;
            return e.capacidadeAtual == capacidadeAtual && e.containerAnt == containerAnt && e.porto == porto;
        }
        return false;
    }
	
	public String toString() {
        return "\n(" + capacidadeAtual + "," + containerAnt + "," + porto + ") - "+op;
    }
	
	private static void adicionaContainer(){
		ContainerP container1 = new ContainerP("40C", 1, "");
		ContainerP container2 = new ContainerP("20E", 1, "40C");
		ContainerP container3 = new ContainerP("20D", 1, "20E");
		ContainerP container4 = new ContainerP("20E", 1, "20D");
		ContainerP container5 = new ContainerP("20D", 1, "20E");
		ContainerP container6 = new ContainerP("40C", 1, "20D");
		ContainerP container7 = new ContainerP("40C", 2, "40C");
		ContainerP container8 = new ContainerP("20E", 2, "40C");
		ContainerP container9 = new ContainerP("20D", 2, "20E");
		ContainerP container10 = new ContainerP("20E", 2, "20D");
		ContainerP container11 = new ContainerP("20D", 2, "20E");
		ContainerP container12 = new ContainerP("40C", 2, "20D");
		ContainerP container13 = new ContainerP("20E", 3, "40C");
		ContainerP container14 = new ContainerP("20D", 3, "20E");
		ContainerP container15 = new ContainerP("40C", 3, "20D");
		ContainerP container16 = new ContainerP("40C", 3, "40C");
		ContainerP container17 = new ContainerP("20E", 3, "40C");
		ContainerP container18 = new ContainerP("20D", 3, "20E");
		
		listContainer.add(container1);
		listContainer.add(container2);
		listContainer.add(container3);
		listContainer.add(container4);
		listContainer.add(container5);
		listContainer.add(container6);
		listContainer.add(container7);
		listContainer.add(container8);
		listContainer.add(container9);
		listContainer.add(container10);
		listContainer.add(container11);
		listContainer.add(container12);
		listContainer.add(container13);
		listContainer.add(container14);
		listContainer.add(container15);
		listContainer.add(container16);
		listContainer.add(container17);
		listContainer.add(container18);
	}
	

}
