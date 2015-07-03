package lyc.com.dragstylecodestandard;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lyc.com.dragstylecodestandard.view.CustomAdapter;
import lyc.com.dragstylecodestandard.view.DragableLisvtView;


public class MainActivity extends ActionBarActivity {
    private Context ctx;
    private Activity act;
    private DragableLisvtView  lv;
    CustomAdapter adapter;
    private ArrayList<String> strs=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=getApplicationContext();
        act=this;
        initView();
        initData();
        adapter =new CustomAdapter(strs,act);
        lv.setAdapter(adapter);
    }

    private void initData() {
        for (int i=0;i<10;i++){
            strs.add("data"+i);
        }
    }

    private void initView() {
        lv= (DragableLisvtView) findViewById(R.id.lv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
