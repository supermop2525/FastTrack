package ASYNCcalls;

import android.os.AsyncTask;
import android.util.Log;
import android.graphics.*;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class calls {
    private final String apiEndpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0";
    private final String subscriptionKey = "b5a3420af7d5493ebe54642996816f19";

    private final FaceServiceClient faceServiceClient = new FaceServiceRestClient(apiEndpoint, subscriptionKey);

    public void makeOnce() {
        AsyncTask<InputStream, String, Face[]> makeTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage="";
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            faceServiceClient.createFaceList("id1", "name1", "description");
                            publishProgress("Made...");
                            //Log.d("randd",""+faceServiceClient.getFaceList("id1").persistedFaces.length);
                            return null;
                        } catch (Exception e) {
                            Log.d("hereerror", e.toString());
                            return null;
                        }
                    }
                };

        makeTask.execute();
    }
    public void add(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, Face[]> addTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            faceServiceClient.addFacesToFaceList("id1",apiEndpoint,null,null);
                            //Log.d("randd",""+faceServiceClient.getFaceList("id1").persistedFaces.length);
                            return null;
                        } catch (Exception e) {
                            Log.d("hereerror", e.toString());
                            return null;
                        }
                    }
                };

        addTask.execute(inputStream);
    }

    public void getFaceListArray() {
        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            faceServiceClient.addFacesToFaceList("id1",apiEndpoint,null,null);
                            return null;
                            //Log.d("randd",""+faceServiceClient.getFaceList("id1").persistedFaces.length);
                        } catch (Exception e) {
                            Log.d("hereerror", e.toString());
                            return null;
                        }
                    }
                };

        detectTask.execute();
    }
}
