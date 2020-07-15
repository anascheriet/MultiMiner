

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Matrix extends MapReduce implements Runnable{
    static ArrayList<String> VecteurLines = new ArrayList<String>();
    int threadNbr;
    private List<Thread> threadList=new ArrayList<>();

    protected int[] nbrLnCol = {0,0};
    protected static double[] res;

    public Matrix(File n) throws FileNotFoundException, MatrixException {
        super(n);
    }
    public Matrix(File n,int i) throws FileNotFoundException, MatrixException {
        super(n);threadNbr=i;
    }
    public Matrix(File n,File m) throws FileNotFoundException, MatrixException{
        super(n,m);
    }
    


    public File Reduce() throws IOException, InterruptedException, MatrixException {
        threadList.clear();
        res=new double[Chunks];

        int[] nbrLnCol2={0,0};



            FileInputStream fin2 = new FileInputStream(file2);
            Scanner sc2 = new Scanner(fin2);

            while (sc2.hasNext()) {
                //Get the next word
                String nextString = sc2.next();

                nbrLnCol2[0]++;
                String [] arr = nextString.split(",");
                if(arr.length!=1) throw new MatrixException("un Vecteur ne peut pas avoir plus d'une colonne");
                nbrLnCol2[1]=arr.length;

                VecteurLines.add(nextString);
            }
            sc2.close();
            fin2.close();


        BufferedReader buffr = new BufferedReader(new FileReader(getNom()+"1.txt"));
        String line=buffr.readLine();
        nbrLnCol[1]=line.split(",").length;
        buffr.close();

        if(nbrLnCol[1]!=nbrLnCol2[0]){
            throw new MatrixException("Erreur, Nombre de Lignes du Vecteur n'est pas egal au nombre de colonnes de la Matrice");
        }

        for (int i = 1; i <= Chunks; i++) {
            Thread t=new Thread(new Matrix(file1,i));
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

        Path p = file1.toPath();
        File MatrixMultip = new File(p+"Multip.txt");	
        BufferedWriter buff =new BufferedWriter(new FileWriter(MatrixMultip));
        for(int k=0;k<res.length;k++)
        {
            buff.write(String.format("%.2f",res[k]));
            if(k!=res.length-1)
                buff.write(System.lineSeparator());

        }

        buff.close();
        
        return  MatrixMultip;
    }
    public static void main(String[] args) throws IOException, InterruptedException, MatrixException {

        Matrix a=new Matrix(new File("Matrix.txt"),new File("Vector.txt"));
        a.split(1);
        a.Reduce();
    }


    @Override
    public void run() {

        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file1.getName()+threadNbr+".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(fin);


        while (sc.hasNext()) {
            String nextString = sc.next();

            nbrLnCol[0]++;
            String [] arr = nextString.split(",");
            nbrLnCol[1]=arr.length;
            double c=0;
            for(int j=0;j<arr.length;j++)
            {
                c+=(Double.parseDouble(arr[j])*Double.parseDouble(VecteurLines.get(j)));

            }

            res[threadNbr-1]=c;
        }
        sc.close();
        try {
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(file1.getName()+threadNbr+".txt");
        file.delete();
    }
}
