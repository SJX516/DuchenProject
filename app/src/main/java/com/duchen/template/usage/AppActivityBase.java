package com.duchen.template.usage;

import android.view.MenuItem;
import android.view.View;

import com.duchen.template.component.ActivityBase;
import com.duchen.template.utils.ManifestUtil;
import com.duchen.template.utils.datahelper.StringUtil;

public abstract class AppActivityBase extends ActivityBase implements View.OnClickListener{

    public static final String KEY_TITLE = "key_title";

    @Override
    protected void initActionBar() {
        super.initActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.show();
            String title = (String) getTitle();
            if (StringUtil.isEmpty(title)) {
                title = ManifestUtil.getApplicationName(this);
            }
            mActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        if (v != null) {
            handleClick(v.getId(), v);
        }
    }

    public abstract void handleClick(int id, View v);
}
