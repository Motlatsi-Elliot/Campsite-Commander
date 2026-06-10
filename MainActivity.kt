/*package com.example.campsidecomander

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}*/


package com.example.campsidecomander

// These are the "tools" we need from Android - think of them as imports from a toolbox
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

// This is our one and only Activity (screen manager)
class MainActivity : AppCompatActivity() {

    // ====================================================
    // STEP 1: PARALLEL ARRAYS
    // These 4 arrays work together. Index 0 in each array
    // belongs to the same item. Like columns in a table.
    //
    // Example (sample data pre-loaded below):
    //   itemNames[0]      = "Tent"
    //   itemCategories[0] = "Shelter"
    //   itemQuantities[0] = 1
    //   itemNotes[0]      = "3-person, waterproof"
    // ====================================================

    // Array 1: stores the name of each gear item
    var itemNames = ArrayList<String>()

    // Array 2: stores the category of each gear item
    var itemCategories = ArrayList<String>()

    // Array 3: stores the quantity of each gear item
    var itemQuantities = ArrayList<Int>()

    // Array 4: stores extra notes for each gear item
    var itemNotes = ArrayList<String>()

    // The category choices shown in the dropdown
    val categoryOptions = arrayOf("Shelter", "Cooking", "First Aid", "Clothing", "Navigation", "Food", "Other")

    // ====================================================
    // STEP 2: SCREEN VARIABLES
    // Each variable below holds one of our three screens.
    // We show/hide them to navigate between screens.
    // ====================================================

    // The three screens (layouts)
    lateinit var screenSplash: View
    lateinit var screenMain: View
    lateinit var screenDetail: View

    // Buttons and text on the Main Screen
    lateinit var btnAddGear: Button
    lateinit var btnViewList: Button
    lateinit var tvTotalItems: TextView

    // Button on the Detail Screen
    lateinit var btnBackToBase: Button

    // The container where we will add gear cards on the Detail Screen
    lateinit var gearListContainer: LinearLayout

    // ====================================================
    // STEP 3: onCreate - This runs when the app first opens
    // ====================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load our XML layout file
        setContentView(R.layout.activity_main)

        // Connect our variables to the views in the XML
        connectViews()

        // Fill arrays with sample data so the app is not empty on first launch
        loadSampleData()

        // Show the splash screen first
        showSplashScreen()

        // After 3 seconds, automatically go to the main screen
        Handler(Looper.getMainLooper()).postDelayed({
            showMainScreen()
        }, 6000)

