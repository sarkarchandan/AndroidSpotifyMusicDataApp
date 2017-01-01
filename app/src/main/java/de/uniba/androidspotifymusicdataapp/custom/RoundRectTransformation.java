package de.uniba.androidspotifymusicdataapp.custom;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by chandan on 01/01/2017.
 */
public class RoundRectTransformation  implements Transformation{

    @Override
    public Bitmap transform(Bitmap source) {

        int size = Math.max(source.getWidth(),source.getHeight());
        int x = (source.getWidth()-size)/2;
        int y = (source.getHeight()-size)/2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source,x,y,size,size);
        if(squaredBitmap!=source){
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size,size,source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader bitmapShader = new BitmapShader(squaredBitmap,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);
        float r = size/8f;
        canvas.drawRoundRect(new RectF(0,0,source.getWidth(),source.getHeight()),r,r,paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "rounded_corners";
    }
}
