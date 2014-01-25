package com.hackdf.autochilango.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class Dialogs {
	  public static void showDialog(FragmentActivity activity) {
          FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
          Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(
                          "loading_frag");

          if (prev != null) {
                  ft.remove(prev);
          }

          // Create and show the dialog.
          try {
                  ProgressDialogFragment newFragment = ProgressDialogFragment
                                  .newInstance(activity);
                  newFragment.setCancelable(false);
                  newFragment.show(ft, "loading_frag");
          } catch (Exception e) {
          }

  }

  public static void dismissLoadingDialog(FragmentActivity activity) {
          try {
                  FragmentTransaction ft = activity.getSupportFragmentManager()
                                  .beginTransaction();
                  Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(
                                  "loading_frag");

                  if (prev != null) {
                          ft.remove(prev);
                          ft.commitAllowingStateLoss();
                  }
          } catch (Exception ex) {
                  ex.printStackTrace();
          }
  }

}