        // Set up what happens when each button is clicked
        setupButtons()
    }

    // ====================================================
    // FUNCTION: connectViews
    // Links our Kotlin variables to the XML views by their ID
    // ====================================================
    fun connectViews() {
        screenSplash = findViewById(R.id.screenSplash)
        screenMain = findViewById(R.id.screenMain)
        screenDetail  = findViewById(R.id.screenDetailedView)

        btnAddGear = findViewById(R.id.btnAddGear)
        btnViewList = findViewById(R.id.btnViewDetail)
        tvTotalItems = findViewById(R.id.tvTotalItems)

        btnBackToBase = findViewById(R.id.btnBackToBase)
        gearListContainer = findViewById(R.id.llGearList)
    }

    // ====================================================
    // FUNCTION: loadSampleData
    // Pre-fills the parallel arrays with example items
    // so the app already has data when it opens
    // ====================================================
    fun loadSampleData() {
        // Sample item 1 - the example given to us
        itemNames.add("Tent")
        itemCategories.add("Shelter")
        itemQuantities.add(1)
        itemNotes.add("3-person, waterproof")

        // Sample item 2
        itemNames.add("Sleeping Bag")
        itemCategories.add("Shelter")
        itemQuantities.add(2)
        itemNotes.add("Good down to 0 degrees")

        // Sample item 3
        itemNames.add("Camp Stove")
        itemCategories.add("Cooking")
        itemQuantities.add(1)
        itemNotes.add("Bring extra gas canister")

        // Sample item 4
        itemNames.add("First Aid Kit")
        itemCategories.add("First Aid")
        itemQuantities.add(1)
        itemNotes.add("Check expiry dates before trip")

        // Sample item 5
        itemNames.add("Flashlight")
        itemCategories.add("safety")
        itemQuantities.add(4)
        itemNotes.add("check batteries")
    }

    // ====================================================
    // FUNCTION: setupButtons
    // Tells each button what to do when it is tapped
    // ====================================================
    fun setupButtons() {

        // When "Add Gear" is tapped, open the add-item form
        btnAddGear.setOnClickListener {
            showAddGearForm()
        }

        // When "View Full Gear List" is tapped, go to detail screen
        btnViewList.setOnClickListener {
            showDetailScreen()
        }

        // When "Back to Base" is tapped, go back to main screen
        btnBackToBase.setOnClickListener {
            showMainScreen()
        }
    }

    // ====================================================
    // SCREEN NAVIGATION FUNCTIONS
    // We hide all screens first, then show only the one we want.
    // This is the "visibility" technique - no new Activities needed.
    // ====================================================

    fun showSplashScreen() {
        // Hide everything
        screenSplash.visibility = View.GONE
        screenMain.visibility   = View.GONE
        screenDetail.visibility = View.GONE

        // Show only splash
        screenSplash.visibility = View.VISIBLE
    }

    fun showMainScreen() {
        // Hide everything
        screenSplash.visibility = View.GONE
        screenMain.visibility   = View.GONE
        screenDetail.visibility = View.GONE

        // Show only main
        screenMain.visibility = View.VISIBLE

        // Update the total items count every time we come back here
        updateTotalCount()
    }

    fun showDetailScreen() {
        // Hide everything
        screenSplash.visibility = View.GONE
        screenMain.visibility   = View.GONE
        screenDetail.visibility = View.GONE

        // Show only detail
        screenDetail.visibility = View.VISIBLE

        // Build the gear cards list
        buildGearList()
    }

    // ====================================================
    // FUNCTION: updateTotalCount
    // Uses a LOOP to add up all quantities in the array
    // and display the total on the main screen
    // ====================================================
    fun updateTotalCount() {
        // Start at zero
        var total = 0

        // Loop through every item in the quantities array
        for (i in 0 until itemQuantities.size) {
            total = total + itemQuantities[i]
        }

        // Show the result on screen
        tvTotalItems.text = "Total Items Packed: $total"
    }

    // ====================================================
    // FUNCTION: showAddGearForm
    // Opens a pop-up dialog where the user types in new gear
    // ====================================================
    fun showAddGearForm() {

        // Create a vertical layout to hold all the input fields
        val formLayout = LinearLayout(this)
        formLayout.orientation = LinearLayout.VERTICAL
        formLayout.setPadding(50, 30, 50, 10)

        // Input field for the item name
        val inputName = EditText(this)
        inputName.hint = "Item name (e.g. Tent)"

        // Label above the category dropdown
        val labelCategory = TextView(this)
        labelCategory.text = "Select Category:"
        labelCategory.setPadding(0, 16, 0, 4)

        // Dropdown spinner for category
        val spinnerCategory = Spinner(this)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = spinnerAdapter

        // Input field for quantity
        val inputQuantity = EditText(this)
        inputQuantity.hint = "Quantity (e.g. 2)"
        inputQuantity.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        // Input field for notes
        val inputNotes = EditText(this)
        inputNotes.hint = "Notes (e.g. waterproof)"

        // Add all fields into the form layout
        formLayout.addView(inputName)
        formLayout.addView(labelCategory)
        formLayout.addView(spinnerCategory)
        formLayout.addView(inputQuantity)
        formLayout.addView(inputNotes)

        // Build and show the dialog pop-up
        AlertDialog.Builder(this)
            .setTitle("Add New Gear")
            .setView(formLayout)
            .setPositiveButton("Add Item") { dialog, which ->
                // This runs when the user taps "Add Item"

                // Read what the user typed
                val name     = inputName.text.toString()
                val category = spinnerCategory.selectedItem.toString()
                val notes    = inputNotes.text.toString()

                // If quantity was left blank, use 1 as default
                val quantityText = inputQuantity.text.toString()
                val quantity: Int
                if (quantityText == "") {
                    quantity = 1
                } else {
                    quantity = quantityText.toInt()
                }

                // Make sure the user actually typed a name
                if (name == "") {
                    Toast.makeText(this, "Please enter an item name!", Toast.LENGTH_SHORT).show()
                } else {
                    // Save to all four parallel arrays at the same index
                    itemNames.add(name)
                    itemCategories.add(category)
                    itemQuantities.add(quantity)
                    itemNotes.add(notes)

                    // Refresh the total count shown on screen
                    updateTotalCount()

                    // Tell the user it worked
                    Toast.makeText(this, name + " added to your pack!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Do nothing, just close the dialog
                dialog.dismiss()
            }
            .show()
    }

    // ====================================================
    // FUNCTION: buildGearList
    // Uses a LOOP to go through all arrays and build
    // a card on screen for every item
    // ====================================================
    fun buildGearList() {

        // Remove any old cards from a previous visit
        gearListContainer.removeAllViews()

        // If there is nothing in the list, show a message
        if (itemNames.size == 0) {
            val emptyMessage = TextView(this)
            emptyMessage.text = "No gear added yet. Go back and add some!"
            emptyMessage.textSize = 16f
            gearListContainer.addView(emptyMessage)
            return
        }

        // LOOP through every item using its index number
        for (i in 0 until itemNames.size) {

            // Create a small vertical box (card) for this item
            val card = LinearLayout(this)
            card.orientation = LinearLayout.VERTICAL
            card.setPadding(24, 20, 24, 20)
            card.setBackgroundResource(android.R.drawable.dialog_holo_dark_frame)

            // Give the card a bottom margin so cards don't touch
            val cardParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            cardParams.setMargins(0, 0, 0, 20)
            card.layoutParams = cardParams

            // Line 1: Item number and name
            val textName = TextView(this)
            textName.text = (i + 1).toString() + ".  " + itemNames[i]
            textName.textSize = 18f
            textName.setTextColor(resources.getColor(android.R.color.holo_orange_light, theme))

            // Line 2: Category
            val textCategory = TextView(this)
            textCategory.text = "Category:  " + itemCategories[i]
            textCategory.textSize = 14f
            textCategory.setTextColor(resources.getColor(android.R.color.white, theme))

            // Line 3: Quantity
            val textQuantity = TextView(this)
            textQuantity.text = "Quantity:  " + itemQuantities[i].toString()
            textQuantity.textSize = 14f
            textQuantity.setTextColor(resources.getColor(android.R.color.white, theme))

            // Line 4: Notes
            val textNotes = TextView(this)
            if (itemNotes[i] == "") {
                textNotes.text = "Notes:  None"
            } else {
                textNotes.text = "Notes:  " + itemNotes[i]
            }
            textNotes.textSize = 14f
            textNotes.setTextColor(resources.getColor(android.R.color.darker_gray, theme))

            // Add all four lines into the card
            card.addView(textName)
            card.addView(textCategory)
            card.addView(textQuantity)
            card.addView(textNotes)

            // Add the finished card into the scrollable list on screen
            gearListContainer.addView(card)
        }
    }

}