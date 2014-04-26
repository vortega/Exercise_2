package com.vortega.exercise_2.app;

import android.content.ClipData;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ResultsActivity extends ActionBarActivity {

    List<ItemDto> items;
    ListView itemList;

    public class ItemDtoAdapter extends ArrayAdapter<ItemDto> {
        public ItemDtoAdapter(){
            super(ResultsActivity.this, R.layout.listview_item, items);
        }

        public View getView(int position, View view, ViewGroup parent) {
            if ( view == null ){
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            }

            ItemDto currentItem = items.get(position);

            TextView title = (TextView) view.findViewById(R.id.title);
            TextView price = (TextView) view.findViewById(R.id.price);

            title.setText( currentItem.getTitle() );
            price.setText( currentItem.getPrice().toString() );

            return view;
        }
    }

    private void populateItems(){
        ArrayAdapter<ItemDto> adapter = new ItemDtoAdapter();
        itemList.setAdapter( adapter );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        itemList = (ListView) findViewById(R.id.listView);

        items = (List<ItemDto>) this.getIntent().getSerializableExtra("items");

        populateItems();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
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
