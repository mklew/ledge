package org.objectledge.modules.views;

import org.objectledge.context.Context;
import org.objectledge.web.mvc.builders.EnclosingView;

/**
 * @author Marek Lewandowski <marek.m.lewandowski@gmail.com>
 * @since 10/25/12
 *        Time: 7:28 PM
 */
public abstract class BasicLedgeTopView extends BasicLedgeView
{
    /**
     * Constructs a builder instance.
     *
     * @param context application context for use by this builder.
     */
    public BasicLedgeTopView(Context context)
    {
        super(context);
    }

    @Override
    public EnclosingView getEnclosingView(String thisViewName)
    {
        return EnclosingView.TOP;
    }


}
