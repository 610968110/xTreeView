package lbx.xtreeviewlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import lbx.xtreeviewlib.bean.Node;
import lbx.xtreeviewlib.bean.TreeItemPadding;
import lbx.xtreeviewlib.helper.TreeHelper;

public abstract class TreeListViewAdapter<T, M> extends BaseAdapter {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<Node<T>> mNodes;
    protected LayoutInflater mInflater;
    /**
     * 存储所有的Node
     */
    protected List<Node<T>> mAllNodes;
    private TreeItemPadding mPadding;

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;

    public interface OnTreeNodeClickListener<T> {
        void onClick(Node<T> node, int position);
    }

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeListViewAdapter(ListView mTree, Context context, List<T> datas, TreeItemPadding padding, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        mContext = context;
        mPadding = padding == null ? new TreeItemPadding(0, 0, 0, 0) : padding;
        /**
         * 对所有的Node进行排序
         */
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        /**
         * 过滤出可见的Node
         */
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        mInflater = LayoutInflater.from(context);

        /**
         * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
         */
        mTree.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expandOrCollapse(position);
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position), position);
                }
            }
        });
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        Node<T> n = mNodes.get(position);
        // 排除传入参数错误异常
        if (n != null) {
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                // 刷新视图
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Node<T> getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node<T> node = mNodes.get(position);
        M entity = (M) node.getEntity();
        convertView = getConvertView(node, entity, position, convertView, parent);
        // 设置内边距
        convertView.setPadding(node.getLevel() * mPadding.getLeft(), mPadding.getTop(), mPadding.getRight(), mPadding.getBottom());
        return convertView;
    }

    public abstract View getConvertView(Node<T> node, M entity, int position, View convertView, ViewGroup parent);

}
