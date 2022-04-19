package es.ucm.fdi.emtntr.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.stopSearch.BusStopLoader;
import es.ucm.fdi.emtntr.stopSearch.BusStopResultListAdapter;
import es.ucm.fdi.emtntr.stopSearch.BusStopInfo;

public class HomeFragment extends Fragment {

    private BusStopLoaderCallBacks busStopLoaderCallBacks;
    private BusStopResultListAdapter busStopResultListAdapter;
    private RecyclerView recyclerView_busStopsList;
    private SearchView searchView;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*Button button = root.findViewById(R.id.search_home_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBusStops(v);
            }
        });*/

        recyclerView_busStopsList = root.findViewById(R.id.search_home_recyclerView_BusStopList);

        busStopLoaderCallBacks = new BusStopLoaderCallBacks(root.getContext());

        busStopResultListAdapter = new BusStopResultListAdapter(root.getContext(), new ArrayList<BusStopInfo>(), inflater);
        recyclerView_busStopsList.setAdapter(busStopResultListAdapter);
        recyclerView_busStopsList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        searchView = (SearchView) root.findViewById(R.id.search_home_searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO
                busStopResultListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchBusStops(root);
        view = root;

        return root;
    }

    public void updateBusStopResultList(List<BusStopInfo> busStopInfoList) {

        //Toast.makeText(getContext(), "Act", Toast.LENGTH_SHORT).show();

        busStopResultListAdapter.setBusStopData(busStopInfoList);
        busStopResultListAdapter.notifyDataSetChanged();
    }

    public void searchBusStops(View view) {

        Bundle queryBundle = new Bundle();
        LoaderManager.getInstance(this).restartLoader(0, queryBundle, busStopLoaderCallBacks);
    }

    public class BusStopLoaderCallBacks implements LoaderManager.LoaderCallbacks<List<BusStopInfo>> {

        private Context context;

        public BusStopLoaderCallBacks(Context context) {
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public BusStopLoader onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {

            BusStopLoader busStopLoader = new BusStopLoader(context, "");

            return busStopLoader;
        }

        @Override
        public void onLoadFinished(@NonNull @NotNull Loader<List<BusStopInfo>> loader, List<BusStopInfo> data) {

            /*if (data.size() < 20) {
                updateBusStopResultList(data);
            }
            else {
                ArrayList<BusStopInfo> busStopInfos = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    busStopInfos.add(data.get(i));
                }
                updateBusStopResultList(busStopInfos);
            }*/
            updateBusStopResultList(data);
        }

        @Override
        public void onLoaderReset(@NonNull @NotNull Loader<List<BusStopInfo>> loader) {

        }
    }
}
