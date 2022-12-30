package com.example.fitpeoassignment.di

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.fitpeoassignment.ui.DetailsActivity
import com.example.fitpeoassignment.ui.MainActivity
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.android.synthetic.main.item_list.view.*

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailsActivity(): DetailsActivity

    companion object {
        @JvmStatic
        @BindingAdapter("mainImageUrl")
       fun mainImageUrl(view: ImageView, imageUrl: String?) {
            val picasso =
                Picasso.Builder(view.context!!)
                    .listener { picasso, uri, exception ->
                        Log.e("IMAGE_ERROR", "ERROR :- " + exception.message)
                    }
                    .build()
            picasso.load(imageUrl)
                .into(view)
        }
    }

}