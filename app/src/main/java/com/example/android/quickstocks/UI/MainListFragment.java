package com.example.android.quickstocks.UI;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.quickstocks.Data.GetData;
import com.example.android.quickstocks.Data.RetrofitInstance;
import com.example.android.quickstocks.MainModel;
import com.example.android.quickstocks.MainRecyclerAdapter;
import com.example.android.quickstocks.MarketPriceWidget;
import com.example.android.quickstocks.R;
import com.example.android.quickstocks.ViewModel;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainListFragment extends Fragment {

    @BindView(R.id.main_list_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.market_price)
    TextView marketPrice;
    @BindView(R.id.market_change)
    TextView marketChange;
    @BindView(R.id.market_percentage)
    TextView marketPercentage;

    private MainRecyclerAdapter mAdapter;
    private List<MainModel> modelList;
    private ViewModel mViewModel;

    public static final String PREF_WIDGET = "pref_";
    public static final String PREF_WIDGET_MAIN = "pref_main";
    public static final String PREF_WIDGET_CHNAGE = "pref_change";
    public static final String PREF_WIDGET_PERCENT = "pref_percent";

    private String price;
    private String change;
    private String percentChange;

    private OnFragmentInteractionListener mListener;

    public MainListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        ButterKnife.bind(this, view);


        modelList = new ArrayList<>();

        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        mAdapter = new MainRecyclerAdapter(modelList, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        saveForWidget();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listLoader();
        priceLoader();
        saveForWidget();
    }

    private void listLoader(){
        if (getSort().equals(getString(R.string.pref_fav))){
            mViewModel.getLiveDate(true).observe(this, new Observer<List<MainModel>>() {
                @Override
                public void onChanged(List<MainModel> mainModels) {
                    modelList.clear();
                    if (mainModels != null){
                        modelList.addAll(mainModels);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            mViewModel.getLiveDate(false).observe(this, new Observer<List<MainModel>>() {
                @Override
                public void onChanged(List<MainModel> mainModels) {
                    modelList.clear();
                    modelList.addAll(mainModels);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void priceLoader(){
        Call<List<MainModel>> call = RetrofitInstance.getRetrofit().create(GetData.class).getTasi();
        call.enqueue(new Callback<List<MainModel>>() {
            @Override
            public void onResponse(Call<List<MainModel>> call, Response<List<MainModel>> response) {
                Document document = (Document) response.body();

                price = document.select("div[id=index_value_holder_3]").text();
                marketPrice.setText(price);
                change = document.select("span[id=li_data_holder_change_3]").text();
                marketChange.setText(change);
                percentChange = document.select("span[id=li_data_holder_pchange_3]").text();
                marketPercentage.setText(percentChange);
                if (percentChange.contains("(")){
                    marketPercentage.setTextColor(getResources().getColor(R.color.red));
                    marketChange.setTextColor(getResources().getColor(R.color.red));
                } else {
                    marketPercentage.setTextColor(getResources().getColor(R.color.green));
                    marketChange.setTextColor(getResources().getColor(R.color.green));
                }

            }

            @Override
            public void onFailure(Call<List<MainModel>> call, Throwable t) {

            }
        });

    }


    private String getSort() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString(getString(R.string.list_key), getString(R.string.pref_normal));
    }


    private void saveForWidget() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREF_WIDGET, 0).edit();
        editor.putString(PREF_WIDGET_MAIN, price);
        editor.putString(PREF_WIDGET_CHNAGE, change);
        editor.putString(PREF_WIDGET_PERCENT, percentChange);

        editor.apply();
        MarketPriceWidget.update(getContext());
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
