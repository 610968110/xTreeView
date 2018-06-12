package lbx.xtreeviewlib.bean;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private int id;
    private String stringId = "";
    /**
     * 根节点pId为0
     */
    private int pId = 0;
    private String stringPId = "";


    private T entity;

    /**
     * 当前的级别
     */
    private int level;

    /**
     * 是否展开
     */
    private boolean isExpand = false;

    private int icon;
    private int[] icons = new int[2];

    /**
     * 下一级的子Node
     */
    private List<Node<T>> children = new ArrayList<>();

    /**
     * 父Node
     */
    private Node parent;

    public Node() {
    }

    public Node(String stringId, String stringPId, int[] icons, T entity) {
        super();
        this.stringId = stringId;
        this.stringPId = stringPId;
        this.icons = icons;
        this.entity = entity;
    }

    public Node(int id, int pId, int[] icons, T entity) {
        super();
        this.id = id;
        this.icons = icons;
        this.pId = pId;
        this.entity = entity;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getStringPId() {
        return stringPId;
    }

    public void setStringPId(String stringPId) {
        this.stringPId = stringPId;
    }

    public int[] getIcons() {
        return icons;
    }

    public void setIcons(int[] icons) {
        this.icons = icons;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 是否为跟节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        }
        return parent.isExpand();
    }

    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    /**
     * 设置展开
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }
}

