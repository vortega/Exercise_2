package com.vortega.exercise_2.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;
import java.io.Serializable;


interface AsyncTaskListener{
    public void searchDone(List<ItemDto> items);
}

public class MainActivity extends ActionBarActivity implements AsyncTaskListener {

    Button searchBtn;
    EditText editText;
    ProgressBar loader;

    boolean mlBound = false;
    List<ItemDto> items;

    public void searchDone(List<ItemDto> items){
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtra("items", (Serializable) items);;

        startActivity(intent);

        editText.setVisibility(View.VISIBLE);
        loader.setVisibility(View.INVISIBLE);
        searchBtn.setEnabled(true);
    }

    /*** LIFECYCLE ***/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = (Button) findViewById( R.id.button );
        editText  = (EditText) findViewById( R.id.editText );
        loader    = (ProgressBar) findViewById( R.id.progressBar );

        searchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setVisibility(View.INVISIBLE);
                loader.setVisibility(View.VISIBLE);
                searchBtn.setEnabled(false);

                new MLApiAsyncTask(MainActivity.this).execute( editText.getText().toString() );
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
