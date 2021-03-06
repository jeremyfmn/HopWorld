package com.jfalck.hopworld.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.jfalck.hopworld.R
import com.jfalck.hopworld.data.BeerTypes
import com.jfalck.hopworld.makeVisible
import com.jfalck.hopworld.utils.GoogleSignUtils
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    private var chips = mutableListOf<Chip>()

    private val checkedChangeListener =
        CompoundButton.OnCheckedChangeListener { chip, isChecked ->
            profileViewModel.updateLikedBeers(BeerTypes.values()[chips.indexOf(chip)], !isChecked)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { context ->
            GoogleSignUtils.getAccount(context)?.let { account ->
                tv_account_name.text = account.displayName
                Glide.with(this).load(account.photoUrl.toString()).into(iv_avatar_image)
                initChips()
            }
        }
        observeViewModel()
    }

    private fun initChips() {
        BeerTypes.values().forEach { beerType ->
            (layoutInflater.inflate(R.layout.custom_chip, chip_group, false) as Chip).apply {
                text = beerType.shortName
                R.style.Widget_MaterialComponents_Chip_Action
                // necessary to get single selection working
                isClickable = true
                isCheckable = true
                chip_group.addView(this)
                setOnCheckedChangeListener(checkedChangeListener)
                chips.add(this)
            }
        }
    }

    private fun observeViewModel() {
        profileViewModel.beerTypesLikedLiveData.observeForever { beerTypes ->
            chips.forEachIndexed { index, chip ->
                chip.setOnCheckedChangeListener(null)
                chip.isChecked = beerTypes.contains(BeerTypes.values()[index])
                chip.setOnCheckedChangeListener(checkedChangeListener)
            }
            show(favorite_beers_container)
        }
    }

    private fun show(view: View) {
        if (view.visibility == View.GONE) {
            view.apply {
                alpha = 0f
                makeVisible()
                animate().alpha(1f).setDuration(300).setListener(null)
            }
        }
    }
}
