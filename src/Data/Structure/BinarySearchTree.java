package Data.Structure;

public class BinarySearchTree {

    private static final int DEFAULT_MAX=10;

    private Node[] nodes;
    private int root=-1;
    private int firstFree=0;
    private int size=DEFAULT_MAX;


    public BinarySearchTree() {
        nodes=new Node[DEFAULT_MAX];
    }
    public BinarySearchTree(int size){
        this.size=size;
        nodes= new Node[size];
    }

    public void insert(String data) throws TreeOverFlowException {
        Node tempNode=new Node(data);
        if (firstFree==size){
            throw new TreeOverFlowException("OverFlow!");
        }
        nodes[firstFree]=tempNode;
       // index++;
        if(root==-1){
            root=0;
        }
        else{
            setNodeLR(nodes[0],firstFree);
        }
        firstFree++;
    }

    public void setNodeLR(Node node,int index){
        int left=node.getLeft();
        int right=node.getRight();
        if (firstFree==nodes.length-1){
            new TreeOverFlowException("OverFlow!");
        }
        if(node.getData().compareTo(nodes[index].getData())>0){//if tempNode smaller than the node , go left
            if(left==-1){
                node.setLeft(index);
                return;
            }else
            setNodeLR(nodes[left],index);
        }else {
            if(right==-1){
                node.setRight(index);
                return;
            }else
            setNodeLR(nodes[right],index);
        }
    }

    public void inOrder(){
        inOrder(root);
    }
    public void inOrder(int index){
        if(index==-1)
            return;
        inOrder(nodes[index].getLeft());
        System.out.println(nodes[index].getData());
        inOrder(nodes[index].getRight());

    }

    public boolean remove(String data){
        int cursor=0;
        String position=null;
        //when the tree is empty
        if(root==-1){
            return false;
        }else {
               //find the target node and its parent need removing
                Node parentNode=fetchParent(data);
                Node currentNode;
                if(parentNode==null) {
                    return false;
                }else if(parentNode.getRight()==-1){
                    position="left";
                    cursor=parentNode.getLeft();
                    currentNode=nodes[cursor];
                }else if(parentNode.getLeft()==-1){
                    position="right";
                    cursor=parentNode.getRight();
                    currentNode=nodes[cursor];
                }else{
                    Node leftChild=nodes[parentNode.getLeft()];
                    Node rightChild=nodes[parentNode.getRight()];
                    if(rightChild.getData().equals(data)) {
                        currentNode = rightChild;
                        cursor=parentNode.getRight();
                        position="right";
                    }else if(leftChild.getData().equals(data)){
                        currentNode=leftChild;
                        cursor=parentNode.getLeft();
                        position="left";
                    }else{
                        currentNode=parentNode;
                    }
                }


                //removing
                    //if the current node has no child
                    if(currentNode.getRight()==-1&&currentNode.getLeft()==-1){
                        if(position.equals("left")){
                            parentNode.setLeft(-1);
                        }else parentNode.setRight(-1);
                    }
                    //if the current node has one child
                    else if(currentNode.getLeft()==-1&&currentNode.getRight()!=-1){
                            if(position.equals("left")){
                                parentNode.setLeft(currentNode.getRight());
                            }else parentNode.setRight(currentNode.getRight());
                        }
                    else if(currentNode.getLeft()!=-1&&currentNode.getRight()==-1){
                            if(position.equals("left")){
                                parentNode.setLeft(currentNode.getLeft());
                            }else parentNode.setRight(currentNode.getLeft());
                        }
                    //if the current node has two child
                    else {
                        //special case : remove the root
                        //parentCursor and cursor are the same one
                        if(position==null){
                             root=currentNode.getRight();
                        }else{
                            //let the parentNode points to the right child of currentNode
                            if(position.equals("left")){
                                parentNode.setLeft(currentNode.getRight());
                            }else parentNode.setRight(currentNode.getRight());
                        }

                        Node  tcn=nodes[currentNode.getRight()];
                        //let the smallest left child of current node's right subtree  point to the left child of current node
                        while(true){
                            if(tcn.getLeft()==-1){
                                tcn.setLeft(currentNode.getLeft());
                                break;
                            }
                            tcn=nodes[tcn.getLeft()];
                        }
                    }

                    //set the hole of the last element in the array
                    Node target=nodes[firstFree-1];
                    nodes[cursor]=target;
                    if(target.getData().equals(currentNode.getData())){
                        nodes[firstFree-1]=null;
                        firstFree=firstFree-1;
                        return true;
                    }
                    //then refresh the index,find the last node and its parent
                    Node parentOfLastNode=fetchParent(nodes[firstFree-1].getData());
                    if(parentOfLastNode.getLeft()==firstFree-1){
                        parentOfLastNode.setLeft(cursor);
                    }else{
                        parentOfLastNode.setRight(cursor);
                    }
                    nodes[firstFree-1]=null;
                    firstFree=firstFree-1;
                    return true;
                }


    }

