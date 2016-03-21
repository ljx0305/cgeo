package cgeo.geocaching;

import butterknife.ButterKnife;
import butterknife.Bind;

import cgeo.geocaching.activity.AbstractActionBarActivity;
import cgeo.geocaching.compatibility.Compatibility;
import cgeo.geocaching.ui.AbstractViewHolder;
import cgeo.geocaching.utils.ProcessUtils;

import org.eclipse.jdt.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UsefulAppsActivity extends AbstractActionBarActivity {

    @Bind(R.id.apps_list) protected ListView list;

    protected static class ViewHolder extends AbstractViewHolder {
        @Bind(R.id.title) protected TextView title;
        @Bind(R.id.image) protected ImageView image;
        @Bind(R.id.description) protected TextView description;

        public ViewHolder(final View rowView) {
            super(rowView);
        }
    }

    private static class HelperApp {
        private final int titleId;
        private final int descriptionId;
        private final int iconId;
        @NonNull
        private final String packageName;

        public HelperApp(final int title, final int description, final int icon, @NonNull final String packageName) {
            this.titleId = title;
            this.descriptionId = description;
            this.iconId = icon;
            this.packageName = packageName;
        }

    }

    private static final HelperApp[] HELPER_APPS = {
            new HelperApp(R.string.helper_calendar_title, R.string.helper_calendar_description, R.drawable.cgeo, "cgeo.calendar"),
            new HelperApp(R.string.helper_sendtocgeo_title, R.string.helper_sendtocgeo_description, R.drawable.cgeo, "http://send2.cgeo.org"),
            new HelperApp(R.string.helper_contacts_title, R.string.helper_contacts_description, R.drawable.cgeo, "cgeo.contacts"),
            new HelperApp(R.string.helper_wear_title, R.string.helper_wear_description, R.drawable.helper_wear, "com.javadog.cgeowear"),
            new HelperApp(R.string.helper_pocketquery_title, R.string.helper_pocketquery_description, R.drawable.helper_pocketquery, "org.pquery"),
            new HelperApp(R.string.helper_google_translate_title, R.string.helper_google_translate_description, R.drawable.helper_google_translate, "com.google.android.apps.translate"),
            new HelperApp(R.string.helper_where_you_go_title, R.string.helper_where_you_go_description, R.drawable.helper_where_you_go, "menion.android.whereyougo"),
            new HelperApp(R.string.helper_gpsstatus_title, R.string.helper_gpsstatus_description, R.drawable.helper_gpsstatus, "com.eclipsim.gpsstatus2"),
            new HelperApp(R.string.helper_bluetoothgps_title, R.string.helper_bluetoothgps_description, R.drawable.helper_bluetoothgps, "googoo.android.btgps"),
            new HelperApp(R.string.helper_barcode_title, R.string.helper_barcode_description, R.drawable.helper_barcode, "com.google.zxing.client.android"),
            new HelperApp(R.string.helper_locus_title, R.string.helper_locus_description, R.drawable.helper_locus, "menion.android.locus"),
    };

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.usefulapps_activity);

        ButterKnife.bind(this);

        list.setAdapter(new ArrayAdapter<HelperApp>(this, R.layout.usefulapps_item, HELPER_APPS) {
            @Override
            public View getView(final int position, final View convertView, final android.view.ViewGroup parent) {
                View rowView = convertView;
                if (rowView == null) {
                    rowView = getLayoutInflater().inflate(R.layout.usefulapps_item, parent, false);
                }
                ViewHolder holder = (ViewHolder) rowView.getTag();
                if (holder == null) {
                    holder = new ViewHolder(rowView);
                }

                final HelperApp app = getItem(position);
                fillViewHolder(holder, app);
                return rowView;
            }

            private void fillViewHolder(final ViewHolder holder, final HelperApp app) {
                holder.title.setText(res.getString(app.titleId));
                holder.image.setImageDrawable(Compatibility.getDrawable(res, app.iconId));
                holder.description.setText(Html.fromHtml(res.getString(app.descriptionId)));
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final HelperApp helperApp = HELPER_APPS[position];
                if (helperApp.packageName.startsWith("http")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(helperApp.packageName)));
                }
                else {
                    ProcessUtils.openMarket(UsefulAppsActivity.this, helperApp.packageName);
                }
            }
        });
    }
}
