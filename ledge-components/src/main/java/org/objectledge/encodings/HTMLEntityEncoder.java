// 
//Copyright (c) 2003, 2004 Caltha - Gajda, Krzewski, Mach, Potempski Sp.J. 
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

package org.objectledge.encodings;

import org.objectledge.encodings.encoders.CharEncoder;
import org.objectledge.encodings.encoders.CharEncoderHTMLEntity;
import org.objectledge.encodings.encoders.CharEncoderUTF;

/**
 * Tool for encoding HTML text to a text which supports a chosen encoding using HTML entities.
 * If an encoding does not support a character code, a HTML or numeric entity is being generated
 * for this character, if a character is supported it is not changed.
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: HTMLEntityEncoder.java,v 1.14 2005-05-17 07:40:12 zwierzem Exp $
 */
public class HTMLEntityEncoder extends AbstractEncoder
{
    private static final CharEncoderHTMLEntity HTML_ENTITY_ENCODER = new CharEncoderHTMLEntity();

    /**
     * Encodes a given text as an attribute with given encoding.
     *
     * @param text  Text to be encoded
     * @param encodingName name of a chosen encoding.
     * @param doubleQuoteQuote set to <code>true</code> if attribute is quoted
     *      using " (double quote character) set to <code>false</code>
     *      if attribute is quoted using ' (apostrophe character)
     * @return          encoded text
     */
    public String encodeAttribute( String text, String encodingName, boolean doubleQuoteQuote )
    {
        if(text == null)
        {
            return null;
        }
        if(text.length() == 0)
        {
            return text;
        }

		CharEncoder charsetEncoder = getCharsetEncoder(encodingName);

        // START: convert string
        StringBuilder buf = new StringBuilder(text.length());
        for (int i=0, s=text.length(); i < s; i++)
        {
            // get a character from input String
            char c = text.charAt(i);
            // check if character is a special XML attribute character
            if(c == '"' && doubleQuoteQuote ||
               c == '\'' && !doubleQuoteQuote ||
               c == '&' || c == '<' || c == '>')
            {
                encodeEntity(c, buf);
            }
            else
            // try to encode using normal encoding
            if(charsetEncoder != null
                            && !(charsetEncoder instanceof CharEncoderUTF)
                            && charsetEncoder.encode(c) == null)
            {
                // if not, encode it using entity encoding
                encodeEntity(c, buf);
            }
            else
            {
                // return it as is - in this case a character will be encoded while
                // encoding a string to a byte array
                buf.append(c);
            }
        }

        return buf.toString();
    }

    /**
     * Encodes a given text as an attribute with given encoding and default quote character -
     * <code>&quot;</code>.
     *
     * @param text  Text to be encoded
     * @param encodingName name of a chosen encoding.
     * @return          encoded text
     */
    public String encodeAttribute( String text, String encodingName )
    {
        return encodeAttribute(text, encodingName, true);
    }

    /**
     * Encodes a given text with given encoding.
     *
     * @param htmlText  Text to be encoded
     * @param encodingName name of a chosen encoding.
     * @return          encoded text
     */
    public String encodeHTML( String htmlText, String encodingName )
    {
        if(htmlText == null)
        {
            return null;
        }
        if(htmlText.length() == 0)
        {
            return htmlText;
        }

		CharEncoder charsetEncoder = getCharsetEncoder(encodingName);

        // pass for unknown and Unicode encodings
        if(charsetEncoder == null || charsetEncoder instanceof CharEncoderUTF)
        {
            return htmlText;
        }

        // START: convert string
        StringBuilder buf = new StringBuilder(htmlText.length());
        for (int i=0, s=htmlText.length(); i < s; i++)
        {
            // get a character from input String
            char c = htmlText.charAt(i);
            // check if it is posible to encode it using current charset
            if(charsetEncoder.encode(c) == null)
            {
                // if not, try to encode it using entity encoding
                encodeEntity(c, buf);
            }
            else
            {
                // return it as is - in this case a character will be encoded while
                // encoding a string to a byte array
                buf.append(c);
            }
        }

        return buf.toString();
    }

	// implementation ----------------------------------------------------------------------------
	
    private void encodeEntity(char c, StringBuilder buf)
    {
        // encode it using entity encoding
        char[] encodedChar = HTML_ENTITY_ENCODER.encode(c);

        buf.append('&');
        if(encodedChar != null)
        {
            buf.append(encodedChar);
        }
        else
        {
            // return it as a numeric entity
            buf.append('#');
            buf.append(Short.toString((short)c));
        }
        buf.append(';');
    }
}
