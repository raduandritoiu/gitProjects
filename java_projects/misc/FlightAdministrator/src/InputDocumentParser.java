import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class InputDocumentParser {
	public String filePath;
	public ArrayList<Review> reviews;
	public ArrayList<Opinion> myOpinions;
	public Document dom;
	
	public InputDocumentParser() {
		filePath = "d:\\crawled_data\\input_data_0.xml";
		reviews = new ArrayList<Review>();
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
				System.out.println("mmmm");
				//parse using builder to get DOM representation of the XML file
				dom = db.parse(filePath);
				System.out.println("a parsat\n");
				dom.getDocumentElement().normalize();
				System.out.println("a normalizat\n");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void parseDocument(){
			//get the root element
			System.out.println("12\n");
			this.parseXmlFile();
			System.out.println("12\n");
			Element docEle = dom.getDocumentElement();
			System.out.println("12\n");
			//get a nodelist of elements
			NodeList nl = docEle.getElementsByTagName("opinion");
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {

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
			String author = getTextValue(opEl,"autor");
			String date = getTextValue(opEl,"date");
			String opinonText = getTextValue(opEl,"text");


			//Create a new Employee with the value read from the xml nodes
			Opinion op = new Opinion(author, date, opinonText);
			return op;
		}

		private String getTextValue(Element ele, String tagName) {
			String textVal = null;
			NodeList nl = ele.getElementsByTagName(tagName);
			if(nl != null && nl.getLength() > 0) {
				Element el = (Element)nl.item(0);
				textVal = el.getFirstChild().getNodeValue();
			}
			return textVal;
		}
}
