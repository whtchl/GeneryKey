package key.tchl.com.generykey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * track key 和 pin key 的明文和密文
 * "SECONDARYKEY": "a37de2af1c38484d5fa008cb0e1a0a8042aedb6a",
 * "PINKEY": "0742e8ad3bb1cca55f7499582939dad8c2e02e1d",
 * "MACKEY": "83433986735088618215972995255837",
 * <p>
 * secondarykey:30547039657040935211301238019089  pinkey:96982763811718716424305453253412
 */


public class MainActivity extends AppCompatActivity {

    //来自POS机的PIN 加密密文：
    String pos_pin_miwen = "44CB489EF60F9E65";
    String bank_card_no = "6226890091868173";
    String pos_pin_jiemi_mingwen = "98138311963891366835227638717314";
    //String pos_pin_mingwen


    private static final String TAG = "tchl";
    // public static final String STATUS = "status";

    @InjectView(R.id.tv1)
    TextView tv1;
    @InjectView(R.id.et_jmq)
    EditText etJmq;
    @InjectView(R.id.ll1)
    LinearLayout ll1;
    @InjectView(R.id.tv2)
    TextView tv2;
    @InjectView(R.id.tv_jmh)
    TextView tvJmh;
    @InjectView(R.id.ll2)
    LinearLayout ll2;
    @InjectView(R.id.btn_masterkey)
    Button btnMasterkey;
    @InjectView(R.id.et_jmh1)
    EditText etJmh1;
    @InjectView(R.id.tv_jmh2)
    TextView tvJmh2;
    @InjectView(R.id.btn_masterkeyjm)
    Button btnMasterkeyjm;
    @InjectView(R.id.et_pin_mingwen)
    EditText etPinMingwen;
    @InjectView(R.id.et_pin_miwen)
    EditText etPinMiwen;
    @InjectView(R.id.et_track_mingwen)
    EditText etTrackMingwen;
    @InjectView(R.id.et_track_miwen)
    EditText etTrackMiwen;
    @InjectView(R.id.btn_pin_track_jiami)
    Button btnPinTrackJiami;
    @InjectView(R.id.et_pin_miwen2)
    EditText etPinMiwen2;
    @InjectView(R.id.et_pin_mingwen2)
    EditText etPinMingwen2;
    @InjectView(R.id.et_track_miwen2)
    EditText etTrackMiwen2;
    @InjectView(R.id.et_track_mingwen2)
    EditText etTrackMingwen2;
    @InjectView(R.id.btn_pin_track_jiemi)
    Button btnPinTrackJiemi;
    @InjectView(R.id.et_pos_pin_miwen)
    EditText etPosPinMiwen;
    @InjectView(R.id.et_bankcard_no)
    EditText etBankcardNo;
    @InjectView(R.id.et_pos_pin_mingwen)
    EditText etPosPinMingwen;
    @InjectView(R.id.btn_bank_pin_jiemi)
    Button btnBankPinJiemi;
    @InjectView(R.id.et_bank_pin_mingwen)
    EditText etBankPinMingwen;
    @InjectView(R.id.et_bank_code)
    EditText etBankCode;
    @InjectView(R.id.et_bank_pin_miwen)
    EditText etBankPinMiwen;
    @InjectView(R.id.btn_bank_pin_jiami)
    Button btnBankPinJiami;
    @InjectView(R.id.et_jiami_pin_key)
    EditText etJiamiPinKey;
    @InjectView(R.id.et_jiemi_pin_key)
    EditText etJiemiPinKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initData();
    }

    private void initData() {
        initBankCardJiemi();

    }

    //给btn_bank_pin_jiemi用的。 银行卡解密按钮数据的初始化
    private void initBankCardJiemi() {
        //etPosPinMiwen.setText(pos_pin_miwen);
        //etBankcardNo.setText(bank_card_no);
    }

    @OnClick({R.id.btn_masterkey, R.id.btn_masterkeyjm, R.id.btn_pin_track_jiemi, R.id.btn_pin_track_jiami, R.id.btn_bank_pin_jiemi
            , R.id.btn_bank_pin_jiami})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_masterkey:   //主秘钥加密
                //masterkey(32):55062688649849448420589726890512  _masterkey(40):74d7e77570edff4bae3a7edfb39a375133d98f2f
                //etJmq.setText("98334294319114771621172817316034");
                if (etJmq.getText().toString().equals("")) {
                    Log.e("wang", "is null");
                    etJmq.setText(SecurityKeyUtil.generateKey());
                }

                String _masterkey = SecurityKeyUtil.encrypt(null, etJmq.getText().toString(), null, true);
                tvJmh.setText(_masterkey);
                etJmh1.setText(_masterkey);
                config.ToastAndLog(this, _masterkey);
                break;
            case R.id.btn_masterkeyjm:  //主秘钥解密
                String str1 = SecurityKeyUtil.decrypt(etJmh1.getText().toString(), null);
                str1 = str1.replace(" ", "");
                tvJmh2.setText(str1);
                config.ToastAndLog(this, tvJmh2.getText().toString());
                break;
            case R.id.btn_pin_track_jiemi:   //PIN track 解密
                //etPinMingwen2.setText();
                etPinMingwen2.setText(SecurityKeyUtil.decrypt(etPinMiwen2.getText().toString(), etJmq.getText().toString()));

                etTrackMingwen2.setText(SecurityKeyUtil.decrypt(etTrackMiwen2.getText().toString(), etJmq.getText().toString()));

                break;
            case R.id.btn_pin_track_jiami:  //PIN Track 加密
                if (etJmq.getText().toString().equals("") || etJmq.getText().toString().length() != 32) {
                    Toast.makeText(this, "主秘钥明文是空或长度不是32", Toast.LENGTH_LONG).show();
                    return;
                }
                //生产PIN 明文
                etPinMingwen.setText(SecurityKeyUtil.generateKey());
                //生产Track 密文
                etTrackMingwen.setText(SecurityKeyUtil.generateKey());
                etPinMiwen.setText(SecurityKeyUtil.encrypt(null, etPinMingwen.getText().toString(), etJmq.getText().toString(), true));

                etTrackMiwen.setText(SecurityKeyUtil.encrypt(null, etTrackMingwen.getText().toString(), etJmq.getText().toString(), true));

                //设置PIN Track解密中的PIN密文和track明文
                etPinMiwen2.setText(etPinMiwen.getText().toString());
                etTrackMiwen2.setText(etTrackMiwen.getText().toString());
                break;

            case R.id.btn_bank_pin_jiami://银行卡密码加密
                if(etPinMingwen2.getText().toString()==null || etPinMingwen2.getText().toString().equals("")){
                    etJiamiPinKey.setText("98138311963891366835227638717314");
                }else{
                    etJiamiPinKey.setText(etPinMingwen2.getText().toString().replace(" ",""));
                }

                if(etBankCode.getText().toString().equals("") || etBankCode.getText().toString()==null){
                    etBankCode.setText(bank_card_no);
                }

                if(etBankPinMingwen.getText().toString().equals("") || etBankPinMingwen.getText().toString()==null){
                    etBankPinMingwen.setText("712373");
                }

                String pinMiwen = SecurityKeyUtil.encryptPin(etJiamiPinKey.getText().toString(), etBankPinMingwen.getText().toString(), etBankCode.getText().toString());
                etBankPinMiwen.setText(pinMiwen);
                break;
            case R.id.btn_bank_pin_jiemi: //银行卡密码解密

                if(etPinMingwen2.getText().toString()==null || etPinMingwen2.getText().toString().equals("")){
                    etJiemiPinKey.setText("98138311963891366835227638717314");
                }else{
                    etJiemiPinKey.setText(etPinMingwen2.getText().toString().replace(" ",""));
                }
                etPosPinMiwen.setText(etBankPinMiwen.getText().toString());
                etBankcardNo.setText(etBankCode.getText().toString());

                //String pin = SecurityKeyUtil.decryptPin(etPosPinMiwen.getText().toString(), pos_pin_jiemi_mingwen, etBankcardNo.getText().toString());
                String pin = SecurityKeyUtil.decryptPin(etPosPinMiwen.getText().toString(), etJiemiPinKey.getText().toString(), etBankcardNo.getText().toString());
                etPosPinMingwen.setText(pin);
                break;

        }
    }


}
