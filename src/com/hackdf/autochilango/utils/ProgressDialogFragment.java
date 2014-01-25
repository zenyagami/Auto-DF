package com.hackdf.autochilango.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

public class ProgressDialogFragment extends DialogFragment {
        private static  Context conc ;
        public static ProgressDialogFragment newInstance() {
                ProgressDialogFragment frag = new ProgressDialogFragment ();
                return frag;
        }
        public static ProgressDialogFragment newInstance(Context c) {
                conc=c;
                ProgressDialogFragment frag = new ProgressDialogFragment ();
                return frag;
        }
 
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
 
                final ProgressDialog dialog = new ProgressDialog(conc);
                dialog.setMessage("Espere por favor...");
                dialog.setTitle("Procesando");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
 
                // Disable the back button
                OnKeyListener keyListener = new OnKeyListener() {
 
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode,
                                        KeyEvent event) {
                                
                                if( keyCode == KeyEvent.KEYCODE_BACK){                                        
                                        return true;
                                }
                                return false;
                        }
 
                
                };
                dialog.setOnKeyListener(keyListener);
                return dialog;
        }
 
}