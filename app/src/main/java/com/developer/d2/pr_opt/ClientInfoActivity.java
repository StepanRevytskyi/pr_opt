package com.developer.d2.pr_opt;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClientInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView mListView;

    private DatabaseReference mDatabaseReference;

    public ArrayList<String> clientInfo = new ArrayList<>();
    public ArrayList<String> clientInfoDescription = new ArrayList<>();
    public ArrayList<Drawable> clientInfoIcon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        setClientInfo();

        mToolbar = findViewById(R.id.info_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(Common.TOOLBAR_ELEVATION);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = findViewById(R.id.info_list_view);
        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);

    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return clientInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_layout, null);

            ImageView imageView = convertView.findViewById(R.id.icon_image_view);
            TextView infoTextView = convertView.findViewById(R.id.info_text_view);
            TextView descriptionTextView = convertView.findViewById(R.id.description_text_view);

            imageView.setImageDrawable(clientInfoIcon.get(position));
            infoTextView.setText(clientInfo.get(position));
            descriptionTextView.setText(clientInfoDescription.get(position));

            return convertView;
        }
    }

    private void setClientInfo() {
        clientInfo.add(Common.selectClient.getSurName() + " " + Common.selectClient.getName() + " " + Common.selectClient.getLastName());
        clientInfoDescription.add(getResources().getString(R.string.client_name));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_person_28dp));

        clientInfo.add(Common.selectClient.getPhone());
        clientInfoDescription.add(getResources().getString(R.string.Phone));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_call_28dp));

        clientInfo.add(Common.selectClient.getDiscountCard());
        clientInfoDescription.add(getResources().getString(R.string.DiscountСard));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_payment_28dp));

        clientInfo.add(Common.selectClient.getDateOfAdmission());
        clientInfoDescription.add(getResources().getString(R.string.DateOfAdmission));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_date_range_28dp));

        clientInfo.add(Common.selectClient.getDepartamentNumber());
        clientInfoDescription.add(getResources().getString(R.string.DepartamentNumber));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_home_28dp));

        clientInfo.add(Common.selectClient.getTypeEyepiece());
        clientInfoDescription.add(getResources().getString(R.string.TypeEyepiece));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getDRR());
        clientInfoDescription.add(getResources().getString(R.string.DRR));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getBrandName());
        clientInfoDescription.add(getResources().getString(R.string.BrandName));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getModelName());
        clientInfoDescription.add(getResources().getString(R.string.ModelName));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getTemplate());
        clientInfoDescription.add(getResources().getString(R.string.Template));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getPriceFrame());
        clientInfoDescription.add(getResources().getString(R.string.PriceFrame));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getDiscountFrame());
        clientInfoDescription.add(getResources().getString(R.string.DiscountFrame));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getPriceLenses());
        clientInfoDescription.add(getResources().getString(R.string.PriceLenses));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getDiscountLenses());
        clientInfoDescription.add(getResources().getString(R.string.DiscountLenses));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getPriceWork());
        clientInfoDescription.add(getResources().getString(R.string.PriceWork));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getDiscountWork());
        clientInfoDescription.add(getResources().getString(R.string.DiscountWork));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getTotalPrice());
        clientInfoDescription.add(getResources().getString(R.string.TotalPrice));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

//OD
        clientInfo.add(Common.selectClient.getOdSphere());
        clientInfoDescription.add(getResources().getString(R.string.OdSphere));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOdCylinder());
        clientInfoDescription.add(getResources().getString(R.string.OdCylinder));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOdAxis());
        clientInfoDescription.add(getResources().getString(R.string.OdAxis));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOdAdd());
        clientInfoDescription.add(getResources().getString(R.string.OdAdd));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOdLens());
        clientInfoDescription.add(getResources().getString(R.string.OdLens));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOdIndex());
        clientInfoDescription.add(getResources().getString(R.string.OdIndex));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOdCoating());
        clientInfoDescription.add(getResources().getString(R.string.OdCoating));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOdPrice());
        clientInfoDescription.add(getResources().getString(R.string.OdPrice));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

//OS
        clientInfo.add(Common.selectClient.getOsSphere());
        clientInfoDescription.add(getResources().getString(R.string.OsSphere));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOsCylinder());
        clientInfoDescription.add(getResources().getString(R.string.OsCylinder));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOsAxis());
        clientInfoDescription.add(getResources().getString(R.string.OsAxis));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOsAdd());
        clientInfoDescription.add(getResources().getString(R.string.OsAdd));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOsLens());
        clientInfoDescription.add(getResources().getString(R.string.OsLens));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOsIndex());
        clientInfoDescription.add(getResources().getString(R.string.OsIndex));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOsCoating());
        clientInfoDescription.add(getResources().getString(R.string.OsCoating));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getOsPrice());
        clientInfoDescription.add(getResources().getString(R.string.OsPrice));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));

        clientInfo.add(Common.selectClient.getManager());
        clientInfoDescription.add(getResources().getString(R.string.name_manager));
        clientInfoIcon.add(getResources().getDrawable(R.drawable.ic_default_image_view_28dp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_client:
                return true;
            case R.id.delete_client:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Common.NAME_DATABASE);
        mDatabaseReference.child(Common.key).child("mEdited").setValue("false");
        this.finish();
    }
}
