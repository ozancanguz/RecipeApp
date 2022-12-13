package com.ozancanguz.recipeapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.adapter.PagerAdapter
import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
import com.ozancanguz.recipeapp.ui.fragments.ingredients.IngredientsFragment
import com.ozancanguz.recipeapp.ui.fragments.instructions.InstructionsFragment
import com.ozancanguz.recipeapp.ui.fragments.overview.OverviewFragment
import com.ozancanguz.recipeapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        val fragments=ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles=ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")


        val resultBundle=Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter= PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager

        )
        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            // save to favorites
        }else if(item.itemId ==R.id.savetofavmenu){
            savetoFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun savetoFavorites(item: MenuItem) {
        var favoriteEntity=FavoriteEntity(0,args.result)
        mainViewModel.insertFavoriteRecipe(favoriteEntity)




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        return true


    }


}