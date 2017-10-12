package edu.ecu.cs.exerciseapplication;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.util.List;

/**
 * Created by hunter on 9/16/17.
 */

public class ProfileFragment extends Fragment {
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private User mUser;
    private File mProfilePhoto;
    private static final int REQUEST_PHOTO=2;
    private static final String TAG="ProfileFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = new User("Hunter","Smith", 160.00,5.8,28,true);
        File filesDir = getActivity().getFilesDir();
        mProfilePhoto = new File(filesDir, mUser.getPhotoFilename());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        mPhotoButton = v.findViewById(R.id.photo_button);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        PackageManager packageManager = getActivity().getPackageManager();

        Log.d(TAG,"mProfilePhoto = " + mProfilePhoto);
        Log.d(TAG,"captureImage.resolveActivity(packageManager) = " + captureImage.resolveActivity(packageManager));

        boolean canTakePhoto = mProfilePhoto != null &&  captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        mPhotoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = FileProvider.getUriForFile(getActivity(),"edu.ecu.cs.exerciseapplication.fileprovider",mProfilePhoto);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities){
                    getActivity().grantUriPermission(activity.activityInfo.packageName,uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = v.findViewById(R.id.photo_view);
        //updatePhotoView();

        return v;
    }

//    public File getPhotoFile(User user){
//        File filesDir = getActivity().getFilesDir();
//        return new File(filesDir, user.getPhotoFilename());
//    }


    private void updatePhotoView() {
        if(mProfilePhoto == null || !mProfilePhoto.exists()){
            mPhotoView.setImageDrawable(null);
        }else{
            Bitmap bitmap = PictureUtils.getScaledBitmap(mProfilePhoto.getPath(),mPhotoView.getWidth(),mPhotoView.getHeight());
            mPhotoView.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_PHOTO){
            Uri uri = FileProvider.getUriForFile(getActivity(),"edu.ecu.cs.exerciseapplication.fileprovider",mProfilePhoto);

            getActivity().revokeUriPermission(uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }
}
