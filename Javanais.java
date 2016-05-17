/* Programme traduisant de français à javanais et inversement
 * 
 * La transformation d'une phrase de français à javanais se fait 
 * selon les règles suivantes :
 * - Dans un mot, “av” est ajouté entre chaque consonne suivie d’une voyelle.
 * - Si le mot commence par une voyelle, “av” est ajouté devant cette voyelle.
 * Le programme ne prend pas en compte les mots incluant des chiffres tels que :
 * Plaques d'immatriculations, numéros de série, ...
 *
 */


public class Javanais {
	//VARIABLES GLOBALES
	//Voyelles reconnues
	static char [] voyelles = {'a','e','i','o','u','A','E','I','O','U'}; 
	static String particule = "av"; //Lettres à insérer
	static int lpart = particule.length(); //Longueur de la particule


	//FONCTION MAIN
	public static void main(String[] args) {
	
		//VERIFICATIONS DE SECURITE
		if (args.length == 0 || (args.length > 1 && 
		!(args[1].equals("fr") || args[1].equals("javanais")))) { 
			/* Vérifie qu'au moins un argument est rentré, et s'il y en a plusieurs 
			qu'ils correspondent aux valeurs attendues */
			System.out.println("Rentrez les arguments comme suis : \n"
			+ "1- Phrase à traduire entre guillemets \n"
			+ "2- 'fr' ou 'javanais' pour choisir vers quoi traduire, javanais en défaut si rien spécifié");
			System.exit(1); //Termine le programme avec erreur
		} 
		
		if (args.length > 1 && args[1].equals("fr")) //Si l'utilisateur spécifie javanais vers français
			System.out.println(javanaisVersFr(args[0])); //Exécute la traduction et l'affiche
		else
			System.out.println(frVersJavanais(args[0])); //Sinon exécute l'autre sens de traduction
	} 
	
	
	//FONCTIONS
	//Fonction transformant une phrase de français à javanais
	public static String frVersJavanais (String s) {
		//Variables
		/* Indique si le caractère en cours et le précédent sont des voyelles,
		initialisé à "faux" pour prendre en compte le début d'une phrase, qui n'est pas une voyelle */
		boolean [] estVoyelle = {false,false}; 
		String reponse = ""; //Phrase traduite
		
		for (int i=0, l=s.length(); i<l ; i++) { //Pour chaque caractère de la phrase
			estVoyelle[0] = verifierVoyelle(s.charAt(i)); //Le caractère en cours est une voyelle ?
			if (estVoyelle[0] && estVoyelle[0] != estVoyelle[1]) //Si oui et que le caractère précédent n'en est pas une
				reponse += particule; //Ajoute la particule avant la voyelle
			reponse += s.charAt(i); //Dans tous les cas, ajoute le caractère en cours
			estVoyelle[1] = estVoyelle[0]; //Le caractère actuel devient le précédent
		}
		
		return reponse;
	}							
	
	
	//Fonction transformant une phrase de javanais à français
	//Remplace les motifs "non voyelle + particule + voyelle" par "non voyelle + voyelle"
	public static String javanaisVersFr (String s) {
		//Variable
		String reponse = ""; //Phrase traduite
			
		for (int i=-1, l=s.length(); i<l; i++) { 
		//Pour chaque caractère de la phrase, -1 étant le caractère "début de phrase"
			if (i+lpart+1<l) {
			//S'il y a la place pout un motif (lpart si début de phrase, lpart+1 sinon)
				if (verifierMotif(s,i)) { //Si le motif est présent
					if (i==-1) //Si début de phrase
						reponse += s.charAt(lpart); //N'écrit que la dernière lettre du motif
					else 
						reponse = reponse + s.charAt(i) + s.charAt(i+lpart+1); //Sinon écrit la 1ere et dernière
					i += lpart + 1; //Passe directement à la suite de la chaîne
					continue; //Itération suivante
				}
			}
			if (i>=0) //Si on n'est pas au caractère "début de phrase"
				reponse += s.charAt(i); //Le motif n'est pas trouvé, on écrit la lettre en cours
		}
		return reponse;
	}
	
	
	//Fonction vérifiant si un caractère est une voyelle
	public static boolean verifierVoyelle (char c) {
		for (int i=0, l=voyelles.length; i < l ; i++) { //On parcourt le tableau des voyelles reconnues
			if (c == voyelles[i]) //Si le caractère à tester est une voyelle, on retourne Vrai
				return true;
		}
		return false;
	}
	
	
	//Fonction vérifiant si une chaîne est un motif "consonne + particule + voyelle"
	public static boolean verifierMotif (String s, int i) {
		if (i==-1 || !verifierVoyelle(s.charAt(i))) { //Si début de phrase ou non voyelle
			for (int j=0, l=lpart; j<l; j++) { //Pour toutes lettres de la particule ('a' et 'v' pour le javanais)
				if (s.charAt(i+j+1) != particule.charAt(j)) //Si il y a une différence
					return false;
			}
			if (verifierVoyelle(s.charAt(i+lpart+1))) //Si ça finit par une voyelle
				return true; //Motif trouvé
		}
		return false;
	}
}


