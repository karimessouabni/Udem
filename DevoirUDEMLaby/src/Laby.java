import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Laby {

	public static Labyrinthe labyOriginal, l;
	public static boolean quitterPgm, nvlPartie = false;

	public Laby() {
		// TODO Auto-generated constructor stub
	}

	public static void afficherNbrVie(Personnage p, int nbrV) {
		System.out.println("Il vous reste " + p.getNbrVie() + " vies sur "
				+ nbrV);
		System.out
				.println("\nQuelle direction souhaitez-vous prendre?\n(droite: d; gauche: g ou s; haut: h ou e; bas: b ou x)");
	}

	public static void jeuLabyrintheInvisible(int h, int la, double densite,
			int nbrScd, int nbrVies) {

		Scanner input = new Scanner(System.in);

		l = new Labyrinthe(h, la);
		l.construitLabyrintheAleatoire(densite, nbrVies);
		labyOriginal = new Labyrinthe(l);

		l.affiche();
		System.out
				.println("Memorisez le labyrinthe avant qu'il soit invisible !");

		Laby.sleep(nbrScd * 1000);
		l.rendreInvisible();
		Labyrinthe.effaceEcran();
		l.affiche();
		afficherNbrVie(l.psn, nbrVies);
		String in;

		while (l.psn.getNbrVie() > 0) {

			in = input.nextLine();

			if (in.equals("d")) {

				if (labyOriginal.muretVerticalExiste(l.psn.getX(),
						l.psn.getY() + 1)) {
					if (!decrementerVie(l.psn, nbrVies))
						break;
					dessinMuretVApresCollision(l.psn.getX(), l.psn.getY() + 1, nbrVies);
					continue;
				} else {
					if (dessinPsnApresDeplacement('d', nbrVies, la))
						break;
					continue;
				}

			} else if (in.equals("g") || in.equals("s")) {
				if (labyOriginal
						.muretVerticalExiste(l.psn.getX(), l.psn.getY())) {
					if (!decrementerVie(l.psn, nbrVies))
						break;
					dessinMuretVApresCollision(l.psn.getX(), l.psn.getY(), nbrVies);
					continue;
				} else {
					if (dessinPsnApresDeplacement('g', nbrVies, la))
						break;
					continue;
				}

			} else if (in.equals("h") || in.equals("e")) {

				if (labyOriginal.muretHorizontalExiste(l.psn.getX(),
						l.psn.getY())) {
					if (!decrementerVie(l.psn, nbrVies))
						break;
					dessinMuretHApresCollision(l.psn.getX(), l.psn.getY(), nbrVies);
					continue;
				} else {
					if (dessinPsnApresDeplacement('h', nbrVies, la))
						break;
					continue;
				}
			} else if (in.equals("b") || in.equals("x")) {
				if (labyOriginal.muretHorizontalExiste(l.psn.getX() + 1,
						l.psn.getY())) {
					if (!decrementerVie(l.psn, nbrVies))
						break;
					dessinMuretHApresCollision(l.psn.getX() + 1, l.psn.getY(), nbrVies);
					continue;
				} else {
					if (dessinPsnApresDeplacement('b', nbrVies, la))
						break;
					continue;
				}
			} else if (in.equals("q")) {
				quitterPgm = true;
				break;
			} else if (in.equals("p")) {
				nvlPartie = true;
				break;
			} else if (in.equals("v")) {
				rendreVisible();
				continue;
			} else if (in.equals("o")) {
				iA(l, nbrVies, la);
				break;
			} else {
				continue;

			}

		}

	}

	public static boolean decrementerVie(Personnage p, int nbrVies) {
		p.setNb_vie(p.getNbrVie() - 1);
		if (p.getNbrVie() == 0) {
			System.out.println("Vous avez perdu, vous avez épuisé vos"
					+ nbrVies + "vies!");
			return false;
		} else
			return true;
	}

	public static void dessinMuretVApresCollision(int x, int y, int nbvie) {
		l.dessineMuretVertical(x, y);
		l.affiche();
		afficherNbrVie(l.psn, nbvie);
	}

	public static void dessinMuretHApresCollision(int x, int y, int nbvie) {
		l.dessineMuretHorizontal(x, y);
		l.affiche();
		afficherNbrVie(l.psn, nbvie);
	}

	public static boolean dessinPsnApresDeplacement(char d, int nbrVies, int la) {
		l.effacePersonnage(l.psn);
		switch (d) {
		case 'd':
			l.psn.perDroite();
			break;
		case 'g':
			l.psn.PerGauche();
			break;
		case 'h':
			l.psn.perHaut();
			break;
		case 'b':
			l.psn.perBas();
			break;
		}
		if (partieGagnee(la - 1)) {
			int nbrFautes = nbrVies - l.psn.getNbrVie();
			System.out
					.println("Bravo, vous êtes parvenu jusqu'à la sortie en comettant seulement "
							+ nbrFautes + " erreurs.");
			return true;
		} else {
			l.dessinePersonnage(l.psn);
			l.affiche();
			return false;
		}

	}

	public static boolean partieGagnee(int largeur) {
		if (l.psn.getY() > largeur)
			return true;
		else
			return false;
	}

	public static void rendreVisible() {
		int x = l.psn.getX();
		int y = l.psn.getY();
		int nbrVie = l.psn.getNbrVie();

		Personnage pnAefacer = new Personnage(labyOriginal,
				labyOriginal.psn.getX(), labyOriginal.psn.getY(), nbrVie);

		l = new Labyrinthe(Laby.labyOriginal);
		l.psn.setNb_vie(nbrVie);
		l.psn.setX(x);
		l.psn.setY(y);

		l.effacePersonnage(pnAefacer);
		l.dessinePersonnage(l.psn);
		l.affiche();
	}

	

	public static void iA(Labyrinthe lab, int nbrVies, int la) {
		rendreVisible();
		while (true) {
			char[] tabnextdir = new char[4];
			if (!l.muretVerticalExiste(l.psn.getX(), l.psn.getY() + 1))
				tabnextdir[0] = 'd';
			if (!l.muretVerticalExiste(l.psn.getX(), l.psn.getY()))
				tabnextdir[1] = 'g';
			if (!l.muretHorizontalExiste(l.psn.getX(), l.psn.getY()))
				tabnextdir[2] = 'h';
			if (!l.muretHorizontalExiste(l.psn.getX() + 1, l.psn.getY()))
				tabnextdir[3] = 'b';

			// choose a random direction
			Random r = new Random();
			int rst = r.nextInt(tabnextdir.length);

			sleep(10);
			if (tabnextdir[rst] != 'd' && tabnextdir[rst] != 'g'
					&& tabnextdir[rst] != 'h' && tabnextdir[rst] != 'b')
				continue;
			if (dessinPsnApresDeplacement(tabnextdir[rst], nbrVies, la))
				break;

		}

	}

	public static void sleep(long millisecondes) {
		try {
			Thread.sleep(millisecondes);
		} catch (InterruptedException e) {
			System.out.println("Sleep interrompu");
		}
	}

	public static void main(String[] args) {

		if (args.length == 5) {
			Laby.jeuLabyrintheInvisible(Integer.parseInt(args[0]),
					Integer.parseInt(args[1]), new Double(args[2]),
					Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			if (quitterPgm)
				System.exit(0);
			while (nvlPartie) {
				nvlPartie = false;
				Laby.jeuLabyrintheInvisible(Integer.parseInt(args[0]),
						Integer.parseInt(args[1]), new Double(args[2]),
						Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			}
			while (true) {
				System.out
						.println("Voulez-vous jouer une nouvelle partie? oui/non");
				Scanner input = new Scanner(System.in);
				String in = input.nextLine();
				if (in.equals("non"))
					break;
				else if (in.equals("oui")) {
					Laby.jeuLabyrintheInvisible(Integer.parseInt(args[0]),
							Integer.parseInt(args[1]), new Double(args[2]),
							Integer.parseInt(args[3]),
							Integer.parseInt(args[4]));
					if (quitterPgm)
						System.exit(0);
					while (nvlPartie) {
						nvlPartie = false;
						Laby.jeuLabyrintheInvisible(Integer.parseInt(args[0]),
								Integer.parseInt(args[1]), new Double(args[2]),
								Integer.parseInt(args[3]),
								Integer.parseInt(args[4]));
					}
					continue;
				} else
					continue;
			}

			System.exit(0);

		} else {
			System.out
					.println("Nombre de paramètres incorrects.\n "
							+ "Utilisation: java Laby <hauteur> <largeur> <densite> <duree visible> <nb vies>\n"
							+ "Ex: java Laby 10 20 0.20 10 5");

		}
	}

}