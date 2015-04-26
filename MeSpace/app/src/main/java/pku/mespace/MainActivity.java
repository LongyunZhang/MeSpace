package pku.mespace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Button unclockBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        unclockBtn = (Button) findViewById(R.id.unclockBtn);
        unclockBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //解锁按钮
       if (view.getId() == R.id.unclockBtn) {

          /* Intent intent = new Intent();
           intent.setAction("android.media.action.IMAGE_CAPTURE");
           intent.addCategory("android.intent.category.DEFAULT");
            //照片存放地址， 000.jpg为照片名
           File file = new File(Environment.getExternalStorageDirectory()+"/000.jpg");
           Uri uri = Uri.fromFile(file);
           intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
           startActivity(intent);*/

            Intent intent1 = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent1);
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
