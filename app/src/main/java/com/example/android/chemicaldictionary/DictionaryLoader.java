package com.example.android.chemicaldictionary;

import java.io.BufferedReader;


import java.util.ArrayList;




/*
 *In the name of Allah the Most Merciful.
 * Author
 * Md. Toufiqul Islam
 * Dept. Of CSE
 * Ahsanullah University Of Science And Technology
 */


					

public class DictionaryLoader {

    public static  void LoadData(BufferedReader bufferedReader, DictionaryDatabaseHelper dictionaryDatabaseHelper){

        ArrayList<WordDefinition> allWords = new ArrayList<WordDefinition>();

        try{

              BufferedReader filereader = bufferedReader;
            try{
                int c=17;
                c= filereader.read();
                while(c!=-1){
                   StringBuilder stringBuilder = new StringBuilder();
                    while((char) c!='\n' && c!=-1 && c!='\t'){

                        try{
                            stringBuilder.append((char)c);
                        }catch (Exception e){

                        }
                        c=filereader.read();

                        if(c==-1){
                            return;
                        }



                    }

                    String wordString = stringBuilder.toString();

                    ArrayList<String> definition = new ArrayList<String>();

                    while(c=='\n' ||c=='\t'){

                        c=filereader.read();
                        if(c=='\n' || c=='\t' || c=='\r'){
                            StringBuilder stringBuilder2 = new StringBuilder();
                            while(c!='\n'){
                               stringBuilder2.append((char)c);
                                c=filereader.read();
                            }

                            String definitionString = stringBuilder2.toString();
                            definition.add(definitionString);
                        }
                        else
                        {
                            break;
                        }

                    }

                    wordString = wordString.trim();
                    allWords.add(new WordDefinition(wordString,definition));

                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                dictionaryDatabaseHelper.initalizeForTheFirstTime(allWords);
                filereader.close();
            }
            catch (Exception e){
                e.printStackTrace();

            }




        }catch (Exception e){
         e.printStackTrace();
        }


    }
}
