/* Programme traduisant de français à javanais et inversement
 * 
 * La transformation d'une phrase de français à javanais se fait selon les règles suivantes :
 * - Dans un mot, “av” est ajouté entre chaque consonne suivie d’une voyelle.
 * - Si le mot commence par une voyelle, “av” est ajouté devant cette voyelle.
 * Le programme ne prend pas en compte les mots incluant des chiffres tels que plaques d'immatriculations, numéros de série
 *
 */

public class Javanais {
	//VARIABLES GLOBALES
	static char [] voyelles = {'a','e','i','o','u','A','E','I','O','U'}; //Liste des voyelles
	static String particule = "av"; //Lettres à insérer
	static int lpart = particule.length(); //Longueur de la particule


	//FONCTION MAIN
	public static void main(String[] args) {
	
		//VERIFICATIONS DE SECURITE
		if (args.length == 0 || (args.length > 1 && !(args[1].equals("fr") || args[1].equals("javanais")))) { 
			//Vérifie qu'au moins un argument est rentré, et s'il y en a plusieurs qu'ils correspondent aux valeurs attendues
			System.out.println("Rentrez les arguments comme suis : \n 1- Phrase à traduire entre guillemets \n 2- 'fr' ou 'javanais' pour choisir vers quoi traduire, javanais en défaut si rien spécifié");
			System.exit(1); //Termine le programme avec erreur
		} 
		
		if (args.length > 1 && args[1].equals("fr")) //Si l'utilisateur a spécifié le sens français vers javanais
			System.out.println(javanaisVersFr(args[0])); //Exécute la traduction et l'affiche
		else
			System.out.println(frVersJavanais(args[0])); //Sinon on exécute l'autre sens de traduction
	} 
	
	
	//FONCTIONS
	//Fonction transformant une phrase de français à javanais
	public static String frVersJavanais (String s) {
		//Variables
		boolean [] estVoyelle = {false,false}; //Indique si le caractère en cours et le précédent sont des voyelles
		String reponse = ""; //La phrase traduite
		
		for (int i=0, l=s.length(); i<l ; i++) { //Pour chaque caractère de la phrase
			estVoyelle[0] = verifierVoyelle(s.charAt(i)); //Est-ce que le caractère en cours est une voyelle ?
			if (estVoyelle[0] && estVoyelle[0] != estVoyelle[1]) //Si oui et que le caractère précédent n'en est pas une
				reponse += particule; //On ajoute la particule avant la voyelle
			reponse += s.charAt(i); //Dans tous les cas, on ajoute le caractère en cours
			estVoyelle[1] = estVoyelle[0]; //Le caractère actuel devient le précédent
		}
		
		return reponse; //Renvoie la réponse
	}							
	
	
	//Fonction transformant une phrase de javanais à français
	public static String javanaisVersFr (String s) {
		//Variables
		String reponse = ""; //La phrase traduite
		
		for (int i=0, l=s.length(); i<l; i++) { //Pour chaque caractère de la phrase
			if (i+lpart+1 < l) { //Tant qu'on a la place d'avoir un motif
				if (verifierMotif(s,i)) { //Si le motif est présent
					reponse = reponse + s.charAt(i) + s.charAt(i+lpart+1); //On écrit la premiere et derniere lettre du motif
					i = i + lpart + 1; //On passe directement à la suite de la chaîne
					continue; //On passe à l'itération suivante
				}
			}
			reponse += s.charAt(i); //Si le motif n'est pas trouvé, on écrit la lettre en cours
		}
		
		return reponse; //Renvoie la réponse
	}
	
	
	//Fonction vérifiant si un caractère est une voyelle
	public static boolean verifierVoyelle (char c) {
		for (int i=0, l=voyelles.length; i < l ; i++) { //On parcourt le tableau des voyelles reconnues
			if (c == voyelles[i]) //Si le caractère à tester est une voyelle, on retourne Vrai
				return true;
		}
		return false; //Sinon, on retourne Faux
	}
	
	
	//Fonction vérifiant si une chaîne est un motif "consonne + particule + voyelle"
	public static boolean verifierMotif (String s, int i) {
		if (!verifierVoyelle(s.charAt(i))) { //Si on commence bien par une consonne
			for (int j=0, l=lpart; j<l; j++) { //Pour toutes les lettres de la particule ('a' et 'v' pour le javanais)
				if (s.charAt(i+j+1) != particule.charAt(j)) //Si on a une seule différence
					return false; //On retourne Faux
			}
			if (verifierVoyelle(s.charAt(i+lpart+1))) //Si on finit par une voyelle
				return true; //On a trouvé un motif, et on retourne Vrai
			return false; //Sinon on retourne Faux
		}
		return false; //Sinon on retourne Faux
	}
}


