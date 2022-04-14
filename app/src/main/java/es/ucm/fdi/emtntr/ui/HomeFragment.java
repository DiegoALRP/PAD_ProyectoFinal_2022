package es.ucm.fdi.emtntr.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.model.BusStop;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    public class BusStopLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<BusStop>> {

        public static final String EXTRA_AUTHOR = "queryStringAuthor";
        public static final String EXTRA_TITLE = "queryStringTitle";
        public static final String EXTRA_PRINT_TYPE = "printType";
        private Context context;

        public BusStopLoaderCallBacks(Context context) {
            this.context = context;
        }

        /*@NotNull
        @Override
        public BookLoader onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            BookLoader bookLoader = new BookLoader(context, args.getString(EXTRA_AUTHOR), args.getString(EXTRA_TITLE), args.getString(EXTRA_PRINT_TYPE));

            return bookLoader;
        }*/

        /*@Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
            if(data.size() == 0) txtv_result.setText("No Results Found");
            else txtv_result.setText("Results");

            imageViewLoading.setImageResource(R.drawable.loading);
            imageViewLoading.setVisibility(View.INVISIBLE);

            updateBooksResultList(data);
        }*/


        @NonNull
        @NotNull
        @Override
        public Loader<List<BusStop>> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BusStop>> loader, List<BusStop> data) {

        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<BusStop>> loader) {

        }
    }
}
