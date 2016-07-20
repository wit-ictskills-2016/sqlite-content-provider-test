package ictskills.contentprovidertest.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import ictskills.contentprovidertest.R;
import ictskills.contentprovidertest.models.Residence;
import ictskills.contentprovidertest.providers.ResidenceContract;

import android.widget.Toast;

/**
 * Set a button click handler.
 * Handler obtains list Residence records from MyRentSqlite
 * Precondition: MyRentSqlite database must be present.
 * Install and run MyRentSqlite and add some Residence records to populate the database.
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
  private Button contentProvider;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    contentProvider = (Button)findViewById(R.id.contentProvider);
    contentProvider.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.contentProvider:
        selectResidences();
    }
  }

  /**
   * This code derived from MyRentSQLite.RefreshResidenceService.selectResidences.
   */
  private void selectResidences() {

    // Query the database
    List<Residence> residences = new ArrayList<Residence>();
    Cursor cursor = getContentResolver().query(ResidenceContract.CONTENT_URI, null, null, null, null);

    if (cursor.moveToFirst()) {
      int columnIndex = 1; // skip column 0, the _id
      do {
        Residence residence = new Residence();

        residence.uuid = UUID.fromString(cursor.getString(columnIndex++));
        residence.geolocation = cursor.getString(columnIndex++);
        residence.date = new Date(Long.parseLong(cursor.getString(columnIndex++)));
        residence.rented = cursor.getString(columnIndex++) == "yes" ? true : false;
        residence.tenant = cursor.getString(columnIndex++);
        residence.zoom = Double.parseDouble(cursor.getString(columnIndex++));
        residence.photo = cursor.getString(columnIndex++);

        columnIndex = 1;

        residences.add(residence);
      } while (cursor.moveToNext());
    }
    cursor.close();
    Toast.makeText(this, "Retrieved MyRentSQLite Residence array, size: " + residences.size(), Toast.LENGTH_SHORT).show();
  }
}
