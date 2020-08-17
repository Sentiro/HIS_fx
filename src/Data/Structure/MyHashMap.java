package Data.Structure;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import java.util.Set;


public class MyHashMap<K,V> {

    static int DEFAULT_CAPACITY=100;
    //内部数组 ----存放的是Node类型的链表
    private int capacity;
    //存放所有key的list
    private List<K> keySet;
    //
    private Node[] hashTable;
    //定义所有的key-value对的总数
    private int count=0;
    //装载因子
    private float loaderFactor;
    //count的上限
    private int threshold;

//以链表的形式实现每一个键值对
    public class Node<K,V>{

        private int keyCode;
        private K key;
        private V value;
        private Node<K,V> next;


        public Node(int keyCode,K key,V value,Node<K,V> next){
            this.keyCode=keyCode;
            this.key=key;
            this.value=value;
            this.next=next;
        }


    }



    /**
     * 构造函数
     */
    public MyHashMap(){
        //初始化keySet
        keySet=new ArrayList<>();
        //初始化容量
        capacity=DEFAULT_CAPACITY;
        //初始化hashTable
        hashTable=new Node[capacity];
        //初始化装载因子
        loaderFactor=2.0f;
        //初始化count的上限
        threshold=(int) (capacity*loaderFactor);
    }


    public List<K> keySet(){
        return keySet;
    }

    public V put(K key,V value){
        //首先是根据K 计算出hash
        int keyCode=key.hashCode();
        int hash=getHash(keyCode);

        for(Node<K,V> n=hashTable[hash];n!=null;n=n.next){

            if(key.equals(n.key)){
                //如果已经存在该索引 则返回oldValue
                //将就得value改成新的value 索引不用改变
                //以输入对象的equals 判断标准判读
                V oldValue=n.value;
                n.value=value;
                return oldValue;
            }
        }
        //索引并没有存在
        addNode(key,value,hash);
        keySet.add(key);
        return null;
    }


    public V replace(K key,V value){
        int keyCode=key.hashCode();
        int hash=getHash(keyCode);
        //查找hash中是否存在索引
        for(Node<K,V>n=hashTable[hash];n!=null;n=n.next){
            if(key.equals(n.key)){
                //如果找到了就更新value
                V oldValue=n.value;
                n.value=value;
                return oldValue;
            }
        }
        //如果没有找到 返回null
        return null;
    }

    public V getV(K key){
        //根据输入的Key 计算出hash
        int hash=getHash(key.hashCode());
        //从header遍历
        for(Node<K,V> node=hashTable[hash];node!=null;node=node.next){
            if(key.equals(node.key)){

                return node.value;
            }
        }
        //如果没有该key  返回null
        return null;
    }

    public boolean delete(K key){

        //首先是查找是否存在该key
        int hash=getHash(key.hashCode());

        //存放上一个Node
        Node<K,V> pre=null;
        Node<K,V> n=hashTable[hash];

        for( ;n!=null;n=n.next){
            if(key.equals(n.key)){
                //删除
                if(pre==null){//删除header
                    hashTable[hash]=n.next;
                    //从keySet里删除
                    keySet.remove(key);
                    return true;
                }else{
                    pre.next=n.next;
                    n=null;
                    keySet.remove(key);
                    return true;
                }
            }
            pre=n;
        }

        return false;
    }



    private int getHash(int num){
        return num%capacity;
    }


    private void addNode(K key,V value,int bucketIndex){

        if(count<threshold){//小于上限
            //取得链表的head
            Node<K,V> n=hashTable[bucketIndex];
            //改变链表的head
            int keyCode=key.hashCode();
            hashTable[bucketIndex]=new Node<K,V>(keyCode,key,value,n);
            count++;

        }else{//大于上限
            //重新建表
            resize();
            //系统的map是不管是否超限  先添加上再判断 此处要先判断的话 就要在重新建表后  将该用户添加上
            //这种问题总是出现在衔接的地方
            //取得链表的head
            Node<K,V> n=hashTable[bucketIndex];
            hashTable[bucketIndex]=new Node<K,V>(key.hashCode(),key,value,n);
            count++;
        }

    }

    private void resize(){
        //	System.out.println("需要重新建表....count="+count);
        //容量增加一倍  capacity 增加到200 或者其他
        capacity=capacity*2;
        //新建
        Node [] newHashTable=new Node[capacity];
        //遍历
        for(int i=0;i<hashTable.length;i++ ){


            Node<K,V> oldNode=hashTable[i];
            while(oldNode!=null){
                //准备工作---依次取出
                Node<K,V> next=oldNode.next;

                //改变旧的地盘
                hashTable[i]=next;
                //安排新的地盘
                int newHash=getHash(oldNode.keyCode);
                Node<K,V> newNext=newHashTable[newHash];
                oldNode.next=newNext;
                newHashTable[newHash]=oldNode;

                //重新指向
                oldNode=hashTable[i];
            }

        }
        //循环过后 table 已经全部是指向了null
        hashTable=newHashTable;
        //改变上限 在这出错
        threshold=(int) (capacity*loaderFactor);

    }
}
