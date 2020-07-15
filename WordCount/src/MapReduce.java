
import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;



public class MapReduce {
    File file1,file2;
	protected static int Chunks = 1;
	public MapReduce(File f1) throws FileNotFoundException,MatrixException {
        if (GetExtension(f1).contains("csv")) {
            CsvToTxt(f1);
            file1=new File(f1+".txt");
        }
        else if (GetExtension(f1).contains("txt")) {
            file1=f1;
        }
        else {
			 throw new MatrixException("Extension de fichier incompatible");
		 }
	}
	
	public MapReduce(File f1,File f2) throws FileNotFoundException, MatrixException{ 
		
		 if (GetExtension(f1).contains("csv") && GetExtension(f2).contains("txt")) {
	            CsvToTxt(f1);
	            file1=new File(f1+".txt");
	            file2 =f2;
	        }
		 else if (GetExtension(f1).contains("txt") && GetExtension(f2).contains("csv")) {
	            CsvToTxt(f2);
	            file2=new File(f2+".txt");
	            file1 =f1;
	        }
		 else if (GetExtension(f1).contains("csv") && GetExtension(f2).contains("csv")) {
	            CsvToTxt(f2);
	            file2=new File(f2+".txt");
	            CsvToTxt(f1);
	            file1=new File(f1+".txt");
	        }
		 else if (GetExtension(f1).contains("txt")&& GetExtension(f2).contains("txt")) 
		 {
		file1=f1;
		file2=f2;
		}
		 else {
			 throw new MatrixException("Extension de fichier incompatible");
		 }
	}
	
	public String getNom()
	{
		return this.file1.getName();
	}
	public String getNom2() { return this.file2.getName();}

	public void split (int maxRows) throws IOException
	{
	File source = file1;
    int i = 1;
    
        try(Scanner sc = new Scanner(source)){
            String line = null;
            int lineNum = 1;

            File splitFile = new File(this.file1.getName()+i+".txt");
            
            FileWriter myWriter = new FileWriter(splitFile);

            while (sc.hasNextLine()) {
            line = sc.nextLine();

                if(lineNum > maxRows){
                	Chunks++;
                    myWriter.close();
                    lineNum = 1;
                    i++;
                    splitFile = new File(this.file1.getName()+i+".txt");
                    myWriter = new FileWriter(splitFile);
                }
                myWriter.write(line);
                if(lineNum<maxRows)
                {
                    myWriter.write("\n");
                }
                lineNum++;
            }

            myWriter.close();
        }
	
}

    public void CsvToTxt(File f) throws FileNotFoundException
    {
    	Path p=f.toPath();
        File file2 =new File(p+".txt");
        FileInputStream fin = new FileInputStream(f);
        Scanner sc =new Scanner(fin);
        String line= null;
        String arr[]=null;
        PrintWriter pr;
        pr=new PrintWriter(file2);
        while (sc.hasNextLine())
        {
            line=sc.nextLine();
            arr=line.split(",");
            for (int i = 0; i < arr.length; i++)
            {
                pr.write(arr[i]+"\t");
            }
            pr.write("\n");
        }
        pr.close();
        sc.close();
    }
    public void deleteFiles()
    {
        File f= new File("");
        for (int i = 1; i <= Chunks; i++) {
            f = new File(this.getNom()+i+".txt");
            f.delete();
        }
    }
    public String GetExtension(File f)
    {
        if(f.getName().lastIndexOf(".") != -1 && f.getName().lastIndexOf(".") != 0)
            return f.getName().substring(f.getName().lastIndexOf(".") + 1);
        else
            return "";
    }

}
