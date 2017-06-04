package com.zizhu.skindetection.controller.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.linj.camera.view.CameraContainer;
import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.fragment.BaseFragment;
import com.zizhu.skindetection.common.model.EventModel;
import com.zizhu.skindetection.common.utils.ScreenUtil;
import com.zizhu.skindetection.common.utils.ToastUtils;

public class SelfTakeFragment extends BaseFragment implements CameraContainer.TakePictureListener {

    private CameraContainer container;
    private ImageView iv_switch_camera;
    String mSaveRoot = "zizhu";

    public static SelfTakeFragment newInstance() {
        SelfTakeFragment fragment = new SelfTakeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self_take;
    }

    @Override
    protected void initWidget(View rootView) {
        container = (CameraContainer) rootView.findViewById(R.id.container);
        iv_switch_camera = (ImageView) rootView.findViewById(R.id.iv_switch_camera);
    }

    @Override
    protected void bindEven() {
        iv_switch_camera.setOnClickListener(this);
    }

    @Override
    protected void setView() {
        container.setRootPath(mSaveRoot);
        container.post(new Runnable() {
            @Override
            public void run() {
                container.switchCamera();
            }
        });
        if(Build.VERSION.SDK_INT >= 19) {
            RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) iv_switch_camera.getLayoutParams();
            lineParams.topMargin = ScreenUtil.dp2px(getActivity(), 15);
            iv_switch_camera.setLayoutParams(lineParams);
        }
    }

    @Override
    protected void onClickView(View view) {
        switch (view.getId()){
            case R.id.iv_switch_camera:{
                try {
                     container.switchCamera();
                } catch (Exception e) {
                    showToast("您的相机无法切换像头");
                }
            }
            break;
        }
    }

    public void takePicture(EventModel eventModel){
        if(eventModel != null){
            if("take".equals(eventModel.type)){
                try {
                    container.takePicture(this);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onTakePictureEnd(Bitmap bm) {

    }

    @Override
    public void onAnimtionEnd(Bitmap bm, boolean isVideo) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ToastUtils.show("---");
    }

}
