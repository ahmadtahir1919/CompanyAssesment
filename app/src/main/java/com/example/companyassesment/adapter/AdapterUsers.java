package com.example.companyassesment.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.companyassesment.R;
import com.example.companyassesment.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.AdapterUsersViewHolder> implements Filterable {


    private ArrayList<User> locationList;
    ArrayList<User> filteredUsersList = new ArrayList<>();
    Context context;
    private OnItemCancelClick onItemCancelClick;

    public AdapterUsers(Context context, ArrayList<User> usersList) {
        this.context = context;
        this.locationList = usersList;
        this.filteredUsersList = usersList;
    }

    @NonNull
    @Override
    public AdapterUsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new AdapterUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUsersViewHolder holder, int position) {
        setDataToTheView(holder, position);


    }

    @Override
    public int getItemCount() {
        if (filteredUsersList != null)
            return filteredUsersList.size();
        else
            return 0;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredUsersList = locationList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User row : locationList) {
                        String[] splitStr = charString.split("\\s+");

                        if (row.getName().toLowerCase().contains(splitStr[0].toLowerCase()) ||
                                row.getPhone().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }
                    filteredUsersList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUsersList;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUsersList = (ArrayList<User>) filterResults.values;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }

                ;
    }

    class AdapterUsersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        AppCompatTextView txtName;
        @BindView(R.id.txt_user_contact_number)
        AppCompatTextView txtUserContactNumber;
        @BindView(R.id.txt_short_name)
        AppCompatTextView txtShortName;
        @BindView(R.id.user_image)
        CircleImageView userImage;

        AdapterUsersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void setDataToTheView(@NonNull AdapterUsersViewHolder holder, int position) {
        holder.txtName.setText(filteredUsersList.get(position).getName());
        holder.itemView.setOnClickListener(v -> onItemCancelClick.onItemCancelClick(position));
        holder.txtUserContactNumber.setText(String.valueOf(filteredUsersList.get(position).getPhone()));
        holder.txtShortName.setText(String.valueOf(filteredUsersList.get(position).getName().charAt(0)));
        if (position % 2 == 0) {
            Picasso
                    .get()
                    .load("https://via.placeholder.com/150/B0E8AF/B0E8AF%20C/O%20https://placeholder.com/")
                    .noFade()
                    .into(holder.userImage);
        } else {
            Picasso
                    .get()
                    .load("https://via.placeholder.com/150/6200EE/6200EE%20C/O%20https://placeholder.com/")
                    .noFade()
                    .into(holder.userImage);
        }
    }

    public interface OnItemCancelClick {
        void onItemCancelClick(int position);
    }

    public void setItemCancelClick(OnItemCancelClick onItemCancelClick) {
        this.onItemCancelClick = onItemCancelClick;
    }
}


