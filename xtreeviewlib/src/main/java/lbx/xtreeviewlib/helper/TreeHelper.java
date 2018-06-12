package lbx.xtreeviewlib.helper;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import lbx.xtreeviewlib.R;
import lbx.xtreeviewlib.bean.Node;
import lbx.xtreeviewlib.iinterface.TreeCloseImgResId;
import lbx.xtreeviewlib.iinterface.TreeNodeId;
import lbx.xtreeviewlib.iinterface.TreeNodeLabel;
import lbx.xtreeviewlib.iinterface.TreeNodePid;
import lbx.xtreeviewlib.iinterface.TreeNodeStringId;
import lbx.xtreeviewlib.iinterface.TreeNodeStringPid;
import lbx.xtreeviewlib.iinterface.TreeOpenImgResId;

/**
 * @author lbx
 * @date 2017/11/24.
 */

public class TreeHelper {
    /**
     * 传入我们的普通bean，转化为我们排序后的Node
     *
     * @param datas
     * @param defaultExpandLevel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<Node<T>> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        List<Node<T>> result = new ArrayList<>();
        //将用户数据转化为List<Node>以及设置Node间关系
        List<Node<T>> nodes = convetData2Node(datas);
        //拿到根节点
        List<Node<T>> rootNodes = getRootNodes(nodes);
        //排序
        for (Node<T> node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     *
     * @param nodes
     * @return
     */
    public static <T> List<Node<T>> filterVisibleNode(List<Node<T>> nodes) {
        List<Node<T>> result = new ArrayList<>();
        for (Node<T> node : nodes) {
            // 如果为跟节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 将我们的数据转化为树的节点
     *
     * @param datas
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static <T> List<Node<T>> convetData2Node(List<T> datas) throws IllegalArgumentException, IllegalAccessException {
        List<Node<T>> nodes = new ArrayList<>();
        Node<T> node = null;
        //是否是int类型的id
        boolean isIntegerId = false;
        for (T t : datas) {
            int id = -1;
            int pId = -1;
            String stringId = "";
            String stringPId = "";
            T label = null;
            int[] icons = new int[]{R.drawable.ic_launcher_round, R.drawable.ic_launcher_round};
            Class<? extends Object> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.getAnnotation(TreeCloseImgResId.class) != null) {
                    f.setAccessible(true);
                    icons[0] = f.getInt(t);
                }
                if (f.getAnnotation(TreeOpenImgResId.class) != null) {
                    f.setAccessible(true);
                    icons[1] = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodeLabel.class) != null) {
                    f.setAccessible(true);
                    label = (T) f.get(t);
                }
                if (f.getAnnotation(TreeNodeId.class) != null) {
                    isIntegerId = true;
                    f.setAccessible(true);
                    id = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodePid.class) != null) {
                    f.setAccessible(true);
                    isIntegerId = true;
                    pId = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodeStringId.class) != null) {
                    f.setAccessible(true);
                    stringId = (String) f.get(t);
                }
                if (f.getAnnotation(TreeNodeStringPid.class) != null) {
                    f.setAccessible(true);
                    stringPId = (String) f.get(t);
                }
                if (id != -1 && pId != -1 && label != null) {
                    break;
                }
            }
            if (isIntegerId) {
                node = new Node<>(id, pId, icons, label);
            } else {
                node = new Node<>(stringId, stringPId, icons, label);
            }
            nodes.add(node);
        }
        if (!isIntegerId) {
            //如果不是int类型的id  需要根据String的id/pid对应关系，去自动设置int类型的id/pid
            nodes = sortIdFromStringId(nodes);
        }

        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        for (int i = 0; i < nodes.size(); i++) {
            Node<T> n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node<T> m = nodes.get(j);
                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }
        // 设置图片
        for (Node<T> n : nodes) {
            setNodeIcon(n);
        }
        return nodes;
    }

    /**
     * 控件会根据level自动排序
     */
    private static <T> ArrayList<Node<T>> sortIdFromStringId(List<Node<T>> nodes) {
        ArrayList<Node<T>> levelAreas = new ArrayList<>();
        //level缓存器，一个id对应一个level级别 ""对应0
        Map<String, Integer> levelMap = getIdCache(nodes);
        for (Node<T> area : nodes) {
            //设置级别
            Node<T> levelArea = setId(area, levelMap);
            levelAreas.add(levelArea);
        }
        return levelAreas;
    }

    /**
     * 设置id
     */
    private static <T> Node<T> setId(Node<T> node, Map<String, Integer> levelMap) {
        String pId = node.getStringPId();
        String areaId = node.getStringId();
        if (TextUtils.isEmpty(pId)) {
            node.setId(0);
        } else {
            Integer integer = levelMap.get(pId);
            node.setpId(integer == null ? 0 : integer);
        }
        node.setId(levelMap.get(areaId));
        return node;
    }

    /**
     * 添加id缓存
     */
    @NonNull
    private static <T> Hashtable<String, Integer> getIdCache(List<Node<T>> nodes) {
        //添加id缓存
        Hashtable<String, Integer> cacheTable = new Hashtable<>();
        for (Node<T> area : nodes) {
            cacheTable.put(area.getStringId(), cacheTable.size() + 1);
        }
        cacheTable.put("", 0);
        return cacheTable;
    }

    private static <T> List<Node<T>> getRootNodes(List<Node<T>> nodes) {
        List<Node<T>> root = new ArrayList<>();
        for (Node<T> node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     */
    private static <T> void addNode(List<Node<T>> nodes, Node<T> node, int defaultExpandLeval, int currentLevel) {
        nodes.add(node);
        if (defaultExpandLeval >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(nodes, node.getChildren().get(i), defaultExpandLeval, currentLevel + 1);
        }
    }

    /**
     * 设置节点的图标
     *
     * @param node
     */
    private static void setNodeIcon(Node node) {
        if (node.getChildren().size() > 0 && node.isExpand()) {
            node.setIcon(node.getIcons()[1]);
        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
            node.setIcon(node.getIcons()[0]);
        } else {
            node.setIcon(-1);
        }
    }
}
