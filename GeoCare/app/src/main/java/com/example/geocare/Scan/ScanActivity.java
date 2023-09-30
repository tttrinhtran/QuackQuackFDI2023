package com.example.geocare.Scan;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geocare.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_CAMERA_PERMISSION = 123;
    private String  currentPhotoPath;
    TextView resultTextView;
    ImageView button, imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        resultTextView = findViewById(R.id.textview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check camera permission before launching the camera
                if (checkCameraPermission()) {
                    String fileName = "photo";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    try {
                        File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);

                        currentPhotoPath = imageFile.getAbsolutePath();

                        Uri imageUri = FileProvider.getUriForFile(ScanActivity.this, "com.example.geocare.fileprovider", imageFile);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        startActivityForResult(intent, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    requestCameraPermission();
                }
            }
        });

    }

    private boolean checkCameraPermission() {
        int result = ContextCompat.checkSelfPermission(this, CAMERA);
        return result == android.content.pm.PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

//    @Override
//    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        ScanActivity.super.onActivityResult(requestCode, resultCode,data);
//        if (requestCode == 1 && resultCode == RESULT_OK){
//            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
//
//            imageView.setImageBitmap(bitmap);
//
//            InputImage image = InputImage.fromBitmap(bitmap);
//
//            recognizeText(image);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageUri = Uri.fromFile(new File(currentPhotoPath));

            InputImage image = null;
            try {
                image = InputImage.fromFilePath(this, imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            recognizeText(image);

            imageView.setImageURI(imageUri);
        }
    }


    private void recognizeText(InputImage image) {

        // [START get_detector_default]
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        // [END get_detector_default]

        // [START run_detector]
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            //StringBuilder extractedText = new StringBuilder();
                            String extractedText = new String();

                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                // [START_EXCLUDE]
                                // [START get_text]

                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                    Rect boundingBox = block.getBoundingBox();
                                    Point[] cornerPoints = block.getCornerPoints();
                                    String text = block.getText();
                                    extractedText +=  "\n" + text;
                                }
//                                    for (Text.Line line : block.getLines()) {
//                                        extractedText.append(block.getText()).append("\n");
//                                        for (Text.Element element : line.getElements()) {
//                                            //extractedText.append(line.getText()).append("\n");
//                                            for (Text.Symbol symbol : element.getSymbols()) {
//                                                  extractedText.append(element.getText()).append("\n");
//                                            }
//                                        }
//                                    }
//                                }
                                // [END get_text]
                                // [END_EXCLUDE]
                                resultTextView.setText(extractedText.toString());
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
        // [END run_detector]
    }

    private void processTextBlock(Text result) {
        // [START mlkit_process_text_block]
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                    for (Text.Symbol symbol : element.getSymbols()) {
                        String symbolText = symbol.getText();
                        Point[] symbolCornerPoints = symbol.getCornerPoints();
                        Rect symbolFrame = symbol.getBoundingBox();
                    }
                }
            }
        }
        // [END mlkit_process_text_block]
    }

    private TextRecognizer getTextRecognizer() {
        // [START mlkit_local_doc_recognizer]
        TextRecognizer detector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        // [END mlkit_local_doc_recognizer]

        return detector;
    }

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String fileName = "photo";
//                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//                try {
//                    File imageFile = File.createTempFile(fileName,".jpg", storageDirectory);
//
//                    currentPhotoPath = imageFile.getAbsolutePath();
//
//                    Uri imageUri = FileProvider.getUriForFile(ScanActivity.this,"com.example.geocare.fileprovider", imageFile);
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//
//                    startActivityForResult(intent,1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    @Override
//    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        ScanActivity.super.onActivityResult(requestCode, resultCode,data);
//        if (requestCode == 1 && resultCode == RESULT_OK){
//            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
//
//            imageView.setImageBitmap(bitmap);
//        }
//    }
//

}