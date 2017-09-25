package com.pp.pikachu.manager;

import android.app.Activity;
import android.content.Intent;
import com.pp.pikachu.ui.activities.home.HomeActivity;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public class AppActivityManager {

  public void launchHomePage(Activity activity){
    final Intent intent = new Intent(activity, HomeActivity.class);
    activity.startActivity(intent);
    activity.finish();
  }
}
