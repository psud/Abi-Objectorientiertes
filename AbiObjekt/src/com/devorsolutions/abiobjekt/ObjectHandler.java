package com.devorsolutions.abiobjekt;

import java.util.ArrayList;
import java.util.List;

public class ObjectHandler {
	
	private static List<Menschen> listMenschen = new ArrayList<Menschen>();
	
	public ObjectHandler(){
		
	}
	
	
	public void addMensch(Menschen newMensch) {
	       listMenschen.add(newMensch);
	}
	public void updateMensch(Menschen oldMensch, Menschen newMensch) {
			listMenschen.remove(oldMensch);
	       listMenschen.add(newMensch);
	}
	
	
	public List<Menschen> findMensch(String name){
		List<Menschen> treffer = new ArrayList<Menschen>();
		for (Menschen m : listMenschen){
			if(similarity(m.getNachname(), name)>=0.8 || similarity(m.getVorname(), name)>=0.8 || similarity(m.getVorname() + " " +m.getNachname(), name)>=0.9){
				treffer.add(m);
			}
		}
		
		return treffer;
	}
	
	public List<Menschen> findCitizans(String searchCountry) {
		// TODO Auto-generated method stub
		List<Menschen> treffer = new ArrayList<Menschen>();
		for (Menschen m : listMenschen){
			if(similarity(m.getHerkunft(), searchCountry)>=0.8){
				treffer.add(m);
			}
		}
		return treffer;
	}
	
	public Menschen findTopMensch(String name){
		List<Menschen> treffer = findMensch(name);
		if (treffer.size()>0){
			return treffer.get(0);
		}
		else {
			return null;
		}
	}
	
	
	public List<Menschen> getList(){
//		Toast.makeText(this, ":MAIN:\n"+listMenschen.get(0).toString(), Toast.LENGTH_LONG).show();
		return listMenschen;
	}
	
	public String allMenschenToStr(){
		StringBuilder str = new StringBuilder();
    	for (Menschen m : listMenschen){
    		str.append(m.toString());
    		str.append("\n");
    	}
    	return str.toString();
	}
	
	
	///////SIMILARITY
	/**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        /* // If you have StringUtils, you can use it to calculate the edit distance:
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                                                             (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
 
    }
    
 // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
 
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }


	
    
}
