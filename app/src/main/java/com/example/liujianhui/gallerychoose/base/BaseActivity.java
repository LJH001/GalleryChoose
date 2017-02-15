package com.example.liujianhui.gallerychoose.base;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liujianhui.gallerychoose.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by issuser on 2017/2/14 0014.
 */
public class BaseActivity extends Activity {
    private static final  String  TAG = BaseActivity.class.getSimpleName();

    /**
     * 整个标题栏的View
     */
    protected RelativeLayout titleRl;

    /**
     * 标题栏左侧返回按钮
     */
    protected Button backBtn;

    /**
     * 左侧按钮
     */
    protected Button leftBtn;

    /**
     * 标题栏名称
     */
    protected TextView titleTv;

    /**
     * 右边按钮
     */
    protected Button rightBtn;

    /**
     * 复选框
     */
    protected CheckBox checkBox;

    /**
     * 内容
     */
    protected LinearLayout contentLl;

    /**
     * 键盘控制器
     */
    private InputMethodManager imm;

    /**
     * 上下文
     */
    protected Context context;

    /**
     * 图片加载实例
     */
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.common_main);
        init();
        setContentLayout(layoutResID);
    }

    /**
     * 设置布局内容 setContentView另种入口
     *
     * @param v 内容为View
     */
    public void setContentView(View v) {
        super.setContentView(R.layout.common_main);
        init();
        setContentLayout(v);
    }

    /**
     * 初始化
     */
    private void init() {
        context = this;

        //初始化公共view
        initCommonView();
    }

    /**
     * 初始化公共view
     */
    private void initCommonView() {
        titleRl = (RelativeLayout) findViewById(R.id.common_rl_title);
        backBtn = (Button) findViewById(R.id.common_btn_back);
        leftBtn = (Button) findViewById(R.id.common_btn_left);
        titleTv = (TextView) findViewById(R.id.common_tv_title);
        rightBtn = (Button) findViewById(R.id.common_btn_right);
        checkBox = (CheckBox) findViewById(R.id.cb_select_all);
        contentLl = (LinearLayout) findViewById(R.id.common_ll_content);

        //返回
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });
        //点击页面其他地方隐藏键盘
        contentLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    hideKeyboard(v);
                }
                return false;
            }
        });

    }


    /**
     * 返回
     */
    public void onBack() {
        finish();
    }

    /**
     * 隐藏顶部Title
     */
    public void hideTitle() {
        titleRl.setVisibility(View.GONE);
    }


    /**
     * 设置头标题
     *
     * @param title 头标题
     */
    public void setTitleText(String title) {
        titleTv.setText(title == null ? "" : title);
    }

    /**
     * 设置头标题
     *
     * @param titleResId 头标题
     */
    public void setTitleText(int titleResId) {
        titleTv.setText(titleResId);
    }


    /**
     * 设置返回按钮文字
     *
     * @param backResId 返回按钮文字
     */
    public void setTitleBackBtnText(int backResId) {
        leftBtn.setVisibility(View.GONE);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setText(backResId);
    }

    /**
     * 设置返回按钮文字
     *
     * @param backText 返回按钮文字
     */
    public void setTitleBackBtnText(String backText) {
        leftBtn.setVisibility(View.GONE);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setText(backText);
    }

    /**
     * 设置头标题栏返回按钮图片
     *
     * @param leftResId   左图
     * @param topResId    上图
     * @param rightResId  右图
     * @param bottomResId 下图
     */
    public void setTitleBackBtnDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        leftBtn.setVisibility(View.GONE);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setCompoundDrawablesWithIntrinsicBounds(leftResId, topResId, rightResId, bottomResId);
    }

    /**
     * 设置头标题栏左边按钮文字
     *
     * @param leftResId 左边按钮文字
     */
    public void setTitleLeftBtn(int leftResId) {
        leftBtn.setText(leftResId);
    }

    /**
     * 设置头标题栏左边按钮文字
     *
     * @param leftBtnText 左边按钮文字
     */
    public void setTitleLeftBtnText(String leftBtnText) {
        backBtn.setVisibility(View.GONE);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setText(leftBtnText == null ? "" : leftBtnText);
    }

    /**
     * 设置头标题栏左边按钮图片
     *
     * @param leftResId   左图
     * @param topResId    上图
     * @param rightResId  右图
     * @param bottomResId 下图
     */
    public void setTitleLeftBtnDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        backBtn.setVisibility(View.GONE);
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setCompoundDrawablesWithIntrinsicBounds(leftResId, topResId, rightResId, bottomResId);
    }

    /**
     * 设置头标题栏右边按钮文字
     *
     * @param rightResId 右边按钮文字
     */
    public void setTitleRightBtnText(int rightResId) {
        rightBtn.setText(rightResId);
    }

    /**
     * 设置头标题栏右边按钮文字
     *
     * @param rightBtnText 右边按钮文字
     */
    public void setTitleRightBtnText(String rightBtnText) {
        rightBtn.setText(rightBtnText == null ? "" : rightBtnText);
    }

    /**
     * 设置头标题栏左边按钮图片
     *
     * @param leftResId   左图
     * @param topResId    上图
     * @param rightResId  右图
     * @param bottomResId 下图
     */
    public void setTitleRightBtnDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        rightBtn.setCompoundDrawablesWithIntrinsicBounds(leftResId, topResId, rightResId, bottomResId);
    }

    /**
     * 添加内容布局文件
     *
     * @param layoutResID 内容布局文件的资源ID
     */
    private void setContentLayout(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        // 需填充的内容的视图
        View contentView = inflater.inflate(layoutResID, null);
        setContentLayout(contentView);
    }

    /**
     * 添加内容布局文件
     *
     * @param contentView 内容布局
     */
    private void setContentLayout(View contentView) {
        // 初始化键盘管理器
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // 将内容填充进父视图
        contentLl.addView(contentView);
        // 内容视图的参数
        android.view.ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        // 设置宽高均为MATCH_PARENT
        layoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        // 将内容视图的参数设置进去
        contentView.setLayoutParams(layoutParams);
    }

    /**
     * 隐藏键盘
     *
     * @param v view组件
     * @author zqzhu
     */
    protected void hideKeyboard(View v) {
        if (imm.isActive()) {
            // 如果键盘为激活状态，则收起键盘
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 展示键盘
     *
     * @param et 输入框
     */
    protected void showKeyboard(EditText et) {
        // 输入框获得焦点
        et.requestFocus();
        et.setSelectAllOnFocus(true);
        et.selectAll();
        // 展示键盘
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


}
