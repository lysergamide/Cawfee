package pw.tewi.cawfee.ui.preferences.sitepreferences;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lombok.NonNull;
import pw.tewi.cawfee.databinding.ActivitySitePreferencesBinding;
import pw.tewi.cawfee.hilt.components.DaggerImageBoardMap;

@AndroidEntryPoint
public final class SitePreferencesActivity extends AppCompatActivity {
    @Inject SitePreferencesAdapter adapter;

    ActivitySitePreferencesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySitePreferencesBinding.inflate(getLayoutInflater());
        binding.sitePreferencesList.setAdapter(adapter);

        DaggerImageBoardMap.create()
                           .imageBoardMap()
                           .forEach((name, imageBoard) -> adapter.add(imageBoard));

        setContentView(binding.getRoot());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
