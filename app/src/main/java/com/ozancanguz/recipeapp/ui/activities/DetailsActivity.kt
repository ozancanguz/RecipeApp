package com.ozancanguz.recipeapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ozancanguz.recipeapp.R
import com.ozancanguz.recipeapp.adapter.PagerAdapter
import com.ozancanguz.recipeapp.data.models.db.entities.FavoriteEntity
import com.ozancanguz.recipeapp.ui.fragments.ingredients.IngredientsFragment
import com.ozancanguz.recipeapp.ui.fragments.instructions.InstructionsFragment
import com.ozancanguz.recipeapp.ui.fragments.overview.OverviewFragment
import com.ozancanguz.recipeapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel:MainViewModel by viewModels()

    private var recipeSaved=false
    private var savedRecipeId=0


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
        }else if(item.itemId ==R.id.savetofavmenu&&!recipeSaved){
            savetoFavorites(item)
        }else if (item.itemId == R.id.savetofavmenu&& recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoriteEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)

       showSnackBar("removed from favorites")
        recipeSaved = false
    }

    private fun savetoFavorites(item: MenuItem) {
        var favoriteEntity=FavoriteEntity(0,args.result)
        mainViewModel.insertFavoriteRecipe(favoriteEntity)

        // change star icon color
      changeMenuItemColor(item,R.color.yellow)
      showSnackBar("Recipe saved")
        recipeSaved=true
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this,color))
    }

    private fun showSnackBar(msg: String) {

        Snackbar.make(detailsLayout,"Recipe saved" ,Snackbar.LENGTH_LONG)
            .setAction("OK"){}
            .show()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        // check saved recipe fun
        val menuItem=menu?.findItem(R.id.savetofavmenu)
        checkSavedRecipe(menuItem!!)

        return true


    }

    // this fun checks if recipe save or not
    private fun checkSavedRecipe(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this,
        Observer { favoritesEntity ->
            try {
                for(savedRecipe in favoritesEntity){
                    if(savedRecipe.result.id==args.result.id){
                        changeMenuItemColor(menuItem,R.color.yellow)
                        savedRecipeId=savedRecipe.id
                        recipeSaved = true
                    }
                }
            }catch (e:Exception){
                Log.d("detailsactivity " ,e.message.toString())
            }

        })

    }


}