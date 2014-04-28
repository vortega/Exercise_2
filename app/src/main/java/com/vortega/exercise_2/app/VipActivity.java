package com.vortega.exercise_2.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class VipActivity extends ActionBarActivity {

    TextView title;
    ImageView picture;
    TextView price;

    ItemDto item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);

        item = (ItemDto) this.getIntent().getSerializableExtra("item");

        title = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.price);
        picture = (ImageView) findViewById(R.id.imageView);

        DownloadImageTask imageLoader = new DownloadImageTask( picture );
        imageLoader.execute( item.getThumbnail() );

        title.setText( item.getTitle() );
        price.setText( item.getPrice().toString() );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
