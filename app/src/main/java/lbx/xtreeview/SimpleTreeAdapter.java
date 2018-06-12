package lbx.xtreeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import lbx.xtreeviewlib.adapter.TreeListViewAdapter;
import lbx.xtreeviewlib.bean.Node;
import lbx.xtreeviewlib.bean.TreeItemPadding;

/**
 * @author lbx
 */
public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T, ItemBean> {

    /**
     * @param tree              listview
     * @param context            context
     * @param datas              list
     * @param defaultExpandLevel 初始化默认展开几级
     */
    public SimpleTreeAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, new TreeItemPadding(20, 3, 3, 3), defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, ItemBean entity, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }
        viewHolder.label.setText(entity.name);
        return convertView;
    }

    private final class ViewHolder {
        ImageView icon;
        TextView label;
    }

}  