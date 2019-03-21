package joker.bloodbanktest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends Activity {

    private Button login;
    private EditText etemail,etpass;
    TextView hlpTV,privacyTV,aboutTV;
    int count =0;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        getActionBar().setTitle("Log In");

        etemail = (EditText)findViewById(R.id.logEmail);
        etpass = (EditText)findViewById(R.id.logPass);
        login = (Button)findViewById(R.id.logInBTN);
        hlpTV = (TextView)findViewById(R.id.help);
        privacyTV = (TextView)findViewById(R.id.privacy);
        aboutTV = (TextView)findViewById(R.id.about);

        hlpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,Help.class));
            }
        });
        privacyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,Privacy.class));
            }
        });
        aboutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,AboutApps.class));
            }
        });

        TextView reg = (TextView)findViewById(R.id.regTV);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getText().toString();
                String pass = etpass.getText().toString();

                String rpass = helper.searchPassword(email);

                if(pass.equals(rpass))
                {
                    int ID = helper.getID();
                    Intent i = new Intent(LoginPage.this,MainActivity.class);
                    Bundle data = new Bundle();
                    data.putInt("USER_ID",ID);
                    i.putExtra("USER_DATA",data);
                    finish();
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong email/password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,RegisterPage.class));
            }
        });



    }

    @Override
    public void onBackPressed() {
        count++;
        if(count==1)
            Toast.makeText(getApplicationContext(), "Press Back Button again to EXIT", Toast.LENGTH_SHORT).show();

        else if(count==2)
            finish();


        //super.onBackPressed();
    }
}
