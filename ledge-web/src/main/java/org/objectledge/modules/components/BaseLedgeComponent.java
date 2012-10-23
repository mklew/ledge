package org.objectledge.modules.components;

import org.objectledge.context.Context;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;
import org.objectledge.web.mvc.builders.BuildException;
import org.objectledge.web.mvc.components.AbstractComponent;

/**
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/23/12
 *        Time: 12:16 PM
 */
public abstract class BaseLedgeComponent extends AbstractComponent
{

    /**
     * Constructs a component instance.
     *
     * @param context application context for use by this component.
     */
    public BaseLedgeComponent(Context context)
    {
        super(context);
    }

    @Override
    public String build(Template template) throws BuildException, ProcessingException
    {
        Parameters parameters = RequestParameters.getRequestParameters(context);
        TemplatingContext templatingContext = TemplatingContext.getTemplatingContext(context);
        MVCContext mvcContext = MVCContext.getMVCContext(context);
        HttpContext httpContext = HttpContext.getHttpContext(context);
        I18nContext i18nContext = I18nContext.getI18nContext(context);
        try
        {
            process(parameters, templatingContext, mvcContext, httpContext, i18nContext);
            return super.build(template);
        }
        catch(Exception e)
        {
            if(template != null)
            {
                throw new BuildException("Failed to build the component with template: "
                        + template.getName(), e);
            }
            throw new BuildException("Failed to build the component",e);
        }

    }

    /**
     * To be implemented in particular components.
     *
     * @param parameters the parameters
     * @param templatingContext the templating context.
     * @param mvcContext the mvc context.
     * @param httpContext
     * @param i18nContext
     * @throws ProcessingException if the processing in the component fails.
     */
    public abstract void process(Parameters parameters, TemplatingContext templatingContext,
                                 MVCContext mvcContext, HttpContext httpContext, I18nContext i18nContext)
            throws ProcessingException;
}
