public class Personnage {

	private int x, y;
	private int nb_vie;

	public Personnage(Labyrinthe l, int i, int j, int nbrVie) {

//		this.x = i * Labyrinth.HMURET + Labyrinth.HMURET / 2;
		x=i	;	y = j ;
//		this.y = j * Labyrinth.LMURET + Labyrinth.LMURET / 2;
		this.nb_vie = nbrVie ; 

	}

	public Personnage (Personnage p){
		this.x = p.x ; 
		this.y = p.y ;
		this.nb_vie = p.nb_vie ;
	}
	
	public void PerGauche() {

		this.y--;

	}

	public void perDroite() {

		this.y++;

	}


	public void setNb_vie(int nb_vie) {
		this.nb_vie = nb_vie;
	}

	public void perHaut() {

		this.x--;

	}

	public void perBas() {

		this.x++;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getNbrVie(){
		return this.nb_vie;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}