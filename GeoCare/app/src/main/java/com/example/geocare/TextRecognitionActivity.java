//package com.example.geocare;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//
//import android.graphics.Point;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.mlkit.vision.common.InputImage;
//import com.google.mlkit.vision.text.Text;
//import com.google.mlkit.vision.text.TextRecognition;
//import com.google.mlkit.vision.text.TextRecognizer;
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
//
//public class TextRecognitionActivity extends AppCompatActivity {
//
//    TextView resultTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_text_recognition);
//        resultTextView = findViewById(R.id.Result);
//        //resultTextView.setText("hi");
//        Intent intent = getIntent();
//        Bitmap image1 = (Bitmap) intent.getExtras().get("data");
//        InputImage image2 = InputImage.fromBitmap(image1, 0);
//        recognizeText(image2);
//    }
//
//    private void recognizeText(InputImage image) {
//
//        // [START get_detector_default]
//        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
//        // [END get_detector_default]
//
//        // [START run_detector]
//        Task<Text> result =
//                recognizer.process(image)
//                        .addOnSuccessListener(new OnSuccessListener<Text>() {
//                            StringBuilder extractedText = new StringBuilder();
//
//                            @Override
//                            public void onSuccess(Text visionText) {
//                                // Task completed successfully
//                                // [START_EXCLUDE]
//                                // [START get_text]
//
//                                for (Text.TextBlock block : visionText.getTextBlocks()) {
//                                    Rect boundingBox = block.getBoundingBox();
//                                    Point[] cornerPoints = block.getCornerPoints();
//                                    String text = block.getText();
//
//                                    for (Text.Line line: block.getLines()) {
//                                        extractedText.append(block.getText()).append("\n");
//                                        for (Text.Element element: line.getElements()) {
//                                            // ...
//                                            for (Text.Symbol symbol: element.getSymbols()) {
//                                                // ...
//                                            }
//                                        }
//                                    }
//                                }
//                                // [END get_text]
//                                // [END_EXCLUDE]
//                                resultTextView.setText(extractedText.toString());
//                            }
//                        })
//                        .addOnFailureListener(
//                                new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        // Task failed with an exception
//                                        // ...
//                                    }
//                                });
//        // [END run_detector]
//    }
//
//    private void processTextBlock(Text result) {
//        // [START mlkit_process_text_block]
//        String resultText = result.getText();
//        for (Text.TextBlock block : result.getTextBlocks()) {
//            String blockText = block.getText();
//            Point[] blockCornerPoints = block.getCornerPoints();
//            Rect blockFrame = block.getBoundingBox();
//            for (Text.Line line : block.getLines()) {
//                String lineText = line.getText();
//                Point[] lineCornerPoints = line.getCornerPoints();
//                Rect lineFrame = line.getBoundingBox();
//                for (Text.Element element : line.getElements()) {
//                    String elementText = element.getText();
//                    Point[] elementCornerPoints = element.getCornerPoints();
//                    Rect elementFrame = element.getBoundingBox();
//                    for (Text.Symbol symbol : element.getSymbols()) {
//                        String symbolText = symbol.getText();
//                        Point[] symbolCornerPoints = symbol.getCornerPoints();
//                        Rect symbolFrame = symbol.getBoundingBox();
//                    }
//                }
//            }
//        }
//        // [END mlkit_process_text_block]
//    }
//
//    private TextRecognizer getTextRecognizer() {
//        // [START mlkit_local_doc_recognizer]
//        TextRecognizer detector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
//        // [END mlkit_local_doc_recognizer]
//
//        return detector;
//    }
//}