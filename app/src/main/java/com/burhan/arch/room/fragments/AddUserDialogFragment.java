package com.burhan.arch.room.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.burhan.arch.room.R;
import com.burhan.arch.room.dbutils.AppDatabase;
import com.burhan.arch.room.dbutils.RoomDB;
import com.burhan.arch.room.models.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.burhan.arch.room.dbutils.Utils.getDateOfBirth;

/**
 * Created by Burhanuddin on 9/9/2017.
 */

public class AddUserDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    public static final String TAG = AddUserDialogFragment.class.getSimpleName();
    public static final String EXTRA_USER_ID = "extraUserID";
    private static final int RESULT_LOAD_IMG = 52;
    EditText edtFirstName, edtLastName, edtAge, edtDob;
    Button btnAddUser;
    ImageView ivEditDob, ivChangeProfile, ivProfilePicture;
    private AppDatabase appDatabase;
    private User user;
    private Date dateOfBith;
    private Bitmap selectedImage;

    public static AddUserDialogFragment show(Context context) {
        AddUserDialogFragment addUserDialogFragment = new AddUserDialogFragment();
        addUserDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG);
        return addUserDialogFragment;
    }

    public static AddUserDialogFragment show(Context context, int userID) {
        AddUserDialogFragment addUserDialogFragment = new AddUserDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_USER_ID, userID);
        addUserDialogFragment.setArguments(bundle);
        addUserDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), TAG);
        return addUserDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();

        if (getArguments() != null) {
            int userID = getArguments().getInt(EXTRA_USER_ID, 0);
            user = appDatabase.userDao().findUserById(userID);
            edtFirstName.setText(user.getFirstName());
            edtLastName.setText(user.getLastName());
            edtAge.setText("" + user.getAge());
            dateOfBith = user.getDateOfBirth();
            edtDob.setText(getDateOfBirth(user.getDateOfBirth()));
            selectedImage = user.getUserBitmapImage();
            if (selectedImage != null) {
                ivProfilePicture.setImageBitmap(selectedImage);
            }
            btnAddUser.setText("Update User");
        }

        ivChangeProfile.setOnClickListener(this);
        btnAddUser.setOnClickListener(this);
        ivEditDob.setOnClickListener(this);

    }

    private void initViews() {
        edtFirstName = getView().findViewById(R.id.edtFirstName);
        edtLastName = getView().findViewById(R.id.edtLastname);
        edtAge = getView().findViewById(R.id.edtAge);
        edtDob = getView().findViewById(R.id.edtDob);
        btnAddUser = getView().findViewById(R.id.btnAdd);
        ivEditDob = getView().findViewById(R.id.ivEditDob);
        ivProfilePicture = getView().findViewById(R.id.ivProfilePicture);
        ivChangeProfile = getView().findViewById(R.id.ivChangeProfile);
        appDatabase = RoomDB.getDefaultInstance();
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int years = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                edtDob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                Calendar instance = Calendar.getInstance();
                instance.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                instance.set(Calendar.MONTH, month);
                instance.set(Calendar.YEAR, year);
                dateOfBith = instance.getTime();
            }
        }, years, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void addUser() {
        if (isValidFrom()) {
            String firstName = edtFirstName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String age = edtAge.getText().toString();
            if (user == null) {
                user = new User();
            }
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(Integer.parseInt(age));
            user.setDateOfBirth(dateOfBith);
            user.setUserBitmapImage(selectedImage);

            //If primary  key is 0 than user is new than insert the data or else update the current user
            if (user.getUid() == 0) {
                appDatabase.userDao().insertAll(user);
            } else {
                appDatabase.userDao().updateUser(user);
            }
            dismiss();
        }
    }

    public boolean isValidFrom() {
        boolean isValid = true;
        if (TextUtils.isEmpty(edtFirstName.getText().toString())) {
            isValid = false;
            edtFirstName.setError("Invalid");
        }
        if (TextUtils.isEmpty(edtLastName.getText().toString())) {
            isValid = false;
            edtLastName.setError("Invalid");
        }
        if (TextUtils.isEmpty(edtAge.getText().toString())) {
            isValid = false;
            edtAge.setError("Invalid");
        }

        if (TextUtils.isEmpty(edtDob.getText().toString())) {
            isValid = false;
            edtDob.setError("Invalid");
        }

        return isValid;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivChangeProfile:
                if (requestStoragePermission()) {
                    openGallery();
                }
                break;
            case R.id.btnAdd:
                addUser();
                break;
            case R.id.ivEditDob:
                openDatePicker();
                break;
        }
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_LOAD_IMG:
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        ivProfilePicture.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }/* else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    public void onStoragePermissionGranted() {
        super.onStoragePermissionGranted();
        openGallery();
    }
}
