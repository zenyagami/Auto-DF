package com.hackdf.autochilango.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hackdf.autochilango.R;

public class FragmentBluetooth extends Fragment implements android.view.View.OnClickListener{

	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket mmSocket;
	BluetoothDevice mmDevice;
	OutputStream mmOutputStream;
	InputStream mmInputStream;
	Thread workerThread;
	byte[] readBuffer;
	int readBufferPosition;
	int counter;
	volatile boolean stopWorker;
	Context context;
	int i=1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View main = inflater.inflate(R.layout.fragment_llaves, null);
		context = main.getContext();
		((Button)main.findViewById(R.id.bLlaves)).setOnClickListener(this);	
		new TurnOnBlueTooth().execute();
		return main;

	}
	private class TurnOnBlueTooth extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			try {
				findBT();
				openBT();
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
	}

	public void findBT() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Log.i("Bluetooth", "No bluetooth adapter available");
		}

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBluetooth = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBluetooth, 0);
		}

		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) 
			{
				Log.i("Nombre de dispositivo", device.getName());
				if (device.getName().equals(" ELEAZAR")) {
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
		mmOutputStream = mmSocket.getOutputStream();
		mmInputStream = mmSocket.getInputStream();
		beginListenForData();
//		Log.i("bluetooth", "Bluetooth Opened");
	}

	void beginListenForData() {
		final Handler handler = new Handler();
		final byte delimiter = 10; // This is the ASCII code for a newline
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
									byte[] encodedBytes = new byte[readBufferPosition];
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
									});
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

	void sendData() throws IOException {
		// String msg = myTextbox.getText().toString();
		// msg += "\n";
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

	void closeBT() throws IOException {
		stopWorker = true;
		mmOutputStream.close();
		mmInputStream.close();
		mmSocket.close();
		Log.i("bluetooth", "Bluetooth Closed");
	}

	public void buscar(View v)
	{
		
	}
	class ByteQueue {
	    public ByteQueue(int size) {
	        mBuffer = new byte[size];
	    }

	    public int getBytesAvailable() {
	        synchronized(this) {
	            return mStoredBytes;
	        }
	    }

	    public int read(byte[] buffer, int offset, int length)
	        throws InterruptedException {
	        if (length + offset > buffer.length) {
	            throw
	                new IllegalArgumentException("length + offset > buffer.length");
	        }
	        if (length < 0) {
	            throw
	            new IllegalArgumentException("length < 0");

	        }
	        if (length == 0) {
	            return 0;
	        }
	        synchronized(this) {
	            while (mStoredBytes == 0) {
	                wait();
	            }
	            int totalRead = 0;
	            int bufferLength = mBuffer.length;
	            boolean wasFull = bufferLength == mStoredBytes;
	            while (length > 0 && mStoredBytes > 0) {
	                int oneRun = Math.min(bufferLength - mHead, mStoredBytes);
	                int bytesToCopy = Math.min(length, oneRun);
	                System.arraycopy(mBuffer, mHead, buffer, offset, bytesToCopy);
	                mHead += bytesToCopy;
	                if (mHead >= bufferLength) {
	                    mHead = 0;
	                }
	                mStoredBytes -= bytesToCopy;
	                length -= bytesToCopy;
	                offset += bytesToCopy;
	                totalRead += bytesToCopy;
	            }
	            if (wasFull) {
	                notify();
	            }
	            return totalRead;
	        }
	    }

	    public void write(byte[] buffer, int offset, int length)
	    throws InterruptedException {
	        if (length + offset > buffer.length) {
	            throw
	                new IllegalArgumentException("length + offset > buffer.length");
	        }
	        if (length < 0) {
	            throw
	            new IllegalArgumentException("length < 0");

	        }
	        if (length == 0) {
	            return;
	        }
	        synchronized(this) {
	            int bufferLength = mBuffer.length;
	            boolean wasEmpty = mStoredBytes == 0;
	            while (length > 0) {
	                while(bufferLength == mStoredBytes) {
	                    wait();
	                }
	                int tail = mHead + mStoredBytes;
	                int oneRun;
	                if (tail >= bufferLength) {
	                    tail = tail - bufferLength;
	                    oneRun = mHead - tail;
	                } else {
	                    oneRun = bufferLength - tail;
	                }
	                int bytesToCopy = Math.min(oneRun, length);
	                System.arraycopy(buffer, offset, mBuffer, tail, bytesToCopy);
	                offset += bytesToCopy;
	                mStoredBytes += bytesToCopy;
	                length -= bytesToCopy;
	            }
	            if (wasEmpty) {
	                notify();
	            }
	        }
	    }

	    private byte[] mBuffer;
	    private int mHead;
	    private int mStoredBytes;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bLlaves:
			try {
				if(mmOutputStream==null)
				{
					Toast.makeText(getActivity(), "Dispositivo BlueTooth no conectado", Toast.LENGTH_SHORT).show();
					return;
				}
				sendData();
			} catch (IOException e) {
				Log.i("Error bluetooth ",e.getMessage());
			}
			break;

		default:
			break;
		}
	}



}
