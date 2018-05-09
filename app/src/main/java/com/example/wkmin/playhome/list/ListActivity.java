
package com.example.wkmin.playhome.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wkmin.playhome.R;
import com.example.wkmin.playhome.data.source.PlayHomeRepository;
import com.example.wkmin.playhome.data.source.network.ItemModel;
import com.example.wkmin.playhome.map.MapsActivity;

import java.util.LinkedList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    private ListContract.Presenter presenter;
    private FlexAdapter adapter;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setRecyclerView();
        setMVP();
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
        new ListPresenter(this, PlayHomeRepository.getInstance());
        requestNaverSearch();
    }

    private void requestNaverSearch() {
        if (presenter != null) {
            Intent intent = getIntent();
            String searchText = intent.getStringExtra(MapsActivity.EXTRA_SEARCH_TEXT);
            System.out.println("SearchText -> " + searchText);
            String title[] = searchText.split(" ");
            setTitle(title[1]);

            presenter.requestNaverApi(searchText);
        }
    }

    @Override
    public void showItems(ItemModel itemModel) {
        adapter.addItem(itemModel);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoItem() {
        AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
        alertDialog.setTitle("알림");
        alertDialog.setMessage("네이버 검색 결과가 없습니다.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    class FlexAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private LinkedList<ItemModel.Item> items = new LinkedList<>();

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_cell, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.bindItem(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        void addItem(ItemModel itemModel) {
            System.out.println("item size:" + itemModel.getItems().size());
            items.addAll(itemModel.getItems());
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

        public void bindItem(ItemModel.Item item) {
            itemView.setTag(item);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                itemTitle.setText(Html.fromHtml(item.getTitle(), Html.FROM_HTML_MODE_COMPACT));
                itemDesc.setText(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                itemTitle.setText(Html.fromHtml(item.getTitle()));
                itemDesc.setText(Html.fromHtml(item.getDescription()));
            }
        }


        @Override
        public void onClick(View v) {
            ItemModel.Item item = (ItemModel.Item) v.getTag();

            String urlStr = item.getLink().replace("&amp;", "&");
            System.out.println("urlStr:"+urlStr);
            Uri webPage = Uri.parse(urlStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            startActivity(intent);
        }
    }
}
