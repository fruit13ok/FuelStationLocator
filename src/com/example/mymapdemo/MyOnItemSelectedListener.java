package com.example.mymapdemo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
/*
 * respond to user selection
 */
public class MyOnItemSelectedListener implements OnItemSelectedListener
{

	@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
		// An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		Toast.makeText(parent.getContext(), 
				"OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
				Toast.LENGTH_SHORT).show();
    }

	@Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }

}
