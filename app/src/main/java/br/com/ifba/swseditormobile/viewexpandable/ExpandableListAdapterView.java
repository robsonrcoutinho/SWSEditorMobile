package br.com.ifba.swseditormobile.viewexpandable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import br.com.ifba.swseditormobile.R;
import br.com.ifba.swseditormobile.model.ChildGrupo;
import br.com.ifba.swseditormobile.model.Group;


/**
 * Created by Robson on 27/03/2016.
 */
public class ExpandableListAdapterView extends BaseExpandableListAdapter {

    private Context  context;
    private List<Group> groups;
    private HashMap<Group, List<ChildGrupo>> listDataChild;


    public ExpandableListAdapterView(Context context, List<Group> listDataGroup,
                                     HashMap<Group, List<ChildGrupo>> listChildData) {
        this.context = context;
        this.groups = listDataGroup;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.groups.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final ChildGrupo subgrupo = (ChildGrupo) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.child_item, null);
            }

            TextView text = (TextView) convertView.findViewById(R.id.child_item_name);
            text.setText(subgrupo.getNomeChildGrupo());

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.groups.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getNameGroup());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
