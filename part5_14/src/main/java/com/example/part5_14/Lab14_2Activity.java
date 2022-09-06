package com.example.part5_14;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Lab14_2Activity extends AppCompatActivity implements View.OnClickListener{
    Button contactsBtn;
    Button cameraDataBtn;
    Button cameraFileBtn;
    Button speechBtn;
    Button mapBtn;
    Button browserBtn;
    Button callBtn;

    TextView resultView;
    ImageView resultImageView;

    File filePath;

    int reqWidth;
    int reqHeight;
    int req;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        if (req == 10)
                        {
                            String string = data.getDataString();
                            resultView.setText(string);
                        }
                        else if (req == 20)
                        {
                            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                            resultImageView.setImageBitmap(bitmap);
                        }
                        else if (req == 30)
                        {
                            if (filePath != null)
                            {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inJustDecodeBounds = true;
                                try
                                {
                                    InputStream in = new FileInputStream(filePath);
                                    BitmapFactory.decodeStream(in, null, options);
                                    in.close();
                                    in = null;
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                final int height = options.outHeight;
                                final int width = options.outWidth;
                                int inSampleSize = 1;
                                if (height > reqHeight || width > reqWidth)
                                {
                                    final int heightRatio = Math.round((float)height/(float)reqHeight);
                                    final int widthRatio = Math.round((float)width/(float)reqWidth);
                                    inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
                                }
                                BitmapFactory.Options imgOptions = new BitmapFactory.Options();
                                imgOptions.inSampleSize = inSampleSize;
                                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath(), imgOptions);
                                resultImageView.setImageBitmap(bitmap);
                            }
                        }
                        else if (req == 40)
                        {
                            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            String string = results.get(0);
                            resultView.setText(string);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab14_2);

        resultView=(TextView)findViewById(R.id.resultView);
        contactsBtn=(Button)findViewById(R.id.btn_contacts);
        cameraDataBtn=(Button)findViewById(R.id.btn_camera_data);
        cameraFileBtn=(Button)findViewById(R.id.btn_camera_file);
        speechBtn=(Button)findViewById(R.id.btn_speech);
        mapBtn=(Button)findViewById(R.id.btn_map);
        browserBtn=(Button)findViewById(R.id.btn_browser);
        callBtn=(Button)findViewById(R.id.btn_call);
        resultImageView=(ImageView)findViewById(R.id.resultImageView);

        contactsBtn.setOnClickListener(this);
        cameraDataBtn.setOnClickListener(this);
        cameraFileBtn.setOnClickListener(this);
        speechBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        browserBtn.setOnClickListener(this);
        callBtn.setOnClickListener(this);
        resultImageView.setOnClickListener(this);

        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);
    }

    @Override
    public void onClick(View v) {
        if (v==contactsBtn)
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            Log.d("callback", "click");
            req = 10;
            activityResultLauncher.launch(intent);
        }
        else if (v==cameraDataBtn)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            req = 20;
            activityResultLauncher.launch(intent);
        }
        else if (v==cameraFileBtn)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                try
                {
                    //String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp";

                    File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);//new File(dirPath);
                    Log.d("click", dir.toString());
                    if (!dir.exists())
                    {
                        dir.mkdir();
                        Log.d("click", "create");
                    }

                    filePath = File.createTempFile("IMG",".jpg", dir);
                    if (!filePath.exists())
                    {
                        filePath.createNewFile();
                    }
                    Uri photoURI = FileProvider.getUriForFile(Lab14_2Activity.this, BuildConfig.APPLICATION_ID + ".provider", filePath);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    req = 30;
                    activityResultLauncher.launch(intent);
                }
                catch (Exception e)
                {
                    if (filePath == null)
                    {
                        Log.d("click", "null");
                    }
                    e.printStackTrace();
                }
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
        else if (v==speechBtn)
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "voice recognition test");
            req = 40;
            activityResultLauncher.launch(intent);
        }
        else if (v==mapBtn)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"));
            activityResultLauncher.launch(intent);
        }
        else if (v==browserBtn)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"));
            activityResultLauncher.launch(intent);
        }
        else if (v==callBtn)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:02-120"));
                activityResultLauncher.launch(intent);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        }
        else if (v==resultImageView)
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(Lab14_2Activity.this, BuildConfig.APPLICATION_ID+".provider", filePath);
            intent.setDataAndType(photoURI, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activityResultLauncher.launch(intent);
        }
    }
}
