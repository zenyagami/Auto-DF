package com.hackdf.autochilango.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import com.hackdf.autochilango.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ServiceBluetoothReceiver extends Service{
	public static final String CLOSE_NOTIFICATION_BLUETOOTH ="com.hackdf.chilango.STOP_NOTIFICATION";
	public static final String SEND_NOTIFICATION_BLUETOOTH ="com.hackdf.chilango.SEND_NOTIFICATION";
	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket mmSocket;
	BluetoothDevice mmDevice;
	OutputStream mmOutputStream;
	InputStream mmInputStream;
	Thread workerThread;
	byte[] readBuffer;
	int readBufferPosition;
	int counter;
	
	private BroadcastReceiver mReceiver= new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				int i =intent.getExtras().getInt("pos");
				sendData(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	private NotificationManager mNotificationManager ;
	volatile boolean stopWorker;
	Handler handler = new Handler();
	Runnable run = new Runnable() {
		
		@Override
		public void run() {
			//loop hasta que encontremos el BT
			if(mmDevice==null)
			{
				findBT();
				handler.postDelayed(run, 1000*3);
			}else
			{
				//empezamos la escucha de data ;D
				try {
					handler.removeCallbacks(run);
					openBT();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(handler!=null)
		{
			handler.removeCallbacks(run);
		}
		unregisterReceiver(mReceiver);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler.post(run);
		mNotificationManager =(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		IntentFilter ifil= new IntentFilter(SEND_NOTIFICATION_BLUETOOTH);
		getApplicationContext().registerReceiver(mReceiver, ifil);
	}
	public void findBT() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Log.i("Bluetooth", "No bluetooth adapter available");
		}

		if (!mBluetoothAdapter.isEnabled()) {
			Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(btIntent);
		}

		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) 
			{
				Log.i("Nombre de dispositivo", device.getName());
				if (device.getName().equals("ELEAZAR") || device.getName().equalsIgnoreCase(" ELEAZAR" )) {
					mmDevice = device;
					break;
				}
			}
		}
		Log.i("Bluetooth", "Bluetooth Device Found");
	}

	void openBT() throws IOException {
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
																				// //SerialPortService
																				// ID
		mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
		mmSocket.connect();
		if(!mmSocket.isConnected())
		{
			mmDevice=null;
			handler.post(run);
			return;
		}
		   new Handler().post(new Runnable() {
		        @Override
		        public void run() {
		             Toast.makeText(getApplicationContext(),"Bluetooth Conectado",Toast.LENGTH_SHORT).show();
		        }
		    });
		mmOutputStream = mmSocket.getOutputStream();
		mmInputStream = mmSocket.getInputStream();
		beginListenForData();
//		Log.i("bluetooth", "Bluetooth Opened");
	}
	private void setNotification()
	{
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
		mBuilder.setSmallIcon(R.drawable.ic_action_not_secure);
		mBuilder.setContentTitle("Peligro!!!!!");
		mBuilder.setContentText("Ha pasado algo con tu auto!!!!!!");
		PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 100,new Intent(CLOSE_NOTIFICATION_BLUETOOTH) , PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pi);
		Notification notif = mBuilder.build();
		notif.flags =Notification.FLAG_AUTO_CANCEL;
		notif.defaults |=Notification.DEFAULT_VIBRATE;
    	notif.defaults |=Notification.DEFAULT_LIGHTS;
    	notif.defaults |=Notification.DEFAULT_SOUND;
		mNotificationManager.notify(10, notif);
	}
	

	void beginListenForData() {
		//final Handler handler = new Handler();
		final byte delimiter = 80; // This is the ASCII code for a newline
									// character

		stopWorker = false;
		readBufferPosition = 0;
		readBuffer = new byte[1024];
		workerThread = new Thread(new Runnable() {
			public void run() {
				while (!Thread.currentThread().isInterrupted() && !stopWorker) {
					try {
						int bytesAvailable = mmInputStream.available();
						if (bytesAvailable > 0) {
							
							byte[] packetBytes = new byte[bytesAvailable];
							mmInputStream.read(packetBytes);
							for (int i = 0; i < bytesAvailable; i++) {
								byte b = packetBytes[i];
								if (b == delimiter) {
									//llego algo del BT mostramos Notif
									setNotification();
									
									/*byte[] encodedBytes = new byte[readBufferPosition];
									System.arraycopy(readBuffer, 0,
											encodedBytes, 0,
											encodedBytes.length);
									final String data = new String(
											encodedBytes, "US-ASCII");
									readBufferPosition = 0;

									handler.post(new Runnable() {
										public void run() {
											Log.i("bluetooth ", data);
										}
									});*/
								} else {
									readBuffer[readBufferPosition++] = b;
								}
							}
						}
					} catch (IOException ex) {
						stopWorker = true;
					}
				}
			}
		});

		workerThread.start();
	}
	void sendData(int i) throws IOException {
		// String msg = myTextbox.getText().toString();
		// msg += "\n";
		if(mmOutputStream==null)
		{
			return;
		}
		switch (i) {
		case 1://Prender
			byte[] mBuffer = new byte[1];
	    	mBuffer[0] = (byte)'e';
			 mmOutputStream.write(mBuffer);
			i=2;
			break;
		case 2://Apagar
			byte[] mBuffer1 = new byte[1];
	    	mBuffer1[0] = (byte)'a';
			 mmOutputStream.write(mBuffer1);
			i=1;
			break;

		default:
			break;
		}
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}


}
