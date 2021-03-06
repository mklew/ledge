//
//Copyright (c) 2003, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.
//All rights reserved.
//
//Redistribution and use in source and binary forms, with or without modification, 
//are permitted provided that the following conditions are met:
//
//* Redistributions of source code must retain the above copyright notice, 
//this list of conditions and the following disclaimer.
//* Redistributions in binary form must reproduce the above copyright notice, 
//this list of conditions and the following disclaimer in the documentation 
//and/or other materials provided with the distribution.
//* Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
//nor the names of its contributors may be used to endorse or promote products 
//derived from this software without specific prior written permission.
//
//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
//AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
//WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
//INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
//BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
//OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
//WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
//ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
//POSSIBILITY OF SUCH DAMAGE.
//

package org.objectledge.parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A compound implementation of parameters.
 *
 * @author <a href="mailto:pablo@caltha.com">Pawel Potempski</a>
 * @author <a href="mailto:rafal@caltha.com">Rafal Krzewski</a>
 * @version $Id: CompoundParameters.java,v 1.8 2006-01-12 15:51:01 rafal Exp $
 */
public class CompoundParameters implements Parameters
{
    /** The underylying containers. */
    private List<Parameters> containers;

    /**
     * Constructs a copound parameter container.
     *
     * <p>The contatiners with lesser indexes will have precedence over the
     * conainer with greater indexes.</p>
     *
     * @param containers the containers.
     */
    public CompoundParameters(Parameters... containers)
    {
        this.containers = Arrays.asList(containers);
    }

    /**
     * Constructs a copound parameter container.
     *
     * <p>The contatiners with lesser indexes will have precenence over the
     * conainer with greater indexes.</p>
     *
     * @param list the containers.
     */
    public CompoundParameters(List<Parameters> list)
    {
        containers = list;
        Iterator<Parameters> i = list.iterator();
        while(i.hasNext())
        {
            Object obj = i.next();
            if(!(obj instanceof Parameters))
            {
                throw new ClassCastException(obj.getClass().getName());
            }
        }
    }

    
    /**
     * {@inheritDoc}
     */
    public boolean isDefined(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getParameterNames()
    {
        SortedSet<String> keys = new TreeSet<String>();
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            keys.addAll(Arrays.asList(c.getParameterNames()));
        }
        String[] result = new String[keys.size()];
        keys.toArray(result);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public String get(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.get(name);
            }
        }
        throw new UndefinedParameterException("Parameter '" + name + "'is undefined");
    }
    
    /**
     * {@inheritDoc}
     */
    public String get(String name, String defaultValue)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.get(name);
            }
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getStrings(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getStrings(name);
            }
        }
        return new String[0];
    }

    /**
     * {@inheritDoc}
     */
    public boolean getBoolean(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getBoolean(name);
            }
        }
        throw new UndefinedParameterException("Parameter '" + name + "'is undefined");
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean getBoolean(String name, boolean defaultValue)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getBoolean(name);
            }
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    public boolean[] getBooleans(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getBooleans(name);
            }
        }
        return new boolean[0];
    }

    /**
     * {@inheritDoc}
     */
    public Date getDate(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getDate(name);
            }
        }
        throw new UndefinedParameterException("Parameter '" + name + "'is undefined");
    }

    /**
     * {@inheritDoc}
     */
    public Date getDate(String name, Date defaultValue)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getDate(name, defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    public Date[] getDates(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getDates(name);
            }
        }
        return new Date[0];
    }
    
    /**
     * {@inheritDoc}
     */
    public int getInt(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getInt(name);
            }
        }
        throw new UndefinedParameterException("Parameter '" + name + "'is undefined");
    }
    
    /**
     * {@inheritDoc}
     */
    public int getInt(String name, int defaultValue)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getInt(name, defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    public int[] getInts(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getInts(name);
            }
        }
        return new int[0];
    }

    /**
     * {@inheritDoc}
     */
    public long getLong(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getLong(name);
            }
        }
        throw new UndefinedParameterException("Parameter '" + name + "'is undefined");
    }
    
    /**
     * {@inheritDoc}
     */
    public long getLong(String name, long defaultValue)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getLong(name, defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    public long[] getLongs(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getLongs(name);
            }
        }
        return new long[0];
    }

    /**
     * {@inheritDoc}
     */
    public float getFloat(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getFloat(name);
            }
        }
        throw new UndefinedParameterException("Parameter '" + name + "'is undefined");
    }
    
    /**
     * {@inheritDoc}
     */
    public float getFloat(String name, float defaultValue)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getFloat(name, defaultValue);
            }
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    public float[] getFloats(String name)
    {
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            if(c.isDefined(name))
            {
                return c.getFloats(name);
            }
        }
        return new float[0];
    }

    /**
     * {@inheritDoc}
     */
    public Parameters getChild(String prefix)
    {
        List<Parameters> list = new ArrayList<Parameters>();
        Iterator<Parameters> i = containers.iterator();
        while(i.hasNext())
        {
            Parameters c = i.next();
            list.add(c.getChild(prefix));
        }
        return new CompoundParameters(list);
    }
    
    //// ---------------------------------

    /**
     * {@inheritDoc}
     */
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(String name)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(String name, String value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(String name, Date value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(String name, float value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(String name, int value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void remove(String name, long value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Remove all parameters with a name contained in given set.
     *
     * @param keys the set of keys.
     */
    public void remove(Set<String> keys)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Remove all except those with a keys specified in the set.
     *
     * @param keys the set of names.
     */
    public void removeExcept(Set<String> keys)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, String value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, String[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, Date value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, Date[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, boolean value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, boolean[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, float value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, float[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, int value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, int[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, long value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void set(String name, long[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
	public void set(Parameters parameters)
	{
		throw new UnsupportedOperationException();
	}
	
    /**
     * {@inheritDoc}
     */
    public void add(String name, String value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, String[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, Date value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, Date[] values)
    {
        throw new UnsupportedOperationException();
    }
    
    /**
     * {@inheritDoc}
     */
    public void add(String name, boolean value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, boolean[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, float value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, float[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, int value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, int[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, long value)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(String name, long[] values)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void add(Parameters parameters, boolean overwrite)
    {
        throw new UnsupportedOperationException();
    }
}
