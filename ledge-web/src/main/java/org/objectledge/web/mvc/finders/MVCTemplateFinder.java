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
package org.objectledge.web.mvc.finders;

import org.objectledge.templating.Template;

/**
 * Finds templates that should be used for rendering specific views and web page components.
 *
 * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
 * @version $Id: MVCTemplateFinder.java,v 1.14 2005-07-22 17:25:53 pablo Exp $
 */
public interface MVCTemplateFinder
{
    // builders /////////////////////////////////////////////////////////////////////////////////
    
	/**
	 * Returns a result object containing reference to the found builder template for a given view
     * name. If no template is found, a <code>null</code> template is returned in the result object.
     * The <b>find</b>ing of the view tamplates uses the defaulting strategy based on
     * the {@link ViewFallbackSequence} name sequence generation.
	 * 
     * @param name view name to look up template for.
	 * @return found template with accompanying info.
     */
    public Result findBuilderTemplate(String name);

	// components /////////////////////////////////////////////////////////////////////////////////
    
	/**
	 * Returns an component template for a given component name. If no template is found, a
	 * <code>null</code> is returned.
	 * 
	 * @param name component name to look up template for.
	 * @return found template
	 */
	public Template getComponentTemplate(String name);
    
    /**
     * The search result for builder template find method.
     *  
     * @author <a href="mailto:dgajda@caltha.pl">Damian Gajda</a>
     */
    public static class Result
    {
        private final String originalView;
        private final Template template;
        private final String actualView;
        private final boolean last;
        
        /**
         * Creates a new Result instance.
         * 
         * @param originalView originally requested template.
         * @param template resolved template.
         * @param actualView the actual view associated with the resolved template.
         */
        public Result(String originalView, Template template, String actualView, boolean last)
        {
            this.originalView = originalView;
            this.template = template;
            this.actualView = actualView;
            this.last = last;
        }

        /**
         * @return Returns the original view used to search for the builder template.
         */
        public String getOriginalView()
        {
            return originalView;
        }

        /**
         * @return Returns the actual view name computed during search.
         */
        public String getActualView()
        {
            return actualView;
        }

        /**
         * @return Returns the found builder template.
         */
        public Template getTemplate()
        {
            return template;
        }
        
        /**
         * @return Tells whether fallback on view name was preformed during builder template search.
         */
        public boolean fallbackPerformed()
        {
            return !originalView.equals(actualView);
        }
        
        /**
         * @return Tell wether this result is the last element of the fallback sequece.
         */
        public boolean isLastFallback()
        {
            return last;
        }
    }
}
