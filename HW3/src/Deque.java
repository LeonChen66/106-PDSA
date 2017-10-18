/*
Created by Leon
HW3 Deque java implementation
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable{
    private Item[] data;
    private int size;
    private int nextFirst;
    private int nextLast;

    public Deque(){
        data = (Item[]) new Object[10];  // initial array size
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public boolean isEmpty(){// is the deque empty?
        return (size==0);
    }
    public int size(){// return the number of items on the deque
        return size;
    }

    private void resize(double scale){
        Item[] new_data = (Item[]) new Object[(int)(data.length*scale)];
        if (scale>1){
            for (int i=0;i<size;i++){
                new_data[i] = data[(i+nextFirst+1)%data.length];
            }
        }else{
            for (int i=0;i<size;i++){
                new_data[i] = data[(i+nextFirst+1)%data.length];
            }
        }
        data = new_data;
    }

    public void addFirst(Item item){// add the item to the front
        if(item==null)  throw new NullPointerException();
        data[nextFirst] = item;
        nextFirst = ((nextFirst-1)+data.length)%data.length;
        size++;
        if(size==data.length){
            resize(2.0);
            nextFirst = data.length-1;
            nextLast = size;
        }
    }

    public void addLast(Item item){// add the item to the end
        if(item==null)  throw new NullPointerException();
        data[nextLast] = item;
        nextLast = (nextLast+1)%data.length;
        size++;
        if(size==data.length){
            resize(2.0);
            nextFirst = data.length-1;
            nextLast = size;
        }
    }

    public Item removeFirst(){// remove and return the item from the front
        if(size==0){
            throw new NoSuchElementException();
        }
        if(size==data.length/2){
            resize(1.0/2);
            nextFirst = data.length-1;
            nextLast  = data.length;
        }
        Item result = data[(nextFirst+1)%data.length];
        data[(nextFirst+1)%data.length] = null;
        nextFirst =  (nextFirst+1) % data.length;
        size--;

        return result;
    }
    public Item removeLast(){// remove and return the item from the end
        if(isEmpty()) throw new NoSuchElementException();
        if(size==data.length/2){
            resize(1.0/2);
            nextFirst=size-1;
            nextLast=data.length;
        }
        Item result = data[((nextLast+data.length-1)%data.length)];
        data[((nextLast-1)+data.length)%data.length] = null;
        nextLast = ((nextLast-1)+data.length)%data.length;
        size--;

        return result;
    }

    public void printDeque(){

        for (int i= (nextFirst+1)%data.length;i!=nextLast;i++,i=i%data.length){
            System.out.print(data[i]+" ");
        }
        System.out.println();
    }

    public Item peekFirst() // return the item from the front; return null if the deque is empty.
    {
        if(isEmpty()){
            return null;
        }else{
            return data[(nextFirst+1)%data.length];
        }
    }
    public Item peekLast() // return the item from the end; return null if the deque is empty.
    {
        if(isEmpty()){
            return null;
        }else{
            return data[(nextLast+data.length-1)%data.length];
        }
    }

    public Iterator<Item> iterator(){// return an iterator over items in order from front to end
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item>{
        private int next_size = 0;
        private int next_i;
        ArrayIterator(){
            next_i = nextFirst;
        }
        public boolean hasNext(){
            return next_size < size;
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item result = data[(next_i + 1) % data.length];
            next_i = (next_i + 1) % data.length;
            next_size++;
            return result;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){// you can test your code here; we won't execute your main function.
        Deque<Integer> deque = new Deque<>();
        long time1,time2;
        time1 = System.currentTimeMillis();
        for (int i=0;i<10;i++){
            deque.addFirst(i);
        }
        for(int i=0;i<10;i++){
        StdOut.print(deque.removeFirst()+"\n");
        }
//        for (int i=0;i<10;i++){
//            deque.addLast(i);
//        }
//        deque.printDeque();
//        for (int i=0;i<20;i++){
//            StdOut.print(deque.removeLast()+"\n");
//        }
//        time2 = System.currentTimeMillis();
//        StdOut.print("Time is : "+(time2-time1)*1000);
//        Iterator<Integer> i = deque.iterator();
//        while(i.hasNext()){
//            StdOut.print(i.next()+"\n");
//        }
    }
}
