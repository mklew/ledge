package org.objectledge.forms.internal.ui;

import org.objectledge.forms.ConstructionException;
import org.objectledge.forms.internal.model.InstanceImpl;
import org.objectledge.forms.internal.util.Util;
import org.xml.sax.Attributes;


/**
 * Formatted control implementation. Includes:
 * <ul>
 *      <li><code>input</code></li>
 *      <li><code>textarea</code></li>
 *      <li><code>secret</code></li>
 *      <li><code>output</code></li>
 * </ul>
 *
 * @author <a href="mailto:zwierzem@ngo.pl">Damian Gajda</a>
 * @version $Id: NodeControlFormatted.java,v 1.2 2005-06-24 10:33:07 zwierzem Exp $
 */
public class NodeControlFormatted extends NodeControl
{
    private int maxLength;

    public NodeControlFormatted(String type, Attributes atts)
    throws ConstructionException
    {
        super(type, atts);

        format = Util.getSAXAttributeVal(atts, "format");
        if(format != null)
        {
            //TODO: build a format depending on its form and field datatype.
            // SimpleDateFormat
            // DecimalFormat
            // MessageFormat ??
            //
        }
        maxLength = Util.createIntAttribute(atts, "maxLength", -1);
    }

    private String format;

    //------------------------------------------------------------------------
    // Control methods
    //
    public Object getValue(InstanceImpl instance)
    {
        Object value = super.getValue(instance);
		//if(value != null && value instanceof String && ((String)value).length() > 0)
		//{
			//TODO: use a format
		//}
        return value;
    }

    /**
     * @return Returns the maxLength.
     */
    public int getMaxLength()
    {
        return maxLength;
    }
}
