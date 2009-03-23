package org.objectledge.html;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.cyberneko.html.HTMLConfiguration;
import org.cyberneko.html.filters.ElementRemover;
import org.cyberneko.html.filters.Purifier;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;
import org.jcontainer.dna.Logger;
import org.objectledge.ComponentInitializationError;

/** Implementation of the DocumentService.
 *
 * @author <a href="mailto:zwierzem@ngo.pl">Damian Gajda</a>
 * @version $Id: HTMLServiceImpl.java,v 1.8 2005-12-30 11:46:03 rafal Exp $
 */
public class HTMLServiceImpl
	implements HTMLService
{
    final private Map<String, Configuration> cleanupProfiles = new HashMap<String, Configuration>();

    public HTMLServiceImpl(Logger logger, Configuration config)
        throws ComponentInitializationError, ConfigurationException
    {
        Configuration[] profilesDefs = config.getChildren("cleanupProfile");

        for(Configuration profileDef : profilesDefs)
        {
            String name = profileDef.getAttribute("name");
            cleanupProfiles.put(name, profileDef);
        }
    }

    private ElementRemover getElementRemover(String profileName)
    {
        ElementRemover elementRemover = new ElementRemover();
        Configuration[] acceptElements = cleanupProfiles.get(profileName).getChild("acceptElements")
            .getChildren("element");
        Configuration[] removeElements = cleanupProfiles.get(profileName).getChild("removeElements")
            .getChildren("element");

        try
        {
            for(int i = 0; i < acceptElements.length; i++)
            {

                String element = acceptElements[i].getAttribute("name");
                String[] attrs = null;

                Configuration[] attrDefs = acceptElements[i].getChildren("attribute");
                if(attrDefs.length > 0)
                {
                    attrs = new String[attrDefs.length];
                    for(int j = 0; j < attrDefs.length; j++)
                    {
                        attrs[j] = attrDefs[j].getAttribute("name");
                    }
                }
                elementRemover.acceptElement(element, attrs);
            }

            for(int i = 0; i < removeElements.length; i++)
            {
                String element = removeElements[i].getAttribute("name");
                elementRemover.removeElement(element);
            }

            return elementRemover;

        }
        catch(ConfigurationException e)
        {
            throw new IllegalArgumentException("Invalid configuration", e);
        }
    }
    
    public List<String> getCleanupProfileNames()
    {   
        return new ArrayList<String>(cleanupProfiles.keySet());
    }        
    
    /**
     * {@inheritDoc}
     */
    @Override
	public org.dom4j.Document emptyDom4j()
    {
        DocumentFactory factory = DocumentFactory.getInstance();
        org.dom4j.Document document = factory.createDocument();
        Element html = document.addElement("HTML");
        html.addElement("HEAD").addElement("TITLE");
        html.addElement("BODY");
        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document textToDom4j(String html) throws HTMLException
    {
        try
        {
            XMLParserConfiguration parser = new HTMLConfiguration();
            Dom4jDocumentBuilder dom4jBuilder = new Dom4jDocumentBuilder();
            XMLDocumentFilter[] filters = { new Purifier(), dom4jBuilder };
            parser.setProperty("http://cyberneko.org/html/properties/filters", filters);

            XMLInputSource source = new XMLInputSource("", "", "", new StringReader(html), "UTF-8");
            parser.parse(source);

            return dom4jBuilder.getDocument();
        }
        catch(Exception e)
        {
            throw new HTMLException("failed to parse HTML document", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document textToDom4j(String html,  Writer errorWriter,
        Properties tidyConfiguration) throws HTMLException
    {
        try
        {
            XMLParserConfiguration parser = new HTMLConfiguration();
            Dom4jDocumentBuilder dom4jBuilder = new Dom4jDocumentBuilder();
            XMLDocumentFilter[] filters = { new Purifier(), dom4jBuilder };
            parser.setProperty("http://cyberneko.org/html/properties/filters", filters);
            parser.setFeature("http://cyberneko.org/html/features/report-errors", true);
            ValidationErrorCollector errorCollector = new ValidationErrorCollector(errorWriter);
            parser.setErrorHandler(errorCollector);

            XMLInputSource source = new XMLInputSource("", "", "", new StringReader(html), "UTF-8");
            parser.parse(source);

            return !errorCollector.errorDetected() ? dom4jBuilder.getDocument() : null;
        }
        catch(Exception e)
        {
            throw new HTMLException("failed to parse HTML document", e);            
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void dom4jToText(org.dom4j.Document dom4jDoc, Writer writer, boolean bodyContentOnly)
    throws HTMLException
    {       
        OutputFormat format = new OutputFormat();
        format.setXHTML(true);
        format.setExpandEmptyElements(true);
        format.setTrimText(false);
        format.setIndent(false);
        HTMLWriter htmlWriter = new HTMLWriter(writer, format);
        try
        {
            if(!bodyContentOnly)
            {
                htmlWriter.write(dom4jDoc);
            }
            else
            {
                for(Node node : (List<Node>)dom4jDoc.getRootElement().element("BODY").content())
                {
                    if(node instanceof Element)
                    {
                        htmlWriter.write((Element)node);
                    }
                    if(node instanceof Text)
                    {
                        writer.append(node.getText());
                    }
                    if(node instanceof Comment)
                    {
                        writer.append("<!--").append(node.getText()).append("-->");
                    }
                }
            }
            writer.flush();
        }
        catch(IOException e)
        {
            throw new HTMLException("Could not serialize the document", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String collectText(Document html)	
    {
    	HTMLTextCollectorVisitor collector = new HTMLTextCollectorVisitor();
    	html.accept(collector);
    	return collector.getText();
    }


    // helper classes
    
    private final class ValidationErrorCollector
        implements XMLErrorHandler
    {
        private final Writer errorWriter;
        
        private boolean errorDetected = false;
    
        private ValidationErrorCollector(Writer errorWriter)
        {
            this.errorWriter = errorWriter;
        }
    
        @Override
        public void fatalError(String domain, String key, XMLParseException exception)
            throws XNIException
        {
            throw exception;
        }
    
        @Override
        public void error(String domain, String key, XMLParseException exception)
            throws XNIException
        {
            errorDetected = true;
            report("error", exception);
        }
    
        @Override
        public void warning(String domain, String key, XMLParseException exception)
            throws XNIException
        {
            report("warning", exception);
        }
    
        public boolean errorDetected()
        {
            return errorDetected;
        }
        
        private void report(String severity, XMLParseException exception)
            throws XNIException
        {
            try
            {
                errorWriter.append(severity).append(" at ");
                if(exception.getExpandedSystemId().length() > 0)
                {
                    errorWriter.append(exception.getExpandedSystemId()).append(" ");
                }
                errorWriter.append("line ").append(
                    Integer.toString(exception.getLineNumber()));
                errorWriter.append(" column ").append(
                    Integer.toString(exception.getColumnNumber())).append(": ");
                errorWriter.append(exception.getMessage()).append("\n");
                errorWriter.flush();
            }
            catch(IOException e)
            {
                throw new XNIException(e);
            }
        }
    }
}