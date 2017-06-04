package com.zizhu.skindetection.controller.fragment;


import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zizhu.skindetection.R;
import com.zizhu.skindetection.base.fragment.BaseFragment;
import com.zizhu.skindetection.common.model.RecordModel;
import com.zizhu.skindetection.common.utils.StorageUtil;
import com.zizhu.skindetection.controller.adapter.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录
 */

//  修改  修改   。。。 
public class RecordFragment extends BaseFragment {

    private RecyclerView rv_record_list;
    private RecordAdapter recordAdapter;
    private List<RecordModel> recordModels = new ArrayList<>();
    private View rl_01;

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    protected void initWidget(View rootView) {
        rv_record_list = (RecyclerView) rootView.findViewById(R.id.rv_record_list);
        rl_01 = rootView.findViewById(R.id.rl_01);
        rv_record_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        findTextView((ViewGroup) rootView);
    }

    private void findTextView(ViewGroup viewGroup){
        for(int i = 0; i < viewGroup.getChildCount(); i++){
            if(viewGroup.getChildAt(i) instanceof ViewGroup){
                findTextView((ViewGroup) viewGroup.getChildAt(i));
            }
            if((viewGroup.getChildAt(i) instanceof TextView)){
                TextView textView = (TextView) viewGroup.getChildAt(i);
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"fonts/lantinghei.ttf");
                textView.setTypeface(face);
            }
        }
    }

    @Override
    protected void bindEven() {

    }

    @Override
    protected void setView() {
        recordAdapter = new RecordAdapter(getActivity(), recordModels, R.layout.layout_record_list_item);
        rv_record_list.setAdapter(recordAdapter);
        recordAdapter.addAll(StorageUtil.getRecordModes());
        if(Build.VERSION.SDK_INT >= 19) {
            rl_01.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onClickView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        recordAdapter.clear();
        recordAdapter.addAll(StorageUtil.getRecordModes());
    }
}
