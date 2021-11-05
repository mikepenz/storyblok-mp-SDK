package com.mikepenz.storyblok.app

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.iconRes
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.util.addItems
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.storyblok.app.databinding.ActivityMainBinding
import com.mikepenz.storyblok.app.items.SimpleItem
import com.mikepenz.storyblok.app.viewmodel.SampleViewModel
import com.mikepenz.storyblok.sdk.model.Story
import com.mikepenz.storyblok.sdk.utils.readableCreatedAt
import java.text.DateFormat

class SampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var headerView: AccountHeaderView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    //save our FastAdapter
    private lateinit var fastAdapter: FastAdapter<SimpleItem>
    private lateinit var modelAdapter: ModelAdapter<Story, SimpleItem>

    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //create the activity
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        // Handle Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.root,
            binding.toolbar,
            com.mikepenz.materialdrawer.R.string.material_drawer_open,
            com.mikepenz.materialdrawer.R.string.material_drawer_close
        )
        binding.root.addDrawerListener(actionBarDrawerToggle)

        headerView = AccountHeaderView(this).apply {
            attachToSliderView(binding.slider)
            headerBackground = ImageHolder(R.drawable.header_background)
            withSavedInstance(savedInstanceState)
        }

        //Create the drawer
        binding.slider.apply {
            setSavedInstance(savedInstanceState)
            addItems(
                PrimaryDrawerItem().apply {
                    nameRes = R.string.storyblok
                    isSelectable = false
                    identifier = 10
                    iconRes = R.drawable.ic_storyblok
                    isIconTinted = true
                },
                PrimaryDrawerItem().apply {
                    nameRes = R.string.open_source
                    isSelectable = false
                    identifier = 100
                    iconicsIcon = MaterialDesignIconic.Icon.gmi_github
                }
            )
            selectedItemPosition = -1
            onDrawerItemClickListener = { _, drawerItem, _ ->
                if (drawerItem.identifier == 10L) {
                    startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("https://www.storyblok.com/") })
                } else if (drawerItem.identifier == 100L) {
                    val intent = LibsBuilder()
                        .withActivityTitle(getString(R.string.open_source))
                        .withAboutIconShown(true)
                        .withVersionShown(true)
                        .withLicenseShown(true)
                        .withAboutVersionShown(true)
                        .intent(this@SampleActivity)
                    this@SampleActivity.startActivity(intent)
                }
                false
            }
        }

        //create our FastAdapter which will manage everything
        modelAdapter = ModelAdapter {
            SimpleItem().withName("getStory: " + it.name).withDescription(
                it.readableCreatedAt(DateFormat.MEDIUM, DateFormat.SHORT)
            )
        }
        fastAdapter = FastAdapter.with(modelAdapter)

        //configure our fastAdapter
        //get our recyclerView and do basic setup
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = fastAdapter
        fastAdapter.withSavedInstanceState(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel.stories.observe(this, Observer {
            modelAdapter.set(it)
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onResume() {
        super.onResume()
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(state: Bundle) {
        var outState = state
        //add the values which need to be saved from the drawer to the bundle
        outState = binding.slider.saveInstanceState(outState)
        //add the values which need to be saved from the adapter to the bundel
        outState = fastAdapter.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (binding.root.isDrawerOpen(binding.slider)) {
            binding.root.closeDrawer(binding.slider)
        } else {
            super.onBackPressed()
        }
    }
}
