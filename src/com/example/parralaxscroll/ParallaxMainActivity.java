package com.example.parralaxscroll;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parralaxscroll.PScrollView.onScrollViewScrollChangedListener;

public class ParallaxMainActivity extends Activity {
    PScrollView scrollview;
    ImageView header;
    LinearLayout contents;
    TextView tab;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_parallax_main);
        header = (ImageView) findViewById(R.id.header_image);
        scrollview = (PScrollView) findViewById(R.id.scrollview);
        contents = (LinearLayout) findViewById(R.id.contents);
        tab = (TextView) findViewById(R.id.tab);
        scrollview.setHeaderView(header);
        scrollview.addTabView(tab);
//         initListener();
        initActionbar();
        printWindowState();
    }
    private void printWindowState(){
       
        Log.i("myTest","value[name:"+getWindow().hasFeature(Window.FEATURE_ACTION_BAR_OVERLAY));
//        TypedArray array = getWindow().getWindowStyle();
//        for(int i = 0; i<array.length();i++){
//            TypedValue outValue = new TypedValue();
//            array.getValue(i, outValue);
//            int resid = outValue.resourceId;
//            String name = "";
//            
//            if(resid !=0){
//               name = getResources().getResourceEntryName(resid);
//            }
////            String name = getResources().getResourceEntryName(outValue.resourceId);
//        }
    }
    private void initActionbar(){
        ActionBar actionbar = getActionBar();
        if(actionbar == null) return;
        actionbar.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
    }

//
//    private void initListener() {
//        scrollview.setOnScrollListener(new onScrollViewScrollChangedListener() {
//
//            @Override
//            public void onScrollChanged(int l, int t, int oldl, int oldt) {
//                // TODO Auto-generated method stub
//
//                int scrolly = scrollview.getScrollY();
//                float padding = header.getBottom();
//                float scroll = t + padding;
//                header.setTranslationY(t);
//            }
//        });
//
//    }

}
