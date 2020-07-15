

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Temperature extends MapReduce implements Runnable{

	private List<Thread> threadList=new ArrayList<>();
	int threadNbr;

	protected static HashMap<String, ArrayList<Double>> ByYear = new HashMap<String, ArrayList<Double>>();
	protected static HashMap<String, Double[][]> ByMonth = new HashMap<String, Double[][]>();

	String[] Mois = {"","Janvier","Fevrier","Mars","Avril","Mai","Juin","Juillet","Aout","Septembre","Octobre","Novembre","Decembre"};

	public Temperature(File n) throws FileNotFoundException, MatrixException {
		super(n);
	}
	public Temperature(File n,int i) throws FileNotFoundException, MatrixException {
		super(n);threadNbr=i;
	}

	
	public void convert() throws IOException
	{
		
		ArrayList<String> Cyears = new ArrayList<String>();
		ArrayList<Integer> Cmonths = new ArrayList<Integer>();
		ArrayList<String> Cdays = new ArrayList<String>();
		ArrayList<Double> CDegrees = new ArrayList<Double>();
		
		FileInputStream fin = new FileInputStream(file1);
		Scanner sc = new Scanner(fin);
		FileWriter myWriter = new FileWriter(this.getNom()+"converted"+".txt");
		
		//Go through the file and find the words
		int cpt=0;
		String nextline= "";
		while (sc.hasNextLine()) {
			cpt++;
		nextline = sc.nextLine();
		
		String Year = nextline.substring(15,19);
		
		String Smonth = nextline.substring(19,21);
		int Month;
		if (Smonth.startsWith("0")) {
			Month = Integer.parseInt(Smonth.substring(1));
		}
		else {
			Month =Integer.parseInt(Smonth);
		}

		String Day = nextline.substring(21,23);
		double temperature= Double.parseDouble((nextline.substring(87,93)))/10;
		Cyears.add(Year);
		Cmonths.add(Month);
		Cdays.add(Day);
		CDegrees.add(temperature);
		}
		
					for (int n = 0; n < CDegrees.size() ; n++) {
		
						 myWriter.write(Cdays.get(n)+" "+Mois[Cmonths.get(n)]+" "+Cyears.get(n)+" "+ CDegrees.get(n)+" \n");
					
					}
					myWriter.close();
				
	}

	public void split() {
		
		Chunks=1;
		File source = new File(this.getNom());
		int i = 0;

		try(Scanner sc = new Scanner(source)){
			String line;
			String year = "";
			File splitFile = new File(this.getNom()+i+".txt");

			FileWriter myWriter = new FileWriter(splitFile);

			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] arr =line.split(" ");
				if(!year.equals(arr[2])){
					Chunks++;
					myWriter.close();
					year = arr[2];
					i++;
					splitFile = new File(this.getNom()+i+".txt");
					myWriter = new FileWriter(splitFile);
				}else {
					myWriter.write(line + "\n");
				}
			}

			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File(this.getNom()+threadNbr+".txt");
		f.delete();
	}

	public File ShowByYear() throws IOException {
		Path p=this.file1.toPath();
		File yr = new File(p+"ReportByYear.txt");
		BufferedWriter buff =new BufferedWriter(new FileWriter(yr));
		for(String year:ByYear.keySet())
		{
			buff.write(String.format(year+" ==> Avg: %.2f",ByYear.get(year).get(2)));
			buff.write(System.lineSeparator());
			buff.write(String.format("         Max: %.2f",ByYear.get(year).get(1)));
			buff.write(System.lineSeparator());
			buff.write(String.format("         Min: %.2f",ByYear.get(year).get(0)));
			buff.write(System.lineSeparator());

		}
		buff.close();
		return yr;
	}

	public File ShowByMonth() throws IOException {
		String p=file1.getPath();
		File mt = new File(p+"ReportByMonth.txt");
		BufferedWriter buff =new BufferedWriter(new FileWriter(mt));
		for(String year:ByMonth.keySet())
		{
			buff.write(year+" ==> ");
			buff.write(System.lineSeparator());
			for(int i=1;i<ByMonth.get(year).length;i++)
			{

				buff.write(String.format("         "+Mois[i]+" ==> Avg: %.2f",ByMonth.get(year)[i][2]));
				buff.write(System.lineSeparator());
				buff.write(String.format("                       Max: %.2f", ByMonth.get(year)[i][1]));
				buff.write(System.lineSeparator());
				buff.write(String.format("                       Min: %.2f",ByMonth.get(year)[i][0]));
				buff.write(System.lineSeparator());

			}

		}
		buff.close();
		return mt;
	}
	
		public ArrayList<File> Reduce() throws InterruptedException, IOException, MatrixException {

            ArrayList<File> results =new ArrayList<File>();
			threadList.clear();
			for (int i = 1; i < Chunks; i++) {
				Thread t = (new Thread(new Temperature(file1,i)));
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

			results.add(ShowByYear());
			results.add(ShowByMonth());
			
			return results;
		}

	public static void main(String[] args) throws IOException, InterruptedException, MatrixException {
		// TODO Auto-generated method stub 
	
        Temperature t1 = new Temperature(new File("WeatherDataSet.txt"));
        t1.convert();
        t1 = new Temperature(new File(t1.getNom()+"converted.txt"));
        t1.split();
        ArrayList<File>results=t1.Reduce();
        System.out.println(results.get(0));
        System.out.println(results.get(1));
	}

	@Override
	public void run() {
		int year =0;
		int linesNbr=0;

		ArrayList<String> months = new ArrayList<String>();
		ArrayList<String> days = new ArrayList<String>();
		ArrayList<Double> Degrees = new ArrayList<Double>();

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

		while(line != null) {
			linesNbr++;

			String[] inputs = line.split(" ");
			Degrees.add(Double.parseDouble(inputs[3]));
			days.add(inputs[0]);
			year=Integer.parseInt(inputs[2]);
			months.add(inputs[1]);

			try {
				line = buff.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		double total = 0;
		double maxyear=0;
		double minyear=0;
		for (int i=1;i<=linesNbr;i++)
		{
			if(Degrees.get(i-1)<minyear)
			{
				minyear=Degrees.get(i-1);
			}
			if(Degrees.get(i-1)>maxyear)
			{
				maxyear=Degrees.get(i-1);
			}
			total+=Degrees.get(i-1);
		}
		ArrayList<Double> tmp=new ArrayList<Double>();
		tmp.add(minyear);
		tmp.add(maxyear);
		tmp.add(total/linesNbr);
		ByYear.put(Integer.toString(year),tmp);
		File file = new File(file1.getName()+threadNbr+".txt");
		file.delete();

		double []cpt= {0,0,0,0,0,0,0,0,0,0,0,0,0};
		double[]Maxvalue= {0,0,0,0,0,0,0,0,0,0,0,0,0};
		double[]Minvalue= {0,0,0,0,0,0,0,0,0,0,0,0,0};
		double []degrees ={0,0,0,0,0,0,0,0,0,0,0,0,0};

		for (int k = 0; k<months.size();k++)
		{
			for (int i = 1; i < Mois.length; i++) {

				if(months.get(k).contains(Mois[i])) {

					if(Maxvalue[i] < Degrees.get(k)){
						Maxvalue[i] = Degrees.get(k);
					}

					if(Minvalue[i] > Degrees.get(k)){
						Minvalue[i] = Degrees.get(k);
					}

					degrees[i]+=Degrees.get(k);
					cpt[i]++;

				}
			}
		}

		Double[][] tmp2 = new Double[degrees.length][3];

		for (int i = 1; i < degrees.length; i++) {
			tmp2[i][0]=Minvalue[i];
			tmp2[i][1]=Maxvalue[i];
			tmp2[i][2]=degrees[i]/cpt[i];

			ByMonth.put(Integer.toString(year),tmp2);
		}
		
	}

}