package com.pp.pikachu.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.pp.pikachu.R;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public abstract class BasePresenter {

  public abstract Activity getActivity();

  public abstract AlertDialog getAlertDialog();

  public void showAlertDialogError(final String message) {
    getAlertDialog().setMessage(message);
    getAlertDialog().setCancelable(false);
    getAlertDialog().setButton(DialogInterface.BUTTON_POSITIVE, getActivity().getString(R.string.button_ok),
        (dialog, which) -> getAlertDialog().dismiss());
    getAlertDialog().show();
  }
}
