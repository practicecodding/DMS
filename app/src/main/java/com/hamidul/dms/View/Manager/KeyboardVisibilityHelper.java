package com.hamidul.dms.View.Manager;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

public class KeyboardVisibilityHelper implements ViewTreeObserver.OnGlobalLayoutListener{
    private final View rootView;
    private final RecyclerView recyclerView;
    private final OnKeyboardVisibilityListener listener;
    public interface OnKeyboardVisibilityListener {
        void onKeyboardVisibilityChanged(boolean visible, int keyboardHeight);
    }

    public KeyboardVisibilityHelper(View rootView, RecyclerView recyclerView, OnKeyboardVisibilityListener listener) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
        this.listener = listener;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }
    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        int screenHeight = rootView.getRootView().getHeight();
        int keypadHeight = screenHeight - r.bottom;

        boolean isVisible = keypadHeight > screenHeight * 0.15;

        // Notify listener
        listener.onKeyboardVisibilityChanged(isVisible, keypadHeight);

        // Scroll focused EditText into view
        if (isVisible) {
            View focusedView = rootView.findFocus();
            if (focusedView instanceof EditText) {
                int[] location = new int[2];
                focusedView.getLocationOnScreen(location);
                int focusedBottom = location[1] + focusedView.getHeight();
                if (focusedBottom > r.bottom) {
                    recyclerView.scrollBy(0, focusedBottom - r.bottom + 20); // small padding
                }
            }
        }

    }

    public void detach() {
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
}
