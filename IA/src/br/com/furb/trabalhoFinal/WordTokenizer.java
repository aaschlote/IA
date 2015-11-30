package br.com.furb.trabalhoFinal;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;

import ptstemmer.Stemmer;
import ptstemmer.implementations.OrengoStemmer;
import ptstemmer.implementations.PorterStemmer;
import ptstemmer.implementations.SavoyStemmer;

public class WordTokenizer {
	
	private static boolean removeNumbersAndSingleLetters = false;
	private StopWords stopWords = new StopWords();
	private Stemmer stemmer;

	public Collection<Word> getWords(String value) throws Exception {
		
		stemmer = new OrengoStemmer();
		
		String temp = Normalizer.normalize(value, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");   
		value = temp.replaceAll("[^\\p{ASCII}]",""); 
		
		String[] wordStrs = value.split("\\s");

		ArrayList<Word> words = new ArrayList<Word>();
		for (String word : wordStrs) {

			if ((removeNumbersAndSingleLetters && word.length() > 1 && !word.matches("[0-9]+"))
					|| (!removeNumbersAndSingleLetters && word.length() > 0)) {
				if	(!stopWords.isStopWord(word)){
					word = word.replace(".", "");
					word = word.replace(",", "");
					word = word.replace(")", "");
					word = word.replace("(", "");
					word = word.replace("/", "");
					words.add(new Word(stemmer.getWordStem(word)));
				}
				
			}

		}
		return words;

	}

}
