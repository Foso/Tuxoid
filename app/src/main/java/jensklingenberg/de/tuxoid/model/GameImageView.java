package jensklingenberg.de.tuxoid.model;

import android.content.Context;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import jensklingenberg.de.tuxoid.model.element.Element;

/**
 * Created by jens on 22/7/17.
 */

public class GameImageView extends ImageView {

    public GameImageView(Context context, Element bit) {
        super(context);
        setImageBitmap(bit.getImage());

    }

    public GameImageView(Context context) {
        super(context);
    }

    public GameImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
