package net.kdt.pojavlaunch.prefs.screens;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;

// Mojo'nun kendi kaynak paketini import ediyoruz
import git.artdeell.mojo.R;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class LauncherPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Arka plan rengini Mojo'nun kendi renk paletinden çek
        try {
            view.setBackgroundColor(getResources().getColor(R.color.background_app));
        } catch (Exception e) {
            // Renk bulunamazsa hata vermemesi için boş bırakıldı
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle b, String str) {
        // Mojo'nun ana ayar dosyasını yükle
        addPreferencesFromResource(R.xml.pref_main);
        
        // Mevcut olan tüm menülerin (Video, Java, Exper) kilitlerini aç
        if (getPreferenceScreen() != null) {
            unlockAll(getPreferenceScreen());
        }
    }

    private void unlockAll(PreferenceGroup group) {
        int count = group.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference p = group.getPreference(i);
            if (p != null) {
                p.setVisible(true); // Gizli her şeyi göster
                p.setEnabled(true); // Tıklanamayan her şeyi aç
                
                // Alt menü varsa (Video Ayarları gibi) içine sız
                if (p instanceof PreferenceGroup) {
                    unlockAll((PreferenceGroup) p);
                }
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
