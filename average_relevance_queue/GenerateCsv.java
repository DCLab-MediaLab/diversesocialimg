import java.io.FileWriter;
import java.io.IOException;


public class GenerateCsv {
	public static void generateCsvFile(String sFileName, double[] average)
	   {
		try
		{
		    FileWriter writer = new FileWriter(sFileName);
	 
		    writer.append("AverageQueue;");
		    for(int i=0; i<average.length; i++){
		    	Double s1 = (Double)average[i];
		    	String s = s1.toString(s1);
		    	writer.append(s);
		    	writer.append(";");
		    }
		    
		   
		    
	 
		    //generate whatever data you want
	 
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	    }

}
