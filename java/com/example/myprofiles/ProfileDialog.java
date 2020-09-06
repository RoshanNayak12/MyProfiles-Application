package com.example.myprofiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ProfileDialog extends DialogFragment {

    private Context activityContext;
    private Repository repository;

    ProfileDialog(Context context) {
        activityContext = context;
        repository = new Repository(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(activityContext);
        final View dialogLayoutView = inflater.inflate(R.layout.dialog_layout, null);
        final Dialog alertDialog = dialogBuilder.setView(dialogLayoutView).create();
        alertDialog.setCanceledOnTouchOutside(false);

        Button addButton = dialogLayoutView.findViewById(R.id.add_profile);
        Button cancelButton = dialogLayoutView.findViewById(R.id.cancel_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText websiteName = dialogLayoutView.findViewById(R.id.website_name);
                EditText imageUrl = dialogLayoutView.findViewById(R.id.image_url);
                EditText profileUrl = dialogLayoutView.findViewById(R.id.profile_link);

                String website = websiteName.getText().toString();
                String image = imageUrl.getText().toString();
                String profile = profileUrl.getText().toString();

                if (website.equals("") || image.equals("") || profile.equals("")) {
                    Toast.makeText(activityContext.getApplicationContext(), "Fill the all details.", Toast.LENGTH_SHORT).show();
                } else {
                    ProfileDatabaseEntity profileDetails = new ProfileDatabaseEntity();
                    profileDetails.setWebsiteName(website);
                    profileDetails.setImageUrl(image);
                    profileDetails.setProfileUrl(profile);
                    repository.insertProfile(profileDetails);
                    ProfileDatabase.threadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Yes", "Size of the database = " + repository.getProfileCount());
                        }
                    });
                    Toast.makeText(activityContext, "Profile added", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activityContext, "Profile not added.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }
}