    public boolean simpleRemove(String data){
        int cursor=0;
        int parentOfCursor=0;
        String position=null;
        //when the tree is empty
        if(root==-1){
            return false;
        }else {
            while (cursor!=-1){
                Node  currentNode=nodes[cursor];
                Node parentNode=nodes[parentOfCursor];
                if(currentNode.getData().compareTo(data)>0){
                    //go left
                    parentOfCursor=cursor;
                    cursor=currentNode.getLeft();
                    position="left";
                }else if(currentNode.getData().compareTo(data)<0){
                    //go right
                    parentOfCursor=cursor;
                    cursor=currentNode.getRight();
                    position="right";
                }else {
                    //find it!
                    //if the current node has no child
                    if(currentNode.getRight()==-1&&currentNode.getLeft()==-1){
                        if(position.equals("left")){
                            parentNode.setLeft(-1);
                        }else parentNode.setRight(-1);
                    }
                    //if the current node has one child
                    else if(currentNode.getLeft()==-1&&currentNode.getRight()!=-1){
                        if(position.equals("left")){
                            parentNode.setLeft(currentNode.getRight());
                        }else parentNode.setRight(currentNode.getRight());
                    }
                    else if(currentNode.getLeft()!=-1&&currentNode.getRight()==-1){
                        if(position.equals("left")){
                            parentNode.setLeft(currentNode.getLeft());
                        }else parentNode.setRight(currentNode.getLeft());
                    }
                    //if the current node has two child
                    else {
                        //special case : remove the root
                        //parentCursor and cursor are the same one
                        if(position==null){
                            root=currentNode.getRight();
                        }else{
                            //let the parentNode points to the right child of currentNode
                            if(position.equals("left")){
                                parentNode.setLeft(currentNode.getRight());
                            }else parentNode.setRight(currentNode.getRight());
                        }

                        Node  tcn=nodes[currentNode.getRight()];
                        //let the smallest left child of current node's right subtree  point to the left child of current node
                        while(true){
                            if(tcn.getLeft()==-1){
                                tcn.setLeft(currentNode.getLeft());
                                break;
                            }
                            tcn=nodes[tcn.getLeft()];
                        }
                    }
                    nodes[cursor]=new Node("null");
                    return true;
                }
            }
            //after searching all the element, no one matches
            return false;
        }

    }
    public String toString(){
        String nodesString="";
        int i=0;
        while (i<firstFree){
            nodesString=nodesString+i+":"+nodes[i].toString()+"\n";
            i++;
        }
        return "root="+root+"\n"+
                "firstFree="+firstFree+"\n"+nodesString;

    }

    public Node fetchParent(String data ){
        int cursor=root;
        int parentOfCursor=root;
        //when the tree is empty
        if(root==-1){
            return null;
        }else {
            while (cursor!=-1){
                Node  currentNode=nodes[cursor];
                if(currentNode.getData().compareTo(data)>0){
                    //go left
                    parentOfCursor=cursor;
                    cursor=currentNode.getLeft();
                }else if(currentNode.getData().compareTo(data)<0){
                    //go right
                    parentOfCursor=cursor;
                    cursor=currentNode.getRight();
                }else {
                    //find it!
                   return nodes[parentOfCursor];
                    }
                }
            }
            //after searching all the element, no one matches
            return null;
        }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

}

