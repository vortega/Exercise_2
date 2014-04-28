package com.vortega.exercise_2.app;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class ResultsActivity extends ActionBarActivity {

    ListView itemList;
    TextView title;

    List<ItemDto> items;

    static class ViewHolder {
        TextView title;
        TextView price;
        ImageView thumb;
        int position;
    }

    public class ItemDtoAdapter extends ArrayAdapter<ItemDto> {
        public ItemDtoAdapter(){
            super(ResultsActivity.this, R.layout.listview_item, items);
        }

        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.price = (TextView) view.findViewById(R.id.price);
                holder.thumb = (ImageView) view.findViewById(R.id.imageView);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            ItemDto currentItem = items.get(position);

            DownloadImageTask imageLoader = new DownloadImageTask( holder.thumb );
            imageLoader.execute( currentItem.getThumbnail() );

            holder.title.setText( currentItem.getTitle() );
            holder.price.setText( currentItem.getPrice().toString() );

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
        title = (TextView) findViewById(R.id.textView);

        items = (List<ItemDto>) this.getIntent().getSerializableExtra("items");
        title.setText( "Resultados: "+ String.valueOf(items.size()) );

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ResultsActivity.this, VipActivity.class);
                intent.putExtra("item", items.get(i) );

                startActivity(intent);
            }
        });

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
