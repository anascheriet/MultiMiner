
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cinema extends MapReduce implements Runnable{

    protected static ArrayList<String> movies = new ArrayList<String>();
    protected static ArrayList<Integer> moviesCount=new ArrayList<Integer>();
    protected static ArrayList<String> client = new ArrayList<String>();
    protected static ArrayList<Integer> clientCount=new ArrayList<Integer>();

    int threadNbr;
    private List<Thread> threadList=new ArrayList<>();



    public Cinema(File n) throws FileNotFoundException, MatrixException {
        super(n);
    }
    
    public Cinema(File n,int i) throws FileNotFoundException, MatrixException {
        super(n);threadNbr=i;
    }

    public ArrayList<File> Reduce() throws InterruptedException, IOException, MatrixException {
        ArrayList<File> results = new ArrayList<File>();
    	threadList.clear();
        for (int i = 1; i <= Chunks; i++) {
            Thread t=(new Thread(new Cinema(file1,i)));
            threadList.add(t);
        }
        for (Thread thread : threadList) {
            thread.start();
        }
        while(true)
        {
            int threadIsNotLive = 0;
            for (Thread t : threadList) {
                if (!t.isAlive()) {
                    ++threadIsNotLive;
                }
            }
            if(threadIsNotLive>0 && (threadList.size() == threadIsNotLive)){
                System.out.println("Progress ==> 100%");
                break;
            }
            else {
                System.out.print(String.format("Progress ==> %02d%%\n",(threadIsNotLive*100/threadList.size())));
                Thread.sleep(10);
            }
        }
        System.out.println("All threads finished!");

        
        results.add(showClient());
        results.add(showMovies());
        return results;
    }


    public File showMovies() throws IOException {
    	File mov=new File(file1.getPath()+"byMovie.txt");
        BufferedWriter buff =new BufferedWriter(new FileWriter(mov));
        for(int i=0;i<movies.size();i++)
        {
            buff.write("Les tickets du film '"+movies.get(i)+"' ont etaient vendu "+moviesCount.get(i)+" fois.");
            buff.write(System.lineSeparator());
        }
        buff.close();
        return mov;
    }
    
    public File showClient() throws IOException {
    	File cust=new File(file1.getPath()+"byCustomer.txt");
        BufferedWriter buff =new BufferedWriter(new FileWriter(cust));

        for(int i=0;i<client.size();i++)
        {
            buff.write("Le client '"+client.get(i)+"' a achete "+clientCount.get(i)+" ticket.");
            buff.write(System.lineSeparator());
        }
        buff.close();
        return cust;
    }

    public static void main(String[] args) throws IOException, InterruptedException, MatrixException {

        Cinema b=new Cinema(new File("Cine.txt"));
        b.split(500);
        b.Reduce();
        //b.deleteFiles();

    }

    @Override
    public void run() {
        BufferedReader buff = null;
        try {
            buff = new BufferedReader(new FileReader(file1.getName()+threadNbr+".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line= null;
        try {
            line = buff.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(line != null){
            String[] arr =line.split("\t");


            if(!movies.contains(arr[1]))
            {
                movies.add(arr[1]);
                moviesCount.add(1);
            }
            else {
                moviesCount.set(movies.indexOf(arr[1]),moviesCount.get(movies.indexOf(arr[1]))+1);
            }

            if(client.contains(arr[2]))
            {
                clientCount.set(client.indexOf(arr[2]),clientCount.get(client.indexOf(arr[2]))+1);
            }
            else {
                client.add(arr[2]);
                clientCount.add(1);
            }
            try {
                line=buff.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(file1.getName()+threadNbr+".txt");
        file.delete();
    }
}
