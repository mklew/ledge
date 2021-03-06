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

package org.objectledge.table.generic;

import org.objectledge.table.TableFilter;
import org.objectledge.table.TableRowSet;
import org.objectledge.table.TableState;

/**
 * This class provides some utility methods for {@link TableRowSet} implementations.
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: BaseRowSet.java,v 1.5 2006-03-16 17:57:03 zwierzem Exp $
 */
public abstract class BaseRowSet<T> implements TableRowSet<T>
{
    /** Table state defining the behaviour of this row set. */
    protected TableState state;

    /** Holds a value of maximum expansion depth for displayed tree. */
    protected int maxDepth;

    /** Table of filters used by the rowset to prepare a list of rows. */
    protected TableFilter<T>[] filters;

    /**
     * construct the object.
     *
     * @param state the state of the table instance
     * @param filters a list of filters to be used while creating the rows set
     */
    public BaseRowSet(TableState state, TableFilter<T>[] filters)
    {
        this.state = state;
        this.filters = filters;

        maxDepth = state.getMaxVisibleDepth();
        if(maxDepth == 0)
        {
            maxDepth = Integer.MAX_VALUE;
        }
    }

	/**
	 * {@inheritDoc}
	 */
    public TableState getState()
    {
        return state;
    }

    // utility methods ///////////////////////////////////////////////////////

	/**
	 * Checks whether the given depth value is smaller than maximal depth value.
	 * @param depth depth value
	 * @return <code>true</code> if depth is ok
	 */
    protected boolean checkDepth(int depth)
    {
        return depth < maxDepth;
    }

    /**
     * Check whether the object is a part of the tree view, with respect to
     * all defined filters.
     *
     * @param object the object to check.
     * @return <code>true</code> if accepted.
     */
    protected boolean accept(T object)
    {
        if(filters != null)
        {
            for(int i=0; i<filters.length; i++)
            {
                if(!filters[i].accept(object))
                {
                    return false;
                }
            }
        }
        return true;
    }
}
