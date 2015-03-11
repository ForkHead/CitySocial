package com.example.sony.citysocial;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.ArrayList;




public class AddQuestion extends ActionBarActivity implements SnackBar.OnMessageClickListener {

    private final int APP_THEME = R.array.sky;
    private ArrayList<FlatButton> flatButtons = new ArrayList<FlatButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);
        setContentView(R.layout.activity_main);

        flatButtons.add((FlatButton) findViewById(R.id.button_block));

        Spinner s1 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                      //  Toast.makeText("Spinner1: position=" + position + " id=" + id);
                        Toast.makeText(getApplicationContext(),
                                "Spinner1: position=" + position + " id=" + id, Toast.LENGTH_LONG).show();
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                      //  showToast("Spinner1: unselected");
                    }

                });











    }

    private void changeTheme(int colorReference) {
        for (FlatButton view : flatButtons) {
            view.getAttributes().setTheme(colorReference, getResources());
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
      /*  if (id == action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);

    }

    public void onQuestionClicked(View view) {
        String message = "Question Submitted";
        int bgColor = R.color.sb__snack_bkgnd;
        short duration = SnackBar.SHORT_SNACK;
        SnackBar mSnackBar = new SnackBar.Builder(this)
                .withMessage(message)
                .withBackgroundColorId(bgColor)
                .withDuration(duration)
                .show();
    }

    @Override
    public void onMessageClick(Parcelable parcelable) {

    }
}