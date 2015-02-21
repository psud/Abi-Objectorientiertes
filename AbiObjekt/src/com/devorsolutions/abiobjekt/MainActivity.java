package com.devorsolutions.abiobjekt;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity implements OnClickListener {

	private Button bNewMensch;
	private Button bDisplayAll;

	private Button bSearchPeople;
	private AutoCompleteTextView etSearchPeople;
	private Button bSearchCountry;
	private EditText etSearchCountry;


	private ObjectHandler oh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		bNewMensch = (Button) findViewById(R.id.mainAdd);
		bDisplayAll = (Button) findViewById(R.id.mainDisplay);
		bSearchPeople = (Button) findViewById(R.id.startEditGo);
		etSearchPeople = (AutoCompleteTextView) findViewById(R.id.startEditEt);
		bSearchCountry = (Button) findViewById(R.id.startCountryGo);
		etSearchCountry = (EditText) findViewById(R.id.startCountry);

		bSearchPeople.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (etSearchPeople.length()>0){
					Intent intentMensch = new Intent(MainActivity.this, AddMensch.class);
					intentMensch.putExtra("editName", etSearchPeople.getText().toString().trim());
					startActivity(intentMensch);
				} 
			}
		});

		bSearchCountry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (etSearchCountry.length()>0){
					Intent intentList = new Intent(MainActivity.this, DisplayAll.class);
					intentList.putExtra("country", etSearchCountry.getText().toString());
					startActivity(intentList);
				}
			}
			}); 
		
		oh = new ObjectHandler();

		bNewMensch.setOnClickListener(this);
		bDisplayAll.setOnClickListener(this);

		addSomePeople();


		makeAutoTV();
		}



		private void addSomePeople() {
			Menschen crazyMan = new Menschen("Patrick", "Sudhaus","29.02.1996","Deutschland");
			Menschen juliaMan = new Menschen("Julia", "Hanselmann","12.09.1998","Deutschland");
			crazyMan.setBestFriend(juliaMan);
			crazyMan.setGewicht(30.5);
			oh.addMensch(crazyMan);
			oh.addMensch(juliaMan);

			oh.addMensch(new Menschen("Nick", "Sudhaus","10.06.1993","Deutschland"));
			oh.addMensch(new Menschen("Michael", "Sudhaus","13.12.2000","Deutschland"));
			oh.addMensch(new Menschen("Sam", "Sprague","12.10.2006","USA"));
			oh.addMensch(new Menschen("Laura", "Sprague","10.11.1966","USA"));
			oh.addMensch(new Menschen("Bennett", "Sprague","10.11.1980","USA"));
			oh.addMensch(new Menschen("Mila", "Sudhaus","4.2.1979","Österreich"));
			oh.addMensch(new Menschen("Felix", "Krauspe","27.12.1995","Deutschland"));
			oh.addMensch(new Menschen("Lucas", "Pisscator","5.05.1996","Deutschland"));
			oh.addMensch(new Menschen("Tim", "Pisscator","10.08.1993","Deutschland"));
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
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.mainAdd:
				Intent i = new Intent(this,AddMensch.class);
				startActivity(i);
				break;
			case R.id.mainDisplay:
				//bDisplayAll.setText(oh.allMenschenToStr());
				Intent i1 = new Intent(this,DisplayAll.class);
				startActivity(i1);
				break;
			}
		}




		private void makeAutoTV() {
			List<String> nameList = new ArrayList<String>();
			List<Menschen> menschenList = oh.getList();
			for (Menschen m : menschenList){
				nameList.add(m.getVorname() + " " + m.getNachname());
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line,nameList);
			etSearchPeople.setAdapter(adapter);
		}
	}
