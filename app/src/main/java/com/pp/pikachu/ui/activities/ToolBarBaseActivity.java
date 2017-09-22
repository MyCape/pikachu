package com.pp.pikachu.ui.activities;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import com.pp.pikachu.R;

/**
 * Created by bry1337 on 21/09/2017.
 *
 * @author edwardbryan.abergas@gmail.com
 */

public abstract class ToolBarBaseActivity extends BaseActivity {

  @BindView(R.id.toolbar) protected Toolbar toolbar;

  @Override protected void setupToolbar() {
    setSupportActionBar(toolbar);
    if (isActionBarBackButtonEnabled()) {
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home && isActionBarBackButtonEnabled()) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
