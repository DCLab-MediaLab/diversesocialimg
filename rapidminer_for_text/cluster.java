package cluster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cluster {

	public static List<String> tokenizer(String untokenedSource, String separator){
		List<String> items = new ArrayList<String>(Arrays.asList(untokenedSource.split(separator)));
		return items;
	}

	public static void main(String [] args){

		File input_folder= new File(args[0]);
		File output_folder= new File(args[1]);
		output_folder.mkdir();
		File file= null;
		File[] files = input_folder.listFiles();

		if(files.length!=0)
			for(int index=0;index<files.length;++index){
				file= new File(files[index].toString());
				if(files[index].toString().endsWith(".csv")){
					//String inputName=file.getName().split(".csv")[0].substring(1);
					System.out.println("File"+index+": "+file.getName());

					BufferedReader br= null;
					BufferedWriter bw= null;

					try {
						String currentLine;
						br = new BufferedReader(new FileReader(file));
						bw= new BufferedWriter(new FileWriter(output_folder+"//"+file.getName()));
						while((currentLine=br.readLine())!= null){
							List<String> list = new ArrayList<>();
							list=tokenizer(currentLine,";");
							list.remove(3);
							String cluster=list.get(2).replaceAll("\\D+","");
							list.set(2,cluster);
							String base=null;
							if(list.get(1).contains("E")){
								base=list.get(1).split("E")[0].replaceAll("\\D+","");

								int pow=Integer.parseInt(list.get(1).split("E")[1]);

								System.out.println(list.get(1)+ " base :"+base);
								System.out.println("pow: "+(pow)+" - "+(base.length()-1));
								int bl=base.length();
								if(base.length()-1<pow){
									System.out.println("little");
									for(int i=0;i<pow-(bl-1);++i){
										base=base+"0";
										System.out.println("index: "+i+" base: "+base);}
								}
							}
							else
								base=Integer.toString((int)Double.parseDouble(list.get(1)));

							list.set(1,base);
							System.out.println("new id: "+list.get(1));
							list.set(0,list.get(0).substring(0,list.get(0).length()-2));
							for(int i=0;i<list.size();++i){
								bw.write(list.get(i));
								if(i!=2)
									bw.write(";");
							}
							bw.write("\n");

						}

					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (br != null)br.close();
							if (bw != null)bw.close();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
	}
}
