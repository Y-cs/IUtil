package self.ycs.util.struct;

import jdk.nashorn.internal.ir.IfNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author Y-cs
 * @date 2021/4/30 11:00
 */
public class Btree<K, V> {

    private int loadVal = 2;
    private int t = 2;
    private Node rootNode = new Node();
    private final Comparator<K> keyComparator;

    public Btree(int loadVal, int t, Comparator<K> keyComparator) {
        this.loadVal = loadVal;
        this.t = t;
        this.keyComparator = keyComparator;
    }

    public void put(K key, V value) {
        if (rootNode.entities.size() == t * loadVal - 1) {
            Node tempNode = rootNode;
            Node node = new Node();
            node.children.add(rootNode);
            rootNode = node;
            splitNode(node, tempNode);
        }
        putNode(rootNode, new Entity<>(key, value));
    }

    public void putNode(Node node, Entity<K, V> entity) {
        int index = node.indexPut(entity);
        if (index != node.entities.size() && node.entities.get(index).key.equals(entity.key)) {
            node.entities.get(index).value = entity.value;
        } else {
            if (node.isLeaf()) {
                node.entities.add(index, entity);
            } else {
                Node childrenNode = node.children.get(index);
                if (childrenNode.entities.size() == t * loadVal - 1) {
                    //分裂节点
                    Node newNode = splitNode(node, childrenNode);
                    if (keyComparator.compare(childrenNode.entities.get(childrenNode.entities.size() - 1).key, entity.key) < 0) {
                        //左边的最大值小于entity
                        childrenNode = newNode;
                    }
                }
                putNode(childrenNode, entity);

            }
        }
    }

    private Node splitNode(Node fNode, Node cNode) {
        int index = cNode.entities.size() / 2;
        Entity<K, V> kvEntity = cNode.entities.get(index);
        Node newNode = new Node();
        newNode.entities = splitList(cNode.entities, index + 1, cNode.entities.size());
        cNode.entities = splitList(cNode.entities, 0, index);
        if (!cNode.children.isEmpty()) {
            newNode.children = splitList(cNode.children, index, cNode.children.size());
            cNode.children = splitList(cNode.children, 0, index);
        }
        //插入父节点
        int upIndex = fNode.indexPut(kvEntity);
        fNode.entities.add(upIndex, kvEntity);
        fNode.children.add(upIndex + 1, newNode);
        return fNode;
    }

    private <T> List<T> splitList(List<T> list, int start, int end) {
        List<T> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public void remove(K key, V value) {

    }

    private class Node {
        private List<Entity<K, V>> entities = new ArrayList<>();
        private List<Node> children = new ArrayList<>();

        public boolean isLeaf() {
            return this.children.size() == 0;
        }

        public int indexPut(Entity<K, V> entity) {
            for (int i = 0; i < entities.size(); i++) {
                Entity<K, V> kvEntity = entities.get(i);
                int compare = keyComparator.compare(entity.key, kvEntity.key);
                if (compare <= 0) {
                    return i;
                }
            }
            return entities.size();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "entities=" + entities +
                    ", children=" + children +
                    '}';
        }
    }

    private static class Entity<K, V> {
        private K key;
        private V value;

        public Entity(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Entity{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

}
