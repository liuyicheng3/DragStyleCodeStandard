package lyc.com.dragstylecodestandard.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lyc on 2015/7/3.
 */
public class  CustomAdapter extends BaseAdapter {
    private ArrayList<String> strs;
    private Activity act;

    public CustomAdapter(ArrayList<String> strs, Activity act) {
        this.strs = strs;
        this.act = act;
    }

    @Override
    public int getCount() {
        return strs.size();
    }

    @Override
    public String getItem(int i) {
        return strs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view=new TextView(act );
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
        }
        ((TextView)view).setText(getItem(i));
        return view;
    }

    public void swapItem(int postionA,int postionB){
        String _temp=strs.get(postionA);
        strs.set(postionA,strs.get(postionB));
        strs.set(postionB,_temp);
        notifyDataSetChanged();
    }
}