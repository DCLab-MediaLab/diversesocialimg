import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.w3c.dom.*;
import org.xml.sax.*;
import com.sun.org.apache.xerces.*;
import com.sun.org.apache.xerces.internal.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;

public class ElementRelevance {
	
	public static int n_element=30;												//itt N elem
	private static double[] rToAverage = new double[n_element];
	private static List<Object> values=new ArrayList<Object>();
	private static List<Double> relevance = new ArrayList<Double>();
	public static List<double[]> relevance_nposition = new ArrayList<double[]>();
	private static List<ArrayList> relevance_q = new ArrayList<ArrayList>();
	public static void readXml(String filename){
		
		try{
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("photos");
			//System.out.println("length: " + nList.getLength());
            
			Element docEl = doc.getDocumentElement();       
		    Node childNode = docEl.getFirstChild();     
		    while( childNode.getNextSibling()!=null){          
		        childNode = childNode.getNextSibling();         
		        if (childNode.getNodeType() == Node.ELEMENT_NODE) {         
		            Element childElement = (Element) childNode;             
		            //System.out.println("NODE num:-" + childElement.getAttribute("id"));
		            //System.out.println("NODE num:-" + childElement.getAttribute("rank"));
		            values.add(childElement.getAttribute("id"));
		            
		        }       
		    }
		
		}catch (Exception e) {
			e.printStackTrace();
	    }
		
	}
	
	public static void readTxt(String xml_id, String filename) throws IOException{
		FileReader readerFile = new FileReader(filename);
		BufferedReader buffer = new BufferedReader(readerFile);
		
		//System.out.println("size: " + values.size());
		Iterator<Object> it=values.iterator();
		
		int count=0;
			while(true){
				String line = buffer.readLine();
				if (line ==null) break;
		    	if(xml_id.equals(Fv(line))){
		    		//System.out.println(line.substring(line.length()-1));
		    		double fromString = Double.parseDouble(line.substring(line.length()-1));
		    		relevance.add(fromString);
		    		relevance.get(count);
		    		count++;
		    	}
		    	
			}		
	}
	public static String Fv(String line) throws IOException{
		String id = line.substring(0, 9);
    	if(id.substring(id.length()-1).equals(",")){
    		id=id.substring(0,8);
    		
    	}
    	return id;
	}
	
	public static void searchId(String filename) throws IOException{
		Iterator<Object> it=values.iterator();
		int count = 0;
		while(it.hasNext()){
			
			String next_id = (String) it.next();
			//System.out.println("next: "+next_id);
			readTxt(next_id, filename);
			//System.out.println("count: "+ count);
			count++;
			
		}
		//System.out.println("size_rel"+relevance.size());
	}
	
	public static void allRelevance(int t){
		relevance_q.add((ArrayList) relevance);
	}
	
	public static List Position(){
		//System.out.println("relevance_size: "+relevance_q.size());
		int count=0;
		//System.out.println("size_rel_q: "+ relevance_q.size());
		while(count!=n_element){
			for(int i =0; i<relevance_q.size(); i++){
				//System.out.println("elem" + i+": "+relevance_q.get(i).get(count) +" "+count);
				rToAverage[count]=(Double) relevance_q.get(i).get(count);
				//System.out.println("count: "+ count);
			}
			relevance_nposition.add(rToAverage);
			count++;
		}
		/*for(int idx=0; idx<relevance_nposition.size(); idx++){
			System.out.println("rel_npos: "+relevance_nposition.get(idx) );
		}*/
		
		return relevance_nposition;
	}
	
	/*public static void toAverage(int i, int j){
		rToAverage[i]=(Double) relevance_q.get(j).get(i);
	}*/
	
	
	
}
