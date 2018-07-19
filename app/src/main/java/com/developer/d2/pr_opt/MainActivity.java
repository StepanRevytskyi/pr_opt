package com.developer.d2.pr_opt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    private MaterialSearchView searchView;
    private EditText searchEditText;
    private RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<Client, ClientViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference(Common.NAME_DATABASE);
        databaseReference.keepSynced(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);

        if (Common.searchText.length() > 0 && haveNetworkConnection()) {
            searchInFirebase(Common.searchText);
        }

        searchEditText = findViewById(R.id.searchTextView);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        && searchEditText.getText().toString().length() > 0
                        && haveNetworkConnection()) {
                    Common.searchText = searchEditText.getText().toString();
                    searchInFirebase(searchEditText.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, R.string.internet_connection_failed, Toast.LENGTH_LONG)
                            .show();
                }
                return true;
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewClientActivity.class);
                startActivity(intent);
            }
        });
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

    private void searchInFirebase(String searchText) {
        Query firebaseSearchQuery;

        if (searchText.matches("[0-9]+") || ((searchText.contains("+") && searchText.matches("[0-9]+")))) {
            firebaseSearchQuery = databaseReference.orderByChild("phone").startAt(searchText).endAt(searchText + "\uf8ff");
        } else {
            firebaseSearchQuery = databaseReference.orderByChild("surName").startAt(searchText).endAt(searchText + "\uf8ff");
        }

        FirebaseRecyclerOptions<Client> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(firebaseSearchQuery, Client.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Client, ClientViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ClientViewHolder holder, final int position, @NonNull final Client model) {
                holder.setInfo(model.getSurName(), model.getName(), model.getTypeEyepiece());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getEdited().equals("false")) {
                            Intent intent = new Intent(MainActivity.this, ClientInfoActivity.class);
                            Common.selectClient = model;
                            Common.key = firebaseRecyclerAdapter.getRef(position).getKey();
                            databaseReference.child(Common.key).child("edited").setValue("true");
                            startActivity(intent);
                        } else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder
                                    .setTitle(R.string.error_text)
                                    .setMessage(R.string.error_message)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layour_row, parent, false);
                return new ClientViewHolder(itemView);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        View view;

        ClientViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setInfo(String surname, String name, String typeEyepiece) {
            TextView surnameTextView = view.findViewById(R.id.surname_text_view);
            TextView nameTextView = view.findViewById(R.id.name_text_view);
            TextView typeEyepieceTextView = view.findViewById(R.id.type_eyepiece_text_view);

            surnameTextView.setText(surname);
            nameTextView.setText(name);
            typeEyepieceTextView.setText(typeEyepiece);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        Common.searchText = "";
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
