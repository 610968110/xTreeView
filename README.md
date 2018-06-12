# xTreeView

项目地址：https://github.com/610968110/xTreeView     

该项目是支持ListView无限层级展开的框架

![](https://github.com/610968110/xTreeView/raw/master/pic/001.png)

一、集成方式：
===
````Xml
compile 'com.lbx:xTreeView:1.0.6'
````

二、使用方法
====
直接举个栗子、首先我们声明一个Bean类，作为ListView的展示：
````Java
    public class ItemBean  {

    public String name;

    public ItemBean(String name) {
        this.name = name;
    }
}
````

然后进一步封装：
````Java
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
````

接下来，继承TreeListViewAdapter：
````Java
public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T, ItemBean>
***
***
//省略部分代码
***
***
        
        
/**
 * @param tree              listview
 * @param context            context
 * @param datas              list
 * @param defaultExpandLevel 初始化默认展开几级
 * @param TreeItemPadding 不同级别展开后与上一级别的周边边距，更好的区分级别
 */
 mAdapter = new SimpleTreeAdapter<>(mTree, this, mDatas, 0,new TreeItemPadding(20, 3, 3, 3));
````

然后向List里面添加数据：
````Java
        // id , pid , label , 其他属性
        mDatas.add(new FileBean(1, 0, new ItemBean("文件")));
        mDatas.add(new FileBean(2, 1, new ItemBean("游戏")));
        mDatas.add(new FileBean(3, 1, new ItemBean("文档")));
        mDatas.add(new FileBean(4, 1, new ItemBean("程序")));
        mDatas.add(new FileBean(5, 2, new ItemBean("war3")));
        mDatas.add(new FileBean(6, 2, new ItemBean("刀塔传奇")));

        mDatas.add(new FileBean(7, 4, new ItemBean("面向对象")));
        mDatas.add(new FileBean(8, 4, new ItemBean("非面向对象")));

        mDatas.add(new FileBean(9, 7, new ItemBean("C++")));
        mDatas.add(new FileBean(10, 7, new ItemBean("JAVA")));
        mDatas.add(new FileBean(11, 7, new ItemBean("Javascript")));
        mDatas.add(new FileBean(13, 8, new ItemBean("C")));
````

到此完成。

三、其他用法
====
有些时候，id和父id并不是int类型，而是一些字符串，那么我们可以用@TreeNodeStringId和@TreeNodeStringPid去标记。
````Java
    /**
     * 自己的id
     */
    @TreeNodeStringId
    private String _id;
    /**
     * 父id
     */
    @TreeNodeStringPid
    private String parentId;
````