package pl.caltha.forms.internal.ui;

import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;

import pl.caltha.forms.ConstructionException;
import pl.caltha.forms.internal.model.InstanceImpl;
import pl.caltha.forms.internal.util.Util;

/**
 * Implementation of Select controls.
 *
 * @author <a href="mailto:zwierzem@ngo.pl">Damian Gajda</a>
 * @version $Id: NodeControlSelect.java,v 1.1 2005-01-19 06:55:28 pablo Exp $
 */
public class NodeControlSelect extends NodeControl
{

    public NodeControlSelect(String type, Attributes atts)
    throws ConstructionException
    {
        super(type, atts);

        selectUI = Util.getSAXAttributeVal(atts, "selectUI");
        size = Util.createIntAttribute(atts, "size", 1);
    }

    private HashMap itemsByValue = new HashMap();

    void addItemByValue(NodeControlSelectItem item)
    {
        if(!this.itemsByValue.containsValue(item))
        {
            this.itemsByValue.put(item.getValue(), item);
        }
    }

    //------------------------------------------------------------------------
    //attributes
    /** UI type for Select control. */
    private String selectUI;
    private int size;

    //------------------------------------------------------------------------
    //access methods for attributes
    public String getSelectUI()
    {
        return selectUI;
    }

    public int getSize()
    {
        return size;
    }

    /** Returns true if given item is selected in a given instance.
     * This method is called by select items. */
    boolean getSelected(InstanceImpl instance, NodeControlSelectItem item)
    {
        Set selectedValues = getSelectedValuesSet(instance);
        // Return true for selected item/value
        return selectedValues.contains(item.getValue());
    }

    /** Key for storing selected values map for this Select element.
     * Selected values is not connected with definitionUIPath, because
     * it is an instance property as opposed to UI state property
     */
    protected String SELECTED_VALUES = "select.selectedValues";

    private Set getSelectedValuesSet(InstanceImpl instance)
    {
        org.dom4j.Node contextNode = ((ReferenceSingle)ref).getContextNode(instance);
        Set selectedValues = (Set)(instance.getStateValue(contextNode, SELECTED_VALUES));

        // 1. build selected values if null
        if(selectedValues == null)
        {
            selectedValues = new java.util.HashSet();
            // 1.1. set value from instance
            String value = ref.getValue(contextNode);
            setSelectedValuesValue(selectedValues, value);
            // 1.2. Store selectedValues in state
            instance.setStateValue(contextNode, SELECTED_VALUES, selectedValues);
        }

        // 2. return selected values
        return selectedValues;
    }

    private void setSelectedValuesValue(Set selectedValues, String value)
    {
        // check for null value and set it
        if(value != null)
        {
            // It Tokenizes a value ONCE for this contextNode
            // it speeds up getSelected
            StringTokenizer tokenizer = new StringTokenizer(value);

            while(tokenizer.hasMoreTokens())
            {
                selectedValues.add(tokenizer.nextToken());
            }
        }
    }

    //------------------------------------------------------------------------
    // Control methods
    //
    void setValue(InstanceImpl instance, String value)
    {
        // 1. Set value in instance
        super.setValue(instance, value);

        // 2. Get selected values
        Set selectedValues = getSelectedValuesSet(instance);
        // 3. clean old value
        selectedValues.clear();
        // 4. set new value
        setSelectedValuesValue(selectedValues, value);
    }
}
