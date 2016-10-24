import java.util.Random;

public class Labyrinthe {

	private char[][] tab;
	public Personnage psn ; 
	private int ouvertureJ; 
	static final int LMURET = 8;
	static final int HMURET = 4;


	public Labyrinthe(int hauteur, int largeur) {

		creeTableau(hauteur, largeur);

	}

	public Labyrinthe(Labyrinthe l) { // pk ca ?

		char[][] tabCopie = new char[l.tab.length][];
		for (int i = 0; i < l.tab.length; i++)
		tabCopie[i] = l.tab[i].clone();
		this.tab = tabCopie;
		this.psn = new Personnage (l.psn); 

	}

	public void creeTableau(int hauteur, int largeur)

	{
		this.tab = new char[hauteur * HMURET + 1][largeur * LMURET + 1];
		effaceTableau();

	}

	public void effaceTableau() {

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++)

			{
				tab[i][j] = ' ';
			}

		}
	}

	public void dessineMurdEnceinte() {
		int i;
		tab[0][0] = '+';
		tab[0][tab[0].length - 1] = '+';
		tab[tab.length - 1][0] = '+';
		tab[tab.length - 1][tab[0].length - 1] = '+';

		for (i = 1; i < tab[0].length - 1; i++) {
			tab[0][i] = '-';
			tab[tab.length - 1][i] = '-';
		}
		for (i = 1; i < tab.length - 1; i++) {
			tab[i][0] = '|';
			tab[i][tab[0].length - 1] = '|';
		}

	}

	public void dessineOuverture(int j) {

		assert (j < (this.tab.length - 1) / 2);

		for (int i = j * 3 + 1; i < j * 3 + HMURET; i++) {
			tab[i + j][tab[0].length - 1] = ' ';
		}

	}

	public void dessineMuretVertical(int i, int j) {
		assert (i < (this.tab.length - 1) / 4 && j < ((this.tab[0].length - 1) / 8) - 1); // Pourquoi
																							// ???
																							// k

		for (int k = 1; k < HMURET; k++) {
			tab[k + i * HMURET][j * LMURET] = '|';
		}
	}

	public void dessineMuretHorizontal(int i, int j) {
		assert (i < ((this.tab.length - 1) / 4) - 1 && j < ((this.tab[0].length - 1) / 8) - 1);

		for (int k = 1; k < LMURET; k++) {
			tab[HMURET * i][j * LMURET + k] = '-';
		}

	}

	public boolean muretVerticalExiste(int i, int j) {

		for (int k = 1; k < HMURET ; k++) {
			if (tab[k + i * HMURET ][j * LMURET] == '|') {
				return true;
			}
		}
		return false;
	}

	public boolean muretHorizontalExiste(int i, int j) {

		for (int k = 1; k < LMURET; k++) {
			if (tab[HMURET * i ][j * LMURET + k] == '-') {
				return true;
			}
		}
		return false;
	}

	public void dessinePersonnage(Personnage p) {
		tab[p.getX() * HMURET + HMURET / 2][p.getY() * LMURET + LMURET / 2] = '@';
//		tab[p.getX() ][p.getY()] = '@';
	}

	public void effacePersonnage(Personnage p) {
		tab[p.getX() * HMURET + HMURET / 2][p.getY() * LMURET + LMURET / 2] = ' ';
	}

	public static void effaceEcran() {

		for (int i = 0; i < 200; i++) {
			System.out.println();
		}
	}

	public void affiche() {
		String s = new String();
		for (int i = 0; i < this.tab.length; i++) {
			for (int j = 0; j < this.tab[0].length; j++) {
				s += this.tab[i][j] + "";
			}
			s += "\n";
		}
		System.out.print(s);
	}

	public void construitLabyrintheAleatoire(double densite, int nbrvie) {
		dessineMurdEnceinte();
		Random rn = new Random();
		int answer = rn.nextInt(((this.tab.length - 1) / HMURET)) + 0;
		Random rnn = new Random();
		int answer2 = rnn.nextInt(((this.tab.length - 1) / HMURET)) + 0;
		Personnage p = new Personnage(this, answer, 0, nbrvie);
		psn = p ; 
		dessineOuverture(answer2);
		ouvertureJ = answer2 ;
		dessinePersonnage(p);

		for (int i = 0; i < ((this.tab.length - 1) / HMURET); i++) {

			for (int j = 0; j < ((this.tab[0].length - 1) / LMURET); j++) {
				double random = Math.random();
				if (random <= densite) {
					this.dessineMuretHorizontal(i, j);
				}
			}
		}

		for (int i = 0; i < ((this.tab.length - 1) / HMURET); i++) {

			for (int j = 0; j < ((this.tab[0].length - 1) / LMURET); j++) {
				double random = Math.random();
				if (random <= densite) {
					this.dessineMuretVertical(i, j);
				}
			}

		}
	}


	public void rendreInvisible() {
		this.effaceTableau(); 
		this.dessineMurdEnceinte();
		this.dessineOuverture(ouvertureJ);
		this.dessinePersonnage(psn);
	}
}