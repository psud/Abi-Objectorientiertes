package com.devorsolutions.abiobjekt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddMensch extends Activity{

	private ObjectHandler oh;

	private EditText vornameTxt ;
	private EditText nachnameTxt ;
	private EditText geburtsdatumTxt;
	private EditText gewichtTxt;
	private EditText herkunftTxt ;
	private AutoCompleteTextView etFriend;
	private Button save;

	private Menschen oldMensch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_mensch);
		oh = new ObjectHandler();
		initAddMensch();
		makeAutoTV();

		Bundle extras = getIntent().getExtras(); 

		if (extras != null) {
			String editName = extras.getString("editName");
			insertDataForEdit(editName);
			// and get whatever type user account id is
		}

	}

	private void insertDataForEdit(String editName) {
		// TODO Auto-generated method stub
		Menschen editMensch = oh.findTopMensch(editName);
		if (editMensch == null){
			//COunldnt find
			Toast.makeText(this, "Mensch not Found", Toast.LENGTH_SHORT).show();
			finish();
		}else {
			save.setText("Update Mensch");
			oldMensch = editMensch;
			vornameTxt.setText(editMensch.getVorname());
			nachnameTxt.setText(editMensch.getNachname());
			if (editMensch.hasGewicht()){
				gewichtTxt.setText(String.valueOf(editMensch.getGewicht()));
			}
			if (editMensch.hasGeburtsdatum()){
				geburtsdatumTxt.setText(editMensch.getGeburtsdatum());
			}
			if (editMensch.hasFriend()){
				Menschen bf = editMensch.getBestFriend();
				etFriend.setText(bf.getVorname()+" "+bf.getNachname());
			}
			if (editMensch.hasHerkunft()){
				herkunftTxt.setText(editMensch.getHerkunft());
			}
		}
	}

	private void makeAutoTV() {
		// TODO Auto-generated method stub

		List<String> nameList = new ArrayList<String>();
		List<Menschen> menschenList = oh.getList();
		for (Menschen m : menschenList){
			nameList.add(m.getVorname() + " " + m.getNachname());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,nameList);
		etFriend.setAdapter(adapter);
	}

	private void initAddMensch() {

		vornameTxt = (EditText) findViewById(R.id.enterVorname);
		nachnameTxt = (EditText) findViewById(R.id.enterNachname);
		geburtsdatumTxt = (EditText) findViewById(R.id.enterGebutsdatum);
		gewichtTxt = (EditText) findViewById(R.id.enterGewicht);
		herkunftTxt = (EditText) findViewById(R.id.enterHerkunft);
		etFriend = (AutoCompleteTextView) findViewById(R.id.enterFriend);
		save = (Button) findViewById(R.id.enterButton);

		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(notEmpty(vornameTxt) && notEmpty(nachnameTxt)){
					//Okay to Save
					String vorname = vornameTxt.getText().toString();
					String nachname = nachnameTxt.getText().toString();
					Menschen newMensch = new Menschen(vorname, nachname);
					if (notEmpty(geburtsdatumTxt)){
						newMensch.setGeburtsdatum(geburtsdatumTxt.getText().toString());
					}
					if (notEmpty(gewichtTxt)){
						newMensch.setGewicht(Double.parseDouble(gewichtTxt.getText().toString()));
					}
					if (notEmpty(herkunftTxt)){
						newMensch.setHerkunft(herkunftTxt.getText().toString());
					}
					if (notEmpty(etFriend)){
						String enterFriendName = etFriend.getText().toString();
						List<Menschen> bfList =  (oh.findMensch(enterFriendName));
						if (bfList.size() > 0){
							Menschen bf = bfList.get(0);
							newMensch.setBestFriend(bf);
						}
					}

					if(oldMensch == null){
						//New Mensch
						oh.addMensch(newMensch);
					}else {
						oh.updateMensch(oldMensch, newMensch);
					}
					//					emptyTextBoxes();
					finish();
					//mainactivity.outPutAllMenschen();
				} else {
				}
			}

			private void emptyTextBoxes() {
				vornameTxt.setText("");
				nachnameTxt.setText("");
				geburtsdatumTxt.setText("");
				gewichtTxt.setText("");
				herkunftTxt.setText("");
			}



			private boolean notEmpty(EditText editTextValue) {
				return editTextValue.length() != 0;
			}
		});


	}
}
