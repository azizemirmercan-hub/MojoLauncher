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
 * MojoLauncher'ın tüm gizli menülerini ve Amethyst benzeri teknik ayarlarını
 * ortaya çıkaran Full Unlocker.
 */
public class LauncherPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Uygulamanın arka plan rengini koru
        view.setBackgroundColor(getResources().getColor(R.color.background_app));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle b, String str) {
        // 1. Ana menü dosyasını yükle
        addPreferencesFromResource(R.xml.pref_main);
        
        // 2. Varsa diğer teknik XML'leri de zorla ekle (Mojo'da gizlenmiş olabilirler)
        try {
            addPreferencesFromResource(R.xml.pref_video);
            addPreferencesFromResource(R.xml.pref_java);
            addPreferencesFromResource(R.xml.pref_experimental);
            addPreferencesFromResource(R.xml.pref_misc);
        } catch (Exception e) {
            // Eğer dosya ismi farklıysa veya yoksa çökmesini engeller
        }

        // 3. KRİTİK: Tüm kısıtlamaları ve gizlilik ayarlarını kaldır
        forceUnlockPreferences(getPreferenceScreen());
    }

    /**
     * Bu metod, Moonlight OS mantığıyla tüm alt düğümlere sızar 
     * ve setVisible(false) olan her şeyi 'true' yapar.
     */
    private void forceUnlockPreferences(PreferenceGroup group) {
        if (group == null) return;

        for (int i = 0; i < group.getPreferenceCount(); i++) {
            Preference p = group.getPreference(i);
            
            // Gizli menüleri ve tıklanamayan butonları aç
            p.setVisible(true);
            p.setEnabled(true);

            // Eğer bu bir kategori veya alt menü ise içine gir
            if (p instanceof PreferenceGroup) {
                forceUnlockPreferences((PreferenceGroup) p);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Ayarların her seferinde güncel yüklendiğinden emin ol
        LauncherPreferences.loadPreferences(getContext());
    }
}
