/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.mrane.zoomview;

import in.designlabs.instimap.R.drawable;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import com.mrane.zoomview.SubsamplingScaleImageView;


public class PinView extends SubsamplingScaleImageView {

    private PointF sPin;
    private Bitmap pin;
    Paint paint = new Paint();

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public void setPin(PointF sPin) {
        this.sPin = sPin;
        initialise();
        invalidate();
    }

    public PointF getPin() {
        return sPin;
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        //pin = BitmapFactory.decodeResource(this.getResources(), drawable.marker);
        float w = (density/420f) * pin.getWidth();
        float h = (density/420f) * pin.getHeight();
        pin = Bitmap.createScaledBitmap(pin, (int)w, (int)h, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAntiAlias(true);
        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isImageReady()) {
            return;
        }

        

        if (sPin != null && pin != null) {
            PointF vPin = sourceToViewCoord(sPin);
            float vX = vPin.x - (pin.getWidth()/2);
            float vY = vPin.y - pin.getHeight();
            canvas.drawBitmap(pin, vX, vY, paint);
        }

    }

}
