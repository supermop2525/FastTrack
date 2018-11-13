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
    public void detectAndAdd(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, Face[]> detectAndaddTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                    null          // returnFaceAttributes:
                                /* new FaceServiceClient.FaceAttributeType[] {
                                    FaceServiceClient.FaceAttributeType.Age,
                                    FaceServiceClient.FaceAttributeType.Gender }
                                */
                            );
                            if (result == null){
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format(
                                    "Detection Finished. %d face(s) detected",
                                    result.length));
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        Log.d("progress",progress[0]);
                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        Log.d("Detect","done");
                        if(!exceptionMessage.equals("")){
                            Log.d("Error in post detect",exceptionMessage);
                        }
                        if (result == null) return;
                        add(result[0]);
                    }
                };
        detectAndaddTask.execute(inputStream);
    }

    public void add(Face face) {
        AsyncTask<InputStream, String, Face[]> addTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Adding...");
                            faceServiceClient.addFacesToFaceList("id1",apiEndpoint,null,null);
                            return null;
                            //Log.d("randd",""+faceServiceClient.getFaceList("id1").persistedFaces.length);
                        } catch (Exception e) {
                            Log.d("Add to FaceList failed:", e.toString());
                            return null;
                        }
                    }
                    @Override
                    protected void onPreExecute() {
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        Log.d("progress",progress[0]);
                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        Log.d("Add","done");
                        if(!exceptionMessage.equals("")){
                            Log.d("Rrror in post Add",exceptionMessage);
                        }
                        if (result == null) return;
                    }
                };

        addTask.execute();
    }

    public void getFaceListArray() {
        AsyncTask<InputStream, String, Face[]> getListTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            faceServiceClient.getFaceList("id1");
                            return null;
                            //Log.d("randd",""+faceServiceClient.getFaceList("id1").persistedFaces.length);
                        } catch (Exception e) {
                            Log.d("hereerror", e.toString());
                            return null;
                        }
                    }
                };

        getListTask.execute();
    }
}
