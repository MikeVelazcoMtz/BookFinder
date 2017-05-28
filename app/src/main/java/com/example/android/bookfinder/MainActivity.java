package com.example.android.bookfinder;

import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private BookAdapter bookAdapter;

    private LoaderManager loaderManager;

    private EditText search_txt;

    private Button search_btn;

    private ProgressBar progressBar;

    private TextView noItems;

    private boolean firstRun;

    // The callbacks through which we will interact with the LoaderManager.
    private LoaderManager.LoaderCallbacks<List<Book>> mCallbacks;

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = v.getContext();

            Bundle args = new Bundle();
            args.putString("search", search_txt.getText().toString());


            if (bookAdapter.getCount() > 0) {
                bookAdapter.clear();
            }

            loaderManager.restartLoader(1, args, mCallbacks);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if there is internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnectedOrConnecting() || !networkInfo.isAvailable()) {
            AlertDialog alert = createSimpleDialog();
            alert.show();
        }



        search_btn = (Button) findViewById(R.id.search_btn);

        search_txt = (EditText) findViewById(R.id.search_text);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        noItems = (TextView) findViewById(R.id.no_items_found);


        ListView listView = (ListView) findViewById(R.id.books_list);

        bookAdapter = new BookAdapter(this, new ArrayList<Book>());

        listView.setAdapter(bookAdapter);

        mCallbacks = this;

        loaderManager = getLoaderManager();

        Bundle args = new Bundle();
        args.putString("search", "");

        firstRun = true;

        loaderManager.initLoader(1, args, mCallbacks);

        search_btn.setOnClickListener(searchListener);


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new BookLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        progressBar.setVisibility(View.GONE);

        // If we made a previous request
        if (bookAdapter.getCount() > 0) {
            bookAdapter.clear();
        } else {
            if (firstRun) {
                firstRun = false;
            } else {
                noItems.setVisibility(View.VISIBLE);
            }
        }

        if (data != null && !data.isEmpty()) {
            noItems.setVisibility(View.GONE);
            bookAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        noItems.setVisibility(View.GONE);
        bookAdapter.clear();
    }


    /**
     * Crea un diálogo de alerta sencillo
     *
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.dialog_no_internet_title))
                .setMessage(getString(R.string.dialog_no_internet_content))
                .setPositiveButton(getString(R.string.dialog_no_internet_positive),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Exit app
                                System.exit(0);
                            }
                        })
                .setNegativeButton(getString(R.string.dialog_no_internet_negative),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // show message and disable button
                                TextView noInternet = (TextView) findViewById(R.id.no_internet);
                                noInternet.setVisibility(View.VISIBLE);
                                search_btn.setEnabled(false);
                            }
                        });

        return builder.create();
    }
}
