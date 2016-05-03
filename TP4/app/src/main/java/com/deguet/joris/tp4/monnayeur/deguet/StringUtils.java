package com.deguet.joris.tp4.monnayeur.deguet;

/**
 * Classe permettant d'afficher facilement une caisse ou du change dans la console
 * @author joris
 *
 */
public class StringUtils {

	public static String toString(TiroirArgent tiroir){
		String result = "Tiroir ::: \n";
		for (ArgentPhysique m : ArgentPhysique.values()){
			result += "  "+String.format("%10s", tiroir.nombreItemsPour(m)+"")+"    @    "+m.nomLisible()+"\n";
		}
		return result;
	}

	public static String toString(Change change){
		String result = "Change ::: \n";
		for (ArgentPhysique m : ArgentPhysique.values()){
			if (change.nombreItemsPour(m) > 0){
				result += "  "+String.format("%10s", change.nombreItemsPour(m)+"")+"    @    "+m.nomLisible()+"\n";
			}
		}
		return result;
	}

}
