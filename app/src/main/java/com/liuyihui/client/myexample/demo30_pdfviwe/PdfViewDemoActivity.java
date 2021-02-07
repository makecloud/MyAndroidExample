package com.liuyihui.client.myexample.demo30_pdfviwe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.liuyihui.client.myexample.R;

import java.io.File;

public class PdfViewDemoActivity extends AppCompatActivity {
    private final String TAG = "PdfViewDemoActivity";
    private PDFView pdfView;
    int mCount;
    int mPageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view_demo);
        pdfView = findViewById(R.id.pdfView);
        String pdfFileName1 = "B4883E15C86F5DDE639C576A62E343F5.pdf";
        String pdfFileName2 = "AA0B2468BB57F50C661C414AC4CEF8B6.pdf";
        String pdfPath = "/sdcard/oohlink/player/.screen/" + pdfFileName2;
        File mPdfFile = new File(pdfPath);


        pdfView.fromFile(mPdfFile)
               //.pageFitPolicy(FitPolicy.BOTH)
               /*.fitEachPage(true).autoSpacing(true)*/.onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                mPageCount = nbPages;
                startIntervalPageTurn();
                mCount++;
            }
        }).load();

    }


    public void startIntervalPageTurn() {
        Log.d(TAG, String.format("mCount:%s,mPageCount:%s.", mCount, mPageCount));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: ", e);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (mPageCount != 0) {
                                //取余，在mCount小于总页数时mCount不变，在mCount等于总页数时mCount回到0
                                mCount = mCount % mPageCount;
                            }

                            pdfView.jumpTo(mCount);
                                           /*MaterialPdfView.this.fromFile(mPdfFile)
                                                               .pageFitPolicy(FitPolicy.BOTH)
                                                               .fitEachPage(true)
                                                               .autoSpacing(true)
                                                               .pages(mCount)
                                                               .load();*/
                            Log.d(TAG, "load page (mCount):" + mCount + "/" + mPageCount);
                            mCount++;
                        }
                    });


                }

            }
        }).start();
    }

}
