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

import java.util.List;
import java.io.Serializable;


public class MainActivity extends ActionBarActivity {

    Button searchBtn;
    EditText editText;
    MLService mlService;
    boolean mlBound = false;
    List<ItemDto> items;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mlConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MLService.MLBinder binder = (MLService.MLBinder) service;
            mlService = binder.getService();
            mlBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mlBound = false;
        }
    };

    /*** LIFECYCLE ***/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MLService.class);
        bindService(intent, mlConnection, Context.BIND_AUTO_CREATE);

        searchBtn = (Button) findViewById( R.id.button );
        editText  = (EditText) findViewById( R.id.editText );

        searchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    items = mlService.getSearch( editText.getText().toString() );
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    intent.putExtra("items", (Serializable) items);;

                    startActivity(intent);
                } catch (Exception e ){
                    Log.e("UI", "Search", e);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mlBound) {
            unbindService(mlConnection);
            mlBound = false;
        }
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
