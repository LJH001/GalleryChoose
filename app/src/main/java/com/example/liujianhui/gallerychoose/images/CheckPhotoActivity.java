package com.example.liujianhui.gallerychoose.images;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.liujianhui.gallerychoose.R;
import com.example.liujianhui.gallerychoose.base.BaseActivity;
import com.example.liujianhui.gallerychoose.contant.CommonConstant;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
   *Description:查看相册图片 <br>
   * <br/>
   *Creator:jhliu <br>
   *Date:2017/2/14 0014 14:21
 */

public class CheckPhotoActivity extends BaseActivity {
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_photos);
        initView();
        showGallery();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.iv_check);
    }

    /**
     * 展示相册原图
     */
    private void showGallery() {
        Intent mIntent = getIntent();
        String galleryUrl = mIntent.getStringExtra(CommonConstant.KEY_GALLERY_URL);
        imageLoader.displayImage("file://" + galleryUrl,
                mImageView, null, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Animation anim = AnimationUtils.loadAnimation(
                                CheckPhotoActivity.this, R.anim.fade_in);
                        mImageView.setAnimation(anim);
                        anim.start();
                    }
                });
    }
}
