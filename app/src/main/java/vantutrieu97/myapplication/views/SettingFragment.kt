package vantutrieu97.myapplication.views

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import vantutrieu97.myapplication.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
