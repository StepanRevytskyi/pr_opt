package com.developer.d2.pr_opt;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private MaterialSearchView mSearchView;
    private EditText mSearchEditText;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    private DatabaseReference mDatabaseReference;
    private Query mFirebaseSearchQuery;
    private FirebaseRecyclerAdapter<Client, ClientViewHolder> mFirebaseRecyclerAdapter;
    private FirebaseRecyclerOptions<Client> mFirebaseRecyclerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Common.NAME_DATABASE);
        mDatabaseReference.keepSynced(true);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchView = findViewById(R.id.search_view);
        mSearchView.setVoiceSearch(false);

        if (Common.searchText.length() > 0) {
            firebaseSearch(Common.searchText);
        }

        mSearchEditText = findViewById(R.id.searchTextView);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && mSearchEditText.getText().toString().length() > 0) {
                    Common.searchText = mSearchEditText.getText().toString();
                    firebaseSearch(mSearchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
//TODO: написати добавлення нового клієнта
        mFloatingActionButton = findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewClientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void firebaseSearch(String searchText) {

        mFirebaseSearchQuery = mDatabaseReference.orderByChild("mSurName").startAt(searchText).endAt(searchText + "\uf8ff");

        mFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Client>()
                .setQuery(mFirebaseSearchQuery, Client.class)
                .build();

        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Client, ClientViewHolder>(mFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ClientViewHolder holder, final int position, @NonNull final Client model) {
                holder.setInfo(model.getSurName(), model.getName(), model.getTypeEyepiece());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getEdited().equals("false")) {
                            Intent intent = new Intent(MainActivity.this, ClientInfoActivity.class);
                            Common.selectClient = model;
                            Common.key = mFirebaseRecyclerAdapter.getRef(position).getKey();
                            mDatabaseReference.child(Common.key).child("mEdited").setValue("true");
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

        mFirebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(mFirebaseRecyclerAdapter);
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        View mView;

        ClientViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setInfo(String surname, String name, String typeEyepiece) {
            TextView surnameTextView = mView.findViewById(R.id.surname_text_view);
            TextView nameTextView = mView.findViewById(R.id.name_text_view);
            TextView typeEyepieceTextView = mView.findViewById(R.id.type_eyepiece_text_view);

            surnameTextView.setText(surname);
            nameTextView.setText(name);
            typeEyepieceTextView.setText(typeEyepiece);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseRecyclerAdapter != null) {
            mFirebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        if (mFirebaseRecyclerAdapter != null) {
            mFirebaseRecyclerAdapter.stopListening();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFirebaseRecyclerAdapter != null) {
            mFirebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

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
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
