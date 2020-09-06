package com.example.myprofiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerListView extends RecyclerView.Adapter<RecyclerListView.ProfileViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private Repository repository;
    private List<ProfileDatabaseEntity> profileData = new ArrayList<>();

    public RecyclerListView(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        repository = new Repository(context);
    }

    public void setProfileData(List<ProfileDatabaseEntity> data) { profileData = data; }
    public List<ProfileDatabaseEntity> getProfileData() { return profileData; }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView siteName;
        private TextView displayMessage;
        private List<ProfileDatabaseEntity> data;
        private Context context;

        public ProfileViewHolder(@NonNull View itemView, List<ProfileDatabaseEntity> data, Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            siteName = itemView.findViewById(R.id.website);
            displayMessage = itemView.findViewById(R.id.link);
            displayMessage.setOnClickListener(listener);

            this.data = data;
            this.context = context;
        }

        /*
        Activity Action: Display the data to the user. This is the most common action performed on
        data -- it is the generic action you can use on a piece of data to get the most reasonable
        thing to occur. For example, when used on a contacts entry it will view the entry; when
        used on a mailto: URI it will bring up a compose window filled with the information supplied
        by the URI; when used with a tel: URI it will invoke the dialer.
        Input: getData() is URI from which to retrieve data.
         */

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = getAdapterPosition();
                ProfileDatabaseEntity values = data.get(position);
                String url = values.getProfileUrl();
                Intent openSite = new Intent(Intent.ACTION_VIEW);
                openSite.setData(Uri.parse(url));

                context.startActivity(openSite);
            }
        };
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_layout, parent, false);
        return new ProfileViewHolder(itemView, profileData, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        ProfileDatabaseEntity details = profileData.get(position);
        holder.siteName.setText(details.getWebsiteName());
        String goToProfile = "Go to your " + details.getWebsiteName() + " profile";
        holder.displayMessage.setText(goToProfile);
        Glide.with(context)
                .load(details.getImageUrl())
                .placeholder(R.drawable.letter_p)
                .error(R.drawable.error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return profileData.size();
    }
}
