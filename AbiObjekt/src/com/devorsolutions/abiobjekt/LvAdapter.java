package com.devorsolutions.abiobjekt;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class LvAdapter extends BaseExpandableListAdapter {

	private Context c;
	private List<Menschen> menschen = new ArrayList<Menschen>();
	//private List<Menschen> subItems = new ArrayList<Menschen>();
	//	private List<String> topItems = new ArrayList<String>();
	//	private List<String> subItems = new ArrayList<String>();



	public LvAdapter(Context c, List<Menschen> menschen){//List<String> topItems, List<String> subItems) {
		this.c = c;
		this.menschen = menschen;
	}

	@Override
	public int getGroupCount() {
		// +1 for the Edit button as last item
		return menschen.size();
	}



	@Override
	public int getChildrenCount(int groupPosition) {
		Menschen thisM = menschen.get(groupPosition);
		return thisM.hasExtraData().size()+1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null){
			LayoutInflater inflater = (LayoutInflater)c.getSystemService
					(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_title, parent, false);
		}
		Menschen thisM = menschen.get(groupPosition);
		TextView item = (TextView) v.findViewById(R.id.item_title);
		item.setText(thisM.getVorname()+" "+thisM.getNachname());
		return v;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null){
			LayoutInflater inflater = (LayoutInflater)c.getSystemService
					(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item, parent, false);
		}
		Menschen thisM = menschen.get(groupPosition);
		TextView item = (TextView) v.findViewById(R.id.item_normal);

		List<String> hasItems = thisM.hasExtraData();
		if (childPosition < thisM.sizeExtras()){
			String nowItem = hasItems.get(childPosition);
			if (nowItem.equals("geburtsdatum")){
				item.setText("Geburtsdatum: " + String.valueOf(thisM.getGeburtsdatum()));
			} else if (nowItem.equals("gewicht")){
				item.setText("Gewicht: " + String.valueOf(thisM.getGewicht())+"kg");
			}else if (nowItem.equals("herkunft")){
				item.setText("Herkunft: " + thisM.getHerkunft());
			}else if (nowItem.equals("friend")){
				item.setText("Best Friend: "+thisM.getBestFriend().getVorname() +" "+ thisM.getBestFriend().getNachname());
			} 
		} else {
			item.setText("Edit Mensch");
		}

		return v;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		Menschen thisM = menschen.get(groupPosition);
		if (childPosition +1 == thisM.sizeExtras() && thisM.hasFriend()){
			return true;
		} else if (childPosition == thisM.sizeExtras() ){
			return true;
		}
		else {
			return false;
		}
	}

}
