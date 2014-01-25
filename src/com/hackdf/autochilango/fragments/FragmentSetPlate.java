package com.hackdf.autochilango.fragments;

import com.hackdf.autochilango.R;
import com.hackdf.autochilango.entities.Car;
import com.hackdf.autochilango.utils.Dialogs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentSetPlate extends Fragment implements OnClickListener {

	private GetPlateInfo getPlateInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_set_current_plate, null);
		((Button) v.findViewById(R.id.btnAcceptPlate)).setOnClickListener(this);
		((Button) v.findViewById(R.id.btnCanelPlate)).setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCanelPlate:

			break;
		case R.id.btnAcceptPlate:
			// manda a llamar API que obtiene la info de la placa :)
			if (getPlateInfo == null) {
				getPlateInfo = new GetPlateInfo();
				getPlateInfo.execute("");

			}
			break;
		default:
			break;
		}

	}

	private class GetPlateInfo extends AsyncTask<String, Void, Car> {

		@Override
		protected Car doInBackground(String... params) {

			try {
				Thread.sleep(1000 * 4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Car result) {
			if (getActivity() == null || isDetached() || !isAdded()) {
				return;
			}
			getPlateInfo=null;
			Dialogs.dismissLoadingDialog(getActivity());

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Dialogs.showDialog(getActivity());
		}

	}

}
