import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static int numberofElement = 30;					//hány N elemre nézze az átlagos relevancia átlagot
	public static int filesnumber = 30;						//mappában található fájlok száma (helyszín)
	private static List<ArrayList> relevance_qList = new ArrayList<ArrayList>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File input_folder_xml = null;
		File input_folder_txt = null;
		File output_folder = null;
		int n = 30;													//itt N elem
		/*String filetxt1 = "D://Pic/acropolis_athens rGT.txt";
		String filetxt2 = "D://Pic/cabrillo rGT.txt";
		String filexml1 = "D://Pic/acropolis_athens.xml";
		String filexml2 = "D://Pic/cabrillo.xml";*/
		try {
			
			input_folder_xml= new File("D://xml");
			input_folder_txt= new File("D://gt/rGT");


			File[] files = input_folder_xml.listFiles();
			String[] filenames_xml = new String[filesnumber]; //Mennyi a helyszín??? Ezt lehet változtatni, hogy mennyi a helyszín
			
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					filenames_xml[i]="D://xml/" +files[i].getName();
					System.out.println("File " + files[i].getName());
				} else if (files[i].isDirectory()) {
					System.out.println("Directory " + files[i].getName());
				}
			}
			File[] files2 = input_folder_txt.listFiles();
			String[] filenames_txt = new String[filesnumber]; //Mennyi a helyszín??? Ezt lehet változtatni, hogy mennyi a helyszín
			
			for (int i = 0; i < files2.length; i++) {
				if (files2[i].isFile()) {
					filenames_txt[i]="D://gt/rGT/"+files2[i].getName();
					System.out.println("File " + files2[i].getName());
				} else if (files2[i].isDirectory()) {
					System.out.println("Directory " + files2[i].getName());
				}
			}
			for(int index=0; index<filesnumber; index++){
				ElementRelevance.readXml(filenames_xml[index]);
				ElementRelevance.searchId(filenames_txt[index]);
				ElementRelevance.allRelevance(index);
				//relevance_qList.add((ArrayList) ElementRelevance.Position());
			}
	
			//ElementRelevance.Position();
			//System.out.println("szia");
			Average b = new Average();
			List<double[]>list = ElementRelevance.Position();
			double[] t = new double[n];
			int n_count = 0;
			while(n_count!=n){
					//list.add(ElementRelevance.Position());
					for(int idx=0; idx<list.size(); idx++){
						//System.out.println(b.averageQueue((double[]) relevance_qList.get(i).get(idx),numberofElement)); //itt N elem
						t[n_count]=b.averageQueue((double[]) list.get(idx),numberofElement);			//itt N elem
					}
				
				n_count++;
		
			//}
			GenerateCsv.generateCsvFile("D://csvF.csv", t);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
