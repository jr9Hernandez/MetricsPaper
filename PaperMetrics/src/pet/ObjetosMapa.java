package pet;

//Esta classe serve para guardar coordenadas dos objetos instanciados no mapa
public class ObjetosMapa {
	
	private int x;
	private int y;
	protected int lagura; //em caso de buraco
	protected int altura; //em caso de tubos
	
	public void setCoordenadas(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;		
	}

}
