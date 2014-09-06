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
	
	public int n_element=30;												//itt N elem
	private double[] rToAverage = new double[n_element];
	//public List<Object> value=new ArrayList<Object>();
	//public List<String> values=new ArrayList<String>();
	public double[] relevance = new double[30];
	public List<double[]> relevance_nposition = new ArrayList<double[]>();
	public List<double[]> relevance_q = new ArrayList<double[]>();
	public List<String> readXml(String filename){
		List<String> values=new ArrayList<String>();
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
		return values;
	}
	
	public double readTxt(String xml_id, String filename, int n_e) throws IOException{
		FileReader readerFile = new FileReader(filename);
		BufferedReader buffer = new BufferedReader(readerFile);
		
		//System.out.println("size: " + values.size());
		//Iterator<Object> it=values.iterator();
		
		int count=0;
			while(true){
				String line = buffer.readLine();
				if (line ==null) break;
		    	if(xml_id.equals(Fv(line))){
		    		//System.out.println("Fvline: "+ Fv(line));
		    		//System.out.println("relevance_length: "+relevance.length);
		    	
		    		double fromString = Double.parseDouble(line.substring(line.length()-1));
		    		//System.out.println("relevancia ertek, xml helyes sorrendben ez lesz: "+fromString);
		    		relevance[n_e]=fromString;
		    		//System.out.println("relevance: "+n_e+".dik érték"+relevance[n_e]);
		    		
		    	}
		    	
			}
			buffer.close();
			return relevance[n_e];
			
	
	}
	public String Fv(String line) throws IOException{
		//System.out.println("line_hossz: "+line.length());
		String id = line.substring(0, line.length()-2);
   		
    	return id;
	}
	
	public double[] searchId(String filename, List<String> v) throws IOException{
		//Iterator<Object> it=values.iterator();
		int count = 0;
		double[] array= new double[30];
		for(int idx=0; idx<30; idx++){
			
			String next_id = v.get(idx);
			//System.out.println("next: "+next_id);
			array[idx]=readTxt(next_id, filename, count);
			//System.out.println("count: "+ count);
			count++;
			
		}
		return array;
		//System.out.println("size_rel"+relevance.size());
	}
	
	
	
	
	
}
