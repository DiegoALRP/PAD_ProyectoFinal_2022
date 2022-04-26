package es.ucm.fdi.emtntr.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import es.ucm.fdi.emtntr.R;
import es.ucm.fdi.emtntr.favorites.FavouriteBusInfo;
import es.ucm.fdi.emtntr.favorites.FavouriteListAdapter;

public class FavouriteListFragment extends Fragment {

    private FavouriteListAdapter favouriteListAdapter;
    private RecyclerView recyclerView_busStopList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String filenameFav = "FavouritesBusStopList.json";
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance("https://pad-proyectofinal-1-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        /*Button button_1 = root.findViewById(R.id.button);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_test(v);
            }
        });*/

        recyclerView_busStopList = root.findViewById(R.id.favourite_home_recyclerView_BusStopList);

        favouriteListAdapter = new FavouriteListAdapter(root.getContext(), new ArrayList<>(), inflater, getParentFragmentManager());
        recyclerView_busStopList.setAdapter(favouriteListAdapter);
        recyclerView_busStopList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        retrieveFavouritesBusStop();

        return root;
    }

    private void updateFavouriteBusList(ArrayList<FavouriteBusInfo> favouriteBusList) {

        favouriteListAdapter.setBusStopData(favouriteBusList);
        favouriteListAdapter.notifyDataSetChanged();
    }

    private void retrieveFavouritesBusStop() {

        ArrayList<FavouriteBusInfo> favouriteBusList = new ArrayList<>();

        databaseReference.child(mAuth.getUid()).child("Favourites")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {

                    HashMap<String, Object> map = (HashMap<String, Object>) task.getResult().getValue();

                    if (map != null) {

                        Gson gson = new Gson();
                        for (Object favouriteBusInfo: map.values()) {

                            String data = gson.toJson(favouriteBusInfo);

                            FavouriteBusInfo favBus = gson.fromJson(data, FavouriteBusInfo.class);

                            favouriteBusList.add(favBus);
                        }

                        updateFavouriteBusList(favouriteBusList);
                    }
                }
            }
        });
    }



    public void button_test(View view){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        transaction.replace(R.id.nav_host_fragment, FavouriteFragment.newInstance("a","A"), null);

        transaction.commit();
    }

}
