package com.devorsolutions.abiobjekt;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class DisplayAll extends Activity{

	private ExpandableListView lv;
	private List<Menschen> listMenschen;
	private ObjectHandler oh;

	private Button bSearch;
	private EditText etSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_all);
		lv = (ExpandableListView) findViewById(R.id.all_list);
		bSearch = (Button) findViewById(R.id.allSearchB);
		etSearch = (EditText) findViewById(R.id.allSearchEt);
		onClickListener(bSearch);
		onListClickListener();
		oh = new ObjectHandler();
		listMenschen = oh.getList();
		
		Bundle b = getIntent().getExtras();
		if (b != null){
			String searchCountry = b.getString("country");
			listMenschen = oh.findCitizans(searchCountry);
			if (listMenschen.size() == 0){
				//empty nothing found
				finish();
				Toast.makeText(this, "Keine Menschen mit der Herkunft", Toast.LENGTH_SHORT).show();
			} 
		}
		showAllList(listMenschen);
	}

	private void onListClickListener() {
		// Child Click is Best friend
		lv.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Menschen nowMensch  = listMenschen.get(groupPosition);
				Log.d("click info", String.valueOf(childPosition)+" - " + nowMensch.sizeExtras());
				if (childPosition +1 == nowMensch.sizeExtras() && nowMensch.hasFriend()){
					List<Menschen> oneManList =	new  ArrayList<Menschen>();
					oneManList.add(nowMensch.getBestFriend());
					listMenschen = oneManList;
					showAllList(oneManList);
					lv.expandGroup(0, true);
				} else if (childPosition  == nowMensch.sizeExtras()){
					Intent intentMensch = new Intent(DisplayAll.this, AddMensch.class);
					intentMensch.putExtra("editName", nowMensch.getVorname()+" " + nowMensch.getNachname());
					startActivity(intentMensch);
				}
				return false;
			}
		});

		etSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() == 0){
					bSearch.setText("Reset");
				}else {
					bSearch.setText("Suchen");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void onClickListener(Button b) {
		// TODO Auto-generated method stub
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etSearch.length()!= 0){
					String searchTerm = etSearch.getText().toString();
					List<Menschen> resultList =	oh.findMensch(searchTerm);
					Log.d("Treffer List", resultList.toString());
					if (resultList.size()>0){
						listMenschen = resultList;
						showAllList(listMenschen);
					} else {
						//no one found
						Toast.makeText(DisplayAll.this, "Kein Mensch gefunden", Toast.LENGTH_SHORT).show();
					}

				} else {
					Log.d("OUTPUT ALL", listMenschen.toString());
					listMenschen =  oh.getList();
					showAllList(listMenschen);
				}
			}
		});
	}



	private void showAllList(List<Menschen> inList) {
		lv.setAdapter(new LvAdapter(this,inList));
	}
}
