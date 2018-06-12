package lbx.xtreeviewlib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import lbx.xtreeviewlib.bean.Node;
import lbx.xtreeviewlib.bean.TreeItemPadding;

/**
 * @author lbx
 *         T 传入类型
 *         M  getEntity类型
 */
public abstract class BaseTreeAdapter<T, M> extends TreeListViewAdapter<T, M> {

    public BaseTreeAdapter(ListView mTree, Context context, List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        this(mTree, context, datas, new TreeItemPadding(0, 0, 0, 0), defaultExpandLevel);
    }

    public BaseTreeAdapter(ListView mTree, Context context, List<T> datas, TreeItemPadding padding, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, padding, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node<T> node, M entity, int position, View convertView, ViewGroup parent) {
        return getTreeConvertView(node, entity, position, convertView, parent);
    }

    public abstract View getTreeConvertView(Node<T> node, M entity, int position, View convertView, ViewGroup parent);
}