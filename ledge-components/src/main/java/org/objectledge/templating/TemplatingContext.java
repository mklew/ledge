//
// Copyright (c) 2003, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without modification, 
// are permitted provided that the following conditions are met:
//
// * Redistributions of source code must retain the above copyright notice, 
//	 this list of conditions and the following disclaimer.
// * Redistributions in binary form must reproduce the above copyright notice, 
//	 this list of conditions and the following disclaimer in the documentation 
//	 and/or other materials provided with the distribution.
// * Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
//	 nor the names of its contributors may be used to endorse or promote products 
//	 derived from this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
// IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
// BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
// OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
// POSSIBILITY OF SUCH DAMAGE.
//

package org.objectledge.templating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.objectledge.context.Context;

/**
 * Interface for all templating contexts.
 *
 * @author <a href="mailto:pablo@caltha.com">Pawel Potempski</a>
 * @version $Id: TemplatingContext.java,v 1.8 2005-12-20 09:09:31 pablo Exp $
 */
///CLOVER:OFF
public abstract class TemplatingContext
{
	/**
	 * Gets an object from the Context.
	 *
	 * @param key the object's key.
	 * @return the object
	 */
	public abstract Object get(String key);
		
	/**
	 * Puts an object into the context.
	 *
	 * @param key the object's key.
	 * @param value the object
	 * @return the previous object with that key.
	 */
	public abstract Object put(String key, Object value);
		
	/**
	 * Removes an object from the context.
	 *
	 * @param key the object's key.
	 * @return the object in the context.
	 */
	public abstract Object remove(String key);
		
	/**
	 * Checks if the context contains an object.
	 *
	 * @param key the object's key.
	 * @return <code>true</code> if object is stored in the context.
	 */
	public abstract boolean containsKey(String key);
	
	/**
	 * Returns keys of all objects.
	 *
	 * @return keys of all objects.
	 */
	public abstract String[] getKeys();
    
    /**
     * Get keys in alpha order.
     * 
     * @return keys of all objects.
     */
    public List<String> getSortedKeys()
    {
        ArrayList<String> list = new ArrayList<String>();
        String[] keys = getKeys();
        for(String key: keys)
        {
            list.add(key);
        }
        Collections.sort(list);
        return list;
    }
    
    // static access ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Retrieves the TemplatingContext from the thread's processing context.
     *
     * @param context the thread's processing context.
     * @return TemplatingContext the templating context.
     */
    public static TemplatingContext getTemplatingContext(Context context)
    {
        return context.getAttribute(TemplatingContext.class);
    }
}
