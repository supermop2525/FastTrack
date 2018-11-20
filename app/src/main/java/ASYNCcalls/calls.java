package ASYNCcalls;

import android.os.AsyncTask;
import android.util.Log;
import android.graphics.*;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.AddPersistedFaceResult;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.FaceList;
import com.microsoft.projectoxford.face.contract.VerifyResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

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
        AsyncTask<Face, String, AddPersistedFaceResult> addTask =
                new AsyncTask<Face, String, AddPersistedFaceResult>() {
                    String exceptionMessage = "";
                    @Override
                    protected AddPersistedFaceResult doInBackground(Face... params) {
                        try {
                            publishProgress("Adding...");
                            AddPersistedFaceResult result = faceServiceClient.addFacesToFaceList("id1",apiEndpoint,null,null);
                            return result;
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
                    protected void onPostExecute(AddPersistedFaceResult result) {
                        Log.d("Add","done");
                        if(!exceptionMessage.equals("")){
                            Log.d("Error in post Add",exceptionMessage);
                        }
                        if (result == null) return;
                    }
                };
        addTask.execute(face);
    }

    public FaceList getFaceListArray() {
        AsyncTask<InputStream, String, FaceList> getListTask =
                new AsyncTask<InputStream, String, FaceList>() {
                    String exceptionMessage = "";
                    @Override
                    protected FaceList doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            FaceList fl = faceServiceClient.getFaceList("id1");
                            return fl;
                            //Log.d("randd",""+faceServiceClient.getFaceList("id1").persistedFaces.length);
                        } catch (Exception e) {
                            Log.d("hereerror", e.toString());
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
                    protected void onPostExecute(FaceList result) {
                        Log.d("Get FaceList","done");
                        if(!exceptionMessage.equals("")){
                            Log.d("Error in post Get FL",exceptionMessage);
                        }
                        if (result == null) return;
                    }
                };
        getListTask.execute();
        while (getListTask.getStatus()!= AsyncTask.Status.FINISHED){
        }
        try {
            return getListTask.get();
        }
        catch (Exception e){
            Log.d("Error in return get",e.toString());
        }
        return null;
    }

    public VerifyResult verifyFaces(UUID one, UUID two) {
        AsyncTask<UUID[], String, VerifyResult> verifyTask =
                new AsyncTask<UUID[], String, VerifyResult>() {
                    String exceptionMessage = "";
                    @Override
                    protected VerifyResult doInBackground(UUID[]... params) {
                        try {
                            UUID[] faces=params[0];
                            publishProgress("Detecting...");
                            VerifyResult confidence = faceServiceClient.verify(faces[0],faces[1]);
                            return confidence;
                            //Log.d("randd",""+faceServiceClient.getFaceList("id1").persistedFaces.length);
                        } catch (Exception e) {
                            Log.d("hereerror", e.toString());
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
                    protected void onPostExecute(VerifyResult result) {
                        Log.d("Get FaceList","done");
                        if(!exceptionMessage.equals("")){
                            Log.d("Error in post Get FL",exceptionMessage);
                        }
                        if (result == null) return;
                    }
                };
        verifyTask.execute(new UUID[] {one,two});
        while (verifyTask.getStatus()!= AsyncTask.Status.FINISHED){
        }
        try {
            return verifyTask.get();
        }
        catch (Exception e){
            Log.d("Error in return get",e.toString());
        }
        return null;
    }
}
