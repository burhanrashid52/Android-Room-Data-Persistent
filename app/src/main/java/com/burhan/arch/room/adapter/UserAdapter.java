package com.burhan.arch.room.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.burhan.arch.room.R;
import com.burhan.arch.room.dbutils.AppDatabase;
import com.burhan.arch.room.dbutils.RoomDB;
import com.burhan.arch.room.models.User;

import static com.burhan.arch.room.dbutils.Utils.getDateOfBirth;


/**
 * Created by Burhanuddin on 9/9/2017.
 */

public class UserAdapter extends PagedListAdapter<User, UserAdapter.ViewHolder> {
    //  private List<User> userList;
    private onItemClickListener mOnItemClickListener;

    public UserAdapter() {
        super(DIFF_CALLBACK);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_users, parent, false);
        return new ViewHolder(view);
    }

    public interface onItemClickListener {
        void onClick(View view, int position);
    }


    public User getUserItem(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = getItem(position);
        if (user != null) {
            holder.txtItemNumber.setText((position + 1) + ".");
            holder.txtUserName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
            holder.txtUserAge.setText(String.format("%d years", user.getAge()));
            holder.txtUserDob.setText(getDateOfBirth(user.getDateOfBirth()));
            if (user.getUserBitmapImage() != null) {
                holder.ivProfilePic.setImageBitmap(user.getUserBitmapImage());
            }
        }
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtUserAge, txtUserDob, txtItemNumber;
        ImageView btnDelete, btnUpdate, ivProfilePic;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtItemNumber = itemView.findViewById(R.id.txtItemNumber);
            txtUserAge = itemView.findViewById(R.id.txtUserAge);
            txtUserDob = itemView.findViewById(R.id.txtDOB);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePicture);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(view, getLayoutPosition());
                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                    alertDialog.setTitle("Delete");
                    alertDialog.setMessage("Are you sure want to delete this user ?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AppDatabase appDatabase = RoomDB.getDefaultInstance();
                            appDatabase.userDao().delete(getItem(getLayoutPosition()));
                            //  userList.remove(getLayoutPosition());
                            notifyItemRemoved(getLayoutPosition());
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.create().show();
                }
            });
        }
    }


    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldUser, @NonNull User newUser) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldUser.getUid() == newUser.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldUser, @NonNull User newUser) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldUser.equals(newUser);
        }
    };
}
