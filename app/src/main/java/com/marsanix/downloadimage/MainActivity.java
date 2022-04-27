package com.marsanix.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    EditText inputUrl;
    ImageView imageView;
    Button btnDownload;

    DownloadManager downloadManager = null;
    long lastDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUrl = findViewById(R.id.inputUrl);
        btnDownload = findViewById(R.id.btnDownload);

        registerReceiver(onDownloadCompleted, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        btnDownload.setOnClickListener(v -> {
           String url = inputUrl.getText().toString();

            DownloadImage("Your Name", url);

        });
    }

    void DownloadImage(String fileName, String imageURL) {

        try {


            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(imageURL);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(fileName)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + fileName + ".jpg");

            lastDownload = downloadManager.enqueue(request);

            Toast.makeText(this, "Image downloaded success", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("DOWNLOAD_FAIL", "" + e.getMessage());
            Toast.makeText(this, "Image downloading failed", Toast.LENGTH_SHORT).show();
        }

    }

    BroadcastReceiver onDownloadCompleted = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(lastDownload));
//            String strColId = c.getString(c.getColumnIndex(DownloadManager.COLUMN_ID));
//            Toast.makeText(context, strColId, Toast.LENGTH_LONG).show();
        }
    };

}