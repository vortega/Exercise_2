package com.vortega.exercise_2.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vortega.exercise_2.imageManagement.ImageLoader;
import com.vortega.exercise_2.restclient.MLApiAsyncTask;
import com.vortega.exercise_2.restclient.MlApiCallbacks;
import com.vortega.exercise_2.restclient.MlRestClient;

import java.util.List;

class ViewHolder {
    TextView title;
    TextView price;
    ImageView thumb;
    String thumbUrl;
    int position;
}

public class ResultsActivity extends ActionBarActivity implements MlApiCallbacks {

    ListView itemList;
    TextView title;

    List<ItemDto> items;
    String searchStr;

    String cacheDir;
    ImageLoader imgLoader;

    public class ItemDtoAdapter extends ArrayAdapter<ItemDto> {
        public ItemDtoAdapter(){
            super(ResultsActivity.this, R.layout.listview_item, items);
        }

        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;

            ItemDto currentItem = items.get(position);

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.price = (TextView) view.findViewById(R.id.price);
                holder.thumb = (ImageView) view.findViewById(R.id.imageView);
                holder.thumbUrl = currentItem.getThumbnail();
                holder.position = position;
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            Context context = this.getContext();
            int icon = context.getResources().getIdentifier("icon", "drawable", context.getPackageName());

            imgLoader.displayImage( currentItem.getThumbnail(), icon, holder.thumb);

            holder.title.setText( currentItem.getTitle() );
            holder.price.setText( currentItem.getPrice().toString() );

            return view;
        }
    }

    private void populateItems(){
        ArrayAdapter<ItemDto> adapter = new ItemDtoAdapter();
        itemList.setAdapter( adapter );
    }


    public void searchItemsDone(List<ItemDto> items){}

    public void getItemDone( ItemDto item ) {
        Intent intent = new Intent(ResultsActivity.this, VipActivity.class);
        intent.putExtra("item", item );

        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        itemList = (ListView) findViewById(R.id.listView);
        title = (TextView) findViewById(R.id.textView);

        items = (List<ItemDto>) this.getIntent().getSerializableExtra("items");
        title.setText( "Resultados: "+ String.valueOf(items.size()) );

        searchStr = this.getIntent().getStringExtra("searchStr");

        final MlRestClient mlClient = new MlRestClient(this);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mlClient.getItem( items.get(i).getId() );
            }
        });

        cacheDir = this.getCacheDir().toString();

        imgLoader = new ImageLoader(getApplicationContext());

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

    public void searchDone(List<ItemDto> items){

    }

}
