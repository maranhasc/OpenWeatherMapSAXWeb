package manejador;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import model.Time;

public class ManejadorWeb extends DefaultHandler {

	private XMLReader xr;
	private String lastContent;
	private InputSource is;
	private ArrayList<Time> times;
	private Time time;
	

	public ManejadorWeb(String strUrl) {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setNamespaceAware(true);
        SAXParser parser;
		try {
			parser = parserFactory.newSAXParser();
	        xr = parser.getXMLReader();
	        this.is=new InputSource(new URL(strUrl).openStream());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		xr.setContentHandler(this);
		xr.setErrorHandler(this);
	}

	public ArrayList<Time> parsear() {
		// En este caso como el xml esta en Internet debemos crear el stream y
		// con este el source
		try {
			// La llamada a este metodo es el que desencadena todo el proceso
			xr.parse(this.is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return times;
	}

	@Override
	public void startDocument() throws SAXException {
		times = new ArrayList<Time>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (localName.equals("time")) {
			time = new Time();
			time.setTo(attributes.getValue("to"));
			time.setFrom(attributes.getValue("from"));
		} else if (localName.equals("symbol")) {
			time.setProno(attributes.getValue("name"));
			time.setIcono(attributes.getValue("var"));
		}  else if (localName.equals("precipitation")) {
			time.setPreci(attributes.getValue("probability"));
		}  else if (localName.equals("temperature")) {
			time.setMin(attributes.getValue("min"));
			time.setMax(attributes.getValue("max"));
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (localName.equals("time")) {
			times.add(time);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		lastContent = String.valueOf(ch, start, length);
	}

}
