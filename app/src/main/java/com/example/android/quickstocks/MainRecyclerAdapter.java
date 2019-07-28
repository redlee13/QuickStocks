package com.example.android.quickstocks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.quickstocks.UI.DetailsFragment;
import com.example.android.quickstocks.UI.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    private List<MainModel> mList;
    private Context mContext;

    public MainRecyclerAdapter(List<MainModel> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.companyName.setText(mList.get(position).getCompanyName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.company_name)
        TextView companyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("key1", mList.get(getAdapterPosition()));
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(bundle);
                    fragmentLoader(detailsFragment);
                }
            });
        }
    }

    private void fragmentLoader(DetailsFragment detailsFragment){
        if (mContext.getResources().getBoolean(R.bool.isTablet)){
            ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment)
                    .commit();
        } else {
            ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.list_container, detailsFragment)
                    .commit();
        }
    }
}
