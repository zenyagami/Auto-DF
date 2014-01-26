package com.hackdf.autochilango.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hackdf.autochilango.R;
import com.hackdf.autochilango.service.ServiceBluetoothReceiver;

public class FragmentBluetooth extends Fragment implements android.view.View.OnClickListener{

	
	int i=1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_llaves, null);
		((ImageButton)main.findViewById(R.id.bLlaves)).setOnClickListener(this);	
		return main;

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bLlaves:
			getActivity().sendBroadcast(new Intent(ServiceBluetoothReceiver.SEND_NOTIFICATION_BLUETOOTH).putExtra("pos", i) );
			if(i==1)
			{
				i=2;
			}else
			{
				i=1;
			}
			break;

		default:
			break;
		}
	}



}
