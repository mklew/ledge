package org.objectledge.modules.views;

import org.objectledge.context.Context;
import org.objectledge.web.mvc.builders.AbstractBuilder;
import org.objectledge.web.mvc.builders.EnclosingView;

/**
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/23/12
 *        Time: 2:01 PM
 */
public class RenderAjaxComponent extends AbstractBuilder
{
    /**
     * Constructs a builder instance.
     *
     * @param context application context for use by this builder.
     */
    public RenderAjaxComponent(Context context)
    {
        super(context);
    }

    @Override
    public EnclosingView getEnclosingView(String thisViewName)
    {
        return EnclosingView.TOP;
    }
}
