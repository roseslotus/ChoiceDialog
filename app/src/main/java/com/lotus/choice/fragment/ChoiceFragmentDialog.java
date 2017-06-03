package com.lotus.choice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lotus.choice.R;
import com.lotus.choice.adapter.ChoiceAdapter;
import com.lotus.choice.interfaces.IDialogChoiceCallBack;

import java.io.Serializable;
import java.util.List;

/**
 *  公用单选dialog
 * Created by Thl on 2017/6/2.
 */

public class ChoiceFragmentDialog extends DialogFragment implements AdapterView.OnItemClickListener {



    private String POSITION_KEY="position_key";
    private String CONTENT_KEY="centent_key";
    private String TITLE_KEY="title_key";
    private String CONFIRM_KEY="confirm_key";;
    private String CANCEL_KEY="cancel_key";

    private TextView tvdialogTitle;
    private ListView lvChoiceListView;
    private TextView tvButtonConfirm;
    private TextView tvButtonCancel;
    /**
     *  dialog标题
     */
    private String titleStr;
    /**
     * 确定文案
     */
    private String confirmStr;
    /**
     * 取消文案
     */
    private String cancelStr;
    private int mPosition=0;
    private  List<String> mDatas;

    IDialogChoiceCallBack callBackListener;
    private ChoiceAdapter choiceAdapter;

    public ChoiceFragmentDialog newInstance(int position, List<String> datas,String titleStr,String confirmStr,String cancelStr){
        ChoiceFragmentDialog cfd=new ChoiceFragmentDialog();
        Bundle bundle=new Bundle();
        bundle.putInt(POSITION_KEY,position);
        bundle.putSerializable(CONTENT_KEY,(Serializable) datas);
        bundle.putString(TITLE_KEY,titleStr);
        bundle.putString(CONFIRM_KEY,confirmStr);
        bundle.putString(CANCEL_KEY,cancelStr);
        cfd.setArguments(bundle);
        return cfd;
    }

    public ChoiceFragmentDialog newInstance(int position, List<String> datas,String titleStr,String confirmStr){
        return newInstance(position,datas,titleStr,confirmStr,"");
    }

    public ChoiceFragmentDialog newInstance(int position, List<String> datas,String titleStr){
        return newInstance(position,datas,titleStr,"");
    }

    public ChoiceFragmentDialog newInstance(int position, List<String> datas){
        return newInstance(position,datas,"");
    }

    public void setIDialogChoiceCallBackListener(IDialogChoiceCallBack listener){
        this.callBackListener=listener;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDataFromBundle();
        View root=inflater.inflate(R.layout.fragment_choce_dialog,container,false);
        initView(root);
        bindView();
        setListView();
        setListener();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void bindView() {
        if (!TextUtils.isEmpty(titleStr)){
            tvdialogTitle.setText(titleStr);
        }

        if (!TextUtils.isEmpty(confirmStr)){
            tvButtonConfirm.setText(confirmStr);
        }

        if (!TextUtils.isEmpty(cancelStr)){
            tvButtonCancel.setText(cancelStr);
        }else {
            tvButtonCancel.setVisibility(View.GONE);
        }

    }

    private void setListener() {
        tvButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton();
            }
        });
        tvButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    private void initView(View root) {
        tvdialogTitle=(TextView)root.findViewById(R.id.tv_dialog_title);
        tvButtonConfirm=(TextView)root.findViewById(R.id.tv_button_confirm);
        tvButtonCancel=(TextView)root.findViewById(R.id.tv_button_cancel);
        lvChoiceListView=(ListView)root.findViewById(R.id.lv_choice);
    }

    private void setListView() {
        choiceAdapter=new ChoiceAdapter(getActivity(),null,mPosition);
        lvChoiceListView.setAdapter(choiceAdapter);
    }

    /**
     *
     */
    public void getDataFromBundle() {
        Bundle bundle=getArguments();
        if (bundle!=null){
            mPosition=bundle.getInt(POSITION_KEY);
            mDatas=(List<String>) bundle.getSerializable(CONTENT_KEY);
            titleStr=bundle.getString(TITLE_KEY);
            confirmStr=bundle.getString(CONFIRM_KEY);
            cancelStr=bundle.getString(CANCEL_KEY);
        }
    }


    /**
     * 确定网络选择的项
     */
    public void confirmButton(){
        if (callBackListener!=null){
            callBackListener.choiceCall(mPosition);
        }
        dismissAllowingStateLoss();
    }

    /**
     * 关闭dialog
     */
    public void dismissDialog(){
        dismissAllowingStateLoss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPosition!=position){
            mPosition=position;
            choiceAdapter.setSelectPosition(mPosition);
        }

    }
}
