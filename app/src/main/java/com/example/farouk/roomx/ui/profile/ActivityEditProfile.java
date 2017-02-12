package com.example.farouk.roomx.ui.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static android.R.attr.bitmap;

/**
 * Created by AbAbdullah on 12/02/2017.
 */

public class ActivityEditProfile extends AppCompatActivity implements VolleyCallback, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.profilePic_imageview)
    CircleImageView profilePicImageview;
    @Bind(R.id.iv_camera)
    CircleImageView ivCamera;
    @Bind(R.id.icon1)
    ImageView icon1;
    @Bind(R.id.username_lable)
    TextView usernameLable;
    @Bind(R.id.username_edittext)
    EditText usernameEdittext;
    @Bind(R.id.icon2)
    ImageView icon2;
    @Bind(R.id.icon3)
    ImageView icon3;
    @Bind(R.id.mobile_lable)
    TextView mobileLable;
    @Bind(R.id.mobile_edittext)
    EditText mobileEdittext;
    @Bind(R.id.icon4)
    ImageView icon4;
    @Bind(R.id.email_lable)
    TextView emailLable;
    @Bind(R.id.email_edittext)
    EditText emailEdittext;
    @Bind(R.id.country_lable)
    TextView countryLable;
    @Bind(R.id.country_edittext)
    EditText countryEdittext;
    @Bind(R.id.icon5)
    ImageView icon5;
    @Bind(R.id.city_lable)
    TextView cityLable;
    @Bind(R.id.city_edittext)
    EditText cityEdittext;
    @Bind(R.id.icon6)
    ImageView icon6;
    @Bind(R.id.dob_lable)
    TextView dobLable;
    @Bind(R.id.dob_edittext)
    EditText dobEdittext;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.pic_layout)
    FrameLayout frameLayout;
    private User userResponse;
    private Requests requests;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_activity_edit_profile));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dobEdittext.setRawInputType(InputType.TYPE_NULL);
        dobEdittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog();
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery();
            }
        });
        requests = new Requests();
        requests.getUserProfile(this, this);
    }
    public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                //Setting image to ImageView
                profilePicImageview.setImageBitmap(bitmap);
               // uploadImage();
                getFile(selectedImage);
/*                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                profilePicImageview.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));*/

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void uploadImage(){
        //converting image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        requests.editProfilePic(this,this,imageString);

    }

    public void getFile(Uri selectedImage){
        File pic_file=null;
        if(selectedImage!=null) {
            pic_file= new File(getRealPath(selectedImage));
        }

        requests.uploadImage(this,this,pic_file);

    }

    String getRealPath(Uri uri){
        if (uri==null)
            return null;
        String[] co={MediaStore.Images.Media.DATA};
        Cursor cursor=getApplicationContext().getContentResolver().query(uri,co,null,null,null);
        cursor.moveToFirst();
        int ci=cursor.getColumnIndex(co[0]);
        String filep=cursor.getString(ci);
        cursor.close();
        Log.d("real_path", filep);
        return filep;

    }
    @Override
    public void onSuccess(Response response) {
        userResponse = (User) response.getObject();
        if(userResponse!=null) {
            Log.d("userResponse", userResponse.toString());
            usernameEdittext.setText(userResponse.getName());
            mobileEdittext.setText(userResponse.getPhone());
            emailEdittext.setText(userResponse.getEmail());
            cityEdittext.setText(userResponse.getCity());
            countryEdittext.setText(userResponse.getCountry());
            dobEdittext.setText(userResponse.getDob());
        }
        if(response.getResult()==1){
            Log.d("getResult", "1");
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.done_succefully),Toast.LENGTH_LONG);
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        dobEdittext.setText(date);
    }

    public void showDateDialog() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.Navy));
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                onDoneClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onDoneClick() {
        User user = new User();
        user.setName(usernameEdittext.getText().toString() + "");
        user.setPhone(mobileEdittext.getText().toString() + "");
        user.setEmail(emailEdittext.getText().toString() + "");
        user.setCity(cityEdittext.getText().toString() + "");
        user.setCountry(countryEdittext.getText().toString() + "");
        user.setDob(dobEdittext.getText().toString() + "");
        requests.editProfile(this, this, user);
    }
}
