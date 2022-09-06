package com.example.part5_15;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/* 에러의 발생원인은 AppCompatActivity를 상속받은 MainActivity에서 NoTitleBar 옵션을 사용하기 때문인데.
 * AppCompatActivity는 안드로이드 서포트 라이브러리에서 사용되는 액션바의 특징을 사용하기 위한 액티비티로
 * 이 액티비티를 사용하면서 동시에 NoTitleBar 옵션을 적용하면 위와 같은 에러가 발생한다.
 * 동시에 풀스크린 모드를 적용할 때도 발생한다.
 * 풀스크린모드와 같은 옵션을 사용하고자 할 경우 AppAompatAcitivity가 아닌 Activity 클래스를 상속받아 사용하도록 한다
 */

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
    }

    public void finishDialog(View v) {
        DialogActivity.this.finish();
    }
}