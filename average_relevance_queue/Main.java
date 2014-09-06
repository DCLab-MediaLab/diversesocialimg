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
	//private List<double[]> relevance_qList = new ArrayList<double[]>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File input_folder_xml = null;
		File input_folder_txt = null;
		File output_folder = null;
		ElementRelevance e = new ElementRelevance();
		int n = 30;													//itt N elem
		
		try {
			
			input_folder_xml= new File("D://xml");
			input_folder_txt= new File("D://gt/rGT");


			File[] files = input_folder_xml.listFiles();
			String[] filenames_xml = new String[filesnumber]; //Mennyi a helyszín??? Ezt lehet változtatni, hogy mennyi a helyszín
			
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					filenames_xml[i]="D://xml/" +files[i].getName();
					//System.out.println("File " + files[i].getName());
				} else if (files[i].isDirectory()) {
					System.out.println("Directory " + files[i].getName());
				}
			}
			File[] files2 = input_folder_txt.listFiles();
			String[] filenames_txt = new String[filesnumber]; //Mennyi a helyszín??? Ezt lehet változtatni, hogy mennyi a helyszín
			
			for (int i = 0; i < files2.length; i++) {
				if (files2[i].isFile()) {
					filenames_txt[i]="D://gt/rGT/"+files2[i].getName();
					//System.out.println("File " + files2[i].getName());
				} else if (files2[i].isDirectory()) {
					System.out.println("Directory " + files2[i].getName());
				}
			}
			List<String> val = new ArrayList<String>();
			List<double[]> r_queue = new ArrayList<double[]>();
			
			double[]relevance_v= new double[filesnumber];
			double[] array=new double[numberofElement];
			double[] array_toA=new double[numberofElement];
			
			for(int index=0; index<filesnumber; index++){
				//System.out.println(filenames_xml[index]);
				
				val=e.readXml(filenames_xml[index]);
				relevance_v=e.searchId(filenames_txt[index], val);
				r_queue.add(relevance_v);
				
				
				}
			
			
			for(int index=0; index<filesnumber; index++){
				
				double szum= 0;
				//System.out.println("size: "+r_queue.size());
					for(int k=0; k<r_queue.size(); k++){
						for(int l=0; l<r_queue.get(k).length; l++){
							
							if(l==index){
								
								array_toA[index]=(Double) r_queue.get(k)[l];
								//System.out.println("Rel_nr: "+l+".dik eleme a" +k+".dik sorban: "+array_toA[index]);
								szum=szum+array_toA[index];
							}
						}
					}
					//System.out.println("szum: "+ szum);
					
					array[index]=szum/numberofElement;
					//System.out.println("átlag:"+array[index]);
			}
			
			
			GenerateCsv.generateCsvFile("D://csvF.csv", array);

		}catch(Exception ex) {
			ex.printStackTrace();
		}
	
	}
}
	