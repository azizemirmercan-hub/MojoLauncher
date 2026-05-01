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

/**
 * Moonlight OS mantığıyla tüm GUI kısıtlamalarını kaldıran Full Mode Fragment.
 */
public class LauncherPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Arka plan rengini ayarla
        view.setBackgroundColor(getResources().getColor(R.color.background_app));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle b, String str) {
        // Ana ayar dosyasını yükle
        addPreferencesFromResource(R.xml.pref_main);
        
        // KRİTİK: Tüm kilitleri açan metod
        unlockEverything(getPreferenceScreen());
    }

    /**
     * Bu metod, XML içinde gizlenmiş (visible=false) veya 
     * tıklanamaz (enabled=false) yapılmış her şeyi "ZORLA" aktif eder.
     */
    private void unlockEverything(PreferenceGroup group) {
        for (int i = 0; i < group.getPreferenceCount(); i++) {
            Preference p = group.getPreference(i);
            
            // Ayarı görünür ve tıklanabilir yap
            p.setVisible(true);
            p.setEnabled(true);

            // Eğer bu bir alt menü (kategori) ise, onun içine de gir ve her şeyi aç
            if (p instanceof PreferenceGroup) {
                unlockEverything((PreferenceGroup) p);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tercihleri her seferinde yeniden yükle
        LauncherPreferences.loadPreferences(getContext());
    }
}
