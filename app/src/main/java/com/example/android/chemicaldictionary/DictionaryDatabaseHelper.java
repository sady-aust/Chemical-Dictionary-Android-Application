package com.example.android.chemicaldictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by rafiqul islam on 11/29/2016.
 */

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {

    final static  String DICTIONARY_DATABSE = "dictionary";
    final static String ITEM_ID_COLUMN = "id";
    final static String WORD_COLUMN ="word";
    final static String DEFINITION_COLUMN = "definition";

    final static String CREATE_DATABASE_QUERY ="CREATE TABLE "+DICTIONARY_DATABSE+" ( "+
            ITEM_ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+WORD_COLUMN+" TEXT, "+DEFINITION_COLUMN+" TEXT)";

    final static String ON_UPGRADE_QUERY="DROP TABLE "+DICTIONARY_DATABSE;



    Context context;


    public DictionaryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DICTIONARY_DATABSE, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_DATABASE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ON_UPGRADE_QUERY);
        onCreate(db);

    }

    public long insertData(WordDefinition wordDefinition){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COLUMN,wordDefinition.word);
        values.put(DEFINITION_COLUMN,wordDefinition.definition);
        return database.insert(DICTIONARY_DATABSE,null,values);

    }

    public long updateData(WordDefinition wordDefinition){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COLUMN,wordDefinition.word);
        values.put(DEFINITION_COLUMN,wordDefinition.definition);

        return  database.update(DICTIONARY_DATABSE,values, WORD_COLUMN+" =?",new String[]{wordDefinition.word});
    }

    public void deleteData(WordDefinition wordDefinition){
      SQLiteDatabase database = this.getWritableDatabase();

        String queryString ="DELETE FROM "+DICTIONARY_DATABSE+" WHERE "+WORD_COLUMN+" ='"+wordDefinition.word+"'";

        database.execSQL(queryString);

    }

    public ArrayList<WordDefinition> getAllWords(){
              ArrayList<WordDefinition> arrayList = new ArrayList<WordDefinition>();

        SQLiteDatabase database= this.getReadableDatabase();

        String selectStringQuery = "SELECT * FROM "+DICTIONARY_DATABSE;
        Cursor cursor = database.rawQuery(selectStringQuery,null);

        if(cursor.moveToFirst()){

            do{
                WordDefinition wordDefinition = new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)),cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));
                arrayList.add(wordDefinition);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    public String[] getALlWordsFromWordCoulmn(){

        SQLiteDatabase database = this.getReadableDatabase();
        String selectStringQuary = "SELECT "+WORD_COLUMN+" FROM "+DICTIONARY_DATABSE;
        Cursor cursor = database.rawQuery(selectStringQuary,null);
        String[] allwords = new String[cursor.getCount()];
            int i=0;
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            allwords[i] = cursor.getString(0);
            i++;
            cursor.moveToNext();

        }
        return  allwords;
    }



    public WordDefinition getWordDefinition(String word){

        SQLiteDatabase database = this.getReadableDatabase();
        WordDefinition wordDefinition = null;

        String selectQueryString ="SELECT * FROM "+DICTIONARY_DATABSE+ " WHERE "+WORD_COLUMN+" ='"+word +"'";
        Cursor cursor = database.rawQuery(selectQueryString,null);


        if(cursor.moveToFirst()){
            wordDefinition = new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)),cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));

        }
        return wordDefinition;
    }

    public WordDefinition getWordDefinition(Long id){
          SQLiteDatabase database = this.getReadableDatabase();
        WordDefinition wordDefinition = null;
        String selectQueryString = "SELECT * FROM "+DICTIONARY_DATABSE+" WHERE "+ITEM_ID_COLUMN+" ='"+id+"'";
        Cursor cursor = database.rawQuery(selectQueryString,null);
        if(cursor.moveToFirst()){
            wordDefinition = new WordDefinition(cursor.getString(cursor.getColumnIndex(WORD_COLUMN)),cursor.getString(cursor.getColumnIndex(DEFINITION_COLUMN)));

        }

        return wordDefinition;
    }

    public void initalizeForTheFirstTime(ArrayList<WordDefinition> wordDefinitions){
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("BEGIN");

        ContentValues contentValues = new ContentValues();

        for(WordDefinition wordDefinition : wordDefinitions){

            contentValues.put(WORD_COLUMN,wordDefinition.word);
            contentValues.put(DEFINITION_COLUMN,wordDefinition.definition);
            database.insert(DICTIONARY_DATABSE,null,contentValues);


        }

        database.execSQL("COMMIT");


    }





}
