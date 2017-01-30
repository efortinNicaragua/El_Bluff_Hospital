package ethanfortin_nicaragua.elbluffhospital;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Wildcat on 1/30/2017.
 */

public class Adapter_ExpandableList extends BaseExpandableListAdapter{

    private Context ctx;
    private HashMap<String, List<String>> Movies_Category;
    private List<String> Movies_List;


    public Adapter_ExpandableList(Context ctx, HashMap<String, List<String>> Movies_Category, List<String> Movies_List)
    {
        this.ctx = ctx;
        this.Movies_Category=Movies_Category;
        this.Movies_List=Movies_List;
    }

    @Override
    public Object getChild(int parent, int child){

        return Movies_Category.get(Movies_List.get(parent)).get(child);
    }

    @Override
    public long getChildId(int parent, int child){

        return child;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertview,
                             ViewGroup parentview)
    {
        String child_title= (String) getChild(parent,child);
        if(convertview == null)
        {
            LayoutInflater inflator =(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview=inflator.inflate(R.layout.row_patinethistory_child, parentview,false);
        }
        TextView child_textView=(TextView) convertview.findViewById(R.id.child_txt);
        child_textView.setText(child_title);
            return convertview;
    }


    @Override
    public int getChildrenCount(int arg0){

        return Movies_Category.get(Movies_List.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0){

        return Movies_List.get(arg0);
    }

    @Override
    public int getGroupCount() {
        return Movies_List.size();
    }

    @Override
    public long getGroupId(int arg0){

        return arg0;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertview, ViewGroup parentview){

        String group_title = (String) getGroup(parent);
        if(convertview == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.row_patienthistory_parent, parentview, false);
        }
        TextView parent_textview = (TextView) convertview.findViewById(R.id.parent_txt);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        return convertview;
    }

    @Override
    public boolean hasStableIds(){

        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1){

        return false;
    }

}


