// 
// Copyright (c) 2003, 2004, Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
// All rights reserved. 
//   
// Redistribution and use in source and binary forms, with or without modification,  
// are permitted provided that the following conditions are met: 
//   
// * Redistributions of source code must retain the above copyright notice,  
// this list of conditions and the following disclaimer. 
// * Redistributions in binary form must reproduce the above copyright notice,  
// this list of conditions and the following disclaimer in the documentation  
// and/or other materials provided with the distribution. 
// * Neither the name of the Caltha - Gajda, Krzewski, Mach, Potempski Sp.J.  
// nor the names of its contributors may be used to endorse or promote products  
// derived from this software without specific prior written permission. 
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
package org.objectledge.web.mvc.tools;

import org.objectledge.utils.StringUtils;

/**
 * The string manipulation tool.
 * 
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: StringTool.java,v 1.5 2004-10-24 12:36:10 pablo Exp $
 */
public class StringTool
{
    /**
     * The constructor. 
     */
    public StringTool()
    {
        super();
    }

    public String shorten(String str, int maxLength)
    {
        if(str.length() > maxLength)
        {
            StringBuffer buf = new StringBuffer(str);
            buf.setLength(maxLength-1);
            buf.append('\u2026'); // the ellipsis character - ie. 3 dots ...
            return buf.toString();
        }
        return str;
    }

    /**
     * 
     */
    public String shortenString(String source, int length, String suffix)
    {
        if(length >= source.length())
        {
            return source;
        }
        return source.substring(0, length)+suffix;
    }
    
    /** 
     * See the StringUtils class.
     * 
     * @param input the input string.
     * @return the output.
     */
    public String toOctalUnicode(String input)
    {
        return StringUtils.toOctalUnicode(input);
    }
    
    /**
	 * Convert int to long value.
	 *
	 * @param vaule the integer value.
	 * @return the long wrapper.
     */
    public Long getLongValue(int value)
    {
    	return new Long(value);
    }

    /**
	 * Convert string to long value.
	 *
	 * @param vaule the string value.
	 * @return the long wrapper.
     */
    public Long getLongValue(String value)
    {
    	return new Long(value);
    }
}
