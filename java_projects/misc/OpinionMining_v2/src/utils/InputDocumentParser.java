package utils;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import common.model.Opinion;


public class InputDocumentParser {
	public String filePath;
	public ArrayList<Opinion> myOpinions;
	public Document dom;
	
	public InputDocumentParser() {
//		filePath = "d:\\input_data_0.xml";
		filePath = "d:\\in.xml";
		myOpinions = new ArrayList<Opinion>();
	}
	/*
	public void parseXML(String path) {
		try {
			File file = new File(path);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			NodeList nodeLst = doc.getElementsByTagName("review");
			System.out.println("Information of all employees");

			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node reviewNode = nodeLst.item(s);
		    
				Node authorNode = reviewNode.getE
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
		  
		           Element fstElmnt = (Element) fstNode;
		      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("firstname");
		      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		      NodeList fstNm = fstNmElmnt.getChildNodes();
		      System.out.println("First Name : "  + ((Node) fstNm.item(0)).getNodeValue());
		      NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("lastname");
		      Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
		      NodeList lstNm = lstNmElmnt.getChildNodes();
		      System.out.println("Last Name : " + ((Node) lstNm.item(0)).getNodeValue());
		    }

		  }
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		 }
		} 
		*/
		
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(filePath);
			dom.getDocumentElement().normalize();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void parseDocument(){
		//get the root element
		this.parseXmlFile();
		
		Element docEle = dom.getDocumentElement();
		//get a nodelist of elements
		NodeList nl = docEle.getElementsByTagName("opinion");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0 ; i < nl.getLength();i++) {

				//get the employee element
				Element el = (Element)nl.item(i);

				//get the Employee object
				Opinion e = getOpinion(el);

				//add it to list
				myOpinions.add(e);
			}
		}
	}
	
	
	public Opinion getOpinion(Element opEl) {
		//for each <employee> element get text or int values of
		//name ,id, age and name
		String author 		= getTextValue(opEl,"autor");
		String date 		= getTextValue(opEl,"date");
		String opinonText 	= getTextValue(opEl,"text");
		String preClass 	= getTextValue(opEl,"preClass");

		Opinion op = new Opinion(author, date, opinonText);
		if (preClass != null) {
			op.preclassification = Integer.parseInt(preClass);
		}
		return op;
	}
	
	
	private String getTextValue(Element elem, String tagName) {
		String textVal = null;
		NodeList nl = elem.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		if (textVal != null)
			textVal = textVal.trim();
		return textVal;
	}
}
