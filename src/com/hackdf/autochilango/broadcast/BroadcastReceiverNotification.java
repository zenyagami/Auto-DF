package com.hackdf.autochilango.broadcast;

import com.hackdf.autochilango.service.ServiceBluetoothReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverNotification extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		context.sendBroadcast(new Intent(ServiceBluetoothReceiver.SEND_NOTIFICATION_BLUETOOTH));
	}

}
