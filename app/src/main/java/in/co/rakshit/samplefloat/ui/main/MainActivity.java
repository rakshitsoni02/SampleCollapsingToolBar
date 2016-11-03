package in.co.rakshit.samplefloat.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.rakshit.samplefloat.R;
import in.co.rakshit.samplefloat.data.model.Base;
import in.co.rakshit.samplefloat.data.model.Business;
import in.co.rakshit.samplefloat.ui.base.BaseActivity;
import in.co.rakshit.samplefloat.util.DialogFactory;

public class MainActivity extends BaseActivity implements MainMvpView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "in.co.rakshit.samplefloat.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";
    @Inject
    MainPresenter mMainPresenter;
    @Inject
    BusinessAdapter mBusinessAdapter;
    @Inject
    CategoriesAdapter mCategoriesAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recycler_view_horizontal)
    RecyclerView mRecyclerViewCategories;
    @BindView(R.id.root_content_view)
    LinearLayout linearLayout;
    @BindView(R.id.progressBar_layout)
    ProgressBar progressBar;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private LocationRequest mLocationRequest = null;
    private LocationManager mLocationManager = null;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initGoogleClient();
        mRecyclerView.setAdapter(mBusinessAdapter);
        mRecyclerViewCategories.setAdapter(mCategoriesAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerViewCategories.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCategories.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));
        mMainPresenter.attachView(this);

    }

    private void initGoogleClient() {
        mLocationRequest = createLocationRequest();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    /*****
     * MVP View methods implementation
     *****/
    @Override
    public void showRibots(Base ribots) {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        mCategoriesAdapter.setBusiness(ribots.getData().getBusinesses(), this);
        mCategoriesAdapter.notifyDataSetChanged();
        mBusinessAdapter.setBusiness(ribots.getData().getBusinesses(), this, mLastLocation);
        mBusinessAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_data))
                .show();
    }

    @Override
    public void showRibotsEmpty() {
        progressBar.setVisibility(View.GONE);
        mBusinessAdapter.setBusiness(Collections.<Business>emptyList(), this, mLastLocation);
        mBusinessAdapter.notifyDataSetChanged();
        mCategoriesAdapter.setBusiness(Collections.<Business>emptyList(), this);
        mCategoriesAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_business, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        Boolean mPermissions = hasPermissions(this, PERMISSIONS);
        int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
        if (!mPermissions) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }

        initLastLocation();


    }

    private void initLastLocation() {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        mMainPresenter.loadBusiness();
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(7000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(1);
        return mLocationRequest;
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (mLastLocation == null) {
            DialogFactory.createGenericErrorDialog(this, "Failed to get location")
                    .show();
        }

        mMainPresenter.loadBusiness();

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }
}
