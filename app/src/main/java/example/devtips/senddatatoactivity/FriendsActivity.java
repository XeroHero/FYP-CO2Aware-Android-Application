package example.devtips.senddatatoactivity;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class FriendsActivity extends Activity {
//    ArrayList<String> friendsArray = new ArrayList<>();

    private Button save;
    Button cancel;
    private EditText user;
    LayoutInflater layoutInflater;
    View popupInputDialogView, popupRemoveDialogView;
    ListView listView;
    Button remove;
    Button cancelRemove;
    private View emptyView;
    private int screenHeight, screenWidth;

    public void configureToasty(@NonNull Typeface typeface, boolean tintIcon, int sizeInSp, boolean allowQueue) {
        Toasty.Config.getInstance()
                .setToastTypeface(typeface) //optional
                .tintIcon(tintIcon) // optional (apply textColor also to the icon)
                .setTextSize(sizeInSp) // optional
                .allowQueue(allowQueue) // optional (prevents several Toastys from queuing)
                .apply(); // required
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Friends using the App");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        save = new Button(this);
        cancel = new Button(this);
        user = new EditText(this);
        layoutInflater = LayoutInflater.from(this);
        remove = new Button(this);
        listView = findViewById(R.id.friends_list);
        emptyView =findViewById(R.id.empty_listview_friends);
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);
        final ArrayList<String> retrieved = new ArrayList<>(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getStringSet("SAVEDATA", new HashSet<String>()));

        final ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.activity_friends_listview, retrieved);

        saveSharedPreferences(retrieved);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        getDisplayMetrics();
        listView.setEmptyView(emptyView);
//        emptyView.setPadding(0,screenHeight/2-emptyView.getHeight(),0, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // Create a AlertDialog Builder.
                AlertDialog.Builder removeFriendsPopUp = new AlertDialog.Builder(FriendsActivity.this);
                // Set title, icon, can not cancel properties.
                removeFriendsPopUp.setTitle("Remove friend?");
                removeFriendsPopUp.setIcon(R.drawable.friends);
                removeFriendsPopUp.setCancelable(false);

                // Init popup dialog view and it's ui controls.
                initRemovePopupViewControls();

                // Set the inflated layout view object to the AlertDialog builder.
                removeFriendsPopUp.setView(popupRemoveDialogView);

                // Create AlertDialog and show.
                final AlertDialog alertDialog = removeFriendsPopUp.create();

                // When user click the save button in the popup dialog.
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retrieved.remove(position);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.activity_friends_listview, retrieved);
                        listView.setAdapter(adapter);
                        saveSharedPreferences(retrieved);
                        alertDialog.cancel();
                    }});
                cancelRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
//                        alertDialog.closeOptionsMenu();
                    }
                });
                alertDialog.show();
            }
        });


        setTheme(R.style.Theme_AppCompat);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a AlertDialog Builder.
                AlertDialog.Builder addFriendsPopUp = new AlertDialog.Builder(FriendsActivity.this);
                // Set title, icon, can not cancel properties.
                addFriendsPopUp.setTitle("Add or invite a friend");
                addFriendsPopUp.setIcon(R.drawable.friends);
                addFriendsPopUp.setCancelable(false);

                // Init popup dialog view and it's ui controls.
                initPopupViewControls();

                // Set the inflated layout view object to the AlertDialog builder.
                addFriendsPopUp.setView(popupInputDialogView);

                // Create AlertDialog and show.
                final AlertDialog alertDialog = addFriendsPopUp.create();

                // When user click the save button in the popup dialog.
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Get data from popup dialog editeext.
                        String userName = user.getText().toString();
                        // Create data for the listview.
                        configureToasty(null, false, 12, false);
                        if (userName.contains("@")){
                            Toast toast=Toast. makeText(getApplicationContext(),"Email has been sent to " + userName,Toast. LENGTH_LONG);
                            toast.show();
                            userName += " (Pending confirmation)";
//                            Toasty.success(this, R.string.success_email_toasty + userName, Toasty.LENGTH_LONG).show();
//                            Toasty.success
                        }else
                            userName += "  " + randomScore(0, 10000);
                        retrieved.add(userName);



                        listView.setItemsCanFocus(true);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.activity_friends_listview, retrieved);
                        listView.setAdapter(adapter);
                        saveSharedPreferences(retrieved);
                        alertDialog.cancel();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void getDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
 screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }


    private void initRemovePopupViewControls() {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        // Inflate the popup dialog from a layout xml file.
        popupRemoveDialogView = layoutInflater.inflate(R.layout.popup_remove_friend, null);

        // Get user input edittext and button ui controls in the popup dialog.
        remove = popupRemoveDialogView.findViewById(R.id.remove_friend);
        cancelRemove = popupRemoveDialogView.findViewById(R.id.cancel_remove);

    }

    public static int randomScore(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void saveSharedPreferences(ArrayList<String> tobesaved) {
//        ArrayList<String> tobesaved = getData(); // fetch the data
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putStringSet("SAVEDATA", new HashSet<>(tobesaved));
        edit.apply();


    }

    /* Initialize popup dialog view and ui controls in the popup dialog. */
    private void initPopupViewControls() {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);

        // Get user input edittext and button ui controls in the popup dialog.
        user = popupInputDialogView.findViewById(R.id.userName);
        save = popupInputDialogView.findViewById(R.id.save);
        cancel = popupInputDialogView.findViewById(R.id.cancel);

        // Display values from the main activity list view in user input edittext.
//        initEditTextUserDataInPopupDialog();
    }


    /* Get current user data from listview and set them in the popup dialog edittext controls. */
    private void initEditTextUserDataInPopupDialog() {
        List<String> userDataList = getExistUserDataInListView(listView);

        if (userDataList.size() == 3) {
            String userName = userDataList.get(0);


            if (user != null) {
                user.setText(userName);
            }
        }
    }

    /* If user data exist in the listview then retrieve them to a string list. */
    private List<String> getExistUserDataInListView(ListView listView) {
        List<String> ret = new ArrayList<>();

        if (listView != null) {
            ListAdapter listAdapter = listView.getAdapter();

            if (listAdapter != null) {

                int itemCount = listAdapter.getCount();

                for (int i = 0; i < itemCount; i++) {
                    Object itemObject = listAdapter.getItem(i);
                    HashMap<String, String> itemMap = (HashMap<String, String>) itemObject;

                    Set<String> keySet = itemMap.keySet();

                    Iterator<String> iterator = keySet.iterator();

                    String key = iterator.next();

                    String value = itemMap.get(key);

                    ret.add(value);
                }
            }
        }

        return ret;
    }
}
