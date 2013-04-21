package qunitGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import oracle.AccessedDOMNode;
import oracle.Attribute;
import oracle.FunctionPoint;
import oracle.Variable;



public class QunitAssertion {
	
	public static enum AssertionType {ok, equal, deepEqual};
	private String assertionCodeForVariable="";
	private String assertioncodeForDom="";
	private ArrayList<String> assertionCodes=new ArrayList<String>();

	public QunitAssertion(){
		
	}
	
	public ArrayList<String> getAssertionCodes(){
		return assertionCodes;
	}
	public int getTotalNumberOfAssertions(){
		return assertionCodes.size();
	}
	
	public void makeQunitAssertionForVariable(String actual, String expected, AssertionType assertionType){

		String assertionCode="";
		if(assertionType.name().equals(AssertionType.ok)){
			
			assertionCode=assertionType.toString() + "(" + actual +" == " + expected + ", " + "" +")" + ";";
			
		}
		else{
			
			assertionCode=assertionType.toString() + "(" + actual +", " + expected + ", " + "" +")" + ";";
			
		}
		assertionCodeForVariable=assertionCode;
		assertionCodes.add(assertionCode);
	
		
		
	}
	
	public void makeQunitAssertionForDomNode(AccessedDOMNode expectedAccessedDomNode){

			
			String xpath=expectedAccessedDomNode.xpath;
			Set<Attribute> attrs=expectedAccessedDomNode.getAllAttibutes();
			String code=getSelectElementByXpathCode(xpath);
		
				
			String assertionCode=code+"\n";
			assertionCode+=AssertionType.ok.toString() + "(" +"node.length>0" + ", " + "" +")" + ";" + "\n";
			Iterator<Attribute> iter=attrs.iterator();
			while(iter.hasNext()){
				Attribute attr=iter.next();
				String attrName=attr.getAttrName();
				String attrValue=attr.getAttrValue();
				if(attrName.equals("tagName")){
					
					String actual="node.prop" + "(" +"'" + attrName + "'" + ")";
					String expected=attrValue;
					assertionCode+=AssertionType.equal.toString() + "(" + actual +", " + expected + ", " + "" +")" + ";";
					assertionCode+="\n";
					assertionCodes.add(assertionCode);
				}
				
				else{
					String actual="node.attr" + "(" +"'" + attrName + "'" + ")";
					String expected=attrValue;
					assertionCode=AssertionType.equal.toString() + "(" + actual +", " + expected + ", " + "" +")" + ";";
					assertionCode+="\n";
					assertionCodes.add(assertionCode);
				}
			}
				
			assertioncodeForDom=assertionCode;
			
		
			
			
		}


	

	
	private String getSelectElementByXpathCode(String xpath){
		String code="";
		/* div added because of <div id="qunit-fixture"></div> */
		String xpathToEvaluate="/div" + xpath;
		code= "evaluated=document.evaluate" + "(" + xpathToEvaluate + ", " + 
		"document" + ", " + "null" +", " +"XPathResult.ANY_TYPE" + ", " + "null" + ")" + ";" + "\n";
		code+= "node = $(evaluated.iterateNext());";
		return code;
	}
	
	public String getAssertionCodeForVariable(){
		return assertionCodeForVariable;
	}
	
	public String getAssertionCodeForDom(){
		return assertioncodeForDom;
	}
}

