package com.example.wkmin.playhome.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wkmin.playhome.R;
import com.example.wkmin.playhome.data.HistoryHome;
import com.example.wkmin.playhome.data.source.PlayHomeRepository;

import java.util.LinkedList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class HistoryActivity extends AppCompatActivity implements HistoryContract.View {

    private HistoryContract.Presenter presenter;
    private FlexAdapter adapter;

    @BindView(R.id.list_history)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setMVP();
        setRecyclerView();

        loadHistory();
    }

    private void loadHistory() {
        this.presenter.getHistory();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRecyclerView() {
        adapter = new FlexAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setMVP() {
        new HistoryPresenter(this, PlayHomeRepository.getInstance());
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showHistory(RealmResults<HistoryHome> historyHomes) {
        System.out.println("size:" + historyHomes.size());
        adapter.addItem(historyHomes);
    }


    class FlexAdapter extends RecyclerView.Adapter<HistoryActivity.MyViewHolder> {

        private LinkedList<HistoryHome> items = new LinkedList<>();

        @NonNull
        @Override
        public HistoryActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_cell, parent, false);
            return new HistoryActivity.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryActivity.MyViewHolder holder, int position) {
            holder.bindItem(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        void addItem(RealmResults<HistoryHome> HistoryHomes) {
            items.addAll(HistoryHomes);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_title)
        TextView itemTitle;

        @BindView(R.id.item_desc)
        TextView itemDesc;


        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        public void bindItem(HistoryHome item) {
            itemView.setTag(item);
            itemTitle.setText(Html.fromHtml(item.getName()));
            itemDesc.setText(Html.fromHtml(item.getAddress()));
        }


        @Override
        public void onClick(View v) {
            HistoryHome item = (HistoryHome) v.getTag();
            Intent intent = new Intent();
            intent.putExtra("latitude", item.getLatitude());
            intent.putExtra("longitude", item.getLongitude());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
