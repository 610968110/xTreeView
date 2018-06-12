package lbx.xtreeview;

import lbx.xtreeviewlib.iinterface.TreeCloseImgResId;
import lbx.xtreeviewlib.iinterface.TreeNodeId;
import lbx.xtreeviewlib.iinterface.TreeNodeLabel;
import lbx.xtreeviewlib.iinterface.TreeNodePid;
import lbx.xtreeviewlib.iinterface.TreeOpenImgResId;

/**
 * 原理是根据父id去找id对应的类
 */
public class FileBean {

    /**
     * 自己的id
     */
    @TreeNodeId
    private int _id;
    /**
     * 父id
     */
    @TreeNodePid
    private int parentId;
    /**
     * 实体类
     */
    @TreeNodeLabel
    private ItemBean name;
    /**
     * 合起的图标
     */
    @TreeCloseImgResId
    private int img = R.drawable.right;
    /**
     * 展开的图标
     */
    @TreeOpenImgResId
    private int img1 = R.drawable.bottom;

    public FileBean(int _id, int parentId, ItemBean name) {
        super();
        this._id = _id;
        this.parentId = parentId;
        this.name = name;
    }
}