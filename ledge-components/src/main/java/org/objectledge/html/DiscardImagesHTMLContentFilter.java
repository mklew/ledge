/*
 * Created on 2003-11-12
 */
package org.objectledge.html;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * An implementation of content filter which removes image tags from documents. 
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: DiscardImagesHTMLContentFilter.java,v 1.1 2005-01-12 20:44:39 pablo Exp $
 */
public class DiscardImagesHTMLContentFilter implements HTMLContentFilter
{
    /* (non-Javadoc)
     * @see net.cyklotron.cms.documents.HTMLContentFilter#filter(org.dom4j.Document)
     */
    @SuppressWarnings("unchecked")
    public Document filter(Document dom)
    {
        Document outDom = (Document)(dom.clone());
		List<Element> images = outDom.selectNodes("//IMG");
		for(Iterator<Element> i=images.iterator(); i.hasNext();)
		{
			Element element = i.next();
			Element parent = element.getParent();
			parent.remove(element);
		}
        return outDom;
    }
}
