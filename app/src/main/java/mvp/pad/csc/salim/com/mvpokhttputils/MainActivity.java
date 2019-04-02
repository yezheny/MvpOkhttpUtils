package mvp.pad.csc.salim.com.mvpokhttputils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pcjz.http.okhttp.callback.HttpCallback;
import com.pcjz.http.okhttp.helper.HttpInvoker;
import com.pcjz.http.okhttp.helper.HttpManager;
import com.pcjz.http.okhttp.helper.ServerResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mvp.pad.csc.salim.com.mvpokhttputils.base.BasePresenterActivity;
import mvp.pad.csc.salim.com.mvpokhttputils.contract.MainContract;
import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.DeviceInfo;
import mvp.pad.csc.salim.com.mvpokhttputils.model.entity.ScreenBean;
import mvp.pad.csc.salim.com.mvpokhttputils.presenter.IBasePresenter;
import mvp.pad.csc.salim.com.mvpokhttputils.presenter.impl.MainImpl;

public class MainActivity extends BasePresenterActivity<MainContract.MainPresenter, MainContract.MainView> implements MainContract.MainView, View.OnClickListener {

    private TextView mText;
    private TextView mTvDreams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_request).setOnClickListener(this);
        findViewById(R.id.bt_result).setOnClickListener(this);
        findViewById(R.id.bt_dreams).setOnClickListener(this);
        findViewById(R.id.bt_upload).setOnClickListener(this);
        mText = findViewById(R.id.tv_text);
        mTvDreams = findViewById(R.id.tv_dreams);
        bulidHeadParams();
    }

    @Override
    protected Class<MainImpl> createPresenter() {
        //return MainImpl.class;
        return null;
    }

    @Override
    protected MainContract.MainPresenter createAPresenter() {
        return new MainImpl();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_request:
                getPresenter().getDeviceInfo("2002031914");
                break;
            case R.id.bt_result:
                getPresenter().getPushStatus();
                break;
            case R.id.bt_dreams:
                getPresenter().getDreams();
                break;
            case R.id.bt_upload:
                getPresenter().uploadImg();
                break;
        }
    }

    @Override
    public void setDeviceInfo(DeviceInfo info) {
        mText.setText(info.toString());
    }

    @Override
    public void setPushStatus() {
        Toast.makeText(this, "反馈成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDreams(List<ScreenBean> beans) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < beans.size(); i++) {
            buffer.append(beans.get(i).toString());
        }
        mTvDreams.setText(buffer);
    }

    @Override
    public void setUploadStatus(String img) {
        Log.i("img=============", img);
    }

    private void bulidHeadParams(){
        Map<String, String> params = new HashMap<>();
        params.put("application1", "dbc8b7ef5a8f48deb30cc8b377727bbe");
        params.put("application2", "1");
        params.put("application3","android-v1.0");
        params.put("application4","13048822391");
        params.put("application5","3743021eac2a45519a97d4ce24097786");
        HttpManager.getInstance().initHead(params);
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;

    @Override
    protected void onResume() {
        super.onResume();
        //判断存储权限，如果没有就申请，用户拒绝就提示某些功能不可用
        setPermission();
    }

    private void setPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取到存储权限
            } else {
                // Permission Denied
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

