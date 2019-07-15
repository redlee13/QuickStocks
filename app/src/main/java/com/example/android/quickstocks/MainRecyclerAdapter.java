package com.example.android.quickstocks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.quickstocks.UI.DetailsActivity;

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
        holder.companyUrl.setText(mList.get(position).getCompanyUrl());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.company_name)
        TextView companyName;
        @BindView(R.id.company_price)
        TextView companyUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, mList.get(getAdapterPosition()).getCompanyUrl());
                    intent.putExtra(Intent.EXTRA_TITLE, mList.get(getAdapterPosition()).getCompanyName());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
