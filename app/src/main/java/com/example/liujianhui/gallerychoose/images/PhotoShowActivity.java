package com.example.liujianhui.gallerychoose.images;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liujianhui.gallerychoose.R;
import com.example.liujianhui.gallerychoose.base.BaseActivity;
import com.example.liujianhui.gallerychoose.contant.CommonConstant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
   *Description: 相册多选<br>
   * <br/>
   *Creator:jhliu <br>
   *Date:2017/2/15 0015 15:32
 */

public class PhotoShowActivity extends BaseActivity {
    private ArrayList<String> imageUrls;
    private DisplayImageOptions options;
    private ImageAdapter imageAdapter;
    private  GridView gridView;
    private ArrayList<CheckBox> checkBoxs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_show);
        initView();
        initData();
        initGallery();
        bindEvent();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
    }

    /**
     * 绑定事件
     */
    private void bindEvent() {
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i=0;i<imageUrls.size();i++){
                  checkBoxs.get(i).setChecked(isChecked);
                }
            }
        });

        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setText("确定");
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PhotoShowActivity.this,"您选择了"+imageUrls.size()+"张图片！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        this.imageUrls = new ArrayList<String>();

        final String[] columns = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor imagecursor = this
                .getApplicationContext()
                .getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                        null, null, orderBy + " DESC");

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imageUrls.add(imagecursor.getString(dataColumnIndex));
            Log.i("imageUrl", imageUrls.get(i));
        }
    }

    /**
     * 初始化相册
     */
    private void initGallery() {
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.stub_image)
                .showImageForEmptyUri(R.drawable.image_for_empty_url)
                .cacheInMemory().cacheOnDisc().build();
        checkBoxs.clear();  //清空缓存

        imageAdapter = new ImageAdapter(this, imageUrls);
        gridView.setAdapter(imageAdapter);

        //查看相册图片
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(PhotoShowActivity.this, CheckPhotoActivity.class);
                mIntent.putExtra(CommonConstant.KEY_GALLERY_URL, imageUrls.get(position));
                startActivity(mIntent);
            }
        });
    }


    @Override
    protected void onResume() {
        imageLoader.resume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        imageLoader.stop();
        super.onStop();
    }

    /**
     * Description GridView Adapter
     */
    private  class ImageAdapter extends BaseAdapter {
        private  ArrayList<String> mList;
        private LayoutInflater mInflater;
        private Context mContext;
        private SparseBooleanArray mSparseBooleanArray;

        public ImageAdapter(Context context, ArrayList<String> imageList) {
            // TODO Auto-generated constructor stub
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mSparseBooleanArray = new SparseBooleanArray();
            mList = new ArrayList<String>();
            this.mList = imageList;
        }

        public ArrayList<String> getCheckedItems() {
            ArrayList<String> mTempArry = new ArrayList<String>();

            for (int i = 0; i < mList.size(); i++) {
                if (mSparseBooleanArray.get(i)) {
                    mTempArry.add(mList.get(i));
                }
            }
            return mTempArry;
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.view_gridview,
                        null);
            }

             CheckBox mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkBox1);
            final ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.imageView1);

           imageLoader.displayImage("file://" + imageUrls.get(position),
                    imageView, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            Animation anim = AnimationUtils.loadAnimation(PhotoShowActivity.this, R.anim.fade_in);
                            imageView.setAnimation(anim);
                            anim.start();
                        }
                    });
            mCheckBox.setTag(position);
            mCheckBox.setChecked(mSparseBooleanArray.get(position));
            mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
            checkBoxs.add(mCheckBox);
            return convertView;
        }

        CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                mSparseBooleanArray.put((Integer) buttonView.getTag(),
                        isChecked);
            }
        };
    }
}
