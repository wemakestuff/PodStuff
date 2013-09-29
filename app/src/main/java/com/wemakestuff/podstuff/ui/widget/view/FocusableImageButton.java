package com.wemakestuff.podstuff.ui.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

public class FocusableImageButton extends ImageButton {
	public FocusableImageButton(Context context) {
		super(context);
	}

	public FocusableImageButton(Context context, AttributeSet attrs) {
		super(context,attrs);
	}

	public FocusableImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setPressed(final boolean pressed) {
		if (pressed && getParent() instanceof View && ((View) getParent()).isPressed()) {
			return;
		}
		super.setPressed(pressed);
	}
}
