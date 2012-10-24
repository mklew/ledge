package org.objectledge.modules.views;

import org.objectledge.context.Context;
import org.objectledge.i18n.I18nContext;
import org.objectledge.parameters.Parameters;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;
import org.objectledge.web.mvc.builders.AbstractBuilder;
import org.objectledge.web.mvc.builders.BuildException;
import org.objectledge.web.mvc.builders.EnclosingView;

/**
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/23/12
 *        Time: 11:38 AM
 */
public abstract class BasicLedgeView extends AbstractBuilder
{
    /**
     * Constructs a builder instance.
     *
     * @param context application context for use by this builder.
     */
    public BasicLedgeView(Context context)
    {
        super(context);
    }

    @Override
    public String build(Template template,  String embeddedBuildResults) throws BuildException, ProcessingException
    {
        Parameters parameters = RequestParameters.getRequestParameters(context);
        TemplatingContext templatingContext = TemplatingContext.getTemplatingContext(context);
        MVCContext mvcContext = MVCContext.getMVCContext(context);
        HttpContext httpContext = HttpContext.getHttpContext(context);
        I18nContext i18nContext = I18nContext.getI18nContext(context);
        templatingContext.put("mvcContext", mvcContext);
        templatingContext.put("parameters", parameters);
        templatingContext.put("httpContext", httpContext);
        templatingContext.put("i18nContext", i18nContext);
        try
        {
            process(parameters, templatingContext, mvcContext, httpContext, i18nContext);
            return super.build(template, embeddedBuildResults);
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
