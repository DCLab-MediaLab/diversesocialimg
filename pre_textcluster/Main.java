import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

	/**
	 * Xml file of location ids and location titles
	 */
	public static File file= null;


	/**
	 * Csv file of image_ids and metadata with tf-idf values
	 */
	public static File file2= null;

	/**
	 * the output file(.csv), which has 3 coloumns:
	 * loc_id;image_id;image_score
	 */
	public static File outfile= null;

	/**
	 * Map of location id and title assigns
	 */
	public static Map<Integer,String> m= new LinkedHashMap<>();

	/**
	 * List containing metadata and tf-idf
	 */
	private static List<String> meta=new ArrayList<>();

	/**
	 * Map of image id and its metadata
	 */
	public static Map<String,List<String>> metamap= new LinkedHashMap<>();

	/**
	 * Map of image id and its tf-idf value
	 */
	public static Map<String,Double> metamap_cnt= new LinkedHashMap<>();

	/**
	 * Map of all locations, image ids and its counted tf-idf scores
	 */
	public static Map<String,List<Map.Entry<String,Double>>> img_map_cnt= new LinkedHashMap<>();
	
	/**
	 * Map of location id and title assigns
	 */
	public static Map<String,List<String>> img_map=new LinkedHashMap<>();

	/**
	 * Multicore Solr instance
	 * the port number: 8983 (default)
	 */
	public static HttpSolrServer testImage = new HttpSolrServer("http://localhost:8983/solr/me2014_testset_imagedoc");

	/**
	 * img_id method to get images and its pois and put them into a map (img_map)
	 * 
	 * @param field - searching in the chosen field 
	 * @param term - searching term
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public static void img_id(String field,String term) throws MalformedURLException, SolrServerException {

		SolrQuery query = new SolrQuery();
		query.setQuery(field+":"+term);
		query.setFields("id");
		query.setRows(300);
		query.setStart(0);
		query.set("defType", "edismax");

		QueryResponse response = testImage.query(query);
		SolrDocumentList results = response.getResults();
				
		List<String> list = new ArrayList<>();
		for (int i = 0; i < results.size(); ++i) {
			String res= results.get(i).getFieldValues("id").toString();
			list.add(res.substring(1,res.length()-1));
		}
		img_map.put(term,list);

	}

	/**
	 * location id's number
	 */
	private static int num = 0;
	
	/**
	 * loaction's name (in the xml it is title)
	 */
	private static String title = null;

	/**
	 * Xmlparser which prints the necessary elements into a map
	 * @param nodeList - defines the current xml document's nodes
	 */
	private static void printNote(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				if( Arrays.asList("number").contains(tempNode.getNodeName())){
					num=Integer.parseInt(tempNode.getTextContent());
				}
				if( Arrays.asList("title").contains(tempNode.getNodeName())){
					title=tempNode.getTextContent();
					m.put(num,title);
				}

				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					printNote(tempNode.getChildNodes());
				}
			}
		}
	}

	/**
	 * Tokenizer which separates the source to units
	 * @param untokenedSource
	 * @param separator
	 * @return
	 */
	public static List<String> tokenizer(String untokenedSource, String separator){
		List<String> items = new ArrayList<String>(Arrays.asList(untokenedSource.split(separator)));
		return items;
	}

	public static void main(String[] args) {
		try {
			if(args.length>0){
				System.out.println("INPUT FILE provided: "+args[0]);
				file= new File(args[0]);
			}
			else{
				System.out.println("INPUT FILE is not provided!\n");
				return;
			}
			if(args.length>1){
				System.out.println("INPUT FILE2 provided: "+args[1]);
				file2= new File(args[1]);
			}
			else{
				System.out.println("INPUT FILE2 is not provided!\n");
				return;
			}
			if(args.length>2){
				System.out.println("OUTPUT FILE provided: "+args[2]+"\n");
				outfile= new File(args[2]);
			}
			else{
				System.out.println("OUTPUT FILE is not provided!\n");
				return;
			}

			/*	XmlParse
			 *	calls the printNote method to get location ids and titles from xml source (file)
			 */
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes());

			}

			Set<Map.Entry<Integer, String>> set = m.entrySet();
			
			System.out.println("Getting data from Solr! Please wait...\n");

			for (Map.Entry<Integer,String> me : set) {
				System.out.println("loc_id :"+me.getKey() +" loc_name : "+ me.getValue());

				/*	for solr search
				 *  whitespace charachters replaced with search one char (?)
				 *  beacause of punctuation eg.: doge_s_palace = doge's palace
				 */
				String term=me.getValue().replace('_', '?'); 
				
				try {
					img_id("poi",term);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("\n"+img_map.size()+" locations - all data retrieved from Solr");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		BufferedReader br = null;
		BufferedWriter bw = null;

		/*
		 * Reading lines from txt source (file2) of image and its metadata
		 * 
		 * Source example:
		 * img_id  term  tf  df  idf  term  tf  df  idf  term  tf  df  idf ...
		 * 982345  cat   1   11  0.09  dog  2   11  0.18 ...
		 */
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(file2.toString()));
			while((currentLine=br.readLine())!= null){

				meta=tokenizer(currentLine, " ");
				String img_id=meta.get(0);
				meta.remove(0);
				double all=0.0;
				for(int i=3;i<=meta.size();i+=4){
					all+=Double.parseDouble(meta.get(i));
				}
				all=all/((meta.size())/4);
				metamap.put(img_id, meta);	//have metamap, if needed for further boosting the image_score
				metamap_cnt.put(img_id,all);
				meta.add("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("\nretrieved "+metamap_cnt.size()+" images and counted scores from:"+args[1]);

		Set<Entry<String, List<String>>> set = img_map.entrySet();
		Set<Entry<String, Double>> set2 = metamap_cnt.entrySet();

		/**
		 * Getting all the images and its scores and put them on a list (img_list_cnt)
		 * Put locations (getting location id) and its images-scores to one map (img_map_cnt)
		 */
		for (Entry<String, List<String>> me : set) {
			List<Map.Entry<String,Double>> img_list_cnt= new ArrayList<>();
			for(String str : me.getValue()){
				for (Entry<String, Double> me2 : set2){
					if(str.equals(me2.getKey())){
						img_list_cnt.add(me2);
					}
				}
			}
			img_map_cnt.put(me.getKey(),img_list_cnt);
		}

		Set<Entry<String, List<Entry<String, Double>>>> endset = img_map_cnt.entrySet();
		
		BufferedWriter bw2=null;

		try {
			bw = new BufferedWriter(new FileWriter(outfile));
			bw.write("loc_id;img_id;img_score\n");
			for (Entry<String, List<Entry<String, Double>>> me : endset) {
				bw2 = new BufferedWriter(new FileWriter("final_"+me.getKey().replace('?', '_')+".csv"));
				bw2.write("loc_id;img_id;img_score\n");
				
				System.out.println("loc: "+me.getKey().replace('?', '_') +" have "+ me.getValue().size()+" images");
				
				for(Entry<Integer,String>  entrym: m.entrySet())
					if(me.getKey().replace('?', '_').equals(entrym.getValue()))
						for(Entry<String, Double> l : me.getValue()){
							bw.write(entrym.getKey()+";"+l.getKey()+";"+l.getValue()+"\n");
							bw2.write(entrym.getKey()+";"+l.getKey()+";"+l.getValue()+"\n");
						}
			}
			System.out.println("\nResult files in project dir.("+endset.size()+" files)\nAll result files are in one file: "+outfile);
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)bw.close();
				if (bw2!= null)bw2.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
