package in.workarounds.instimap.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by manidesto on 03/02/15.
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            Log.e("ImageDownloader", "Invalid link, " + e.getMessage());
        } catch (IOException e){
            Log.e("ImageDownloader", "IOException, " + e.getMessage());
        }

        return mIcon;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
