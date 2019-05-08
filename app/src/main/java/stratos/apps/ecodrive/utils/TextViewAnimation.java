package stratos.apps.ecodrive.utils;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TextViewAnimation extends Animation {
    private TextView textView;
    private int from;
    private int to;

    public TextViewAnimation(TextView textView, int from, int to) {
        super();
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        textView.setText(Math.round(value) + "%");
    }
}
