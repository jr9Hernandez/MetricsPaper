package br.ufv.dpi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class StringsPaisesLinguagens {     
 
    public static ArrayList<String> getListOfLanguages(Locale local) {
    	
    	ArrayList<String> listaLinguas = new ArrayList<String>();
    	
    	String[] linguas = Locale.getISOLanguages();
    	
    	for (String linguasCode : linguas) {
    		 
  		  Locale obj = null;  	 
  		  obj = new Locale(linguasCode); 
  		  listaLinguas.add(obj.getDisplayLanguage());
  		  
  		}
    	
    	Collections.sort(listaLinguas);
    	
    	return listaLinguas;
    }
    
    public static ArrayList<String> getListOfCountries(Locale locale) {
    	
    	ArrayList<String> listaPaises = new ArrayList<String>();
    	
    	String[] locales = Locale.getISOCountries();
    	
    	if(locale == null){
	    	for (String countryCode : locales) {
	    		
	    		Locale obj = new Locale("", countryCode);
	    		 
				listaPaises.add(obj.getDisplayCountry());
	    	}
    	}else{
    		for (String countryCode : locales) {
	    		
	    		Locale obj = new Locale("", countryCode);
	    		 
				listaPaises.add(obj.getDisplayCountry(locale));
	    	}
    	}
    	
    	Collections.sort(listaPaises);
    	
    	return listaPaises;
	 
    }
    
    
    /*
    public static void main(String[] args) {
   	 
    	StringsPaisesLinguagens obj = new StringsPaisesLinguagens();
    	ArrayList<String> lista = new ArrayList<String>();
    	
    	lista = getListOfLanguages(null);
    	for(int i= 0; i < lista.size(); i++){
    		System.out.println(lista.get(i));
    	}
    	
    	
 
    }*/
    

}
