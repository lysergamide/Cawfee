package pw.tewi.cawfee.ui;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import dagger.hilt.android.AndroidEntryPoint;
import pw.tewi.cawfee.databinding.ActivityMainBinding;

/**
 * Entry point for Cawfee
 */

@AndroidEntryPoint
public final class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(ActivityMainBinding.inflate(getLayoutInflater())
                                          .getRoot());

    }
}
