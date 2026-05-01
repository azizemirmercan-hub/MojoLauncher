package net.kdt.pojavlaunch.prefs.screens;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import git.artdeell.mojo.R;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class LauncherPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.setBackgroundColor(getResources().getColor(R.color.background_app));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle b, String str) {
        // Mojo'nun ana ayar dosyasını yükle
        addPreferencesFromResource(R.xml.pref_main);
        
        // Diğer dosyalar yoksa derleme hatası almamak için 
        // manuel kontrol yerine sadece ana ekran üzerinden kilit açıyoruz
        unlockEverything(getPreferenceScreen());
    }

    private void unlockEverything(PreferenceGroup group) {
        if (group == null) return;
        for (int i = 0; i < group.getPreferenceCount(); i++) {
            Preference p = group.getPreference(i);
            
            p.setVisible(true);
            p.setEnabled(true);

            if (p instanceof PreferenceGroup) {
                unlockEverything((PreferenceGroup) p);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() != null) {
            LauncherPreferences.loadPreferences(getContext());
        }
    }
}
