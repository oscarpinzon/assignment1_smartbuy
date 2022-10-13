package www.smartbuy.com;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Array of countries
    static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    // Current computer type
    enum ComputerType {
        Desktop,
        Laptop,
    }

    ComputerType currentComputerType;

    // Reference to Widgets
    AutoCompleteTextView countryTextView;
    RadioGroup computerTypeRadioGroup;
    RadioButton rdDesktop, rdLaptop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Widgets
        countryTextView = findViewById(R.id.customerCountry);
        computerTypeRadioGroup = findViewById(R.id.computerTypeRadioGroup);
        rdDesktop = findViewById(R.id.desktopRadioButton);
        rdLaptop = findViewById(R.id.laptopRadioButton);

        // Create an adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        countryTextView.setAdapter(adapter);

        // Attach event listener to RadioGroup
        computerTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            // set current computer type
            if (radioButton == rdDesktop) {
                currentComputerType = ComputerType.Desktop;
            } else if (radioButton == rdLaptop) {
                currentComputerType = ComputerType.Laptop;
            }
        });
    }
}