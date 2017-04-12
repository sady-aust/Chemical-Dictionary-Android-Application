package com.example.android.chemicaldictionary;

import java.util.ArrayList;

/*
 *In the name of Allah the Most Merciful.
 * Author
 * Md. Toufiqul Islam
 * Dept. Of CSE
 * Ahsanullah University Of Science And Technology
 */


					

public class WordDefinition {

    String word,definition;


    public WordDefinition(String word, ArrayList<String> alldefinition){
        this.word = word;
        StringBuilder stringBuilder = new StringBuilder();

        for(String srting : alldefinition){

            stringBuilder.append(srting);
        }

        this.definition = stringBuilder.toString();
    }


    public WordDefinition(String word,String alldefinition){
        this.word = word;
        this.definition = alldefinition;
    }

    public WordDefinition(String word){
        this.word = word;
    }
}
