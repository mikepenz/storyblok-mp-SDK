package com.mikepenz.storyblok.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.storyblok.app.items.SimpleItem
import com.mikepenz.storyblok.app.viewmodel.SampleViewModel
import com.mikepenz.storyblok.sdk.model.Story
import kotlinx.android.synthetic.main.activity_main.*

class SampleActivity : AppCompatActivity() {

    //save our header or result
    private lateinit var result: Drawer
    //save our FastAdapter
    private lateinit var fastAdapter: FastAdapter<SimpleItem>
    private lateinit var modelAdapter: ModelAdapter<Story, SimpleItem>

    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //create the activity
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Handle Toolbar
        setSupportActionBar(toolbar)

        val header = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_background)
                .build()

        //Create the drawer
        result = DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .withHasStableIds(true)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.storyblok).withSelectable(false).withIdentifier(10).withIcon(AppCompatResources.getDrawable(this, R.drawable.ico_storyblok)).withIconTintingEnabled(true)
                        //PrimaryDrawerItem().withName(R.string.open_source).withSelectable(false).withIdentifier(100).withIcon(MaterialDesignIconic.Icon.gmi_github)
                )
                .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                        if (drawerItem?.identifier == 10L) {
                            startActivity(Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("https://www.storyblok.com/") })
                        } else if (drawerItem?.identifier == 100L) {
                            val intent = LibsBuilder()
                                    .withFields(R.string::class.java.fields)
                                    .withActivityTitle(getString(R.string.open_source))
                                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                    .withAboutIconShown(true)
                                    .withVersionShown(true)
                                    .withAboutVersionShown(true)
                                    .intent(this@SampleActivity)
                            this@SampleActivity.startActivity(intent)
                        }
                        return false
                    }
                })
                .withSelectedItemByPosition(-1)
                .build()

        //create our FastAdapter which will manage everything
        modelAdapter = ModelAdapter {
            SimpleItem().withName("getStory: " + it.uuid).withDescription(it.name)
        }
        fastAdapter = FastAdapter.with(modelAdapter)

        //configure our fastAdapter
        //get our recyclerView and do basic setup
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = fastAdapter
        fastAdapter.withSavedInstanceState(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel.stories.observe(this, Observer {
            modelAdapter.set(it)
        })
    }

    override fun onSaveInstanceState(state: Bundle) {
        var outState = state
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState)
        //add the values which need to be saved from the adapter to the bundel
        outState = fastAdapter.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result.isDrawerOpen) {
            result.closeDrawer()
        } else {
            super.onBackPressed()
        }
    }
}
