package br.ufv.dpi;

import java.util.ArrayList;

public class ArvoreBinaria {
	
	public String item;
	public ArvoreBinaria arvore_direita = null;
	public ArvoreBinaria arvore_esquerda = null;
	
	public ArvoreBinaria insereItem(String item, ArvoreBinaria arvore){
		
		if(arvore != null){
			if(item.compareTo(arvore.item) > 0){
				System.out.println(item + " maior que " + arvore.item);
				arvore.arvore_direita = insereItem(item, arvore.arvore_direita);		
			}
			else if(item.compareTo(arvore.item) < 0){
				System.out.println(item + " menor que " + arvore.item);
				arvore.arvore_esquerda = insereItem(item, arvore.arvore_esquerda);		
			}
		}else {
			arvore = new ArvoreBinaria();
			arvore.item = item;
			System.out.println("Inserido " + item);		
			
		}
		
		return arvore;
	}
	
	public void imprimeArvore(ArvoreBinaria arvore){		
		if(arvore!= null){
			imprimeArvore(arvore.arvore_esquerda);
			System.out.println(arvore.item);
			imprimeArvore(arvore.arvore_direita);
		}		 
	}
	
	
	public ArrayList<String> preencheLista(ArvoreBinaria arvore, ArrayList<String> lista){		
		if(arvore!= null){
			lista = preencheLista(arvore.arvore_esquerda, lista);
			lista.add(arvore.item);
			lista = preencheLista(arvore.arvore_direita, lista);
		}
		return lista;		 
	}
}
