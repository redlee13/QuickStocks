package com.example.android.quickstocks.UI;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quickstocks.Data.GetData;
import com.example.android.quickstocks.Data.MainDatabase;
import com.example.android.quickstocks.Data.MainHelper;
import com.example.android.quickstocks.Data.RetrofitInstance;
import com.example.android.quickstocks.DetailsModel;
import com.example.android.quickstocks.MainModel;
import com.example.android.quickstocks.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";

    private String fullName;
    private MainHelper helper;
    private boolean clicked = false;
    private MainModel mMainModel;

    @BindView(R.id.details_ac_name)
    TextView tvName;
    @BindView(R.id.last_price)
    TextView tvPrice;
    @BindView(R.id.change)
    TextView tvChange;
    @BindView(R.id.percent_change)
    TextView tvPercentChange;
    @BindView(R.id.highest_value)
    TextView tvHigh;
    @BindView(R.id.lowest_value)
    TextView tvLow;
    @BindView(R.id.previous_close_value)
    TextView tvLastClose;
    @BindView(R.id.open_value)
    TextView tvOpen;
    @BindView(R.id.details_full_name)
    TextView tvFullName;
    @BindView(R.id.total_deals)
    TextView tvTotalDeals;
    @BindView(R.id.transaction_volume)
    TextView tvDealsAmount;
    @BindView(R.id.fav_button)
    FloatingActionButton favoriteFab;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMainModel = getArguments().getParcelable("key1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        helper = new MainHelper(getContext());

        String companyUrl = mMainModel.getCompanyUrl();
        fullName = mMainModel.getCompanyName();

        Log.d(TAG, "onCreate: " + mMainModel.getId());
        GetData data = RetrofitInstance.getRetrofit().create(GetData.class);
        Call<List<DetailsModel>> call = data.getCompanyUrl(companyUrl);

        call.enqueue(new Callback<List<DetailsModel>>() {
            @Override
            public void onResponse(Call<List<DetailsModel>> call, Response<List<DetailsModel>> response) {
                Document document = (Document) response.body();

                // Company name
                String name = document.title();
//                name = removeUnwantedChars(name,0,25).substring(0,name.indexOf("|")-1).trim();
                tvFullName.setText(name);
                tvName.setText(fullName);

                // Company Price
                Elements latestPrice = document.select("div[rid]");
                tvPrice.setText(latestPrice.text());

//                // Last Trade
//                Elements lastTrade = document.select("td[rid=CLOSEVALUE]");


                // Change
                Elements change = document.select("td[rid=CHANGE]");
                // To change the color if positive or negative
                if (change.text().contains("(")){
                    tvChange.setTextColor(getResources().getColor(R.color.red));
                    tvChange.setText("-" + removeUnwantedChars(change.text(), 1,1));
                } else {
                    tvChange.setTextColor(getResources().getColor(R.color.green));
                    tvChange.setText(change.text());
                }

                // Percent Change
                Elements percentChange = document.select("td[rid=PERCENTAGECHANGE]");
                // To change the color if positive or negative
                if (percentChange.text().contains("(")){
                    tvPercentChange.setTextColor(getResources().getColor(R.color.red));
                    tvPercentChange.setText("-" + removeUnwantedChars(percentChange.text(), 1,1) + "%");
                } else {
                    tvPercentChange.setTextColor(getResources().getColor(R.color.green));
                    tvPercentChange.setText(percentChange.text() + "%");
                }

                // Open Value
                Elements openValue = document.select("td[rid=OPENVALUE]");
                tvOpen.setText(openValue.text());

                // Lowest Value
                Elements lowest = document.select("td[rid=LOW]");
                tvLow.setText(lowest.text());

                // Highest Value
                Elements highest = document.select("td[rid=HIGH]");
                tvHigh.setText(highest.text());

                // Previous Close
                Elements previousClose = document.select("td[rid=PREVIOUSCLOSEVALUE]");
                tvLastClose.setText(previousClose.text());

                // Total Deals
                Elements totalDeals = document.select("td[rid=CONTRACTCOUNT]");
                tvTotalDeals.setText(totalDeals.text());

                // Deals Amount
                Elements dealsAmount = document.select("td[rid=VOLUME]");
                tvDealsAmount.setText(dealsAmount.text());


                favoriteFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CompanyAsyncTask().execute(mMainModel);
                        clicked = true;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<DetailsModel>> call, Throwable t) {
                Toast.makeText(getContext(), getContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t);
            }
        });


        return view;
    }

    private void markFavorite(MainModel model) {
        helper.insert(model);
    }

    private void unmarkFavorite(MainModel model) {
        helper.delete(model);
    }

    private static String removeUnwantedChars(String str, int start, int end) {
        return str.substring(start, str.length() - end);
    }

    private class CompanyAsyncTask extends AsyncTask<MainModel, Void, MainModel> {
        @Override
        protected MainModel doInBackground(MainModel... mainModels) {
            MainDatabase database = MainDatabase.getInstance(getContext());
            return database.mainDao().getSingleCompany(mainModels[0].getCompanyName());
        }

        @Override
        protected void onPostExecute(MainModel mainModel) {
            if (clicked) {
                if (mainModel != null){
                    unmarkFavorite(mMainModel);
                    favoriteFab.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                } else {
                    markFavorite(mMainModel);
                    favoriteFab.setImageResource(R.drawable.ic_baseline_favorite_24px);
                }
            } else {
                if (mainModel != null){
                    favoriteFab.setImageResource(R.drawable.ic_baseline_favorite_24px);
                } else {
                    favoriteFab.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                }
            }
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
