package www.smartbuy.com;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Available countries
    static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Canada"
    };

    // Computer types
    enum ComputerType {
        Desktop,
        Laptop
    }

    // Current computer type to track selected state
    ComputerType currentComputerType;

    // Available brands
    static final String[] BRANDS = new String[]{
            "Dell", "HP", "Lenovo"
    };

    String addedPeripherals = "";

    // Reference to Widgets
    EditText nameEditText, emailEditText;
    AutoCompleteTextView countryTextView;
    RadioGroup computerTypeRadioGroup, laptopPeripheralsRadioGroup, desktopPeripheralsRadioGroup;
    RadioButton rdDesktop, rdLaptop, rdLaptopCoolingMat, rdUsbHub, rdlaptopStand, rdNone, rdWebcam, rdExternalHardDrive;
    Spinner brandSpinner;
    CheckBox chkSSD, chkPrinter;
    TextView peripheralsPrompt, invoiceTextView;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Widgets
        nameEditText = findViewById(R.id.customerName);
        emailEditText = findViewById(R.id.customerEmail);
        countryTextView = findViewById(R.id.customerCountry);
        computerTypeRadioGroup = findViewById(R.id.computerTypeRadioGroup);
        rdDesktop = findViewById(R.id.desktopRadioButton);
        rdLaptop = findViewById(R.id.laptopRadioButton);
        brandSpinner = findViewById(R.id.brandSpinner);
        chkSSD = findViewById(R.id.checkbox_ssd);
        chkPrinter = findViewById(R.id.checkbox_printer);
        laptopPeripheralsRadioGroup = findViewById(R.id.laptopPeripheralsRadioGroup);
        desktopPeripheralsRadioGroup = findViewById(R.id.desktopPeripheralsRadioGroup);
        rdLaptopCoolingMat = findViewById(R.id.laptopCoolingMatRadioButton);
        rdUsbHub = findViewById(R.id.usbHubRadioButton);
        rdlaptopStand = findViewById(R.id.laptopStandRadioButton);
        rdNone = findViewById(R.id.noneRadioButton);
        rdWebcam = findViewById(R.id.webcamRadioButton);
        rdExternalHardDrive = findViewById(R.id.externalHardDriveRadioButton);
        peripheralsPrompt = findViewById(R.id.peripheralsTextView);
        btnSubmit = findViewById(R.id.submitButton);
        invoiceTextView = findViewById(R.id.invoiceTextView);

        // Create an adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        countryTextView.setAdapter(adapter);

        // hide peripherals radio group
        peripheralsPrompt.setVisibility(android.view.View.GONE);
        laptopPeripheralsRadioGroup.setVisibility(RadioGroup.GONE);
        desktopPeripheralsRadioGroup.setVisibility(RadioGroup.GONE);

        // Attach event listener to computer type radio group
        computerTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            // set current computer type
            if (radioButton == rdDesktop) {
                currentComputerType = ComputerType.Desktop;
                peripheralsPrompt.setVisibility(android.view.View.VISIBLE);
                desktopPeripheralsRadioGroup.setVisibility(RadioGroup.VISIBLE);
                laptopPeripheralsRadioGroup.setVisibility(RadioGroup.GONE);
            } else if (radioButton == rdLaptop) {
                currentComputerType = ComputerType.Laptop;
                peripheralsPrompt.setVisibility(android.view.View.VISIBLE);
                laptopPeripheralsRadioGroup.setVisibility(RadioGroup.VISIBLE);
                desktopPeripheralsRadioGroup.setVisibility(RadioGroup.GONE);
            }
        });

        // Create an ArrayAdapter using the string array
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BRANDS);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        brandSpinner.setAdapter(spinnerAdapter);

        // Attach event listener to desktop peripherals radio group
        desktopPeripheralsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            // set current computer type
            if (radioButton == rdWebcam) {
                addedPeripherals = "Webcam";
            } else if (radioButton == rdExternalHardDrive) {
                addedPeripherals = "External Hard Drive";
            } else if (radioButton == rdNone) {
                addedPeripherals = "None";
            }
        });

        // Attach event listener to laptop peripherals radio group
        laptopPeripheralsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            // set current computer type
            if (radioButton == rdLaptopCoolingMat) {
                addedPeripherals = "Cooling Mat";
            } else if (radioButton == rdUsbHub) {
                addedPeripherals = "USB C-HUB";
            } else if (radioButton == rdlaptopStand) {
                addedPeripherals = "Laptop Stand";
            }
        });

        // Attach event listener to submit button
        btnSubmit.setOnClickListener(v -> {
            if (currentComputerType == null) {
                // print error message
                Toast.makeText(this, "Please select a computer type", Toast.LENGTH_SHORT).show();
                return;
            }

            String invoice = "";
            invoice += "Customer: " + nameEditText.getText().toString() + "\n";
            invoice += "E-mail: " + emailEditText.getText().toString() + "\n";
            invoice += "Country: " + countryTextView.getText().toString() + "\n";
            invoice += "Computer: " + currentComputerType.toString() + "\n";
            invoice += "Brand: " + brandSpinner.getSelectedItem().toString() + "\n";
            if (chkSSD.isChecked() && chkPrinter.isChecked()) {
                invoice += "Additional: SSD, Printer\n";
            } else if (chkSSD.isChecked()) {
                invoice += "Additional: SSD\n";
            } else if (chkPrinter.isChecked()) {
                invoice += "Additional: Printer\n";
            } else {
                invoice += "Additional: None\n";
            }
            invoice += "Added Peripherals: " + addedPeripherals + "\n";
            invoice += "Cost: $" + calculateCost() + "\n";
            invoiceTextView.setText(invoice);
        });
    }

    private String calculateCost() {
        double cost = 0;
        if (currentComputerType == ComputerType.Desktop) {
            if(brandSpinner.getSelectedItem().toString().equals("Dell")) {
                cost += 575;
            } else if(brandSpinner.getSelectedItem().toString().equals("HP")) {
                cost += 450;
            } else if(brandSpinner.getSelectedItem().toString().equals("Lenovo")) {
                cost += 500;
            }
        } else if (currentComputerType == ComputerType.Laptop) {
            if(brandSpinner.getSelectedItem().toString().equals("Dell")) {
                cost += 1359;
            } else if(brandSpinner.getSelectedItem().toString().equals("HP")) {
                cost += 1320;
            } else if(brandSpinner.getSelectedItem().toString().equals("Lenovo")) {
                cost += 1689;
            }
        }
        if (chkSSD.isChecked()) {
            cost += 80;
        }
        if (chkPrinter.isChecked()) {
            cost += 120;
        }
        switch (addedPeripherals) {
            case "Webcam":
                cost += 98;
                break;
            case "External Hard Drive":
                cost += 72;
                break;
            case "Cooling Mat":
                cost += 44;
                break;
            case "USB C-HUB":
                cost += 67;
                break;
            case "Laptop Stand":
                cost += 141;
                break;
        }
        cost *= 1.13;
        return String.format(Locale.CANADA,"%.2f", cost);
    }
}