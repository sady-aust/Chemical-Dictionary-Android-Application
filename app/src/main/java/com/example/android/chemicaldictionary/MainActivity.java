package com.example.android.chemicaldictionary;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DictionaryDatabaseHelper myDictionaryDatabaseHelper;

   Button searchButton;
   AutoCompleteTextView nameEditText;
    TextView wordText,wordDefinitionText,masstext,appearnceText;
    ImageView image;
    ImageButton speechButton;
    TextToSpeech toSpeech;
    String first="";

    boolean isIniatialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDictionaryDatabaseHelper=new DictionaryDatabaseHelper(this, "Dictionary", null, 1);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // searchButton = (Button)findViewById(R.id.button);
        nameEditText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
       // wordText = (TextView)findViewById(R.id.wordText);
        wordDefinitionText =(TextView)findViewById(R.id.wordDefinitionText);
        masstext = (TextView)findViewById(R.id.textView2);
        image =(ImageView)findViewById(R.id.molImage) ;
        appearnceText  = (TextView)findViewById(R.id.textView3);
        speechButton = (ImageButton) findViewById(R.id.speech);
      //  searchButton.setVisibility();
      /*/  ImageSpan imageHint = new ImageSpan(this,R.drawable.searchicon);

        SpannableString hintString = new SpannableString("  Chemical Formula For Search");
        hintString.setSpan(imageHint,0,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);*/
        nameEditText.setHint("Chemical Component For Search");
        
        initializeDatabase();
        if(isIniatialized){
            Toast.makeText(this,"Dictionary Loaded",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Dictionary Not Loaded",Toast.LENGTH_LONG).show();
        }
        /*ArrayList<WordDefinition> allWordDefinitions=new ArrayList<WordDefinition>();
        allWordDefinitions=myDictionaryDatabaseHelper.getAllWords();

        String[] allwordsString = new String[allWordDefinitions.size()];
        allwordsString = allWordDefinitions.toArray(allwordsString);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, allwordsString);*/
     //   nameEditText.setAdapter(adapter);

       /* for(int i = 0; i < allwordsString.length ; i++){
            Log.d("string is",(String)allwordsString[i]);
        }*/
        String[] allWordsString = new String[3000];
        allWordsString = myDictionaryDatabaseHelper.getALlWordsFromWordCoulmn();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,allWordsString);
        nameEditText.setAdapter(adapter);


        toSpeech =  new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    toSpeech.setLanguage(Locale.US);
                }
            }
        });

        nameEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = nameEditText.getText().toString();
                WordDefinition wordDefinition =myDictionaryDatabaseHelper.getWordDefinition(string);
                //     String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Permanganate-anion-3D-balls.png/150px-Permanganate-anion-3D-balls.png";
                //
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

                if (wordDefinition == null) {
                    Toast.makeText(MainActivity.this,"Word Not Found",Toast.LENGTH_LONG).show();

                }
                else {
                    //  Toast.makeText(MainActivity.this,"Word  Found",Toast.LENGTH_LONG).show();
                    //   wordDefinitionText.setText(wordDefinition.definition);
                    String fullDefinition = wordDefinition.definition;
                    // String[] finalDefinition =fullDefinition.split("\t");
                    //    for(int i=0;i<finalDefinition.length;i++){
                    //wordDefinitionText.setText(finalDefinition[1]);
                    ///  Log.d("OUTPUT ",finalDefinition[1]);
                    //   }
                    String third = "";

                    String second = "";
                    String forth= "";
                    StringTokenizer tokens = new StringTokenizer(fullDefinition, "|");
                    while (tokens.hasMoreTokens()) {
                        first = tokens.nextToken();
                        first.trim();
                        wordDefinitionText.setText(first);
                        try {
                            second = tokens.nextToken();
                        }catch(Exception e ){
                            masstext.setText("---");
                        }
                        try {
                            third = tokens.nextToken();
                        }catch(Exception e ){
                            appearnceText.setText("kisu nai");
                        }
                       try{
                           forth = tokens.nextToken();
                       }catch(Exception e){

                       }



                    }
                //    speechButton.setVisibility(View.INVISIBLE);

                   // speechButton.setVisibility(View.VISIBLE);

                    second=second.trim();
                   try {
                       masstext.setText(second);

                   }catch(Exception e ){
                       masstext.setText("---*");
                   }


                    third = third.trim();
                    try {
                        appearnceText.setText(third);
                    }catch (Exception e ){
                        appearnceText.setText("kisu nai");
                    }

                   try {

                       loadimage(forth);
                   }catch(Exception e){
                       /*String uri = "@drawable/download.jpg";
                       int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                       Drawable res = getResources().getDrawable(imageResource);*/
                       image.setImageResource(R.drawable.download);
                   }

                }


            }
        });
        nameEditText.clearComposingText();


       speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = first;
                toSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
            }
        });


    }
    public void loadimage(String url){
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(image,new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }

    private void initializeDatabase() {

        InputStream inputStream = getResources().openRawResource(R.raw.names);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        DictionaryLoader.LoadData(bufferedReader,myDictionaryDatabaseHelper);
        isIniatialized=true;
    }
  /*  public void SearchButton(){
        String string = nameEditText.getText().toString();
        WordDefinition wordDefinition =myDictionaryDatabaseHelper.getWordDefinition(string);

        if (wordDefinition == null) {
            Toast.makeText(MainActivity.this,"Word Not Found",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(MainActivity.this,"Word  Found",Toast.LENGTH_LONG).show();
        }
    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.guideLine) {
            // Handle the camera action
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
