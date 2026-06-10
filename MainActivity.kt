package com.example.campsidecomander

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var itemNames = ArrayList<String>() // Create four parallel arrays and populate with starter data
    var itemCategories = ArrayList<String>()
    var itemQuantities = ArrayList<Int>()
    var itemNotes = ArrayList<String>()

    // This will be dropdown menu options
    val categoryOptions = arrayOf(
        "Shelter", "Cooking", "First Aid",
        "Clothing", "Navigation", "Food", "Other"
    )

    // These match the XML palette so cards look consistent.
    val COLOR_CARD_BG  = Color.parseColor("#121F14")
    val COLOR_CARD_BORDER   = Color.parseColor("#2E4A32")
    val COLOR_ITEM_NAME = Color.parseColor("#E8913A")
    val COLOR_LABEL_TEXT = Color.parseColor("#A8C5A0")
    val COLOR_BODY_TEXT = Color.parseColor("#D4E8D0")
    val COLOR_NOTES_TEXT = Color.parseColor("#6B8F6E")
    val COLOR_BADGE_BG = Color.parseColor("#1C2B1E")
    val COLOR_ACCENT_ORANGE = Color.parseColor("#FF6D00")


    // SCREEN VARIABLES (they are not yet initialized)
    lateinit var screenSplash : View
    lateinit var screenMain : View
    lateinit var screenDetail : View
    lateinit var btnAddGear : Button
    lateinit var btnViewList : Button
    lateinit var tvTotalItems : TextView
    lateinit var btnBackToBase : Button
    lateinit var gearListContainer: LinearLayout


    // Any code inside the onCreate() function runs when the app starts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectViews() // called a function that links Kotlin variables to XML views by their ID
        loadSampleData() // called a function that pre-fills the four parallel arrays with starter items

        // Start on splash, auto-move to main after 3 seconds
        showSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            showMainScreen()
        }, 6000)

        setupButtons()
    }

    // Links Kotlin variables to XML views by their ID
    fun connectViews() {
        screenSplash = findViewById(R.id.screenSplash)
        screenMain = findViewById(R.id.screenMain)
        screenDetail = findViewById(R.id.screenDetailedView)

        btnAddGear = findViewById(R.id.btnAddGear)
        btnViewList = findViewById(R.id.btnViewDetail)
        tvTotalItems = findViewById(R.id.tvTotalItems)

        btnBackToBase = findViewById(R.id.btnBackToBase)
        gearListContainer = findViewById(R.id.llGearList)
    }

    // Pre-fills the four parallel arrays with starter items.
    // Index 0 is the example item given in the brief.
    fun loadSampleData() {
        // Item 0 — Items that are added to the pack,so initially there are this items
        itemNames.add("Tent")
        itemCategories.add("Shelter")
        itemQuantities.add(1)
        itemNotes.add("3-person, waterproof")

        // Item 1
        itemNames.add("Sleeping Bag")
        itemCategories.add("Shelter")
        itemQuantities.add(2)
        itemNotes.add("Good down to 0 degrees")

        // Item 2
        itemNames.add("Camp Stove")
        itemCategories.add("Cooking")
        itemQuantities.add(1)
        itemNotes.add("Bring extra gas canister")

        // Item 3
        itemNames.add("First Aid Kit")
        itemCategories.add("First Aid")
        itemQuantities.add(1)
        itemNotes.add("Check expiry dates")

        // Item 4
        itemNames.add("Canned Beans")
        itemCategories.add("Food")
        itemQuantities.add(4)
        itemNotes.add("Easy to cook on camp stove")
    }

    // Assigns a click action to every button in the app
    fun setupButtons() {
        btnAddGear.setOnClickListener { showAddGearForm() }
        btnViewList.setOnClickListener { showDetailScreen() }
        btnBackToBase.setOnClickListener { showMainScreen() }
    }


    // All three functions follow the same pattern:
    //   1. Hide every screen
    //   2. Show only the one we want
    fun showSplashScreen() {
        screenSplash.visibility = View.GONE
        screenMain.visibility   = View.GONE
        screenDetail.visibility = View.GONE
        screenSplash.visibility = View.VISIBLE //splash screen visible and other screens invisible
    }

    fun showMainScreen() {
        screenSplash.visibility = View.GONE
        screenMain.visibility   = View.GONE
        screenDetail.visibility = View.GONE
        screenMain.visibility   = View.VISIBLE //main screen visible and other screens invisible
        updateTotalCount() // call function to update the total count
    }

    fun showDetailScreen() {
        screenSplash.visibility = View.GONE
        screenMain.visibility   = View.GONE
        screenDetail.visibility = View.GONE
        screenDetail.visibility = View.VISIBLE // detail screen visible and other screens invisible
        buildGearList()// call function to build the gear list
    }

    // FUNCTION: updateTotalCount
    // Loops through itemQuantities and adds them all up
    fun updateTotalCount() {
        var total = 0
        for (i in 0 until itemQuantities.size) {
            total = total + itemQuantities[i]
        }
        tvTotalItems.text = "Total Items Packed: $total"
    }

    // FUNCTION: showAddGearForm
    // Opens a dialog pop-up for the user to type in new gear
    fun showAddGearForm() {
        val formLayout = LinearLayout(this)
        formLayout.orientation = LinearLayout.VERTICAL
        formLayout.setPadding(50, 30, 50, 10) //Set the spacing/padding on the left, top, right, and bottom of the form

        val inputName = EditText(this)
        inputName.hint = "Item name (e.g. Rope)"
        inputName.setTextColor(COLOR_LABEL_TEXT)
        inputName.setHintTextColor(COLOR_BODY_TEXT) //changes the colour of the hint text

        val labelCategory = TextView(this)
        labelCategory.text = "Category:"
        labelCategory.setTextColor(COLOR_LABEL_TEXT)
        labelCategory.setPadding(0, 16, 0, 4)

        val spinnerCategory = Spinner(this)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = spinnerAdapter

        val inputQuantity = EditText(this)
        inputQuantity.hint = "Quantity (e.g. 2)"
        inputQuantity.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        inputQuantity.setTextColor(COLOR_BODY_TEXT)
        inputQuantity.setHintTextColor(COLOR_NOTES_TEXT)

        val inputNotes = EditText(this)
        inputNotes.hint = "Notes (e.g. waterproof)"
        inputNotes.setTextColor(COLOR_BODY_TEXT)
        inputNotes.setHintTextColor(COLOR_NOTES_TEXT)

        formLayout.addView(inputName)
        formLayout.addView(labelCategory)
        formLayout.addView(spinnerCategory)
        formLayout.addView(inputQuantity)
        formLayout.addView(inputNotes)

        AlertDialog.Builder(this)
            .setTitle("Add New Gear")
            .setView(formLayout)
            .setPositiveButton("Add Item") { dialog, which ->
                val name     = inputName.text.toString()
                val category = spinnerCategory.selectedItem.toString()
                val notes    = inputNotes.text.toString()

                val quantityText = inputQuantity.text.toString()//convert quantity to an integer so that it can be added to the array
                val quantity: Int = if (quantityText == "") 1 else quantityText.toInt() // if  quantity is blank, default to 1

                if (name == "") { // Error handing, ensures that is the user ever tries to add an empty item name, the error message will appear`
                    Toast.makeText(this, "Please enter an item name!", Toast.LENGTH_SHORT).show() // Toast message
                } else {
                    // Add to all four parallel arrays at the same time
                    itemNames.add(name)
                    itemCategories.add(category)
                    itemQuantities.add(quantity)
                    itemNotes.add(notes)

                    updateTotalCount()// call function to update the total count
                    Toast.makeText(this, "$name added to your pack!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    // FUNCTION: buildGearList
    // Loops through all arrays and builds a styled card
    // for each item using the outdoor dark-mode palette.
    fun buildGearList() {

        // Clear any cards from a previous visit
        gearListContainer.removeAllViews()

        // If no items exist yet, show a friendly message
        if (itemNames.size == 0) { //if the array is empty
            val emptyMsg = TextView(this)
            emptyMsg.text = "🌿  No gear yet — go add some!"
            emptyMsg.textSize = 15f //set the text size
            emptyMsg.setTextColor(COLOR_NOTES_TEXT)
            emptyMsg.setPadding(0, 32, 0, 0)
            gearListContainer.addView(emptyMsg)
            return
        }

        // ── LOOP: build one card per item ────────────────
        for (i in 0 until itemNames.size) { //loop through all the arrays to build a card for each item

            // ── Outer card container ──────────────────────
            val card = LinearLayout(this) // create a card variable that will be a LinearLayout and give it all the Necessary properties
            card.orientation = LinearLayout.VERTICAL
            card.setBackgroundColor(COLOR_CARD_BG)

            val cardParams = LinearLayout.LayoutParams( //  create a card layout parameters variable that will be a LinearLayout and give it all the Necessary properties
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            cardParams.setMargins(0, 0, 0, 14)   // gap between cards
            card.layoutParams = cardParams

            // ── Top accent stripe (forest green, 3dp tall) ─
            val topStripe = View(this)
            topStripe.setBackgroundColor(COLOR_CARD_BORDER)
            topStripe.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 3
            )

            // ── Card inner padding container ──────────────
            val innerPad = LinearLayout(this)
            innerPad.orientation = LinearLayout.VERTICAL
            innerPad.setPadding(22, 16, 22, 18)

            // ── Row 1: item number + name ──────────────────
            val textName = TextView(this)
            textName.text = (i + 1).toString() + ".   " + itemNames[i]
            textName.textSize = 18f
            textName.setTypeface(null, android.graphics.Typeface.BOLD)
            textName.setTextColor(COLOR_ITEM_NAME)

            // ── Thin horizontal divider ────────────────────
            val divider = View(this)
            divider.setBackgroundColor(COLOR_CARD_BORDER)
            val divParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1
            )
            divParams.setMargins(0, 10, 0, 10)
            divider.layoutParams = divParams

            // ── Row 2: category badge ──────────────────────
            val textCategory = TextView(this)
            textCategory.text = "📁  " + itemCategories[i]
            textCategory.textSize = 13f
            textCategory.setTextColor(COLOR_LABEL_TEXT)
            textCategory.setBackgroundColor(COLOR_BADGE_BG)
            textCategory.setPadding(12, 6, 12, 6)

            val badgeParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            badgeParams.setMargins(0, 0, 0, 8)
            textCategory.layoutParams = badgeParams

            // ── Row 3: quantity ────────────────────────────
            val textQuantity = TextView(this)
            textQuantity.text = "📦  Quantity:   " + itemQuantities[i].toString()
            textQuantity.textSize = 14f
            textQuantity.setTextColor(COLOR_BODY_TEXT)
            textQuantity.setPadding(0, 4, 0, 4)

            // ── Row 4: notes ───────────────────────────────
            val notesValue = if (itemNotes[i] == "") "None" else itemNotes[i]
            val textNotes = TextView(this)
            textNotes.text = "📝  Notes:   $notesValue"
            textNotes.textSize = 13f
            textNotes.setTextColor(COLOR_NOTES_TEXT)
            textNotes.setPadding(0, 4, 0, 0)

            // ── Assemble the card ──────────────────────────
            innerPad.addView(textName) // add the rows to the card
            innerPad.addView(divider)
            innerPad.addView(textCategory)
            innerPad.addView(textQuantity)
            innerPad.addView(textNotes)

            card.addView(topStripe)
            card.addView(innerPad)

            // ── Add finished card to the screen ───────────
            gearListContainer.addView(card)
        }
    }

}