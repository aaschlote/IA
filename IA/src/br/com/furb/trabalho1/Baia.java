package br.com.furb.trabalho1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import busca.BuscaLargura;
import busca.BuscaProfundidade;
import busca.Estado;
import busca.Nodo;

public class Baia implements Estado {

	final String op;
	final String baias[][];
	final String dsIdentifCont;
	static ArrayList<ContainerP> listContainer = new ArrayList<ContainerP>();
	
	public Baia(String op, String baias[][], String dsIdentifCont) {
		this.op = op;
		this.dsIdentifCont = dsIdentifCont;
		this.baias = baias;
	}

	public static void main(String[] args) {

		adicionaContainer();
		
		String baiasAux[][] = {
				{"", "", "", "","", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", },  
				{"", "", "", "","", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "", "", "", "", }
		};
		
		Estado baia = new Baia("inicial", baiasAux, "");

		// chama busca em largura
		System.out.println("busca em largura");
		Nodo n = new BuscaLargura().busca(baia);
		if (n == null) {
			System.out.println("sem solucao!");
		} else {
			System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
		}

		adicionaContainer();

		System.out.println("busca em profundidade");
		Nodo n2 = new BuscaProfundidade().busca(baia);
		if (n2 == null) {
			System.out.println("sem solucao!");
		} else {
			System.out.println("solucao:\n" + n2.montaCaminho() + "\n\n");
		}
		
	}
	
	public int ultimaLinhaBaia(int baia){
		int ultimaLinhaReg = -1; 
		for (int i = 0; i < baias[baia].length; i++) {
			String info = baias[baia][i];
			if	(!info.equals("")){
				ultimaLinhaReg = i;
			}
		}
		
		return ultimaLinhaReg;
	}
	
	public int ultimoPortoAlocadoBaia(int baia){
		int ultimaLinhaReg = -1; 
		for (int i = 0; i < baias[baia].length; i++) {
			String info = baias[baia][i];
			if	(!info.equals("")){
				ultimaLinhaReg = i;
			}
		}
		
		if	(ultimaLinhaReg >= 0){
			String dado = baias[baia][ultimaLinhaReg];
			return Integer.parseInt(String.valueOf(dado.charAt(1)));
		}
		
		return Integer.MAX_VALUE;
	}

	public boolean isPodeAlocarContainer(int baia, String container) {
		return maximoCont(baia) && containerAlocado(container);
	}
	
	public boolean maximoCont(int baia){
		int qtContainerAlocados = 0;
		for (int i = 0; i < baias[baia].length; i++) {
			String info = baias[baia][i];
			
			if	(info.length() > 0){
				if (info.charAt(0) == 'T') {
					qtContainerAlocados += 40;
				} else if (info.charAt(0) == 'F') {
					qtContainerAlocados += 20;
				}
			}
		}

		return qtContainerAlocados < 480;
	}
	
	public boolean containerAlocado(String dsContainer){
		for (int i = 0; i < this.baias.length; i++) {
			for (int j = 0; j < baias[i].length; j++) {
				String info = baias[i][j];
				if	(info.length() > 0){
					if (info.substring(3).equalsIgnoreCase(dsContainer)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public int getIndexContainer(int porto){
		
		for	(int i = 0; i < listContainer.size(); i++){
			ContainerP container = listContainer.get(i);
			if	((container.getNrPorto() == porto) &&
				(container.getDsTipo().equals("F"))){
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public int custo() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean ehMeta() {
		return !maximoCont(0) && !maximoCont(1);
	}

	@Override
	public List<Estado> sucessores() {
		List<Estado> suc = new LinkedList<Estado>();

		ContainerP container = listContainer.remove(0);
		
		if	(isPodeAlocarContainer(0,container.getDsIdentificacao()) &&
			(ultimoPortoAlocadoBaia(0) >= container.getNrPorto())) {
			int linha = ultimaLinhaBaia(0)+1;
			baias[0][linha] = container.getDsTipo() + container.getNrPorto() + " " + container.getDsIdentificacao();
			
			if	(container.getDsTipo().equalsIgnoreCase("F")){
				int index = getIndexContainer(container.getNrPorto());
				
				if	(index > 0){
					ContainerP containerAux = listContainer.remove(index);
					baias[0][linha++] = container.getDsTipo() + container.getNrPorto() + " " + container.getDsIdentificacao();
				}
				
			}
			
			suc.add(new Baia("Moveu " + container.getDsTipo()+"EU do porto " + container.getNrPorto() + " para a baia " + 0 , baias.clone(), container.getDsIdentificacao()));
		}
		
		container = listContainer.remove(0);

		if	(isPodeAlocarContainer(1,container.getDsIdentificacao()) &&
				(ultimoPortoAlocadoBaia(1) >= container.getNrPorto())) {
				int linha = ultimaLinhaBaia(1)+1;
				baias[1][linha] = container.getDsTipo() + container.getNrPorto()+ " " + container.getDsIdentificacao();
				suc.add(new Baia("Moveu " + container.getDsTipo()+"EU do porto " + container.getNrPorto() + " para a baia " + 1 , baias.clone(), container.getDsIdentificacao()));
			}

		return suc;
	}

	public int hashCode() {
		return (""+baias).hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof Baia) {
			Baia e = (Baia) o;
			 return e.baias == baias;
		}
		return false;
	}

	public String toString() {
		String baiasString = "";
		
		for (int i = 0; i < this.baias.length; i++) {
			baiasString += "BAIA " + i + "\n";
			for (int j = 0; j < baias[i].length; j++) {
				baiasString += baias[i][j] + " \n"; 
			}
		}
		
		return baiasString;
	}

	private static void adicionaContainer() {
		ContainerP container1 = new ContainerP("T", 3, "T3A");
		
		
		ContainerP container2 = new ContainerP("F", 3, "F3A");
		ContainerP container3 = new ContainerP("F", 3, "F3B");
		ContainerP container4 = new ContainerP("T", 3, "T3C");
		ContainerP container5 = new ContainerP("F", 3, "F3D");
		ContainerP container6 = new ContainerP("F", 3, "F3E");
		ContainerP container7 = new ContainerP("F", 3, "F3F");
		ContainerP container8 = new ContainerP("F", 3, "F3G");
		ContainerP container9 = new ContainerP("F", 3, "F3H");
		ContainerP container10 = new ContainerP("F", 3, "F3I");
		ContainerP container11 = new ContainerP("F", 3, "F3J");
		ContainerP container12 = new ContainerP("F", 3, "F3K");
		ContainerP container13 = new ContainerP("F", 2, "F2A");
		ContainerP container14 = new ContainerP("F", 2, "F2B");
		ContainerP container15 = new ContainerP("F", 2, "F2C");
		ContainerP container16 = new ContainerP("T", 2, "T2A");
		ContainerP container17 = new ContainerP("F", 2, "F2D");
		ContainerP container18 = new ContainerP("F", 2, "F2E");
		
		ContainerP container19 = new ContainerP("T", 2, "T2B");
		ContainerP container20 = new ContainerP("F", 2, "40C");
		ContainerP container21 = new ContainerP("F", 2, "F2F");
		ContainerP container22 = new ContainerP("T", 2, "T2C");
		ContainerP container23 = new ContainerP("F", 1, "F1A");
		ContainerP container24 = new ContainerP("F", 1, "F1B");
		ContainerP container25 = new ContainerP("F", 1, "F1C");
		ContainerP container26 = new ContainerP("F", 1, "F1D");
		ContainerP container27 = new ContainerP("F", 2, "F2G");
		ContainerP container28 = new ContainerP("F", 2, "F2H");
		ContainerP container29 = new ContainerP("F", 2, "F2I");
		ContainerP container30 = new ContainerP("F", 2, "F2J");
		ContainerP container31 = new ContainerP("F", 1, "F1E");
		ContainerP container32 = new ContainerP("F", 1, "F1F");
		ContainerP container33 = new ContainerP("F", 1, "F1G");
		ContainerP container34 = new ContainerP("T", 1, "T1A");
		ContainerP container35 = new ContainerP("F", 1, "F1H");
		ContainerP container36 = new ContainerP("F", 1, "F1I");
		
		ContainerP container37 = new ContainerP("T", 3, "T3B");
		ContainerP container38 = new ContainerP("T", 3, "T3C");
		ContainerP container39 = new ContainerP("T", 3, "T3D");
		ContainerP container40 = new ContainerP("T", 3, "T3E");
		ContainerP container41 = new ContainerP("T", 3, "T3F");
		ContainerP container42 = new ContainerP("T", 3, "T3G");
		ContainerP container43 = new ContainerP("T", 3, "T3H");
		ContainerP container44 = new ContainerP("T", 3, "T3I");
		ContainerP container45 = new ContainerP("T", 3, "T3J");
		ContainerP container46 = new ContainerP("T", 3, "T3K");
		
		ContainerP container47 = new ContainerP("F", 1, "F1J");
		ContainerP container48 = new ContainerP("F", 1, "F1K");
		ContainerP container49 = new ContainerP("F", 1, "F1L");
		ContainerP container50 = new ContainerP("F", 1, "F1M");

		listContainer.add(container1);
		
		listContainer.add(container37);
		listContainer.add(container38);
		listContainer.add(container39);
		listContainer.add(container40);
		listContainer.add(container41);
		
		listContainer.add(container42);
		listContainer.add(container43);
		listContainer.add(container44);
		listContainer.add(container45);
		listContainer.add(container46);
		
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
		
		listContainer.add(container19);
		listContainer.add(container20);
		listContainer.add(container21);
		listContainer.add(container22);
		listContainer.add(container23);
		listContainer.add(container24);
		listContainer.add(container25);
		listContainer.add(container26);
		listContainer.add(container27);
		listContainer.add(container28);
		listContainer.add(container29);
		listContainer.add(container30);
		listContainer.add(container31);
		listContainer.add(container32);
		listContainer.add(container33);
		listContainer.add(container34);
		listContainer.add(container35);
		listContainer.add(container36);
		
		listContainer.add(container47);
		listContainer.add(container48);
		listContainer.add(container49);
		listContainer.add(container50);
		
	}

}
