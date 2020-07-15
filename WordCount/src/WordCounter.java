
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordCounter extends MapReduce implements Runnable {

	static ArrayList<String> words = new ArrayList<String>();
	static ArrayList<Integer> count = new ArrayList<Integer>();
	int threadNbr;
	private List<Thread> threadList=new ArrayList<>();



	
	public WordCounter(File n) throws FileNotFoundException, MatrixException {
		super(n);
	}
	public WordCounter(File n,int i) throws FileNotFoundException, MatrixException {
		super(n);threadNbr=i;
	}

	public File Reduce() throws IOException, InterruptedException, MatrixException {

		threadList.clear();
		for (int i = 1; i <= Chunks; i++) {
			Thread t=new Thread(new WordCounter(file1,i));
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

		// Creating a File object that represents the disk file.
		Path p = file1.toPath();
		File res = new File(p+"CountedWords.txt");
		FileWriter myWriter = new FileWriter(res);
		for (int i = 0; i < words.size(); i++) {
			myWriter.write(words.get(i)+ " : " +count.get(i) +"\n");
		}
		myWriter.close();
        
		deleteFiles();
		return res;
		
	}


	public static void main(String[] args) throws IOException, InterruptedException, MatrixException {
		
		WordCounter File1 = new WordCounter(new File("test.csv"));
		File1.split(2);
		File1.Reduce();
	
	}

	@Override
	public void run() {
		//create the input stream (recevoir le texte)
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file1.getName()+threadNbr+".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//go through the text with a scanner
		Scanner sc = new Scanner(fin);

		while (sc.hasNext()) {
			//Get the next word
			String nextString = sc.next();

			//Determine if the string exists in words
			if (words.contains(nextString)) {
				int index = words.indexOf(nextString);

				count.set(index, count.get(index)+1);
			}
			else {
				words.add(nextString);
				count.add(1);
			}
		}
		sc.close();
		try {
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//File file = new File(file1.getName()+threadNbr+".txt");
		//file.delete();
	}
}

