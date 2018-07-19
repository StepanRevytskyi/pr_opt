package com.developer.d2.pr_opt;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateClientActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private EditText surname, name, lastname, phone, discountCard, dateOfAdmission, departamentNumber,
            drr, brand, model, template, odSphere, odCylinder, odAxis, odAdd, odDeg, odLens, odIndex,
            odCoating, odPrice, osSphere, osCylinder, osAxis, osAdd, osDeg, osLens, osIndex,
            osCoating, osPrice, framePrice, workPrice, discount, manager;

    private Spinner spinner;

    private double DISCOUNT = 1;
    private double totalPrice = 0;
    private double priceWork = 0;
    private double priceOdLens = 0;
    private double priceOsLens = 0;
    private double priceFrame = 0;
    private double discountPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Common.NAME_DATABASE);

        Toolbar toolbar = findViewById(R.id.update_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(Common.TOOLBAR_ELEVATION);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        surname = findViewById(R.id.update_surname);
        name = findViewById(R.id.update_name);
        lastname = findViewById(R.id.update_lastname);
        phone = findViewById(R.id.update_phone);
        discountCard = findViewById(R.id.update_discount_card);
        dateOfAdmission = findViewById(R.id.update_data_of_admission);
        departamentNumber = findViewById(R.id.update_departament_number);
        drr = findViewById(R.id.update_drr);
        brand = findViewById(R.id.update_brand);
        model = findViewById(R.id.update_model);
        template = findViewById(R.id.update_template);
        odSphere = findViewById(R.id.update_od_sphere);
        odCylinder = findViewById(R.id.update_od_cylinder);
        odAxis = findViewById(R.id.update_od_axis);
        odAdd = findViewById(R.id.update_od_add);
        odDeg = findViewById(R.id.update_od_deg);
        odLens = findViewById(R.id.update_od_lens);
        odIndex = findViewById(R.id.update_od_index);
        odCoating = findViewById(R.id.update_od_coating);
        odPrice = findViewById(R.id.update_od_price);
        osSphere = findViewById(R.id.update_os_sphere);
        osCylinder = findViewById(R.id.update_os_cylinder);
        osAxis = findViewById(R.id.update_os_axis);
        osAdd = findViewById(R.id.update_os_add);
        osDeg = findViewById(R.id.update_os_deg);
        osLens = findViewById(R.id.update_os_lens);
        osIndex = findViewById(R.id.update_os_index);
        osCoating = findViewById(R.id.update_os_coating);
        osPrice = findViewById(R.id.update_os_price);
        framePrice = findViewById(R.id.update_price_frame);
        workPrice = findViewById(R.id.update_price_work);
        discount = findViewById(R.id.update_discount);
        manager = findViewById(R.id.update_manager);

        spinner = findViewById(R.id.update_type_eye_spinner);

        setInfo();
    }

    private void setInfo() {
        surname.setText(Common.selectClient.getSurName());
        name.setText(Common.selectClient.getName());
        lastname.setText(Common.selectClient.getLastName());
        phone.setText(Common.selectClient.getPhone());
        discountCard.setText(Common.selectClient.getDiscountCard());
        dateOfAdmission.setText(Common.selectClient.getDateOfAdmission());
        departamentNumber.setText(Common.selectClient.getDepartamentNumber());
        drr.setText(Common.selectClient.getDrr());
        brand.setText(Common.selectClient.getBrandName());
        model.setText(Common.selectClient.getModelName());
        template.setText(Common.selectClient.getTemplate());
        odSphere.setText(Common.selectClient.getOdSphere());
        odCylinder.setText(Common.selectClient.getOdCylinder());
        odAxis.setText(Common.selectClient.getOdAxis());
        odAdd.setText(Common.selectClient.getOdAdd());
        odDeg.setText(Common.selectClient.getOdDeg());
        odLens.setText(Common.selectClient.getOdLens());
        odIndex.setText(Common.selectClient.getOdIndex());
        odCoating.setText(Common.selectClient.getOdCoating());
        osSphere.setText(Common.selectClient.getOsSphere());
        osCylinder.setText(Common.selectClient.getOsCylinder());
        osAxis.setText(Common.selectClient.getOsAxis());
        osAdd.setText(Common.selectClient.getOsAdd());
        osDeg.setText(Common.selectClient.getOsDeg());
        osLens.setText(Common.selectClient.getOsLens());
        osIndex.setText(Common.selectClient.getOsIndex());
        osCoating.setText(Common.selectClient.getOsCoating());

        if (Common.selectClient.getOdPrice().equals("0.0")) {
            odPrice.setText("");
        } else {
            odPrice.setText(Common.selectClient.getOdPrice());
        }
        if (Common.selectClient.getOsPrice().equals("0.0")) {
            osPrice.setText("");
        } else {
            osPrice.setText(Common.selectClient.getOsPrice());
        }
        if (Common.selectClient.getPriceFrame().equals("0.0")) {
            framePrice.setText("");
        } else {
            framePrice.setText(Common.selectClient.getPriceFrame());
        }
        if (Common.selectClient.getPriceWork().equals("0.0")) {
            workPrice.setText("");
        } else {
            workPrice.setText(Common.selectClient.getPriceWork());
        }
        if (Common.selectClient.getDiscountWork().equals("0.0")) {
            discount.setText("");
        } else {
            discount.setText(Common.selectClient.getDiscountWork());
        }

        manager.setText(Common.selectClient.getManager());

        if (Common.selectClient.getTypeEyepiece().equals("Для роботи")) spinner.setSelection(0);
        if (Common.selectClient.getTypeEyepiece().equals("Для далі")) spinner.setSelection(1);
        if (Common.selectClient.getTypeEyepiece().equals("Для постійного носіння"))
            spinner.setSelection(2);
        if (Common.selectClient.getTypeEyepiece().equals("По окулярам клієнта"))
            spinner.setSelection(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                if (haveNetworkConnection()) {
                    showInfoDialog();
                } else {
                    Toast.makeText(UpdateClientActivity.this, R.string.internet_connection_failed, Toast.LENGTH_LONG)
                            .show();
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean tryParseDouble(String s) {
        double number = 0;
        if (!s.startsWith("0")) {
            try {
                number = Double.valueOf(s);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void showInfoDialog() {
        if (odPrice.getText().toString().length() > 0) {
            if (tryParseDouble(odPrice.getText().toString())) {
                priceOdLens = Math.round(Double.valueOf(odPrice.getText().toString()) * 100) / 100.0d;
            } else {
                Toast.makeText(UpdateClientActivity.this, "Не правильна OD ціна", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (osPrice.getText().toString().length() > 0) {
            if (tryParseDouble(osPrice.getText().toString())) {
                priceOsLens = Math.round(Double.valueOf(osPrice.getText().toString()) * 100) / 100.0d;
            } else {
                Toast.makeText(UpdateClientActivity.this, "Не правильна OS ціна", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (framePrice.getText().toString().length() > 0) {
            if (tryParseDouble(framePrice.getText().toString())) {
                priceFrame = Math.round(Double.valueOf(framePrice.getText().toString()) * 100) / 100.0d;
            } else {
                Toast.makeText(UpdateClientActivity.this, "Не правильна ціна за оправу", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (workPrice.getText().toString().length() > 0) {
            if (tryParseDouble(workPrice.getText().toString())) {
                priceWork = Math.round(Double.valueOf(workPrice.getText().toString()) * 100) / 100.0d;
            } else {
                Toast.makeText(UpdateClientActivity.this, "Не правильна ціна за роботу", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (discount.getText().toString().length() > 0) {
            if (tryParseDouble(discount.getText().toString())) {
                discountPrice = Math.round(Double.valueOf(discount.getText().toString()) * 100) / 100.0d;
            } else {
                Toast.makeText(UpdateClientActivity.this, "Не правильна знижка", Toast.LENGTH_LONG).show();
                return;
            }
        }

        //перевірка на то чи є дисконтна картка(якщо є то знижка в 10%)
        if (discountCard.getText().toString().length() > 0) {
            DISCOUNT = 0.9;
        }

        totalPrice = (priceOdLens + priceOsLens) * DISCOUNT + (priceFrame * DISCOUNT) + priceWork - discountPrice;

        String message =
                "Ціна лінз = " + String.valueOf(priceOdLens + priceOsLens) + "\n" +
                        "Ціна лінз зі знижкою = " + String.valueOf((priceOdLens + priceOsLens) * DISCOUNT) + "\n" +
                        "Ціна оправи = " + String.valueOf(priceFrame) + "\n" +
                        "Ціна оправи зі знижкою = " + String.valueOf(priceFrame * DISCOUNT) + "\n" +
                        "Ціна роботи = " + String.valueOf(priceWork) + "\n" +
                        "Знижка = " + String.valueOf(discountPrice) + "\n" +
                        "Загальна сума = " + String.valueOf(totalPrice);

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateClientActivity.this);
        builder
                .setTitle("Додати нову інформацію?")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSaveClientInfo();
                        dialog.dismiss();
                        UpdateClientActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void onSaveClientInfo() {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Common.NAME_DATABASE);

        Client client = new Client(
                name.getText().toString(),
                surname.getText().toString(),
                lastname.getText().toString(),
                phone.getText().toString(),
                discountCard.getText().toString(),
                dateOfAdmission.getText().toString(),
                departamentNumber.getText().toString(),
                manager.getText().toString(),
                spinner.getSelectedItem().toString(),
                drr.getText().toString(),
                brand.getText().toString(),
                model.getText().toString(),
                template.getText().toString(),

                //ціна оправи
                String.valueOf(priceFrame),
                //ціна оправи зі знижкою
                String.valueOf(priceFrame * DISCOUNT),

                //ціна лінз(OD i OS)
                String.valueOf(priceOdLens + priceOsLens),
                //ціна лінз зі знижкою
                String.valueOf((priceOdLens + priceOsLens) * DISCOUNT),

                //ціна роботи
                String.valueOf(priceWork),

                //знижка
                String.valueOf(discountPrice),

                //Загальна сума
                String.valueOf(totalPrice),

                odSphere.getText().toString(),
                odCylinder.getText().toString(),
                odAxis.getText().toString(),
                odAdd.getText().toString(),
                odDeg.getText().toString(),
                odLens.getText().toString(),
                odIndex.getText().toString(),
                odCoating.getText().toString(),
                String.valueOf(priceOdLens),

                osSphere.getText().toString(),
                osCylinder.getText().toString(),
                osAxis.getText().toString(),
                osAdd.getText().toString(),
                osDeg.getText().toString(),
                osLens.getText().toString(),
                osIndex.getText().toString(),
                osCoating.getText().toString(),
                String.valueOf(priceOsLens),
                "false"
        );

        mDatabaseReference.child(Common.key).setValue(client);
        Toast.makeText(getApplicationContext(), "Інформацію про клієнта додано", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabaseReference.child(Common.key).child("edited").setValue("false");
        UpdateClientActivity.this.finish();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo ni : networkInfos) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateClientActivity.this);
        builder
                .setTitle("")
                .setMessage(R.string.exit_message_text)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateClientActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
