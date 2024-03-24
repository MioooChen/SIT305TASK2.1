package com.example.myapplicationtask21

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplicationtask21.ui.theme.MyApplicationTask21Theme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem


// Entry point of the application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTask21Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}
// Composable function for the unit converter UI
@Composable
fun UnitConverter() {
    // State management for input text, source and destination units, and result text
    var inputText by remember { mutableStateOf("") }
    var selectedSourceUnit by remember { mutableStateOf("inch") }
    var selectedDestinationUnit by remember { mutableStateOf("cm") }
    var resultText by remember { mutableStateOf("") }
    // List of units available for conversion
    val units = listOf("inch", "cm", "foot", "yard", "mile", "pound", "kg", "ounce", "ton", "Celsius", "Fahrenheit", "Kelvin")

    // Column layout for organizing UI elements vertically
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Value to convert") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        // Display and select source unit
        Text("Source unit: $selectedSourceUnit")
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            units.forEach { unit ->
                Button(
                    onClick = { selectedSourceUnit = unit },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(unit)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        // Display and select destination unit
        Text("Destination unit: $selectedDestinationUnit")
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            units.forEach { unit ->
                Button(
                    onClick = { selectedDestinationUnit = unit },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(unit)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to initiate conversion
        Button(
            onClick = {
                resultText = convertUnits(
                    value = inputText.toDoubleOrNull() ?: 0.0,
                    sourceUnit = selectedSourceUnit,
                    destinationUnit = selectedDestinationUnit
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display the result of conversion
        Text("Result: $resultText")
    }
}

// Function to convert units based on given source and destination units
fun convertUnits(value: Double, sourceUnit: String, destinationUnit: String): String {
    val convertedValue = when (sourceUnit to destinationUnit) {
        // Length Conversions
        "inch" to "cm" -> value * 2.54
        "foot" to "cm" -> value * 30.48
        "yard" to "cm" -> value * 91.44
        "mile" to "km" -> value * 1.60934
        // Weight Conversions
        "pound" to "kg" -> value * 0.453592
        "ounce" to "g" -> value * 28.3495
        "ton" to "kg" -> value * 907.185
        // Temperature Conversions
        "Celsius" to "Fahrenheit" -> (value * 1.8) + 32
        "Fahrenheit" to "Celsius" -> (value - 32) / 1.8
        "Celsius" to "Kelvin" -> value + 273.15
        "Kelvin" to "Celsius" -> value - 273.15
        else -> null
    }
    // Returning the conversion result or a message if conversion is not supported
    return if (convertedValue != null) {
        "$value $sourceUnit is equivalent to $convertedValue $destinationUnit"
    } else {
        "Conversion from $sourceUnit to $destinationUnit is not supported."
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTask21Theme {
        UnitConverter()
    }
}