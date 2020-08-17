package Data.Structure;

public class MyQueue<E> {
    private E[] list;
    private static final int DEFAULT_MAXIMUM=100;
    private int first;
    private int firstFree;
    private int length;
    public MyQueue() {
        firstFree=0;
        first=0;
        length=0;
        list=(E[]) new Object[DEFAULT_MAXIMUM];
    }
    public boolean insert(E object){
        //判断是不是已经满了
        if(length==DEFAULT_MAXIMUM){

            return false;
        }

       list[firstFree]=object;
       //更新firstFree
       if(firstFree==DEFAULT_MAXIMUM) firstFree=0;
       else  firstFree++;
       //更新length
        length++;
        return true;
    }
    public boolean isEmpty(){
        return length==0;
    }
    public E remove(){
        if(isEmpty()){
            System.out.println("是空的");
            return null;
        }
        //把第一个临时储存
        E temp=list[first];
        //更新first
        if(first==DEFAULT_MAXIMUM) first=0;
        else
        first++;
        //更新length
        length--;
        return temp;
    }

    public E getFront(){
        return list[first];
    }

    public int length(){
        return length;
    }

    public String toString(){
        String str=list[first]+"\n";
        if(first<firstFree){
           for(int i =first+1;i<firstFree;i++){
               str=str+list[i]+"\n";
           }
        }else{
            //第一段从first到END
            for(int i=first+1;i<DEFAULT_MAXIMUM;i++){
                str=str+list[i]+"\n";
            }for(int i=0;i<firstFree;i++){
                str=str+list[i]+"\n";
            }
        }
        return str;
    }

    /*public int getIndex(E object){
        String str=list[first]+"\n";
        if(first<firstFree){
            for(int i =first+1;i<firstFree;i++){
               if(object.equals(list[i])){
                   return i-first-1;
               }
            }
        }else{
            //第一段从first到END
            for(int i=first+1;i<DEFAULT_MAXIMUM;i++){
                if(object.equals(list[i])){
                    return i-first-1;
                }
            }for(int i=0;i<firstFree;i++){
                if(object.equals(list[i])){
                    return DEFAULT_MAXIMUM-first+i+1;
                }
            }
        }
        return -1;
    }*/

    public E[] getList() {
       return list;
    }
}
