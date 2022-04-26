package es.ucm.fdi.emtntr.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;

import es.ucm.fdi.emtntr.R;

public class FavouriteListFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        /*Button button_1 = root.findViewById(R.id.button);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_test(v);
            }
        });*/

        return root;
    }

    public void button_test(View view){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        transaction.replace(R.id.nav_host_fragment, FavouriteFragment.newInstance("a","A"), null);

        transaction.commit();
    }

}
