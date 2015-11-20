package pet;

import java.io.FileReader;
import java.util.*;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class ClassificadorWeka {
	
	//Denifição dos atributos. Cada atributo corresponde ao elemento do cenário conforme são salvos	
	Attribute at_gaps1 = new Attribute("gaps1"); //1	Lacunas
	Attribute at_gaps2 = new Attribute("gaps2"); //2
	Attribute at_gaps3 = new Attribute("gaps3"); //3
	Attribute at_tubes1 = new Attribute("tubes1"); //4 Tubos
	Attribute at_tubes2 = new Attribute("tubes2"); //5
	Attribute at_tubes3 = new Attribute("tubes3"); //6
	Attribute at_moutains = new Attribute("mountains"); //7 Montanhas
	Attribute at_coins = new Attribute("coins"); //8 Moedas
	Attribute at_quests = new Attribute("quests"); //9 Quest
	Attribute at_red_koopas1 = new Attribute("red_koopas1"); //10 
	Attribute at_red_koopas2 = new Attribute("red_koopas2"); //11
	Attribute at_green_koopas1 = new Attribute("green_koopas1"); //12
	Attribute at_green_koopas2 = new Attribute("green_koopas2"); //13
	Attribute at_goombas1 = new Attribute("goombas1"); //14
	Attribute at_goombas2 = new Attribute("goombas2"); //15
	Attribute at_spinies = new Attribute("spinies"); //16
	Attribute at_plants = new Attribute("plants"); //17
	Attribute at_avaliacao = new Attribute("avaliacao"); //18
	
	//Declaração do vetor de atributos
	weka.core.FastVector fvAttributes = new FastVector(18);
	
	//Método pra setar os atributos
	void setFastVector(){
		this.fvAttributes.addElement(at_gaps1); //1
		this.fvAttributes.addElement(at_gaps2); //2 
		this.fvAttributes.addElement(at_gaps3); //3
		this.fvAttributes.addElement(at_tubes1); //4
		this.fvAttributes.addElement(at_tubes2); //5
		this.fvAttributes.addElement(at_tubes3); //6
		this.fvAttributes.addElement(at_moutains); //7
		this.fvAttributes.addElement(at_coins); //8
		this.fvAttributes.addElement(at_quests); //9
		this.fvAttributes.addElement(at_red_koopas1); //10
		this.fvAttributes.addElement(at_red_koopas2); //11
		this.fvAttributes.addElement(at_green_koopas1);
		this.fvAttributes.addElement(at_green_koopas2);
		this.fvAttributes.addElement(at_goombas1); //14
		this.fvAttributes.addElement(at_goombas2); //15
		this.fvAttributes.addElement(at_spinies); //16
		this.fvAttributes.addElement(at_plants); //17
		this.fvAttributes.addElement(at_avaliacao); //18
	}
	
	//Create an empty training set (Conjunto de treinamento vazio)
	Instances isTrainingSet = new Instances("Rel", fvAttributes, 10);
	
	//Set class index
	void setIsTrainingSet(){	
		this.isTrainingSet.setClassIndex(17);		
	}
	
	Instance iExemple = new Instance(18);
	
	void setInstace(int atributos[]){
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(0), atributos[0]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(1), atributos[1]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(2), atributos[2]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(3), atributos[3]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(4), atributos[4]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(5), atributos[5]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(6), atributos[6]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(7), atributos[7]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(8), atributos[8]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(9), atributos[9]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(10), atributos[10]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(11), atributos[11]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(12), atributos[12]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(13), atributos[13]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(14), atributos[14]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(15), atributos[15]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(16), atributos[16]);
		this.iExemple.setValue((Attribute)fvAttributes.elementAt(17), atributos[17]);
		this.isTrainingSet.add(iExemple);
	}
	
	//Criando o classificador
	weka.classifiers.trees.M5P cls = new weka.classifiers.trees.M5P();
	
	void setClassificador() throws Exception{
		isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);
		this.cls.buildClassifier(isTrainingSet);
		
    }
	
	// Create an empty training set
    Instances instancias_sem_rotulo = new Instances("Rel2", fvAttributes, 10);
    // Set class index
    void setClassIndexInstancia(){
    	instancias_sem_rotulo.setClassIndex(8);
    }

    //Create the instance 1
    Instance iExample2 = new Instance(9);
    
    void setValuesExemple2(int vetor[]){
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(0), vetor[0]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(1), vetor[1]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(2), vetor[2]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(3), vetor[3]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(4), vetor[4]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(5), vetor[6]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(7), vetor[7]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(8), vetor[8]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(9), vetor[9]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(10), vetor[10]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(11), vetor[11]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(12), vetor[12]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(13), vetor[13]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(14), vetor[14]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(15), vetor[15]);
    	iExample2.setValue((weka.core.Attribute)fvAttributes.elementAt(16), vetor[16]);
    	instancias_sem_rotulo.add(iExample2);
    }

        //--------------------------
    void rotulaInstancais() throws Exception{
    	instancias_sem_rotulo.setClassIndex(instancias_sem_rotulo.numAttributes() - 1);
    	weka.core.Instances instancias_rotuladas = new weka.core.Instances(instancias_sem_rotulo);
    	
    	double clsLabel = cls.classifyInstance(instancias_sem_rotulo.instance(0));
        instancias_rotuladas.instance(0).setClassValue(clsLabel);
    }
    
    
    
}
