package lbx.xtreeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lbx.xtreeviewlib.adapter.TreeListViewAdapter;
import lbx.xtreeviewlib.bean.Node;

/**
 * @author lbx
 */
public class MainActivity extends AppCompatActivity {

    private List<FileBean> mDatas = new ArrayList<>();
    private ListView mTree;
    private SimpleTreeAdapter<FileBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        mTree = (ListView) findViewById(R.id.id_tree);
        try {
            /**
             *
             */
            mAdapter = new SimpleTreeAdapter<>(mTree, this, mDatas, 0);
            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {

            }
        });
    }

    private void initDatas() {
        // id , pid , label , 其他属性
        mDatas.add(new FileBean(1 + "", 0 + "", new ItemBean("文件")));
        mDatas.add(new FileBean(2 + "", 1 + "", new ItemBean("游戏")));
        mDatas.add(new FileBean(3 + "", 1 + "", new ItemBean("文档")));
        mDatas.add(new FileBean(4 + "", 1 + "", new ItemBean("程序")));
        mDatas.add(new FileBean(5 + "", 2 + "", new ItemBean("war3")));
        mDatas.add(new FileBean(6 + "", 2 + "", new ItemBean("刀塔传奇")));

        mDatas.add(new FileBean(7 + "", 4 + "", new ItemBean("面向对象")));
        mDatas.add(new FileBean(8 + "", 4 + "", new ItemBean("非面向对象")));

        mDatas.add(new FileBean(9 + "", 7 + "", new ItemBean("C++")));
        mDatas.add(new FileBean(10 + "", 7 + "", new ItemBean("JAVA")));
        mDatas.add(new FileBean(11 + "", 7 + "", new ItemBean("Javascript")));
        mDatas.add(new FileBean(13 + "", 8 + "", new ItemBean("C")));
    }
}
